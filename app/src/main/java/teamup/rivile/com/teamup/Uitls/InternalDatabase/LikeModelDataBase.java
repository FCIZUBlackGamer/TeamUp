package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmObject;

public class LikeModelDataBase extends RealmObject {
    public int Id ;
    public int UserId ;
    public int OfferId ;
    public Integer Status;

    public LikeModelDataBase() {
    }

    public void setId(int id) {
        Id = id;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setOfferId(int offerId) {
        OfferId = offerId;
    }

    public int getId() {
        return Id;
    }

    public int getUserId() {
        return UserId;
    }

    public int getOfferId() {
        return OfferId;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer getStatus() {
        return Status;
    }
}
