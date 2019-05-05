package teamup.rivile.com.teamup.APIS.WebServiceConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig {
    private String BASE_URL;
//    private static String BASE_URL = "http://www.teamupapi.rivile.com/";
    public AppConfig(String url){
        BASE_URL = url;
    }
    public Retrofit getRetrofit() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("MM/dd/yyyy hh:mm:ss")
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }
}
