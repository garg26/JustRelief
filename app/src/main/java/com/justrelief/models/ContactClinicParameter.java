package com.justrelief.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ContactClinicParameter {
    @SerializedName("FacilityID")
    @Expose
    private String facilityID;
    @SerializedName("FacilityPhone")
    @Expose
    private String facilityPhone;
    @SerializedName("FacilityEmail")
    @Expose
    private String facilityEmail;
    @SerializedName("FaciliyAbout")
    @Expose
    private String faciliyAbout;
    @SerializedName("FacilityPayMode")
    @Expose
    private String facilityPayMode;

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

    public String getFaciliyAbout() {
        return faciliyAbout;
    }

    public void setFaciliyAbout(String faciliyAbout) {
        this.faciliyAbout = faciliyAbout;
    }

    public String getFacilityPayMode() {
        return facilityPayMode;
    }

    public void setFacilityPayMode(String facilityPayMode) {
        this.facilityPayMode = facilityPayMode;
    }

}
