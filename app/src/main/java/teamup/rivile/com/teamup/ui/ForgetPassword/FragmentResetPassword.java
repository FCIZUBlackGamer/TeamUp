package teamup.rivile.com.teamup.ui.ForgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.google.gson.Gson;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.ui.DrawerActivity;
import teamup.rivile.com.teamup.ui.FragmentLogin;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;

public class FragmentResetPassword extends Fragment {
    View view;
    static int id;
    EditText ed_pass, ed_conPass;
    Button reset;
    ImageView back;
    Realm realm;
    public static FragmentResetPassword setId(int ids){
        id = ids;
        return new FragmentResetPassword();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ed_pass = view.findViewById(R.id.ed_password);
        ed_conPass = view.findViewById(R.id.ed_conPassword);
        reset = view.findViewById(R.id.btn_save);
        back = view.findViewById(R.id.back);
        realm = Realm.getDefaultInstance();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        back.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.first, new FragmentLogin());
            transaction.commit();
        });
        ed_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isDidicated(ed_pass.getText().toString(),ed_conPass.getText().toString() )){
                    reset.setEnabled(true);
                    reset.setBackgroundResource(R.drawable.rounded_corner_button_blue);
                }else {
                    reset.setEnabled(false);
                    reset.setBackgroundResource(R.drawable.rounded_corner_button_gray);
                }
            }
        });

        ed_conPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isDidicated(ed_pass.getText().toString(),ed_conPass.getText().toString() )){
                    reset.setEnabled(true);
                    reset.setBackgroundResource(R.drawable.rounded_corner_button_blue);
                }else {
                    reset.setEnabled(false);
                    reset.setBackgroundResource(R.drawable.rounded_corner_button_gray);
                }
            }
        });
        reset.setOnClickListener(v -> {
            reset.setEnabled(false);
            Snackbar.make(view, R.string.processing, Snackbar.LENGTH_LONG).show();
            /**Connect To API with id*/
            if (ed_conPass.getText().toString().equals(ed_pass.getText().toString())){
                login(id, ed_conPass.getText().toString());
            }else {
                //Todo: showSearchBar Error Password
            }
        });
    }

    private boolean isDidicated(String f, String s){
        boolean res = false;
        if (f.equals(s)){
            res = true;
        }
        return res;
    }
    private void login(int id, String password) {
        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);
        // Parsing any Media type file

        realm.executeTransaction(realm1 -> {
            realm1.deleteAll();
        });
        Gson gson = new Gson();

        RetrofitMethods reg = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<LoginDataBase> call = reg.SavePasswordLogin(id, password, API.URL_TOKEN);
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
                    reset.setEnabled(true);

                    //textView.setText(serverResponse.toString());
                    Log.e("Err", "Empty");
                    Toast toast = new Toast(getActivity());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM,0,0);
                    toast.setView(LayoutInflater.from(getContext()).inflate(R.layout.connection_error, null));
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<LoginDataBase> call, Throwable t) {
                reset.setEnabled(true);

                //textView.setText(t.getMessage());
                Log.e("Err", t.getMessage());
                Toast toast = new Toast(getActivity());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.setView(LayoutInflater.from(getContext()).inflate(R.layout.connection_error, null));
                toast.show();
            }
        });
    }
}
