package teamup.rivile.com.teamup.ui.Loading;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;

import teamup.rivile.com.teamup.R;

public class ShowSpinnerTask extends AsyncTask<Void, Void, Void> {
    SpinnerFragment mSpinnerFragment;
    static FragmentManager fragmentManager;

    public static AsyncTask getManager(FragmentManager fragmentManage) {
        fragmentManager = fragmentManage;
        return new ShowSpinnerTask().execute();
    }

    @Override
    protected void onPreExecute() {
        mSpinnerFragment = new SpinnerFragment();
        fragmentManager.beginTransaction().add(R.id.frame, mSpinnerFragment).commit();
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Do some background process here.
        // It just waits 5 sec in this Tutorial
        SystemClock.sleep(1000); //TODO : احنا هنهزر 5555555555555
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            fragmentManager.beginTransaction().remove(mSpinnerFragment).commit();
        } catch (Exception e) {

        }
    }
}