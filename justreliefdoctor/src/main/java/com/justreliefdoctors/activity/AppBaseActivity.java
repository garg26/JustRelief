package com.justreliefdoctors.activity;

import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import simplifii.framework.activity.BaseActivity;

/**
 * Created by kartikeya-pc on 6/7/17.
 */

public abstract class AppBaseActivity extends BaseActivity {

    protected void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener, int... ids){
        for (int actvId : ids){
            AutoCompleteTextView completeTextView = (AutoCompleteTextView) findViewById(actvId);
            completeTextView.setOnItemClickListener(onItemClickListener);
        }
    }


}
