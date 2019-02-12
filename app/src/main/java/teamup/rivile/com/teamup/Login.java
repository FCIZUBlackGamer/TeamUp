package teamup.rivile.com.teamup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.ProfileTracker;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

public class Login extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    private static final String EMAIL = "email";

    private GoogleSignInClient mGoogleSignInClient;


    private CallbackManager mCallbackManager;
    private ProfileTracker mProfileTracker;

    EditText ed_email, ed_password;
    Button btn_save;
    TextView tv_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        UserModel userModel = new UserModel();
        //Todo: Call login(userModel)


    }

    private void initViews() {
        tv_login = findViewById(R.id.tv_login);
        btn_save = findViewById(R.id.btn_save);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
    }

    private void login(UserModel userModel) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.LOGIN_URL);
        Gson gson = new Gson();

        // Parsing any Media type file

        ApiConfig reg = appConfig.getRetrofit().create(ApiConfig.class);
        Call<UserModel> call = reg.login(gson.toJson(userModel), API.URL_TOKEN);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, retrofit2.Response<UserModel> response) {
                UserModel serverResponse = response.body();
                if (serverResponse != null) {
                   finish();
                } else {
                    //textView.setText(serverResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }
}
