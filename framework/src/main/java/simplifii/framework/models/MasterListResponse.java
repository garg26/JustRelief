package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import simplifii.framework.models.response.MasterValues;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Util;


public class MasterListResponse {
    @SerializedName("Table")
    @Expose
    private List<MasterValues> table = null;
    @SerializedName("Table1")
    @Expose
    private List<MasterValues> table1 = null;
    @SerializedName("Table2")
    @Expose
    private List<MasterValues> table2 = null;
    @SerializedName("Table3")
    @Expose
    private List<MasterValues> table3 = null;

    public List<MasterValues> getTable() {
        return table;
    }

    public void setTable(List<MasterValues> table) {
        this.table = table;
    }

    public List<MasterValues> getTable1() {
        return table1;
    }

    public void setTable1(List<MasterValues> table1) {
        this.table1 = table1;
    }

    public List<MasterValues> getTable2() {
        return table2;
    }

    public void setTable2(List<MasterValues> table2) {
        this.table2 = table2;
    }

    public List<MasterValues> getTable3() {
        return table3;
    }

    public void setTable3(List<MasterValues> table3) {
        this.table3 = table3;
    }

//    public static List<MasterValues> parseJson(String json) throws JSONException {
//        List<MasterValues> values = new ArrayList<>();
//        JSONObject object=new JSONObject(json);
//
//        Iterator<String> keys = object.keys();
//
//        while (keys.hasNext()){
//            JSONArray jsonArray = object.getJSONArray(keys.next());
//            List<MasterValues> responseList = Util.toList(jsonArray.toString(), MasterValues.class);
//            values.addAll(responseList);
//        }
//        return values;
//    }
}
