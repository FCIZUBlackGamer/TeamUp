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

public class RegisterActivity extends AppCompatActivity {

    private EditText mFullNameEditText, mEmailEditText, mPasswordEditText;
    private Button mRegisterButton;
    private TextView mLoginTextView;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();

        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm1 -> {
            RealmResults<LoginDataBase> results = mRealm.where(LoginDataBase.class).findAll();
            if (results.size() > 0) {
                Gson gson = new Gson();
                Log.e("results", results.get(0).getUser().getId() + "");
                startActivity(new Intent(RegisterActivity.this, DrawerActivity.class));
                finish();
            }
        });

        mLoginTextView.setOnClickListener(v -> {
            RegisterActivity.this.finish();
        });

        mRegisterButton.setOnClickListener(v -> {
            UserModel userModel = new UserModel();
            userModel.setFullName(mFullNameEditText.getText().toString());
            userModel.setMail(mEmailEditText.getText().toString());
            userModel.setPassword(mPasswordEditText.getText().toString());
            if (validUserData(userModel)) {
                register(userModel);
            }
        });
    }

    private void initViews() {
        mLoginTextView = findViewById(R.id.tv_login);
        mRegisterButton = findViewById(R.id.btn_sign_up);
        mFullNameEditText = findViewById(R.id.ed_full_name);
        mEmailEditText = findViewById(R.id.ed_email);
        mPasswordEditText = findViewById(R.id.til_password);
    }

    private boolean validUserData(UserModel userModel) {
        boolean isValidUserData = true;

        String fullName = userModel.getFullName();
        if (fullName == null || fullName.isEmpty()) {
            mFullNameEditText.setError(getString(R.string.enter_valid_full_name));
            isValidUserData = false;
        } else {
            mFullNameEditText.setError(null);
        }

        String email = userModel.getMail();
        if (email == null || email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailEditText.setError(getString(R.string.enter_valid_email));
            isValidUserData = false;
        } else {
            mEmailEditText.setError(null);
        }

        String password = userModel.getPassword();
        if (password == null || password.isEmpty()) {
            mPasswordEditText.setError(getString(R.string.enter_valid_password));
            isValidUserData = false;
        } else if (password.length() < 8) {
            mPasswordEditText.setError(getString(R.string.password_must_have_8));
            isValidUserData = false;
        } else {
            mPasswordEditText.setError(null);
        }

        return isValidUserData;
    }

    private void register(UserModel userModel) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, R.string.registerInProgress, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {

                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);
        Gson gson = new Gson();


        Log.e("RegisterModel", gson.toJson(userModel));
        RetrofitMethods reg = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        //TODO: Set location instead of null here
        Call<String> call = reg.register(gson.toJson(userModel), API.URL_TOKEN, "null");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String serverResponse = response.body();
                if (serverResponse != null) {
                    Log.v("Re", serverResponse);
                    if (serverResponse.equals("Success")) {
                        finish();
                        startActivity(
                                new Intent(RegisterActivity.this, FirstActivity.class)
                        );
                        Toast.makeText(RegisterActivity.this, serverResponse, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, serverResponse, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.v("Re", response.message());
                    Toast.makeText(RegisterActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.v("Re", t.getMessage());
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
