package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmObject;

public class OfferDetailsJsonObjectDataBase extends RealmObject {

    private OfferDetailsDataBase Offers;

    public OfferDetailsJsonObjectDataBase(OfferDetailsDataBase offers) {
        Offers = offers;
    }

    public OfferDetailsJsonObjectDataBase() {
    }

    public OfferDetailsDataBase getOffers() {
        return Offers;
    }

    public void setOffers(OfferDetailsDataBase offers) {
        Offers = offers;
    }
}
