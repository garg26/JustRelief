package simplifii.framework.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import simplifii.framework.R;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.models.BaseApi;
import simplifii.framework.utility.AppConstants;




public class ForgotPassword extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgort_password);


        setOnClickListener(R.id.btn_submit);
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_submit) {
            if (isValidateTIL(R.id.til_email)) {
                forgotPassword();
            }

        }
    }
    private void forgotPassword() {
        executeTask(AppConstants.TASKCODES.FORGOT_PASSWORD, BaseApiGenerator.forgotPassword(getTextFromTil(R.id.til_email)));
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        switch (taskCode) {
            case AppConstants.TASKCODES.FORGOT_PASSWORD:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi != null && baseApi.isSuccess()) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.BUNDLE_KEYS.EMAIL,getTextFromTil(R.id.til_email));
                    intent.putExtras(bundle);
                    setResult(Activity.RESULT_OK, intent);
                    finish();

                } else {
                    showToast(getString(R.string.fail));
                }
                break;

        }
    }

}
