package teamup.rivile.com.teamup.Search;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import teamup.rivile.com.teamup.Department.DepartmentJson;
import teamup.rivile.com.teamup.R;

public class FilterSearchFragment extends Fragment {
    private EditText mProjectNameEditText;
    private StepperIndicator mEducationLevelStepperIndicator;
    private RadioGroup mGenderRadioGroup;
    private EditText mAddressEditText;
    private RangeSeekBar mAgeRangeSeekBar;
    private RecyclerView mDepartmentsRecyclerView;
    private RangeSeekBar mProjectCostRangeSeekBar;
    private RangeSeekBar mContributorsNumberRangeSeekBar;
    private RangeSeekBar mContributorExperienceRangeSeekBar;
    private Button mGoButton;

    private DepartmentsAdapter mDepartmentsAdapter;
    private MutableLiveData<List<Department>> mLoadedDepartments = new MutableLiveData<>();
    private List<Department> mSelectedDepartments = null;

    public FilterSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProjectNameEditText = view.findViewById(R.id.project_name);
        mEducationLevelStepperIndicator = view.findViewById(R.id.educationLevel);
        mGenderRadioGroup = view.findViewById(R.id.genderGroup);
        mAddressEditText = view.findViewById(R.id.address);
        mAgeRangeSeekBar = view.findViewById(R.id.ageSeekbar);

        mDepartmentsRecyclerView = view.findViewById(R.id.recDep);
        mDepartmentsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mProjectCostRangeSeekBar = view.findViewById(R.id.costSeekbar);
        mContributorsNumberRangeSeekBar = view.findViewById(R.id.numberContributersSeekbar);
        mContributorExperienceRangeSeekBar = view.findViewById(R.id.experienceSeekbar);
        mGoButton = view.findViewById(R.id.go);
    }

    @Override
    public void onStart() {
        super.onStart();

        loadCategories();

        mDepartmentsAdapter = new DepartmentsAdapter(null, (ArrayList<Department>) mSelectedDepartments);
        mDepartmentsRecyclerView.setAdapter(mDepartmentsAdapter);
        mLoadedDepartments.observe(this, departments -> mDepartmentsAdapter.swapData(departments));

        mGoButton.setOnClickListener(v -> {
            mSelectedDepartments = mDepartmentsAdapter.getSelectedDepartments();
            if(mSelectedDepartments.isEmpty()){
                Toast.makeText(getContext(), "إختر قسم واحد علي الاقل", Toast.LENGTH_SHORT).show();
            }else {
                //TODO: write your code here
            }
        });
    }

    private void loadCategories(){
        AppConfig appConfig = new AppConfig(API.BASE_URL);

        ApiConfig getDepartments = appConfig.getRetrofit().create(ApiConfig.class);
        Call<DepartmentJson> call = getDepartments.getCategory(API.URL_TOKEN);
        call.enqueue(new Callback<DepartmentJson>() {
            @Override
            public void onResponse(@NonNull Call<DepartmentJson> call, @NonNull retrofit2.Response<DepartmentJson> response) {
                if (response.body() != null) {
                    if (response.body().getCategory() != null) {
                        mLoadedDepartments.postValue(response.body().getCategory());
                    } else {
                        Toast.makeText(getContext(), "Failed To Load Departments.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed To Load Departments.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DepartmentJson> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
