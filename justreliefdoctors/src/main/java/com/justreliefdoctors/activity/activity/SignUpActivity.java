package com.justreliefdoctors.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.justrelief.R;

import simplifii.framework.activity.BaseSignUpActivity;

public class SignUpActivity extends BaseSignUpActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}
