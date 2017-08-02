package com.justrelief.models;

/**
 * Created by kartikeya-pc on 7/3/17.
 */

public class SetClinicDetail {
    private String val;

    public SetClinicDetail(String doctorID) {
        val=doctorID;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
