package simplifii.framework.activity;

import android.os.Bundle;
import android.view.View;
import simplifii.framework.R;
import simplifii.framework.models.BaseApi;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Util;

public class BaseSignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setOnClickListener(R.id.bt_signup,R.id.tv_registered);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_signup) {
            onSignUp();
        }

    }

    protected void onSignUp() {

        if(isValidateTIL(R.id.til_first_name,R.id.til_last_name,R.id.til_email,R.id.til_number,R.id.til_password))
        {
            if (Util.VERY_STRONG_PASSWORD.matcher(getTextFromTil(R.id.til_password)).matches()) {
                if(getTextFromTil(R.id.til_confirm_password).equals(getTextFromTil(R.id.til_password))) {

                    signUp();


                }
                else{
                    setTilError(R.id.til_confirm_password,R.string.error_password_not_match);
                }
            }
            else{
                setTilError(R.id.til_password,R.string.error_password);
            }
        }

    }

    protected void signUp() {

    }



    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {

        super.onPostExecute(response,taskCode,params);
        if (response!=null) {
            switch (taskCode) {
                case AppConstants.TASKCODES.SIGNUP:
                    BaseApi userLoginResponse = (BaseApi) response;
                    if (userLoginResponse.isSuccess()) {
                        showToast("Success");
                        finish();
                    }
                    if(userLoginResponse.isFail()) {
                        showToast("Failed");
                        return;
                    }
                    if(userLoginResponse.isUserAlreadyExist()) {
                        showToast("User Already Exists Please choose unique phone number and email address");
                    }

            }

        }
    }



}
