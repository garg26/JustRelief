package com.justreliefdoctors.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kartikeya-pc on 6/12/17.
 */

public class GetDoctorDetails implements Serializable{
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("DoctorPhone")
    @Expose
    private String doctorPhone;
    @SerializedName("DoctorImageURL")
    @Expose
    private String doctorImageURL;
    @SerializedName("DoctorEmail")
    @Expose
    private String doctorEmail;
    @SerializedName("DoctorAddress")
    @Expose
    private String doctorAddress;
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("AboutDoctor")
    @Expose
    private String aboutDoctor;
    @SerializedName("DoctorGender")
    @Expose
    private String doctorGender;
    @SerializedName("DoctorExperience")
    @Expose
    private String doctorExperience;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("DoctorTitle")
    @Expose
    private String doctorTitle;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorImageURL() {
        return doctorImageURL;
    }

    public void setDoctorImageURL(String doctorImageURL) {
        this.doctorImageURL = doctorImageURL;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorAddress() {
        return doctorAddress;
    }

    public void setDoctorAddress(String doctorAddress) {
        this.doctorAddress = doctorAddress;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAboutDoctor() {
        return aboutDoctor;
    }

    public void setAboutDoctor(String aboutDoctor) {
        this.aboutDoctor = aboutDoctor;
    }

    public String getDoctorGender() {
        return doctorGender;
    }

    public void setDoctorGender(String doctorGender) {
        this.doctorGender = doctorGender;
    }

    public String getDoctorExperience() {
        return doctorExperience;
    }

    public void setDoctorExperience(String doctorExperience) {
        this.doctorExperience = doctorExperience;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDoctorTitle() {
        return doctorTitle;
    }

    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle;
    }

}
