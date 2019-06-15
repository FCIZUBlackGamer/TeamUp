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
import teamup.rivile.com.teamup.Uitls.InternalDatabase.JoinedProjectRealmObject;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.NotificationDatabase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.UserDataBase;
import teamup.rivile.com.teamup.ui.DrawerActivity;
import teamup.rivile.com.teamup.ui.FirstActivity;

import static teamup.rivile.com.teamup.APIS.API.NotificationType.*;

public class NotificationService extends FirebaseMessagingService {
    private final String CHANNEL_ID = "TEAMUPNOTIFICATIONCHANNELID";
    private int mNotificationId = 0;
    private int mNotificationType = -1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        NotificationData data = createNotificationDataFromRemoteMessage(remoteMessage);

        if (userLoggedIn(data.getTargetUserId())) {
            String notificationMessage = createNotificationMessageFromData(data);
            if (notificationMessage != null) pushNotification(notificationMessage);
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Realm.init(this);

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(realm1 -> {
            NotificationDatabase database = realm1.where(NotificationDatabase.class).findFirst();
            if (database == null) realm1.insert(new NotificationDatabase(s, true));
            else database.setDevice_FCM_Token(s);
        });
    }

    private boolean userLoggedIn(int targetUserId) {
        Realm.init(this);

        Realm realm = Realm.getDefaultInstance();
        LoginDataBase loginDataBase = realm.where(LoginDataBase.class).findFirst();
        if (loginDataBase != null) {
            UserDataBase userDataBase = loginDataBase.getUser();
            if (userDataBase != null) {
                int loggedUserId = userDataBase.getId();
                return loggedUserId == targetUserId;
            }
        }

        return false;
    }

    private NotificationData createNotificationDataFromRemoteMessage(RemoteMessage remoteMessage) {
        Map<String, String> dataMap = remoteMessage.getData();

        String targetUserId = dataMap.get(API.NotificationDataKey.TARGET_USER_ID_KEY);
        if (targetUserId == null) targetUserId = "-1";

        String notificationType = dataMap.get(API.NotificationDataKey.NOTIFICATION_TYPE_KEY);
        if (notificationType == null) notificationType = "-1";

        String projectId = dataMap.get(API.NotificationDataKey.PROJECT_ID_KEY);
        if (projectId == null) projectId = "-1";

        String userName = dataMap.get(API.NotificationDataKey.USER_NAME_KEY);

        String projectName = dataMap.get(API.NotificationDataKey.PROJECT_NAME_KEY);

        return new NotificationData(Integer.valueOf(targetUserId),
                Integer.valueOf(notificationType),
                Integer.valueOf(projectId),
                userName,
                projectName
        );
    }

    @Nullable
    private String createNotificationMessageFromData(@NonNull NotificationData data) {
        String bodyMessage;
        switch (data.getNotificationType()) {
            case TYPE_JOIN:
                bodyMessage = getString(R.string.request_to_join_project);
                mNotificationType = TYPE_JOIN;
                break;
            case TYPE_EDIT:
                bodyMessage = getString(R.string.edited_your_project);
                mNotificationType = TYPE_EDIT;
                break;
            case TYPE_LIKE:
                bodyMessage = getString(R.string.liked_your_project);
                mNotificationType = TYPE_LIKE;
                break;
            case TYPE_DELETE:
                bodyMessage = getString(R.string.deleted_your_project);
                mNotificationType = TYPE_DELETE;
                break;
            case TYPE_ACCEPT:
                bodyMessage = getString(R.string.accepted_your_request_to_join_his_project);
                mNotificationType = TYPE_ACCEPT;
                break;
            case TYPE_REFUSE:
                bodyMessage = getString(R.string.refused_your_request_to_join_his_project);
                mNotificationType = TYPE_REFUSE;
                allowUserToRejoin(data);
                break;
            default:
                bodyMessage = null;
                mNotificationType = -1;
        }

        return createNotificationMessage(data, bodyMessage);
    }

    @Nullable
    private String createNotificationMessage(@NonNull NotificationData data, @Nullable String message) {
        return message == null ? null : data.getUserName() + " " + message + " (" + data.getProjectName() + ").";
    }

    private void allowUserToRejoin(NotificationData data) {
        Realm.init(this);

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            JoinedProjectRealmObject joinedProject =
                    realm.where(JoinedProjectRealmObject.class)
                            .equalTo("OfferId", data.getProjectId())
                            .findAll().last();

            if (joinedProject != null) {
                joinedProject.setStatus(0);
                realm1.insertOrUpdate(joinedProject);
            }
        });
    }

    private void pushNotification(String text) {
        createNotificationChannel();

        PendingIntent pendingIntent = createNotificationPendingIntent();

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

    private PendingIntent createNotificationPendingIntent() {
        Intent intent = new Intent(this, DrawerActivity.class);
        intent.putExtra(getString(R.string.notification_type_key), mNotificationType);

        return PendingIntent.getActivity(
                this,
                mNotificationId,
                intent,
                PendingIntent.FLAG_ONE_SHOT
        );
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