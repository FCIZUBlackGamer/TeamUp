package teamup.rivile.com.teamup.APIS.WebServiceConnection;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {
    // variable name should be same as in the json response from php
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;
    String getMessage() {
        return message;
    }
    public boolean getSuccess() {
        return success;
    }
}