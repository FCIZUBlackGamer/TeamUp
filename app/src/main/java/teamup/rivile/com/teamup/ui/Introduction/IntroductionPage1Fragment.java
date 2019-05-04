package teamup.rivile.com.teamup.ui.Introduction;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamup.rivile.com.teamup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroductionPage1Fragment extends Fragment {


    public IntroductionPage1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_introduction_page1, container, false);
    }

}
