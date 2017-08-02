package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/15/17.
 */

public class GetDoctorEducation {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("QualName")
    @Expose
    private String qualName;
    @SerializedName("CollegeName")
    @Expose
    private String collegeName;
    @SerializedName("PassYear")
    @Expose
    private Integer passYear;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getQualName() {
        return qualName;
    }

    public void setQualName(String qualName) {
        this.qualName = qualName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public Integer getPassYear() {
        return passYear;
    }

    public void setPassYear(Integer passYear) {
        this.passYear = passYear;
    }
}
