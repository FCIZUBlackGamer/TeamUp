package teamup.rivile.com.teamup.ui.ForgetPassword;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.network.repository.AppNetworkRepository;
import teamup.rivile.com.teamup.network.retrofit.RetrofitMethods;
import teamup.rivile.com.teamup.network.retrofit.RetrofitConfigurations;
import teamup.rivile.com.teamup.ui.FragmentLogin;
import teamup.rivile.com.teamup.R;

public class FragmentSendCode extends Fragment {
    private View view;
    private static String mLoadedEmail;
    private EditText mEmailEditText;
    private Button mRecoverButton;
    private ImageView mBackImageView;

    private AppNetworkRepository mNetworkRepository;

    public static FragmentSendCode setEmail(String email) {
        mLoadedEmail = email;
        return new FragmentSendCode();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        mEmailEditText = view.findViewById(R.id.ed_email);
        mRecoverButton = view.findViewById(R.id.btn_save);
        mBackImageView = view.findViewById(R.id.back);

        mNetworkRepository = AppNetworkRepository.getInstance(getActivity().getApplication());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mEmailEditText.setText(mLoadedEmail);

        mBackImageView.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.first, new FragmentLogin());
            transaction.commit();
        });

        mRecoverButton.setOnClickListener(v -> {
            if (Patterns.EMAIL_ADDRESS.matcher(mEmailEditText.getText().toString()).matches()) {
                forgetPassword(mEmailEditText.getText().toString());
                mRecoverButton.setEnabled(false);
            } else {
                Snackbar.make(view, R.string.wrongEmailPattern, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void forgetPassword(String mail) {
        Snackbar.make(view, R.string.processing, Snackbar.LENGTH_LONG).show();

        mNetworkRepository.forgetPassword(mail)
                .observe(this, serverResponse -> {
                    if (serverResponse != null) {
                        Log.i("Response", serverResponse + "");
                        if (serverResponse != 0) {
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                            transaction.replace(R.id.first, FragmentConfirmCode.setId(serverResponse, mail));//Get Id from API
                            transaction.commit();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(getString(R.string.canotFindEmail))
                                    .setIcon(R.drawable.broken_connection)
                                    .setCancelable(true)
                                    .create().show();
                        }
                    } else {
                        //textView.setText(serverResponse.toString());
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }

                    mRecoverButton.setEnabled(true);
                });
    }
}