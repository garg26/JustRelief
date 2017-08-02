package simplifii.framework.asyncmanager;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;

import simplifii.framework.exceptionhandler.RestException;

/**
 * Created by nbansal2211 on 28/01/17.
 */

public class FetchAssignmentClassesService extends OKHttpService {
    @Override
    public Object getData(Object... params) throws JSONException, SQLException, NullPointerException, RestException, ClassCastException, IOException {
        if(params != null && params.length == 2){
            HttpParamObject classObject = (HttpParamObject) params[0];
            HttpParamObject assignmentObject = (HttpParamObject) params[1];

        }else{
            throw new IllegalArgumentException();
        }
        return super.getData(params);
    }
}
