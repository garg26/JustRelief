package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/16/17.
 */

public class SetDocSpecializationItem {
    @SerializedName("DoctorID")
    @Expose
    private String doctorID;
    @SerializedName("specialization")
    @Expose
    private String specialization;

    public SetDocSpecializationItem(String doctorID, String valueIDs) {
        this.doctorID = doctorID;
        specialization=valueIDs;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
