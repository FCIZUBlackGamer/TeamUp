package teamup.rivile.com.teamup.Uitls.InternalDatabase.model;

import io.realm.RealmObject;

public class FavouriteDataBase extends RealmObject {

    public int Id ;
    public int OfferId ;
    public int UserId ;
    public int Status ;

    public FavouriteDataBase() {
    }

    public FavouriteDataBase(int id, int offerId, int userId) {
        Id = id;
        OfferId = offerId;
        UserId = userId;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getStatus() {
        return Status;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getOfferId() {
        return OfferId;
    }

    public void setOfferId(int offerId) {
        OfferId = offerId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
