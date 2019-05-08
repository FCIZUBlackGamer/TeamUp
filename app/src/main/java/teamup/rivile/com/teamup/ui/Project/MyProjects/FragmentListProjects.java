package teamup.rivile.com.teamup.ui.Project.MyProjects;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
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
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.ui.DrawerActivity;
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
    static String Word = null;
    public static int NORMAL = 0;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    View view;
    static FilterModel filterModel;
    Realm realm;
    List<LikeModelDataBase> likeModelDataBase;
    List<FavouriteDataBase> favouriteDataBases;
    ConstraintLayout cl_emptyView;
    TabLayout tabs;

    public static FragmentListProjects setWord(String word) {
        Word = word;//Todo: Make Action
        return new FragmentListProjects();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_projects, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        tabs = view.findViewById(R.id.tabs);
        recyclerView = view.findViewById(R.id.rec);
        cl_emptyView = view.findViewById(R.id.cl_emptyView);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).showSearchBar("MyProjects");
        ((DrawerActivity) getActivity()).setTitle("المشاريع المسجلة");
        ((DrawerActivity) getActivity()).hideFab();
        tabs.setVisibility(View.GONE);


//        likeModelDataBase = new ArrayList<>();
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

        ((DrawerActivity) getActivity()).setTitle(getString(R.string.savedProjects));

        RealmResults<LoginDataBase> loginDataBases = realm.where(LoginDataBase.class)
                .findAll();
        RealmList<OfferDataBase> offerDetailsDataBases = loginDataBases.get(0).getOffers();

////                Log.e("UserId Mine", String.valueOf(userDataBase.getId()));
        Log.e("Size", offerDetailsDataBases.size() + "");
        if (offerDetailsDataBases.size() > 0) {
            cl_emptyView.setVisibility(View.GONE);
            //Log.e("B Size", convertList(offerDetailsDataBases).getOffers().size() + "");

            if (Word != null) {
                RealmResults<OfferDataBase> filltered = loginDataBases.get(0).getOffers().where().contains("Name", Word).findAll();
                Offer offers = convertResult(filltered);
                fillOffers(offers, MINE);
            }else {
//                RealmResults<OfferDetailsDataBase> filltered = loginDataBases.get(0).getOffers();
                Offer offers = convertList(loginDataBases.get(0).getOffers());
                fillOffers(offers, MINE);
            }
        } else {
            cl_emptyView.setVisibility(View.GONE);
        }

    }

    public static Offer convertList(RealmList<OfferDataBase> offerDetailsDataBases) {
        Offer offer = new Offer();
        List<Offers> offers = new ArrayList<>();

        for (int j = 0; j < offerDetailsDataBases.size(); j++) {
            Offers offers1 = new Offers();
            OfferDataBase base = offerDetailsDataBases.get(j);
            offers1.setUserId(base.getUserId());
            offers1.setId(base.getId());
            offers1.setDescription(base.getDescription());
            offers1.setName(base.getName());
            offers1.setAddress(base.getAddress());
//            offers1.setDate(base.getDate());
            offers1.setNumContributor(base.getNumContributor());
            offers1.setGenderContributor(base.getGenderContributor());
            offers1.setNumJoinOffer(base.getNumJoinOffer());
            offers1.setNumLiks(base.getNumLiks());
            offers1.setStatus(base.getStatus());
            if (base.getUsers() != null && base.getUsers().size() > 0) {
                List<UserModel> userModels = new ArrayList<>();
                for (int i = 0; i < base.getUsers().size(); i++) {
                    UserModel userModel = new UserModel();
                    UserDataBase base1 = base.getUsers().get(i);
                    userModel.setId(base1.getId());
                    userModel.setFullName(base1.getFullName());
                    userModel.setImage(base1.getImage());
                    userModel.setId(base1.getId());
                    userModels.add(userModel);
                }
                offers1.setUsers(userModels);
            }
            offers.add(offers1);
        }

        offer.setOffers(offers);
        return offer;
    }

    private Offer convertResult(RealmResults<OfferDataBase> offerDetailsDataBases) {
        Offer offer = new Offer();
        List<Offers> offers = new ArrayList<>();
        for (int i = 0; i < offerDetailsDataBases.size(); i++) {
            Offers offers1 = new Offers();
            OfferDataBase base = offerDetailsDataBases.get(i);
            offers1.setUserId(base.getUserId());
            offers1.setId(base.getId());
            offers1.setDescription(base.getDescription());
            offers1.setName(base.getName());
            offers1.setAddress(base.getAddress());
//            offers1.setDate(base.getDate());
            offers1.setNumContributor(base.getNumContributor());
            offers1.setGenderContributor(base.getGenderContributor());
            offers1.setNumJoinOffer(base.getNumJoinOffer());
            offers1.setNumLiks(base.getNumLiks());
            offers1.setStatus(base.getStatus());
            if (base.getUsers() != null && base.getUsers().size() > 0) {
                List<UserModel> userModels = new ArrayList<>();
                for (int j = 0; j < base.getUsers().size(); j++) {
                    UserModel userModel = new UserModel();
                    UserDataBase base1 = base.getUsers().get(j);
                    userModel.setId(base1.getId());
                    userModel.setFullName(base1.getFullName());
                    userModel.setImage(base1.getImage());
                    userModel.setId(base1.getId());
                    userModels.add(userModel);
                }
                offers1.setUsers(userModels);
            }
            offers.add(offers1);

        }
        offer.setOffers(offers);
        return offer;
    }

    private void loadOffers(int depId) {
        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.HOME_URL);

        RetrofitMethods getOffers = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<Offer> call;

        if (depId != -1)
            call = getOffers.getOffersByCatId("1",depId, API.URL_TOKEN);//TODO: get user ID
        else call = getOffers.getAllOffers("1",API.URL_TOKEN);//TODO: get user ID

        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, retrofit2.Response<Offer> response) {
                Offer serverResponse = response.body();
                if (serverResponse != null) {
                    cl_emptyView.setVisibility(View.GONE);
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
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.HOME_URL);

        Gson gson = new Gson();
        RetrofitMethods getOffers = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<Offer> call = getOffers.filterSearchOffer(gson.toJson(filterModel), API.URL_TOKEN);

        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, retrofit2.Response<Offer> response) {
                Offer serverResponse = response.body();
                if (serverResponse != null) {
                    cl_emptyView.setVisibility(View.GONE);
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
                        fillOffers(serverResponse, MINE);
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