package simplifii.framework.asyncmanager;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.requestmodels.SelectContent;
import simplifii.framework.requestmodels.UploadImageResponse;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.CollectionUtils;

/**
 * Created by nbansal2211 on 19/12/16.
 */

public class PostFeedBaseService extends GenericService {
    @Override
    public Object getData(Object... params) throws JSONException, SQLException, NullPointerException, RestException, ClassCastException, IOException {
        if (params != null) {
            List<SelectContent> contentList = (List<SelectContent>) params[0];
            JSONArray responseArray = new JSONArray();
            if (!CollectionUtils.isEmpty(contentList)) {
                for (SelectContent content : contentList) {
                    JSONObject obj = new JSONObject();
                    if (!TextUtils.isEmpty(content.getUri())) {
                        obj.put("uri", content.getUri());
                        obj.put("mimetype", content.getMimetype());
                        responseArray.put(obj);
                    } else {
                        FileParamObject fileParamObject = getFileParam(content);
//                        FileUploadService service = new FileUploadService();
                        AudioUploadService service = new AudioUploadService();
                        UploadImageResponse response = (UploadImageResponse) service.getData(fileParamObject);
                        if (response != null) {
                            obj.put("uri", response.getData().getUri());
                            obj.put("mimetype", response.getData().getMimetype());
                            responseArray.put(obj);
                        }
                    }
                }
                return responseArray;
            }
        }

        return null;
    }

    private FileParamObject getFileParam(SelectContent content) {
        File file = new File(content.getFilePath());
        if (file.exists()) {
            FileParamObject paramObject = new FileParamObject(file, file.getName(), "file");
            paramObject.setUrl(AppConstants.PAGE_URL.UPLOAD_FILE);
            paramObject.setClassType(UploadImageResponse.class);
            paramObject.setPostMethod();
            return paramObject;
        }
        return null;
    }

}
