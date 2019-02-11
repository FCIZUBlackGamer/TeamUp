package teamup.rivile.com.teamup.Project.join;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;

import de.hdodenhof.circleimageview.CircleImageView;
import teamup.rivile.com.teamup.Project.Details.OfferDetails;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;

public class FragmentOffer1 extends Fragment {
    View view;
    RelativeLayout money, contributors;
    LinearLayout moneySection, contributorsSection;

    TextView project_name;
    RadioButton profitTypeRadioButton, availMoneyRadioButton, genderRadioButton;
    StepperIndicator educationLevel;

    FloatingActionButton arrowContributors, arrowMoney;
    EditText moneyOutFrom, moneyOutTo, moneyInFrom, moneyInTo, conFrom, conTo;

    CircleImageView userImage;
    private static MutableLiveData<OfferDetailsJsonObject> mOffer;

    private final int minMoneyOut = 0,
            maxMoneyOut = 100000,
            minMoneyIn = 0,
            maxMoneyIn = 100000,
            minContributor = 0,
            maxContributor = 15;

    static ViewPager pager;
    static FragmentPagerAdapter pagerAdapter;


    static FragmentOffer1 setPager(ViewPager viewPager, FragmentPagerAdapter pagerAdapte,
                                   MutableLiveData<OfferDetailsJsonObject> offer) {
        pager = viewPager;
        pagerAdapter = pagerAdapte;
        mOffer = offer;
        return new FragmentOffer1();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1_join_project, container, false);
        /** Shrink and Expand Views */
        money = view.findViewById(R.id.money);
        contributors = view.findViewById(R.id.contributors);
        moneySection = view.findViewById(R.id.moneySection);
        contributorsSection = view.findViewById(R.id.contributorsSection);
        arrowMoney = view.findViewById(R.id.arrowMoney);
        arrowContributors = view.findViewById(R.id.arrowContributors);
        /** Input Views */

        project_name = view.findViewById(R.id.project_name);

        genderRadioButton = view.findViewById(R.id.rb_gender);
        profitTypeRadioButton = view.findViewById(R.id.rb_profit_type);
        availMoneyRadioButton = view.findViewById(R.id.rb_availMoney);

        educationLevel = view.findViewById(R.id.educationLevel);

        conFrom = view.findViewById(R.id.conFrom);
        conTo = view.findViewById(R.id.conTo);

        userImage = view.findViewById(R.id.user_image);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mOffer.observe(this, offerDetailsJsonObject -> {
            if (offerDetailsJsonObject != null) {
                OfferDetails details = offerDetailsJsonObject.getOffers();
                project_name.setText(details.getName());

                String profitType = null;
                if (details.getProfitType() == 0) profitType = getString(R.string.day);
                else if (details.getProfitType() == 1) profitType = getString(R.string.month);
                else if (details.getProfitType() == 2) profitType = getString(R.string.year);
                else if (details.getProfitType() == 3) profitType = getString(R.string.anotherKind);
                profitTypeRadioButton.setText(profitType);

                if (details.getRequirments() != null && !details.getRequirments().isEmpty()) {
                    String availMoney = details.getRequirments().get(0).isNeedMoney() ?
                            getString(R.string.yes) : getString(R.string.no);
                    availMoneyRadioButton.setText(availMoney);
                }

                String gender = null;
                if (details.getGenderContributor() == 0) gender = getString(R.string.male);
                else if (details.getGenderContributor() == 1) gender = getString(R.string.female);
                else if (details.getGenderContributor() == 2) gender = getString(R.string.both);
                genderRadioButton.setText(gender);

                conFrom.setText(String.valueOf(details.getNumContributorFrom()));
                conTo.setText(String.valueOf(details.getNumContributorTo()));

                educationLevel.setCurrentStep(details.getEducationContributorLevel());
            } else {
                Toast.makeText(getContext(), "Error Loading Offer Details.", Toast.LENGTH_LONG).show();
            }
        });

        //region Shrink And Expand

        money.setOnClickListener(v -> {
            if (moneySection.getVisibility() == View.VISIBLE) {
                moneySection.setVisibility(View.GONE);
                arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                moneySection.setVisibility(View.VISIBLE);
                arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
            }
        });

        contributors.setOnClickListener(v -> {
            if (contributorsSection.getVisibility() == View.VISIBLE) {
                contributorsSection.setVisibility(View.GONE);
                arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                contributorsSection.setVisibility(View.VISIBLE);
                arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
            }
        });

        //endregion
    }
}
