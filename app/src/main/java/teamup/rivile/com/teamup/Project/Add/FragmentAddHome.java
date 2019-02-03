package teamup.rivile.com.teamup.Project.Add;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel;

public class FragmentAddHome extends Fragment {

    static FloatingActionButton fab;
    public static FragmentAddHome setFab(FloatingActionButton view){
        fab = view;
        return new FragmentAddHome();
    }
    View view;
    //FragmentManager fragmentManager;
    ViewPager viewPager = null;
    FragmentPagerAdapter pagerAdapter;
    //FragmentTransaction d;

    private Offers offer = null;
    private RequirmentModel requirmentModel = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_home, container, false);
        //fragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        viewPager = (ViewPager) view.findViewById(R.id.offer_pager);
        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStart() {
        super.onStart();

        //d = fragmentManager.beginTransaction();

        fab.setVisibility(View.GONE);
        pagerAdapter = new pager(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);


    }

    class pager extends FragmentPagerAdapter {

        public pager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentOffer1().setPager(viewPager, pagerAdapter, offer, requirmentModel);
                //d.commitNow();
            } else if (position == 1) {
                fragment = new FragmentOffer2().setPager(viewPager, pagerAdapter, offer, requirmentModel);
                //d.commitNow();
            } else if (position == 2) {
                fragment = new FragmentOffer3().setPager(viewPager, pagerAdapter, offer, requirmentModel);
                //d.commitNow();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
