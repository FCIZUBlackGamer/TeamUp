package teamup.rivile.com.teamup.ui.Project.Add;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Locale;

import teamup.rivile.com.teamup.network.repository.AppNetworkRepository;
import teamup.rivile.com.teamup.Uitls.AppModels.OfferDetails;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.network.APIModels.StateModel;
import teamup.rivile.com.teamup.network.APIModels.Offers;
import teamup.rivile.com.teamup.network.APIModels.TagsModel;

public class FragmentAddHome extends Fragment {
    private MutableLiveData<ArrayList<TagsModel>> mLoadedTagsLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<StateModel>> mLoadedCapitalLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<StateModel>> mLoadedCategoryLiveData = new MutableLiveData<>();

    private ImageView mIndicatorImageView;

    private static int mProjectId = -1;
    private static MutableLiveData<OfferDetails> mLoadedProjectWithAllDataLiveData = new MutableLiveData<>();

    private AppNetworkRepository mNetworkRepository;

    public static FragmentAddHome openForEdit(int projectId, FloatingActionButton view) {
        mProjectId = projectId;
        return new FragmentAddHome();
    }

    View view;
    //FragmentManager fragmentManager;
    ViewPager viewPager = null;
    FragmentPagerAdapter pagerAdapter;
    //FragmentTransaction d;

    private Offers offer = null;
//    private RequirmentModel requirmentModel = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_home, container, false);

        mNetworkRepository = AppNetworkRepository.getInstance(getActivity().getApplication());

        teamup.rivile.com.teamup.ui.Project.Add.StaticShit.Offers.reset();
        //fragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        viewPager = view.findViewById(R.id.offer_pager);
        mIndicatorImageView = view.findViewById(R.id.iv_indicator);
        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStart() {
        super.onStart();

        //d = fragmentManager.beginTransaction();
//        viewPager.setRotationY(180);

        pagerAdapter = new pager(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        mIndicatorImageView.setImageResource(R.drawable.ic_indicator_first);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) mIndicatorImageView.setImageResource(R.drawable.ic_indicator_first);
                else if (i == 1)
                    mIndicatorImageView.setImageResource(R.drawable.ic_indicator_second);
//                else if (i == 2)
//                    mIndicatorImageView.setImageResource(R.drawable.ic_indicator_third);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        if (Locale.getDefault().getLanguage().equals("ar")) {
            viewPager.setCurrentItem(1);
        } else if (Locale.getDefault().getLanguage().equals("en")) {
            viewPager.setCurrentItem(0);
        }
    }

    class pager extends FragmentPagerAdapter {

        public pager(FragmentManager fm) {
            super(fm);
            loadCapTagCat();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                if (Locale.getDefault().getLanguage().equals("ar")) {
                    fragment = FragmentOffer2.setPager(viewPager, pagerAdapter, mLoadedTagsLiveData, mLoadedCapitalLiveData, mLoadedCategoryLiveData, mLoadedProjectWithAllDataLiveData);
                } else if (Locale.getDefault().getLanguage().equals("en")) {
                    fragment = FragmentOffer1.setPager(viewPager, pagerAdapter, mLoadedProjectWithAllDataLiveData);
                }

                //d.commitNow();
            }
//            else if (position == 1) {
//                fragment = FragmentOffer2.setPager(viewPager, pagerAdapter, mExperienceTypesLiveData, mLoadedProjectWithAllDataLiveData);
//                //d.commitNow();
//            }
            else if (position == 1) {
                if (Locale.getDefault().getLanguage().equals("ar")) {
                    fragment = FragmentOffer1.setPager(viewPager, pagerAdapter, mLoadedProjectWithAllDataLiveData);
                } else if (Locale.getDefault().getLanguage().equals("en")) {
                    fragment = FragmentOffer2.setPager(viewPager, pagerAdapter, mLoadedTagsLiveData, mLoadedCapitalLiveData, mLoadedCategoryLiveData, mLoadedProjectWithAllDataLiveData);
                }

                //d.commitNow();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private void loadCapTagCat() {
        mNetworkRepository.getCapTagCat()
                .observe(this, response -> {
                    if (response != null) {
                        mLoadedCapitalLiveData.postValue((ArrayList<StateModel>) response.getCapital());
                        mLoadedCategoryLiveData.postValue((ArrayList<StateModel>) response.getCategory());
                        mLoadedTagsLiveData.postValue((ArrayList<TagsModel>) response.getTags());

                        if (mProjectId != -1)
                            loadAllProjectDataForEdit();

                    }
                });
    }

    private void loadAllProjectDataForEdit() {
        mNetworkRepository.offerDetails(mProjectId)
                .observe(this, response -> {
                    if (response != null) {
                        mLoadedProjectWithAllDataLiveData.postValue(response.getOffer());
                    }
                });
    }
}