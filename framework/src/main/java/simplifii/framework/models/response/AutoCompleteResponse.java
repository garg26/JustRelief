package simplifii.framework.models.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.JsonUtil;

/**
 * Created by nbansal2211 on 16/05/17.
 */

public class AutoCompleteResponse {

    private List<MasterValues> list = new ArrayList<>();

    public List<MasterValues> getList() {
        return list;
    }

    public void setList(List<MasterValues> list) {
        this.list = list;
    }

    public static List<MasterValues> parseJson(String json)  {

        List<MasterValues> values = new ArrayList<>();
        try {
          //  JSONObject jsonObject =new JSONObject(json);
            JSONArray array = new JSONArray(json);
            //JSONArray  array =  jsonObject.getJSONObject("Table");
        if (array != null && array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                MasterValues v = (MasterValues) JsonUtil.parseJson(obj.toString(), MasterValues.class);
                if (v != null)
                    values.add(v);

            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return values;
    }
}
