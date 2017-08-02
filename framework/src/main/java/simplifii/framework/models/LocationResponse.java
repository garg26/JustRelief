package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 5/27/17.
 */

public class LocationResponse {
    @SerializedName("FacilityID")
    @Expose
    private String facilityID;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("LocalityName")
    @Expose
    private String localityName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("PinCode")
    @Expose
    private String pinCode;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;

    public LocationResponse(String facilityID, String cityName, String localityName, String address, String pinCode, double latitude, double longitude) {

        this.facilityID = facilityID;
        this.cityName = cityName;
        this.localityName = localityName;
        this.address = address;
        this.pinCode = pinCode;
        this.latitude = String.valueOf(latitude);
        this.longitude = String.valueOf(longitude);

    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
