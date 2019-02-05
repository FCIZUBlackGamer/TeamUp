package teamup.rivile.com.teamup.Project.Add;

import android.annotation.SuppressLint;
import android.os.Build;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    int m, c;/** متغير ثابت عشان اغير حاله ال shrink وال expand*/
    /**
     * 1: Expand, 0:Shrink
     */


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
        m = c = 1;
        /** Shrink and Expand Views */
        money = view.findViewById(R.id.money);
        contributors = view.findViewById(R.id.contributors);
        moneySection = view.findViewById(R.id.moneySection);
        contributorsSection = view.findViewById(R.id.contributorsSection);
        /** Input Views */

        project_name = view.findViewById(R.id.project_name);
        proDetail = view.findViewById(R.id.proDetail);
//        moneyDesc = view.findViewById(R.id.moneyDesc);
        moneyGroup = view.findViewById(R.id.moneyGroup);
        genderGroup = view.findViewById(R.id.genderGroup);
        availGroupMoney = view.findViewById(R.id.availGroupMoney);
        moneySeekbar = view.findViewById(R.id.moneySeekbar);
        moneyRequiredSeekbar = view.findViewById(R.id.moneyRequiredSeekbar);
        contributorSeekbar = view.findViewById(R.id.contributorSeekbar);
        educationLevel = view.findViewById(R.id.educationLevel);
        arrowMoney = view.findViewById(R.id.arrowMoney);
        arrowContributors = view.findViewById(R.id.arrowContributors);

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
        project_name.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        // the user is done typing.
                        Offers.setName(project_name.getText().toString());
                        return true; // consume.
                    }
                }
                return false;
            }
        });

        moneyDesc.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        // the user is done typing.
                        RequirmentModel.setMoneyDescriptions(moneyDesc.getText().toString());
                        return true; // consume.
                    }
                }
                return false;
            }
        });

        moneySeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Offers.setProfitFrom((int) minValue);
                Offers.setProfitTo((int) maxValue);
            }
        });

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

        availGroupMoney.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.notAvail) {
                    RequirmentModel.setNeedMoney(false);
                } else if (checkedId == R.id.avail) {
                    RequirmentModel.setNeedMoney(true);
                }
            }
        });

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
        moneyRequiredSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                RequirmentModel.setMoneyFrom((int) minValue);
                RequirmentModel.setMoneyTo((int) maxValue);
            }
        });

        contributorSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Offers.setNumContributorFrom((int) minValue);
                Offers.setNumContributorTo((int) maxValue);
            }
        });
        setUpSeekBarViews(minMoneyOut, maxMoneyOut, moneyOutFrom, moneyOutTo, moneySeekbar);
        setUpSeekBarViews(minMoneyIn, maxMoneyIn, moneyInFrom, moneyInTo, moneyRequiredSeekbar);
        setUpSeekBarViews(minContributor, maxContributor, conFrom, conTo, contributorSeekbar);

        if(educationLevel.getCurrentStep()!=0) offer.setEducationContributorLevel(educationLevel.getCurrentStep());
        educationLevel.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                step++;
                educationLevel.setCurrentStep(step);
                offer.setEducationContributorLevel(step);
            }
        });

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m == 1) {
                    m = 0;
                    moneySection.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        arrowMoney.setImageDrawable(getActivity().getDrawable(R.drawable.ic_arrow_down));
                    }
                } else {
                    m = 1;
                    moneySection.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        arrowMoney.setImageDrawable(getActivity().getDrawable(R.drawable.ic_arrow_up));
                    }
                }
            }
        });

        contributors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c == 1) {
                    c = 0;
                    contributorsSection.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        arrowContributors.setImageDrawable(getActivity().getDrawable(R.drawable.ic_arrow_down));
                    }
                } else {
                    c = 1;
                    contributorsSection.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        arrowContributors.setImageDrawable(getActivity().getDrawable(R.drawable.ic_arrow_up));
                    }
                }
            }
        });
    }

    private void setUpSeekBarViews(
            final int minVal,
            final int maxVal,
            final EditText fromEditText,
            final EditText toEditText,
            final RangeSeekBar seekBar) {
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

//                if (seekBarOrder == 1) {
//                    offer.setProfitFrom(minVal);
//                    offer.setProfitTo(maxVal);
//                } else if (seekBarOrder == 2) {
//                    requirmentModel.setMoneyFrom(minVal);
//                    requirmentModel.setMoneyTo(maxVal);
//                } else if (seekBarOrder == 3) {
//                    offer.setNumContributorFrom(minVal);
//                    offer.setNumContributorTo(maxVal);
//                }
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

    private void setUpDataSaving() {
        //All SeekBar data saving are handled in setUpSeekBarViews()

        proDetail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    offer.setDescription(proDetail.getText().toString());
            }
        });

        int currentMoneyCheckedId = moneyGroup.getCheckedRadioButtonId();
        int currentProfitType = currentMoneyCheckedId == R.id.day ? 0 :
                currentMoneyCheckedId == R.id.month ? 1 :
                        currentMoneyCheckedId == R.id.year ? 2 : 3;
        offer.setProfitType(currentProfitType);
        moneyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int profitType = checkedId == R.id.day ? 0 :
                        checkedId == R.id.month ? 1 :
                                checkedId == R.id.year ? 2 : 3;
                offer.setProfitType(profitType);
            }
        });

        requirmentModel.setNeedMoney(availGroupMoney.getCheckedRadioButtonId() == R.id.avail);
        availGroupMoney.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                requirmentModel.setNeedMoney(checkedId == R.id.avail);
                if (checkedId == R.id.avail) {
                    moneyRequiredSeekbar.setEnabled(true);
                    moneyInFrom.setEnabled(true);
                    moneyInTo.setEnabled(true);
                } else if (checkedId == R.id.notAvail) {
                    moneyRequiredSeekbar.setEnabled(false);
                    moneyInFrom.setEnabled(false);
                    moneyInTo.setEnabled(false);
                }
            }
        });


        int currentGenderCheckedId = genderGroup.getCheckedRadioButtonId();
        int currentGender = currentGenderCheckedId == R.id.male ? 0 :
                currentGenderCheckedId == R.id.female ? 1 : 2;
        offer.setGenderContributor(currentGender);
        offer.setGenderContributor(genderGroup.getCheckedRadioButtonId());
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int gender = checkedId == R.id.male ? 0 :
                        checkedId == R.id.female ? 1 : 2;
                offer.setGenderContributor(gender);
            }
        });


    }
}
