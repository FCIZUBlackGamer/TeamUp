package teamup.rivile.com.teamup.Uitls.InternalDatabase.model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class LoginDataBase extends RealmObject {
    UserDataBase User;
    RealmList<LikeModelDataBase> Likes;
    RealmList<FavouriteDataBase> Favorites;
    RealmList<OfferDataBase> Offers;

    public LoginDataBase(UserDataBase user, RealmList<LikeModelDataBase> likes, RealmList<FavouriteDataBase> favorites, RealmList<OfferDataBase> offers) {
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

    public void addLikes(int offerId, int userId) {
        LikeModelDataBase l = new LikeModelDataBase();
        l.setOfferId(offerId);
        l.setUserId(userId);
        l.setStatus(0);
        Likes.add(l);
    }

    public void addFavuriteOffer(int offerId, int userId) {
        FavouriteDataBase l = new FavouriteDataBase();
        l.setOfferId(offerId);
        l.setUserId(userId);
        l.setStatus(0);
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

    public RealmList<OfferDataBase> getOffers() {
        return Offers;
    }

    public void setOffers(RealmList<OfferDataBase> offers) {
        Offers = offers;
    }

//    public static void main(String[] args) {
//
//        Object o = new Example();
//        System.out.println(((Example) o).sum());
//    }
//
//    static class Example {
//        int x = 1;
//        int y = 2;
//
//        int sum() {
//            return x + y;
//        }
//    }
}