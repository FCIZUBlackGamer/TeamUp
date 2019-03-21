package teamup.rivile.com.teamup;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.Department.FragmentHome;
import teamup.rivile.com.teamup.GoMap.GoMap;
import teamup.rivile.com.teamup.GoMap.MovableFloatingActionButton;
import teamup.rivile.com.teamup.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.Project.Add.FragmentAddHome;
import teamup.rivile.com.teamup.Project.IncommingRequirement.FragmentIncommingRequirement;
import teamup.rivile.com.teamup.Project.List.FragmentListProjects;
import teamup.rivile.com.teamup.Project.join.FragmentJoinHome;
import teamup.rivile.com.teamup.Search.FilterSearchFragment;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.UserDataBase;
import teamup.rivile.com.teamup.account.AccountSettingsFragment;

import static teamup.rivile.com.teamup.Project.List.FragmentListProjects.MINE;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    BottomNavigationView navigation;
    FragmentManager fragmentManager;
    static SearchView searchView;
    static String Whome = "Home";
    static Toolbar toolbar;
    static DrawerLayout drawer;

    static FloatingActionButton fab;
    MovableFloatingActionButton movableFloatingActionButton;
    static FloatingActionButton civ_filter;

    Drawable filterDrawable, filterFABDrawable;
    static ImageView iv_cancel;

    RecyclerView locs;
    RecyclerView.Adapter adapter;
    Realm realm;
    int userId = 0;
    TextView image_name
    int userState = 0;
    /**
     * nav views
     */
    CircleImageView user_image;
    TextView user_name;
    TextView user_num_projects;

    private boolean mIsCurrentFragmentIsHomeFragment = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setVisibility(View.VISIBLE);
                    navigationView.setCheckedItem(R.id.nav_home);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new FragmentHome())
                            .commit();
                    return true;
                case R.id.navigation_go_map:
                    toolbar.setVisibility(View.GONE);
                    navigationView.setCheckedItem(R.id.nav_go_map);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new GoMap())
                            .commit();
                    return true;
                case R.id.navigation_saved_project:
                    toolbar.setVisibility(View.VISIBLE);
                    navigationView.setCheckedItem(R.id.nav_saved_project);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new FragmentListProjects().setType(MINE))
                            .commit();
                    return true;
                case R.id.navigation_favourite_projects:
                    toolbar.setVisibility(View.VISIBLE);
                    navigationView.setCheckedItem(R.id.nav_favourite_projects);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, FragmentJoinHome.setOfferId(2))
                            .commit();
                    return true;
                case R.id.navigation_profile:
                    toolbar.setVisibility(View.VISIBLE);
                    navigationView.setCheckedItem(R.id.nav_profile);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new FragmentProfileHome())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.home));
        setSupportActionBar(toolbar);

        searchView = findViewById(R.id.search);

        Realm.init(this);
        realm = Realm.getDefaultInstance();


        fab = findViewById(R.id.addOffer);
        fab.setVisibility(View.VISIBLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (Whome.equals("Home")) {/** Means Current fragment is home*/
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, FragmentHome.setWord(s)).addToBackStack(FragmentHome.class.getSimpleName())
                            .commit();
                } else if (Whome.equals("ListProjects")) {/** Means Current fragment is ListProjects*/
                    if (s.startsWith("#")) {
                        String word = s.replace("#", "");
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame, FragmentListProjects.setWord(1, word))
                                .commit();
                    } else if (s.startsWith("@")) {
                        String word = s.replace("@", "");
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame, FragmentListProjects.setWord(2, word))
                                .commit();
                    } else {
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame, FragmentListProjects.setWord(0, s))
                                .commit();
                    }
                } else if (Whome.equals("MyProjects")) {/** Means Current fragment is MyProjects*/
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, teamup.rivile.com.teamup.Project.MyProjects.FragmentListProjects.setWord(s))
                            .commit();

                } else if (Whome.equals("Favourite")) {/** Means Current fragment is Favourite*/
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.frame, teamup.rivile.com.teamup.Project.Favourite.FragmentListProjects.setWord(s))
//                            .commit();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                return false;
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        user_image = header.findViewById(R.id.imageView);
        image_name = header.findViewById(R.id.image_name);
        user_name = header.findViewById(R.id.user_name);
        user_num_projects = header.findViewById(R.id.num_projects);

        realm.executeTransaction(realm1 -> {
            UserDataBase User = realm1.where(LoginDataBase.class).findFirst().getUser();
            user_num_projects.setText(" " + getResources().getString(R.string.nav_header_subtitle) + " " + User.getNumProject());
            userId = User.getId();
            String[] name = User.getFullName().split(" ");
            user_name.setText(name[0]);
//            user_name.setText(User.getFullName());
            if (User.getImage() != null && !User.getImage().isEmpty()) {
                try {
                    if (User.getSocialId() != null){
                        Picasso.get().load(User.getImage()).into(user_image);
                    }else {
                        Picasso.get().load(API.BASE_URL + User.getImage()).into(user_image);
                    }
                    image_name.setVisibility(View.GONE);
                } catch (Exception e) {
                    image_name.setVisibility(View.VISIBLE);
                    String[] sp = User.getFullName().split(" ");
                    if (!User.getFullName().contains(" ")){
                        image_name.setText(User.getFullName().charAt(0)+"");
                    }else if (sp.length > 0 && sp.length <= 2) {
                        for (int j = 0; j < sp.length; j++) {
                            image_name.append(sp[j]+"");
                        }
                    }else if (sp.length > 2){
                        for (int j = 0; j < 2; j++) {
                            image_name.append(sp[j]+"");
                        }
                    }
                }
            }
        });

        user_image.setOnClickListener(v -> {
            if (userId != 0) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,
                        FragmentProfileHome.setId(userId)).commit();
                controlNavDrawer();
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentProfileHome.class.getSimpleName());
            }
        });
        user_name.setOnClickListener(v -> {
            if (userId != 0) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,
                        FragmentProfileHome.setId(userId)).commit();
                controlNavDrawer();
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentProfileHome.class.getSimpleName());
            }
        });
        user_num_projects.setOnClickListener(v -> {
            if (userId != 0) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,
                        FragmentProfileHome.setId(userId)).commit();
                controlNavDrawer();
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentProfileHome.class.getSimpleName());
            }
        });

        /**
         * handel Action press on (image, name, numProjects) in navigator
         * **/
        header.setOnClickListener(v -> {
            realm.executeTransaction(realm1 -> {
                LoginDataBase loginDataBases = realm1.where(LoginDataBase.class)
                        .findFirst();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,
                        FragmentProfileHome.setId(loginDataBases.getUser().getId()));
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentProfileHome.class.getSimpleName());
            });
        });

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.nav_home);
        navigationView.setCheckedItem(R.id.navigation_home);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame, new FragmentHome())
                .commit();
        mIsCurrentFragmentIsHomeFragment = true;
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        realm.executeTransaction(realm1 -> {
//            LoginDataBase loginDataBase = realm1.where(LoginDataBase.class).findFirst();
//
//            Log.e("results", loginDataBase.getUser().getId()+"");
//            Log.e("size", loginDataBase.getOffers().size()+"");
//            for (int i = 0; i < loginDataBase.getOffers().size(); i++) {
//                Log.e("Offer Name "+loginDataBase.getOffers().get(i).getId(),
//                        loginDataBase.getOffers().get(i).getName()+"");
//            }
//
//        });

        realm.executeTransaction(realm1 -> {
            userState = realm1.where(LoginDataBase.class).findFirst().getUser().getStatus();
        });

        fab.setOnClickListener(view -> {
            if (userState != 1) {
                //TODO: Show user alert dialog of what's happening here
                Toast.makeText(this, "لم يتم تفعيل حسابك بشكل كامل بعد.", Toast.LENGTH_SHORT).show();
            } else {
                /** Half Pizza Animation */
                hideSearchBar();
                fab.setVisibility(View.GONE);
                fragmentManager.beginTransaction().replace(R.id.frame, FragmentAddHome.setFab(fab))
                        .addToBackStack(FragmentAddHome.class.getSimpleName()).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
            }
//            super.onBackPressed();
            if (fragmentManager.getBackStackEntryCount() == 0) {
                mIsCurrentFragmentIsHomeFragment = true;
                fragmentManager.beginTransaction().replace(R.id.frame, new FragmentHome()).commit();
                showToolbar();
            }
        }
    }

    public void initNav() {
        civ_filter = findViewById(R.id.fab_filter);

        iv_cancel = findViewById(R.id.iv_cancel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        locs = findViewById(R.id.recyclerView);

        //Todo: get list of locations from Api and pass to 'locs'

    }

    public void actionNav() {

        civ_filter.setOnClickListener(v -> {

            if (civ_filter.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_projects).getConstantState()) {
                civ_filter.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_marker));
                Nav();
                loadProjectsData();
            } else {
                civ_filter.setImageDrawable(getResources().getDrawable(R.drawable.ic_projects));
                Nav();
                loadUsersLocationsData();
            }

            //Todo: Reload recycle view data


        });

        iv_cancel.setOnClickListener(v -> opOrCl());
    }

    public void opOrCl() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);

        }
    }

    public void controlNavDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void loadUsersLocationsData() {
        //Todo
    }

    private void loadProjectsData() {
        //Todo
    }

    public void Nav() {

        if (civ_filter.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_projects).getConstantState()) {

            loadProjectsData();

        } else {

            loadUsersLocationsData();
        }
    }

    public static void hideSearchBar() {
        searchView.setVisibility(View.GONE);
    }

    public static void showSearchBar(String whome) {
        searchView.setVisibility(View.VISIBLE);
        Whome = whome;
    }

    public static void hideFab() {
        fab.setVisibility(View.GONE);
    }

    public static void showFab() {
        fab.setVisibility(View.VISIBLE);
    }

    public static void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    public static void showToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_advanced_search) {
            Log.e("Search", "H");
            fragmentTransaction.replace(R.id.frame, new FilterSearchFragment()).addToBackStack(FilterSearchFragment.class.getSimpleName()).commit();
            return true;
        } else if (id == R.id.action_settings) {
            hideFab();
            fragmentTransaction.replace(R.id.frame, new AccountSettingsFragment()).addToBackStack(AccountSettingsFragment.class.getSimpleName()).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean isCurrentFragmentIsHomeFragment = false;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (id) {
            case R.id.nav_home:
                showToolbar();
                navigation.setSelectedItemId(R.id.navigation_home);

                fragmentTransaction.replace(R.id.frame, new FragmentHome());

                isCurrentFragmentIsHomeFragment = true;
                break;

            case R.id.nav_go_map:
                hideToolbar();
                navigation.setSelectedItemId(R.id.navigation_go_map);

                fragmentTransaction.replace(R.id.frame, new GoMap());
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(GoMap.class.getSimpleName());
                break;

            case R.id.nav_saved_project: // internal db
                showToolbar();
                navigation.setSelectedItemId(R.id.navigation_saved_project);

                fragmentTransaction.replace(R.id.frame, new teamup.rivile.com.teamup.Project.MyProjects.FragmentListProjects());
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentListProjects.class.getSimpleName());
                break;

            case R.id.nav_requirement: // internal db
                showToolbar();
//                navigation.setSelectedItemId(R.id.navigation_saved_project);

                fragmentTransaction.replace(R.id.frame, new FragmentIncommingRequirement());
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentIncommingRequirement.class.getSimpleName());
                break;

            case R.id.nav_favourite_projects: // internal db
                showToolbar();
                navigation.setSelectedItemId(R.id.navigation_favourite_projects);

                fragmentTransaction.replace(R.id.frame, new teamup.rivile.com.teamup.Project.Favourite.FragmentListProjects());
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentListProjects.class.getSimpleName());
                break;

            case R.id.nav_profile:
                showToolbar();
                realm.executeTransaction(realm1 -> {
                    LoginDataBase loginDataBases = realm1.where(LoginDataBase.class)
                            .findFirst();
                    navigation.setSelectedItemId(R.id.navigation_profile);

                    fragmentTransaction.replace(R.id.frame,
                            FragmentProfileHome.setId(loginDataBases.getUser().getId()));
                    if (mIsCurrentFragmentIsHomeFragment)
                        fragmentTransaction.addToBackStack(FragmentProfileHome.class.getSimpleName());
                });

                break;

            case R.id.nav_sign:
                realm.executeTransaction(realm1 -> {
                    RealmResults<LoginDataBase> results = realm.where(LoginDataBase.class).findAll();
                    results.deleteAllFromRealm();
//                finish();
                    startActivity(new Intent(DrawerActivity.this, FirstActivity.class));
                    this.finish();
                });

                break;
        }

        fragmentTransaction.commit();

//        if (isCurrentFragmentIsHomeFragment && !mIsCurrentFragmentIsHomeFragment) {
//            fragmentManager.popBackStack();
//        }
        mIsCurrentFragmentIsHomeFragment = isCurrentFragmentIsHomeFragment;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setTitle(String name) {
        toolbar.setTitle(name);
    }

    public void updateNavData(UserModel userModel) {
        if (userModel != null) {
            if (userModel.getImage() != null && !userModel.getImage().isEmpty()) {
                Picasso.get().load(API.BASE_URL + userModel.getImage()).into(user_image);
            }

            String[] name = userModel.getFullName().split(" ");
            user_name.setText(name[0]);

        }
    }
}
