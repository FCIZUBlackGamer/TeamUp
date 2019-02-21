package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmList;
import io.realm.RealmObject;

public class LikeDataBase extends RealmObject {
    RealmList<LikeModelDataBase> Likes;

    public LikeDataBase() {
    }

    public RealmList<LikeModelDataBase> getLikes() {
        return Likes;
    }

    public void setLikes(RealmList<LikeModelDataBase> likes) {
        Likes = likes;
    }
}
