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
import teamup.rivile.com.teamup.network.repository.AppNetworkRepository;
import teamup.rivile.com.teamup.network.retrofit.RetrofitConfigurations;
import teamup.rivile.com.teamup.network.retrofit.RetrofitMethods;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.JoinedProjectRealmObject;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.LoginDataBase;

public class ListJoinedProjectsFragment extends Fragment {
    private ConstraintLayout mLoadingViewConstraintLayout;
    private JoinedProjectsAdapter mProjectsAdapter;
    private ConstraintLayout mEmptyLayout;

    private Context mContext;

    private Realm mRealm;
    private int mUserId = -1;

    private AppNetworkRepository mNetworkRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_joined_projects, container, false);

        mLoadingViewConstraintLayout = view.findViewById(R.id.cl_loading);

        mContext = getContext();

        mNetworkRepository = AppNetworkRepository.getInstance(getActivity().getApplication());

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

        mNetworkRepository.listJoinedProjects(mUserId)
                .observe(this, joinedProjectList -> {
                            if (joinedProjectList != null) {
                                if (joinedProjectList.size() == 0) {
                                    mEmptyLayout.setVisibility(View.VISIBLE);
                                } else {
                                    mEmptyLayout.setVisibility(View.GONE);
                                    mProjectsAdapter.swapData(joinedProjectList);

                                    mRealm.executeTransaction(realm -> {
                                        realm.where(JoinedProjectRealmObject.class).findAll().deleteAllFromRealm();
                                        for (JoinedProjectRealmObject project : joinedProjectList) {
                                            realm.insert(project);
                                        }
                                    });
                                }
                            } else {
                                mEmptyLayout.setVisibility(View.VISIBLE);
                            }

                            mLoadingViewConstraintLayout.setVisibility(View.GONE);
                        }
                );
    }
}