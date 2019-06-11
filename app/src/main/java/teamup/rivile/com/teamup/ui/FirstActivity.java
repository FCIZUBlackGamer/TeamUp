package teamup.rivile.com.teamup.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import teamup.rivile.com.teamup.R;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Realm.init(this);
        FirebaseApp.initializeApp(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);

        setContentView(R.layout.activity_first);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.first, new FragmentLogin()).commit();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
