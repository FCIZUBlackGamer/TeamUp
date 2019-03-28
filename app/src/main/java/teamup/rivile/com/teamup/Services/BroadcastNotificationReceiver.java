package teamup.rivile.com.teamup.Services;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.realm.Realm;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.Settings;

public class BroadcastNotificationReceiver extends BroadcastReceiver {

    Context contex;
    BroadcastNotificationReceiver(){
        realm = Realm.getDefaultInstance();
    }

    Realm realm;
    @Override
    public void onReceive(Context context, Intent intent) {
        contex = context;
        Settings settings = realm.where(Settings.class).findFirst();
        if (settings.isNotificaionStatus()){
            if (!isServiceRunning(MyService.class)){
                contex.startService(new Intent(contex, MyService.class));
            }
        }
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
