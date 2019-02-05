package teamup.rivile.com.teamup.Project.Add;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import teamup.rivile.com.teamup.Project.Add.Adapters.ChipsAdapter;
import teamup.rivile.com.teamup.Project.Add.Adapters.LoadedChipsAdapter;
import teamup.rivile.com.teamup.Project.Add.StaticShit.Offers;
import teamup.rivile.com.teamup.Project.Add.StaticShit.RequirmentModel;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.ExperienceTypeModel;
import teamup.rivile.com.teamup.Uitls.MaxTextWatcher;
import teamup.rivile.com.teamup.Uitls.MinTextWatcher;

public class FragmentOffer2 extends Fragment {
    private final char SPACE = ' ';
    private final char NEW_LINE = '\n';

    View view;
    RelativeLayout place, experience;
    LinearLayout placeSection, experienceSection;
    int p, e;/** متغير ثابت عشان اغير حاله ال shrink وال expand*/
    /**
     * 1: Expand, 0:Shrink
     */

    RadioGroup placeGroup, placeKindGroup, placeStateGroup, exGroup;
    RadioButton avail, notAvail, owned, rent;
    EditText placeDesc, exDesc, exDep, experienceFrom, experienceTo;
    RecyclerView exRec, exRecUserAdd;
    RangeSeekBar exRequiredSeekbar;

    View map;

    ChipsAdapter mExRecUserAdapter;
    LoadedChipsAdapter mExRecLoadedAdapter;
    static ArrayList<ExperienceTypeModel> mLoadedExperienceTypes = new ArrayList<>();

    private int minExperienceNeeded = 0, maxExperienceNeeded = 15;
    static ViewPager pager;
    static FragmentPagerAdapter pagerAdapter;


    static FragmentOffer2 setPager(ViewPager viewPager, FragmentPagerAdapter pagerAdapte, ArrayList<ExperienceTypeModel> loadedExperienceTypes) {
        pager = viewPager;
        pagerAdapter = pagerAdapte;
        mLoadedExperienceTypes = loadedExperienceTypes;
        return new FragmentOffer2();
    }

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
        exDep = view.findViewById(R.id.exDep);
        map = view.findViewById(R.id.map);

        exRecUserAdd = view.findViewById(R.id.exRecUserAdd);

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {
        super.onStart();

        placeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes) {
                    RequirmentModel.setNeedPlace(true);
                } else if (checkedId == R.id.no) {
                    RequirmentModel.setNeedPlace(false);
                }
            }
        });

        placeKindGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rent) {
                    RequirmentModel.setNeedPlaceType(true);
                } else if (checkedId == R.id.owned) {
                    RequirmentModel.setNeedPlaceType(false);
                }
            }
        });

        placeStateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.avail) {
                    RequirmentModel.setNeedPlaceType(true);
                } else if (checkedId == R.id.notAvail) {
                    RequirmentModel.setNeedPlaceType(false);
                }
            }
        });

        exGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.y) {
                    RequirmentModel.setNeedExperience(true);
                } else if (checkedId == R.id.n) {
                    RequirmentModel.setNeedExperience(false);
                }
            }
        });

        exRequiredSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                RequirmentModel.setExperienceFrom((int) minValue);
                RequirmentModel.setExperienceTo((int) maxValue);
            }
        });

        placeDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!validate()) {
                    pager.setCurrentItem(0);
                    pagerAdapter.notifyDataSetChanged();
                } else {
                    Log.e("ttt", teamup.rivile.com.teamup.Project.Add.StaticShit.Offers.getDescription());
                    RequirmentModel.setPlaceDescriptions(placeDesc.getText().toString());

                    Log.e("r", RequirmentModel.getPlaceDescriptions());
                    Toast.makeText(getActivity(), "dgjvhds", Toast.LENGTH_SHORT).show();
                }

            }
        });

        exDesc.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        // the user is done typing.
                        RequirmentModel.setExperienceDescriptions(exDesc.getText().toString());
                        return true; // consume.
                    }
                }
                return false;
            }
        });

        setUpProjectPlaceNeedViewsVisibility();
        setUpProjectExperienceNeedViewsVisibility();
        setUpSeekBarViews(minExperienceNeeded, maxExperienceNeeded, experienceFrom, experienceTo, exRequiredSeekbar);

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


        setUpExpDepViews();
    }

    private void setUpExpDepViews() {
        //TODO: load data from the network

        exRec.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL));

        //TODO: change adapter data here or use mTagsRecLoadedAdapter.swapData(<Pass yor Data List>);
        mExRecLoadedAdapter = new LoadedChipsAdapter(null, mExRecUserAdapter);
        exRec.setAdapter(mExRecLoadedAdapter);
        //TODO: to get selected chips, use mTagsRecLoadedAdapter.getSelectedTypeModels(). get them when moving to next fragment

        //TODO: Same data loading as mTagsRecLoadedAdapter
        exRecUserAdd.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL));
        mExRecUserAdapter = new ChipsAdapter(null, mExRecLoadedAdapter);
        exRecUserAdd.setAdapter(mExRecUserAdapter);

        exDep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                boolean addText = false;
                if (!text.isEmpty()) {
                    for (int i = text.length() - 1; i >= 0; --i) {
                        if (text.charAt(i) != SPACE && text.charAt(i) != NEW_LINE)
                            addText = true;
                    }

                    addText = addText && (text.charAt(text.length() - 1) == SPACE ||
                            text.charAt(text.length() - 1) == NEW_LINE);

                    if (addText) {
                        //if user added this before
                        List<ExperienceTypeModel> userAddedExperienceTypes =
                                mExRecUserAdapter.getSelectedTypeModels();
                        for (int i = userAddedExperienceTypes.size() - 1; i >= 0; --i) {
                            if (userAddedExperienceTypes.get(i).getName().equals(text)) {
                                return;
                            }
                        }

                        //else if user typed something exists int loaded list
                        for (int i = mLoadedExperienceTypes.size() - 1; i >= 0; --i) {
                            ExperienceTypeModel typeModel = mLoadedExperienceTypes.get(i);
                            if (typeModel.getName().equals(text)) {
                                mExRecLoadedAdapter.removeTypeModel(typeModel);
                                mExRecUserAdapter.addTypeModel(typeModel);
                                return;
                            }
                        }

                        //else
                        ExperienceTypeModel typeModel = new ExperienceTypeModel();
                        typeModel.setId(0);
                        typeModel.setName(text);
                        mExRecUserAdapter.addTypeModel(typeModel);

                        //clear EditText
                        exDep.setText("");
                    }
                }
            }
        });
    }

    private boolean validate() {
        boolean res = true;
        if (Offers.getDescription() == null || Offers.getDescription().isEmpty() || Offers.getName() == null || Offers.getName().isEmpty()) {
            res = false;
        }
        Log.e("res", String.valueOf(res));
        return res;
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
