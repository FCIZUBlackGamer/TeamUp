package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmObject;

public class FavouriteDataBase extends RealmObject {

    public int Id ;
    public int OfferId ;
    public int UserId ;

    public FavouriteDataBase() {
    }

    public FavouriteDataBase(int id, int offerId, int userId) {
        Id = id;
        OfferId = offerId;
        UserId = userId;
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
