package teamup.rivile.com.teamup.ui.ForgetPassword;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.ui.FragmentLogin;
import teamup.rivile.com.teamup.R;

public class FragmentSendCode extends Fragment {
    View view;
    static String em;
    EditText email;
    Button recover;
    ImageView back;
    public static FragmentSendCode setEmail(String email){
        em = email;
        return new FragmentSendCode();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        email = view.findViewById(R.id.ed_email);
        recover = view.findViewById(R.id.btn_save);
        back = view.findViewById(R.id.back);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        email.setText(em);

        back.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.first, new FragmentLogin());
            transaction.commit();
        });

        recover.setOnClickListener(v -> {
            if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                forgetPassword(email.getText().toString());
            }else {
                Snackbar.make(view, R.string.wrongEmailPattern, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void forgetPassword(String mail) {
        Snackbar.make(view, R.string.processing, Snackbar.LENGTH_LONG).show();

        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);
        // Parsing any Media type file

        RetrofitMethods reg = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<Integer> call = reg.ForgetPassword(mail, API.URL_TOKEN);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                Integer serverResponse = response.body();
                if (serverResponse != null) {
                    Log.i("Response",serverResponse+"");
                    if (serverResponse != 0) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.first, FragmentConfirmCode.setId(serverResponse, mail));//Get Id from API
                        transaction.commit();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(getString(R.string.canotFindEmail))
                                .setIcon(R.drawable.broken_connection)
                                .setCancelable(true)
                                .create().show();
                    }
                } else {
                    //textView.setText(serverResponse.toString());
                    Log.e("Err","Empty");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Err",t.getMessage());
            }
        });
    }
}