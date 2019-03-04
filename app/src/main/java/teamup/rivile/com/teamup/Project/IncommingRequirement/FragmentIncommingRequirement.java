package teamup.rivile.com.teamup.Project.IncommingRequirement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.EmptyView.FragmentEmpty;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.Offer;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.AppModels.SpinnerModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;

public class FragmentIncommingRequirement extends Fragment {
    View view;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    Realm realm;
    private List<SpinnerModel> spinnerModels;
    private List<Offers> offerList;
    Spinner project_requests;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_filter_incoming_requests, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        project_requests = view.findViewById(R.id.project_requests);
        recyclerView = view.findViewById(R.id.rec);
        recyclerView.setLayoutManager(layoutManager);
        spinnerModels = new ArrayList<>();
        if (offerList == null) {
            offerList = new ArrayList<>();
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ((DrawerActivity) getActivity()).Hide();
        ((DrawerActivity) getActivity()).hideFab();
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            LoginDataBase loginDataBases = realm1.where(LoginDataBase.class)
                    .findFirst();
//            UserModel userModel = new UserModel();
//            userModel.setMail(loginDataBases.getUser().getMail());
//            userModel.setPassword(loginDataBases.getUser().getPassword());
//            userModel.setSocialId(loginDataBases.getUser().getSocialId());
            loadSpinnerData(loginDataBases.getUser().getId());
        });

    }

    private void fillOffers(OfferDetailsJsonObject offers) {
        if (offers.getOffer()!=null && offers.getOffer().getRequirments()!=null && offers.getOffer().getRequirments().size() > 0){
            adapter = new AdapterListRequirement(getActivity(), offers);
            recyclerView.setAdapter(adapter);
        }else {
            ((DrawerActivity) getActivity()).Hide();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, new FragmentEmpty()).commit();
            project_requests.setVisibility(View.GONE);
        }
    }

    private void fillSpinner(Offer serverResponse) {
        if (serverResponse.getOffersList().size() > 0) {
            for (int i = 0; i < serverResponse.getOffersList().size(); i++) {
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setId(serverResponse.getOffersList().get(0).getId());
                spinnerModel.setName(serverResponse.getOffersList().get(0).getName());
                spinnerModels.add(spinnerModel);
            }
            project_requests.setVisibility(View.VISIBLE);
            ArrayAdapter<SpinnerModel> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerModels);
            project_requests.setAdapter(adapter);

            project_requests.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("PO", spinnerModels.get(position).getId() + "");
                    loadRequirements(spinnerModels.get(position).getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
//                fillOffers(FragmentListProjects.convertList(offerDetailsDataBases));
        } else {
            //Todo: Show Empty view (You haven't make offers yet)
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//                FragmentTransaction trans = manager.beginTransaction();
//                trans.remove(myFrag);
//                trans.commit();
//                manager.popBackStack();

            ((DrawerActivity) getActivity()).Hide();
            ((DrawerActivity) getActivity()).hideFab();
            try {
                ((DrawerActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new FragmentEmpty()).commit();
            }catch (Exception e){

            }
            project_requests.setVisibility(View.GONE);
        }
    }

    private void loadRequirements(int id) {
        AppConfig appConfig = new AppConfig(API.BASE_URL);
        // Parsing any Media type file

        ApiConfig reg = appConfig.getRetrofit().create(ApiConfig.class);
        Call<OfferDetailsJsonObject> call = reg.getRequirements(id, API.URL_TOKEN);
        call.enqueue(new Callback<OfferDetailsJsonObject>() {
            @Override
            public void onResponse(Call<OfferDetailsJsonObject> call, retrofit2.Response<OfferDetailsJsonObject> response) {
                OfferDetailsJsonObject serverResponse = response.body();
                if (serverResponse != null) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.serializeNulls();
                    Gson gson = gsonBuilder.create();
                    Log.i("Response", gson.toJson(serverResponse));

                    fillOffers(serverResponse);
                } else {
                    //textView.setText(serverResponse.toString());
                    Log.e("Err", "Empty");
                }
            }

            @Override
            public void onFailure(Call<OfferDetailsJsonObject> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Err", t.getMessage());
            }
        });
    }

    private void loadSpinnerData(int userId) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);
        // Parsing any Media type file

        ApiConfig reg = appConfig.getRetrofit().create(ApiConfig.class);
        Call<Offer> call = reg.SelectOffer(userId, API.URL_TOKEN);
        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, retrofit2.Response<Offer> response) {
                Offer serverResponse = response.body();
                if (serverResponse != null) {
                    Gson gson = new Gson();
                    Log.i("Response", gson.toJson(serverResponse));
                    for (int i = 0; i < serverResponse.getOffersList().size(); i++) {
                        if (!offerList.contains(serverResponse.getOffersList().get(i))) {
                            offerList.add(serverResponse.getOffersList().get(i));
                        }
                    }

                    fillSpinner(serverResponse);
                } else {
                    //textView.setText(serverResponse.toString());
                    Log.e("Err", "Empty");
                }
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Err", t.getMessage());
            }
        });
    }

}
