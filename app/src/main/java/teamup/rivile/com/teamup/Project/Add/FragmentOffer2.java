package teamup.rivile.com.teamup.Project.Add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import teamup.rivile.com.teamup.Project.Add.Adapters.MaxTextWatcher;
import teamup.rivile.com.teamup.Project.Add.Adapters.MinTextWatcher;
import teamup.rivile.com.teamup.R;

public class FragmentOffer2 extends Fragment {
    View view;
    RelativeLayout place, experience;
    LinearLayout placeSection, experienceSection;
    int p, e;/** متغير ثابت عشان اغير حاله ال shrink وال expand*/
    /**
     * 1: Expand, 0:Shrink
     */

    RadioGroup placeGroup, placeKindGroup, placeStateGroup, exGroup;
    RadioButton avail, notAvail, owned, rent;
    EditText placeDesc, exDesc, experienceFrom, experienceTo;
    RecyclerView exRec;
    RangeSeekBar exRequiredSeekbar;

    View map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2_add_project, container, false);
        p = e = 1;
        /** Shrink and Expand Views */
        place = view.findViewById(R.id.place);
        experience = view.findViewById(R.id.experience);
        placeSection = view.findViewById(R.id.placeSection);
        experienceSection = view.findViewById(R.id.experienceSection);
        /** Input Views */

        avail = view.findViewById(R.id.avail);
        notAvail = view.findViewById(R.id.notAvail);
        owned = view.findViewById(R.id.owned);
        rent = view.findViewById(R.id.rent);

        placeGroup = view.findViewById(R.id.placeGroup);
        placeKindGroup = view.findViewById(R.id.placeKindGroup);
        placeStateGroup = view.findViewById(R.id.placeStateGroup);
        exGroup = view.findViewById(R.id.exGroup);
        placeDesc = view.findViewById(R.id.placeDesc);
        exDesc = view.findViewById(R.id.exDesc);
        experienceFrom = view.findViewById(R.id.experienceFrom);
        experienceTo = view.findViewById(R.id.experienceTo);
        exRequiredSeekbar = view.findViewById(R.id.exRequiredSeekbar);

        exRec = view.findViewById(R.id.exRec);
        map = view.findViewById(R.id.map);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        setUpProjectPlaceNeedViewsVisibility();
        setUpProjectExperienceNeedViewsVisibility();
        setUpSeekBarViews(0, 15, experienceFrom, experienceTo, exRequiredSeekbar);

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

    private void setUpProjectPlaceNeedViewsVisibility() {
        placeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes) {
                    map.setEnabled(true);
                    placeDesc.setEnabled(true);

                    avail.setEnabled(true);
                    notAvail.setEnabled(true);
                    owned.setEnabled(true);
                    rent.setEnabled(true);
                } else if (checkedId == R.id.no) {
                    map.setEnabled(false);
                    placeDesc.setEnabled(false);

                    avail.setEnabled(false);
                    notAvail.setEnabled(false);
                    owned.setEnabled(false);
                    rent.setEnabled(false);
                }
            }
        });
    }

    private void setUpProjectExperienceNeedViewsVisibility() {
        exGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.y) {
                    exDesc.setEnabled(true);
                    exRec.setEnabled(true);
                    exRequiredSeekbar.setEnabled(true);
                    experienceFrom.setEnabled(true);
                    experienceTo.setEnabled(true);
                } else if (checkedId == R.id.n) {
                    exDesc.setEnabled(false);
                    exRec.setEnabled(false);
                    exRequiredSeekbar.setEnabled(false);
                    experienceFrom.setEnabled(false);
                    experienceTo.setEnabled(false);
                }
            }
        });
    }
}
