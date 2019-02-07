package teamup.rivile.com.teamup.Department;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.Project.List.AdapterListOffers;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;


public class FragmentHome extends Fragment {

    // All Category
    GridView gridView;
    DepartmentAdapter departmentAdapter;
    List<Department> categories;
    View view;

    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);

        fragmentManager = getFragmentManager();
        gridView = (GridView) view.findViewById(R.id.gridview);
        categories = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).Show("Home");
        loadOffers();

    }

    private void loadOffers() {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.LOAD_DEPARTMENTS_URL);

        ApiConfig getDepartments = appConfig.getRetrofit().create(ApiConfig.class);
        Call<List<Department>> call = getDepartments.getCategory(API.URL_TOKEN);
        call.enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, retrofit2.Response<List<Department>> response) {
                List<Department> serverResponse = response.body();
                if (serverResponse != null) {
                    fillOffers(serverResponse);
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }

    private void fillOffers(List<Department> categories) {
        departmentAdapter = new DepartmentAdapter(getActivity(), categories);
        gridView.setAdapter(departmentAdapter);
    }
}
