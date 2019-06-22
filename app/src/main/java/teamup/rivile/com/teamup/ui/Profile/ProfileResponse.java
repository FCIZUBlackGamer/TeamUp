package teamup.rivile.com.teamup.ui.Profile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import teamup.rivile.com.teamup.network.APIModels.Offers;
import teamup.rivile.com.teamup.network.APIModels.UserModel;

public class ProfileResponse {
    @SerializedName("UserDetails")
    UserModel UserDetails;

    @SerializedName("ListOffer")
    List<Offers> ListOffer;

    public void setListOffer(List<Offers> listOffer) {
        ListOffer = listOffer;
    }

    public void setUserDetails(UserModel userDetails) {
        UserDetails = userDetails;
    }

    public List<Offers> getListOffer() {
        return ListOffer;
    }

    public UserModel getUserDetails() {
        return UserDetails;
    }
}
