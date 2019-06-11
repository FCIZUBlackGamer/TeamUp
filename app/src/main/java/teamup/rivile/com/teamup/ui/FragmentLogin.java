package teamup.rivile.com.teamup.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.JoinedOfferRealmModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.JoinedProject;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.NotificationDatabase;
import teamup.rivile.com.teamup.ui.ForgetPassword.FragmentSendCode;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;

public class FragmentLogin extends Fragment {
    private static final int RC_SIGN_IN = 100;

    private ConstraintLayout mLoadingViewConstraintLayout;

    private EditText mEmailEditText, mPasswordEditText;
    private Button mLoginButton;

    private TextView mResetPasswordTextView;
    private TextView mRegisterTextView;

    private SignInButton mGoogleSignInButton;
    private LoginButton mFacebookLoginButton;

    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    private ProfileTracker mProfileTracker;
    private UserModel mUserModel;
    private Realm mRealm;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mLoadingViewConstraintLayout = view.findViewById(R.id.cl_loading);

        mContext = getContext();

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mEmailEditText = view.findViewById(R.id.ed_email);
        mPasswordEditText = view.findViewById(R.id.til_password);

        mLoginButton = view.findViewById(R.id.btn_save);

        mGoogleSignInButton = view.findViewById(R.id.btn_login_google);
        mGoogleSignInButton.setSize(SignInButton.SIZE_WIDE);

        mFacebookLoginButton = view.findViewById(R.id.btn_login_facebook);

        mRegisterTextView = view.findViewById(R.id.tv_register);

        mResetPasswordTextView = view.findViewById(R.id.tv_login);
    }

    @Override
    public void onStart() {
        super.onStart();

        mRealm = Realm.getDefaultInstance();

        mLoadingViewConstraintLayout.setVisibility(View.VISIBLE);
        mRealm.executeTransaction(realm1 -> {
            RealmResults<LoginDataBase> results = mRealm.where(LoginDataBase.class).findAll();
            if (results.size() > 0) {
                startActivity(new Intent(getActivity(), DrawerActivity.class));
                getActivity().finish();
            }

            mLoadingViewConstraintLayout.setVisibility(View.GONE);
        });

        mUserModel = new UserModel();
        mGoogleSignInButton.setOnClickListener(v -> {
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


        mFacebookLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        mFacebookLoginButton.setFragment(this);
        // If you are using in a fragment, call mFacebookLoginButton.setFragment(this);

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                (object, response) -> {
                                    if (object != null) {
                                        try {
                                            String id = object.getString("id");
                                            String firstName = object.getString("first_name");
                                            String lastName = object.getString("last_name");
                                            String email = object.getString("email");
//                                            String gender = object.getString("link");
//                                            String birthday = object.getString("birthday"); // 01/31/1980 format
                                            mUserModel.setSocialId(id);
                                            mUserModel.setMail(email);
                                            mUserModel.setFullName(firstName + " " + lastName);
//                                            mUserModel.setDateOfBirth(birthday);
//                                            if (gender.equals("male")) {
//                                                mUserModel.setGender(true);
//                                            } else if (gender.equals("female")) {
//                                                mUserModel.setGender(false);
//                                            }
                                            socialLogin(mUserModel);
                                            Gson gson = new Gson();
                                            Log.e("UserModel", gson.toJson(mUserModel));
//                                            Log.e("link", gender);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "hometown,first_name,last_name,email");
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
                            mUserModel.setImage(imageURL + "");
//                            mUserImageImageView.setImageBitmap(bitmap);
                        } catch (IOException ignored) {
                        }


                    }

                    @Override
                    public void onCancel() {
                        activateViews();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        activateViews();
                        Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_SHORT).show();
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
                }
            }
        };

        mResetPasswordTextView.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.first, FragmentSendCode.setEmail(mEmailEditText.getText().toString()));
            transaction.commit();
        });

        mLoginButton.setOnClickListener(v -> {
            if (mEmailEditText.getText().toString().isEmpty()) {
                mEmailEditText.setError(getString(R.string.email_required));
            } else if (mPasswordEditText.getText().toString().isEmpty()) {
                mPasswordEditText.setError(getString(R.string.password_required));
            } else {
                mEmailEditText.setError(null);
                mPasswordEditText.setError(null);

                suspendViews();

                mUserModel = new UserModel();
                mUserModel.setPassword(mPasswordEditText.getText().toString());
                mUserModel.setMail(mEmailEditText.getText().toString());

                login(mUserModel);
            }
        });

        mRegisterTextView.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, RegisterActivity.class));
        });
    }

    private void suspendViews() {
        mResetPasswordTextView.setEnabled(false);
        mLoginButton.setEnabled(false);
        mEmailEditText.setEnabled(false);
        mPasswordEditText.setEnabled(false);
        mGoogleSignInButton.setEnabled(false);
        mFacebookLoginButton.setEnabled(false);
    }

    private void activateViews() {
        mResetPasswordTextView.setEnabled(true);
        mLoginButton.setEnabled(true);
        mEmailEditText.setEnabled(true);
        mPasswordEditText.setEnabled(true);
        mGoogleSignInButton.setEnabled(true);
        mFacebookLoginButton.setEnabled(true);
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
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

            activateViews();
        }
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
                mUserModel.setFullName(userName);
                mUserModel.setImage(userImageUri + "");
                mUserModel.setMail(account.getEmail());
                mUserModel.setSocialId(account.getId());

                socialLogin(mUserModel);
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            activateViews();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            mProfileTracker.stopTracking();
        } catch (Exception ignored) {

        }
    }

    private void login(UserModel userModel) {
        // Map is used to multipart the file using okhttp3.RequestBody
        mLoadingViewConstraintLayout.setVisibility(View.VISIBLE);
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);
        Gson gson = new Gson();
        Log.e("Here", gson.toJson(userModel));
        // Parsing any Media type file

//        mRealm.executeTransaction(realm1 -> {
//            realm1.deleteAll();
//        });

        RetrofitMethods reg = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<LoginDataBase> call = reg.login(gson.toJson(userModel), API.URL_TOKEN, getDeviceToken());
        call.enqueue(new Callback<LoginDataBase>() {
            @Override
            public void onResponse(Call<LoginDataBase> call, retrofit2.Response<LoginDataBase> response) {
                LoginDataBase serverResponse = response.body();
                if (serverResponse != null && serverResponse.getUser().getId() != 0) {
                    loadJoinedProjects(serverResponse.getUser().getId());
                    mRealm.executeTransaction(realm -> realm.insertOrUpdate(serverResponse));
                } else {
                    Toast.makeText(getContext(), getString(R.string.login_failed_try_again), Toast.LENGTH_SHORT).show();
                    mLoadingViewConstraintLayout.setVisibility(View.GONE);
                    activateViews();
                }
            }

            @Override
            public void onFailure(Call<LoginDataBase> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                activateViews();
                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }
        });
    }

    private void socialLogin(UserModel userModel) {
        mLoadingViewConstraintLayout.setVisibility(View.VISIBLE);
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);
        Gson gson = new Gson();
        Log.e("Here", gson.toJson(userModel));
        // Parsing any Media type file

        RetrofitMethods reg = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<LoginDataBase> call = reg.socialLogin(
                gson.toJson(userModel),
                API.URL_TOKEN,
                getDeviceToken(), "null");

        call.enqueue(new Callback<LoginDataBase>() {
            @Override
            public void onResponse(Call<LoginDataBase> call, retrofit2.Response<LoginDataBase> response) {
                LoginDataBase serverResponse = response.body();
                if (serverResponse != null) {
                    loadJoinedProjects(serverResponse.getUser().getId());
                    mRealm.executeTransaction(realm -> realm.insertOrUpdate(serverResponse));
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                    mLoadingViewConstraintLayout.setVisibility(View.GONE);
                }
                activateViews();
            }

            @Override
            public void onFailure(Call<LoginDataBase> call, Throwable t) {
                //textView.setText(t.getMessage());
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                activateViews();
                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }
        });
    }

    private void loadJoinedProjects(int id) {
        RetrofitMethods retrofitMethods = new RetrofitConfigurations(API.BASE_URL).
                getRetrofit().
                create(RetrofitMethods.class);

        Call<List<JoinedProject>> call = retrofitMethods.listJoinedProjects(id, API.URL_TOKEN);

        call.enqueue(new Callback<List<JoinedProject>>() {
            @Override
            public void onResponse(Call<List<JoinedProject>> call, Response<List<JoinedProject>> response) {
                List<JoinedProject> joinedProjectList = response.body();
                if (joinedProjectList != null) {
                    if (joinedProjectList.size() != 0) {
                        mRealm.executeTransaction(realm1 -> {
                            for (JoinedProject project : joinedProjectList) {
                                if (project.getStatus() == null)
                                    project.setStatus(API.Constants.STATUS_ON_HOLD);

                                realm1.insert(new JoinedOfferRealmModel(project));
                            }
                        });
                    }

                    startActivity(new Intent(getActivity(), DrawerActivity.class));
                    getActivity().finish();
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }

                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<JoinedProject>> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }
        });
    }

    private String getDeviceToken() {
        AtomicReference<String> deviceToken = new AtomicReference<>();
        NotificationDatabase database = mRealm.where(NotificationDatabase.class).findFirst();
        if (database != null) {
            deviceToken.set(database.getDevice_FCM_Token());
        } else {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new Instance ID token
                        deviceToken.set(task.getResult().getToken());

                        NotificationDatabase notificationDatabase = new NotificationDatabase(deviceToken.get(), true);
                        mRealm.executeTransaction(realm -> realm.insertOrUpdate(notificationDatabase));
                    });
        }

        return deviceToken.get();
    }
}
