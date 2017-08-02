package com.justrelief.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.justrelief.R;
import com.justrelief.models.UserSIgnUp;
import com.justrelief.util.ApiGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;
import simplifii.framework.widgets.CustomFontButton;
import simplifii.framework.widgets.CustomFontEditText;
import simplifii.framework.widgets.CustomFontRadio;
import simplifii.framework.widgets.CustomTextInputLayout;

public class SignUpActivity extends AppBaseActivity {

    private CustomFontEditText et_firstName,et_lastName,et_email,et_number,et_password,et_confirmPassword;
    private CustomTextInputLayout til_firstName,til_lastName,til_email,til_number,til_password,til_confirmPassowrd;
    private String firstName,lastName,email,number,password,confirmPassword;
    private CustomFontRadio clinic,doctor;
    private String usertype;
    private UserSIgnUp userSignUp;
    public ApiGenerator apiGenerator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        clinic = (CustomFontRadio)findViewById(R.id.radio_clinic);
        doctor = (CustomFontRadio)findViewById(R.id.radio_doctor);

        apiGenerator = new ApiGenerator();


        et_firstName = (CustomFontEditText)findViewById(R.id.et_first_name);
        et_lastName = (CustomFontEditText)findViewById(R.id.et_last_name);
        et_email = (CustomFontEditText)findViewById(R.id.et_email);
        et_number = (CustomFontEditText)findViewById(R.id.et_number);
        et_password = (CustomFontEditText)findViewById(R.id.et_password);
        et_confirmPassword = (CustomFontEditText)findViewById(R.id.et_confirm_password);

        til_firstName = (CustomTextInputLayout)findViewById(R.id.til_first_name);
        til_lastName = (CustomTextInputLayout)findViewById(R.id.til_last_name);
        til_email = (CustomTextInputLayout)findViewById(R.id.til_email);
        til_number = (CustomTextInputLayout)findViewById(R.id.til_number);
        til_password = (CustomTextInputLayout)findViewById(R.id.til_password);
        til_confirmPassowrd = (CustomTextInputLayout)findViewById(R.id.til_confirm_password);

        CustomFontButton sign_up_btn = (CustomFontButton) findViewById(R.id.bt_signup);
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initData();
                if(validateData()) {
                    HttpParamObject httpParamObject = ApiGenerator.signupRequest(userSignUp);
                    executeTask(AppConstants.TASKCODES.SIGNUP, httpParamObject);

                    startFragmentContainer();
                }
//                    ApiGenerator.signupRequest(firstName, lastName, email, number, password,usertype);
//                    startFragmentContainer();
            }
        });
    }

    private void initData() {

        firstName = et_firstName.getText().toString().trim();
        lastName = et_lastName.getText().toString().trim();
        email = et_email.getText().toString().trim();
        number = et_number.getText().toString().trim();
        password = et_password.getText().toString().trim();
        confirmPassword = et_confirmPassword.getText().toString().trim();

        if(clinic.isChecked())
            usertype = AppConstants.USER_CLINIC;
        else if(doctor.isChecked())
            usertype = AppConstants.USER_DOCTOR;

        userSignUp = new UserSIgnUp(number,email,password,firstName,lastName,AppConstants.USER_CLINIC,"1","-1");

    }

    private boolean validateData() {

        if(firstName.isEmpty()){
            til_firstName.setErrorEnabled(true);
            et_firstName.setError("First name is empty");
            et_firstName.requestFocus();

            return false;
        }
        else
            til_firstName.setErrorEnabled(false);

        if (lastName.isEmpty()){
            til_lastName.setErrorEnabled(true);
            et_lastName.setError("Last name is empty");
            et_lastName.requestFocus();
            return false;
        }
        else
            til_lastName.setErrorEnabled(false);

        if (email.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            til_email.setErrorEnabled(true);
            et_email.setError("Email Address is not correct");
            et_email.requestFocus();
            return false;
        }
        else
            til_email.setErrorEnabled(false);

        if (number.isEmpty() && number.length()!=10){
            til_number.setErrorEnabled(true);
            et_number.setError("Number is not correct");
            et_number.requestFocus();
            return false;
        }
        else
           til_number.setErrorEnabled(false);

        if (!Util.VERY_STRONG_PASSWORD.matcher(password).matches()){
            til_password.setErrorEnabled(true);
            et_password.setError("Password must be atleast 8 characters and contain:- 1 Numeric 1 Special character 1 Capital letter");
            return false;
        }
        else
          til_password.setErrorEnabled(false);
        if (!confirmPassword.equals(password)){
            til_confirmPassowrd.setErrorEnabled(true);
            et_confirmPassword.setError("Paassword does not match");
            et_confirmPassword.requestFocus();
            return false;
        }
        else
            til_confirmPassowrd.setErrorEnabled(false);
       return true;

    }

    private void startFragmentContainer() {
        FragmentContainer.startActivity(this, AppConstants.FRAGMENT_TYPES.PROFILE_LIST_FRAGMENT,null);
    }

    @Override
    public void onPreExecute(int taskCode) {
        super.onPreExecute(taskCode);
    }
    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {

        super.onPostExecute(response,taskCode,params);

        if (response==null)
            showToast("Unsuscessful");

        switch(taskCode){
            case AppConstants.TASKCODES.SIGNUP:
                  showToast("Suscessful");
                  break;
        }



    }

}
