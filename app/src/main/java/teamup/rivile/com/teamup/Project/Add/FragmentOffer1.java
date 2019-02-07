package teamup.rivile.com.teamup.Project.Add;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.badoualy.stepperindicator.StepperIndicator;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import teamup.rivile.com.teamup.Project.Add.StaticShit.Offers;
import teamup.rivile.com.teamup.Project.Add.StaticShit.RequirmentModel;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.MaxTextWatcher;
import teamup.rivile.com.teamup.Uitls.MinTextWatcher;

public class FragmentOffer1 extends Fragment {
    View view;
    RelativeLayout money, contributors;
    LinearLayout moneySection, contributorsSection;

    TextInputEditText project_name;
    EditText proDetail/*, moneyDesc*/;
    RadioGroup moneyGroup, availGroupMoney, genderGroup;
    RangeSeekBar moneySeekbar, moneyRequiredSeekbar, contributorSeekbar;
    StepperIndicator educationLevel;

    FloatingActionButton arrowContributors, arrowMoney;
    EditText moneyOutFrom, moneyOutTo, moneyInFrom, moneyInTo, conFrom, conTo;

    private final int minMoneyOut = 0,
            maxMoneyOut = 100000,
            minMoneyIn = 0,
            maxMoneyIn = 100000,
            minContributor = 0,
            maxContributor = 15;

    static ViewPager pager;
    static FragmentPagerAdapter pagerAdapter;


    static FragmentOffer1 setPager(ViewPager viewPager, FragmentPagerAdapter pagerAdapte) {
        pager = viewPager;
        pagerAdapter = pagerAdapte;
        return new FragmentOffer1();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1_add_project, container, false);
        /** Shrink and Expand Views */
        money = view.findViewById(R.id.money);
        contributors = view.findViewById(R.id.contributors);
        moneySection = view.findViewById(R.id.moneySection);
        contributorsSection = view.findViewById(R.id.contributorsSection);
        arrowMoney = view.findViewById(R.id.arrowMoney);
        arrowContributors = view.findViewById(R.id.arrowContributors);
        /** Input Views */

        project_name = view.findViewById(R.id.project_name);
        proDetail = view.findViewById(R.id.proDetail);
//        moneyDesc = view.findViewById(R.id.moneyDesc);

        moneyGroup = view.findViewById(R.id.moneyGroup);
        int checkedId = moneyGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.day) {
            Offers.setProfitType(0);
        } else if (checkedId == R.id.month) {
            Offers.setProfitType(1);
        } else if (checkedId == R.id.year) {
            Offers.setProfitType(2);
        } else if (checkedId == R.id.other) {
            Offers.setProfitType(3);
        }

        genderGroup = view.findViewById(R.id.genderGroup);
        checkedId = genderGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.male) {
            Offers.setGenderContributor(0);
        } else if (checkedId == R.id.female) {
            Offers.setGenderContributor(1);
        } else if (checkedId == R.id.both) {
            Offers.setGenderContributor(2);
        }

        availGroupMoney = view.findViewById(R.id.availGroupMoney);
        checkedId = availGroupMoney.getCheckedRadioButtonId();
        RequirmentModel.setNeedMoney(checkedId == R.id.avail);

        moneySeekbar = view.findViewById(R.id.moneySeekbar);
        Offers.setProfitFrom(minMoneyOut);
        Offers.setProfitTo(maxMoneyOut);

        moneyRequiredSeekbar = view.findViewById(R.id.moneyRequiredSeekbar);
        RequirmentModel.setMoneyFrom(minMoneyIn);
        RequirmentModel.setMoneyTo(maxMoneyIn);

        contributorSeekbar = view.findViewById(R.id.contributorSeekbar);
        Offers.setNumContributorFrom(minContributor);
        Offers.setNumContributorTo(maxContributor);

        educationLevel = view.findViewById(R.id.educationLevel);
        educationLevel.setCurrentStep(0);
        Offers.setEducationContributorLevel(0);

        moneyOutFrom = view.findViewById(R.id.moneyOutFrom);
        moneyOutTo = view.findViewById(R.id.moneyOutTo);

        moneyInFrom = view.findViewById(R.id.moneyInFrom);
        moneyInTo = view.findViewById(R.id.moneyInTo);

        conFrom = view.findViewById(R.id.conFrom);
        conTo = view.findViewById(R.id.conTo);

//        if (Offers == null) {
//            Offers = new Offerss();
//        }
//
//        if (RequirmentModel == null) {
//            RequirmentModel = new RequirmentModel();
//        }
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {
        super.onStart();


        proDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Offers.setDescription(proDetail.getText().toString());
                Log.e("Data", Offers.getDescription());
            }
        });

//
//       moneyDesc.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
//                        actionId == EditorInfo.IME_ACTION_DONE ||
//                        event != null &&
//                                event.getAction() == KeyEvent.ACTION_DOWN &&
//                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                    if (event == null || !event.isShiftPressed()) {
//                        // the user is done typing.
//                        requirmentModel.setMoneyDescriptions(moneyDesc.getText().toString());
//                        return true; // consume.
//                    }
//                }
//                return false;
//            }
//            });
        project_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Offers.setName(s.toString());
            }
        });

//        moneyDesc.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
//                        actionId == EditorInfo.IME_ACTION_DONE ||
//                        event != null &&
//                                event.getAction() == KeyEvent.ACTION_DOWN &&
//                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                    if (event == null || !event.isShiftPressed()) {
//                        // the user is done typing.
//                        RequirmentModel.setMoneyDescriptions(moneyDesc.getText().toString());
//                        return true; // consume.
//                    }
//                }
//                return false;
//            }
//        });
        moneyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.day) {
                    Offers.setProfitType(0);
                } else if (checkedId == R.id.month) {
                    Offers.setProfitType(1);
                } else if (checkedId == R.id.year) {
                    Offers.setProfitType(2);
                } else if (checkedId == R.id.other) {
                    Offers.setProfitType(3);
                }
            }
        });

        setUpProjectMoneyAvailabilityViewsVisibility();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {
                    Offers.setGenderContributor(0);
                } else if (checkedId == R.id.female) {
                    Offers.setGenderContributor(1);
                } else if (checkedId == R.id.both) {
                    Offers.setGenderContributor(2);
                }
            }
        });

        setUpSeekBarViews(minMoneyOut, maxMoneyOut, moneyOutFrom, moneyOutTo, moneySeekbar, 1);
        setUpSeekBarViews(minMoneyIn, maxMoneyIn, moneyInFrom, moneyInTo, moneyRequiredSeekbar, 2);
        setUpSeekBarViews(minContributor, maxContributor, conFrom, conTo, contributorSeekbar, 3);

        if (educationLevel.getCurrentStep() != 0)
            Offers.setEducationContributorLevel(educationLevel.getCurrentStep());
        educationLevel.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                educationLevel.setCurrentStep(step);
                Offers.setEducationContributorLevel(step);
            }
        });

        //region Shrink And Expand

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moneySection.getVisibility() == View.VISIBLE) {
                    moneySection.setVisibility(View.GONE);
                    arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                } else {
                    moneySection.setVisibility(View.VISIBLE);
                    arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });

        contributors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contributorsSection.getVisibility() == View.VISIBLE) {
                    contributorsSection.setVisibility(View.GONE);
                    arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                } else {
                    contributorsSection.setVisibility(View.VISIBLE);
                    arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });

        //endregion
    }

    private void setUpSeekBarViews(
            final int minVal,
            final int maxVal,
            final EditText fromEditText,
            final EditText toEditText,
            final RangeSeekBar seekBar,
            final int seekBarOrder) {
        final MinTextWatcher minTextWatcher = new MinTextWatcher(fromEditText, minVal, seekBar);
        fromEditText.addTextChangedListener(minTextWatcher);

        final MaxTextWatcher maxTextWatcher = new MaxTextWatcher(toEditText, maxVal, seekBar);
        toEditText.addTextChangedListener(maxTextWatcher);

        seekBar.setRangeValues(minVal, maxVal);
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                fromEditText.removeTextChangedListener(minTextWatcher);
                fromEditText.setText(minValue.toString());
                fromEditText.addTextChangedListener(minTextWatcher);

                toEditText.removeTextChangedListener(maxTextWatcher);
                toEditText.setText(maxValue.toString());
                toEditText.addTextChangedListener(maxTextWatcher);

                if (seekBarOrder == 1) {
                    Offers.setProfitFrom(minVal);
                    Offers.setProfitTo(maxVal);
                } else if (seekBarOrder == 2) {
                    RequirmentModel.setMoneyFrom(minVal);
                    RequirmentModel.setMoneyTo(maxVal);
                } else if (seekBarOrder == 3) {
                    Offers.setNumContributorFrom(minVal);
                    Offers.setNumContributorTo(maxVal);
                }
            }
        });

//        fromEditText.setText(String.valueOf(minVal));
        fromEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (fromEditText.getText().toString().isEmpty())
                    fromEditText.setText(String.valueOf(minVal));
                if (Integer.valueOf(fromEditText.getText().toString()) > Integer.valueOf(toEditText.getText().toString()))
                    fromEditText.setText(toEditText.getText().toString());
            }
        });

//        toEditText.setText(String.valueOf(maxVal));
        toEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (toEditText.getText().toString().isEmpty())
                    toEditText.setText(String.valueOf(maxVal));
                if (Integer.valueOf(toEditText.getText().toString()) < Integer.valueOf(fromEditText.getText().toString()))
                    toEditText.setText(fromEditText.getText().toString());
            }
        });
    }


    private void setUpProjectMoneyAvailabilityViewsVisibility() {
        availGroupMoney.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.avail) {
                    moneyRequiredSeekbar.setEnabled(true);
                    moneyInFrom.setEnabled(true);
                    moneyInTo.setEnabled(true);

                    RequirmentModel.setNeedMoney(true);
                } else if (checkedId == R.id.notAvail) {
                    moneyRequiredSeekbar.setEnabled(false);
                    moneyInFrom.setEnabled(false);
                    moneyInTo.setEnabled(false);

                    RequirmentModel.setNeedMoney(false);
                }
            }
        });
    }

}
