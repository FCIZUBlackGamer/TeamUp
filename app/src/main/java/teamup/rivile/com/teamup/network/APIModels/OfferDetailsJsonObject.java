package teamup.rivile.com.teamup.network.APIModels;

import teamup.rivile.com.teamup.Uitls.AppModels.OfferDetails;

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
