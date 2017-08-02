package com.justrelief.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kartikeya-pc on 6/10/17.
 */

public class GetAwardResponseTable {
    @SerializedName("Table")
    @Expose
    private List<GetAwardResponse> table = null;

    public List<GetAwardResponse> getTable() {
        return table;
    }

    public void setTable(List<GetAwardResponse> table) {
        this.table = table;
    }
}
