package teamup.rivile.com.teamup.APIS.WebServiceConnection;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;

public interface ApiConfig {
    @Multipart
    @POST("upload.php")
//    @POST("/Upload/Upload")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    Call<List<Offers>> getOffers(@Field("token") String token);

}
