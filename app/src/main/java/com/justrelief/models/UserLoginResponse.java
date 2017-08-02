package com.justrelief.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kartikeya-pc on 5/15/17.
 */

public class UserLoginResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("LoginID")
    @Expose
    private String loginID;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("MobVerified")
    @Expose
    private String mobVerified;
    @SerializedName("EmailVerified")
    @Expose
    private String emailVerified;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("cliniclist")
    @Expose
    private List<Object> cliniclist = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobVerified() {
        return mobVerified;
    }

    public void setMobVerified(String mobVerified) {
        this.mobVerified = mobVerified;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Object> getCliniclist() {
        return cliniclist;
    }

    public void setCliniclist(List<Object> cliniclist) {
        this.cliniclist = cliniclist;
    }

}
