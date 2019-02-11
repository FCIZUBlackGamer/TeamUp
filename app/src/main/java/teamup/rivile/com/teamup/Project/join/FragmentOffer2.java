package teamup.rivile.com.teamup.Project.join;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import teamup.rivile.com.teamup.Project.Details.OfferDetails;
import teamup.rivile.com.teamup.Project.Details.OfferDetailsRequirment;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;

public class FragmentOffer2 extends Fragment {
    View view;
    RelativeLayout place, experience;
    LinearLayout placeSection, experienceSection;

    RadioButton placeRadioButton, placeKindRadioButton, placeStateRadioButton, exRadioButton;
    EditText experienceFrom, experienceTo;
    RecyclerView exRec;
    FloatingActionButton arrowPlace, arrowExperience;

    View map;

    private static MutableLiveData<OfferDetailsJsonObject> mOffer;

    static ViewPager pager;
    static FragmentPagerAdapter pagerAdapter;


    static FragmentOffer2 setPager(ViewPager viewPager, FragmentPagerAdapter pagerAdapte, MutableLiveData<OfferDetailsJsonObject> offer) {
        pager = viewPager;
        pagerAdapter = pagerAdapte;
        mOffer = offer;
        return new FragmentOffer2();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2_join_project, container, false);
        /** Shrink and Expand Views */
        place = view.findViewById(R.id.place);
        experience = view.findViewById(R.id.experience);
        placeSection = view.findViewById(R.id.placeSection);
        experienceSection = view.findViewById(R.id.experienceSection);
        arrowPlace = view.findViewById(R.id.arrowPlace);
        arrowExperience = view.findViewById(R.id.arrowExperiance);
        /** Input Views */

        placeRadioButton = view.findViewById(R.id.rb_place);

        placeKindRadioButton = view.findViewById(R.id.rb_placeKind);

        placeStateRadioButton = view.findViewById(R.id.rb_placeState);

        exRadioButton = view.findViewById(R.id.rb_ex);

        experienceFrom = view.findViewById(R.id.experienceFrom);
        experienceTo = view.findViewById(R.id.experienceTo);

        exRec = view.findViewById(R.id.exRec);
        map = view.findViewById(R.id.map);

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {
        super.onStart();

        mOffer.observe(this, offerDetailsJsonObject -> {
            if (offerDetailsJsonObject != null) {
                OfferDetails details = offerDetailsJsonObject.getOffers();
                OfferDetailsRequirment requirementModel = details.getRequirments().get(0);
                if (requirementModel != null) {
                    placeRadioButton.setText(requirementModel.isNeedPlace() ?
                            getString(R.string.neddplace) : getString(R.string.no));

                    if (!requirementModel.isNeedPlace()) {
                        placeSection.setVisibility(View.GONE);
                    }

                    placeStateRadioButton.setText(requirementModel.isNeedPlaceStatus() ?
                            getString(R.string.avail) : getString(R.string.notAvail));

                    placeKindRadioButton.setText(requirementModel.isNeedPlaceType() ?
                            getString(R.string.owned) : getString(R.string.rent));

                    exRadioButton.setText(requirementModel.isNeedExperience() ?
                            getString(R.string.yes) : getString(R.string.no));

                    experienceFrom.setText(String.valueOf(requirementModel.getExperienceFrom()));
                    experienceTo.setText(String.valueOf(requirementModel.getExperienceTo()));
                }
            }
        });

        //region Shrink And Expand

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (placeSection.getVisibility() == View.VISIBLE) {
                    placeSection.setVisibility(View.GONE);
                    arrowPlace.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                } else {
                    placeSection.setVisibility(View.VISIBLE);
                    arrowPlace.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });

        experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (experienceSection.getVisibility() == View.VISIBLE) {
                    experienceSection.setVisibility(View.GONE);
                    arrowExperience.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                } else {
                    experienceSection.setVisibility(View.VISIBLE);
                    arrowExperience.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });

        // endregion
    }
}
