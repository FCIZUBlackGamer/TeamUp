package teamup.rivile.com.teamup.ui.Search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.network.repository.AppNetworkRepository;
import teamup.rivile.com.teamup.network.retrofit.RetrofitMethods;
import teamup.rivile.com.teamup.network.retrofit.RetrofitConfigurations;
import teamup.rivile.com.teamup.network.APIModels.Department;
import teamup.rivile.com.teamup.ui.DrawerActivity;
import teamup.rivile.com.teamup.ui.Project.Add.Adapters.CapitalsRecyclerViewAdapter;
import teamup.rivile.com.teamup.ui.Project.List.FragmentListProjects;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.network.APIModels.CapTagCat;
import teamup.rivile.com.teamup.network.APIModels.StateModel;
import teamup.rivile.com.teamup.network.APIModels.FilterModel;

public class FilterSearchFragment extends Fragment {
    private ConstraintLayout mLoadingViewConstraintLayout;
    private EditText mProjectNameEditText;
//    private StepperIndicator mEducationLevelStepperIndicator;
    private RadioGroup mGenderRadioGroup;
    //    private EditText mAddressEditText;
//    private RangeSeekBar mAgeRangeSeekBar;
//    private TextView mAgeRangeSeekBarMin;
//    private TextView mAgeRangeSeekBarMax;

    private RecyclerView mDepartmentsRecyclerView;
    private RecyclerView mCapitalsRecyclerView;
    private RangeSeekBar mProjectCostRangeSeekBar;
    private TextView mProjectCostRangeSeekBarMin;
    private TextView mProjectCostRangeSeekBarMax;

    private RangeSeekBar mContributorsNumberRangeSeekBar;
    private TextView mContributorsNumberRangeSeekBarMin;
    private TextView mContributorsNumberRangeSeekBarMax;

//    private RangeSeekBar mContributorExperienceRangeSeekBar;
//    private TextView mContributorExperienceRangeSeekBarMin;
//    private TextView mContributorExperienceRangeSeekBarMax;

    private Button mGoButton;

    private DepartmentsAdapter mDepartmentsAdapter;
    private List<Department> mLoadedDepartments = new ArrayList<>();
    private List<StateModel> mLoadedCapitals = new ArrayList<>();
    private List<Department> mSelectedDepartments = null;

    private CheckBox mEgyptCheckBox;
    ArrayList<StateModel> mSelectedStateModels = new ArrayList<>();
    CapitalsRecyclerViewAdapter mCapitalsRecyclerViewAdapter;
    private FilterModel filterModel;
    FragmentManager fragmentManager;
    View view;

    private AppNetworkRepository mNetworkRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_filter_search, container, false);

        mNetworkRepository = AppNetworkRepository.getInstance(getActivity().getApplication());

        mLoadingViewConstraintLayout = view.findViewById(R.id.cl_loading);

        mProjectNameEditText = view.findViewById(R.id.project_name);

//        mEducationLevelStepperIndicator = view.findViewById(R.id.educationLevel);
//        mEducationLevelStepperIndicator.addOnStepClickListener(step -> mEducationLevelStepperIndicator.setCurrentStep(step));

        mGenderRadioGroup = view.findViewById(R.id.genderGroup);
//        mAddressEditText = view.findViewById(R.id.address);

//        mAgeRangeSeekBar = view.findViewById(R.id.ageSeekbar);
//        mAgeRangeSeekBar.setRangeValues(18,80);
//        mAgeRangeSeekBar.setNotifyWhileDragging(true);
//        mAgeRangeSeekBarMin = view.findViewById(R.id.tv_ageMin);
//        mAgeRangeSeekBarMax = view.findViewById(R.id.tv_ageMax);
//        mAgeRangeSeekBar.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
//            mAgeRangeSeekBarMin.setText(String.valueOf(minValue));
//            mAgeRangeSeekBarMax.setText(String.valueOf(maxValue));
//        });

        mDepartmentsRecyclerView = view.findViewById(R.id.recDep);
        mDepartmentsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mCapitalsRecyclerView = view.findViewById(R.id.recCapitals);
        mCapitalsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mEgyptCheckBox = view.findViewById(R.id.egypt);

        mProjectCostRangeSeekBar = view.findViewById(R.id.costSeekbar);
        mProjectCostRangeSeekBar.setRangeValues(0,100000);
        mProjectCostRangeSeekBar.setNotifyWhileDragging(true);
        mProjectCostRangeSeekBarMin = view.findViewById(R.id.tv_costMin);
        mProjectCostRangeSeekBarMax = view.findViewById(R.id.tv_costMax);
        mProjectCostRangeSeekBar.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
            mProjectCostRangeSeekBarMin.setText(String.valueOf(minValue));
            mProjectCostRangeSeekBarMax.setText(String.valueOf(maxValue));
        });

        mContributorsNumberRangeSeekBar = view.findViewById(R.id.numberContributersSeekbar);
        mContributorsNumberRangeSeekBar.setRangeValues(1,15);
        mContributorsNumberRangeSeekBar.setNotifyWhileDragging(true);
        mContributorsNumberRangeSeekBarMin = view.findViewById(R.id.tv_numberContributerMin);
        mContributorsNumberRangeSeekBarMax = view.findViewById(R.id.tv_numberContributerMax);
        mContributorsNumberRangeSeekBar.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
            mContributorsNumberRangeSeekBarMin.setText(String.valueOf(minValue));
            mContributorsNumberRangeSeekBarMax.setText(String.valueOf(maxValue));
        });


//        mContributorExperienceRangeSeekBar = view.findViewById(R.id.experienceSeekbar);
//        mContributorExperienceRangeSeekBar.setRangeValues(0,65);
//        mContributorExperienceRangeSeekBar.setNotifyWhileDragging(true);
//        mContributorExperienceRangeSeekBarMin = view.findViewById(R.id.tv_experienceMin);
//        mContributorExperienceRangeSeekBarMax = view.findViewById(R.id.tv_experienceMax);
//        mContributorExperienceRangeSeekBar.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
//            mContributorExperienceRangeSeekBarMin.setText(String.valueOf(minValue));
//            mContributorExperienceRangeSeekBarMax.setText(String.valueOf(maxValue));
//        });


        mGoButton = view.findViewById(R.id.go);
        if (filterModel == null) {
            filterModel = new FilterModel();
        }
        fragmentManager = getFragmentManager();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Search", "R");
        ((DrawerActivity) getActivity()).hideSearchBar();

        ((DrawerActivity) getActivity()).setTitle(getString(R.string.advancedSearch));

        loadCategoriesAndCapitals();

        mDepartmentsAdapter = new DepartmentsAdapter(null, (ArrayList<Department>) mSelectedDepartments);
        mDepartmentsRecyclerView.setAdapter(mDepartmentsAdapter);

        mCapitalsRecyclerViewAdapter = new CapitalsRecyclerViewAdapter(null, mSelectedStateModels);
        mCapitalsRecyclerView.setAdapter(mCapitalsRecyclerViewAdapter);
        mEgyptCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (mLoadedCapitals != null) {
                    mSelectedStateModels.addAll(mLoadedCapitals);
                    mCapitalsRecyclerViewAdapter.setSelectedCapitalModels(mSelectedStateModels);
                }
            } else {
                mSelectedStateModels.clear();
                mCapitalsRecyclerViewAdapter.setSelectedCapitalModels(new ArrayList<>());
            }
        });

        mGoButton.setOnClickListener(v -> {
            mSelectedDepartments = mDepartmentsAdapter.getSelectedDepartments();
            //TODO: write your code here
            filterModel.setName(mProjectNameEditText.getText().toString());
//            filterModel.setAddress(mAddressEditText.getText().toString());

            if (mCapitalsRecyclerViewAdapter.getSelectedCapitals().size() > 0) {
                List<Integer> address = new ArrayList<>();
                for (int i = 0; i < mCapitalsRecyclerViewAdapter.getSelectedCapitals().size(); i++) {
                    address.add(mCapitalsRecyclerViewAdapter.getSelectedCapitals().get(i).getId());
                }
                filterModel.setAddress(address);
            }

//            filterModel.setAgeRequiredFrom((int) mAgeRangeSeekBar.getSelectedMinValue());
//            filterModel.setAgeRequiredTo((int) mAgeRangeSeekBar.getSelectedMaxValue());
//            filterModel.setExperienceFrom((int) mContributorExperienceRangeSeekBar.getSelectedMinValue());
//            filterModel.setExperienceTo((int) mContributorExperienceRangeSeekBar.getSelectedMaxValue());
//            filterModel.setEducationContributorLevel(mEducationLevelStepperIndicator.getCurrentStep());
            filterModel.setNumContributorFrom((int) mContributorsNumberRangeSeekBar.getSelectedMinValue());
            filterModel.setNumContributorTo((int) mContributorsNumberRangeSeekBar.getSelectedMaxValue());
            filterModel.setMoneyFrom((int) mProjectCostRangeSeekBar.getSelectedMinValue());
            filterModel.setMoneyTo((int) mProjectCostRangeSeekBar.getSelectedMaxValue());
            if (mGenderRadioGroup.getCheckedRadioButtonId() == R.id.male) {
                filterModel.setGenderContributor(1);
            } else if (mGenderRadioGroup.getCheckedRadioButtonId() == R.id.female) {
                filterModel.setGenderContributor(0);
            } else {
                filterModel.setGenderContributor(2);
            }
            if (mSelectedDepartments.size() > 0) {
                List<Integer> deps = new ArrayList<>();
                for (int i = 0; i < mSelectedDepartments.size(); i++) {
                    deps.add(mSelectedDepartments.get(i).getId());
                }
                filterModel.setCategoryId(deps);
            }

            fragmentManager.beginTransaction().replace(R.id.frame, FragmentListProjects.setFilteredOffers(filterModel)).commit();
        });
    }

    private void loadCategoriesAndCapitals() {
        mLoadingViewConstraintLayout.setVisibility(View.VISIBLE);
        mNetworkRepository.getCapTagCat()
                .observe(this, response -> {
                    if (response!= null && response.getCategory() != null) {
                        mLoadingViewConstraintLayout.setVisibility(View.GONE);
                        List<Department> departments = new ArrayList<>();
                        for (int i = 0; i < response.getCategory().size(); i++) {
                            Department department = new Department();
                            department.setId(response.getCategory().get(i).getId());
                            department.setName(response.getCategory().get(i).getName());
                            departments.add(department);
                        }
                        mDepartmentsAdapter.swapData(departments);

                        mLoadedCapitals.addAll(response.getCapital());
                        mCapitalsRecyclerViewAdapter.swapData(mLoadedCapitals);
                    } else {
                        Toast.makeText(getContext(), getString(R.string.failed_to_load_departments), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
