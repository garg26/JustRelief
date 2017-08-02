package com.justreliefdoctors.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/14/17.
 */

public class GetCouncilDetailResponse {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("CouncilNumber")
    @Expose
    private String councilNumber;
    @SerializedName("CouncilName")
    @Expose
    private String councilName;
    @SerializedName("CouncilYear")
    @Expose
    private Integer councilYear;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getCouncilNumber() {
        return councilNumber;
    }

    public void setCouncilNumber(String councilNumber) {
        this.councilNumber = councilNumber;
    }

    public String getCouncilName()   {
        return councilName;
    }

    public void setCouncilName(String councilName) {
        this.councilName = councilName;
    }

    public Integer getCouncilYear() {
        return councilYear;
    }

    public void setCouncilYear(Integer councilYear) {
        this.councilYear = councilYear;
    }

}
