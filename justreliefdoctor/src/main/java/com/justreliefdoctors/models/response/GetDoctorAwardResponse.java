package com.justreliefdoctors.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/13/17.
 */

public class GetDoctorAwardResponse {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("AwardDesc")
    @Expose
    private String awardDesc;
    @SerializedName("AwardYear")
    @Expose
    private Integer awardYear;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getAwardDesc() {
        return awardDesc;
    }

    public void setAwardDesc(String awardDesc) {
        this.awardDesc = awardDesc;
    }

    public Integer getAwardYear() {
        return awardYear;
    }

    public void setAwardYear(Integer awardYear) {
        this.awardYear = awardYear;
    }
}
