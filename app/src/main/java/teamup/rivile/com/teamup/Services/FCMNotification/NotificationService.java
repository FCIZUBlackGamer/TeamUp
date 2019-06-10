package teamup.rivile.com.teamup.Services.FCMNotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import io.realm.Realm;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.NotificationDatabase;

public class NotificationService extends FirebaseMessagingService {
    private final String CHANNEL_ID = "TEAMUPNOTIFICATIONCHANNELID";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //TODO:Check if target user id is equal to current logged in user id
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Realm.init(this);

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(realm1 -> {
            NotificationDatabase database = realm.where(NotificationDatabase.class).findFirst();
            if (database == null) realm.insert(new NotificationDatabase(s, true));
            else database.setDevice_FCM_Token(s);
        });
    }

    private void pushNotification(String title, String text) {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(100, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name) + " Notification Channel";
            String description = "NO_DESCRIPTION";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // RegisterActivity the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
