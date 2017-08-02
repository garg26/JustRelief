package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/14/17.
 */

public class SetServiceItem {
    @SerializedName("DoctorID")
    @Expose
    private String doctorID;
    @SerializedName("Service")
    @Expose
    private String service;

    public SetServiceItem(String doctorID, String valueIDs) {
        this.doctorID = doctorID;
        service = valueIDs;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

}
