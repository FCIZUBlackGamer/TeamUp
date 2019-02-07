package teamup.rivile.com.teamup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

public class Register extends AppCompatActivity {

    EditText ed_full_name, ed_email, ed_password;
    Button btn_save;
    TextView tv_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Register.this,Login.class));
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userModel = new UserModel();
                userModel.setFullName(ed_full_name.getText().toString());
                userModel.setMail(ed_email.getText().toString());
                userModel.setPassword(ed_password.getText().toString());
                register(userModel);
            }
        });
    }

    private void initViews() {
        tv_login = findViewById(R.id.tv_login);
        btn_save = findViewById(R.id.btn_save);
        ed_full_name = findViewById(R.id.ed_full_name);
        ed_email = findViewById(R.id.ed_email);
//        ed_user_name = findViewById(R.id.ed_user_name);
        ed_password = findViewById(R.id.ed_password);
    }

    private void register(UserModel userModel) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.REGISTER_URL);
        Gson gson = new Gson();

        // Parsing any Media type file

        ApiConfig reg = appConfig.getRetrofit().create(ApiConfig.class);
        Call<String> call = reg.register(gson.toJson(userModel), API.URL_TOKEN);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String serverResponse = response.body();
                if (serverResponse.equals("\"Success\"")) {
                   finish();
                   startActivity(new Intent(Register.this,Login.class));
                } else {
                    Toast.makeText(Register.this,serverResponse,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }
}
