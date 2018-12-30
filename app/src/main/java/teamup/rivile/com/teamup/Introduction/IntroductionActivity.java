package teamup.rivile.com.teamup.Introduction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.badoualy.stepperindicator.StepperIndicator;

import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.R;

public class IntroductionActivity extends AppCompatActivity {
    private ViewPager mPager;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //if user saw the introduction before, skip it
        SharedPreferences preferences = getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
        if (preferences.getBoolean(getString(R.string.saw_intro_preference_key), false)) {
            Intent intent = new Intent(IntroductionActivity.this, DrawerActivity.class);
            startActivity(intent);

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        mPager = findViewById(R.id.pager);

        final StepperIndicator indicator = findViewById(R.id.indicator);

        PagerAdapter mPagerAdapter = new IntroductionAdapter(getSupportFragmentManager(), indicator);
        mPager.setAdapter(mPagerAdapter);

        indicator.setViewPager(mPager, mPagerAdapter.getCount());
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
}
