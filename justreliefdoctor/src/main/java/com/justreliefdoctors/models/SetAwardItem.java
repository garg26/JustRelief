package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/13/17.
 */

public class SetAwardItem {
    @SerializedName("DoctorID")
    @Expose
    private String doctorID;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Year")
    @Expose
    private String year;

    public SetAwardItem(String doctorID, String title, String year) {
        this.doctorID = doctorID;
        this.title = title;
        this.year = year;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
