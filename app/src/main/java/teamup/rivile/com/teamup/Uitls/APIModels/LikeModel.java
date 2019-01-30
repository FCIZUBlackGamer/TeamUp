package teamup.rivile.com.teamup.Uitls.APIModels;

public class LikeModel {
    public int Id ;
    public int UserId ;
    public int OfferId ;

    public LikeModel() {
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
}
