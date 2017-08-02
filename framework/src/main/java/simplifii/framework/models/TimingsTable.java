package simplifii.framework.models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import simplifii.framework.models.response.TimingsResponse;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Util;

/**
 * Created by kartikeya-pc on 6/3/17.
 */

public class TimingsTable {

    private Object object;

    @SerializedName("Table")
    @Expose
    private List<TimingsResponse> list = new ArrayList<>();

    private TimingsTable(Object object){
        this.object = object;
    }

    public List<TimingsResponse> getTable() {
        return list;
    }

    public void setTable(List<TimingsResponse> table) {
        this.list = table;
    }
//    public static List<TimingsResponse> parseJson(String json) throws JSONException {
//
//        List<TimingsResponse> tableList = new ArrayList<>();
//        JSONObject object=new JSONObject(json);
//        Iterator<String> keys = object.keys();
//
//        while (keys.hasNext()){
//            JSONArray jsonArray = object.getJSONArray(keys.next());
//            List<TimingsResponse> responseList = Util.toList(jsonArray.toString(), TimingsResponse.class);
//            tableList.addAll(responseList);
//        }
//         return tableList;
//    }
}
