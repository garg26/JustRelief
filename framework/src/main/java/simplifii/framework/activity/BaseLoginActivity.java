package simplifii.framework.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import simplifii.framework.R;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.models.UserLoginResponse;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.AppConstants;

public class BaseLoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setOnClickListener(R.id.bt_login, R.id.bt_sign_up, R.id.tv_forgot_password);


    }
    protected void login() {

    }

    @Override
    public void onClick(View v) {
        int i1 = v.getId();
        if (i1 == R.id.bt_login) {
            if (isValidateTIL(R.id.til_email, R.id.til_password)) {
                login();
            }

        } else if (i1 == R.id.bt_sign_up) {

            startSignUp();


        } else if (i1 == R.id.tv_forgot_password) {
            startNextActivityForResult(null, ForgotPassword.class, AppConstants.REQUEST_CODES.FORGOT_PASSWORD);


        }

    }

    protected void startSignUp(){

    }
    protected void showErrorDialog(String error){
        final Dialog dialog=new Dialog(this,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        dialog.setContentView(R.layout.error_dialog);
        dialog.show();
        TextView textView= (TextView) dialog.findViewById(R.id.tv_errorMsg);
        textView.setText(error);
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_resendEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendEmail();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }
    private void resendEmail() {
        HttpParamObject httpParamObject = BaseApiGenerator.resendEmail();
        executeTask(AppConstants.TASKCODES.RESEND_EMAIL,httpParamObject);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);


        if (response != null) {

            switch (taskCode) {
                case AppConstants.TASKCODES.LOGIN:
                    UserLoginResponse userLoginResponse = (UserLoginResponse) response;

                    if (userLoginResponse.isSuccess()) {
                        String json = new Gson().toJson(userLoginResponse);
                        if (json != null) {
                            if (!userLoginResponse.isMobileVerified()) {
                                startNextActivityForResult(null, OTPActivity.class, AppConstants.REQUEST_CODES.VALIDATE_OTP);
                            }
                            else {
                                method(userLoginResponse);
                            }

                        }
                    } else {
                            showToast(R.string.error_invalid_credentials);
                         }
                        break;

                case AppConstants.TASKCODES.RESEND_EMAIL:
                    showToast(getString(R.string.resend_email));
                    break;

            }


        }
    }

    protected void method(UserLoginResponse userLoginResponse) {

    }
}

