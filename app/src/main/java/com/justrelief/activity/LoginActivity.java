package com.justrelief.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.justrelief.R;

import java.util.List;

import simplifii.framework.activity.BaseLoginActivity;
import simplifii.framework.activity.ChangePassword;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.models.UserLoginResponse;
import simplifii.framework.models.UserSIgnUp;
import simplifii.framework.models.response.ClinicResponse;
import simplifii.framework.models.response.UserProfileResponse;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;


public class LoginActivity extends BaseLoginActivity {

    private UserLoginResponse userLoginResponse = new UserLoginResponse();
    private List<ClinicResponse> clinicResponses;

    @Override
    protected void login() {
        UserSIgnUp userSignUp = new UserSIgnUp(getTextFromTil(simplifii.framework.R.id.til_email), getTextFromTil(simplifii.framework.R.id.til_password), AppConstants.USER_TYPES);
        HttpParamObject httpParamObject = BaseApiGenerator.loginrequest(userSignUp);
        executeTask(AppConstants.TASKCODES.LOGIN, httpParamObject);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {

            case AppConstants.REQUEST_CODES.VALIDATE_OTP:
                login();
                break;
            case AppConstants.REQUEST_CODES.CLINIC_DETAILS:
                getUserClinicProfileData();

                break;
            case AppConstants.REQUEST_CODES.NEW_CLINIC:
                 login();
                 getUserClinicProfileData();

                break;

            case AppConstants.REQUEST_CODES.FORGOT_PASSWORD:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    startNextActivityForResult(bundle, ChangePassword.class, AppConstants.REQUEST_CODES.CHANGE_PASSWORD);
                }
                break;
            case AppConstants.REQUEST_CODES.CHANGE_PASSWORD:
                break;
        }

    }

    @Override
    protected void method(UserLoginResponse userLoginResponse) throws NullPointerException {
        this.userLoginResponse=userLoginResponse;
        getUserClinicProfileData();

    }

    private void clinicResponse(){


        if (clinicResponses.size()==0){
            if (userLoginResponse.isEmailVerified()) {
                startNextActivityForResult(null, CreateClinic.class, AppConstants.REQUEST_CODES.CLINIC_DETAILS);
            } else {
                showErrorDialog(getString(R.string.error_verify_not_email));
            }
        }
        else {
            Preferences.saveData(Preferences.LOGIN_KEY, true);
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    private void getUserClinicProfileData() {
        HttpParamObject httpParamObject = BaseApiGenerator.getUserProfileData();
        executeTask(AppConstants.TASKCODES.USER_PROFILE,httpParamObject);
    }





    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        if (response==null){
            return;
        }
            switch (taskCode){

                case AppConstants.TASKCODES.USER_PROFILE:
                    try {
                        UserProfileResponse userProfileResponse = (UserProfileResponse) response;

                        clinicResponses = userProfileResponse.getTable();
                        if(clinicResponses.size()==0){
                            clinicResponse();
                        }
                        else{
                            String json = new Gson().toJson(clinicResponses);
                            ClinicResponse.setJson(json);
                            clinicResponse();
                        }

                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }
                    break;

            }
    }

    @Override
    protected void startSignUp() {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
    }

    
}

