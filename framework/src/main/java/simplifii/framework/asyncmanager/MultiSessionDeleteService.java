package simplifii.framework.asyncmanager;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import simplifii.framework.exceptionhandler.RestException;

/**
 * Created by nbansal2211 on 04/02/17.
 */

public class MultiSessionDeleteService extends GenericService {
    @Override
    public Object getData(Object... params) throws JSONException, SQLException, NullPointerException, RestException, ClassCastException, IOException {
        List<HttpParamObject> list = (List<HttpParamObject>) params[0];
        for (HttpParamObject p : list) {
            DeleteContentService service = new DeleteContentService();
            service.getData(p);
        }
        return true;
    }
}
