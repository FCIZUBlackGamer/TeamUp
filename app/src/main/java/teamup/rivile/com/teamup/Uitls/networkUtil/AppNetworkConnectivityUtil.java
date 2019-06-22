package teamup.rivile.com.teamup.Uitls.networkUtil;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppNetworkConnectivityUtil {
    private AppNetworkConnectivityUtil() {
    }

    private static ConnectivityManager mConnectivityManager;

    private static void init(Application application) {
        if (mConnectivityManager == null)
            mConnectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static boolean isDeviceConnectedToTheInternet(Application application) {
        init(application);

        NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
