package simplifii.framework.asyncmanager;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

/**
 * Created by nbansal2211 on 09/01/17.
 */

public class LocalityAsyncService extends OKHttpService {

    @Override
    public Object getData(Object... params) throws JSONException, SQLException, NullPointerException, RestException, ClassCastException, IOException {
        if (params != null && params.length > 0) {
            int count = 0;
            String key = (String) params[1];

            if (isCacheValid(key)) {
                String data = getDataFromCache(key);
                if (!TextUtils.isEmpty(data)) {
                    return parseJson(data, (HttpParamObject) params[0]);
                }
            }
            HttpParamObject param = (HttpParamObject) params[0];
            JSONArray array = null;
            List<JSONArray> arrayList = new ArrayList<>();
            String cityId = param.getPostParams().get("cityId");
            boolean flag = true;
            do {
                array = getLocalities(count, key, param);
                if (array != null) {
                    if (array.length() >= 1000) {
                        arrayList.add(array);
                        flag = true;
                        count++;
                        param = getCopy(count, param, cityId);
                    } else {
                        arrayList.add(array);
                        flag = false;
                    }
                } else {
                    flag = false;
                }
            } while (flag);

            JSONArray finalArray = new JSONArray();
            for (JSONArray array1 : arrayList) {
                for (int i = 0; i < array1.length(); i++) {
                    finalArray.put(array1.get(i));
                }
            }
            if (finalArray.length() > 0) {
                saveToCache(key, finalArray.toString());
                return parseJson(finalArray.toString(), param);
            } else {
                return parseJson(finalArray.toString(), param);
            }

        } else {
            throw new IllegalArgumentException();
        }

    }

    private HttpParamObject getCopy(int count, HttpParamObject object, String cityId) {
        HttpParamObject copy = new HttpParamObject();
        copy.setUrl(AppConstants.PAGE_URL.GET_LOCALITIES);
        copy.setClassType(object.getClassType());
        copy.addParameter("cityId", cityId);
        copy.addParameter("count", "1000");
        copy.addParameter("offset", (count * 1000) + "");
        return copy;
    }

    private JSONArray getLocalities(int count, String key, HttpParamObject param) throws JSONException, SQLException, NullPointerException, RestException, ClassCastException, IOException {

        final OkHttpClient client = OKHttpService.getUnsafeOkHttpClient();

        final MediaType mediaType = MediaType.parse(param.getContentType());

        if ("GET".equalsIgnoreCase(param.getMethod())) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(param.getUrl()).newBuilder();
            for (Map.Entry<String, String> entry : param.getPostParams().entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            String url = urlBuilder.build().toString();
            Log.d("LocalityURL", "" + url);
            param.setUrl(url);
        }
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
                .url(param.getUrl());

        if ("GET".equalsIgnoreCase(param.getMethod())) {
            builder = builder.get();
        }

        // Add Headers
        for (Map.Entry<String, String> pair : param.getHeaders().entrySet()) {
            builder.addHeader(pair.getKey(), pair.getValue());
        }

//            builder.(60, TimeUnit.SECONDS)
//                    .readTimeout(60, TimeUnit.SECONDS)
//                    .build()

        final Response response = client.newCall(builder.build()).execute();

        if (response.isSuccessful()) {
            String json = response.body().string();
            if (!TextUtils.isEmpty(json)) {
                JSONArray array = new JSONArray(json);
                if (array != null && array.length() > 0)
                    return array;
            } else {
                return null;
            }

        } else {
            return null;
        }

        return null;
    }

    private void saveToCache(String key, String data) {
        Preferences.saveData(key, data);
        Preferences.saveData(key + "time", new Date().getTime() + 24 * 60 * 60 * 1000);
    }

    private String getDataFromCache(String key) {
        String data = Preferences.getData(key, "");
        return data;
    }

    private boolean isCacheValid(String key) {
        long time = new Date().getTime();
        long cachedTime = Preferences.getData(key + "time1", time);
        return cachedTime > time;
    }
}


