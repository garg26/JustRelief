package com.justreliefdoctors.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.justreliefdoctors.fragment.DoctorListFragment;
import com.justreliefdoctors.fragment.DrawerFragment;

import simplifii.framework.utility.AppConstants;

public class HomeActivity extends simplifii.framework.activity.HomeActivity {

    private DoctorListFragment doctorListFragment = new DoctorListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    protected void setAddFragment() {
        addFragment(doctorListFragment, false);
    }

    @Override
    protected void addDrawerFragment(Toolbar toolbar, DrawerLayout drawerLayout, FragmentManager fragmentManager) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, simplifii.framework.R.string.navigation_drawer_open, simplifii.framework.R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        DrawerFragment drawerFragment = DrawerFragment.getInstance(this, drawerLayout);
        fragmentManager.beginTransaction().replace(simplifii.framework.R.id.lay_drawer, drawerFragment).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppConstants.REQUEST_CODES.CONTACT) {
            doctorListFragment.refresh();
        }
    }

}
