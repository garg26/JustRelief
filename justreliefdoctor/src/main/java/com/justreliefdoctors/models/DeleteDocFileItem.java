package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/13/17.
 */

public class DeleteDocFileItem {
    @SerializedName("DoctorID")
    @Expose
    private String doctorID;
    @SerializedName("FileID")
    @Expose
    private String fileID;
    @SerializedName("Type")
    @Expose
    private String type;

    public DeleteDocFileItem(String doctorID, String fileID, String fileType) {
        this.doctorID=doctorID;
        this.fileID=fileID;
        type=fileType;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
