package teamup.rivile.com.teamup.Search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.Department.Department;
import teamup.rivile.com.teamup.Project.Add.Adapters.CapitalsRecyclerViewAdapter;
import teamup.rivile.com.teamup.Project.List.FragmentListProjects;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.CapTagCat;
import teamup.rivile.com.teamup.Uitls.APIModels.CapitalModel;
import teamup.rivile.com.teamup.Uitls.APIModels.FilterModel;

public class FilterSearchFragment extends Fragment {
    private EditText mProjectNameEditText;
    private StepperIndicator mEducationLevelStepperIndicator;
    private RadioGroup mGenderRadioGroup;
//    private EditText mAddressEditText;
    private RangeSeekBar mAgeRangeSeekBar;
    private RecyclerView mDepartmentsRecyclerView;
    private RecyclerView mCapitalsRecyclerView;
    private RangeSeekBar mProjectCostRangeSeekBar;
    private RangeSeekBar mContributorsNumberRangeSeekBar;
    private RangeSeekBar mContributorExperienceRangeSeekBar;
    private Button mGoButton;

    private DepartmentsAdapter mDepartmentsAdapter;
    private List<Department> mLoadedDepartments = new ArrayList<>();
    private List<CapitalModel> mLoadedCapitals = new ArrayList<>();
    private List<Department> mSelectedDepartments = null;

    ArrayList<CapitalModel> mSelectedCapitalModels = new ArrayList<>();
    CapitalsRecyclerViewAdapter mCapitalsRecyclerViewAdapter;
    private FilterModel filterModel;
    FragmentManager fragmentManager;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_filter_search, container, false);
        mProjectNameEditText = view.findViewById(R.id.project_name);
        mEducationLevelStepperIndicator = view.findViewById(R.id.educationLevel);
        mGenderRadioGroup = view.findViewById(R.id.genderGroup);
//        mAddressEditText = view.findViewById(R.id.address);
        mAgeRangeSeekBar = view.findViewById(R.id.ageSeekbar);
        mAgeRangeSeekBar.setNotifyWhileDragging(true);

        mDepartmentsRecyclerView = view.findViewById(R.id.recDep);
        mDepartmentsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mCapitalsRecyclerView = view.findViewById(R.id.recCapitals);
        mCapitalsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mProjectCostRangeSeekBar = view.findViewById(R.id.costSeekbar);
        mProjectCostRangeSeekBar.setNotifyWhileDragging(true);

        mContributorsNumberRangeSeekBar = view.findViewById(R.id.numberContributersSeekbar);
        mContributorsNumberRangeSeekBar.setNotifyWhileDragging(true);

        mContributorExperienceRangeSeekBar = view.findViewById(R.id.experienceSeekbar);
        mContributorExperienceRangeSeekBar.setNotifyWhileDragging(true);

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

        loadCategoriesAndCapitals();


        mDepartmentsAdapter = new DepartmentsAdapter(null, (ArrayList<Department>) mSelectedDepartments);
        mDepartmentsRecyclerView.setAdapter(mDepartmentsAdapter);

        mCapitalsRecyclerViewAdapter = new CapitalsRecyclerViewAdapter(null, mSelectedCapitalModels);
        mCapitalsRecyclerView.setAdapter(mCapitalsRecyclerViewAdapter);

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

            filterModel.setAgeRequiredFrom((int) mAgeRangeSeekBar.getSelectedMinValue());
            filterModel.setAgeRequiredTo((int) mAgeRangeSeekBar.getSelectedMaxValue());
            filterModel.setExperienceFrom((int) mContributorExperienceRangeSeekBar.getSelectedMinValue());
            filterModel.setExperienceTo((int) mContributorExperienceRangeSeekBar.getSelectedMaxValue());
            filterModel.setEducationContributorLevel(mEducationLevelStepperIndicator.getCurrentStep());
            filterModel.setNumContributorFrom((int) mContributorsNumberRangeSeekBar.getSelectedMaxValue());
            filterModel.setNumContributorTo((int) mContributorsNumberRangeSeekBar.getSelectedMinValue());
            filterModel.setMoneyFrom((int) mProjectCostRangeSeekBar.getSelectedMaxValue());
            filterModel.setMoneyTo((int) mProjectCostRangeSeekBar.getSelectedMinValue());
            if (mGenderRadioGroup.getCheckedRadioButtonId() == R.id.male) {
                filterModel.setGenderContributor(1);
            } else if (mGenderRadioGroup.getCheckedRadioButtonId() == R.id.female) {
                filterModel.setGenderContributor(0);
            }else {
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
        AppConfig appConfig = new AppConfig(API.BASE_URL);

        ApiConfig getDepartments = appConfig.getRetrofit().create(ApiConfig.class);
        Call<CapTagCat> call = getDepartments.getCapTagCat(API.URL_TOKEN);
        call.enqueue(new Callback<CapTagCat>() {
            @Override
            public void onResponse(@NonNull Call<CapTagCat> call, @NonNull retrofit2.Response<CapTagCat> response) {
                if (response.body() != null) {
                    if (response.body().getCategory() != null) {
                        List<Department> departments = new ArrayList<>();
                        for (int i = 0; i < response.body().getCategory().size(); i++) {
                            Department department = new Department();
                            department.setId(response.body().getCategory().get(i).getId());
                            department.setName(response.body().getCategory().get(i).getName());
                            departments.add(department);
                        }
                        mDepartmentsAdapter.swapData(departments);

                        mCapitalsRecyclerViewAdapter.swapData(response.body().getCapital());
                    } else {
                        Toast.makeText(getContext(), "Failed To Load Departments.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed To Load Departments.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CapTagCat> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
