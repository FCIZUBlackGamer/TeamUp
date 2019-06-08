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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.ui.FragmentLogin;
import teamup.rivile.com.teamup.R;

public class FragmentConfirmCode extends Fragment {
    View view;
    static int id;
    static String mail;
    EditText n1, n2, n3, n4, n5, n6;
    Button confirm;
    ImageView back, resend;

    public static FragmentConfirmCode setId(int ids, String m) {
        id = ids;
        mail = m;
        return new FragmentConfirmCode();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirm_code, container, false);
        n1 = view.findViewById(R.id.n1);
        n2 = view.findViewById(R.id.n2);
        n3 = view.findViewById(R.id.n3);
        n4 = view.findViewById(R.id.n4);
        n5 = view.findViewById(R.id.n5);
        n6 = view.findViewById(R.id.n6);
        confirm = view.findViewById(R.id.btn_save);
        back = view.findViewById(R.id.back);
        resend = view.findViewById(R.id.resend);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

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
                } else if(s.toString().length() > 1){
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
                } else if(s.toString().length() > 1){
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
                } else if(s.toString().length() > 1){
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
                } else if(s.toString().length() > 1){
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
                } else if(s.toString().length() > 1){
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
               if(s.toString().length() == 1){
                   //n6.setText(s.toString().substring(1));
                   confirm.setEnabled(true);
                   confirm.setBackgroundResource(R.drawable.rounded_corner_button_blue);
                }else {
                   confirm.setEnabled(false);
                   confirm.setBackgroundResource(R.drawable.rounded_corner_button_gray);
                   n6.setText(s.toString().substring(1));
               }
            }
        });

        back.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.first, new FragmentLogin());
            transaction.commit();
        });

        confirm.setOnClickListener(v -> {
            /** Make The Action*/
            String code = n1.getText().toString() + n2.getText().toString() + n3.getText().toString() + n4.getText().toString() + n5.getText().toString() + n6.getText().toString();
            confirmCode(code);
        });

        resend.setOnClickListener(v -> {
            /** Send the Code Again*/
            Snackbar.make(view, R.string.processing, Snackbar.LENGTH_LONG).show();
            forgetPassword(mail);
        });
    }

    private void confirmCode(String code) {
        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);
        // Parsing any Media type file

        RetrofitMethods reg = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<Integer> call = reg.CheakCode(code, API.URL_TOKEN);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                Integer serverResponse = response.body();
                if (serverResponse != null) {
                    Log.i("Response", serverResponse + "");
                    if (serverResponse != 0) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.first, FragmentResetPassword.setId(serverResponse));// id from response webService
                        transaction.commit();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(getString(R.string.wrongCode))
                                .setIcon(R.drawable.broken_connection)
                                .setCancelable(true)
                                .create().show();
                    }
                } else {
                    //textView.setText(serverResponse.toString());
                    Log.e("Err", "Empty");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Err", t.getMessage());
            }
        });
    }

    private void forgetPassword(String mail) {
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
                    Log.i("Response", serverResponse + "");
                    id = serverResponse;
                } else {
                    //textView.setText(serverResponse.toString());
                    Log.e("Err", "Empty");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Err", t.getMessage());
            }
        });
    }
}
