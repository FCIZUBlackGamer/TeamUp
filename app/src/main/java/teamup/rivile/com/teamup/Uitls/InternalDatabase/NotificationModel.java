package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmList;
import io.realm.RealmObject;

public class NotificationModel extends RealmObject {
    private Integer UserId ;
    private RealmList<Model> MyProjectStatus ;
    private RealmList<Model> Like ;
    private RealmList<Model> JoinOffer ; // For Owner User
    private RealmList<Model> AcceptJoinOffer ; // For User self

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public RealmList<Model> getMyProjectStatus() {
        return MyProjectStatus;
    }

    public void setMyProjectStatus(RealmList<Model> myProjectStatus) {
        MyProjectStatus = myProjectStatus;
    }

    public RealmList<Model> getLike() {
        return Like;
    }

    public void setLike(RealmList<Model> like) {
        Like = like;
    }

    public RealmList<Model> getJoinOffer() {
        return JoinOffer;
    }

    public void setJoinOffer(RealmList<Model> joinOffer) {
        JoinOffer = joinOffer;
    }

    public RealmList<Model> getAcceptJoinOffer() {
        return AcceptJoinOffer;
    }

    public void setAcceptJoinOffer(RealmList<Model> acceptJoinOffer) {
        AcceptJoinOffer = acceptJoinOffer;
    }
}
