package teamup.rivile.com.teamup.ui.account;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.ui.DrawerActivity;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Services.BroadcastNotificationReceiver;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.Settings;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.UserDataBase;

public class AccountSettingsFragment extends Fragment {
    private ImageView mUserImage;
    private TextView mUserFullNameTextView;

    private EditText mUserEmailEditText;
    private ImageView mEditUserEmail;

    private EditText mNationalIdEditText;
    private ImageView mEditNationalIdImageView;

    private ImageView mNationalImage;
    private ImageView mEditNationalImage;

    private EditText mPasswordEditText;
    private ImageView mEditPasswordImageView;

    private UserModel mUserModel;

    private ProgressDialog mProgressDialog;

    private Switch sNotification;

    private Realm mRealm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_settings,
                container, false);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("جاري التحميل...");

        mUserFullNameTextView = view.findViewById(R.id.tv_user_full_name);
        mUserImage = view.findViewById(R.id.iv_user_image);

        mUserEmailEditText = view.findViewById(R.id.ed_email);
        mEditUserEmail = view.findViewById(R.id.iv_edit_email);

        mNationalIdEditText = view.findViewById(R.id.ed_national_id);
        mEditNationalIdImageView = view.findViewById(R.id.iv_edit_national_id);

        mNationalImage = view.findViewById(R.id.iv_national_image);
        mEditNationalImage = view.findViewById(R.id.civ_edit_national_image);

        mPasswordEditText = view.findViewById(R.id.ed_current_password);
        mEditPasswordImageView = view.findViewById(R.id.iv_edit_password);

        sNotification = view.findViewById(R.id.sNotification);

        loadCurrentUserData();

        if (mUserModel != null) {
            mUserFullNameTextView.setText(mUserModel.getFullName());

            String userImageUrl = mUserModel.getImage();
            if (userImageUrl != null && !userImageUrl.isEmpty()) {
                Picasso.get().load(userImageUrl).into(mUserImage);
            }

            mUserEmailEditText.setText(mUserModel.getMail());

            String nationalId = mUserModel.getIdentityNum();
            if (nationalId != null && !nationalId.isEmpty())
                mNationalIdEditText.setText(mUserModel.getIdentityNum());

            String identityImageUrl = mUserModel.getIdentityImage();
            if (identityImageUrl != null && !identityImageUrl.isEmpty())
                Picasso.get().load(identityImageUrl).into(mNationalImage);

            mPasswordEditText.setText("Nice Try :)");

            setUpClickListeners();
        } else {
            Toast.makeText(getContext(), "فشل تحميل بيانات المستخدم!", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }

        ((DrawerActivity) getActivity()).hideFab();
        ((DrawerActivity) getActivity()).hideSearchBar();
        ((DrawerActivity) getActivity()).setTitle(getString(R.string.edit_account_settings));

        return view;
    }

    private void loadCurrentUserData() {
        mProgressDialog.show();

        mUserModel = new UserModel();
        Realm.init(getActivity());
        mRealm = Realm.getDefaultInstance();

        mRealm.executeTransaction(realm -> {

            Settings settings = realm.where(Settings.class).findFirst();

            if (settings != null &&  String.valueOf(realm.where(Settings.class).findFirst().isNotificaionStatus()).equals("true")){
                getActivity().runOnUiThread(() -> {
                    sNotification.setChecked(true);
                });
            }else {
                getActivity().runOnUiThread(() -> {
                    sNotification.setChecked(false);
                });
            }

            LoginDataBase loginDataBase = realm.where(LoginDataBase.class).findFirst();
            if (loginDataBase != null) {
                UserDataBase userDataBase = loginDataBase.getUser();
                mUserModel.setId(userDataBase.getId());
                mUserModel.setFullName(userDataBase.getFullName());
                mUserModel.setImage(userDataBase.getImage());
                mUserModel.setMail(userDataBase.getMail());
                mUserModel.setIdentityNum(userDataBase.getIdentityNum());
                mUserModel.setIdentityImage(userDataBase.getIdentityImage());
            } else mUserModel = null;

            mProgressDialog.hide();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).hideSearchBar();
        ((DrawerActivity) getActivity()).hideFab();

        sNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (sNotification.isChecked()){
                mRealm.executeTransaction(realm -> {
                    Settings settings = realm.where(Settings.class).findFirst();
                    if (settings == null){
                        Settings s = new Settings();
                        s.setNotificaionStatus(true);
                        realm.insertOrUpdate(s);
                    }else {
                        Log.e("Not",  String.valueOf(settings.isNotificaionStatus()));
                        settings.setNotificaionStatus(true);
                        realm.insertOrUpdate(settings);
                    }

                    Intent broadcastIntent = new Intent(getContext(), BroadcastNotificationReceiver.class);
                    getActivity().sendBroadcast(broadcastIntent);
                });
            }else {
                mRealm.executeTransaction(realm -> {
                    Settings settings = realm.where(Settings.class).findFirst();
                    if (settings == null){
                        Settings s = new Settings();
                        s.setNotificaionStatus(false);
                        realm.insertOrUpdate(s);
                    }else {
                        settings.setNotificaionStatus(false);
                        realm.insertOrUpdate(settings);
                    }

                });
            }
        });

    }

    private void setUpClickListeners() {
        mEditUserEmail.setOnClickListener(v -> showEditDialog(true));
        mNationalIdEditText.setOnClickListener(v -> showEditDialog(false));
        mEditNationalIdImageView.setOnClickListener(v -> mNationalIdEditText.callOnClick());
        mEditNationalImage.setOnClickListener(v -> mNationalIdEditText.callOnClick());
        mEditPasswordImageView.setOnClickListener(v -> mNationalIdEditText.callOnClick());
    }

    public void showEditDialog(boolean isForEditEmail) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.edit_account, null);
        EditText emailEditText = view.findViewById(R.id.ed_email);
        EditText nationalIdEditText = view.findViewById(R.id.ed_national_id);
        ImageView nationalImage = view.findViewById(R.id.iv_national_image);
        EditText passwordEditText = view.findViewById(R.id.ed_current_password);
        EditText newPasswordEditText = view.findViewById(R.id.ed_new_password);
        EditText repeatNewPasswordEditText = view.findViewById(R.id.ed_repeat_new_password);
        Button saveButton = view.findViewById(R.id.btn_save);

        if (isForEditEmail) {
            view.findViewById(R.id.cl_not_email).setVisibility(View.GONE);
            emailEditText.setText(mUserModel.getMail());
        } else {
            view.findViewById(R.id.tv_email_header).setVisibility(View.GONE);
            emailEditText.setVisibility(View.GONE);

            nationalIdEditText.setText(mUserModel.getIdentityNum());

            String identityImageUrl = mUserModel.getIdentityImage();
            if (identityImageUrl != null && !identityImageUrl.isEmpty()) {
                Picasso.get().load(identityImageUrl).into(nationalImage);
            }
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false)
                .setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(v -> {
            mProgressDialog.show();
            UserModel userModel = new UserModel();
            userModel.setId(mUserModel.getId());

            String password = passwordEditText.getText().toString();
            if (!password.isEmpty()) {
                if (isForEditEmail) {
                    String email = emailEditText.getText().toString();
                    if (isValidEmail(email)) {
                        userModel.setPassword(password);
                        userModel.setMail(email);

                        updateEmail(userModel);
                        dialog.dismiss();
                    } else
                        Toast.makeText(getContext(), "البريد الإلكتروني الجديد غير صحيح", Toast.LENGTH_SHORT).show();
                } else {
                    userModel.setIdentityNum(nationalIdEditText.getText().toString());

                    //TODO: National Id Image Upload

                    String newPassword = newPasswordEditText.getText().toString();
                    if (repeatNewPasswordEditText.getText().toString().equals(newPassword)) {
                        userModel.setPassword(newPassword);

                        updateOtherData(userModel, password);
                        dialog.dismiss();
                    } else
                        Toast.makeText(getContext(), "تأكد من تطابق كلمة المرور الجديدة", Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(getContext(), "كلمة المرور مطلوبة", Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.iv_cancel).setOnClickListener(v -> dialog.dismiss());
    }

    private void updateEmail(UserModel userModel) {
        final ApiConfig apiConfig = new AppConfig(API.BASE_URL).getRetrofit().create(ApiConfig.class);
        Call<String> call = apiConfig.mailReset(
                new Gson().toJson(userModel),
                API.URL_TOKEN);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                mProgressDialog.hide();

                if (response.errorBody() == null) {
                    if (response.body() != null && !response.body().isEmpty()) {
                        showCodeConfirmationDialog(userModel.getId(), userModel.getMail());
                    } else {
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mProgressDialog.hide();

                Toast toast = new Toast(getActivity());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setView(LayoutInflater.from(getContext()).inflate(R.layout.connection_error, null));
                toast.show();
            }
        });
    }

    private void updateOtherData(UserModel userModel, String currentPassword) {
        final ApiConfig apiConfig = new AppConfig(API.BASE_URL).getRetrofit().create(ApiConfig.class);
        Call<String> call = apiConfig.accountSettings(
                new Gson().toJson(userModel),
                currentPassword,
                API.URL_TOKEN);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                mProgressDialog.hide();

                if (response.errorBody() == null) {
                    if (response.body() != null) {
                        if (response.body().equals("Success")) {
                            Toast.makeText(getContext(), "تم التعديل", Toast.LENGTH_SHORT).show();
                            mNationalIdEditText.setText(userModel.getIdentityNum());
                            //TODO: update database
                            mRealm.executeTransaction(realm -> {
                                UserDataBase userDataBase = realm.where(UserDataBase.class).equalTo("Id", userModel.getId()).findFirst();
                                if(userDataBase != null){
                                    userDataBase.setIdentityNum(userModel.getIdentityNum());
                                    userDataBase.setIdentityImage(userModel.getIdentityImage());
                                    realm.insertOrUpdate(userDataBase);
                                }
                            });

                        } else if (response.body().equals("IncorectPassword")) {
                            Toast.makeText(getContext(), "كلمة مرور خاطئة", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mProgressDialog.hide();

                Toast toast = new Toast(getActivity());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setView(LayoutInflater.from(getContext()).inflate(R.layout.connection_error, null));
                toast.show();
            }
        });
    }

    public boolean isValidEmail(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void showCodeConfirmationDialog(int userId, String newEmail) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_confirm_code, null);

        EditText n1 = view.findViewById(R.id.n1);
        EditText n3 = view.findViewById(R.id.n3);
        EditText n2 = view.findViewById(R.id.n2);
        EditText n4 = view.findViewById(R.id.n4);
        EditText n5 = view.findViewById(R.id.n5);
        EditText n6 = view.findViewById(R.id.n6);
        Button confirm = view.findViewById(R.id.btn_save);

        n1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    n2.requestFocus();
                } else if (s.toString().length() > 1) {
                    n1.setText(s.toString().substring(1));
                }
            }
        });

        n2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    n3.requestFocus();
                } else if (s.toString().length() > 1) {
                    n2.setText(s.toString().substring(1));
                }
            }
        });

        n3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    n4.requestFocus();
                } else if (s.toString().length() > 1) {
                    n3.setText(s.toString().substring(1));
                }
            }
        });

        n4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    n5.requestFocus();
                } else if (s.toString().length() > 1) {
                    n4.setText(s.toString().substring(1));
                }
            }
        });

        n5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    n6.requestFocus();
                } else if (s.toString().length() > 1) {
                    n5.setText(s.toString().substring(1));
                }
            }
        });

        n6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    //n6.setText(s.toString().substring(1));
                    confirm.setEnabled(true);
                    confirm.setBackgroundResource(R.drawable.rounded_corner_button_blue);
                } else {
                    confirm.setEnabled(false);
                    confirm.setBackgroundResource(R.drawable.rounded_corner_button_gray);
                }
            }
        });

        view.findViewById(R.id.resend).setVisibility(View.GONE);
        view.findViewById(R.id.back).setVisibility(View.GONE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false)
                .setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        confirm.setOnClickListener(v -> {
            mProgressDialog.show();

            String code = n1.getText().toString() + n2.getText().toString() + n3.getText().toString() + n4.getText().toString() + n5.getText().toString() + n6.getText().toString();

            confirmCode(userId, newEmail, code);
            dialog.dismiss();
        });

    }

    public void confirmCode(int userId, String newEmail, String code) {
        final ApiConfig apiConfig = new AppConfig(API.BASE_URL).getRetrofit().create(ApiConfig.class);
        Call<String> call = apiConfig.cheakCodeMail(userId, newEmail, code, API.URL_TOKEN);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                mProgressDialog.hide();

                if (response.errorBody() == null) {
                    if (response.body() != null) {
                        if (response.body().equals(String.valueOf(userId))) {
                            Toast.makeText(getContext(), "تم التعديل", Toast.LENGTH_SHORT).show();
                            mUserEmailEditText.setText(newEmail);
                            //TODO: update database
                            mRealm.executeTransactionAsync(realm -> {
                                UserDataBase userDataBase = realm.where(UserDataBase.class).equalTo("Id", userId).findFirst();
                                if(userDataBase != null){
                                    userDataBase.setMail(newEmail);
                                    realm.insertOrUpdate(userDataBase);
                                }
                            });

                        } else if (response.body().equals("0")) {
                            Toast.makeText(getContext(), "تأكد من الكود وحاول مره اخري", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mProgressDialog.hide();

                Toast toast = new Toast(getActivity());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setView(LayoutInflater.from(getContext()).inflate(R.layout.connection_error, null));
                toast.show();
            }
        });
    }
}