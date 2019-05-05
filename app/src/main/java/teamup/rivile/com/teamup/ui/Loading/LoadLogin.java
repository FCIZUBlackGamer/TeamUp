package teamup.rivile.com.teamup.ui.Loading;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import teamup.rivile.com.teamup.R;

public class LoadLogin extends AsyncTask<Void, Void, Void> {
    SpinnerFragment mSpinnerFragment;
    static FragmentManager fragmentManager;

    public static AsyncTask getManager(FragmentManager fragmentManage){
        fragmentManager = fragmentManage;
        return new LoadLogin().execute();
    }

    @Override
    protected void onPreExecute() {
        mSpinnerFragment = new SpinnerFragment();
        fragmentManager.beginTransaction().add(R.id.first, mSpinnerFragment).commit();
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Do some background process here.
        // It just waits 5 sec in this Tutorial
        SystemClock.sleep(5000);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            fragmentManager.beginTransaction().remove(mSpinnerFragment).commit();
        }catch (Exception e){
            Log.e("EXLogin", e.getMessage());
        }
    }
}