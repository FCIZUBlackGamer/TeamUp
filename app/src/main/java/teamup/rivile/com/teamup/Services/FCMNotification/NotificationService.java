package teamup.rivile.com.teamup.Services.FCMNotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.realm.Realm;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.NotificationData;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.NotificationDatabase;
import teamup.rivile.com.teamup.ui.FirstActivity;

public class NotificationService extends FirebaseMessagingService {
    private final String CHANNEL_ID = "TEAMUPNOTIFICATIONCHANNELID";
    private int mNotificationId = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        NotificationData data = createNotificationDataFromRemoteMessage(remoteMessage);

        String notificationMessage = createNotificationMessageFromData(data);
        if (notificationMessage != null) pushNotification(notificationMessage);
    }

    private NotificationData createNotificationDataFromRemoteMessage(RemoteMessage remoteMessage) {
        Map<String, String> dataMap = remoteMessage.getData();

        String targetUserId = dataMap.get(API.NotificationDataKey.TARGET_USER_ID_KEY);
        if (targetUserId == null) targetUserId = "-1";

        String notificationType = dataMap.get(API.NotificationDataKey.NOTIFICATION_TYPE_KEY);
        if (notificationType == null) notificationType = "-1";

        String userName = dataMap.get(API.NotificationDataKey.USER_NAME_KEY);

        String projectName = dataMap.get(API.NotificationDataKey.PROJECT_NAME_KEY);

        return new NotificationData(Integer.valueOf(targetUserId),
                Integer.valueOf(notificationType),
                userName,
                projectName
        );
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

    @Nullable
    private String createNotificationMessageFromData(@NonNull NotificationData data) {
        switch (data.getNotificationType()) {
            case API.NotificationType.TYPE_JOIN:
                return createJoinNotificationMessage(data);
            case API.NotificationType.TYPE_EDIT:
                return createEditNotificationMessage(data);
            case API.NotificationType.TYPE_LIKE:
                return createLikeNotificationMessage(data);
            case API.NotificationType.TYPE_DELETE:
                return createDeleteNotificationMessage(data);
            case API.NotificationType.TYPE_ACCEPT:
                return createAcceptNotificationMessage(data);
            case API.NotificationType.TYPE_REFUSE:
                return createRefuseNotificationMessage(data);
        }

        return null;
    }

    @NonNull
    private String createJoinNotificationMessage(NotificationData data) {
        return createNotificationMessage(data, getString(R.string.request_to_join_project));
    }

    @NonNull
    private String createEditNotificationMessage(NotificationData data) {
        return createNotificationMessage(data, getString(R.string.edited_your_project));
    }

    @NonNull
    private String createLikeNotificationMessage(NotificationData data) {
        return createNotificationMessage(data, getString(R.string.liked_your_project));
    }

    @NonNull
    private String createDeleteNotificationMessage(NotificationData data) {
        return createNotificationMessage(data, getString(R.string.deleted_your_project));
    }

    @NonNull
    private String createAcceptNotificationMessage(NotificationData data) {
        return createNotificationMessage(data, getString(R.string.accepted_your_request_to_join_his_project));
    }

    @NonNull
    private String createRefuseNotificationMessage(NotificationData data) {
        return createNotificationMessage(data, getString(R.string.refused_your_request_to_join_his_project));
    }

    @NonNull
    private String createNotificationMessage(@NonNull NotificationData data, @NonNull String message) {
        return data.getUserName() + " " + message + " (" + data.getProjectName() + ").";
    }

    private void pushNotification(String text) {
        createNotificationChannel();

        Intent intent = new Intent(this, FirstActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(text)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(mNotificationId++, builder.build());
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
