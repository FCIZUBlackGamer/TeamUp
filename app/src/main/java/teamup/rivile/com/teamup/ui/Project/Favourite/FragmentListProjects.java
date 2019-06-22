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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.FavouriteDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.LoginDataBase;
import teamup.rivile.com.teamup.network.APIModels.FilterModel;
import teamup.rivile.com.teamup.network.APIModels.Offer;
import teamup.rivile.com.teamup.network.repository.AppNetworkRepository;
import teamup.rivile.com.teamup.ui.DrawerActivity;
import teamup.rivile.com.teamup.ui.Project.List.AdapterListOffers;
import teamup.rivile.com.teamup.ui.Project.ShareDialogFragment;

public class FragmentListProjects extends Fragment implements ShareDialogFragment.Helper, AdapterListOffers.Helper, AdapterView.OnItemSelectedListener {
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

    private AppNetworkRepository mNetworkRepository;

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

        mNetworkRepository = AppNetworkRepository.getInstance(getActivity().getApplication());

        Spinner spinner = view.findViewById(R.id.s_sort_by);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_by_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        view.findViewById(R.id.tabs).setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).showSearchBar("ListProjects");
        view.findViewById(R.id.tabs).setVisibility(View.GONE);


        likeModelDataBase = new ArrayList<>();
//        if (recyclerView != null ){
//            recyclerView.setAdapter(null);
//        }
        realm = Realm.getDefaultInstance();
        //realm.beginTransaction();
        LoginDataBase loginData = realm.where(LoginDataBase.class)
                .findFirst();

        if (loginData != null) {
            likeModelDataBase = loginData.getLikes();
            favouriteDataBases = loginData.getFavorites();
            Log.e("UserId", loginData.getUser().getId() + "");
            Log.e("Type", FAVOURITE + "");


            ((DrawerActivity) getActivity()).setTitle(getString(R.string.favourite));

            RealmResults<LoginDataBase> loginDataBases = realm.where(LoginDataBase.class)
                    .findAll();

            LoginDataBase firstLoginDataBase = loginDataBases.get(0);
            RealmList<FavouriteDataBase> favouriteDataBases = null;
            if (firstLoginDataBase != null) {
                favouriteDataBases = firstLoginDataBase.getFavorites();
            }
            if (favouriteDataBases != null && favouriteDataBases.size() > 0) {
                cl_emptyView.setVisibility(View.GONE);

                List<Integer> offerIds = new ArrayList<>();
                for (int i = 0; i < favouriteDataBases.size(); i++) {
                    FavouriteDataBase favouriteDataBase = favouriteDataBases.get(i);
                    if (favouriteDataBase != null) {
                        offerIds.add(favouriteDataBase.getOfferId());
                    }
                }

                loadOffers(offerIds);
            } else {
                cl_emptyView.setVisibility(View.VISIBLE);
                ((DrawerActivity) getActivity()).hideSearchBar();
            }
        }
    }

    private void loadOffers(List<Integer> favIds) {
        mNetworkRepository.getFavourite(new Gson().toJson(favIds))
                .observe(this, serverResponse -> {
                    if (serverResponse != null) {
                        cl_emptyView.setVisibility(View.GONE);
                        if (serverResponse.getOffers().size() > 0) {
                            fillOffers(serverResponse, FAVOURITE);
                        } else {
                            ((DrawerActivity) getActivity()).hideSearchBar();
                            cl_emptyView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ((DrawerActivity) getActivity()).hideSearchBar();
                        cl_emptyView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void fillOffers(Offer offers, int type) {
//        if (likeModelDataBase != null) {
        Log.e("A Size", offers.getOffers().size() + "");
        if (offers.getOffers() != null && !offers.getOffers().isEmpty() && offers.getOffers().size() > 0) {
            cl_emptyView.setVisibility(View.GONE);
            adapter = new AdapterListOffers(getActivity().getApplication(),
                    offers.getOffers(),
                    likeModelDataBase,
                    favouriteDataBases,
                    type, this,
                    this, getContext());

            recyclerView.setAdapter(adapter);
        } else {
            cl_emptyView.setVisibility(View.VISIBLE);
        }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (adapter != null) ((AdapterListOffers) adapter).sort(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (adapter != null) ((AdapterListOffers) adapter).sort(0);
    }
}