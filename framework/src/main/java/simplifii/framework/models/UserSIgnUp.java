package simplifii.framework.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSIgnUp {

    @SerializedName("UserMobile")
    @Expose
    private String userMobile;
    @SerializedName("UserEmail")
    @Expose
    private String userEmail;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("UserRole")
    @Expose
    private String userRole;
    @SerializedName("ParentID")
    @Expose
    private String parentID;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("pwd")
    @Expose
    private String pwd;
    @SerializedName("type")
    @Expose
    private String type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public UserSIgnUp(String userEmail, String password, String userType) {
        this.uid = userEmail;
        this.pwd = password;
        this.type = userType;
    }

    public UserSIgnUp(String userMobile, String userEmail, String password, String firstName, String lastName, String userType, String userRole, String parentID) {
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.userRole = userRole;
        this.parentID = parentID;

    }
    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


}
