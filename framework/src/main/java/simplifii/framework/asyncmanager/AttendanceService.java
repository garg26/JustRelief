package simplifii.framework.asyncmanager;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.Logger;

/**
 * Created by nbansal2211 on 08/11/16.
 */

public class AttendanceService extends HttpRestService {

    @Override
    public Object getData(Object... params) throws JSONException, SQLException, NullPointerException, RestException, ClassCastException, IOException {
        List<HttpParamObject> objects = (List<HttpParamObject>) params[0];
        for (HttpParamObject obj : objects) {
            String data = (String) super.getData(obj);
            Logger.error("AttendanceService", data + "");
        }
        return true;
    }
}
