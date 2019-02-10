package teamup.rivile.com.teamup.Uitls.APIModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offer {
    @SerializedName("Offers")
    private List<Offers> offersList;

    public Offer(List<Offers> offersList) {
        this.offersList = offersList;
    }

    public Offer() {
    }

    public List<Offers> getOffersList() {
        return offersList;
    }

    public void setOffersList(List<Offers> offersList) {
        this.offersList = offersList;
    }
}
