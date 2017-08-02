package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 5/24/17.
 */

public class DoctorDetailResponse {
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("CouncilNumber")
    @Expose
    private String councilNumber;
    @SerializedName("CouncilName")
    @Expose
    private String councilName;
    @SerializedName("Specialization")
    @Expose
    private Integer specialization;
    @SerializedName("CityName")
    @Expose
    private Integer cityName;
    @SerializedName("CouncilYear")
    @Expose
    private Integer councilYear;

    public DoctorDetailResponse(String doctor_name, String spec_id, String city_id, String registration_no, String registration_council, String year) {

        doctorName = doctor_name;
        specialization = Integer.valueOf(spec_id);
        cityName = Integer.valueOf(city_id);
        councilNumber = registration_no;
        councilName = registration_council;
        councilYear = Integer.valueOf(year);
    }

    public String getDoctorName() {
        return doctorName;
    }



    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public Integer getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Integer specialization) {
        this.specialization = specialization;
    }

    public Integer getCityName() {
        return cityName;
    }

    public void setCityName(Integer cityName) {
        this.cityName = cityName;
    }

    public Integer getCouncilYear() {
        return councilYear;
    }

    public void setCouncilYear(Integer councilYear) {
        this.councilYear = councilYear;
    }
}
