package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmObject;

public class JoinedOfferRealmModel extends RealmObject {
   private JoinedProject joinedProject;

    public JoinedOfferRealmModel(JoinedProject joinedProject) {
        this.joinedProject = joinedProject;
    }

    public JoinedOfferRealmModel() {
    }

    public JoinedProject getJoinedProject() {
        return joinedProject;
    }

    public void setJoinedProject(JoinedProject joinedProject) {
        this.joinedProject = joinedProject;
    }
}
