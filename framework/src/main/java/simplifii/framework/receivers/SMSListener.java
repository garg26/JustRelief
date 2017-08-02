package simplifii.framework.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by manish on 6/8/2015.
 */
public class SMSListener extends BroadcastReceiver {

        public static final Pattern CODE_PATTERN;
        private static final String TAG = "SMSLIstener";

        private OTPCallback callback;

        public SMSListener(){

        }
        public SMSListener(OTPCallback callback) {
            this.callback = callback;
        }

        static {
            CODE_PATTERN = Pattern.compile("\\d{6}");
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    try {
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        SmsMessage[] msgs = new SmsMessage[pdus.length];
                        for (int i = 0; i < msgs.length; i++) {
                            msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            String msg_from = msgs[i].getOriginatingAddress();
                            String msgBody = msgs[i].getMessageBody().toLowerCase();
                            Log.d(TAG, "" + msgBody);
//                        if (msgBody.contains("konverse")) {
                            Matcher m = CODE_PATTERN.matcher(msgBody);
                            if (m.find()) {
                                if (callback != null){
                                    callback.onOTPReceived(m.group());
                                }

                            }
//                        }
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "Exception :" + e.getMessage());
                    }
                }
            }
        }

        public static interface OTPCallback {
            public void onOTPReceived(String otp);
        }

}
