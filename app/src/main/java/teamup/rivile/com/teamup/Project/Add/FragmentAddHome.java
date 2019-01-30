package teamup.rivile.com.teamup.Project.Add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamup.rivile.com.teamup.R;

public class FragmentAddHome extends Fragment {

    View view;
    //FragmentManager fragmentManager;
    ViewPager viewPager = null;
    //FragmentTransaction d;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_home, container, false);
        //fragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        viewPager = (ViewPager) view.findViewById(R.id.offer_pager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //d = fragmentManager.beginTransaction();

            viewPager.setAdapter(new pager(getChildFragmentManager()));


    }

    class pager extends FragmentPagerAdapter {

        public pager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentOffer1();
                //d.commitNow();
            } else if (position == 1) {
                fragment = new FragmentOffer2();
                //d.commitNow();
            } else if (position == 2) {
                fragment = new FragmentOffer3();
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
