package teamup.rivile.com.teamup.Introduction;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroductionPage3Fragment extends Fragment {

    private Helper mHelper;

    public IntroductionPage3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the view for this fragment
        ConstraintLayout view = (ConstraintLayout) inflater.inflate(R.layout.fragment_introduction_page3, container, false);

        Button doneIntroductionButton = view.findViewById(R.id.btn_introduction_done);
        doneIntroductionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.showIndicatorDoneAction();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), DrawerActivity.class);
                        startActivity(intent);

                        if (getActivity() != null) {
                            SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean(getString(R.string.saw_intro_preference_key), true).apply();

                            getActivity().finish();
                        }
                    }
                }, 300);
            }
        });

        return view;
    }

    public void setHelper(Helper helper) {
        mHelper = helper;
    }

    public interface Helper {
        void showIndicatorDoneAction();
    }
}
