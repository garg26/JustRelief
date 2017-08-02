package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kartikeya-pc on 5/27/17.
 */

public class ServicesSpecializationResponse {
    @SerializedName("FacilityID")
    @Expose
    private String facilityID;
    @SerializedName("Service")
    @Expose
    private String service;
    @SerializedName("Specialization")
    @Expose
    private String specialization;

    public ServicesSpecializationResponse(String facilityID, String services_selected_list, String specialization_selected_list) {

        this.facilityID = facilityID;
        service = services_selected_list;
        specialization = specialization_selected_list;

    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }




}
