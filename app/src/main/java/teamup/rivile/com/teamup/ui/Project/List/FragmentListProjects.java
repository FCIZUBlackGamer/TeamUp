package teamup.rivile.com.teamup.ui.Project.List;

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
import teamup.rivile.com.teamup.network.repository.AppNetworkRepository;
import teamup.rivile.com.teamup.network.APIModels.Offers;
import teamup.rivile.com.teamup.ui.DrawerActivity;
import teamup.rivile.com.teamup.ui.Project.ShareDialogFragment;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.network.APIModels.FilterModel;
import teamup.rivile.com.teamup.network.APIModels.Offer;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.FavouriteDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.LoginDataBase;

public class FragmentListProjects extends Fragment implements ShareDialogFragment.Helper, AdapterListOffers.Helper,
        AdapterView.OnItemSelectedListener {

    private ConstraintLayout mLoadingViewConstraintLayout;

    private String mProjectURL = "";
    private String mProjectName = "";
    public static int MINE = 1;
    public static int FAVOURITE = 2;
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
    List<FavouriteDataBase> favouriteDataBases;
    TabLayout tabLayout;
    static ConstraintLayout cl_emptyView;

    private AppNetworkRepository mNetworkRepository;

    private int mUserId = -1;

    /**
     * @param id refers to Department Id from Home Page
     */
    public static FragmentListProjects setDepId(int id) {
        DepId = id;
        ProType = NORMAL;
        Word = null;
        return new FragmentListProjects();
    }

    /**
     * @param type refers to favourite projects(2) or all projects(0)
     */
    public static FragmentListProjects setType(int type) {
        ProType = type;
        DepId = -1;
        return new FragmentListProjects();
    }

    /**
     * @param filter refers to my filtered projects from FilterSearchFragment
     */
    public static FragmentListProjects setFilteredOffers(FilterModel filter) {
        filterModel = filter;
        return new FragmentListProjects();
    }

    /**
     * @param word refers to my projects(1), favouriteImageView projects(2) or all projects(-1)
     * @param type {2: UserName, 1: ProjectName, 0: Tag}
     */
    public static FragmentListProjects setWord(int type, String word) {
        Word = word;//Todo: Make Action
        Type = type;
        DepId = -1;
        return new FragmentListProjects();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_projects, container, false);

        mLoadingViewConstraintLayout = view.findViewById(R.id.cl_loading);

        mNetworkRepository = AppNetworkRepository.getInstance(getActivity().getApplication());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = view.findViewById(R.id.rec);
        tabLayout = view.findViewById(R.id.tabs);
        cl_emptyView = view.findViewById(R.id.cl_emptyView);
        recyclerView.setLayoutManager(layoutManager);

        Spinner spinner = view.findViewById(R.id.s_sort_by);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_by_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).showSearchBar("ListProjects");

        mLoadingViewConstraintLayout.setVisibility(View.VISIBLE);

        tabLayout.getTabAt(1/*Available Projects*/).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Todo: action filter the list and load Spin
                mLoadingViewConstraintLayout.setVisibility(View.VISIBLE);
                int position = tab.getPosition();
                if (position == 0) {
                    Log.d("Status", getString(R.string.availableProjects));
                    loadJoinedOffer(DepId);
                } else if (position == 1) {
                    Log.d("Status", getString(R.string.hintProjects));
                    loadOffers(DepId);
                } else if (position == 2) {
                    Log.d("Status", getString(R.string.successProjects));
                    loadSuccessOffer(DepId);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        realm = Realm.getDefaultInstance();
        LoginDataBase loginData = realm.where(LoginDataBase.class)
                .findFirst();


        Log.d("TESERSDGSDFG", String.valueOf(loginData.getUser()));

        if (loginData != null) {
            mUserId = loginData.getUser().getId();

            Log.d("TESERSDGSDFG", String.valueOf(mUserId));

            likeModelDataBase = loginData.getLikes();
            favouriteDataBases = loginData.getFavorites();
            Log.e("UserId", loginData.getUser().getId() + "");

            if (DepId != -1) {
                Type = -1;
                Word = null;
            }

            int position = tabLayout.getSelectedTabPosition();
            if (position == 0) {
                Log.d("Status", getString(R.string.availableProjects));
                loadJoinedOffer(DepId);
            } else if (position == 1) {
                Log.d("Status", getString(R.string.hintProjects));
                loadOffers(DepId);
            } else if (position == 2) {
                Log.d("Status", getString(R.string.successProjects));
                loadSuccessOffer(DepId);
            }
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

    private void loadOffers(int depId) {
        if (depId != -1)
            mNetworkRepository.getOffersByCatId(String.valueOf(mUserId), depId)
                    .observe(this, serverResponse -> {
                        if (serverResponse != null) {
                            fillOffers(serverResponse, ProType);
                        } else {
                            showEmptyView();
                        }
                        mLoadingViewConstraintLayout.setVisibility(View.GONE);
                    });
        else mNetworkRepository.getAllOffers(String.valueOf(mUserId))
                .observe(this, serverResponse -> {
                    if (serverResponse != null) {
                        fillOffers(serverResponse, ProType);
                    } else {
                        showEmptyView();
                    }
                    mLoadingViewConstraintLayout.setVisibility(View.GONE);
                });
    }

    private void loadJoinedOffer(int depId) {
        if (depId != -1)
            mNetworkRepository.getJoinedOffer(String.valueOf(mUserId), depId)
                    .observe(this, serverResponse -> {
                        if (serverResponse != null) {

                            fillOffers(serverResponse, NORMAL);
                        } else {
                            showEmptyView();
                        }

                        mLoadingViewConstraintLayout.setVisibility(View.GONE);
                    });
        else mNetworkRepository.getJoinedOffer(String.valueOf(mUserId))
                .observe(this, serverResponse -> {
                    if (serverResponse != null) {

                        fillOffers(serverResponse, NORMAL);
                    } else {
                        showEmptyView();
                    }

                    mLoadingViewConstraintLayout.setVisibility(View.GONE);
                });
    }

    private void loadSuccessOffer(int depId) {
        if (depId != -1)
            mNetworkRepository.getSuccessOffer(String.valueOf(mUserId), depId)
                    .observe(this, serverResponse -> {
                        if (serverResponse != null) {
                            fillOffers(serverResponse, NORMAL);
                        } else {
                            showEmptyView();
                        }
                        mLoadingViewConstraintLayout.setVisibility(View.GONE);
                    });
        else mNetworkRepository.getSuccessOffer(String.valueOf(mUserId))
                .observe(this,serverResponse -> {
                    if (serverResponse != null) {
                        fillOffers(serverResponse, NORMAL);
                    } else {
                        showEmptyView();
                    }
                    mLoadingViewConstraintLayout.setVisibility(View.GONE);
                });
    }

    private void loadOffers(FilterModel filterModel) {
        Gson gson = new Gson();
        mNetworkRepository.filterSearchOffer(gson.toJson(filterModel))
                .observe(this, serverResponse -> {
                    if (serverResponse != null) {
                        fillOffers(serverResponse, NORMAL);
                    } else {
                        showEmptyView();
                    }
                    mLoadingViewConstraintLayout.setVisibility(View.GONE);
                });
    }

    private void loadOffers(int type, String word) {
        mNetworkRepository.searchOffer(type, word)
        .observe(this, serverResponse -> {
            if (serverResponse != null) {
                if (serverResponse.getOffers().size() > 0) {
                    hideEmptyView();
                    fillOffers(serverResponse, NORMAL);
                }
            } else {
                showEmptyView();
            }
            mLoadingViewConstraintLayout.setVisibility(View.GONE);
        });
    }

    private void fillOffers(Offer offer, int type) {
        if (offer.getOffers() != null) {
            if (type == MINE) {
                ArrayList<Offers> offers = new ArrayList<>();
                for (Offers o : offer.getOffers()) {
                    if (o.getUserId().equals(mUserId)) offers.add(o);
                }
                offer.setOffers(offers);
            }

            if (offer.getOffers().size() == 0) showEmptyView();
            else hideEmptyView();

            adapter = new AdapterListOffers(getActivity().getApplication(),
                    offer.getOffers(),
                    likeModelDataBase,
                    favouriteDataBases,
                    type,
                    this, this, getContext());

            recyclerView.setAdapter(adapter);
        } else showEmptyView();
    }

    public static void showEmptyView() {
        cl_emptyView.setVisibility(View.VISIBLE);
    }

    public static void hideEmptyView() {
        cl_emptyView.setVisibility(View.GONE);
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