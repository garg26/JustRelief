package com.justreliefdoctors.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/15/17.
 */

public class GetDoctorDocumentResponse {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("DocumentType")
    @Expose
    private String documentType;
    @SerializedName("DocumentPath")
    @Expose
    private String documentPath;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

}
