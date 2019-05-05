package teamup.rivile.com.teamup.ui.Project.Favourite;

import android.content.Intent;
import android.net.Uri;
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
import teamup.rivile.com.teamup.ui.Project.List.AdapterListOffers;
import teamup.rivile.com.teamup.ui.Project.ShareDialogFragment;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.FilterModel;
import teamup.rivile.com.teamup.Uitls.APIModels.Offer;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.FavouriteDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDataBase;
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
    List<FavouriteDataBase> favouriteDataBases;
    ConstraintLayout cl_emptyView;


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
        cl_emptyView = view.findViewById(R.id.cl_emptyView);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).showSearchBar("ListProjects");
        ((DrawerActivity) getActivity()).showFab();
        ((DrawerActivity) getActivity()).hideFab();

        likeModelDataBase = new ArrayList<>();
//        if (recyclerView != null ){
//            recyclerView.setAdapter(null);
//        }
        realm = Realm.getDefaultInstance();
        //realm.beginTransaction();
        LoginDataBase loginData = realm.where(LoginDataBase.class)
                .findFirst();

        likeModelDataBase = loginData.getLikes();
        favouriteDataBases = loginData.getFavorites();
        Log.e("UserId", loginData.getUser().getId() + "");
        Log.e("Type", FAVOURITE + "");

        ((DrawerActivity) getActivity()).setTitle(getString(R.string.favourite));

        RealmResults<LoginDataBase> loginDataBases = realm.where(LoginDataBase.class)
                .findAll();
        RealmList<FavouriteDataBase> favouriteDataBases = loginDataBases.get(0).getFavorites();
        if (favouriteDataBases != null && favouriteDataBases.size() > 0) {
            cl_emptyView.setVisibility(View.GONE);
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

        }


    }


    private Offer convertResult(RealmResults<OfferDataBase> offerDetailsDataBases) {
        Offer offer = new Offer();
        List<Offers> offers = new ArrayList<>();
        Offers offersItem = new Offers();
        offersItem.setId(offerDetailsDataBases.get(0).getId());
//        offersItem.setEducationContributorLevel(offerDetailsDataBases.get(0).getEducationContributorLevel());
//        offersItem.setNumContributorTo(offerDetailsDataBases.get(0).getNumContributorTo());
//        offersItem.setNumContributorFrom(offerDetailsDataBases.get(0).getNumContributorFrom());
        offersItem.setGenderContributor(offerDetailsDataBases.get(0).getGenderContributor());
//        offersItem.setProfitFrom(offerDetailsDataBases.get(0).getProfitFrom());
        offersItem.setProfitType(offerDetailsDataBases.get(0).getProfitType());
        offersItem.setName(offerDetailsDataBases.get(0).getName());
        offersItem.setDescription(offerDetailsDataBases.get(0).getDescription());
//        offersItem.setAgeRequiredFrom(offerDetailsDataBases.get(0).getAgeRequiredFrom());
//        offersItem.setAgeRequiredTo(offerDetailsDataBases.get(0).getAgeRequiredTo());
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

        offers.add(offersItem);
        offer.setOffers(offers);
        return offer;
    }

    private void loadOffers(int depId) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.HOME_URL);

        ApiConfig getOffers = appConfig.getRetrofit().create(ApiConfig.class);
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
                    cl_emptyView.setVisibility(View.GONE);
                    if (serverResponse.getOffers().size() > 0) {
                        fillOffers(serverResponse, FAVOURITE);
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
        Log.e("A Size", offers.getOffers().size() + "");
        if (offers.getOffers() != null && !offers.getOffers().isEmpty() && offers.getOffers().size() > 0) {
            cl_emptyView.setVisibility(View.GONE);
            adapter = new AdapterListOffers(getActivity(),
                    offers.getOffers(),
                    likeModelDataBase,
                    favouriteDataBases,
                    type,
                    this);

            recyclerView.setAdapter(adapter);
        }
//        else {
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.frame, new FragmentEmpty())
//                    .commit();
//        }
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