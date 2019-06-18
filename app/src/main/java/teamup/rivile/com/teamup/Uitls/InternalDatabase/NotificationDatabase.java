package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import android.support.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
 * ***************************************NOTE*******************************************
 * First item of this database comes with id of 0 and holds the device firebase token.
 * **************************************************************************************
 * */
public class NotificationDatabase extends RealmObject {
    private String device_FCM_Token;
    private Boolean isActive;
    private Integer userId;

    public NotificationDatabase(String device_FCM_Token, Boolean isActive, int userId) {
        this.device_FCM_Token = device_FCM_Token;
        this.isActive = isActive;
        this.userId = userId;
    }

    public NotificationDatabase() {
    }

    public String getDevice_FCM_Token() {
        return device_FCM_Token;
    }

    public void setDevice_FCM_Token(String device_FCM_Token) {
        this.device_FCM_Token = device_FCM_Token;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @NonNull
    public static String getUserIdFieldName() {
        return "userId";
    }
}
