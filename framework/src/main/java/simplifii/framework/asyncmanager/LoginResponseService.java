package simplifii.framework.asyncmanager;

import simplifii.framework.models.UserLoginResponse;
import simplifii.framework.utility.Preferences;

/**
 * Created by nbansal2211 on 16/05/17.
 */

public class LoginResponseService extends OKHttpService {

    @Override
    protected Object parseJson(String jsonString, HttpParamObject paramObject) {
        UserLoginResponse userLoginResponse = (UserLoginResponse) super.parseJson(jsonString, paramObject);
        if (userLoginResponse != null && userLoginResponse.isSuccess()) {
            String authResponse = response.header("Authorization");
            if (authResponse != null) {
                userLoginResponse.setAuthToken(authResponse);
                Preferences.saveData(Preferences.KEY_AUTH_TOKEN, authResponse);
            }
        }
        return userLoginResponse;
    }
}
