package teamup.rivile.com.teamup.ui.projectsJoinRequests;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.JoinedProject;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.JoinedOfferIdRealmModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.ui.DrawerActivity;

public class ListJoinedProjectsFragment extends Fragment {
    private ConstraintLayout mLoadingViewConstraintLayout;
    private JoinedProjectsAdapter mProjectsAdapter;
    private ConstraintLayout mEmptyLayout;

    private Context mContext;

    private Realm mRealm;
    private int mUserId = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_joined_projects, container, false);

        mLoadingViewConstraintLayout = view.findViewById(R.id.cl_loading);

        mContext = getContext();

        ((DrawerActivity) getActivity()).hideFab();

        mRealm = Realm.getDefaultInstance();
        LoginDataBase loginData = mRealm.where(LoginDataBase.class)
                .findFirst();

        if (loginData != null && loginData.getUser() != null) {
            mUserId = loginData.getUser().getId();
        }

        mEmptyLayout = view.findViewById(R.id.cl_emptyView);

        RecyclerView joinedProjectsRecyclerView = view.findViewById(R.id.recyclerView);
        joinedProjectsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mProjectsAdapter = new JoinedProjectsAdapter(mContext);
        joinedProjectsRecyclerView.setAdapter(mProjectsAdapter);

        loadJoinedProjects();

        return view;
    }

    private void loadJoinedProjects() {
        mLoadingViewConstraintLayout.setVisibility(View.VISIBLE);

        RetrofitMethods retrofitMethods = new RetrofitConfigurations(API.BASE_URL).
                getRetrofit().
                create(RetrofitMethods.class);

        Call<List<JoinedProject>> call = retrofitMethods.listJoinedProjects(mUserId, API.URL_TOKEN);

        call.enqueue(new Callback<List<JoinedProject>>() {
            @Override
            public void onResponse(Call<List<JoinedProject>> call, Response<List<JoinedProject>> response) {
                List<JoinedProject> joinedProjectList = response.body();
                if (joinedProjectList != null) {
                    if (joinedProjectList.size() == 0) {
                        mEmptyLayout.setVisibility(View.VISIBLE);
                    } else {
                        mEmptyLayout.setVisibility(View.GONE);
                        mProjectsAdapter.swapData(joinedProjectList);

                        mRealm.executeTransaction(realm -> {
                            realm.where(JoinedOfferIdRealmModel.class).findAll().deleteAllFromRealm();
                            for (JoinedProject project : joinedProjectList) {
                                realm.insert(new JoinedOfferIdRealmModel(project.getOfferId()));
                            }
                        });
                    }
                } else {
                    Toast.makeText(mContext, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    mEmptyLayout.setVisibility(View.VISIBLE);
                }

                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<JoinedProject>> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                mEmptyLayout.setVisibility(View.VISIBLE);

                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }
        });
    }
}