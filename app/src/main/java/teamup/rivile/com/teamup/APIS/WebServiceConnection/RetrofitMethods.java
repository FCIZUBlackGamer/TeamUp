package teamup.rivile.com.teamup.APIS.WebServiceConnection;

import android.support.annotation.NonNull;

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
import teamup.rivile.com.teamup.Uitls.APIModels.RefuseReason;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.JoinedProject;
import teamup.rivile.com.teamup.ui.Department.DepartmentJson;
import teamup.rivile.com.teamup.ui.Profile.ProfileResponse;
import teamup.rivile.com.teamup.Uitls.APIModels.CapTagCat;
import teamup.rivile.com.teamup.Uitls.APIModels.NotificationModel;
import teamup.rivile.com.teamup.Uitls.APIModels.Offer;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;

public interface RetrofitMethods {
    @Multipart
    @POST(API.UPLOAD_URL)
    Call<List<String>> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @FormUrlEncoded
    @POST(API.LOAD_ListOfCapTagCat_URL)
    Call<CapTagCat> getCapTagCat(@Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.HOME_URL)
    Call<List<Offers>> getOffersByCatAndCapId(@Field("UserId") String userId, @Field("CatId") int CatId, @Field("CapId") int CapId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.HOME_URL)
    Call<Offer> getOffersByCatId(@Field("UserId") String userId, @Field("CatId") int CatId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.HOME_URL)
    Call<Offer> getAllOffers(@Field("UserId") String userId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.ListJoinedOffer_URL)
    Call<Offer> getJoinedOffer(@Field("UserId") String userId, @Field("CatId") int CatId, @Field("CapId") int CapId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.ListJoinedOffer_URL)
    Call<Offer> getJoinedOffer(@Field("UserId") String userId, @Field("CatId") int CatId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.ListJoinedOffer_URL)
    Call<Offer> getJoinedOffer(@Field("UserId") String userId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.ListSuccessOffer_URL)
    Call<Offer> getSuccessOffer(@Field("UserId") String userId, @Field("CatId") int CatId, @Field("CapId") int CapId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.ListSuccessOffer_URL)
    Call<Offer> getSuccessOffer(@Field("UserId") String userId, @Field("CatId") int CatId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.ListSuccessOffer_URL)
    Call<Offer> getSuccessOffer(@Field("UserId") String userId, @Field("token") String token);

    /**
     * @param Type {2: UserName, 1: ProjectName, 0: Tag}
     */
    @FormUrlEncoded
    @POST(API.GENERAL_SEARCH_URL)
    Call<Offer> searchOffer(@Field("Type") int Type, @Field("Name") String Name, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.FILTER_SEARCH_URL)
    Call<Offer> filterSearchOffer(@Field("Filter") String Filter, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.LOAD_FAVOURITE_URL)
    Call<Offer> getFavourite(@Field("Ids") String Ids, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.LOGIN_URL)
    Call<LoginDataBase> login(@NonNull @Field(API.PARAM_NAME_USER) String user,
                              @NonNull @Field(API.PARAM_NAME_TOKEN) String token,
                              @NonNull @Field(API.PARAM_NAME_DEVICE_TOKEN) String deviceToken);

    @FormUrlEncoded
    @POST(API.SOCIAL_LOGIN_URL)
    Call<LoginDataBase> socialLogin(@NonNull @Field(API.PARAM_NAME_USER) String user,
                                    @NonNull @Field(API.PARAM_NAME_TOKEN) String token,
                                    @NonNull @Field(API.PARAM_NAME_DEVICE_TOKEN) String deviceToken,
                                    @Field(API.PARAM_NAME_LOCATION) String location);

    @FormUrlEncoded
    @POST(API.REGISTER_URL)
    Call<String> register(@NonNull @Field(API.PARAM_NAME_USER) String user,
                          @NonNull @Field(API.PARAM_NAME_TOKEN) String token,
                          @NonNull @Field(API.PARAM_NAME_DEVICE_TOKEN) String deviceToken,
                          @NonNull @Field(API.PARAM_NAME_LOCATION) String location);

    @FormUrlEncoded
    @POST(API.REPORT_URL)
    Call<String> reportOffer(@Field("report") String report, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.PROFILE_URL)
    Call<ProfileResponse> getProfile(@Field("Id") int id, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.LIST_USERS_JOIN_REQUESTS)
    Call<OfferDetailsJsonObject> getRequirements(@Field("OfferId") int OfferId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.ForgetPassword_URL)
    Call<Integer> ForgetPassword(@Field("Mail") String Mail, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.CheckCode_URL)
    Call<Integer> CheakCode(@Field("Code") String Code, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.SavePasswordLogin_URL)
    Call<LoginDataBase> SavePasswordLogin(@Field("Id") int Id, @Field("Password") String Password, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.LOAD_DEPARTMENTS_URL)
    Call<DepartmentJson> getCategory(@Field("token") String token);

    @FormUrlEncoded
    @POST(API.OFFER_DETAILS_URL)
    Call<OfferDetailsJsonObject> offerDetails(@Field(API.PARAM_NAME_ID) int Id, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.ACCEPT_JOIN_OFFER_URL)
    Call<String> acceptRequirement(@Field("OfferId") int OfferId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.DeleteRequirement_URL)
    Call<String> deleteRequirement(@Field("Id") int Id, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.REFUSE_JOIN_OFFER_URL)
    Call<String> rejectRequirement(@Field("OfferId") int OfferId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.DELETE_OFFER_URL)
    Call<String> deleteOffer(@Field("Id") int Id, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.SelectOffer_URL)
    Call<Offer> SelectOffer(@Field("UserId") int UserId, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.EDIT_PROFILE_URL)
    Call<String> editProfile(@Field("User") String User, @Field("Location") String Location, @Field("token") String token);


    @FormUrlEncoded
    @POST(API.FAVOURITE_URL)
    Call<String> favouriteOffer(@Field("favorite") String favorite, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.LIKE_URL)
    Call<String> likeOffer(@Field("Like") String Like, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.NOTIFICATION_URL)
    Call<NotificationModel> getNotification(@Field("Notification") String Notification, @Field("token") String token);

    @FormUrlEncoded
    @POST(API.ADD_OFFER_URL)
    Call<Integer> addOffer(@Field(API.PARAM_NAME_TOKEN) String token,
                           @Field(API.PARAM_NAME_OFFER) String offer,
                           @Field(API.PARAM_NAME_ATTACHMENT) String attachment,
                           @Field(API.PARAM_NAME_CAPITAL) String capital,
                           @Field(API.PARAM_NAME_TAGS) String tags,
                           @Field(API.PARAM_NAME_LOCATION) String location);

    @FormUrlEncoded
    @POST(API.EDIT_OFFER_URL)
    Call<Integer> editOffer(@Field(API.PARAM_NAME_TOKEN) String token,
                            @Field(API.PARAM_NAME_OFFER) String offer,
                            @Field(API.PARAM_NAME_ATTACHMENT) String attachment,
                            @Field(API.PARAM_NAME_DELETED_ATTACHMENT) String deletedAttachment,
                            @Field(API.PARAM_NAME_CAPITAL) String capital,
                            @Field(API.PARAM_NAME_TAGS) String tags,
                            @Field(API.PARAM_NAME_LOCATION) String location);

    @FormUrlEncoded
    @POST(API.JOIN_OFFER_URL)
    Call<String> joinOffer(@Field(API.PARAM_NAME_USER_ID) int userId,
                           @Field(API.PARAM_NAME_OFFER_ID) int offerId,
                           @Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.LIST_USERS_JOIN_REQUESTS)
    Call<OfferDetailsJsonObject> listUsersJoinRequests(@Field(API.PARAM_NAME_OFFER_ID) int offerId,
                                                       @Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.ACCOUNT_SETTINGS_URL)
    Call<String> accountSettings(@Field(API.PARAM_NAME_USER) String User,
                                 @Field(API.PARAM_NAME_CURRENT_PASSWORD) String CurrentPassword,
                                 @Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.RESET_MAIL_URL)
    Call<String> mailReset(@Field(API.PARAM_NAME_USER) String User,
                           @Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.RESET_MAIL_CHECK_CODE_MAIL)
    Call<String> checkCodeMail(@Field(API.PARAM_NAME_USER_ID) int UserId,
                               @Field(API.PARAM_NAME_MAIL) String Mail,
                               @Field(API.PARAM_NAME_CODE) String Code,
                               @Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.OWNER_PANEL_URL)
    Call<String> acceptUserJoinRequest(@Field(API.PARAM_NAME_OFFER_ID) int projectId,
                                       @Field(API.PARAM_NAME_USER_ID) int userId,
                                       @Field(API.PARAM_NAME_STATUS) int status,
                                       @Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.OWNER_PANEL_URL)
    Call<String> blockUser(@Field(API.PARAM_NAME_OFFER_ID) int projectId,
                           @Field(API.PARAM_NAME_USER_ID) int userId,
                           @Field(API.PARAM_NAME_STATUS) int status,
                           @Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.OWNER_PANEL_URL)
    Call<String> refuseUserJoinRequestWithSystemReason(@Field(API.PARAM_NAME_OFFER_ID) int projectId,
                                                       @Field(API.PARAM_NAME_USER_ID) int userId,
                                                       @Field(API.PARAM_NAME_STATUS) int status,
                                                       @Field(API.PARAM_NAME_REFUSE_ID) int refuseReasonId,
                                                       @Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.OWNER_PANEL_URL)
    Call<String> refuseUserJoinRequestWithReason(@Field(API.PARAM_NAME_OFFER_ID) int offerId,
                                                 @Field(API.PARAM_NAME_USER_ID) int userId,
                                                 @Field(API.PARAM_NAME_STATUS) int status,
                                                 @Field(API.PARAM_NAME_OTHER_REASON) String otherReason,
                                                 @Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.LIST_REASONS_URL)
    Call<List<RefuseReason>> getSystemRefuseReasons(@Field(API.PARAM_NAME_TOKEN) String token);

    @FormUrlEncoded
    @POST(API.LIST_JOINED_OFFERS)
    Call<List<JoinedProject>> listJoinedProjects(@Field(API.PARAM_NAME_USER_ID) int userId,
                                                 @Field(API.PARAM_NAME_TOKEN) String token);
}
