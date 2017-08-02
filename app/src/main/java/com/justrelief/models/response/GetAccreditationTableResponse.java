package com.justrelief.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kartikeya-pc on 6/10/17.
 */

public class GetAccreditationTableResponse {
    @SerializedName("Table")
    @Expose
    private List<GetAccreditationResponse> table = null;

    public List<GetAccreditationResponse> getTable() {
        return table;
    }

    public void setTable(List<GetAccreditationResponse> table) {
        this.table = table;
    }
}
