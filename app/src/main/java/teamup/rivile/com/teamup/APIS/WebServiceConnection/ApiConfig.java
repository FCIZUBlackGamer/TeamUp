package teamup.rivile.com.teamup.APIS.WebServiceConnection;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.Department.Department;
import teamup.rivile.com.teamup.Department.DepartmentJson;
import teamup.rivile.com.teamup.Profile.ProfileResponse;
import teamup.rivile.com.teamup.Uitls.APIModels.AttachmentModel;
import teamup.rivile.com.teamup.Uitls.APIModels.CapTagCat;
import teamup.rivile.com.teamup.Uitls.APIModels.Offer;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

public interface ApiConfig {
    @Multipart
    @POST(API.UPLOAD_URL)
    Call<List<String>> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @FormUrlEncoded
    @POST(API.LOAD_ListOfCapTagCat_URL)
    Call<CapTagCat> getCapTagCat(@Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.HOME_URL)
    Call<List<Offers>> getOffersByCatAndCapId(@Field("CatId") int CatId, @Field("CapId") int CapId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.HOME_URL)
    Call<Offer> getOffersByCatId(@Field("CatId") int CatId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.HOME_URL)
    Call<Offer> getAllOffers(@Field("token") String token);

    @FormUrlEncoded
    @POST(API.LOGIN_URL)
    Call<UserModel> login(@Field("User") String User, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.REGISTER_URL)
    Call<String> register(@Field("User") String User, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.PROFILE_URL)
    Call<ProfileResponse> getProfile(@Field("Id") int id, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.LOAD_DEPARTMENTS_URL)
    Call<DepartmentJson> getCategory(@Field("token") String token);

    @FormUrlEncoded
    @POST(API.OFFER_DETAILS_URL)
    Call<OfferDetailsJsonObject> offerDetails(@Field("Id") int Id, @Field("token") String token);


    @FormUrlEncoded
    @POST(API.LIKE_URL)
    Call<String> likeOffer(@Field("Like") String Like, @Field("token") String token);


    @FormUrlEncoded
    @POST(API.ADD_OFFER_URL)
    Call<String> addOffer(@Field(API.PARAM_NAME_TOKEN) String token,
                          @Field(API.PARAM_NAME_OFFER) String offer,
                          @Field(API.PARAM_NAME_REQUIREMENT) String requirement,
                          @Field(API.PARAM_NAME_ATTACHMENT) String attachment,
                          @Field(API.PARAM_NAME_CAPITAL) String capital,
                          @Field(API.PARAM_NAME_TAGS) String tags);

    @POST("{file_name}")
    @Streaming
    void download(@Path("file_name") String fileName, Callback<AttachmentModel> callback);

    @FormUrlEncoded
    @POST(API.JOIN_OFFER_URL)
    Call<String> joinOffer(@Field(API.PARAM_NAME_TOKEN) String token,
                           @Field(API.PARAM_NAME_REQUIREMENT) String requirement,
                           @Field(API.PARAM_NAME_ATTACHMENT) String attachment,
                           @Field(API.PARAM_NAME_OFFER_ID) String offerId);
}
