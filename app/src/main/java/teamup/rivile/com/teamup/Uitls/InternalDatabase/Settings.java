package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmObject;

public class Settings extends RealmObject {
    private boolean notificaionStatus;

    public void setNotificaionStatus(boolean notificaionStatus) {
        this.notificaionStatus = notificaionStatus;
    }

    public boolean isNotificaionStatus() {
        return notificaionStatus;
    }
}
