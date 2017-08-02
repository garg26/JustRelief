package simplifii.framework.models;

import simplifii.framework.utility.AppConstants;

/**
 * Created by nbansal2211 on 16/05/17.
 */

public class AutoCompleteApiRequest {

    private String city;
    private String locality;
    private String type;
    private String record;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public static AutoCompleteApiRequest getAllCityRequest() {
        AutoCompleteApiRequest request = new AutoCompleteApiRequest();
        request.setRecord("all");
        request.setType(AppConstants.AutoCompleteTypes.CITY);
        return request;
    }
    public static AutoCompleteApiRequest getAllServicesRequest() {
        AutoCompleteApiRequest request = new AutoCompleteApiRequest();
        request.setRecord("all");
        request.setType(AppConstants.AutoCompleteTypes.SERVICES);
        return request;
    }

    public static AutoCompleteApiRequest getAllSpecializationRequest() {
        AutoCompleteApiRequest request = new AutoCompleteApiRequest();
        request.setRecord("all");
        request.setType(AppConstants.AutoCompleteTypes.DOCTOR_SPECIALIZATION);
        return request;
    }

    public static AutoCompleteApiRequest getAllRegistrationCouncil() {
        AutoCompleteApiRequest request = new AutoCompleteApiRequest();
        request.setRecord("all");
        request.setType(AppConstants.AutoCompleteTypes.REGISTRATION_COUNCIL);
        return request;
    }


    public static AutoCompleteApiRequest getAllLocalityRequest(String city) {
        AutoCompleteApiRequest request = new AutoCompleteApiRequest();
        request.setRecord("all");
        request.setType(AppConstants.AutoCompleteTypes.LOCALITY);
        request.setLocality("");
        request.setCity(city);
        return request;
    }
    public static AutoCompleteApiRequest getAllCollegeRequest() {
        AutoCompleteApiRequest request = new AutoCompleteApiRequest();
        request.setRecord("all");
        request.setType(AppConstants.AutoCompleteTypes.COLLEGE);
        return request;
    }
    public static AutoCompleteApiRequest getAllQUALIFICATIONRequest() {
        AutoCompleteApiRequest request = new AutoCompleteApiRequest();
        request.setRecord("all");
        request.setType(AppConstants.AutoCompleteTypes.QUALIFICATION);
        return request;
    }


    public static AutoCompleteApiRequest getAllMembershipRequest() {
        AutoCompleteApiRequest request = new AutoCompleteApiRequest();
        request.setRecord("all");
        request.setType(AppConstants.AutoCompleteTypes.MEMBERSHIP);
        return request;

    }

    public static AutoCompleteApiRequest getAllDoctorServiceRequest() {
        AutoCompleteApiRequest request = new AutoCompleteApiRequest();
        request.setRecord("all");
        request.setType(AppConstants.AutoCompleteTypes.DOCTOR_SERVICE);
        return request;
    }

    public static AutoCompleteApiRequest getAllDoctorSpecialization() {
        AutoCompleteApiRequest request = new AutoCompleteApiRequest();
        request.setRecord("all");
        request.setType(AppConstants.AutoCompleteTypes.DOCTOR_SPECIALIZATION);
        return request;
    }
}
