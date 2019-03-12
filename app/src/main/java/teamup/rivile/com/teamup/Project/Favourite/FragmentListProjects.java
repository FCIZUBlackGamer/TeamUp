package teamup.rivile.com.teamup.Project.Favourite;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.EmptyView.FragmentEmpty;
import teamup.rivile.com.teamup.Project.List.AdapterListOffers;
import teamup.rivile.com.teamup.Project.ShareDialogFragment;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.CapitalModel;
import teamup.rivile.com.teamup.Uitls.APIModels.FilterModel;
import teamup.rivile.com.teamup.Uitls.APIModels.Offer;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.CapitalModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.FavouriteDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDetailsDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDetailsRequirmentDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.UserDataBase;


public class FragmentListProjects extends Fragment implements ShareDialogFragment.Helper, AdapterListOffers.Helper {
    private String mProjectURL = "";
    private String mProjectName = "";
    public static int MINE = 1;
    public static int FAVOURITE = 2;
    public static int NORMAL = 0;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    View view;
    static int ProType = -1;
    static FilterModel filterModel;
    Realm realm;
    List<LikeModelDataBase> likeModelDataBase;


    /**
     * @param id refers to my projects(1), favourite projects(2) or all projects(-1)
     */
    public static FragmentListProjects setType(int id) {
        ProType = id;
        return new FragmentListProjects();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_projects, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = view.findViewById(R.id.rec);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).Hide();
        ((DrawerActivity) getActivity()).hideFab();

//        likeModelDataBase = new ArrayList<>();
//        if (recyclerView != null ){
//            recyclerView.setAdapter(null);
//        }
        realm = Realm.getDefaultInstance();
        //realm.beginTransaction();
        LoginDataBase loginData = realm.where(LoginDataBase.class)
                .findFirst();

        likeModelDataBase = loginData.getLikes();
        Log.e("UserId", loginData.getUser().getId() + "");
        Log.e("Type", ProType + "");

        ((DrawerActivity) getActivity()).setTitle(getString(R.string.favourite));

        RealmResults<LoginDataBase> loginDataBases = realm.where(LoginDataBase.class)
                .findAll();
        RealmList<FavouriteDataBase> favouriteDataBases = loginDataBases.get(0).getFavorites();
        if (favouriteDataBases != null && favouriteDataBases.size() > 0) {
            /**
             * get offer ids from favouriteDataBases
             *
             * and fetch them from OfferDetailsDataBase
             *
             * */
            List<Integer> offerIds = new ArrayList<>();
            for (int i = 0; i < favouriteDataBases.size(); i++) {
                offerIds.add(favouriteDataBases.get(i).getOfferId());
            }

            loadOffers(offerIds);
//                RealmList<OfferDetailsDataBase> offerDetailsDataBases = new RealmList<>();
//                for (int i = 0; i < offerIds.size(); i++) {
//                    offerDetailsDataBases.add(realm.where(OfferDetailsDataBase.class)
//                            .equalTo("Id", offerIds.get(i))
//                            .findFirst());
//                }
//
////                Log.e("UserId Mine", String.valueOf(userDataBase.getId()));
//                if (offerDetailsDataBases.size() > 0) {
//                    List<Integer> list = new ArrayList<>();
//                    for (int i = 0; i < offerDetailsDataBases.size(); i++) {
//                        list.add(offerDetailsDataBases.get(i).ge);
//                    }
//                } else {
//                    ((DrawerActivity) getActivity()).Hide();
//                    //Todo: Show Empty view
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frame, new FragmentEmpty()).commit();
//                }


        } else {
            ((DrawerActivity) getActivity()).Hide();
            //Todo: Show Empty view
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, new FragmentEmpty()).commit();
        }

//        if (ProType == 1) {
//            ((DrawerActivity) getActivity()).setTitle(getString(R.string.savedProjects));
//
//            RealmResults<LoginDataBase> loginDataBases = realm.where(LoginDataBase.class)
//                    .findAll();
//            RealmList<FavouriteDataBase> offerDetailsDataBases = loginDataBases.get(0).getFavorites();
//
//////                Log.e("UserId Mine", String.valueOf(userDataBase.getId()));
//            Log.e("Size", offerDetailsDataBases.size() + "");
//            if (offerDetailsDataBases.size() > 0) {
//                Log.e("B Size", convertList(offerDetailsDataBases).getOffersList().size() + "");
//
//                Offer offers = convertList(offerDetailsDataBases);
//                fillOffers(offers, MINE);
//            } else {
//
//                ((DrawerActivity) getActivity()).Hide();
//                //Todo: Show Empty view
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame, new FragmentEmpty());
//            }
//
//        } else if (ProType == 2) {
//            ((DrawerActivity) getActivity()).setTitle(getString(R.string.favourite));
//
//            RealmResults<LoginDataBase> loginDataBases = realm.where(LoginDataBase.class)
//                    .findAll();
//            RealmList<FavouriteDataBase> favouriteDataBases = loginDataBases.get(0).getFavorites();
//            if (favouriteDataBases != null && favouriteDataBases.size() > 0) {
//                /**
//                 * get offer ids from favouriteDataBases
//                 *
//                 * and fetch them from OfferDetailsDataBase
//                 *
//                 * */
//                List<Integer> offerIds = new ArrayList<>();
//                for (int i = 0; i < favouriteDataBases.size(); i++) {
//                    offerIds.add(favouriteDataBases.get(i).getOfferId());
//                }
//
//                loadOffers(offerIds);
////                RealmList<OfferDetailsDataBase> offerDetailsDataBases = new RealmList<>();
////                for (int i = 0; i < offerIds.size(); i++) {
////                    offerDetailsDataBases.add(realm.where(OfferDetailsDataBase.class)
////                            .equalTo("Id", offerIds.get(i))
////                            .findFirst());
////                }
////
//////                Log.e("UserId Mine", String.valueOf(userDataBase.getId()));
////                if (offerDetailsDataBases.size() > 0) {
////                    List<Integer> list = new ArrayList<>();
////                    for (int i = 0; i < offerDetailsDataBases.size(); i++) {
////                        list.add(offerDetailsDataBases.get(i).ge);
////                    }
////                } else {
////                    ((DrawerActivity) getActivity()).Hide();
////                    //Todo: Show Empty view
////                    getActivity().getSupportFragmentManager().beginTransaction()
////                            .replace(R.id.frame, new FragmentEmpty()).commit();
////                }
//
//
//            } else {
//                ((DrawerActivity) getActivity()).Hide();
//                //Todo: Show Empty view
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame, new FragmentEmpty()).commit();
//            }
//        }


//        realm.executeTransaction(realm1 -> {
////            LoginDataBase loginData = realm1.where(LoginDataBase.class)
////                    .findFirst();
////            likeModelDataBase = loginData.getLikes();
//            Log.e("UserId", loginData.getUser().getId() + "");
//            Log.e("Type", ProType + "");
//            if (ProType == 1 ) {
//                ((DrawerActivity) getActivity()).setTitle(getString(R.string.savedProjects));
//
//                RealmResults<LoginDataBase> loginDataBases = realm1.where(LoginDataBase.class)
//                        .findAll();
//                RealmList<OfferDetailsDataBase> offerDetailsDataBases = loginDataBases.get(0).getOffers();
//
//////                Log.e("UserId Mine", String.valueOf(userDataBase.getId()));
//                Log.e("Size", offerDetailsDataBases.size() + "");
//                if (offerDetailsDataBases.size() > 0) {
//                    Log.e("B Size", convertList(offerDetailsDataBases).getOffersList().size() + "");
//                    //Todo: this method is calling twice
//                    fillOffers(convertList(offerDetailsDataBases), MINE);
//                } else {
//
//                    ((DrawerActivity) getActivity()).Hide();
//                    //Todo: Show Empty view
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frame, new FragmentEmpty());
//                }
//
//            } else if (ProType == 2) {
//                ((DrawerActivity) getActivity()).setTitle(getString(R.string.favourite));
//
//                RealmResults<LoginDataBase> loginDataBases = realm1.where(LoginDataBase.class)
//                        .findAll();
//                RealmList<FavouriteDataBase> favouriteDataBases = loginDataBases.get(0).getFavorites();
//                if (favouriteDataBases.size() > 0) {
//                    /**
//                     * get offer ids from favouriteDataBases
//                     *
//                     * and fetch them from OfferDetailsDataBase
//                     *
//                     * */
//                    List<Integer> offerIds = new ArrayList<>();
//                    for (int i = 0; i < favouriteDataBases.size(); i++) {
//                        offerIds.add(favouriteDataBases.get(i).getOfferId());
//                    }
//
//                    RealmList<OfferDetailsDataBase> offerDetailsDataBases = new RealmList<>();
//                    for (int i = 0; i < offerIds.size(); i++) {
//                        offerDetailsDataBases.add(realm1.where(OfferDetailsDataBase.class)
//                                .equalTo("Id", offerIds.get(i))
//                                .findFirst());
//                    }
//
////                Log.e("UserId Mine", String.valueOf(userDataBase.getId()));
//                    if (offerDetailsDataBases.size() > 0) {
//                        fillOffers(convertList(offerDetailsDataBases), FAVOURITE);
//                    } else {
//                        ((DrawerActivity) getActivity()).Hide();
//                        //Todo: Show Empty view
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.frame, new FragmentEmpty()).commit();
//                    }
//
//
//                } else {
//                    ((DrawerActivity) getActivity()).Hide();
//                    //Todo: Show Empty view
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frame, new FragmentEmpty()).commit();
//                }
//            }
//        });


    }

//    public static Offer convertList(RealmList<FavouriteDataBase> offerDetailsDataBases) {
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
//            List<CapitalModel> capitalModels = new ArrayList<>();
//            for (int i = 0; i < offerDetailsDataBases.get(j).getCapitals().size(); i++) {
//                CapitalModel capitalModel = new CapitalModel();
//                CapitalModelDataBase capitalDataBase = offerDetailsDataBases.get(j).getCapitals().get(i);
//                capitalModel.setId(capitalDataBase.getId());
//                capitalModel.setName(capitalDataBase.getName());
//                capitalModels.add(capitalModel);
//            }
//            offersItem.setCapitals(capitalModels);
//
//            List<RequirmentModel> requirmentModels = new ArrayList<>();
//            for (int i = 0; i < offerDetailsDataBases.get(j).getRequirments().size(); i++) {
//                RequirmentModel requirmentModel = new RequirmentModel();
//                OfferDetailsRequirmentDataBase capitalDataBase = offerDetailsDataBases.get(j).getRequirments().get(i);
//                requirmentModel.setId(capitalDataBase.getId());
//                requirmentModel.setPlaceAddress(capitalDataBase.getPlaceAddress());
//                requirmentModel.setPlaceDescriptions(capitalDataBase.getPlaceDescriptions());
//                requirmentModel.setMoneyDescriptions(capitalDataBase.getMoneyDescriptions());
//                requirmentModel.setExperienceDescriptions(capitalDataBase.getExperienceDescriptions());
//                requirmentModel.setExperienceTypeId(capitalDataBase.getExperienceTypeId());
//                requirmentModel.setNeedPlaceStatus(capitalDataBase.isNeedPlaceStatus());
//                requirmentModel.setNeedPlaceType(capitalDataBase.isNeedPlaceType());
//                requirmentModel.setNeedPlace(capitalDataBase.isNeedPlace());
//                requirmentModel.setNeedMoney(capitalDataBase.isNeedMoney());
//                requirmentModel.setNeedExperience(capitalDataBase.isNeedExperience());
//                requirmentModel.setMoneyFrom(capitalDataBase.getMoneyFrom());
//                requirmentModel.setMoneyTo(capitalDataBase.getMoneyTo());
//                requirmentModel.setExperienceFrom(capitalDataBase.getExperienceFrom());
//                requirmentModel.setExperienceTo(capitalDataBase.getExperienceTo());
//                requirmentModel.setUserId(capitalDataBase.getUserId());
//                requirmentModels.add(requirmentModel);
//            }
//            offersItem.setRequirments(requirmentModels);
//            offers.add(offersItem);
//        }
//
//        offer.setOffersList(offers);
//        return offer;
//    }

    private Offer convertResult(RealmResults<OfferDetailsDataBase> offerDetailsDataBases) {
        Offer offer = new Offer();
        List<Offers> offers = new ArrayList<>();
        Offers offersItem = new Offers();
        offersItem.setId(offerDetailsDataBases.get(0).getId());
        offersItem.setEducationContributorLevel(offerDetailsDataBases.get(0).getEducationContributorLevel());
        offersItem.setNumContributorTo(offerDetailsDataBases.get(0).getNumContributorTo());
        offersItem.setNumContributorFrom(offerDetailsDataBases.get(0).getNumContributorFrom());
        offersItem.setGenderContributor(offerDetailsDataBases.get(0).getGenderContributor());
        offersItem.setProfitFrom(offerDetailsDataBases.get(0).getProfitFrom());
        offersItem.setProfitType(offerDetailsDataBases.get(0).getProfitType());
        offersItem.setName(offerDetailsDataBases.get(0).getName());
        offersItem.setDescription(offerDetailsDataBases.get(0).getDescription());
        offersItem.setAgeRequiredFrom(offerDetailsDataBases.get(0).getAgeRequiredFrom());
        offersItem.setAgeRequiredTo(offerDetailsDataBases.get(0).getAgeRequiredTo());
        offersItem.setCategoryId(offerDetailsDataBases.get(0).getCategoryId());
        offersItem.setCategoryName(offerDetailsDataBases.get(0).getCategoryName());
        offersItem.setNumJoinOffer(offerDetailsDataBases.get(0).getNumJoinOffer());
        offersItem.setNumLiks(offerDetailsDataBases.get(0).getNumLiks());
        offersItem.setStatus(offerDetailsDataBases.get(0).getStatus());
        offersItem.setUserId(offerDetailsDataBases.get(0).getUserId());

        List<UserModel> userModels = new ArrayList<>();
        for (int i = 0; i < offerDetailsDataBases.get(0).getUsers().size(); i++) {
            UserModel userModel = new UserModel();
            UserDataBase userDataBase = offerDetailsDataBases.get(0).getUsers().get(i);
            userModel.setId(userDataBase.getId());
            userModel.setFullName(userDataBase.getFullName());
            userModel.setPassword(userDataBase.getPassword());
            userModel.setGender(userDataBase.getGender());
            userModel.setDateOfBirth(userDataBase.getDateOfBirth());
            userModel.setPhone(userDataBase.getPhone());
            userModel.setAddress(userDataBase.getAddress());
            userModel.setImage(userDataBase.getImage());
            userModel.setJobtitle(userDataBase.getJobtitle());
            userModel.setStatus(userDataBase.getStatus());
            userModel.setBio(userDataBase.getBio());
            userModel.setMail(userDataBase.getMail());
            userModel.setNumProject(userDataBase.getNumProject());
            userModel.setCapitalId(userDataBase.getCapitalId());
            userModel.setIdentityNum(userDataBase.getIdentityNum());
            userModel.setSocialId(userDataBase.getSocialId());
            userModel.setIdentityImage(userDataBase.getIdentityImage());
            userModels.add(userModel);
        }
        offersItem.setUsers(userModels);

        List<CapitalModel> capitalModels = new ArrayList<>();
        for (int i = 0; i < offerDetailsDataBases.get(0).getCapitals().size(); i++) {
            CapitalModel capitalModel = new CapitalModel();
            CapitalModelDataBase capitalDataBase = offerDetailsDataBases.get(0).getCapitals().get(i);
            capitalModel.setId(capitalDataBase.getId());
            capitalModel.setName(capitalDataBase.getName());
            capitalModels.add(capitalModel);
        }
        offersItem.setCapitals(capitalModels);

        List<RequirmentModel> requirmentModels = new ArrayList<>();
        for (int i = 0; i < offerDetailsDataBases.get(0).getRequirments().size(); i++) {
            RequirmentModel requirmentModel = new RequirmentModel();
            OfferDetailsRequirmentDataBase capitalDataBase = offerDetailsDataBases.get(0).getRequirments().get(i);
            requirmentModel.setId(capitalDataBase.getId());
            requirmentModel.setPlaceAddress(capitalDataBase.getPlaceAddress());
            requirmentModel.setPlaceDescriptions(capitalDataBase.getPlaceDescriptions());
            requirmentModel.setMoneyDescriptions(capitalDataBase.getMoneyDescriptions());
            requirmentModel.setExperienceDescriptions(capitalDataBase.getExperienceDescriptions());
            requirmentModel.setExperienceTypeId(capitalDataBase.getExperienceTypeId());
            requirmentModel.setNeedPlaceStatus(capitalDataBase.isNeedPlaceStatus());
            requirmentModel.setNeedPlaceType(capitalDataBase.isNeedPlaceType());
            requirmentModel.setNeedPlace(capitalDataBase.isNeedPlace());
            requirmentModel.setNeedMoney(capitalDataBase.isNeedMoney());
            requirmentModel.setNeedExperience(capitalDataBase.isNeedExperience());
            requirmentModel.setMoneyFrom(capitalDataBase.getMoneyFrom());
            requirmentModel.setMoneyTo(capitalDataBase.getMoneyTo());
            requirmentModel.setExperienceFrom(capitalDataBase.getExperienceFrom());
            requirmentModel.setExperienceTo(capitalDataBase.getExperienceTo());
            requirmentModel.setUserId(capitalDataBase.getUserId());
            requirmentModels.add(requirmentModel);
        }
        offersItem.setRequirments(requirmentModels);
        offers.add(offersItem);
        offer.setOffersList(offers);
        return offer;
    }

    private void loadOffers(int depId) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.HOME_URL);

        ApiConfig getOffers = appConfig.getRetrofit().create(ApiConfig.class);
        Call<Offer> call;

        if (depId != -1)
            call = getOffers.getOffersByCatId(depId, API.URL_TOKEN);
        else call = getOffers.getAllOffers(API.URL_TOKEN);

        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, retrofit2.Response<Offer> response) {
                Offer serverResponse = response.body();
                if (serverResponse != null) {
                    fillOffers(serverResponse, NORMAL);
                } else {
                    Log.d("DABUGG", "serverResponse = null");
                }
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.d("DABUGG", t.getMessage());
            }
        });
    }

    private void loadOffers(FilterModel filterModel) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.HOME_URL);

        Gson gson = new Gson();
        ApiConfig getOffers = appConfig.getRetrofit().create(ApiConfig.class);
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
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.d("DABUGG", t.getMessage());
            }
        });
    }

    private void loadOffers(List<Integer> favIds) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.HOME_URL);

        ApiConfig getOffers = appConfig.getRetrofit().create(ApiConfig.class);
        Call<Offer> call;
        Gson gson = new Gson();

        call = getOffers.getFavourite(gson.toJson(favIds), API.URL_TOKEN);

        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, retrofit2.Response<Offer> response) {
                Offer serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getOffersList().size() > 0) {
                        fillOffers(serverResponse, ProType);
                    } else {
                        ((DrawerActivity) getActivity()).Hide();
                        //Todo: Show Empty view
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame, new FragmentEmpty()).commit();
                    }
                } else {
                    Log.d("DABUGG", "serverResponse = null");
                }
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.d("DABUGG", t.getMessage());
            }
        });
    }

    private void fillOffers(Offer offers, int type) {
//        if (likeModelDataBase != null) {
        Log.e("A Size", offers.getOffersList().size() + "");
        if (offers.getOffersList() != null && !offers.getOffersList().isEmpty() && offers.getOffersList().size() > 0) {
            adapter = new AdapterListOffers(getActivity(),
                    offers.getOffersList(),
                    likeModelDataBase,
                    type,
                    this);

            recyclerView.setAdapter(adapter);
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.frame, new FragmentEmpty())
                    .commit();
        }
//        }
    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.cl_facebook:
                try {
                    shareToFaceBook();
                } catch (IOException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.cl_twitter:
                shareToTwitter();
                break;

            case R.id.cl_whatsapp:
                shareToWhatsApp();
        }
    }

    private void shareToWhatsApp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);

        if (ShareDialogFragment.isWhatsAppAvailable)
            whatsappIntent.setPackage(ShareDialogFragment.WHATSAPP_PACKAGE);
        else if (ShareDialogFragment.isWhatsAppBusinessAvailable)
            whatsappIntent.setPackage(ShareDialogFragment.WHATSAPP_BUSINESS_PACKAGE);

        whatsappIntent.setType("text/plain");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, mProjectName + "\n" + mProjectURL + "\n");
//        if (imageUri != null)
//            whatsappIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ignored) {
            Toast.makeText(getContext(), ignored.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void shareToTwitter() {
        Intent twitterIntent = new Intent(Intent.ACTION_SEND);
        twitterIntent.setType("*/*");

        if (ShareDialogFragment.isTwitterAvailable)
            twitterIntent.setPackage(ShareDialogFragment.TWITTER_PACKAGE);
        else if (ShareDialogFragment.isTwitterLiteAvailable)
            twitterIntent.setPackage(ShareDialogFragment.TWITTER_LITE_PACKAGE);

        twitterIntent.putExtra(Intent.EXTRA_TEXT, mProjectName + "\n" + mProjectURL + "\n");
//        if (imageUri != null)
//            twitterIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

        twitterIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(twitterIntent);
        } catch (android.content.ActivityNotFoundException ignored) {
            Toast.makeText(getContext(), ignored.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void shareToFaceBook() throws IOException {
//        if (imageUri != null) {
//            //share image(s)
//            SharePhoto photo1 = new SharePhoto.Builder()
//                    .setBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri))
//                    .build();
//            ShareContent shareContent = new ShareMediaContent.Builder()
//                    .addMedium(photo1)
////                .addMedium(photo2)
////                .addMedium(photo3)
////                .addMedium(photo4)
//                    .build();
//
//            ShareDialog shareDialog = new ShareDialog(this);
//            shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);
//        } else {
        //share URL
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(mProjectURL))
                .setQuote(mProjectName)
                .build();

        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(shareLinkContent, ShareDialog.Mode.AUTOMATIC);
//        }
    }

    @Override
    public void shareUrl(String url, String projectName) {
        mProjectURL = url;
        mProjectName = projectName + " - " + getString(R.string.app_name);

        ShareDialogFragment.getInstance(FragmentListProjects.this)
                .show(getFragmentManager(), "ShareDialogFragment");
    }
}