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
import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.ui.Project.Add.StaticShit.Offers;
import teamup.rivile.com.teamup.ui.Project.Details.OfferDetails;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;

public class FragmentOffer1 extends Fragment {
    View view;
    CircleImageView user_image;
    RelativeLayout money, contributors, place;
    LinearLayout moneySection, contributorsSection, placeSection;

    TextInputEditText project_name;
    EditText proDetail, moneyDesc;
    RadioGroup moneyGroup, genderGroup, placeGroup;

    ImageView arrowContributors, arrowMoney, arrowPlace;
    EditText ed_profitMoney, ed_initialCost, ed_directCost, ed_IndirectCost, ed_proDuration, ed_contributers;
    Spinner sp_durationType, sp_profitMoney, /*sp_initialCost,*/ sp_directCost, sp_IndirectCost, sp_proType;

    static ViewPager pager;
    static FragmentPagerAdapter pagerAdapter;
    Realm realm;

    private static MutableLiveData<OfferDetails> mLoadedProjectWithAllDataLiveData = null;

    static FragmentOffer1 setPager(ViewPager viewPager, FragmentPagerAdapter pagerAdapte, MutableLiveData<OfferDetails> loadedProjectWithAllDataLiveData) {
        pager = viewPager;
        pagerAdapter = pagerAdapte;
        mLoadedProjectWithAllDataLiveData = loadedProjectWithAllDataLiveData;
        return new FragmentOffer1();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1_add_project, container, false);
        realm = Realm.getDefaultInstance();
        /** Shrink and Expand Views */
        place = view.findViewById(R.id.place);
        money = view.findViewById(R.id.money);
        contributors = view.findViewById(R.id.contributors);
        moneySection = view.findViewById(R.id.moneySection);
        placeSection = view.findViewById(R.id.placeSection);
        contributorsSection = view.findViewById(R.id.contributorsSection);
        arrowMoney = view.findViewById(R.id.arrowMoney);
        arrowPlace = view.findViewById(R.id.arrowPlace);
        arrowContributors = view.findViewById(R.id.arrowContributors);
        /** Input Views */

        user_image = view.findViewById(R.id.user_image);
        placeGroup = view.findViewById(R.id.placeGroup);
        project_name = view.findViewById(R.id.project_name);
        proDetail = view.findViewById(R.id.proDetail);
//        moneyDesc = view.findViewById(R.id.moneyDesc);
        ed_profitMoney = view.findViewById(R.id.ed_profitMoney);
        ed_initialCost = view.findViewById(R.id.ed_initialCost);
        ed_directCost = view.findViewById(R.id.ed_directCost);
        ed_IndirectCost = view.findViewById(R.id.ed_IndirectCost);
        ed_proDuration = view.findViewById(R.id.ed_proDuration);
        sp_durationType = view.findViewById(R.id.sp_durationType);
        ed_contributers = view.findViewById(R.id.ed_contributers);

        sp_proType = view.findViewById(R.id.sp_proType);
        sp_profitMoney = view.findViewById(R.id.sp_profitMoney);
//        sp_initialCost = view.findViewById(R.id.sp_initialCost);
        sp_directCost = view.findViewById(R.id.sp_directCost);
        sp_IndirectCost = view.findViewById(R.id.sp_IndirectCost);

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

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {
        super.onStart();

        ((DrawerActivity) getActivity()).hideSearchBar();
        ((DrawerActivity) getActivity()).hideFab();

        realm.executeTransaction( realm1 -> {
            String image = realm1.where(LoginDataBase.class).findFirst().getUser().getImage();
            if (false){ // founded in device

            }else {
                if (!image.startsWith("http"))
                Picasso.get().load(API.BASE_URL+image).into(user_image);
                else {
                    Picasso.get().load(image).into(user_image);
                }
            }
        });

        validate_Money();
        validate_Type();
        proDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().matches("[^&%$#@!~]*")){
                    proDetail.setError(getString(R.string.noSpecailChars));
                    Offers.setDescription(s.toString().replaceAll("[^&%$#@!~]*","•"));
                }else {
                    Offers.setDescription(proDetail.getText().toString());
                }
            }
        });

        project_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().matches("[^&%$#@!~]*")){
                    project_name.setError(getString(R.string.noSpecailChars));
                    Offers.setName(s.toString().replaceAll("[^&%$#@!~]*","•"));
                }else {
                    Offers.setName(s.toString());
                }
            }
        });

        moneyGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.day) {
                Offers.setProfitType(0);
            } else if (checkedId == R.id.month) {
                Offers.setProfitType(1);
            } else if (checkedId == R.id.year) {
                Offers.setProfitType(2);
            } else if (checkedId == R.id.other) {
                Offers.setProfitType(3);
            }
        });

        genderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.male) {
                Offers.setGenderContributor(0);
            } else if (checkedId == R.id.female) {
                Offers.setGenderContributor(1);
            } else if (checkedId == R.id.both) {
                Offers.setGenderContributor(2);
            }
        });

        //region Shrink And Expand

        money.setOnClickListener(v -> {
            if (moneySection.getVisibility() == View.VISIBLE) {
                moneySection.setVisibility(View.GONE);
                arrowMoney.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);

            } else {
                moneySection.setVisibility(View.VISIBLE);
                arrowMoney.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            }
        });

        place.setOnClickListener(v -> {
            if (placeSection.getVisibility() == View.VISIBLE) {
                placeSection.setVisibility(View.GONE);
                arrowPlace.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);

            } else {
                placeSection.setVisibility(View.VISIBLE);
                arrowPlace.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            }
        });

        contributors.setOnClickListener(v -> {
            if (contributorsSection.getVisibility() == View.VISIBLE) {
                contributorsSection.setVisibility(View.GONE);
                arrowContributors.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);

            } else {
                contributorsSection.setVisibility(View.VISIBLE);
                arrowContributors.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            }
        });

        //endregion

        if (mLoadedProjectWithAllDataLiveData != null) {
            mLoadedProjectWithAllDataLiveData.observe(this, offers -> {
                if (offers != null) {
                    project_name.setText(offers.getName());
                    proDetail.setText(offers.getDescription());
                    switch (offers.getProfitType()) {
                        case 0:
                            moneyGroup.check(R.id.day);
                            break;
                        case 1:
                            moneyGroup.check(R.id.month);
                            break;
                        case 2:
                            moneyGroup.check(R.id.year);
                            break;
                        case 3:
                            moneyGroup.check(R.id.other);
                            break;
                    }

                    switch (offers.getGenderContributor()) {
                        case 0:
                            genderGroup.check(R.id.male);
                            break;
                        case 1:
                            genderGroup.check(R.id.female);
                            break;
                        case 2:
                            genderGroup.check(R.id.both);
                            break;
                    }

                }
            });
        }

    }

    private void validate_Type() {
        sp_profitMoney.setDropDownWidth(200);
        sp_proType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Offers.setProjectType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_IndirectCost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Offers.setIndectExpensesType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_directCost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        sp_durationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Offers.setProjectDurationType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_profitMoney.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        ed_contributers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ed_contributers.removeTextChangedListener(this);

                try {
                    if (s.toString().isEmpty()) {
                        Offers.setNumContributor(1);
                        ed_contributers.setText("1");
                    } else {
                        Offers.setNumContributor(Integer.parseInt(s.toString()));
                    }
                    String givenstring = s.toString();
                    Long longval;
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replaceAll(",", "");
                    }
                    longval = Long.parseLong(givenstring);
                    DecimalFormat formatter = new DecimalFormat("#,###,###,###,###,###,###,###,###,###,###");
                    String formattedString = formatter.format(longval);
                    ed_contributers.setText(formattedString);
                    ed_contributers.setSelection(ed_contributers.getText().length());
                    // to place the cursor at the end of text
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ed_contributers.addTextChangedListener(this);
            }
        });

        ed_proDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()|| Integer.parseInt(s.toString()) < 1) {
                    Offers.setProjectDuration(1f);
                    ed_proDuration.setText("1");
                } else {
                    Offers.setProjectDuration(Float.parseFloat(s.toString()));
                }

            }
        });

        ed_IndirectCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ed_IndirectCost.removeTextChangedListener(this);

                try {
                    if (s.toString().isEmpty()) {
                        Offers.setIndectExpenses(0f);
                        ed_IndirectCost.setText("0");
                    } else {
                        Offers.setIndectExpenses(Float.parseFloat(s.toString()));
                    }
                    String givenstring = s.toString();
                    Long longval;
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replaceAll(",", "");
                    }
                    longval = Long.parseLong(givenstring);
                    DecimalFormat formatter = new DecimalFormat("#,###,###,###,###,###,###,###,###,###,###");
                    String formattedString = formatter.format(longval);
                    ed_IndirectCost.setText(formattedString);
                    ed_IndirectCost.setSelection(ed_IndirectCost.getText().length());
                    // to place the cursor at the end of text
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ed_IndirectCost.addTextChangedListener(this);
            }
        });

        ed_directCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ed_directCost.removeTextChangedListener(this);

                try {
                    if (s.toString().isEmpty() || Integer.parseInt(s.toString()) < 1000) {
                        Offers.setDirectExpenses(1000f);
                        ed_directCost.setText("1000");
                    } else {
                        Offers.setDirectExpenses(Float.parseFloat(s.toString()));
                    }
                    String givenstring = s.toString();
                    Long longval;
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replaceAll(",", "");
                    }
                    longval = Long.parseLong(givenstring);
                    DecimalFormat formatter = new DecimalFormat("#,###,###,###,###,###,###,###,###,###,###");
                    String formattedString = formatter.format(longval);
                    ed_directCost.setText(formattedString);
                    ed_directCost.setSelection(ed_directCost.getText().length());
                    // to place the cursor at the end of text
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ed_directCost.addTextChangedListener(this);
            }
        });

        ed_initialCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ed_initialCost.removeTextChangedListener(this);

                try {
                    if (s.toString().isEmpty()  || Integer.parseInt(s.toString()) < 15000) {
                        Offers.setInitialCost(15000f);
                        ed_initialCost.setText("15000");
                    } else {
                        Offers.setInitialCost(Float.parseFloat(s.toString()));
                    }
                    String givenstring = s.toString();
                    Long longval;
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replaceAll(",", "");
                    }
                    longval = Long.parseLong(givenstring);
                    DecimalFormat formatter = new DecimalFormat("#,###,###,###,###,###,###,###,###,###,###");
                    String formattedString = formatter.format(longval);
                    ed_initialCost.setText(formattedString);
                    ed_initialCost.setSelection(ed_initialCost.getText().length());
                    // to place the cursor at the end of text
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ed_initialCost.addTextChangedListener(this);
            }
        });

        ed_profitMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ed_profitMoney.removeTextChangedListener(this);

                try {
                    if (s.toString().isEmpty()  || Integer.parseInt(s.toString()) < 5000)  {
                        Offers.setProfitMoney(5000f);
                        ed_profitMoney.setText("5000");
                    } else {
                        Offers.setProfitMoney(Float.parseFloat(s.toString()));
                    }
                    String givenstring = s.toString();
                    Long longval;
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replaceAll(",", "");
                    }
                    longval = Long.parseLong(givenstring);
                    DecimalFormat formatter = new DecimalFormat("#,###,###,###,###,###,###,###,###,###,###");
                    String formattedString = formatter.format(longval);
                    ed_profitMoney.setText(formattedString);
                    ed_profitMoney.setSelection(ed_profitMoney.getText().length());
                    // to place the cursor at the end of text
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ed_profitMoney.addTextChangedListener(this);
            }
        });


    }

}
