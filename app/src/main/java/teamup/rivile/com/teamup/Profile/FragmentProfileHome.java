package teamup.rivile.com.teamup.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

public class FragmentProfileHome extends Fragment {

    FloatingActionButton fab_edit;
    CircleImageView cir_user_image;
    TextView txt_name, txt_job_title, txt_location, txt_bio, txt_dateOfBirth, txt_email, txt_phone, txt_num_projects;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Offers> offersList;
    View view;
    FragmentManager fragmentManager;
    //    ViewPager viewPager = null;
    static int Id = 1;

    public static FragmentProfileHome setId(int id) {
//        Id = id; TODO
        Id = 1;
        return new FragmentProfileHome();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = view.findViewById(R.id.rec);
        txt_name = view.findViewById(R.id.name);
        txt_job_title = view.findViewById(R.id.job_title);
        txt_location = view.findViewById(R.id.location);
        txt_bio = view.findViewById(R.id.bio);
        txt_dateOfBirth = view.findViewById(R.id.dateOfBirth);
        txt_email = view.findViewById(R.id.email);
        txt_phone = view.findViewById(R.id.phone);
        fab_edit = view.findViewById(R.id.edit);
        cir_user_image = view.findViewById(R.id.user_image);
        txt_num_projects = view.findViewById(R.id.num_projects);

        recyclerView.setLayoutManager(layoutManager);
        offersList = new ArrayList<>();
        fragmentManager = getFragmentManager();
//        viewPager = (ViewPager) view.findViewById(R.id.pager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).Hide();

        if (Id != 0) {
            loadProfile(Id);
        }

//        viewPager.setAdapter(new pager(fragmentManager));
        adapter = new AdapterProfileProject(getActivity(), offersList);
        recyclerView.setAdapter(adapter);
    }

    private void loadProfile(int id) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);

        final ApiConfig profile = appConfig.getRetrofit().create(ApiConfig.class);
        Call<ProfileResponse> call = profile.getProfile(id, API.URL_TOKEN);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull retrofit2.Response<ProfileResponse> response) {
                ProfileResponse allData = response.body();
                UserModel profObject = allData.getUserDetails();
                List<Offers> offers = allData.getListOffer();
                fillProfData(profObject);
                fillProfOffersData(offers);
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                Log.d("loadProfile", t.getMessage());
            }
        });
    }

    private void fillProfOffersData(List<Offers> offers) {
        txt_num_projects.setText(String.valueOf(offers.size()));
        offersList.addAll(offers);
        adapter.notifyDataSetChanged();

    }

    private void fillProfData(UserModel profObject) {
        txt_name.setText(profObject.getFullName());
        txt_job_title.setText(profObject.getJobtitle());
        txt_location.setText(profObject.getAddress());
        txt_bio.setText(profObject.getBio());
        txt_dateOfBirth.setText(profObject.getDateOfBirth());
        txt_email.setText(profObject.getMail());
        txt_phone.setText(profObject.getPhone());
        txt_num_projects.setText(String.valueOf(profObject.getNumProject()));
        if (profObject.getImage() != null && !profObject.getImage().isEmpty() && profObject.getImage() != null) {
            Picasso.get().load(profObject.getImage()).into(cir_user_image);
        }

        fab_edit.setOnClickListener(v -> {

        });
    }

    class pager extends FragmentPagerAdapter {

        public pager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentSwipe1();
            } else if (position == 1) {
                fragment = new FragmentSwipe2();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
