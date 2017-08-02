package com.justrelief.util;

import android.util.Log;

import com.justrelief.models.UserLoginResponse;
import com.justrelief.models.UserSIgnUp;

import org.json.JSONTokener;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.JsonUtil;

/**
 * Created by kartikeya-pc on 5/15/17.
 */

public class ApiGenerator {

    public static HttpParamObject loginrequest(UserSIgnUp userSignUp){
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.LOGIN);
        Log.e("String123",JsonUtil.toJson(userSignUp));
        httpParamObject.setJSONContentType();
        httpParamObject.setJson(JsonUtil.toJson(userSignUp));
        httpParamObject.setClassType(UserLoginResponse.class);
        httpParamObject.setPostMethod();
        return httpParamObject;
    }

    public static HttpParamObject signupRequest(UserSIgnUp userSignUp) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SIGNUP);
        Log.e("String",JsonUtil.toJson(userSignUp));
        httpParamObject.setJSONContentType();
        httpParamObject.setJson(JsonUtil.toJson(userSignUp));
        httpParamObject.setPostMethod();


        return httpParamObject;

    }
}
