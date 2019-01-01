package teamup.rivile.com.teamup;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import teamup.rivile.com.teamup.Department.FragmentHome;
import teamup.rivile.com.teamup.GoMap.GoMap;
import teamup.rivile.com.teamup.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.Project.List.FragmentListProjects;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    BottomNavigationView navigation;
    FragmentManager fragmentManager;
    static SearchView searchView;
    static String Whome = "Home";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    navigationView.setCheckedItem(R.id.nav_home);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new FragmentHome()).addToBackStack("Home")
                            .commit();
                    return true;
                case R.id.navigation_go_map:
                    navigationView.setCheckedItem(R.id.nav_go_map);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new GoMap()).addToBackStack("Home")
                            .commit();
                    return true;
                case R.id.navigation_saved_project:
                    navigationView.setCheckedItem(R.id.nav_saved_project);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new FragmentListProjects()).addToBackStack("Home")
                            .commit();
                    return true;
                case R.id.navigation_favourite_projects:
                    navigationView.setCheckedItem(R.id.nav_favourite_projects);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, new FragmentListProjects()).addToBackStack("Home")
                            .commit();
                    return true;
                case R.id.navigation_profile:
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchView = findViewById(R.id.search);

        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (Whome.equals("Home")){/** Means Current fragment is home*/

                }else if (Whome.equals("ListProjects")){/** Means Current fragment is ListProjects*/

                }

                return false;
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static void Hide(){
        searchView.setVisibility(View.GONE);
    }

    public static void Show(String whome){
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
            navigation.setSelectedItemId(R.id.navigation_home);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, new FragmentHome()).addToBackStack("Home")
                    .commit();
        } else if (id == R.id.nav_go_map) {
            navigation.setSelectedItemId(R.id.navigation_go_map);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, new GoMap()).addToBackStack("Home")
                    .commit();
        } else if (id == R.id.nav_saved_project) {
            navigation.setSelectedItemId(R.id.navigation_saved_project);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, new FragmentListProjects()).addToBackStack("Home")
                    .commit();
        } else if (id == R.id.nav_favourite_projects) {
            navigation.setSelectedItemId(R.id.navigation_favourite_projects);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, new FragmentListProjects()).addToBackStack("Home")
                    .commit();
        } else if (id == R.id.nav_profile) {
            navigation.setSelectedItemId(R.id.navigation_profile);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, new FragmentProfileHome()).addToBackStack("Home")
                    .commit();
        } else if (id == R.id.nav_sign) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
