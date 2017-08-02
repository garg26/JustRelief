package com.justrelief.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.justrelief.R;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.models.BaseApi;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;


public class NewClinicProfile extends AppBaseActivity {

    private String clinic_name, cityID, localityID;
    private String constant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_get_new_clinic_profile);

        setOnClickListener(R.id.btn_go_back, R.id.btn_create_clinic_profile, R.id.tv_resend_mail);
    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);

        clinic_name = bundle.getString(AppConstants.BUNDLE_KEYS.CLINIC_NAME);
        cityID = bundle.getString(AppConstants.BUNDLE_KEYS.CITY_NAME);
        localityID = bundle.getString(AppConstants.BUNDLE_KEYS.LOCALITY_NAME);
        constant = bundle.getString(AppConstants.BUNDLE_KEYS.CONSTANT);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_go_back) {// startActivity(new Intent(this,CreateClinic.class));
            finish();

        } else if (i == R.id.btn_create_clinic_profile) {
            emailVerify();


        } else if (i == R.id.tv_resend_mail) {
            resendEmail();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }

    }

    private void createClinicProfile() {

        HttpParamObject httpParamObject = BaseApiGenerator.newProfile(clinic_name, cityID, localityID, AppConstants.RELATION_ID0);
        executeTask(AppConstants.TASKCODES.NEW_CLINIC, httpParamObject);
    }

    private void resendEmail() {
        HttpParamObject httpParamObject = BaseApiGenerator.resendEmail();
        executeTask(AppConstants.TASKCODES.RESEND_EMAIL, httpParamObject);
    }

    private void emailVerify() {

        executeTask(AppConstants.TASKCODES.EMAIL_VERIFIY, BaseApiGenerator.emailVerify());
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        super.onBackgroundError(re, e, taskCode, params);

        switch (taskCode) {
            case AppConstants.TASKCODES.RESEND_EMAIL:
                showToast(getString(R.string.error_resend_mail));
                break;


        }
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        switch (taskCode) {
            case AppConstants.TASKCODES.EMAIL_VERIFIY:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi != null && baseApi.isSuccess()) {
                    createClinicProfile();
//                    UserLoginResponse instance = UserLoginResponse.getInstance();
//                    if (instance != null) {
//                        instance.setEmailVerified("true");
//                        instance.save();
//
//                    }
                    // createClinicProfile();

                } else {
                    showToast(getString(R.string.error_email_verify));
                }
            case AppConstants.TASKCODES.NEW_CLINIC:
                BaseApi baseApi1 = (BaseApi) response;
                if (baseApi1 != null && baseApi1.isSuccess()) {

                    if (CollectionUtils.isEmpty(constant)) {

                        setResult(RESULT_OK);
                        finish();
                    }
                    else{
                        startNextActivityForResult(null,HomeActivity.class,AppConstants.REQUEST_CODES.NEW_ACTIVITY);
                    }
                }
                break;
            case AppConstants.TASKCODES.RESEND_EMAIL:
                showToast(getString(R.string.resend_email));
                break;


        }
    }

}
