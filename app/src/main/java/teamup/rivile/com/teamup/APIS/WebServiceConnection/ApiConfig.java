package teamup.rivile.com.teamup.APIS.WebServiceConnection;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.Project.Add.CapTagCat;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;

public interface ApiConfig {
    @Multipart
    @POST(API.UPLOAD_URL)
    Call<List<String>> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @FormUrlEncoded
    @POST(API.LOAD_ListOfCapTagCat_URL)
    Call<CapTagCat> getCapTagCat(@Field(API.PARAM_NAME_TOKEN) String token);

    Call<List<Offers>> getOffers(@Field("token") String token);

    @FormUrlEncoded
    @POST(API.ADD_OFFER_URL)
    Call<String> addOffer(@Field(API.PARAM_NAME_TOKEN) String token,
                          @Field(API.PARAM_NAME_OFFER) String offer,
                          @Field(API.PARAM_NAME_REQUIREMENT) String requirement,
                          @Field(API.PARAM_NAME_ATTACHMENT) String attachment,
                          @Field(API.PARAM_NAME_CAPITAL) String capital,
                          @Field(API.PARAM_NAME_TAGS) String tags);

}
