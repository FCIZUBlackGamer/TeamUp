package teamup.rivile.com.teamup.Project.Add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import teamup.rivile.com.teamup.R;

public class FragmentOffer2 extends Fragment {
    View view;
    RelativeLayout place, experience;
    LinearLayout placeSection, experienceSection;
    int p, e;/** متغير ثابت عشان اغير حاله ال shrink وال expand*/ /** 1: Expand, 0:Shrink */

    RadioGroup placeGroup, placeKindGroup, placeStateGroup, exGroup;
    EditText placeDesc, exDesc;
    GridView exRec;
    RangeSeekBar exRequiredSeekbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2_add_project, container, false);
        p = e = 1;
        /** Shrink and Expand Views */
        place = view.findViewById(R.id.place);
        experience = view.findViewById(R.id.experience);
        placeSection = view.findViewById(R.id.placeSection);
        experienceSection = view.findViewById(R.id.experienceSection);
        /** Input Views */

        placeGroup = view.findViewById(R.id.placeGroup);
        placeKindGroup = view.findViewById(R.id.placeKindGroup);
        placeStateGroup = view.findViewById(R.id.placeStateGroup);
        exGroup = view.findViewById(R.id.exGroup);
        placeDesc = view.findViewById(R.id.placeDesc);
        exDesc = view.findViewById(R.id.exDesc);
        exRequiredSeekbar = view.findViewById(R.id.exRequiredSeekbar);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p==1){
                    p = 0;
                    placeSection.setVisibility(View.GONE);
                }else {
                    p = 1;
                    placeSection.setVisibility(View.VISIBLE);
                }
            }
        });

        experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e==1){
                    e = 0;
                    experienceSection.setVisibility(View.GONE);
                }else {
                    e = 1;
                    experienceSection.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
