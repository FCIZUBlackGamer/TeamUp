package teamup.rivile.com.teamup.Project.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.AttachmentModel;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel;


public class FragmentListProjects extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Offers> offersList;
    View view;
    static int DepId;

    public static FragmentListProjects setDepId(int id){
        DepId = id;
        return new FragmentListProjects();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_projects, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        offersList = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).Show("ListProjects");

        loadOffers(DepId);
        adapter = new AdapterListOffers(getActivity(), offersList);
        recyclerView.setAdapter(adapter);
    }

    private void loadOffers(int depId) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.HOME_URL);

        ApiConfig getOffers = appConfig.getRetrofit().create(ApiConfig.class);
        Call<List<Offers>> call;
        if (true){
            call = getOffers.getOffers(depId, API.URL_TOKEN);
        }else {
            call = getOffers.getOffers(depId, API.URL_TOKEN);
        }
        call.enqueue(new Callback<List<Offers>>() {
            @Override
            public void onResponse(Call<List<Offers>> call, retrofit2.Response<List<Offers>> response) {
                List<Offers> serverResponse = response.body();
                if (serverResponse != null) {
                    fillOffers(serverResponse);
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Offers>> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }

    private void fillOffers(List<Offers> offers) {
        adapter = new AdapterListOffers(getActivity(), offers);
        recyclerView.setAdapter(adapter);
    }
}
