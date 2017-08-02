package simplifii.framework.asyncmanager;

import android.util.Log;
import android.webkit.MimeTypeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.JsonUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by raghu on 10/2/17.
 */

public class AudioUploadService extends HttpRestService {
    @Override
    public Object getData(Object... params) throws JSONException, SQLException, NullPointerException, RestException, ClassCastException, IOException {
        if (params != null && params.length > 0) {
            FileParamObject fileParamObject = (FileParamObject) params[0];

            OkHttpClient client = OKHttpService.getUnsafeOkHttpClient();

            OkHttpClient.Builder builder = client.newBuilder();
            builder.connectTimeout(10, TimeUnit.MINUTES);
            builder.readTimeout(10, TimeUnit.MINUTES);
            client = builder.build();
            String mediaType = URLConnection.guessContentTypeFromName(fileParamObject.getFileName());
//            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
//            RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"file\"; filename=\"abc.mp3\"\r\nContent-Type: audio/mpeg\r\n\r\n\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--", fileParamObject.getFile());
//

//            fileParamObject.getFile().get
            String fileName = System.currentTimeMillis() + "." + getExtension(fileParamObject.getFileName());
            Log.d("FileName", fileName);

            MultipartBody.Builder multiPartBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    ;

            if (fileParamObject.getPostParams() != null) {
                for (Map.Entry<String, String> entry : fileParamObject.getPostParams().entrySet()) {
                    multiPartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
            multiPartBuilder.addFormDataPart(fileParamObject.getFileKeyName(), fileName, RequestBody.create(MediaType.parse(mediaType), fileParamObject.getFile()));
            RequestBody requestBody = multiPartBuilder.build();

            Request request = new Request.Builder()
                    .url(fileParamObject.getUrl())
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return parseJson(response.body().string(), fileParamObject);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static String getExtension(String fileName) {
        String encoded;
        try {
            encoded = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            encoded = fileName;
        }
        return MimeTypeMap.getFileExtensionFromUrl(encoded).toLowerCase();
    }
}
