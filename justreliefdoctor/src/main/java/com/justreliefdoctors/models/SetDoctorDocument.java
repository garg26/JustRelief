package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/14/17.
 */

public class SetDoctorDocument {
    @SerializedName("DoctorID")
    @Expose
    private String doctorID;
    @SerializedName("DocType")
    @Expose
    private String docType;
    @SerializedName("Extension")
    @Expose
    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public SetDoctorDocument(String doctorID, String documentType,String extension) {
        this.doctorID=doctorID;
        docType=documentType;
        this.extension=extension;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

}
