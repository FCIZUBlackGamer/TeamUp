package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmObject;

public class JoinedProjectRealmObject extends RealmObject {
    private Integer Id;
    private Integer OfferId;
    private String OfferName;
    private Integer UserId;
    private Integer Status;
    private Integer RefuseId;
    private String Reson;

    public JoinedProjectRealmObject() {
    }

    public JoinedProjectRealmObject(Integer offerId, String offerName, Integer userId, Integer status, Integer refuseId, String reson) {
        OfferId = offerId;
        OfferName = offerName;
        UserId = userId;
        Status = status;
        RefuseId = refuseId;
        Reson = reson;
    }

    public JoinedProjectRealmObject(int id, Integer offerId, String offerName, Integer userId, Integer status, Integer refuseId, String reson) {
        Id = id;
        OfferId = offerId;
        OfferName = offerName;
        UserId = userId;
        Status = status;
        RefuseId = refuseId;
        Reson = reson;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Integer getOfferId() {
        return OfferId;
    }

    public void setOfferId(Integer offerId) {
        OfferId = offerId;
    }

    public String getOfferName() {
        return OfferName;
    }

    public void setOfferName(String offerName) {
        OfferName = offerName;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer getRefuseId() {
        return RefuseId;
    }

    public void setRefuseId(Integer refuseId) {
        RefuseId = refuseId;
    }

    public String getReson() {
        return Reson;
    }

    public void setReson(String reson) {
        Reson = reson;
    }
}
