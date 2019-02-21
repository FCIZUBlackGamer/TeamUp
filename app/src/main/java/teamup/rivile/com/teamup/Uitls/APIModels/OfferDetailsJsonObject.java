package teamup.rivile.com.teamup.Uitls.APIModels;

import teamup.rivile.com.teamup.Project.Details.OfferDetails;

public class OfferDetailsJsonObject {

    private OfferDetails Offer;

    public OfferDetailsJsonObject(OfferDetails offer) {
        Offer = offer;
    }

    public OfferDetailsJsonObject() {
    }

    public OfferDetails getOffer() {
        return Offer;
    }

    public void setOffer(OfferDetails offer) {
        Offer = offer;
    }
}
