package teamup.rivile.com.teamup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.realm.Realm;
import teamup.rivile.com.teamup.Services.BroadcastNotificationReceiver;
import teamup.rivile.com.teamup.Services.MyService;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.Settings;

public class FirstActivity extends AppCompatActivity {
    Realm realm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Settings settings = new Settings();
            settings.setNotificaionStatus(true);
            realm1.insertOrUpdate(settings);


            /** Launch the service for calling the API */
            Intent broadcastIntent = new Intent(this, BroadcastNotificationReceiver.class);
            sendBroadcast(broadcastIntent);

            Intent intent = new Intent(FirstActivity.this, MyService.class);
            startService(intent);
        });

        realm.executeTransaction(realm1 -> {
            Log.e("Not", String.valueOf(realm1.where(Settings.class).findFirst().isNotificaionStatus()));
        });
//        /**
//         *  we will create a service to be alive
//         * calling API every 5 Min
//         * and if the result is positive then
//         * it will call the BroadcastReceiver to walk him up
//         * and broadcastReceiver will make the notifications directly
//         * */
//
//        /** create BroadcastReceiver for notifications */
//        BroadcastReceiver broadcastReceiver = new BroadcastNotificationReceiver();
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        this.registerReceiver(broadcastReceiver, filter);



        setContentView(R.layout.activity_first);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.first, new Login()).commit();
    }
}
