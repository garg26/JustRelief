package com.justrelief.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.justrelief.R;
import com.justrelief.models.UserLoginResponse;
import com.justrelief.models.UserSIgnUp;
import com.justrelief.util.ApiGenerator;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.widgets.CustomFontButton;
import simplifii.framework.widgets.CustomFontEditText;
import simplifii.framework.widgets.CustomTextInputLayout;


public class HomeActivity extends AppBaseActivity {

    private CustomTextInputLayout til_email, til_passsowrd;
    private CustomFontEditText et_email,et_password;
    private String email,password;
    private UserSIgnUp userSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        et_email = (CustomFontEditText)findViewById(R.id.et_email);
        et_password = (CustomFontEditText)findViewById(R.id.et_password);
        til_email = (CustomTextInputLayout)findViewById(R.id.til_email);
        til_passsowrd = (CustomTextInputLayout)findViewById(R.id.til_password);

        CustomFontButton sign_up_btn = (CustomFontButton) findViewById(R.id.bt_sign_up);
        CustomFontButton btn_login = (CustomFontButton)findViewById(R.id.bt_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();

                if(validateData()) {
                    HttpParamObject httpParamObject = ApiGenerator.loginrequest(userSignUp);
                    executeTask(AppConstants.TASKCODES.LOGIN, httpParamObject);
                    startFragmentContainer();
                }
//                   ApiGenerator.loginrequest(email, password);
//                   startFragmentContainer();

            }
        });

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, SignUpActivity.class);
                startActivity(i);

            }
        });

    }

    private boolean validateData() {


        if (email.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            til_email.setErrorEnabled(true);
            et_email.setError("Email Address is not correct");
            et_email.requestFocus();
            return false;
        }
        else
            til_email.setErrorEnabled(false);

        if (password.isEmpty()){
            til_passsowrd.setErrorEnabled(true);
            et_password.setError("Password is empty");
            et_password.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        super.onBackgroundError(re, e, taskCode, params);
    }

    private void initData() {
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();

        userSignUp = new UserSIgnUp(email,password,"1");

    }

    private void startFragmentContainer() {
        FragmentContainer.startActivity(this, AppConstants.FRAGMENT_TYPES.PROFILE_LIST_FRAGMENT, null);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response,taskCode,params);

        if (response==null)
            showToast("Unsuscessful");

        switch(taskCode){
            case AppConstants.TASKCODES.LOGIN:
                UserLoginResponse userLoginResponse = (UserLoginResponse) response;
                if(userLoginResponse!=null) {

                    showToast("Suscessful");
                }

                break;
        }

    }

}

