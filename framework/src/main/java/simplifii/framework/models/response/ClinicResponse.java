package simplifii.framework.models.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import simplifii.framework.models.UserLoginResponse;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Preferences;

/**
 * Created by kartikeya-pc on 6/5/17.
 */

public class ClinicResponse {

    private static ClinicResponse instance;

    @SerializedName("FacilityID")
    @Expose
    private Integer facilityID;
    @SerializedName("FacilityName")
    @Expose
    private String facilityName;
    @SerializedName("IsPublished")
    @Expose
    private Boolean isPublished;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("ParentID")
    @Expose
    private Integer parentID;
    @SerializedName("FacilityImage")
    @Expose
    private String facilityImage;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("DoctorCount")
    @Expose
    private Integer doctorCount;
    @SerializedName("UDoctorCount")
    @Expose
    private Integer uDoctorCount;

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public String getFacilityImage() {
        return facilityImage;
    }

    public void setFacilityImage(String facilityImage) {
        this.facilityImage = facilityImage;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getDoctorCount() {
        return doctorCount;
    }

    public void setDoctorCount(Integer doctorCount) {
        this.doctorCount = doctorCount;
    }

    public Integer getUDoctorCount() {
        return uDoctorCount;
    }

    public void setUDoctorCount(Integer uDoctorCount) {
        this.uDoctorCount = uDoctorCount;
    }

    public static List<ClinicResponse> getInstance()  {
        String json = Preferences.getData(Preferences.KEY_USER, null);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ClinicResponse>>() {}.getType();
        List<ClinicResponse> clinicResponses = gson.fromJson(json, listType);

        return clinicResponses;
    }

    public static void setJson(String json)  {
        Preferences.saveData(Preferences.KEY_USER, json);
    }

}
