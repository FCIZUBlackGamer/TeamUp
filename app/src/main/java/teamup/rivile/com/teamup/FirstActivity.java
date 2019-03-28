package teamup.rivile.com.teamup;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import teamup.rivile.com.teamup.Services.BroadcastNotificationReceiver;
import teamup.rivile.com.teamup.Services.MyService;
import teamup.rivile.com.teamup.Services.NotificationService;

public class FirstActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
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

        /** Launch the service for calling the API */
        Intent intent = new Intent(FirstActivity.this, MyService.class);
        startService(intent);

        setContentView(R.layout.activity_first);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.first, new Login()).commit();
    }
}
