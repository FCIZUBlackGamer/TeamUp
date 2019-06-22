package teamup.rivile.com.teamup.network.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.AppExecutors;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.JoinedProjectRealmObject;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.networkUtil.AppNetworkConnectivityUtil;
import teamup.rivile.com.teamup.network.APIModels.CapTagCat;
import teamup.rivile.com.teamup.network.APIModels.Department;
import teamup.rivile.com.teamup.network.APIModels.Offer;
import teamup.rivile.com.teamup.network.APIModels.OfferDetailsJsonObject;
import teamup.rivile.com.teamup.network.APIModels.RefuseReason;
import teamup.rivile.com.teamup.network.retrofit.RetrofitConfigurations;
import teamup.rivile.com.teamup.network.retrofit.RetrofitMethods;
import teamup.rivile.com.teamup.ui.Profile.ProfileResponse;

public class AppNetworkRepository {
    private static AppNetworkRepository mInstance = null;

    private AppExecutors mAppExecutors;
    private RetrofitMethods mRetrofitMethods;
    private Application mApplication;

    public static AppNetworkRepository getInstance(@NonNull Application application) {
        if (mInstance == null) {
            mInstance = new AppNetworkRepository(application);
        }
        return mInstance;
    }

    private AppNetworkRepository(Application application) {
        mAppExecutors = AppExecutors.getInstance();
        mRetrofitMethods = new RetrofitConfigurations(API.BASE_URL).getRetrofit().create(RetrofitMethods.class);
        mApplication = application;
    }

    public LiveData<List<String>> uploadFile(MultipartBody.Part file, RequestBody name) {
        MutableLiveData<List<String>> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<List<String>> call;
            call = mRetrofitMethods.uploadFile(file, name);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<CapTagCat> getCapTagCat() {
        MutableLiveData<CapTagCat> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<CapTagCat> call = mRetrofitMethods.getCapTagCat(API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Offer> getOffersByCatId(String userId, int catId) {
        MutableLiveData<Offer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Offer> call = mRetrofitMethods.getOffersByCatId(userId, catId, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Offer> getAllOffers(String userId) {
        MutableLiveData<Offer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Offer> call = mRetrofitMethods.getAllOffers(userId, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Offer> getJoinedOffer(String userId, int catId) {
        MutableLiveData<Offer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Offer> call = mRetrofitMethods.getJoinedOffer(userId, catId, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Offer> getJoinedOffer(String userId) {
        MutableLiveData<Offer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Offer> call = mRetrofitMethods.getJoinedOffer(userId, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Offer> getSuccessOffer(String userId, int catId) {
        MutableLiveData<Offer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Offer> call = mRetrofitMethods.getSuccessOffer(userId, catId, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Offer> getSuccessOffer(String userId) {
        MutableLiveData<Offer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Offer> call = mRetrofitMethods.getSuccessOffer(userId, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Offer> searchOffer(int type, String name) {
        MutableLiveData<Offer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Offer> call = mRetrofitMethods.searchOffer(type, name, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Offer> filterSearchOffer(String filter) {
        MutableLiveData<Offer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Offer> call = mRetrofitMethods.filterSearchOffer(filter, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Offer> getFavourite(String ids) {
        MutableLiveData<Offer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Offer> call = mRetrofitMethods.getFavourite(ids, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<LoginDataBase> login(@NonNull String user, @NonNull String deviceToken, @Nullable String location, @NonNull Boolean isSocial) {
        MutableLiveData<LoginDataBase> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<LoginDataBase> call;
            if (isSocial) {
                call = mRetrofitMethods.socialLogin(user, API.URL_TOKEN, deviceToken, location);
            } else {
                call = mRetrofitMethods.login(user, API.URL_TOKEN, deviceToken);
            }
            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> register(String user, String deviceToken, String location) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.register(user, API.URL_TOKEN, deviceToken, location);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> reportOffer(String report) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.reportOffer(report, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<ProfileResponse> getProfile(int id) {
        MutableLiveData<ProfileResponse> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<ProfileResponse> call = mRetrofitMethods.getProfile(id, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Integer> forgetPassword(String email) {
        MutableLiveData<Integer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Integer> call = mRetrofitMethods.ForgetPassword(email, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Integer> checkCode(String code) {
        MutableLiveData<Integer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Integer> call = mRetrofitMethods.CheakCode(code, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<LoginDataBase> savePasswordLogin(int id, String password) {
        MutableLiveData<LoginDataBase> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<LoginDataBase> call = mRetrofitMethods.SavePasswordLogin(id, password, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<List<Department>> getCategory() {
        MutableLiveData<List<Department>> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<List<Department>> call = mRetrofitMethods.getCategory(API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<OfferDetailsJsonObject> offerDetails(int id) {
        MutableLiveData<OfferDetailsJsonObject> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<OfferDetailsJsonObject> call = mRetrofitMethods.offerDetails(id, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> deleteOffer(int id) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.deleteOffer(id, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> editProfile(String user, String location) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.editProfile(user, location, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> favouriteOffer(String favorite) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.favouriteOffer(favorite, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> likeOffer(String like) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.likeOffer(like, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Integer> addOffer(String offer, String attachment, String capital, String tags, String location) {
        MutableLiveData<Integer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Integer> call = mRetrofitMethods.addOffer(API.URL_TOKEN, offer, attachment, capital, tags, location);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<Integer> editOffer(String offer, String attachment, String deletedAttachment, String capital, String tags, String location) {
        MutableLiveData<Integer> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<Integer> call = mRetrofitMethods.editOffer(API.URL_TOKEN, offer, attachment, deletedAttachment, capital, tags, location);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> joinOffer(int userId, int offerId) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.joinOffer(userId, offerId, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<OfferDetailsJsonObject> listUsersJoinRequests(int offerId) {
        MutableLiveData<OfferDetailsJsonObject> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<OfferDetailsJsonObject> call = mRetrofitMethods.listUsersJoinRequests(offerId, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> accountSettings(String user, String currentPassword) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.accountSettings(user, currentPassword, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> mailReset(String user) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.mailReset(user, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> checkCodeMail(int userId, String mail, String code) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.checkCodeMail(userId, mail, code, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> acceptUserJoinRequest(int projectId, int userId) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.acceptUserJoinRequest(projectId, userId, API.JoinRequestResponse.STATUS_ACCEPT, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> blockUser(int projectId, int userId) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.blockUser(projectId, userId, API.JoinRequestResponse.STATUS_BLOCK, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> refuseUserJoinRequestWithSystemReason(int projectId, int userId, int refuseReasonId) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.refuseUserJoinRequestWithSystemReason(projectId, userId, API.JoinRequestResponse.STATUS_REFUSE, refuseReasonId, API.BASE_URL);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<String> refuseUserJoinRequestWithReason(int offerId, int userId, String otherReason) {
        MutableLiveData<String> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<String> call = mRetrofitMethods.refuseUserJoinRequestWithReason(offerId, userId, API.JoinRequestResponse.STATUS_REFUSE, otherReason, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<List<RefuseReason>> getSystemRefuseReasons() {
        MutableLiveData<List<RefuseReason>> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<List<RefuseReason>> call = mRetrofitMethods.getSystemRefuseReasons(API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    public LiveData<List<JoinedProjectRealmObject>> listJoinedProjects(int userId) {
        MutableLiveData<List<JoinedProjectRealmObject>> responseLiveData = new MutableLiveData<>();

        if (isDeviceConnectedToTheInternet()) {
            Call<List<JoinedProjectRealmObject>> call = mRetrofitMethods.listJoinedProjects(userId, API.URL_TOKEN);

            call.enqueue(new APICallback<>(responseLiveData));
            return responseLiveData;
        } else responseLiveData.postValue(null);

        return responseLiveData;
    }

    private boolean isDeviceConnectedToTheInternet() {
        boolean isConnected = AppNetworkConnectivityUtil.isDeviceConnectedToTheInternet(mApplication);
        if (!isConnected) {
            mAppExecutors.mainThread().execute(() ->
                    Toast.makeText(mApplication, mApplication.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show());
        }
        return isConnected;
    }

    private class APICallback<T> implements Callback<T> {
        private MutableLiveData<T> mResponse;

        APICallback(MutableLiveData<T> response) {
            super();
            mResponse = response;
        }

        @Override
        public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
            if (response.body() != null) {
                mResponse.postValue(response.body());
            } else {
                Log.v(APICallback.class.getSimpleName(), response.code() + " // " + response.message());
                mAppExecutors.mainThread().execute(() -> Toast.makeText(mApplication, response.code() + " // " + response.message(), Toast.LENGTH_SHORT).show());
            }
        }

        @Override
        public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
            Log.v(APICallback.class.getSimpleName(), t.getMessage());
            mAppExecutors.mainThread().execute(() -> Toast.makeText(mApplication, t.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}
