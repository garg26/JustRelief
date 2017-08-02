package com.justrelief.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kartikeya-pc on 7/3/17.
 */

public class GetClinicFileResponse {
    @SerializedName("Table")
    @Expose
    private List<Object> table = null;
    @SerializedName("Table1")
    @Expose
    private List<Object> table1 = null;
    @SerializedName("Table2")
    @Expose
    private List<Object> table2 = null;
    @SerializedName("Table3")
    @Expose
    private List<Object> table3 = null;
    @SerializedName("Table4")
    @Expose
    private List<Object> table4 = null;

    public List<Object> getTable() {
        return table;
    }

    public void setTable(List<Object> table) {
        this.table = table;
    }

    public List<Object> getTable1() {
        return table1;
    }

    public void setTable1(List<Object> table1) {
        this.table1 = table1;
    }

    public List<Object> getTable2() {
        return table2;
    }

    public void setTable2(List<Object> table2) {
        this.table2 = table2;
    }

    public List<Object> getTable3() {
        return table3;
    }

    public void setTable3(List<Object> table3) {
        this.table3 = table3;
    }

    public List<Object> getTable4() {
        return table4;
    }

    public void setTable4(List<Object> table4) {
        this.table4 = table4;
    }


}
