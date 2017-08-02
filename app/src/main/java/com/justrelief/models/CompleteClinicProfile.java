package com.justrelief.models;

import com.justrelief.models.response.GetClinicFileResponse;
import com.justrelief.models.response.GetClinicResponseTable;

/**
 * Created by kartikeya-pc on 7/3/17.
 */

public class CompleteClinicProfile {

    private GetClinicResponseTable clinicResponseTable;
    private GetClinicFileResponse clinicFileResponse;

    public GetClinicResponseTable getClinicResponseTable() {
        return clinicResponseTable;
    }

    public void setClinicResponseTable(GetClinicResponseTable clinicResponseTable) {
        this.clinicResponseTable = clinicResponseTable;
    }

    public GetClinicFileResponse getClinicFileResponse() {
        return clinicFileResponse;
    }

    public void setClinicFileResponse(GetClinicFileResponse clinicFileResponse) {
        this.clinicFileResponse = clinicFileResponse;
    }
}
