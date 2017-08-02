package com.justreliefdoctors.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.justreliefdoctors.R;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.utility.Preferences;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

                checkLogin();
            }
        }.execute();
    }
    private void checkLogin() {
        //Boolean data = Preferences.getData(Preferences.LOGIN_KEY, false);

        if (Preferences.isUserLoggerIn()) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        } else {
            //showToast(getString(R.string.error_complete_profile));
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }

    }
}
