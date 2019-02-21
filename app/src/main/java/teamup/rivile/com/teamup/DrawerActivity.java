package teamup.rivile.com.teamup;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import teamup.rivile.com.teamup.Department.FragmentHome;
import teamup.rivile.com.teamup.GoMap.GoMap;
import teamup.rivile.com.teamup.GoMap.MovableFloatingActionButton;
import teamup.rivile.com.teamup.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.Project.Add.FragmentAddHome;
import teamup.rivile.com.teamup.Project.IncommingRequirement.FragmentIncommingRequirement;
import teamup.rivile.com.teamup.Project.List.FragmentListProjects;
import teamup.rivile.com.teamup.Project.join.FragmentJoinHome;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    BottomNavigationView navigation;
    FragmentManager fragmentManager;
    static SearchView searchView;
    static String Whome = "Home";
    Toolbar toolbar;
    static DrawerLayout drawer;

    FloatingActionButton fab;
    MovableFloatingActionButton movableFloatingActionButton;
    FloatingActionButton civ_filter;

    Drawable filterDrawable, filterFABDrawable;
    ImageView iv_cancel;

    RecyclerView locs;
    RecyclerView.Adapter adapter;
    Realm realm;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setVisibility(View.VISIBLE);
                    navigationView.setCheckedItem(R.id.nav_home);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new FragmentHome()).addToBackStack("Home")
                            .commit();
                    return true;
                case R.id.navigation_go_map:
                    toolbar.setVisibility(View.GONE);
                    navigationView.setCheckedItem(R.id.nav_go_map);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new GoMap()).addToBackStack("Home")
                            .commit();
                    return true;
                case R.id.navigation_saved_project:
                    toolbar.setVisibility(View.VISIBLE);
                    navigationView.setCheckedItem(R.id.nav_saved_project);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new FragmentListProjects()).addToBackStack("Home")
                            .commit();
                    return true;
                case R.id.navigation_favourite_projects:
                    toolbar.setVisibility(View.VISIBLE);
                    navigationView.setCheckedItem(R.id.nav_favourite_projects);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, FragmentJoinHome.setOfferId(2)).addToBackStack("Home")
                            .commit();
                    return true;
                case R.id.navigation_profile:
                    toolbar.setVisibility(View.VISIBLE);
                    navigationView.setCheckedItem(R.id.nav_profile);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new FragmentProfileHome()).addToBackStack("Home")
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchView = findViewById(R.id.search);


        realm = Realm.getDefaultInstance();

        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        fab = (FloatingActionButton) findViewById(R.id.addOffer);
        fab.setVisibility(View.VISIBLE);


        toolbar.setVisibility(View.VISIBLE);
//        navigation.setSelectedItemId(R.id.navigation_home);
        fragmentManager.beginTransaction()
                .replace(R.id.frame, new FragmentHome()).addToBackStack("Home")
                .commit();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (Whome.equals("Home")) {/** Means Current fragment is home*/

                } else if (Whome.equals("ListProjects")) {/** Means Current fragment is ListProjects*/

                }

                return false;
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.nav_home);
        navigationView.setCheckedItem(R.id.navigation_home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Half Pizza Animation */
                Hide();
                fab.setVisibility(View.GONE);
                fragmentManager.beginTransaction().replace(R.id.frame, new FragmentAddHome().setFab(fab)).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        civ_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


            }
        });

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opOrCl();

            }
        });
    }

    public void opOrCl() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);

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

    public static void Hide() {
        searchView.setVisibility(View.GONE);
    }

    public static void Show(String whome) {
        searchView.setVisibility(View.VISIBLE);
        Whome = whome;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            toolbar.setVisibility(View.VISIBLE);
            navigation.setSelectedItemId(R.id.navigation_home);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, new FragmentHome()).addToBackStack("Home")
                    .commit();
        } else if (id == R.id.nav_go_map) {
            toolbar.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.navigation_go_map);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, new GoMap()).addToBackStack("Home")
                    .commit();
        } else if (id == R.id.nav_saved_project) {// internal db
            toolbar.setVisibility(View.VISIBLE);
            navigation.setSelectedItemId(R.id.navigation_saved_project);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, FragmentListProjects.setType(1)).addToBackStack("Home")
                    .commit();
        } else if (id == R.id.nav_requirement) {// internal db
            toolbar.setVisibility(View.VISIBLE);
            navigation.setSelectedItemId(R.id.navigation_saved_project);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, new FragmentIncommingRequirement()).addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_favourite_projects) {// internal db
            toolbar.setVisibility(View.VISIBLE);
            navigation.setSelectedItemId(R.id.navigation_favourite_projects);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, FragmentListProjects.setType(2)).addToBackStack("Home")
                    .commit();
        } else if (id == R.id.nav_profile) {
            toolbar.setVisibility(View.VISIBLE);
            realm.executeTransaction(realm1 -> {
                LoginDataBase loginDataBases = realm1.where(LoginDataBase.class)
                        .findFirst();
                navigation.setSelectedItemId(R.id.navigation_profile);
                fragmentManager.beginTransaction()
                        .replace(R.id.frame, new FragmentProfileHome().setId(loginDataBases.getUser().getId())).addToBackStack("Home")
                        .commit();
            });

        } else if (id == R.id.nav_sign) {
            realm.executeTransaction(realm1 -> {
                RealmResults<LoginDataBase> results = realm.where(LoginDataBase.class).findAll();
                results.deleteAllFromRealm();
                finish();
                startActivity(new Intent(DrawerActivity.this, Login.class));
            });
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
