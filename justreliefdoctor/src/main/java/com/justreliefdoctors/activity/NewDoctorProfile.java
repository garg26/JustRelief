package com.justreliefdoctors.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.justreliefdoctors.R;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.FindDoctorResponse;
import simplifii.framework.models.UserLoginResponse;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.AppConstants;


public class NewDoctorProfile extends BaseActivity {

    private String doctor_name,spec_id,city_id,registration_no,registration_council,year;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_get_new_doctor_profile);

        setOnClickListener(R.id.btn_go_back,R.id.btn_create_doctor_profile);
    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);

        this.bundle=bundle;
        doctor_name = bundle.getString(AppConstants.BUNDLE_KEYS.DOCTOR_NAME);
        spec_id = bundle.getString(AppConstants.BUNDLE_KEYS.SPECIALIZATION_ID);
        city_id = bundle.getString(AppConstants.BUNDLE_KEYS.CITY_ID);
        registration_no = bundle.getString(AppConstants.BUNDLE_KEYS.REGISTRATION_NUMBER);
        registration_council = bundle.getString(AppConstants.BUNDLE_KEYS.REGISTRATION_COUNCIL);
        year = bundle.getString(AppConstants.BUNDLE_KEYS.YEAR);

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_go_back) {//  startNextActivityForResult(bundle,CreateDoctor.class, AppConstants.REQUEST_CODES.CREATE_DOCTOR);
            finish();

        } else if (i == R.id.btn_create_doctor_profile) {
            emailVerify();


//            case R.id.tv_resend_mail:
//                resendEmail();
        }
    }
    private void resendEmail() {
        HttpParamObject httpParamObject = BaseApiGenerator.resendEmail();
        executeTask(AppConstants.TASKCODES.RESEND_EMAIL,httpParamObject);
    }

    private void emailVerify() {

        executeTask(AppConstants.TASKCODES.EMAIL_VERIFIY, BaseApiGenerator.emailVerify());
    }
    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        super.onBackgroundError(re, e, taskCode, params);

        switch (taskCode){
            case AppConstants.TASKCODES.RESEND_EMAIL:
                showToast("Failed to resend email");
                break;


        }
    }
    private void createDoctorProfile() {

        FindDoctorResponse findDoctorResponse = new FindDoctorResponse(doctor_name,spec_id,city_id,registration_no,registration_council,year);
        HttpParamObject httpParamObject = BaseApiGenerator.newDoctor(findDoctorResponse);
        executeTask(AppConstants.TASKCODES.NEW_DOCTOR,httpParamObject);

    }
    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        switch (taskCode) {
            case AppConstants.TASKCODES.EMAIL_VERIFIY:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi!=null && baseApi.isSuccess()) {
                    UserLoginResponse instance = UserLoginResponse.getInstance();
                    if (instance != null) {
                        instance.setEmailVerified("true");
                        instance.save();

                    }
                    createDoctorProfile();

                }

            case AppConstants.TASKCODES.NEW_DOCTOR:
                BaseApi baseApi1 = (BaseApi) response;
                if (baseApi1 != null && baseApi1.isSuccess()) {
                    setResult(RESULT_OK);
                    finish();
                }
                break;
            case AppConstants.TASKCODES.RESEND_EMAIL:
                showToast("Email has been send");
                break;


        }
    }

}
