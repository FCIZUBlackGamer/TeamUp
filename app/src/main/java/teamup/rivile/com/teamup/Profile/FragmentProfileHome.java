package teamup.rivile.com.teamup.Profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDetailsDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDetailsRequirmentDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.UserDataBase;

public class FragmentProfileHome extends Fragment {

    FloatingActionButton fab_edit;
    ImageView cir_user_image;
    TextView txt_name, txt_job_title, txt_location, txt_bio, txt_dateOfBirth, txt_email, txt_phone, txt_num_projects;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Offers> offersList;
    View view;
    FragmentManager fragmentManager;
    //    ViewPager viewPager = null;
    static int Id = -1;
    CollapsingToolbarLayout ctl ;
    Realm realm;

    public static FragmentProfileHome setId(int id) {
        Id = id; //TODO
//        Id = 1;
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
        ctl = view.findViewById(R.id.collapsing_toolbar);

        recyclerView.setLayoutManager(layoutManager);
        offersList = new ArrayList<>();
        fragmentManager = getFragmentManager();
        realm = Realm.getDefaultInstance();
//        viewPager = (ViewPager) view.findViewById(R.id.pager);
        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).Hide();
        ctl.setCollapsedTitleTextAppearance(R.style.coll_toolbar_title);
        ctl.setExpandedTitleTextAppearance(R.style.exp_toolbar_title);
        realm.executeTransaction(realm1 -> {
            LoginDataBase loginDataBases = realm1.where(LoginDataBase.class)
                    .findFirst();
            if (Id != loginDataBases.getUser().getId()) {
                loadProfile(Id);
                fab_edit.setVisibility(View.GONE);
            }else {
                fab_edit.setVisibility(View.VISIBLE);
                loadProfileFromDB(loginDataBases);
            }
        });


//        viewPager.setAdapter(new pager(fragmentManager));
        adapter = new AdapterProfileProject(getActivity(), offersList);
        recyclerView.setAdapter(adapter);
    }

    private void loadProfileFromDB(LoginDataBase loginDataBases) {
        UserDataBase userDataBase = loginDataBases.getUser();
        List<OfferDetailsDataBase> offerDetailsDataBase = loginDataBases.getOffers();
        UserModel profObject = loadUser(userDataBase);
        List<Offers> offers = loadOffers(offerDetailsDataBase);
        fillProfData(profObject);
        fillProfOffersData(offers);
    }

    private List<Offers> loadOffers(List<OfferDetailsDataBase> offerDetailsDataBase) {
        List<Offers> offers =  new ArrayList<>();
        for (int i = 0; i < offerDetailsDataBase.size(); i++) {
            Offers offers1 = new Offers();
            OfferDetailsDataBase base = offerDetailsDataBase.get(i);
            offers1.setUserId(base.getUserId());
            offers1.setId(base.getId());
//            offers1.setCategoryId(base.getCategoryId());
//            offers1.setCategoryName(base.getCategoryName());
            offers1.setDescription(base.getDescription());
            offers1.setName(base.getName());
            offers1.setAddress(base.getAddress());
//            offers1.setAgeRequiredFrom(base.getAgeRequiredFrom());
//            offers1.setAgeRequiredTo(base.getAgeRequiredTo());
            offers1.setDate(base.getDate());
//            offers1.setEducationContributorLevel(base.getEducationContributorLevel());
            offers1.setNumContributorFrom(base.getNumContributorFrom());
            offers1.setNumContributorTo(base.getNumContributorTo());
            offers1.setGenderContributor(base.getGenderContributor());
            offers1.setNumJoinOffer(base.getNumJoinOffer());
            offers1.setNumLiks(base.getNumLiks());
//            offers1.setProfitFrom(base.getProfitFrom());
//            offers1.setProfitTo(base.getProfitTo());
//            offers1.setProfitType(base.getProfitType());
            offers1.setStatus(base.getStatus());
            List<UserModel> userModels = new ArrayList<>();
            for (int j = 0; j < offers1.getUsers().size(); j++) {
                UserModel userModel = new UserModel();
                UserDataBase base1 = base.getUsers().get(j);
                userModel.setId(base1.getId());
                userModel.setFullName(base1.getFullName());
                userModel.setImage(base1.getImage());
                userModel.setId(base1.getId());
                userModels.add(userModel);
            }
            offers1.setUsers(userModels);
            offers.add(offers1);
//            List<RequirmentModel> rec = new ArrayList<>();
//            for (int j = 0; j < base.getRequirments().size(); j++) {
//                RequirmentModel requirmentModel = new RequirmentModel();
//                OfferDetailsRequirmentDataBase re = base.getRequirments().get(j);
//                requirmentModel.setExperienceDescriptions(re.getExperienceDescriptions());
//                requirmentModel.setNeedExperience(re.isNeedExperience());
//                requirmentModel.setNeedPlaceType(re.isNeedPlaceType());
//                requirmentModel.setNeedPlace(re.isNeedPlace());
//                requirmentModel.setNeedMoney(re.isNeedMoney());
//                requirmentModel.setPlaceAddress(re.getPlaceAddress());
//                requirmentModel.setNeedPlaceStatus(re.isNeedPlaceStatus());
//            }
        }
        return offers;
    }

    private UserModel loadUser(UserDataBase userDataBase) {
        UserModel model = new UserModel();
        model.setSocialId(userDataBase.getSocialId());
        model.setPassword(userDataBase.getPassword());
        model.setMail(userDataBase.getMail());
        model.setId(userDataBase.getId());
        model.setImage(userDataBase.getImage());
        model.setFullName(userDataBase.getFullName());
        model.setGender(userDataBase.getGender());
        model.setDateOfBirth(userDataBase.getDateOfBirth());
        model.setAddress(userDataBase.getAddress());
        model.setBio(userDataBase.getBio());
        model.setCapitalId(userDataBase.getCapitalId());
        model.setCode(userDataBase.getCode());
        model.setIdentityImage(userDataBase.getIdentityImage());
        model.setIdentityNum(userDataBase.getIdentityNum());
        model.setCoded(userDataBase.getCoded());
        model.setJobtitle(userDataBase.getJobtitle());
        model.setPhone(userDataBase.getPhone());
        model.setStatus(userDataBase.getStatus());
        return model;
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
        offersList = new ArrayList<>();
        offersList.addAll(offers);
        adapter.notifyDataSetChanged();

    }

    private void fillProfData(UserModel user) {
        ctl.setTitle(user.getFullName());
        txt_name.setText(user.getFullName());
        txt_job_title.setText(user.getJobtitle());
        txt_location.setText(user.getAddress());
        txt_bio.setText(user.getBio());
        txt_dateOfBirth.setText(user.getDateOfBirth());
        txt_email.setText(user.getMail());
        txt_phone.setText(user.getPhone());
        txt_num_projects.setText(String.valueOf(user.getNumProject()));
        if (user.getImage() != null && !user.getImage().isEmpty() && user.getImage() != null) {
            Picasso.get().load(user.getImage()).into(cir_user_image);
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
