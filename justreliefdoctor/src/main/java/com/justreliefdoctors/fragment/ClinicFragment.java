package com.justreliefdoctors.fragment;

import android.content.Intent;

import com.justreliefdoctors.R;

import simplifii.framework.fragments.BaseFragment;

/**
 * Created by kartikeya-pc on 6/8/17.
 */

public class ClinicFragment extends BaseFragment {
    @Override
    public void initViews() {
        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.justrelief");
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found

        }
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_clinic;
    }
}
