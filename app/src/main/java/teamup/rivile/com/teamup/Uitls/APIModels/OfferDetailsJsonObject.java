package teamup.rivile.com.teamup.Uitls.APIModels;

import teamup.rivile.com.teamup.Project.Details.OfferDetails;

public class OfferDetailsJsonObject {

    private OfferDetails Offers;

    public OfferDetailsJsonObject(OfferDetails offers) {
        Offers = offers;
    }

    public OfferDetailsJsonObject() {
    }

    public OfferDetails getOffers() {
        return Offers;
    }

    public void setOffers(OfferDetails offers) {
        Offers = offers;
    }
}
