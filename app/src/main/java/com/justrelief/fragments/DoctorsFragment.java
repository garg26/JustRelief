package com.justrelief.fragments;

import android.content.Intent;

import com.justrelief.R;

/**
 * Created by kartikeya-pc on 5/13/17.
 */

public class DoctorsFragment extends AppBaseFragment {
    @Override
    public void initViews() {
        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.justreliefdoctors");
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found

        }
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_doctors;
    }
}
