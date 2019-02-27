package teamup.rivile.com.teamup.Department;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.AttachmentModel;


public class FragmentHome extends Fragment {

    // All Category
    GridView gridView;
    DepartmentAdapter departmentAdapter;
    List<Department> categories;
    View view;

    FragmentManager fragmentManager;
    static String Word;

    public static FragmentHome setWord(String word) {
        Word = word;
        return new FragmentHome();
    }

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
    public void onConfigurationChanged(Configuration newConfig) {
//        gridView.setNumColumns(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStart() {
        Toast.makeText(getContext(), "FragmentHome", Toast.LENGTH_SHORT).show();//todo

        super.onStart();
        ((DrawerActivity) getActivity()).Show("Home");
        ((DrawerActivity) getActivity()).ShowFab();
        loadOffers();

    }

    private void loadOffers() {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);

        ApiConfig getDepartments = appConfig.getRetrofit().create(ApiConfig.class);
        Call<DepartmentJson> call = getDepartments.getCategory(API.URL_TOKEN);
        call.enqueue(new Callback<DepartmentJson>() {
            @Override
            public void onResponse(Call<DepartmentJson> call, retrofit2.Response<DepartmentJson> response) {
                if (response.body() != null) {
                    if (response.body().getCategory() != null) {
                        List<Department> serverResponse = response.body().getCategory();
                        fillOffers(serverResponse);
                    } else {
                        Toast.makeText(getContext(), "Failed To Load Categories.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed To Load Categories.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DepartmentJson> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillOffers(List<Department> categories) {
        departmentAdapter = new DepartmentAdapter(getActivity(), categories);
        gridView.setAdapter(departmentAdapter);
    }
}
