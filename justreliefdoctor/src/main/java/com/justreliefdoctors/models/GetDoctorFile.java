package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/13/17.
 */

public class GetDoctorFile {
    @SerializedName("FacilityID")
    @Expose
    private String facilityID;
    @SerializedName("FileType")
    @Expose
    private String fileType;

    public GetDoctorFile(String doctorID, String fileType) {
        facilityID = doctorID;
        this.fileType = fileType;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
