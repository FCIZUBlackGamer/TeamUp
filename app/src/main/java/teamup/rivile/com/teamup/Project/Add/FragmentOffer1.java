package teamup.rivile.com.teamup.Project.Add;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import teamup.rivile.com.teamup.R;

public class FragmentOffer1 extends Fragment {
    View view;
    RelativeLayout money, contributors;
    LinearLayout moneySection, contributorsSection;
    int m, c;/** متغير ثابت عشان اغير حاله ال shrink وال expand*/
    /**
     * 1: Expand, 0:Shrink
     */


    TextInputEditText project_name;
    EditText proDetail, moneyDesc;
    RadioGroup moneyGroup, availGroupMoney, genderGroup;
    RangeSeekBar moneySeekbar, moneyRequiredSeekbar, contributorSeekbar;
    StepperIndicator educationLevel;
    TextView noLev, basic, mid, high;

    FloatingActionButton arrowContributors, arrowMoney;
    EditText moneyOutFrom, moneyOutTo, moneyInFrom, moneyInTo, conFrom, conTo;


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
        moneyDesc = view.findViewById(R.id.moneyDesc);
        moneyGroup = view.findViewById(R.id.moneyGroup);
        genderGroup = view.findViewById(R.id.genderGroup);
        availGroupMoney = view.findViewById(R.id.availGroupMoney);
        moneySeekbar = view.findViewById(R.id.moneySeekbar);
        moneyRequiredSeekbar = view.findViewById(R.id.moneyRequiredSeekbar);
        contributorSeekbar = view.findViewById(R.id.contributorSeekbar);
        educationLevel = view.findViewById(R.id.educationLevel);
        noLev = view.findViewById(R.id.noLev);
        basic = view.findViewById(R.id.basic);
        mid = view.findViewById(R.id.mid);
        high = view.findViewById(R.id.high);
        arrowMoney = view.findViewById(R.id.arrowMoney);
        arrowContributors = view.findViewById(R.id.arrowContributors);

        moneyOutFrom = view.findViewById(R.id.moneyOutFrom);
        moneyOutTo = view.findViewById(R.id.moneyOutTo);

        moneyInFrom = view.findViewById(R.id.moneyInFrom);
        moneyInTo = view.findViewById(R.id.moneyInTo);

        conFrom = view.findViewById(R.id.conFrom);
        conTo = view.findViewById(R.id.conTo);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        setUpSeekBarViews(0, 100000, moneyOutFrom, moneyOutTo, moneySeekbar);
        setUpSeekBarViews(0, 100000, moneyInFrom, moneyInTo, moneyRequiredSeekbar);
        setUpSeekBarViews(0, 15, conFrom, conTo, contributorSeekbar);

        setUpProjectMoneyAvailabilityViewsVisibility();

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

        noLev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                educationLevel.setCurrentStep(1);
            }
        });
        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                educationLevel.setCurrentStep(2);
            }
        });
        mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                educationLevel.setCurrentStep(3);
            }
        });
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                educationLevel.setCurrentStep(4);
            }
        });
    }

    private void setUpSeekBarViews(
            final int minVal,
            final int maxVal,
            final EditText fromEditText,
            final EditText toEditText,
            RangeSeekBar seekBar) {
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
            }
        });

        fromEditText.setText(String.valueOf(minVal));
        fromEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (fromEditText.getText().toString().isEmpty())
                    fromEditText.setText(String.valueOf(minVal));
                if (Integer.valueOf(fromEditText.getText().toString()) > Integer.valueOf(toEditText.getText().toString()))
                    fromEditText.setText(toEditText.getText().toString());
            }
        });

        toEditText.setText(String.valueOf(maxVal));
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
                } else if (checkedId == R.id.notAvail) {
                    moneyRequiredSeekbar.setEnabled(false);
                    moneyInFrom.setEnabled(false);
                    moneyInTo.setEnabled(false);
                }
            }
        });
    }

    private class MinTextWatcher implements TextWatcher {

        private EditText mEditText;
        private RangeSeekBar mRangeSeekBar;

        private int mMinVal;

        private final String MIN_ERROR_MESSAGE;

        MinTextWatcher(EditText editText, int minVal, RangeSeekBar rangeSeekBar) {
            this.mEditText = editText;

            this.mMinVal = minVal;
            this.mRangeSeekBar = rangeSeekBar;

            MIN_ERROR_MESSAGE = getString(R.string.min_val_error) + String.valueOf(mMinVal) + ".";
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String val = s.toString();
            if (val.isEmpty()) val = String.valueOf(mMinVal);

            if (Integer.valueOf(val) >= mMinVal) {
                mRangeSeekBar.setSelectedMinValue(Integer.valueOf(val));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String val = s.toString();
            if (val.isEmpty()) val = String.valueOf(mMinVal);

            if (Integer.valueOf(val) < mMinVal) {
                Toast.makeText(getContext(), MIN_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                mRangeSeekBar.setSelectedMinValue(mMinVal);
                changeTextToMin();
            }
        }

        private void changeTextToMin() {
            mEditText.removeTextChangedListener(this);
            mEditText.setText(String.valueOf(mMinVal));
            mEditText.addTextChangedListener(this);
        }
    }

    private class MaxTextWatcher implements TextWatcher {

        private EditText mEditText;
        private RangeSeekBar mRangeSeekBar;

        private int mMaxVal;

        private final String MAX_ERROR_MESSAGE;

        MaxTextWatcher(EditText editText, int maxVal, RangeSeekBar rangeSeekBar) {
            this.mEditText = editText;
            this.mRangeSeekBar = rangeSeekBar;

            this.mMaxVal = maxVal;

            MAX_ERROR_MESSAGE = getString(R.string.max_error_val) + String.valueOf(mMaxVal) + ".";
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String val = s.toString();
            if (val.isEmpty()) val = String.valueOf(mMaxVal);

            if (Integer.valueOf(val) <= mMaxVal) {
                mRangeSeekBar.setSelectedMaxValue(Integer.valueOf(val));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String val = s.toString();
            if (val.isEmpty()) val = String.valueOf(mMaxVal);

            if (Integer.valueOf(val) > mMaxVal) {
                Toast.makeText(getContext(), MAX_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                mRangeSeekBar.setSelectedMaxValue(mMaxVal);
                changeTextToMax();
            }
        }

        private void changeTextToMax() {
            mEditText.removeTextChangedListener(this);
            mEditText.setText(String.valueOf(mMaxVal));
            mEditText.addTextChangedListener(this);
        }
    }
}
