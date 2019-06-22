package teamup.rivile.com.teamup.network.APIModels;

public class FavouriteModel {
    public int Id ;
    public int UserId ;
    public int OfferId ;
    public int Status ;

    public FavouriteModel() {
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

    public void setStatus(int status) {
        Status = status;
    }

    public int getStatus() {
        return Status;
    }
}
