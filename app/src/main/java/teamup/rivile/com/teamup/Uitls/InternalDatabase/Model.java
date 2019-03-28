package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmObject;

public class Model extends RealmObject {
    private Integer OfferId ;
    private String UserFullName ;
    private String OfferName ;
    private Integer Status ;
    private Integer LikeId ;
    private Integer JoinOfferId ;
    private Integer AcceptJoinOfferId ;

    public Integer getOfferId() {
        return OfferId;
    }

    public void setOfferId(Integer offerId) {
        OfferId = offerId;
    }

    public String getUserFullName() {
        return UserFullName;
    }

    public void setUserFullName(String userFullName) {
        UserFullName = userFullName;
    }

    public String getOfferName() {
        return OfferName;
    }

    public void setOfferName(String offerName) {
        OfferName = offerName;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer getLikeId() {
        return LikeId;
    }

    public void setLikeId(Integer likeId) {
        LikeId = likeId;
    }

    public Integer getJoinOfferId() {
        return JoinOfferId;
    }

    public void setJoinOfferId(Integer joinOfferId) {
        JoinOfferId = joinOfferId;
    }

    public Integer getAcceptJoinOfferId() {
        return AcceptJoinOfferId;
    }

    public void setAcceptJoinOfferId(Integer acceptJoinOfferId) {
        AcceptJoinOfferId = acceptJoinOfferId;
    }
}
