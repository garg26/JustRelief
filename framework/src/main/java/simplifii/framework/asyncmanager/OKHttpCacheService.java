package simplifii.framework.asyncmanager;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Preferences;

/**
 * Created by robin on 9/27/16.
 */

public class OKHttpCacheService extends OKHttpService {
    private static final String TAG = "cache";

    @Override
    public Object getData(Object... params) throws JSONException, SQLException, NullPointerException, RestException, ClassCastException, IOException {
        if (params != null && params.length > 1) {
            HttpParamObject httpParamObject = (HttpParamObject) params[0];
            boolean isCache = (boolean) params[1];
            String objectKey = httpParamObject.getUrl() + httpParamObject.getMethod();
            if(params.length>2){
                objectKey = (String) params[2];
            }
            long defCacheTimeValue = 10 * 60 * 1000;
            if(params.length>3){
                Long t = (Long) params[3];
                if(t != null){
                    defCacheTimeValue = t;
                }
            }

            if (isCache) {
                String timeKey = httpParamObject.getUrl() + httpParamObject.getMethod() + "Time";
                String json = Preferences.getData(objectKey, "");
                long time = Preferences.getData(timeKey, 0l);
                if (TextUtils.isEmpty(json)) {
                    Object data = super.getData(params);
                    json = new Gson().toJson(data);
                    Preferences.saveData(objectKey, json);
                    Preferences.saveData(timeKey, System.currentTimeMillis());
                    Log.i(TAG, "Saved to cache: " + json);
                    return data;
                } else {
                    if ((System.currentTimeMillis() - time) <= defCacheTimeValue) {
                        Log.i(TAG, "load from cache: " + json);
                        return parseJson(json, httpParamObject);
                    }
                }
            }
        }
        return super.getData(params);
    }
}
