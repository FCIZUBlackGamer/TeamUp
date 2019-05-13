package teamup.rivile.com.teamup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;

public class Register extends AppCompatActivity {

    EditText ed_full_name, ed_email, ed_password;
    Button btn_save;
    TextView tv_login;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();

        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<LoginDataBase> results = realm.where(LoginDataBase.class).findAll();
            if (results.size() > 0){
//                Log.i("REalm", results.get)
                Gson gson = new Gson();
                Log.e("results", results.get(0).getUser().getId()+"");
                startActivity(new Intent(Register.this, DrawerActivity.class));
                finish();
            }
        });

        tv_login.setOnClickListener(v -> {
            //finish();
            startActivity(new Intent(Register.this, FirstActivity.class));
        });

        btn_save.setOnClickListener(v -> {
            UserModel userModel = new UserModel();
            userModel.setFullName(ed_full_name.getText().toString());
            userModel.setMail(ed_email.getText().toString());
            userModel.setPassword(ed_password.getText().toString());
            register(userModel);
        });
    }

    private void initViews() {
        tv_login = findViewById(R.id.tv_login);
        btn_save = findViewById(R.id.btn_save);
        ed_full_name = findViewById(R.id.ed_full_name);
        ed_email = findViewById(R.id.ed_email);
//        ed_user_name = findViewById(R.id.ed_user_name);
        ed_password = findViewById(R.id.til_password);
    }

    private void register(UserModel userModel) {
        // Map is used to multipart the file using okhttp3.RequestBody
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, R.string.registerInProgress, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {

                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);
        Gson gson = new Gson();

        // Parsing any Media type file

        Log.e("RegisterModel", gson.toJson(userModel));
        RetrofitMethods reg = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        //TODO: Set location instead of null here
        Call<String> call = reg.register(gson.toJson(userModel), API.URL_TOKEN, "null");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String serverResponse = response.body();
                if (serverResponse != null){
                    Log.v("Re",serverResponse);
                    if (serverResponse.equals("Success")) {
                        finish();
                        startActivity(new Intent(Register.this, FirstActivity.class));
                        Toast.makeText(Register.this, serverResponse, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Register.this, serverResponse, Toast.LENGTH_LONG).show();
                    }
                }else {
                    Log.v("Re",response.message());
                    Toast.makeText(Register.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.v("Re",t.getMessage());
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
