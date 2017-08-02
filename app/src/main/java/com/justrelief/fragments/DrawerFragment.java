package com.justrelief.fragments;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.justrelief.activity.HomeActivity;
import com.justrelief.activity.LoginActivity;


import simplifii.framework.utility.Preferences;


public class DrawerFragment extends simplifii.framework.fragments.DrawerFragment {

    private HomeActivity homeActivity;
    private DrawerLayout drawerLayout;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == simplifii.framework.R.id.rl_logout) {
            Preferences.removeData(Preferences.LOGIN_KEY);
            Preferences.removeData(Preferences.USER_DETAILS);
            startNextActivity(LoginActivity.class);
            getActivity().finish();


        }
    }
    public static DrawerFragment getInstance(HomeActivity homeActivity, DrawerLayout drawerLayout) {
        DrawerFragment drawerFragment = new DrawerFragment();
        drawerFragment.homeActivity = homeActivity;
        drawerFragment.drawerLayout = drawerLayout;
        return drawerFragment;
    }
}
