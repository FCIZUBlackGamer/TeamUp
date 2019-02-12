package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmObject;

public class LoginDataBase extends RealmObject {
    UserDataBase User;
    LikeDataBase Likes;
    FavouriteDataBase Favorites;
    OfferDetailsDataBase Offers;

    public LoginDataBase(UserDataBase user, LikeDataBase likes, FavouriteDataBase favorites, OfferDetailsDataBase offers) {
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

    public LikeDataBase getLikes() {
        return Likes;
    }

    public void setLikes(LikeDataBase likes) {
        Likes = likes;
    }

    public FavouriteDataBase getFavorites() {
        return Favorites;
    }

    public void setFavorites(FavouriteDataBase favorites) {
        Favorites = favorites;
    }

    public OfferDetailsDataBase getOffers() {
        return Offers;
    }

    public void setOffers(OfferDetailsDataBase offers) {
        Offers = offers;
    }
}
