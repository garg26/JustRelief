package com.justrelief;

import android.app.Application;

import simplifii.framework.utility.Preferences;

/**
 * Created by nbansal2211 on 08/05/17.
 */

public class AppController extends Application {
    private static AppController instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Preferences.initSharedPreferences(this);
    }

    public static AppController getInstance(){
        return instance;
    }
}
