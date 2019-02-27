package teamup.rivile.com.teamup.Project.Add;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.Project.Details.OfferDetails;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.CapTagCat;
import teamup.rivile.com.teamup.Uitls.APIModels.CapitalModel;
import teamup.rivile.com.teamup.Uitls.APIModels.ExperienceTypeModel;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel;

public class FragmentAddHome extends Fragment {

    private MutableLiveData<ArrayList<ExperienceTypeModel>> mLoadedTagsLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ExperienceTypeModel>> mExperienceTypesLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<CapitalModel>> mLoadedCapitalLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<CapitalModel>> mLoadedCategoryLiveData = new MutableLiveData<>();

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

        return setFab(view);
    }

    View view;
    //FragmentManager fragmentManager;
    ViewPager viewPager = null;
    FragmentPagerAdapter pagerAdapter;
    //FragmentTransaction d;

    private Offers offer = null;
    private RequirmentModel requirmentModel = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_home, container, false);
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
                else if (i == 2)
                    mIndicatorImageView.setImageResource(R.drawable.ic_indicator_third);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
                fragment = FragmentOffer1.setPager(viewPager, pagerAdapter, mLoadedProjectWithAllDataLiveData);
                //d.commitNow();
            } else if (position == 1) {
                fragment = FragmentOffer2.setPager(viewPager, pagerAdapter, mExperienceTypesLiveData, mLoadedProjectWithAllDataLiveData);
                //d.commitNow();
            } else if (position == 2) {
                fragment = FragmentOffer3.setPager(viewPager, pagerAdapter, mLoadedTagsLiveData, mLoadedCapitalLiveData, mLoadedCategoryLiveData, mLoadedProjectWithAllDataLiveData);
                //d.commitNow();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private void loadCapTagCat() {
        Retrofit retrofit = new AppConfig(API.BASE_URL).getRetrofit();

        ApiConfig retrofitService = retrofit.create(ApiConfig.class);

        Call<CapTagCat> response = retrofitService.getCapTagCat(API.URL_TOKEN);

        response.enqueue(new Callback<CapTagCat>() {
            @Override
            public void onResponse(@NonNull Call<CapTagCat> call, @NonNull Response<CapTagCat> response) {
                if (response.errorBody() == null) {
                    if (response.body() != null) {
                        mLoadedCapitalLiveData.postValue((ArrayList<CapitalModel>) response.body().getCapital());
                        mLoadedCategoryLiveData.postValue((ArrayList<CapitalModel>) response.body().getCategory());
                        mLoadedTagsLiveData.postValue((ArrayList<ExperienceTypeModel>) response.body().getTags());
                        mExperienceTypesLiveData.postValue((ArrayList<ExperienceTypeModel>) response.body().getExperienceType());
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
        Retrofit retrofit = new AppConfig(API.BASE_URL).getRetrofit();

        ApiConfig retrofitService = retrofit.create(ApiConfig.class);

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
//            mLoadedCapitalLiveData.add(gson.fromJson(capitalsJsonObject.toString(), CapitalModel.class));
//        }
//
//        JSONArray categoryArray = responseObject.getJSONArray("Category");
//        for (int i = categoryArray.length() - 1; i >= 0; --i) {
//            JSONObject CategoriesJsonObject = categoryArray.getJSONObject(i);
//
//            mLoadedCategoryLiveData.add(gson.fromJson(CategoriesJsonObject.toString(), CapitalModel.class));
//        }
//    }
}