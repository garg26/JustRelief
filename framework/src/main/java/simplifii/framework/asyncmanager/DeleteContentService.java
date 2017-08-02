package simplifii.framework.asyncmanager;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

/**
 * Created by nbansal2211 on 22/11/16.
 */

public class DeleteContentService extends OKHttpService {

    @Override
    public Object getData(Object... params) throws JSONException, SQLException, NullPointerException, RestException, ClassCastException, IOException {
        HttpParamObject param = (HttpParamObject) params[0];
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(param.getUrl())
                .delete(null)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("x-access-token", Preferences.getData(AppConstants.PREF_KEYS.USER_TOKEN, ""))
                .addHeader("postman-token", "3ce63f77-32b9-53d9-7fb9-6b93891587e3")
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return parseJson(response.body().string(), param);
        } else {
            throw new RestException(response.code(), getResponseReason(response.body().string()));
        }


    }
}
