package com.justrelief.activity;


import android.view.View;

import com.justrelief.R;

import simplifii.framework.activity.BaseSignUpActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.models.UserSIgnUp;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.AppConstants;

public class SignUpActivity extends BaseSignUpActivity {


    @Override
    protected void signUp() {
        UserSIgnUp userSignUp = new UserSIgnUp(getTextFromTil(simplifii.framework.R.id.til_number),getTextFromTil(simplifii.framework.R.id.til_email),getTextFromTil(simplifii.framework.R.id.til_password),getTextFromTil(simplifii.framework.R.id.til_first_name),getTextFromTil(simplifii.framework.R.id.til_last_name), AppConstants.USER_CLINIC,AppConstants.USER_ROLE,AppConstants.PARENT_ID);
        HttpParamObject httpParamObject = BaseApiGenerator.signupRequest(userSignUp);
        executeTask(AppConstants.TASKCODES.SIGNUP, httpParamObject);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        int i = v.getId();
        if (i == R.id.tv_registered) {
            startNextActivity(null, LoginActivity.class);
            finish();
        }
    }
}
