package teamup.rivile.com.teamup.Uitls.APIModels;

import teamup.rivile.com.teamup.Project.Details.OfferDetails;

public class OfferDetailsJsonObject {

    private OfferDetails Offers;

    public OfferDetailsJsonObject(OfferDetails offer) {
        Offers = offer;
    }

    public OfferDetailsJsonObject() {
    }

    public OfferDetails getOffer() {
        return Offers;
    }

    public void setOffer(OfferDetails offer) {
        Offers = offer;
    }
}
