package teamup.rivile.com.teamup.ui.Introduction;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.badoualy.stepperindicator.StepperIndicator;

public class IntroductionAdapter extends FragmentStatePagerAdapter implements IntroductionPage3Fragment.Helper {
    private final int COUNT = 3;

    private StepperIndicator mIndicator;

    IntroductionAdapter(FragmentManager fm, StepperIndicator indicator) {
        super(fm);

        mIndicator = indicator;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IntroductionPage1Fragment();
            case 1:
                return new IntroductionPage2Fragment();
            case 2:
                IntroductionPage3Fragment fragment = new IntroductionPage3Fragment();
                fragment.setHelper(this);
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public void showIndicatorDoneAction() {
        mIndicator.setCurrentStep(3);
    }
}
