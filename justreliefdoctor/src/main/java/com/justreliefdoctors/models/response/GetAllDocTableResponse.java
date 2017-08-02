package com.justreliefdoctors.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.justreliefdoctors.models.GetDoctorEducation;

import java.util.List;

/**
 * Created by kartikeya-pc on 6/14/17.
 */

public class GetAllDocTableResponse {
    @SerializedName("Table")
    @Expose
    private List<GetDoctorEducation> table = null;
    @SerializedName("Table1")
    @Expose
    private List<GetCouncilDetailResponse> table1 = null;
    @SerializedName("Table2")
    @Expose
    private List<GetDoctorDocumentResponse> table2 = null;
    @SerializedName("Table3")
    @Expose
    private List<GetDocExperienceResponse> table3 = null;
    @SerializedName("Table4")
    @Expose
    private List<GetDoctorAwardResponse> table4 = null;

    public List<GetDoctorEducation> getTable() {
        return table;
    }

    public void setTable(List<GetDoctorEducation> table) {
        this.table = table;
    }

    public List<GetCouncilDetailResponse> getTable1() {
        return table1;
    }

    public void setTable1(List<GetCouncilDetailResponse> table1) {
        this.table1 = table1;
    }

    public List<GetDoctorDocumentResponse> getTable2() {
        return table2;
    }

    public void setTable2(List<GetDoctorDocumentResponse> table2) {
        this.table2 = table2;
    }

    public List<GetDocExperienceResponse> getTable3() {
        return table3;
    }

    public void setTable3(List<GetDocExperienceResponse> table3) {
        this.table3 = table3;
    }

    public List<GetDoctorAwardResponse> getTable4() {
        return table4;
    }

    public void setTable4(List<GetDoctorAwardResponse> table4) {
        this.table4 = table4;
    }
}
