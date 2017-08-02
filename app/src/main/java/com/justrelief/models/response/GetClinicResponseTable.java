package com.justrelief.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import simplifii.framework.models.response.ClinicResponse;
import simplifii.framework.models.response.MasterValues;

/**
 * Created by kartikeya-pc on 6/9/17.
 */

public class GetClinicResponseTable implements Serializable {
    @SerializedName("Table")
    @Expose
    private List<GetClinicResponse> table = null;
//    @SerializedName("Table1")
//    @Expose
//    private List<Object> table1 = null;
    @SerializedName("Table2")
    @Expose
    private List<GetPaymentResponse> table2 = null;
    @SerializedName("Table3")
    @Expose
    private List<MasterValues> table3 = null;
    @SerializedName("Table4")
    @Expose
    private List<MasterValues> table4 = null;

    public List<GetClinicResponse> getTable() {
        return table;
    }

    public void setTable(List<GetClinicResponse> table) {
        this.table = table;
    }

//    public List<Object> getTable1() {
//        return table1;
//    }

//    public void setTable1(List<Object> table1) {
//        this.table1 = table1;
//    }

    public List<GetPaymentResponse> getTable2() {
        return table2;
    }

    public void setTable2(List<GetPaymentResponse> table2) {
        this.table2 = table2;
    }

    public List<MasterValues> getTable3() {
        return table3;
    }

    public void setTable3(List<MasterValues> table3) {
        this.table3 = table3;
    }

    public List<MasterValues> getTable4() {
        return table4;
    }

    public void setTable4(List<MasterValues> table4) {
        this.table4 = table4;
    }

}
