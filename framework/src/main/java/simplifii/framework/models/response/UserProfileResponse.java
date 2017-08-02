package simplifii.framework.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.JsonUtil;

/**
 * Created by kartikeya-pc on 6/5/17.
 */

public class UserProfileResponse {
    @SerializedName("Table")
    @Expose
    private List<ClinicResponse> clinicList = null;
    @SerializedName("Table1")
    @Expose
    private List<DoctorResponse> doctorList = null;

    public List<ClinicResponse> getTable() {
        return clinicList;
    }

    public void setTable(List<ClinicResponse> table) {
        this.clinicList = table;
    }

    public List<DoctorResponse> getTable1() {
        return doctorList;
    }

    public void setTable1(List<DoctorResponse> table1) {
        this.doctorList = table1;
    }



}
