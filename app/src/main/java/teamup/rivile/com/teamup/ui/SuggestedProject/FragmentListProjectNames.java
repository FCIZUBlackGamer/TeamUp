package teamup.rivile.com.teamup.ui.SuggestedProject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.google.gson.Gson;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.ui.DrawerActivity;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.FilterModel;
import teamup.rivile.com.teamup.Uitls.APIModels.Offer;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;

public class FragmentListProjectNames extends Fragment {
    private ConstraintLayout mLoadingViewConstraintLayout;

    public static int NORMAL = 0;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    View view;
    static int DepId = -1;
    static int ProType = -1;
    static String Word = null;
    static int Type = -1;
    static FilterModel filterModel;
    Realm realm;
    List<LikeModelDataBase> likeModelDataBase;
    Switch sProType;
    ConstraintLayout cl_emptyView;

    /**
     * @param id refers to Department Id from Home Page
     */
    public static FragmentListProjectNames setDepId(int id) {
        DepId = id;
        Word = null;
        return new FragmentListProjectNames();
    }

    /**
     * @param id refers to favourite projects(2) or all projects(0)
     */
    public static FragmentListProjectNames setType(int id) {
        ProType = id;
        DepId = -1;
        return new FragmentListProjectNames();
    }

    /**
     * @param filter refers to my filtered projects from FilterSearchFragment
     */
    public static FragmentListProjectNames setFilteredOffers(FilterModel filter) {
        filterModel = filter;
        return new FragmentListProjectNames();
    }

    /**
     * @param word refers to my projects(1), favourite projects(2) or all projects(-1)
     * @param type {2: UserName, 1: ProjectName, 0: Tag}
     */
    public static FragmentListProjectNames setWord(int type, String word) {
        Word = word;//Todo: Make Action
        Type = type;
        DepId = -1;
        return new FragmentListProjectNames();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_projects, container, false);

        mLoadingViewConstraintLayout = view.findViewById(R.id.cl_loading);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = view.findViewById(R.id.rec);
        cl_emptyView = view.findViewById(R.id.cl_emptyView);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).showSearchBar("ListProjects");
        ((DrawerActivity) getActivity()).showFab();

        mLoadingViewConstraintLayout.setVisibility(View.VISIBLE);

        realm = Realm.getDefaultInstance();

        LoginDataBase loginData = realm.where(LoginDataBase.class)
                .findFirst();

        likeModelDataBase = loginData.getLikes();
        Log.e("UserId", loginData.getUser().getId() + "");


        if (DepId != -1) {
            Type = -1;
            Word = null;
            loadOffers(DepId);
        } else if (Word != null) {
            DepId = -1;/** For Reducing Network Useless Connections about load offers with DepID if it's ot -1**/
            loadOffers(Type, Word);
        } else if (filterModel != null) {
            Type = -1;
            Word = null;
            DepId = -1;
            loadOffers(filterModel);
        }

    }

//    public static Offer convertList(RealmList<OfferDetailsDataBase> offerDetailsDataBases) {
//        Offer offer = new Offer();
//        List<Offers> offers = new ArrayList<>();
//
//        for (int j = 0; j < offerDetailsDataBases.size(); j++) {
//            Offers offersItem = new Offers();
//            offersItem.setId(offerDetailsDataBases.get(j).getId());
//            Log.e("rrrrrrrrrrrr", offerDetailsDataBases.get(j).getEducationContributorLevel() + " gg");
//            offersItem.setEducationContributorLevel(offerDetailsDataBases.get(j).getEducationContributorLevel());
//            offersItem.setNumContributorTo(offerDetailsDataBases.get(j).getNumContributorTo());
//            offersItem.setNumContributorFrom(offerDetailsDataBases.get(j).getNumContributorFrom());
//            offersItem.setGenderContributor(offerDetailsDataBases.get(j).getGenderContributor());
//            offersItem.setProfitFrom(offerDetailsDataBases.get(j).getProfitFrom());
//            offersItem.setProfitType(offerDetailsDataBases.get(j).getProfitType());
//            offersItem.setName(offerDetailsDataBases.get(j).getName());
//            offersItem.setDescription(offerDetailsDataBases.get(j).getDescription());
//            offersItem.setAgeRequiredFrom(offerDetailsDataBases.get(j).getAgeRequiredFrom());
//            offersItem.setAgeRequiredTo(offerDetailsDataBases.get(j).getAgeRequiredTo());
//            offersItem.setCategoryId(offerDetailsDataBases.get(j).getCategoryId());
//            offersItem.setCategoryName(offerDetailsDataBases.get(j).getCategoryName());
//            offersItem.setNumJoinOffer(offerDetailsDataBases.get(j).getNumJoinOffer());
//            offersItem.setNumLiks(offerDetailsDataBases.get(j).getNumLiks());
//            offersItem.setStatus(offerDetailsDataBases.get(j).getStatus());
//            offersItem.setUserId(offerDetailsDataBases.get(j).getUserId());
//
//            List<UserModel> userModels = new ArrayList<>();
//            for (int i = 0; i < offerDetailsDataBases.get(j).getUsers().size(); i++) {
//                UserModel userModel = new UserModel();
//                UserDataBase userDataBase = offerDetailsDataBases.get(j).getUsers().get(i);
//                userModel.setId(userDataBase.getId());
//                userModel.setFullName(userDataBase.getFullName());
//                userModel.setPassword(userDataBase.getPassword());
//                userModel.setGender(userDataBase.getGender());
//                userModel.setDateOfBirth(userDataBase.getDateOfBirth());
//                userModel.setPhone(userDataBase.getPhone());
//                userModel.setAddress(userDataBase.getAddress());
//                userModel.setImage(userDataBase.getImage());
//                userModel.setJobtitle(userDataBase.getJobtitle());
//                userModel.setStatus(userDataBase.getStatus());
//                userModel.setBio(userDataBase.getBio());
//                userModel.setMail(userDataBase.getMail());
//                userModel.setNumProject(userDataBase.getNumProject());
//                userModel.setCapitalId(userDataBase.getCapitalId());
//                userModel.setIdentityNum(userDataBase.getIdentityNum());
//                userModel.setSocialId(userDataBase.getSocialId());
//                userModel.setIdentityImage(userDataBase.getIdentityImage());
//                userModels.add(userModel);
//            }
//            offersItem.setUsers(userModels);
//
//            List<StateModel> stateModels = new ArrayList<>();
//            for (int i = 0; i < offerDetailsDataBases.get(j).getCapitals().size(); i++) {
//                StateModel stateModel = new StateModel();
//                CapitalModelDataBase capitalDataBase = offerDetailsDataBases.get(j).getCapitals().get(i);
//                stateModel.setId(capitalDataBase.getId());
//                stateModel.setName(capitalDataBase.getName());
//                stateModels.add(stateModel);
//            }
//            offersItem.setCapitals(stateModels);
//
////            List<RequirmentModel> requirmentModels = new ArrayList<>();
////            for (int i = 0; i < offerDetailsDataBases.get(j).getRequirments().size(); i++) {
////                RequirmentModel requirmentModel = new RequirmentModel();
////                OfferDetailsRequirmentDataBase capitalDataBase = offerDetailsDataBases.get(j).getRequirments().get(i);
////                requirmentModel.setId(capitalDataBase.getId());
////                requirmentModel.setPlaceAddress(capitalDataBase.getPlaceAddress());
////                requirmentModel.setPlaceDescriptions(capitalDataBase.getPlaceDescriptions());
////                requirmentModel.setMoneyDescriptions(capitalDataBase.getMoneyDescriptions());
////                requirmentModel.setExperienceDescriptions(capitalDataBase.getExperienceDescriptions());
////                requirmentModel.setExperienceTypeId(capitalDataBase.getExperienceTypeId());
////                requirmentModel.setNeedPlaceStatus(capitalDataBase.isNeedPlaceStatus());
////                requirmentModel.setNeedPlaceType(capitalDataBase.isNeedPlaceType());
////                requirmentModel.setNeedPlace(capitalDataBase.isNeedPlace());
////                requirmentModel.setNeedMoney(capitalDataBase.isNeedMoney());
////                requirmentModel.setNeedExperience(capitalDataBase.isNeedExperience());
////                requirmentModel.setMoneyFrom(capitalDataBase.getMoneyFrom());
////                requirmentModel.setMoneyTo(capitalDataBase.getMoneyTo());
////                requirmentModel.setExperienceFrom(capitalDataBase.getExperienceFrom());
////                requirmentModel.setExperienceTo(capitalDataBase.getExperienceTo());
////                requirmentModel.setUserId(capitalDataBase.getUserId());
////                requirmentModels.add(requirmentModel);
////            }
////            offersItem.setRequirments(requirmentModels);
//            offers.add(offersItem);
//        }
//
//        offer.setOffers(offers);
//        return offer;
//    }
//
//    private Offer convertResult(RealmResults<OfferDetailsDataBase> offerDetailsDataBases) {
//        Offer offer = new Offer();
//        List<Offers> offers = new ArrayList<>();
//        Offers offersItem = new Offers();
//        offersItem.setId(offerDetailsDataBases.get(0).getId());
//        offersItem.setEducationContributorLevel(offerDetailsDataBases.get(0).getEducationContributorLevel());
//        offersItem.setNumContributorTo(offerDetailsDataBases.get(0).getNumContributorTo());
//        offersItem.setNumContributorFrom(offerDetailsDataBases.get(0).getNumContributorFrom());
//        offersItem.setGenderContributor(offerDetailsDataBases.get(0).getGenderContributor());
//        offersItem.setProfitFrom(offerDetailsDataBases.get(0).getProfitFrom());
//        offersItem.setProfitType(offerDetailsDataBases.get(0).getProfitType());
//        offersItem.setName(offerDetailsDataBases.get(0).getName());
//        offersItem.setDescription(offerDetailsDataBases.get(0).getDescription());
//        offersItem.setAgeRequiredFrom(offerDetailsDataBases.get(0).getAgeRequiredFrom());
//        offersItem.setAgeRequiredTo(offerDetailsDataBases.get(0).getAgeRequiredTo());
//        offersItem.setCategoryId(offerDetailsDataBases.get(0).getCategoryId());
//        offersItem.setCategoryName(offerDetailsDataBases.get(0).getCategoryName());
//        offersItem.setNumJoinOffer(offerDetailsDataBases.get(0).getNumJoinOffer());
//        offersItem.setNumLiks(offerDetailsDataBases.get(0).getNumLiks());
//        offersItem.setStatus(offerDetailsDataBases.get(0).getStatus());
//        offersItem.setUserId(offerDetailsDataBases.get(0).getUserId());
//
//        List<UserModel> userModels = new ArrayList<>();
//        for (int i = 0; i < offerDetailsDataBases.get(0).getUsers().size(); i++) {
//            UserModel userModel = new UserModel();
//            UserDataBase userDataBase = offerDetailsDataBases.get(0).getUsers().get(i);
//            userModel.setId(userDataBase.getId());
//            userModel.setFullName(userDataBase.getFullName());
//            userModel.setPassword(userDataBase.getPassword());
//            userModel.setGender(userDataBase.getGender());
//            userModel.setDateOfBirth(userDataBase.getDateOfBirth());
//            userModel.setPhone(userDataBase.getPhone());
//            userModel.setAddress(userDataBase.getAddress());
//            userModel.setImage(userDataBase.getImage());
//            userModel.setJobtitle(userDataBase.getJobtitle());
//            userModel.setStatus(userDataBase.getStatus());
//            userModel.setBio(userDataBase.getBio());
//            userModel.setMail(userDataBase.getMail());
//            userModel.setNumProject(userDataBase.getNumProject());
//            userModel.setCapitalId(userDataBase.getCapitalId());
//            userModel.setIdentityNum(userDataBase.getIdentityNum());
//            userModel.setSocialId(userDataBase.getSocialId());
//            userModel.setIdentityImage(userDataBase.getIdentityImage());
//            userModels.add(userModel);
//        }
//        offersItem.setUsers(userModels);
//
//        List<StateModel> stateModels = new ArrayList<>();
//        for (int i = 0; i < offerDetailsDataBases.get(0).getCapitals().size(); i++) {
//            StateModel stateModel = new StateModel();
//            CapitalModelDataBase capitalDataBase = offerDetailsDataBases.get(0).getCapitals().get(i);
//            stateModel.setId(capitalDataBase.getId());
//            stateModel.setName(capitalDataBase.getName());
//            stateModels.add(stateModel);
//        }
//        offersItem.setCapitals(stateModels);
//
////        List<RequirmentModel> requirmentModels = new ArrayList<>();
////        for (int i = 0; i < offerDetailsDataBases.get(0).getRequirments().size(); i++) {
////            RequirmentModel requirmentModel = new RequirmentModel();
////            OfferDetailsRequirmentDataBase capitalDataBase = offerDetailsDataBases.get(0).getRequirments().get(i);
////            requirmentModel.setId(capitalDataBase.getId());
////            requirmentModel.setPlaceAddress(capitalDataBase.getPlaceAddress());
////            requirmentModel.setPlaceDescriptions(capitalDataBase.getPlaceDescriptions());
////            requirmentModel.setMoneyDescriptions(capitalDataBase.getMoneyDescriptions());
////            requirmentModel.setExperienceDescriptions(capitalDataBase.getExperienceDescriptions());
////            requirmentModel.setExperienceTypeId(capitalDataBase.getExperienceTypeId());
////            requirmentModel.setNeedPlaceStatus(capitalDataBase.isNeedPlaceStatus());
////            requirmentModel.setNeedPlaceType(capitalDataBase.isNeedPlaceType());
////            requirmentModel.setNeedPlace(capitalDataBase.isNeedPlace());
////            requirmentModel.setNeedMoney(capitalDataBase.isNeedMoney());
////            requirmentModel.setNeedExperience(capitalDataBase.isNeedExperience());
////            requirmentModel.setMoneyFrom(capitalDataBase.getMoneyFrom());
////            requirmentModel.setMoneyTo(capitalDataBase.getMoneyTo());
////            requirmentModel.setExperienceFrom(capitalDataBase.getExperienceFrom());
////            requirmentModel.setExperienceTo(capitalDataBase.getExperienceTo());
////            requirmentModel.setUserId(capitalDataBase.getUserId());
////            requirmentModels.add(requirmentModel);
////        }
////        offersItem.setRequirments(requirmentModels);
//        offers.add(offersItem);
//        offer.setOffers(offers);
//        return offer;
//    }

    private void loadOffers(int depId) {
        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.HOME_URL);

        RetrofitMethods getOffers = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<Offer> call;

        if (depId != -1)
            call = getOffers.getOffersByCatId("1", depId, API.URL_TOKEN);//TODO: get user ID
        else call = getOffers.getAllOffers("1", API.URL_TOKEN);//TODO: get user ID

        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, retrofit2.Response<Offer> response) {
                Offer serverResponse = response.body();
                if (serverResponse != null) {
                    fillOffers(serverResponse, NORMAL);
                } else {
                    Log.d("DABUGG", "serverResponse = null");
                }

                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.d("DABUGG", t.getMessage());

                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }
        });
    }

    private void loadOffers(FilterModel filterModel) {
        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.HOME_URL);

        Gson gson = new Gson();
        RetrofitMethods getOffers = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<Offer> call = getOffers.filterSearchOffer(gson.toJson(filterModel), API.URL_TOKEN);

        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, retrofit2.Response<Offer> response) {
                Offer serverResponse = response.body();
                if (serverResponse != null) {
                    fillOffers(serverResponse, NORMAL);
                } else {
                    Log.d("DABUGG", "serverResponse = null");
                }

                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.d("DABUGG", t.getMessage());

                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }
        });
    }

    private void loadOffers(int type, String word) {
        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.HOME_URL);

        RetrofitMethods getOffers = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<Offer> call;

        call = getOffers.searchOffer(type, word, API.URL_TOKEN);

        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, retrofit2.Response<Offer> response) {
                Offer serverResponse = response.body();
                if (serverResponse != null) {
                    cl_emptyView.setVisibility(View.GONE);
                    if (serverResponse.getOffers().size() > 0) {
                        fillOffers(serverResponse, NORMAL);
                    }
//                    else {
//                        ((DrawerActivity) getActivity()).hideSearchBar();
//                        //Todo: showSearchBar Empty view
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.frame, new FragmentEmpty()).commit();
//                    }
                } else {
                    Log.d("DABUGG", "serverResponse = null");
                }

                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.d("DABUGG", t.getMessage());
                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }
        });
    }

    private void fillOffers(Offer offers, int type) {
//        if (likeModelDataBase != null) {
        Log.e("A Size", offers.getOffers().size() + "");
        if (offers.getOffers() != null && !offers.getOffers().isEmpty() && offers.getOffers().size() > 0) {
            cl_emptyView.setVisibility(View.GONE);
            adapter = new AdapterListOffers(getActivity(),
                    offers.getOffers(),
                    likeModelDataBase);

            recyclerView.setAdapter(adapter);
        }
//            else{
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.frame, new FragmentEmpty())
//                        .commit();
//            }
//        }
    }
}