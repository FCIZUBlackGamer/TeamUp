package teamup.rivile.com.teamup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.ForgetPassword.FragmentSendCode;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;

public class Login extends Fragment {
    private static final int RC_SIGN_IN = 100;
    private static final String EMAIL = "email";

    private GoogleSignInClient mGoogleSignInClient;

    private CallbackManager mCallbackManager;
    private ProfileTracker mProfileTracker;

    SignInButton signInButton;
    LoginButton loginButton;

    EditText ed_email, ed_password;
    Button btn_save;
    TextView tv_login;
    View view;
    UserModel userModel;
    Realm realm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Realm.init(getActivity());
        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);

        realm = Realm.getDefaultInstance();

        realm.executeTransaction(realm1 -> {
            RealmResults<LoginDataBase> results = realm.where(LoginDataBase.class).findAll();
            if (results.size() > 0){
                Gson gson = new Gson();
                Log.e("results", results.get(0).getUser().getId()+"");
                startActivity(new Intent(getActivity(), DrawerActivity.class));
            }
        });

        userModel = new UserModel();
        signInButton.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


        FacebookSdk.sdkInitialize(getActivity());
        mCallbackManager = CallbackManager.Factory.create();


        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        loginButton.setFragment(this);
        // If you are using in a fragment, call loginButton.setFragment(this);

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                (object, response) -> {
                                    Log.v("LoginActivity", response.toString());

                                    /**
                                     * All Data
                                     * {
                                     *   "id": "12345678",
                                     *   "birthday": "1/1/1950",
                                     *   "first_name": "Chris",
                                     *   "gender": "male",
                                     *   "last_name": "Colm", 
                                     *   "link": "http://www.facebook.com/12345678",
                                     *   "location": {
                                     *     "id": "110843418940484",
                                     *     "name": "Seattle, Washington"
                                     *   },
                                     *   "locale": "en_US",
                                     *   "name": "Chris Colm",
                                     *   "timezone": -8,
                                     *   "updated_time": "2010-01-01T16:40:43+0000",
                                     *   "verified": true
                                     * }
                                     * */
                                    // Application code
                                    if (object != null) {
                                        try {
                                            String id = object.getString("id");
                                            String firstName = object.getString("first_name");
                                            String lastName = object.getString("last_name");
                                            String email = object.getString("email");
//                                            String gender = object.getString("link");
                                            String birthday = object.getString("birthday"); // 01/31/1980 format
                                            userModel.setSocialId(id);
                                            userModel.setMail(email);
                                            userModel.setFullName(firstName + " " + lastName);
                                            userModel.setDateOfBirth(birthday);
//                                            if (gender.equals("male")) {
//                                                userModel.setGender(true);
//                                            } else if (gender.equals("female")) {
//                                                userModel.setGender(false);
//                                            }
                                            loginFb(userModel);
                                            Gson gson = new Gson();
                                            Log.e("UserModel", gson.toJson(userModel));
//                                            Log.e("link", gender);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "hometown,first_name,last_name,email,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();


//                        String userName = loginResult.getAccessToken().;
//                        //mUserNameTextView.setText(userName);
//
                        URL imageURL = null;
                        try {
                            imageURL = new URL("https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?type=large");
//                            Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                            Log.e("UserImage", imageURL + "");
                            userModel.setImage(imageURL + "");
//                            mUserImageImageView.setImageBitmap(bitmap);
                        } catch (IOException ignored) {
                        }


                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("ERROR ", exception.toString());
                    }
                });


        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    String name = currentProfile.getName();
                    Uri userImageUri = currentProfile.getProfilePictureUri(60, 60);
                    Log.e("FbName ", name);
                    Log.e("FbImage ", userImageUri + "");
                } else {

                }
            }
        };

        tv_login.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.first, FragmentSendCode.setEmail(ed_email.getText().toString()));
            transaction.commit();
        });

        btn_save.setOnClickListener(v -> {
            suspendViews();
            userModel = new UserModel();
            userModel.setPassword(ed_password.getText().toString());
            userModel.setMail(ed_email.getText().toString());
            //Todo: Call login(userModel)
            login(userModel);
        });

    }

    private void initViews(View view) {
        tv_login = view.findViewById(R.id.tv_login);
        btn_save = view.findViewById(R.id.btn_save);
        ed_email = view.findViewById(R.id.ed_email);
        ed_password = view.findViewById(R.id.til_password);
        signInButton = view.findViewById(R.id.btn_login_google);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        loginButton = view.findViewById(R.id.btn_login_facebook);

    }

    private void suspendViews() {
        tv_login.setEnabled(false);
        btn_save.setEnabled(false);
        ed_email.setEnabled(false);
        ed_password.setEnabled(false);
        signInButton.setEnabled(false);
        loginButton.setEnabled(false);
    }

    private void activateViews() {
        tv_login.setEnabled(true);
        btn_save.setEnabled(true);
        ed_email.setEnabled(true);
        ed_password.setEnabled(true);
        signInButton.setEnabled(true);
        loginButton.setEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        suspendViews();
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        } else mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //TODO: Signed in successfully

            //set mUserNameTextView text to user id
            String userName;
            if (account != null) {
                userName = account.getDisplayName();
                Uri userImageUri = account.getPhotoUrl();
                Log.e("GName ", userName);
                Log.e("GEmail ", account.getEmail());
                Log.e("GId ", account.getId());
                Log.e("GImage ", userImageUri + "");
                userModel.setFullName(userName);
                userModel.setImage(userImageUri + "");
                userModel.setMail(account.getEmail());
                userModel.setSocialId(account.getId());
                loginFb(userModel);
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("ERROR ", e.getStatusCode() + "");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mProfileTracker.stopTracking();
    }

    private void login(UserModel userModel) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);
        Gson gson = new Gson();
        Log.e("Here", gson.toJson(userModel));
        // Parsing any Media type file

        realm.executeTransaction(realm1 -> {
            realm1.deleteAll();
        });

        ApiConfig reg = appConfig.getRetrofit().create(ApiConfig.class);
        Call<LoginDataBase> call = reg.login(gson.toJson(userModel), API.URL_TOKEN);
        call.enqueue(new Callback<LoginDataBase>() {
            @Override
            public void onResponse(Call<LoginDataBase> call, retrofit2.Response<LoginDataBase> response) {
                LoginDataBase serverResponse = response.body();
                if (serverResponse != null) {
                    Log.i("Response", gson.toJson(serverResponse));
                    realm.executeTransaction(realm1 -> {
                        realm1.insertOrUpdate(serverResponse);
                        Log.e("results", serverResponse.getUser().getId()+"");
                        startActivity(new Intent(getActivity(), DrawerActivity.class));
                    });
                } else {
                    //textView.setText(serverResponse.toString());
                    Log.e("Err", "Empty");
                }
                activateViews();
            }

            @Override
            public void onFailure(Call<LoginDataBase> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Err", t.getMessage());
                activateViews();
            }
        });
    }

    private void loginFb(UserModel userModel) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);
        Gson gson = new Gson();
        Log.e("Here", gson.toJson(userModel));
        // Parsing any Media type file

        realm.executeTransaction(realm1 -> {
            realm1.deleteAll();
        });

        ApiConfig reg = appConfig.getRetrofit().create(ApiConfig.class);
        Call<LoginDataBase> call = reg.loginFb(gson.toJson(userModel), API.URL_TOKEN);
        call.enqueue(new Callback<LoginDataBase>() {
            @Override
            public void onResponse(Call<LoginDataBase> call, retrofit2.Response<LoginDataBase> response) {
                LoginDataBase serverResponse = response.body();
                if (serverResponse != null) {
                    Log.i("Response", gson.toJson(serverResponse));
                    realm.executeTransaction(realm1 -> {
                        realm1.insertOrUpdate(serverResponse);
                        Log.e("results", serverResponse.getUser().getId()+"");
                        startActivity(new Intent(getActivity(), DrawerActivity.class));
                    });

                } else {
                    //textView.setText(serverResponse.toString());
                    Log.e("Err", "Empty");
                }
                activateViews();
            }

            @Override
            public void onFailure(Call<LoginDataBase> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Err", t.getMessage());
                activateViews();
            }
        });
    }
}
