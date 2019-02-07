package teamup.rivile.com.teamup.Project.Details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.badoualy.stepperindicator.StepperIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.Project.Add.Adapters.ChipsAdapter;
import teamup.rivile.com.teamup.Project.Add.Adapters.LoadedChipsAdapter;
import teamup.rivile.com.teamup.Project.List.AdapterListOffers;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.ExperienceTypeModel;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;

public class FragmentOfferDetails extends Fragment {
    View view;
    RelativeLayout money, contributors;
    LinearLayout moneySection, contributorsSection;
    RelativeLayout place, experience;
    LinearLayout placeSection, experienceSection;
    RelativeLayout attachment, cap, dep, tags;
    LinearLayout attachmentSection, CapSection, DepSection, tagSection;

    int m, c, p, e, a, ca, d, t;/** متغير ثابت عشان اغير حاله ال shrink وال expand*/
    /**
     * 1: Expand, 0:Shrink
     */

    TextView project_name, user_name;
    TextView proDetail/*, moneyDesc*/;
    CheckBox moneyProfitType, genderRequired, placeState, placeType;
    StepperIndicator educationLevel;

    FloatingActionButton arrowContributors, arrowMoney, arrowTag, arrowDep, arrowAttach, arrowEx, arrowPlace;
    TextView moneyOutFrom, moneyOutTo, moneyInFrom, moneyInTo, conFrom, conTo;


    TextView placeDesc, placeAddress, exDesc, exDep, depName, experienceFrom, experienceTo, tagsList;

    View map;

    ChipsAdapter mExRecUserAdapter;
    LoadedChipsAdapter mExRecLoadedAdapter;
    ArrayList<ExperienceTypeModel> mLoadedExperienceTypes = new ArrayList<>();

    static int projectId;

    static FragmentOfferDetails setProjectId(int proId) {
        projectId = proId;
        return new FragmentOfferDetails();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_details, container, false);
        m = c = 1;
        p = e = 1;
        a = ca = d = t = 1;
        /** Shrink and Expand Views */
        money = view.findViewById(R.id.money);
        contributors = view.findViewById(R.id.contributors);
        moneySection = view.findViewById(R.id.moneySection);
        contributorsSection = view.findViewById(R.id.contributorsSection);
        place = view.findViewById(R.id.place);
        experience = view.findViewById(R.id.experience);
        placeSection = view.findViewById(R.id.placeSection);
        experienceSection = view.findViewById(R.id.experienceSection);
        attachment = view.findViewById(R.id.attachment);
        cap = view.findViewById(R.id.cap);
        dep = view.findViewById(R.id.dep);
        tags = view.findViewById(R.id.tags);
        attachmentSection = view.findViewById(R.id.attachmentSection);
        CapSection = view.findViewById(R.id.CapSection);
        DepSection = view.findViewById(R.id.DepSection);
        tagSection = view.findViewById(R.id.tagSection);
        /** Input Views */

        project_name = view.findViewById(R.id.project_name);
        user_name = view.findViewById(R.id.user_name);
        proDetail = view.findViewById(R.id.proDetail);
        educationLevel = view.findViewById(R.id.educationLevel);
        educationLevel.setCurrentStep(0);
        arrowMoney = view.findViewById(R.id.arrowMoney);
        arrowContributors = view.findViewById(R.id.arrowContributors);
        arrowTag = view.findViewById(R.id.arrowTag);
        arrowDep = view.findViewById(R.id.arrowDep);
        arrowAttach = view.findViewById(R.id.arrowAttach);
        arrowEx = view.findViewById(R.id.arrowEx);
        arrowPlace = view.findViewById(R.id.arrowPlace);
        moneyOutFrom = view.findViewById(R.id.moneyOutFrom);
        moneyOutTo = view.findViewById(R.id.moneyOutTo);
        moneyInFrom = view.findViewById(R.id.moneyInFrom);
        moneyInTo = view.findViewById(R.id.moneyInTo);
        conFrom = view.findViewById(R.id.conFrom);
        conTo = view.findViewById(R.id.conTo);
        moneyProfitType = view.findViewById(R.id.moneyProfitType);
        genderRequired = view.findViewById(R.id.genderRequired);
        placeState = view.findViewById(R.id.placeState);
        placeType = view.findViewById(R.id.placeType);
        placeDesc = view.findViewById(R.id.placeDesc);
        placeAddress = view.findViewById(R.id.placeAddress);
        exDesc = view.findViewById(R.id.exDesc);
        depName = view.findViewById(R.id.depName);
        experienceFrom = view.findViewById(R.id.experienceFrom);
        experienceTo = view.findViewById(R.id.experienceTo);
        tagsList = view.findViewById(R.id.tagsList);
        exDep = view.findViewById(R.id.exDep);
        map = view.findViewById(R.id.map);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m == 1) {
                    m = 0;
                    moneySection.setVisibility(View.GONE);
                    arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                } else {
                    m = 1;
                    moneySection.setVisibility(View.VISIBLE);
                    arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));

                }
            }
        });

        contributors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c == 1) {
                    c = 0;
                    contributorsSection.setVisibility(View.GONE);
                    arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                } else {
                    c = 1;
                    contributorsSection.setVisibility(View.VISIBLE);
                    arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }

            }
        });



        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p == 1) {
                    p = 0;
                    placeSection.setVisibility(View.GONE);
                } else {
                    p = 1;
                    placeSection.setVisibility(View.VISIBLE);
                }
            }
        });

        experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e == 1) {
                    e = 0;
                    experienceSection.setVisibility(View.GONE);
                } else {
                    e = 1;
                    experienceSection.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void loadOfferDetails(int Id) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.HOME_URL);

        ApiConfig getOffers = appConfig.getRetrofit().create(ApiConfig.class);
        Call<Offers> call;

        call = getOffers.offerDetails(Id, API.URL_TOKEN);

        call.enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, retrofit2.Response<Offers> response) {
                Offers serverResponse = response.body();
                if (serverResponse != null) {
                    fillOffers(serverResponse);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }

    @SuppressLint("ResourceType")
    private void fillOffers(Offers offers) {
        project_name.setText(offers.getName());
        proDetail.setText(offers.getDescription());
        if (offers.getProfitType() == 0){
            moneyProfitType.setText(getResources().getString(R.id.day));
        }else if (offers.getProfitType() == 1){
            moneyProfitType.setText(getResources().getString(R.id.month));
        }else if (offers.getProfitType() == 2){
            moneyProfitType.setText(getResources().getString(R.id.year));
        }else if (offers.getProfitType() == 3){
            moneyProfitType.setText(getResources().getString(R.id.other));
        }

        if (offers.getGenderContributor() == 0){
            moneyProfitType.setText(getResources().getString(R.id.male));
        }else if (offers.getProfitType() == 1){
            moneyProfitType.setText(getResources().getString(R.id.female));
        }else if (offers.getProfitType() == 2){
            moneyProfitType.setText(getResources().getString(R.id.both));
        }


        if (offers.getRequirments().get(0).isNeedPlaceType()){
            placeType.setText(getResources().getString(R.id.avail));
        }else if (offers.getProfitType() == 1){
            placeType.setText(getResources().getString(R.id.notAvail));
        }

        if (offers.getRequirments().get(0).isNeedPlaceStatus()){
            placeState.setText(getResources().getString(R.id.rent));
        }else if (offers.getProfitType() == 1){
            placeState.setText(getResources().getString(R.id.owned));
        }

        educationLevel.setCurrentStep(offers.getEducationContributorLevel());

//        user_name;
//
//        educationLevel;
    }
}
