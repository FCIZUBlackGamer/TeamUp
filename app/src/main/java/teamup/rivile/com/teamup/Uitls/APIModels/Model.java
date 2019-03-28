package teamup.rivile.com.teamup.Uitls.APIModels;

public class Model {
    private int OfferId ;
    private String UserFullName ;
    private String OfferName ;
    private int Status ;
    private int LikeId ;
    private int JoinOfferId ;
    private int AcceptJoinOfferId ;

    public int getOfferId() {
        return OfferId;
    }

    public void setOfferId(int offerId) {
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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getLikeId() {
        return LikeId;
    }

    public void setLikeId(int likeId) {
        LikeId = likeId;
    }

    public int getJoinOfferId() {
        return JoinOfferId;
    }

    public void setJoinOfferId(int joinOfferId) {
        JoinOfferId = joinOfferId;
    }

    public int getAcceptJoinOfferId() {
        return AcceptJoinOfferId;
    }

    public void setAcceptJoinOfferId(int acceptJoinOfferId) {
        AcceptJoinOfferId = acceptJoinOfferId;
    }
}
