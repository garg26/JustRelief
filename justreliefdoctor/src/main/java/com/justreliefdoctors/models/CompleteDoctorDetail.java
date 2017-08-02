package com.justreliefdoctors.models;

import com.justreliefdoctors.models.response.GetAllDocTableResponse;
import com.justreliefdoctors.models.response.GetDoctorDetailsTable;

/**
 * Created by kartikeya-pc on 6/20/17.
 */

public class CompleteDoctorDetail {
    private GetDoctorDetailsTable detailsTable;
    private GetAllDocTableResponse tableResponse;

    public GetDoctorDetailsTable getDetailsTable() {
        return detailsTable;
    }

    public void setDetailsTable(GetDoctorDetailsTable detailsTable) {
        this.detailsTable = detailsTable;
    }

    public   GetAllDocTableResponse getTableResponse() {
        return tableResponse;
    }

    public void setTableResponse(GetAllDocTableResponse tableResponse) {
        this.tableResponse = tableResponse;
    }



}
