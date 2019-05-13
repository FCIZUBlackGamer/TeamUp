package teamup.rivile.com.teamup.Services;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.realm.Realm;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.Settings;

public class BroadcastNotificationReceiver extends BroadcastReceiver {

    Context contex;
    int userId;

    public BroadcastNotificationReceiver() {
        super();
    }

    Realm realm;

    @Override
    public void onReceive(Context context, Intent intent) {
        contex = context;

        Realm.init(context);
        realm = Realm.getDefaultInstance();

        realm.executeTransaction(realm1 -> {
            try {
                if (realm == null) {
                    Realm.init(context);
                    realm = Realm.getDefaultInstance();
                }
                Settings settings = realm1.where(Settings.class).findFirst();
                if (realm1.where(LoginDataBase.class).findFirst() != null && realm1.where(LoginDataBase.class).findFirst().getUser() != null) {
                    userId = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
                    if (settings != null && settings.isNotificaionStatus()) {
                        if (!isServiceRunning(MyService.class)) {
                            Intent in = new Intent(contex, MyService.class);
//                    in.putExtra("Id",userId);
                            contex.startService(in);
                        }
                    } else {
                        Log.e("GG", "Null");
                    }
                } else {
                    Log.e("GG", "So bad1");
                }

            } catch (Exception r) {

            } finally {
                if (realm != null) {
                    realm.close();
                }
            }
        });
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) contex.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}