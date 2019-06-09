package teamup.rivile.com.teamup.ui.Project.Add;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.Uitls.APIModels.Offer;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.UserDataBase;
import teamup.rivile.com.teamup.ui.DrawerActivity;
import teamup.rivile.com.teamup.ui.Project.Add.StaticShit.Offers;
import teamup.rivile.com.teamup.ui.Project.Details.OfferDetails;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;

public class FragmentOffer1 extends Fragment {
    private CircleImageView mUserImage;
    private RelativeLayout mMoneyRelativeLayout, mContributorsRelativeLayout, mPlaceRelativeLayout;
    private LinearLayout mMoneySectionLinearLayout, mContributorsSectionLinearLayout, mPlaceSectionLinearLayout;

    private TextInputEditText mProjectNameTextInputEditText;
    private EditText mProductDetailsEditText, mMoneyDescriptionEditText;
    private RadioGroup mMoneyRadioGroup, mGenderRadioGroup, mPlaceRadioGroup;

    private ImageView mArrowContributorsImageView, mArrowMoneyImageView, mArrowPlaceImageView;
    private EditText mProfitMoneyEditText, mInitialCostEditText, mDirectCostEditText,
            mIndirectCostEditText, mTotalCostEditText, mProjectDurationEditText, mContributorsEditText;
    private Spinner mDurationTypeSpinner, mProfitMoneySpinner,
    /*sp_initialCost,*/ mDirectCostSpinner, mIndirectCostSpinner, mProjectTypeSpinner;

    private static ViewPager mPager;
    private static FragmentPagerAdapter mPagerAdapter;
    private Realm mRealm;

    private static MutableLiveData<OfferDetails> mLoadedProjectWithAllDataLiveData = null;

    static FragmentOffer1 setPager(ViewPager viewPager, FragmentPagerAdapter pagerAdapter, MutableLiveData<OfferDetails> loadedProjectWithAllDataLiveData) {
        mPager = viewPager;
        mPagerAdapter = pagerAdapter;
        mLoadedProjectWithAllDataLiveData = loadedProjectWithAllDataLiveData;
        return new FragmentOffer1();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_add_project, container, false);
        mRealm = Realm.getDefaultInstance();
        /** Shrink and Expand Views */
        mPlaceRelativeLayout = view.findViewById(R.id.place);
        mMoneyRelativeLayout = view.findViewById(R.id.money);
        mContributorsRelativeLayout = view.findViewById(R.id.contributors);
        mMoneySectionLinearLayout = view.findViewById(R.id.moneySection);
        mPlaceSectionLinearLayout = view.findViewById(R.id.placeSection);
        mContributorsSectionLinearLayout = view.findViewById(R.id.contributorsSection);
        mArrowMoneyImageView = view.findViewById(R.id.arrowMoney);
        mArrowPlaceImageView = view.findViewById(R.id.arrowPlace);
        mArrowContributorsImageView = view.findViewById(R.id.arrowContributors);
        /** Input Views */

        mUserImage = view.findViewById(R.id.user_image);
        mPlaceRadioGroup = view.findViewById(R.id.placeGroup);
        mProjectNameTextInputEditText = view.findViewById(R.id.project_name);
        mProductDetailsEditText = view.findViewById(R.id.proDetail);
//        mMoneyDescriptionEditText = view.findViewById(R.id.mMoneyDescriptionEditText);
        mProfitMoneyEditText = view.findViewById(R.id.ed_profitMoney);
        mInitialCostEditText = view.findViewById(R.id.ed_initialCost);
        mDirectCostEditText = view.findViewById(R.id.ed_directCost);
        mIndirectCostEditText = view.findViewById(R.id.ed_IndirectCost);
        mTotalCostEditText = view.findViewById(R.id.ed_totalCost);
        mProjectDurationEditText = view.findViewById(R.id.ed_proDuration);
        mDurationTypeSpinner = view.findViewById(R.id.sp_durationType);
        mContributorsEditText = view.findViewById(R.id.ed_contributers);

        mProjectTypeSpinner = view.findViewById(R.id.sp_proType);
        mProfitMoneySpinner = view.findViewById(R.id.sp_profitMoney);
//        sp_initialCost = view.findViewById(R.id.sp_initialCost);
        mDirectCostSpinner = view.findViewById(R.id.sp_directCost);
        mIndirectCostSpinner = view.findViewById(R.id.sp_IndirectCost);

        mMoneyRadioGroup = view.findViewById(R.id.moneyGroup);

        mGenderRadioGroup = view.findViewById(R.id.genderGroup);

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {
        super.onStart();

        ((DrawerActivity) getActivity()).hideSearchBar();


        mRealm.executeTransaction(realm1 -> {
            LoginDataBase loginDataBase = realm1.where(LoginDataBase.class).findFirst();
            UserDataBase userDataBase = null;
            if (loginDataBase != null) {
                userDataBase = loginDataBase.getUser();
            }

            if (loginDataBase != null) {
                String image = userDataBase.getImage();
                if (false) { // founded in device

                } else {
                    if (!image.startsWith("http"))
                        Picasso.get().load(API.BASE_URL + image).into(mUserImage);
                    else {
                        Picasso.get().load(image).into(mUserImage);
                    }
                }
            }
        });

        validate_Money();
        validate_Type();


        mProjectNameTextInputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) Offers.setName(mProjectNameTextInputEditText.getText().toString());
        });

        mProductDetailsEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                Offers.setDescription(mProductDetailsEditText.getText().toString());
        });

        int checkedId = mMoneyRadioGroup.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.day:
                Offers.setProfitType(0);
                break;
            case R.id.month:
                Offers.setProfitType(1);
                break;
            case R.id.year:
                Offers.setProfitType(2);
                break;
            case R.id.other:
                Offers.setProfitType(3);
                break;
        }

        checkedId = mGenderRadioGroup.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.male:
                Offers.setGenderContributor(0);
                break;
            case R.id.female:
                Offers.setGenderContributor(1);
                break;
            case R.id.both:
                Offers.setGenderContributor(2);
                break;
        }

        mMoneyRadioGroup.setOnCheckedChangeListener((group, checkedId2) -> {
            switch (checkedId2) {
                case R.id.day:
                    Offers.setProfitType(0);
                    break;
                case R.id.month:
                    Offers.setProfitType(1);
                    break;
                case R.id.year:
                    Offers.setProfitType(2);
                    break;
                case R.id.other:
                    Offers.setProfitType(3);
                    break;
            }
        });

        mGenderRadioGroup.setOnCheckedChangeListener((group, checkedId2) -> {
            switch (checkedId2) {
                case R.id.male:
                    Offers.setGenderContributor(0);
                    break;
                case R.id.female:
                    Offers.setGenderContributor(1);
                    break;
                case R.id.both:
                    Offers.setGenderContributor(2);
                    break;
            }
        });

        Offers.setStatus(0);

        if (mLoadedProjectWithAllDataLiveData != null) {
            mLoadedProjectWithAllDataLiveData.observe(this, offers -> {
                if (offers != null) {
                    Offers.setStatus(offers.getStatus());

                    mProjectNameTextInputEditText.setText(offers.getName());
                    Offers.setName(mProjectNameTextInputEditText.getText().toString());

                    mProductDetailsEditText.setText(offers.getDescription());
                    Offers.setDescription(mProductDetailsEditText.getText().toString());

//                    switch (offers.getProfitType()) {
//                        case 0:
//                            mMoneyRadioGroup.check(R.id.day);
//                            Offers.setProfitType(0);
//                            break;
//                        case 1:
//                            mMoneyRadioGroup.check(R.id.month);
//                            Offers.setProfitType(1);
//                            break;
//                        case 2:
//                            mMoneyRadioGroup.check(R.id.year);
//                            Offers.setProfitType(2);
//                            break;
//                        case 3:
//                            mMoneyRadioGroup.check(R.id.other);
//                            Offers.setProfitType(3);
//                            break;
//                    }

                    switch (offers.getGenderContributor()) {
                        case 0:
                            mGenderRadioGroup.check(R.id.male);
                            Offers.setGenderContributor(0);
                            break;
                        case 1:
                            mGenderRadioGroup.check(R.id.female);
                            Offers.setGenderContributor(1);
                            break;
                        case 2:
                            mGenderRadioGroup.check(R.id.both);
                            Offers.setGenderContributor(2);
                            break;
                    }

                    mInitialCostEditText.setText(String.valueOf(offers.getInitialCost()));
                    Offers.setInitialCost(offers.getInitialCost());

                    mDirectCostEditText.setText(String.valueOf(offers.getDirectExpenses()));
                    Offers.setDirectExpenses(offers.getDirectExpenses());

                    mIndirectCostEditText.setText(String.valueOf(offers.getIndectExpenses()));
                    Offers.setIndectExpenses(offers.getIndectExpenses());

                    mTotalCostEditText.setText(String.valueOf(
                            Offers.getDirectExpenses() + Offers.getIndectExpenses()
                    ));

                    mProfitMoneyEditText.setText(String.valueOf(offers.getProfitMoney()));
                    Offers.setProfitMoney(offers.getProfitMoney());

                    mContributorsEditText.setText(String.valueOf(offers.getNumContributor()));
                    Offers.setNumContributor(offers.getNumContributor());
                }
            });
        }

        //region Shrink And Expand

        mMoneyRelativeLayout.setOnClickListener(v -> {
            if (mMoneySectionLinearLayout.getVisibility() == View.VISIBLE) {
                mMoneySectionLinearLayout.setVisibility(View.GONE);
                mArrowMoneyImageView.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);

            } else {
                mMoneySectionLinearLayout.setVisibility(View.VISIBLE);
                mArrowMoneyImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            }
        });

        mPlaceRelativeLayout.setOnClickListener(v -> {
            if (mPlaceSectionLinearLayout.getVisibility() == View.VISIBLE) {
                mPlaceSectionLinearLayout.setVisibility(View.GONE);
                mArrowPlaceImageView.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);

            } else {
                mPlaceSectionLinearLayout.setVisibility(View.VISIBLE);
                mArrowPlaceImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            }
        });

        mContributorsRelativeLayout.setOnClickListener(v -> {
            if (mContributorsSectionLinearLayout.getVisibility() == View.VISIBLE) {
                mContributorsSectionLinearLayout.setVisibility(View.GONE);
                mArrowContributorsImageView.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);

            } else {
                mContributorsSectionLinearLayout.setVisibility(View.VISIBLE);
                mArrowContributorsImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            }
        });

        //endregion
    }

    private void validate_Type() {
        mProfitMoneySpinner.setDropDownWidth(200);
        mProjectTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Offers.setProjectType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mIndirectCostSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Offers.setIndectExpensesType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDirectCostSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Offers.setDirectExpensesType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        sp_initialCost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Offers.setInitialCostType(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        mDurationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Offers.setProjectDurationType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mProfitMoneySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Offers.setProfitType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void validate_Money() {

        mContributorsEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String valString = mContributorsEditText.getText().toString();
                if (valString.isEmpty()) valString = "0";
                int value = Integer.valueOf(valString);
                if (value <= 1) value = 1;
                Offers.setNumContributor(value);
                mContributorsEditText.setText(String.valueOf(value));
            }
        });

        mProjectDurationEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String valString = mProjectDurationEditText.getText().toString();
                if (valString.isEmpty()) valString = "0";
                float value = Float.valueOf(valString);
                if (value <= 1f) value = 1f;
                Offers.setProjectDuration(value);
                mProjectDurationEditText.setText(String.valueOf(value));
            }
        });

        mIndirectCostEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String valString = mIndirectCostEditText.getText().toString();
                if (valString.isEmpty()) valString = "0";
                float value = Float.valueOf(valString);
                if (value <= 0f) value = 0f;
                Offers.setIndectExpenses(value);
                mIndirectCostEditText.setText(String.valueOf(value));

                mTotalCostEditText.setText(String.valueOf(
                        Offers.getDirectExpenses() + Offers.getIndectExpenses()
                ));
            }
        });

        mDirectCostEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String valString = mDirectCostEditText.getText().toString();
                if (valString.isEmpty()) valString = "0";
                float value = Float.valueOf(valString);
                if (value <= 1000f) value = 1000f;
                Offers.setDirectExpenses(value);
                mDirectCostEditText.setText(String.valueOf(value));

                mTotalCostEditText.setText(String.valueOf(
                        Offers.getDirectExpenses() + Offers.getIndectExpenses()
                ));
            }
        });

        mInitialCostEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String valString = mInitialCostEditText.getText().toString();
                if (valString.isEmpty()) valString = "0";
                float value = Float.valueOf(valString);
                if (value <= 0f) value = 0f;
                Offers.setInitialCost(value);
                mInitialCostEditText.setText(String.valueOf(value));
            }
        });

        mProfitMoneyEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String valString = mProfitMoneyEditText.getText().toString();
                if (valString.isEmpty()) valString = "0";
                float value = Float.valueOf(valString);
                if (value <= 5000f) value = 5000f;
                Offers.setProfitMoney(value);
                mProfitMoneyEditText.setText(String.valueOf(value));
            }
        });
    }
}
