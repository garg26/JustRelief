package com.justreliefdoctors.fragment;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.justreliefdoctors.R;
import com.justreliefdoctors.activity.HomeActivity;
import com.justreliefdoctors.activity.LoginActivity;

import java.util.List;

import simplifii.framework.enums.DrawerData;
import simplifii.framework.models.DrawerItem;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

/**
 * Created by kartikeya-pc on 6/8/17.
 */

public class DrawerFragment extends simplifii.framework.fragments.DrawerFragment {
    private HomeActivity homeActivity;
    private DrawerLayout drawerLayout;
    private List<DrawerItem> itemList;


    @Override
    public void initViews() {
        super.initViews();

        itemList = DrawerData.getAllDrawerItems();
    }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        DrawerItem drawerItem = itemList.get(position);
        int itemID = drawerItem.getItemID();
        switch (itemID) {
            case AppConstants.PATIENTS.PATIENTS_ID:
                break;
            case AppConstants.ALL_PATIENTS.ALL_PATIENTS_ID:
                showToast("Kartikeya");
                break;
            case AppConstants.DOCTOR_PT.DOCTOR_PT_ID:
                break;
            case AppConstants.HOSPITAL_CLINICS.HOSPITAL_CLINICS_ID:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HospitalClinicFragment()).commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case AppConstants.REPORTS.REPORTS_ID:
                break;
            case AppConstants.FEEDBACK.FEEDBACK_ID:
                break;

        }
    }
}
