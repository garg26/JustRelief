package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 5/23/17.
 */

public class FindDoctorResponse {
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("CityID")
    @Expose
    private String cityID;
    @SerializedName("Specialization")
    @Expose
    private String specialization;
    @SerializedName("CouncilNumber")
    @Expose
    private String councilNumber;
    @SerializedName("CouncilName")
    @Expose
    private String councilName;
    @SerializedName("CouncilYear")
    @Expose
    private String councilYear;

    public FindDoctorResponse(String doctorName, String specializationID, String cityID, String councilNumber, String councilName, String councilYear) {
        this.doctorName=doctorName;
        this.specialization=specializationID;
        this.cityID=cityID;
        this.councilNumber=councilNumber;
        this.councilName=councilName;
        this.councilYear=councilYear;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getCouncilNumber() {
        return councilNumber;
    }

    public void setCouncilNumber(String councilNumber) {
        this.councilNumber = councilNumber;
    }

    public String getCouncilName() {
        return councilName;
    }

    public void setCouncilName(String councilName) {
        this.councilName = councilName;
    }

    public String getCouncilYear() {
        return councilYear;
    }

    public void setCouncilYear(String councilYear) {
        this.councilYear = councilYear;
    }
}
