package teamup.rivile.com.teamup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ForgetPassword extends Fragment {
    View view;
    static String em;
    EditText email;
    Button recover;
    ImageView back;
    public static ForgetPassword setEmail(String email){
        em = email;
        return new ForgetPassword();
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
            transaction.replace(R.id.first, new Login());
            transaction.commit();
        });
        recover.setOnClickListener(v -> {

        });
    }
}
