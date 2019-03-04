package teamup.rivile.com.teamup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_first);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.first, new Login()).commit();
    }
}
