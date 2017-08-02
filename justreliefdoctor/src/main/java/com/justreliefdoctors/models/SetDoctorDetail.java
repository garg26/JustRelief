package com.justreliefdoctors.models;

/**
 * Created by kartikeya-pc on 6/21/17.
 */

public class SetDoctorDetail {
    private String val;

    public SetDoctorDetail(String doctorID) {
        val=doctorID;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
