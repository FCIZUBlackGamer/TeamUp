package teamup.rivile.com.teamup.Uitls.APIModels;

import java.util.List;

public class Offer {

    private List<Offers> Offers;

    public Offer(List<Offers> Offers) {
        this.Offers = Offers;
    }

    public Offer() {
    }

    public List<Offers> getOffers() {
        return Offers;
    }

    public void setOffers(List<Offers> offers) {
        this.Offers = offers;
    }
}
