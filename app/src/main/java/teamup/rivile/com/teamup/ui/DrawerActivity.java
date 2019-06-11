package teamup.rivile.com.teamup.ui;

import android.content.Intent;
import android.os.Bundle;
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

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.FavouriteDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.JoinedOfferRealmModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDataBase;
import teamup.rivile.com.teamup.ui.Department.FragmentHome;
import teamup.rivile.com.teamup.ui.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.ui.Project.Add.FragmentAddHome;
//import teamup.rivile.com.teamup.Project.IncommingRequirement.FragmentIncommingRequirement;
import teamup.rivile.com.teamup.ui.Project.List.FragmentListProjects;
import teamup.rivile.com.teamup.ui.Search.FilterSearchFragment;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.UserDataBase;
import teamup.rivile.com.teamup.ui.account.AccountSettingsFragment;
import teamup.rivile.com.teamup.ui.projectsJoinRequests.ListJoinedProjectsFragment;

import static teamup.rivile.com.teamup.ui.Project.List.FragmentListProjects.MINE;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    static SearchView mSearchView;
    static String HOME_STRING = "Home";
    static Toolbar mToolbar;
    static DrawerLayout mDrawer;

    static FloatingActionButton mFilterFloatingActionButton;

    static ImageView mCancelImageView;

    private RecyclerView locs;
    private RecyclerView.Adapter adapter;
    private Realm realm;
    private int userId = 0;
    private TextView image_name;
    private int userState = 0;
    /**
     * nav views
     */
    private CircleImageView user_image;
    private TextView user_name;
    private TextView user_num_projects;

    private boolean mIsCurrentFragmentIsHomeFragment = false;

    private LoginDataBase mLoginDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.home));
        setSupportActionBar(mToolbar);

        mSearchView = findViewById(R.id.search);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (HOME_STRING.equals("Home")) {/** Means Current fragment is home*/
                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame, FragmentHome.setWord(s)).addToBackStack(FragmentHome.class.getSimpleName())
                            .commit();
                } else if (HOME_STRING.equals("ListProjects")) {/** Means Current fragment is ListProjects*/
                    if (s.startsWith("#")) {
                        String word = s.replace("#", "");
                        mFragmentManager.beginTransaction()
                                .replace(R.id.frame, FragmentListProjects.setWord(1, word))
                                .commit();
                    } else if (s.startsWith("@")) {
                        String word = s.replace("@", "");
                        mFragmentManager.beginTransaction()
                                .replace(R.id.frame, FragmentListProjects.setWord(2, word))
                                .commit();
                    } else {
                        mFragmentManager.beginTransaction()
                                .replace(R.id.frame, FragmentListProjects.setWord(0, s))
                                .commit();
                    }
                } else if (HOME_STRING.equals("MyProjects")) {/** Means Current fragment is MyProjects*/
                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame, FragmentListProjects.setWord(1, s))
                            .commit();

                } else if (HOME_STRING.equals("Favourite")) {/** Means Current fragment is Favourite*/
//                    mFragmentManager.beginTransaction()
//                            .replace(R.id.frame, teamup.rivile.com.teamup.Project.Favourite.FragmentListProjectNames.setWord(s))
//                            .commit();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                return false;
            }
        });
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        View header = mNavigationView.getHeaderView(0);
        user_image = header.findViewById(R.id.imageView);
        image_name = header.findViewById(R.id.image_name);
        user_name = header.findViewById(R.id.user_name);
        user_num_projects = header.findViewById(R.id.num_projects);

        realm.executeTransaction(realm1 -> {
            mLoginDataBase = realm1.where(LoginDataBase.class).findFirst();
            if (mLoginDataBase != null) {
                UserDataBase User = mLoginDataBase.getUser();
                user_num_projects.setText(" " + getResources().getString(R.string.nav_header_subtitle) + " " + User.getNumProject());
                userId = User.getId();
                String[] name = User.getFullName().split(" ");
                user_name.setText(name[0]);
//            user_name.setText(User.getFullName());
                if (User.getImage() != null && !User.getImage().isEmpty()) {
                    try {
                        if (User.getSocialId() != null) {
                            Picasso.get().load(User.getImage()).into(user_image);
                        } else {
                            Picasso.get().load(API.BASE_URL + User.getImage()).into(user_image);
                        }
                        image_name.setVisibility(View.GONE);
                    } catch (Exception e) {
                        image_name.setVisibility(View.VISIBLE);
                        String[] sp = User.getFullName().split(" ");
                        if (!User.getFullName().contains(" ")) {
                            image_name.setText(User.getFullName().charAt(0) + "");
                        } else if (sp.length > 0 && sp.length <= 2) {
                            for (int j = 0; j < sp.length; j++) {
                                image_name.append(sp[j] + "");
                            }
                        } else if (sp.length > 2) {
                            for (int j = 0; j < 2; j++) {
                                image_name.append(sp[j] + "");
                            }
                        }
                    }
                }
            }
        });

        user_image.setOnClickListener(v -> {
            if (userId != 0) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,
                        FragmentProfileHome.setId(userId)).commit();
                controlNavDrawer();
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentProfileHome.class.getSimpleName());
            }
        });
        user_name.setOnClickListener(v -> {
            if (userId != 0) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,
                        FragmentProfileHome.setId(userId)).commit();
                controlNavDrawer();
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentProfileHome.class.getSimpleName());
            }
        });
        user_num_projects.setOnClickListener(v -> {
            if (userId != 0) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
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

                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,
                        FragmentProfileHome.setId(loginDataBases.getUser().getId()));
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentProfileHome.class.getSimpleName());
            });
        });

        mNavigationView.setCheckedItem(R.id.navigation_home);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.frame, new FragmentHome())
                .commit();
        mIsCurrentFragmentIsHomeFragment = true;
    }

    @Override
    protected void onStart() {
        super.onStart();

//        realm.executeTransaction(realm1 -> {
//            LoginDataBase mLoginDataBase = realm1.where(LoginDataBase.class).findFirst();
//
//            Log.e("results", mLoginDataBase.getUser().getId()+"");
//            Log.e("size", mLoginDataBase.getOffers().size()+"");
//            for (int i = 0; i < mLoginDataBase.getOffers().size(); i++) {
//                Log.e("Offer Name "+mLoginDataBase.getOffers().get(i).getId(),
//                        mLoginDataBase.getOffers().get(i).getName()+"");
//            }
//
//        });

        realm.executeTransaction(realm1 -> {
            if (mLoginDataBase != null) {
                userState = mLoginDataBase.getUser().getStatus();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mFragmentManager.getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            if (mFragmentManager.getBackStackEntryCount() > 0) {
                mFragmentManager.popBackStackImmediate();
            }
//            super.onBackPressed();
            if (mFragmentManager.getBackStackEntryCount() == 0) {
                mIsCurrentFragmentIsHomeFragment = true;
                mFragmentManager.beginTransaction().replace(R.id.frame, new FragmentHome()).commit();
                showToolbar();
            }
        }
    }

    public void initNav() {
        mFilterFloatingActionButton = findViewById(R.id.fab_filter);

        mCancelImageView = findViewById(R.id.iv_cancel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        locs = findViewById(R.id.recyclerView);

        //Todo: get list of locations from Api and pass to 'locs'

    }

    public void actionNav() {

        mFilterFloatingActionButton.setOnClickListener(v -> {

            if (mFilterFloatingActionButton.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_projects).getConstantState()) {
                mFilterFloatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_marker));
                Nav();
                loadProjectsData();
            } else {
                mFilterFloatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_projects));
                Nav();
                loadUsersLocationsData();
            }

            //Todo: Reload recycle view data


        });

        mCancelImageView.setOnClickListener(v -> opOrCl());
    }

    public void opOrCl() {
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.END);
        } else {
            mDrawer.openDrawer(GravityCompat.END);

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

        if (mFilterFloatingActionButton.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_projects).getConstantState()) {

            loadProjectsData();

        } else {

            loadUsersLocationsData();
        }
    }

    public static void hideSearchBar() {
        mSearchView.setVisibility(View.GONE);
    }

    public static void showSearchBar(String whome) {
        mSearchView.setVisibility(View.VISIBLE);
        HOME_STRING = whome;
    }

//    public static void hideToolbar() {
//        mToolbar.setVisibility(View.GONE);
//    }

    public static void showToolbar() {
        mToolbar.setVisibility(View.VISIBLE);
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
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_advanced_search) {
            Log.e("Search", "H");
            fragmentTransaction.replace(R.id.frame, new FilterSearchFragment()).addToBackStack(FilterSearchFragment.class.getSimpleName()).commit();
            return true;
        } else if (id == R.id.action_settings) {
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
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        switch (id) {
            case R.id.nav_home:
                showToolbar();

                fragmentTransaction.replace(R.id.frame, new FragmentHome());

                isCurrentFragmentIsHomeFragment = true;
                break;

//            case R.id.nav_go_map:
//                hideToolbar();
//                navigation.setSelectedItemId(R.id.navigation_go_map);
//
//                fragmentTransaction.replace(R.id.frame, new GoMap());
//                if (mIsCurrentFragmentIsHomeFragment)
//                    fragmentTransaction.addToBackStack(GoMap.class.getSimpleName());
//                break;

            case R.id.nav_share_project_idea:
                fragmentTransaction.replace(R.id.frame, new FragmentAddHome());
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentAddHome.class.getSimpleName());
                break;

            case R.id.nav_saved_project: // internal db
                showToolbar();

                fragmentTransaction.replace(R.id.frame, FragmentListProjects.setType(MINE));
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentListProjects.class.getSimpleName());
                break;

            case R.id.nav_requirement: // internal db
                showToolbar();

                fragmentTransaction.replace(R.id.frame, new ListJoinedProjectsFragment());
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(ListJoinedProjectsFragment.class.getSimpleName());
                break;

            case R.id.nav_favourite_projects: // internal db
                showToolbar();

                fragmentTransaction.replace(R.id.frame, new teamup.rivile.com.teamup.ui.Project.Favourite.FragmentListProjects());
                if (mIsCurrentFragmentIsHomeFragment)
                    fragmentTransaction.addToBackStack(FragmentListProjects.class.getSimpleName());
                break;

            case R.id.nav_profile:
                showToolbar();
                realm.executeTransaction(realm1 -> {
                    LoginDataBase loginDataBases = realm1.where(LoginDataBase.class)
                            .findFirst();

                    if (loginDataBases != null) {
                        UserDataBase userDataBase = loginDataBases.getUser();
                        if (userDataBase != null) {
                            fragmentTransaction.replace(R.id.frame,
                                    FragmentProfileHome.setId(userDataBase.getId()));
                        }
                    }

                    if (mIsCurrentFragmentIsHomeFragment)
                        fragmentTransaction.addToBackStack(FragmentProfileHome.class.getSimpleName());
                });

                break;

            case R.id.nav_sign:
                realm.executeTransaction(realm1 -> {
                    realm.where(LoginDataBase.class).findAll().deleteAllFromRealm();
                    realm.where(JoinedOfferRealmModel.class).findAll().deleteAllFromRealm();
                    realm.where(FavouriteDataBase.class).findAll().deleteAllFromRealm();
                    realm.where(LikeModelDataBase.class).findAll().deleteAllFromRealm();
                    realm.where(OfferDataBase.class).findAll().deleteAllFromRealm();
                    realm.where(UserDataBase.class).findAll().deleteAllFromRealm();

//                finish();
                    startActivity(new Intent(DrawerActivity.this, FirstActivity.class));
                    DrawerActivity.this.finish();
                });

                break;
        }

        fragmentTransaction.commit();

//        if (isCurrentFragmentIsHomeFragment && !mIsCurrentFragmentIsHomeFragment) {
//            mFragmentManager.popBackStack();
//        }
        mIsCurrentFragmentIsHomeFragment = isCurrentFragmentIsHomeFragment;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setTitle(String name) {
        mToolbar.setTitle(name);
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
