package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmObject;

public class JoinedOfferIdRealmModel extends RealmObject {
    private int id;

    public JoinedOfferIdRealmModel(int id) {
        this.id = id;
    }

    public JoinedOfferIdRealmModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
