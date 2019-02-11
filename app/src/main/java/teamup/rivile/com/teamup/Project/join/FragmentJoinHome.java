package teamup.rivile.com.teamup.Project.join;

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
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;

public class FragmentJoinHome extends Fragment {

    private MutableLiveData<OfferDetailsJsonObject> mOffer = new MutableLiveData<>();

    static FloatingActionButton fab;

    public static FragmentJoinHome setFab(FloatingActionButton view) {
        fab = view;
        return new FragmentJoinHome();
    }

    private static int mOfferId;

    View view;
    //FragmentManager fragmentManager;
    ViewPager viewPager = null;
    FragmentPagerAdapter pagerAdapter;
    //FragmentTransaction d;


    public static FragmentJoinHome setOfferId(int offerId) {
        mOfferId = offerId;

        return new FragmentJoinHome();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_home, container, false);
        //fragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        viewPager = view.findViewById(R.id.offer_pager);
        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStart() {
        super.onStart();

        //d = fragmentManager.beginTransa//todo bugction();

//        fab.setVisibility(View.GONE); //todo bug
        pagerAdapter = new pager(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    class pager extends FragmentPagerAdapter {

        public pager(FragmentManager fm) {
            super(fm);
            loadOffer();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = FragmentOffer1.setPager(viewPager, pagerAdapter, mOffer);
                //d.commitNow();
            } else if (position == 1) {
                fragment = FragmentOffer2.setPager(viewPager, pagerAdapter, mOffer);
                //d.commitNow();
            } else if (position == 2) {
                fragment = FragmentOffer3.setPager(viewPager, pagerAdapter, mOffer);
                //d.commitNow();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private void loadOffer() {
        Retrofit retrofit = new AppConfig(API.BASE_URL).getRetrofit();

        ApiConfig retrofitService = retrofit.create(ApiConfig.class);

        Call<OfferDetailsJsonObject> response = retrofitService.offerDetails(mOfferId, API.URL_TOKEN);

        response.enqueue(new Callback<OfferDetailsJsonObject>() {
            @Override
            public void onResponse(@NonNull Call<OfferDetailsJsonObject> call, @NonNull Response<OfferDetailsJsonObject> response) {
                if (response.errorBody() == null) {
                    if (response.body() != null) {
                        mOffer.postValue(response.body());
                    } else
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Call<OfferDetailsJsonObject> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}