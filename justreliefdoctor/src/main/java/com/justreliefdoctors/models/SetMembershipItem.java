package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetMembershipItem {
    @SerializedName("DoctorID")
    @Expose
    private String doctorID;
    @SerializedName("Membership")
    @Expose
    private String membership;

    public SetMembershipItem(String doctorID, String membershipID) {
        this.doctorID=doctorID;
        membership = membershipID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

}
