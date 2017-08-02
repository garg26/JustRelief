package com.justreliefdoctors.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kartikeya-pc on 6/13/17.
 */

public class GetDoctorAwardTableResponse {
    @SerializedName("Table")
    @Expose
    private List<GetDoctorAwardResponse> table = null;

    public List<GetDoctorAwardResponse> getTable() {
        return table;
    }

    public void setTable(List<GetDoctorAwardResponse> table) {
        this.table = table;
    }
}

