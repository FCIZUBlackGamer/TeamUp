package teamup.rivile.com.teamup.network.APIModels;

import java.util.List;

public class NotificationModel {
    private int UserId ;
    private List<Model> MyProjectStatus ;
    private  List<Model> Like ;
    private List<Model> JoinOffer ; // For Owner User
    private List<Model> AcceptJoinOffer ; // For User self

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public List<Model> getMyProjectStatus() {
        return MyProjectStatus;
    }

    public void setMyProjectStatus(List<Model> myProjectStatus) {
        MyProjectStatus = myProjectStatus;
    }

    public List<Model> getLike() {
        return Like;
    }

    public void setLike(List<Model> like) {
        Like = like;
    }

    public List<Model> getJoinOffer() {
        return JoinOffer;
    }

    public void setJoinOffer(List<Model> joinOffer) {
        JoinOffer = joinOffer;
    }

    public List<Model> getAcceptJoinOffer() {
        return AcceptJoinOffer;
    }

    public void setAcceptJoinOffer(List<Model> acceptJoinOffer) {
        AcceptJoinOffer = acceptJoinOffer;
    }
}
