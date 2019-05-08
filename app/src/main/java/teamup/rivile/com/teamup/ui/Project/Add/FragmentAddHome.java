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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.ui.Project.Details.OfferDetails;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.CapTagCat;
import teamup.rivile.com.teamup.Uitls.APIModels.StateModel;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.APIModels.TagsModel;

public class FragmentAddHome extends Fragment {
    private MutableLiveData<ArrayList<TagsModel>> mLoadedTagsLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<StateModel>> mLoadedCapitalLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<StateModel>> mLoadedCategoryLiveData = new MutableLiveData<>();

    private ImageView mIndicatorImageView;

    private static int mProjectId = -1;
    private static MutableLiveData<OfferDetails> mLoadedProjectWithAllDataLiveData = new MutableLiveData<>();

    static FloatingActionButton fab;

    public static FragmentAddHome setFab(FloatingActionButton view) {
        fab = view;
        return new FragmentAddHome();
    }

    public static FragmentAddHome openForEdit(int projectId, FloatingActionButton view) {
        mProjectId = projectId;

        if (view != null)
            return setFab(view);
        else
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

        if (fab != null)
            fab.setVisibility(View.GONE);
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
        }
        else if (Locale.getDefault().getLanguage().equals("en")) {
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
                }
                else if (Locale.getDefault().getLanguage().equals("en")) {
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
                }
                else if (Locale.getDefault().getLanguage().equals("en")) {
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
        Retrofit retrofit = new RetrofitConfigurations(API.BASE_URL).getRetrofit();

        RetrofitMethods retrofitService = retrofit.create(RetrofitMethods.class);

        Call<CapTagCat> response = retrofitService.getCapTagCat(API.URL_TOKEN);

        response.enqueue(new Callback<CapTagCat>() {
            @Override
            public void onResponse(@NonNull Call<CapTagCat> call, @NonNull Response<CapTagCat> response) {
                if (response.errorBody() == null) {
                    if (response.body() != null) {
                        mLoadedCapitalLiveData.postValue((ArrayList<StateModel>) response.body().getCapital());
                        mLoadedCategoryLiveData.postValue((ArrayList<StateModel>) response.body().getCategory());
                        mLoadedTagsLiveData.postValue((ArrayList<TagsModel>) response.body().getTags());

                        if (mProjectId != -1)
                            loadAllProjectDataForEdit();

                    } else
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Call<CapTagCat> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadAllProjectDataForEdit() {
        Retrofit retrofit = new RetrofitConfigurations(API.BASE_URL).getRetrofit();

        RetrofitMethods retrofitService = retrofit.create(RetrofitMethods.class);

        Call<OfferDetailsJsonObject> response = retrofitService.offerDetails(mProjectId, API.URL_TOKEN);

        response.enqueue(new Callback<OfferDetailsJsonObject>() {
            @Override
            public void onResponse(Call<OfferDetailsJsonObject> call, Response<OfferDetailsJsonObject> response) {
                if (response.errorBody() == null) {
                    if (response.body() != null) {
                        mLoadedProjectWithAllDataLiveData.postValue(response.body().getOffer());
                    } else
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<OfferDetailsJsonObject> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

//    private void parseRetrofitResponse(String response) throws JSONException {
//        JSONObject responseObject = new JSONObject(response);
//        Gson gson = new Gson();
//
//        JSONArray tagsJsonArray = responseObject.getJSONArray("Tags");
//        for (int i = tagsJsonArray.length() - 1; i >= 0; --i) {
//            JSONObject tagJsonObject = tagsJsonArray.getJSONObject(i);
//
//            mLoadedTagsLiveData.add(gson.fromJson(tagJsonObject.toString(), ExperienceTypeModel.class));
//        }
//
//        JSONArray experienceTypeArray = responseObject.getJSONArray("ExperienceType");
//        for (int i = tagsJsonArray.length() - 1; i >= 0; --i) {
//            JSONObject tagJsonObject = experienceTypeArray.getJSONObject(i);
//
//            mExperienceTypesLiveData.add(gson.fromJson(tagJsonObject.toString(), ExperienceTypeModel.class));
//        }
//
//        JSONArray capitalArray = responseObject.getJSONArray("Capital");
//        for (int i = capitalArray.length() - 1; i >= 0; --i) {
//            JSONObject capitalsJsonObject = capitalArray.getJSONObject(i);
//
//            mLoadedCapitalLiveData.add(gson.fromJson(capitalsJsonObject.toString(), StateModel.class));
//        }
//
//        JSONArray categoryArray = responseObject.getJSONArray("Category");
//        for (int i = categoryArray.length() - 1; i >= 0; --i) {
//            JSONObject CategoriesJsonObject = categoryArray.getJSONObject(i);
//
//            mLoadedCategoryLiveData.add(gson.fromJson(CategoriesJsonObject.toString(), StateModel.class));
//        }
//    }
}