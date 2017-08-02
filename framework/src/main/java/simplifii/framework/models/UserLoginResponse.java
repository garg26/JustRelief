package simplifii.framework.models;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import simplifii.framework.models.response.ClinicResponse;
import simplifii.framework.models.response.DoctorResponse;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Preferences;



public class UserLoginResponse extends BaseApi {

    private static UserLoginResponse instance;
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
    private List<Cliniclist> cliniclist = null;
    @SerializedName("doctorlist")
    @Expose
    private List<Doctorlist> doctorlist = null;

    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
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

    public List<Cliniclist> getCliniclist() {
        return cliniclist;
    }

    public void setCliniclist(List<Cliniclist> cliniclist) {
        this.cliniclist = cliniclist;
    }

    public List<Doctorlist> getDoctorlist() {
        return doctorlist;
    }

    public void setDoctorlist(List<Doctorlist> doctorlist) {
        this.doctorlist = doctorlist;
    }

    public boolean isMobileVerified() {
        return "true".equalsIgnoreCase(mobVerified);
    }

    public boolean isEmailVerified() {
        return "true".equalsIgnoreCase(emailVerified);
    }

    public static UserLoginResponse getInstance() {
        String json = Preferences.getData(Preferences.USER_DETAILS, null);
        return parseJson(json);
    }

    public static UserLoginResponse parseJson(String json) {
        UserLoginResponse response = (UserLoginResponse) JsonUtil.parseJson(json, UserLoginResponse.class);
        if (response != null && response.isSuccess()) {
            Preferences.saveData(Preferences.USER_DETAILS, json);

        }
        instance = response;
        return response;
    }

    public void save(){
        String json = new Gson().toJson(this);
        Preferences.saveData(Preferences.USER_DETAILS, json);
    }

}
