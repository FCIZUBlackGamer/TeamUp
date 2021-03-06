package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmList;
import io.realm.RealmObject;

public class LoginDataBase extends RealmObject {
    UserDataBase User;
    RealmList<LikeModelDataBase> Likes;
    RealmList<FavouriteDataBase> Favorites;
    RealmList<OfferDetailsDataBase> Offers;

    public LoginDataBase(UserDataBase user, RealmList<LikeModelDataBase> likes, RealmList<FavouriteDataBase> favorites, RealmList<OfferDetailsDataBase> offers) {
        User = user;
        Likes = likes;
        Favorites = favorites;
        Offers = offers;
    }

    public LoginDataBase() {
    }

    public UserDataBase getUser() {
        return User;
    }

    public void setUser(UserDataBase user) {
        User = user;
    }

    public RealmList<LikeModelDataBase> getLikes() {
        return Likes;
    }

    public void addLikes(int offerId, int userId){
        LikeModelDataBase l = new LikeModelDataBase();
        l.setOfferId(offerId);
        l.setUserId(userId);
        l.setStatus(0);
        Likes.add(l);
    }

    public void addFavuriteOffer(int offerId, int userId){
        FavouriteDataBase l = new FavouriteDataBase();
        l.setOfferId(offerId);
        l.setUserId(userId);
        Favorites.add(l);
    }

    public void setLikes(RealmList<LikeModelDataBase> likes) {
        Likes = likes;
    }

    public RealmList<FavouriteDataBase> getFavorites() {
        return Favorites;
    }

    public void setFavorites(RealmList<FavouriteDataBase> favorites) {
        Favorites = favorites;
    }

    public RealmList<OfferDetailsDataBase> getOffers() {
        return Offers;
    }

    public void setOffers(RealmList<OfferDetailsDataBase> offers) {
        Offers = offers;
    }
}
