package simplifii.framework.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import simplifii.framework.models.UserLoginResponse;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Preferences;

/**
 * Created by kartikeya-pc on 6/5/17.
 */

public class DoctorResponse {

    private static DoctorResponse instance;

    @SerializedName("DoctorID")
    @Expose
    private Integer doctorID;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("IsPublished")
    @Expose
    private Boolean isPublished;
    @SerializedName("ParentID")
    @Expose
    private Integer parentID;
    @SerializedName("DoctorImage")
    @Expose
    private String doctorImage;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("ClinicCount")
    @Expose
    private Integer clinicCount;
    @SerializedName("UClinicCount")
    @Expose
    private Integer uClinicCount;

    public Integer getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Integer doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getClinicCount() {
        return clinicCount;
    }

    public void setClinicCount(Integer clinicCount) {
        this.clinicCount = clinicCount;
    }

    public Integer getUClinicCount() {
        return uClinicCount;
    }

    public void setUClinicCount(Integer uClinicCount) {
        this.uClinicCount = uClinicCount;
    }

    public static DoctorResponse getInstance() {
        String json = Preferences.getData(Preferences.USER_DETAILS, null);
        return setJson(json);
    }

    public static DoctorResponse setJson(String json) {
        DoctorResponse response = (DoctorResponse) JsonUtil.parseJson(json, DoctorResponse.class);
        if (response != null) {
            Preferences.saveData(Preferences.USER_DETAILS, json);

        }
        instance = response;
        return response;
    }

}
