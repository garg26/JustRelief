package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ContactClinicResponse {

    @SerializedName("FacilityID")
    @Expose
    private String facilityID;
    @SerializedName("FacilityPhone")
    @Expose
    private String facilityPhone;
    @SerializedName("FacilityEmail")
    @Expose
    private String facilityEmail;
    @SerializedName("FacilityAbout")
    @Expose
    private String facilityAbout;
    @SerializedName("FacilityPayMode")
    @Expose
    private String facilityPayMode;
    @SerializedName("Extension")
    @Expose
    private String extension;

    public ContactClinicResponse(String facilityID, String facilityPhone, String facilityEmail, String facilityAbout, String facilityPayMode, String extension) {
        this.facilityID=facilityID;
        this.facilityPhone=facilityPhone;
        this.facilityEmail=facilityEmail;
        this.facilityAbout=facilityAbout;
        this.facilityPayMode=facilityPayMode;
        this.extension=extension;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getFacilityPhone() {
        return facilityPhone;
    }

    public void setFacilityPhone(String facilityPhone) {
        this.facilityPhone = facilityPhone;
    }

    public String getFacilityEmail() {
        return facilityEmail;
    }

    public void setFacilityEmail(String facilityEmail) {
        this.facilityEmail = facilityEmail;
    }

    public String getFacilityAbout() {
        return facilityAbout;
    }

    public void setFacilityAbout(String facilityAbout) {
        this.facilityAbout = facilityAbout;
    }

    public String getFacilityPayMode() {
        return facilityPayMode;
    }

    public void setFacilityPayMode(String facilityPayMode) {
        this.facilityPayMode = facilityPayMode;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}
