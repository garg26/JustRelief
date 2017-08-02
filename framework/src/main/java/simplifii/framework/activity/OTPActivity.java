package simplifii.framework.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import me.philio.pinentry.PinEntryView;
import simplifii.framework.R;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.UserLoginResponse;
import simplifii.framework.receivers.SMSListener;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;


public class OTPActivity extends BaseActivity implements SMSListener.OTPCallback {

    private PinEntryView pinView;
    private SMSListener smsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_screen);
        askPermissions();

        pinView = (PinEntryView) findViewById(R.id.pin_entry_view);
        setOnClickListener(R.id.btn_next, R.id.tv_resend_otp);
    }

    @Override
    protected void onPermissionVerify() {
            registerOTPReciever();
    }

    private void registerOTPReciever() {
        if (smsListener==null){
            smsListener = new SMSListener(this);
        }
        registerReceiver(smsListener,getFilter());
    }

    private IntentFilter getFilter(){
        return new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_next) {
            String text = pinView.getText().toString();
            if (TextUtils.isEmpty(text) || text.length() != 6) {
                showToast(R.string.error_invalid_otp);
            } else {
                checkOTP(text);
            }

        } else if (i == R.id.tv_resend_otp) {
            resendOTP();

        }
    }

    private void checkOTP(String otp) {
        executeTask(AppConstants.TASKCODES.CHECK_OTP, BaseApiGenerator.checkOTP(otp));
    }

    private void resendOTP() {
        pinView.setText("");
        executeTask(AppConstants.TASKCODES.RESEND_OTP, BaseApiGenerator.resendOtp());
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        switch (taskCode) {
            case AppConstants.TASKCODES.CHECK_OTP:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi != null && baseApi.isSuccess()) {
                    UserLoginResponse instance = UserLoginResponse.getInstance();
                    if (instance != null) {
                        instance.setMobVerified("true");
                        instance.save();

                    }
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    showToast(R.string.error_invalid_otp);
                }
                break;
            case AppConstants.TASKCODES.RESEND_OTP:

                break;
        }
    }

    @Override
    public void onOTPReceived(String otp) {
        if (CollectionUtils.isEmpty(otp)) {
            return;
        }
        else {
           pinView.setText(otp);
            checkOTP(otp);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsListener!=null){
            unregisterReceiver(smsListener);
        }

    }
}
