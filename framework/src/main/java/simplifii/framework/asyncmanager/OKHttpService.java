package simplifii.framework.asyncmanager;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.JsonUtil;

/**
 * Created by robin on 9/27/16.
 */

public class OKHttpService extends GenericService {
    private static final String TAG = "HttpRestService";

    @Override
    public Object getData(Object... params) throws JSONException, SQLException, NullPointerException, RestException, ClassCastException, IOException {
        if (params != null && params.length > 0) {
            HttpParamObject param = (HttpParamObject) params[0];
            final OkHttpClient client = OKHttpService.getUnsafeOkHttpClient();

            final MediaType mediaType = MediaType.parse(param.getContentType());

            if ("GET".equalsIgnoreCase(param.getMethod())) {
                HttpUrl.Builder urlBuilder = HttpUrl.parse(param.getUrl()).newBuilder();
                for (Map.Entry<String, String> entry : param.getPostParams().entrySet()) {
                    urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                }
                String url = urlBuilder.build().toString();

                param.setUrl(url);
            }
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
                    .url(param.getUrl());

            if ("GET".equalsIgnoreCase(param.getMethod())) {
                builder = builder.get();
            } else if ("POST".equalsIgnoreCase(param.getMethod())) {
                if (TextUtils.isEmpty(param.getJson())) {
                    // It will contain post param string
                    param.setJson(getPostParamsString(param));
                }
                final RequestBody body = RequestBody.create(mediaType, param.getJson());
                builder = builder.post(body);
            } else if ("PUT".equalsIgnoreCase(param.getMethod())) {
                final RequestBody body = RequestBody.create(mediaType, param.getJson());
                builder = builder.put(body);
            } else if ("DELETE".equalsIgnoreCase(param.getMethod())) {
                final RequestBody body = RequestBody.create(mediaType, param.getJson());
                builder = builder.delete(body);
            }


            // Add Headers
            for (Map.Entry<String, String> pair : param.getHeaders().entrySet()) {
                builder.addHeader(pair.getKey(), pair.getValue());
            }

            final Response response = client.newCall(builder.build()).execute();
            if (response.isSuccessful()) {
                return parseJson(response.body().string(), param);
            } else {
                throw new RestException(response.code(), getResponseReason(response.body().string()));
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    protected String getResponseReason(String body) {
        try {
            JSONObject obj = new JSONObject(body);
            if (obj.has("msg")) {
                return obj.getString("msg");
            }
            if (obj.has("errors")) {
                JSONArray errors = obj.getJSONArray("errors");
                if (errors.length() > 0) {
                    return errors.getString(0);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Please hang on! We are facing some technical issues";
    }

    protected String getPostParamsString(HttpParamObject obj) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : obj.getPostParams().entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    protected Object parseJson(String jsonString, HttpParamObject paramObject) {

        Log.d(TAG, "JSON Response:" + jsonString);
        if (null == paramObject.getClassType()) {
            return jsonString;
        }

        // For custom parsing
        Class classType = paramObject.getClassType();
        Method m = null;

        Object o = null;
        try {
            m = classType.getDeclaredMethod("parseJson", String.class);
            o = m.invoke(null, jsonString);
            return o;
        } catch (Exception e) {
//            e.printStackTrace();
            Log.e(TAG, "CustomParser not found");
        }

        // Parse with Gson
        return JsonUtil.parseJson(jsonString, paramObject.getClassType());

    }


    public static OkHttpClient getUnsafeOkHttpClient() {

        if (true) {
            return new OkHttpClient();
        }

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient = okHttpClient.newBuilder().sslSocketFactory(sslSocketFactory).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            }).build();
//            okHttpClient.ss(sslSocketFactory);
            HostnameVerifier verifier = okHttpClient.hostnameVerifier();
//            okHttpClient.hostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
