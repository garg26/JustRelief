package simplifii.framework.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import simplifii.framework.R;


public class HomeActivity extends BaseActivity implements DrawerLayout.DrawerListener {

    private boolean isDrawerOpen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setAddFragment();
        setDrawer(toolbar);

    }

    public void setDrawer(Toolbar toolbar) {
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            toolbar.setNavigationIcon(getDrawable(R.mipmap.bacon2x));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerOperation(drawerLayout);
                }
            });
        }

        drawerLayout.addDrawerListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        addDrawerFragment(this.toolbar,drawerLayout, fragmentManager);

        initNavigationDrawer();
    }


    protected void setAddFragment() {

    }

    protected void  initNavigationDrawer() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.lay_drawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });

    }

    private void drawerOperation(DrawerLayout drawerLayout) {
        if (isDrawerOpen) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        isDrawerOpen = true;
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        isDrawerOpen = false;
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    public void addFragment(Fragment fragment, boolean b) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().popBackStack();
        if (b) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    protected void addDrawerFragment(Toolbar toolbar, DrawerLayout drawerLayout, FragmentManager fragmentManager) {

    }
}
