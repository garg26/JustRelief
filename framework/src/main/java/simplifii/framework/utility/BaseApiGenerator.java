package simplifii.framework.utility;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import simplifii.framework.asyncmanager.FileParamObject;
import simplifii.framework.models.AutoCompleteApiRequest;
import simplifii.framework.models.AwardResponse;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.CheckOTPRequest;
import simplifii.framework.models.CheckPasswordRequest;
import simplifii.framework.models.ContactClinicResponse;
import simplifii.framework.models.CreateClinicRequest;
import simplifii.framework.models.DeleteFilesItem;
import simplifii.framework.models.FileUploadUrlItem;
import simplifii.framework.models.FindDoctorResponse;
import simplifii.framework.models.GetClinicBodyItem;
import simplifii.framework.models.GetDocFileTable;
import simplifii.framework.models.LocationResponse;
import simplifii.framework.models.MasterListResponse;
import simplifii.framework.models.ServicesSpecializationResponse;
import simplifii.framework.models.GetFileItem;
import simplifii.framework.models.TimingsItem;
import simplifii.framework.models.UserLoginResponse;




import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.models.UserSIgnUp;
import simplifii.framework.models.response.AutoCompleteResponse;
import simplifii.framework.models.TimingsTable;
import simplifii.framework.models.response.UserProfileResponse;


public class BaseApiGenerator {

    public static HttpParamObject loginrequest(UserSIgnUp userSignUp) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.LOGIN);
        httpParamObject.setJSONContentType();
        httpParamObject.setJson(JsonUtil.toJson(userSignUp));
        httpParamObject.setClassType(UserLoginResponse.class);
        httpParamObject.setPostMethod();
        return httpParamObject;
    }

    public static HttpParamObject sendOtpRequest(UserSIgnUp userSignUp) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SIGNUP);
        httpParamObject.setJSONContentType();
        httpParamObject.setJson(JsonUtil.toJson(userSignUp));
        httpParamObject.setPostMethod();
        httpParamObject.setClassType(BaseApi.class);
        return httpParamObject;

    }

    public static HttpParamObject signupRequest(UserSIgnUp userSignUp) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SIGNUP);
        httpParamObject.setJSONContentType();
        httpParamObject.setJson(JsonUtil.toJson(userSignUp));
        httpParamObject.setPostMethod();
        httpParamObject.setClassType(BaseApi.class);

        return httpParamObject;

    }

    public static HttpParamObject resendOtp() {
        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SEND_OTP);
        return httpParamObject;

    }

    public static HttpParamObject findClinic(String name, String cityID, String localityID) {

        CreateClinicRequest request = new CreateClinicRequest();
        request.setFName(name);
        request.setCityID(cityID);
        request.setLocalityID(localityID);
        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.FIND_CLINIC);
        httpParamObject.setJson(JsonUtil.toJson(request));

        return httpParamObject;

    }

    public static HttpParamObject newProfile(String clinic_name, String cityID, String localityID, String relationId) {

        CreateClinicRequest request = new CreateClinicRequest();
        request.setFName(clinic_name);
        request.setCityID(cityID);
        request.setLocalityID(localityID);
        request.setRelationID(relationId);

        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SET_CLINIC);
        httpParamObject.setJson(JsonUtil.toJson(request));

        return httpParamObject;
    }


    public static HttpParamObject checkOTP(String val) {
        CheckOTPRequest request = new CheckOTPRequest();
        request.setVal(val);
        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.CHECK_OTP);
        httpParamObject.setJson(JsonUtil.toJson(request));
        return httpParamObject;

    }

    public static HttpParamObject forgotPassword(String email) {

        CheckPasswordRequest request = new CheckPasswordRequest();
        request.setVal(email);
        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.FORGOT_PASSWORD);
        httpParamObject.setJson(JsonUtil.toJson(request));
        return httpParamObject;

    }

    public static HttpParamObject changePassword(String number, String newPassword, String otp) {

        CheckPasswordRequest request = new CheckPasswordRequest();
        request.setUid(number);
        request.setPass(newPassword);
        request.setOtp(otp);
        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.CHANGE_PASSWORD);
        httpParamObject.setJson(JsonUtil.toJson(request));
        return httpParamObject;

    }

    public static HttpParamObject emailVerify() {

        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.EMAIL_VERIFY);
        return httpParamObject;

    }

    public static HttpParamObject setTiming(TimingsItem timingsItem) {
        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.CLINIC_TIMINGS);
        httpParamObject.setJson(JsonUtil.toJson(timingsItem));
        return httpParamObject;
    }


    public static HttpParamObject getHttpForPostJson() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setClassType(BaseApi.class);
        return httpParamObject;
    }

    public static HttpParamObject getHttpToAutoComplete(AutoCompleteApiRequest req) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.AUTO_COMPLETE);
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(req));
        httpParamObject.setClassType(AutoCompleteResponse.class);
        return httpParamObject;
    }

    public static HttpParamObject resendEmail() {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setPostMethod();
        httpParamObject.setUrl(AppConstants.PAGE_URL.RESEND_EMAIL);
        return httpParamObject;

    }


    public static HttpParamObject findDoctor(FindDoctorResponse findDoctorResponse) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setClassType(BaseApi.class);
        httpParamObject.setJson(JsonUtil.toJson(findDoctorResponse));
        httpParamObject.setUrl(AppConstants.PAGE_URL.FIND_DOCTOR);

        return httpParamObject;

    }

    public static HttpParamObject newDoctor(FindDoctorResponse findDoctorResponse) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setClassType(BaseApi.class);
        httpParamObject.setJson(JsonUtil.toJson(findDoctorResponse));
        httpParamObject.setUrl(AppConstants.PAGE_URL.SET_DOCTOR);

        return httpParamObject;

    }


    public static HttpParamObject findSpecialization(AutoCompleteApiRequest allSpecializationRequest) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.AUTO_COMPLETE);
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(allSpecializationRequest));
        httpParamObject.setClassType(AutoCompleteResponse.class);
        return httpParamObject;

    }

    public static HttpParamObject setLocation(LocationResponse locationResponse) {

        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.LOCATION);
        httpParamObject.setJson(JsonUtil.toJson(locationResponse));
        return httpParamObject;
    }

    public static HttpParamObject addServices(ServicesSpecializationResponse servicesSpecializationResponse) {
        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SERVICE);
        httpParamObject.setJson(JsonUtil.toJson(servicesSpecializationResponse));
        return httpParamObject;

    }

    public static HttpParamObject addAward(AwardResponse awardResponse) {
        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.AWARD);
        httpParamObject.setJson(JsonUtil.toJson(awardResponse));
        return httpParamObject;

    }

    public static HttpParamObject addaccreditation(AwardResponse awardResponse) {
        HttpParamObject httpParamObject = getHttpForPostJson();
        httpParamObject.setUrl(AppConstants.PAGE_URL.AWARD);
        httpParamObject.setJson(JsonUtil.toJson(awardResponse));
        return httpParamObject;
    }

    public static FileParamObject uploadImage(File file, String name, ContactClinicResponse clinicResponse) {
        FileParamObject fileParamObject = new FileParamObject(file,name,"FacilityImg");
        String json = JsonUtil.toJson(clinicResponse);
        String encode = null;
        try {
            encode = URLEncoder.encode(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        fileParamObject.setUrl(AppConstants.PAGE_URL.UPLOAD_IMAGE+"?data1="+encode);
        fileParamObject.setContentType("");
        fileParamObject.setPostMethod();
        fileParamObject.setClassType(BaseApi.class);
        return fileParamObject;
    }

    public static HttpParamObject getMasterList() {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.MASTER_LIST);
        httpParamObject.setContentType("");
        httpParamObject.setPostMethod();
        httpParamObject.setClassType(MasterListResponse.class);
        return httpParamObject;
    }

    public static HttpParamObject getImageFile(GetFileItem getFileItem){
        HttpParamObject httpParamObject = getFiles(getFileItem);
        httpParamObject.setClassType(GetDocFileTable.class);
        return httpParamObject;

    }

    public static HttpParamObject deleteTimeEntry(DeleteFilesItem filesItem) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(filesItem));
        httpParamObject.setUrl(AppConstants.PAGE_URL.DELETE_FILES);
        httpParamObject.setClassType(BaseApi.class);

        return httpParamObject;

    }

    public static HttpParamObject getUserProfileData() {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setUrl(AppConstants.PAGE_URL.USER_PROFILE);
        httpParamObject.setClassType(UserProfileResponse.class);

        return httpParamObject;

    }
    public static HttpParamObject getDetails(GetClinicBodyItem item) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();

        httpParamObject.setUrl(AppConstants.PAGE_URL.GET_CLINIC_DETAIL);
        httpParamObject.setJson(JsonUtil.toJson(item));
        return httpParamObject;

    }

    public static FileParamObject uploadDocument(FileUploadUrlItem urlItem, File file) {

        FileParamObject fileParamObject = new FileParamObject(file,"FacilityImg");
        String json = JsonUtil.toJson(urlItem);
        String encode = null;
        try {
            encode = URLEncoder.encode(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        fileParamObject.setUrl(AppConstants.PAGE_URL.UPLOAD_DOCUMENT+"?data1="+encode);
        fileParamObject.setContentType("");
        fileParamObject.setPostMethod();
        fileParamObject.setClassType(BaseApi.class);
        return fileParamObject;

    }

    public static HttpParamObject getTimeFiles(GetFileItem getFileItem) {

       HttpParamObject httpParamObject = getImageFile(getFileItem);
        httpParamObject.setClassType(TimingsTable.class);
        return httpParamObject;

    }
   public static HttpParamObject getFiles(GetFileItem getFileItem){
       HttpParamObject httpParamObject = new HttpParamObject();
       String json = JsonUtil.toJson(getFileItem);

       String encode = null;
       try {
           encode = URLEncoder.encode(json, "utf-8");
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }
       httpParamObject.setUrl(AppConstants.PAGE_URL.GET_FILES+"?data1="+encode);
       httpParamObject.setContentType("");
       httpParamObject.setPostMethod();

       return httpParamObject;
    }



}
