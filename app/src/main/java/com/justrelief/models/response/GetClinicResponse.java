package com.justrelief.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kartikeya-pc on 6/9/17.
 */

public class GetClinicResponse implements Serializable{
    @SerializedName("FacilityName")
    @Expose
    private String facilityName;
    @SerializedName("FacilityPhone")
    @Expose
    private String facilityPhone;
    @SerializedName("FacilityImage")
    @Expose
    private String facilityImage;
    @SerializedName("FacilityEmailId")
    @Expose
    private String facilityEmailId;
    @SerializedName("isPublished")
    @Expose
    private Boolean isPublished;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Locality")
    @Expose
    private String locality;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("PinCode")
    @Expose
    private String pinCode;
    @SerializedName("OverView")
    @Expose
    private String overView;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityPhone() {
        return facilityPhone;
    }

    public void setFacilityPhone(String facilityPhone) {
        this.facilityPhone = facilityPhone;
    }

    public String getFacilityImage() {
        return facilityImage;
    }

    public void setFacilityImage(String facilityImage) {
        this.facilityImage = facilityImage;
    }

    public String getFacilityEmailId() {
        return facilityEmailId;
    }

    public void setFacilityEmailId(String facilityEmailId) {
        this.facilityEmailId = facilityEmailId;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

}
