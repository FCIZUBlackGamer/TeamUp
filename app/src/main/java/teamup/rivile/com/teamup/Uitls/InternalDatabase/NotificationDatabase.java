package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmObject;

public class NotificationDatabase extends RealmObject {
    private String device_FCM_Token;
    private boolean isActive;

    public NotificationDatabase(String device_FCM_Token, boolean isActive) {
        this.device_FCM_Token = device_FCM_Token;
        this.isActive = isActive;
    }

    public NotificationDatabase() {
    }

    public String getDevice_FCM_Token() {
        return device_FCM_Token;
    }

    public void setDevice_FCM_Token(String device_FCM_Token) {
        this.device_FCM_Token = device_FCM_Token;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
