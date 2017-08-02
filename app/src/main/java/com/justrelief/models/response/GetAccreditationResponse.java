package com.justrelief.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/10/17.
 */

public class GetAccreditationResponse {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("DocDesc")
    @Expose
    private String docDesc;
    @SerializedName("AccrYear")
    @Expose
    private Integer accrYear;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getDocDesc() {
        return docDesc;
    }

    public void setDocDesc(String docDesc) {
        this.docDesc = docDesc;
    }

    public Integer getAccrYear() {
        return accrYear;
    }

    public void setAccrYear(Integer accrYear) {
        this.accrYear = accrYear;
    }

}
