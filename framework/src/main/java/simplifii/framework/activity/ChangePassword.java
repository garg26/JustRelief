package simplifii.framework.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import simplifii.framework.R;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.models.BaseApi;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.utility.Util;




public class ChangePassword extends BaseActivity {

    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_password);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            number = bundle.getString(AppConstants.BUNDLE_KEYS.EMAIL);
        }

        setOnClickListener(R.id.btn_submit);
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_submit) {
            if (Util.VERY_STRONG_PASSWORD.matcher(getTextFromTil(R.id.til_new_password)).matches())
                {
                    if (getTextFromTil(R.id.til_confirm_password).equals(getTextFromTil(R.id.til_new_password))){
                        if (CollectionUtils.isNotEmpty(getTextFromTil(R.id.til_otp))){
                            changePassword();
                        }else{
                            setTilError(R.id.til_otp,R.string.error_invalid_otp);
                        }
                    }else{
                        setTilError(R.id.til_confirm_password,R.string.error_password_not_match);
                    }
                }else{
                setTilError(R.id.til_new_password,R.string.error_password);
               }

            }

        }


    private void changePassword() {

        executeTask(AppConstants.TASKCODES.CHANGE_PASSWORD, BaseApiGenerator.changePassword(number,getTextFromTil(R.id.til_new_password),getTextFromTil(R.id.til_otp)));
    }


    @Override
    public void onPostExecute(Object response, int taskCode, Object... params){
        super.onPostExecute(response,taskCode,params);

        if (response==null)
            return;

                switch (taskCode){
                    case AppConstants.TASKCODES.CHANGE_PASSWORD:
                        BaseApi baseApi = (BaseApi) response;
                        if (baseApi!=null & baseApi.isSuccess()){
                            showToast(getString(R.string.password_changed_msg));
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                        else{
                            showToast(getString(R.string.error_fail_change_password));
                        }
                        break;
                }
    }

}
