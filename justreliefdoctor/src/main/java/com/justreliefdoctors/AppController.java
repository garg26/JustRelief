package com.justreliefdoctors;

import android.app.Application;
import android.support.multidex.MultiDexApplication;


import simplifii.framework.utility.Preferences;

/**
 * Created by kartikeya-pc on 5/24/17.
 */
public class AppController extends MultiDexApplication {
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
