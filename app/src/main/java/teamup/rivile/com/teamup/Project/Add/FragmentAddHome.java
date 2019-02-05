package teamup.rivile.com.teamup.Project.Add;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.ExperienceTypeModel;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel;

public class FragmentAddHome extends Fragment {

    private static ArrayList<ExperienceTypeModel> mTagsArrayList = new ArrayList<>();
    private static ArrayList<ExperienceTypeModel> mExperienceTypesArrayList = new ArrayList<>();

    static FloatingActionButton fab;

    public static FragmentAddHome setFab(FloatingActionButton view) {
        fab = view;
        return new FragmentAddHome();
    }

    View view;
    //FragmentManager fragmentManager;
    ViewPager viewPager = null;
    FragmentPagerAdapter pagerAdapter;
    //FragmentTransaction d;

    private static Offers offer = null;
    private static RequirmentModel requirmentModel = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_home, container, false);
        //fragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        viewPager = (ViewPager) view.findViewById(R.id.offer_pager);
        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStart() {
        super.onStart();

        //d = fragmentManager.beginTransaction();

        fab.setVisibility(View.GONE);
        pagerAdapter = new pager(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);


    }

    class pager extends FragmentPagerAdapter {

        public pager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentOffer1().setPager(viewPager, pagerAdapter, offer, requirmentModel);
                //d.commitNow();
            } else if (position == 1) {
                fragment = new FragmentOffer2().setPager(viewPager, pagerAdapter, offer, requirmentModel, mExperienceTypesArrayList);
                //d.commitNow();
            } else if (position == 2) {
                fragment = new FragmentOffer3().setPager(viewPager, pagerAdapter, offer, requirmentModel, mTagsArrayList);
                //d.commitNow();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private void loadAllDepartments() {
        Retrofit retrofit = new AppConfig(API.BASE_URL).getRetrofit();

        ApiConfig retrofitService = retrofit.create(ApiConfig.class);

        Call<String> response = retrofitService.getAllDepratments();

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.errorBody() == null) {
                    if (response.body() != null) {
                        try {
                            parseRetrofitResponse(response.body());
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void parseRetrofitResponse(String response) throws JSONException {
        JSONObject responseObject = new JSONObject(response);
        Gson gson = new Gson();

        JSONArray tagsJsonArray = responseObject.getJSONArray("Tags");
        for(int i = tagsJsonArray.length()-1; i >= 0; --i){
            JSONObject tagJsonObject = tagsJsonArray.getJSONObject(i);

            mTagsArrayList.add(gson.fromJson(tagJsonObject.toString(),ExperienceTypeModel.class));
        }

        JSONArray experienceTypeArray = responseObject.getJSONArray("ExperienceType");
        for(int i = tagsJsonArray.length()-1; i >= 0; --i){
            JSONObject tagJsonObject = experienceTypeArray.getJSONObject(i);

            mExperienceTypesArrayList.add(gson.fromJson(tagJsonObject.toString(),ExperienceTypeModel.class));
        }
    }
}