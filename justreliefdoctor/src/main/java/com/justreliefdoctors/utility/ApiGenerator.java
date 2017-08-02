package com.justreliefdoctors.utility;

import com.justreliefdoctors.models.ContactDoctorItem;
import com.justreliefdoctors.models.DeleteDocFileItem;
import com.justreliefdoctors.models.GetDoctorFile;
import com.justreliefdoctors.models.SetAwardItem;
import com.justreliefdoctors.models.SetCouncilItem;
import com.justreliefdoctors.models.SetDocSpecializationItem;
import com.justreliefdoctors.models.SetDoctorDetail;
import com.justreliefdoctors.models.SetDoctorDetailsItem;
import com.justreliefdoctors.models.SetDoctorDocument;
import com.justreliefdoctors.models.SetDoctorExperienceItem;
import com.justreliefdoctors.models.SetMembershipItem;
import com.justreliefdoctors.models.SetServiceItem;
import com.justreliefdoctors.models.response.GetAllDocTableResponse;
import com.justreliefdoctors.models.response.GetDoctorDetailsTable;
import com.justreliefdoctors.models.response.GetDoctorAwardTableResponse;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import simplifii.framework.asyncmanager.FileParamObject;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.models.AutoCompleteApiRequest;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.GetClinicBodyItem;
import simplifii.framework.models.response.AutoCompleteResponse;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.JsonUtil;

/**
 * Created by kartikeya-pc on 6/12/17.
 */

public class ApiGenerator {


    public static HttpParamObject getDoctorDetails(GetClinicBodyItem item) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        httpParamObject.setUrl(AppConstants.PAGE_URL.GET_DOCTOR_DETAIL);
        httpParamObject.setJson(JsonUtil.toJson(item));
        httpParamObject.setClassType(GetDoctorDetailsTable.class);
        return httpParamObject;
    }

    public static FileParamObject getDoctorContactDetails(File file, SetDoctorDetailsItem detailsItem) {

        String encode = convertUrlIntoEncode(detailsItem);
        FileParamObject fileParamObject = new FileParamObject(file,"FacilityImg");
        fileParamObject.setUrl(AppConstants.PAGE_URL.GET_FILES+"?data1="+encode);
        fileParamObject.setContentType("");
        fileParamObject.setPostMethod();
        fileParamObject.setClassType(BaseApi.class);
        return fileParamObject;
    }

    public static HttpParamObject getCollegeList(AutoCompleteApiRequest allCollegeRequest) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.AUTO_COMPLETE);
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(allCollegeRequest));
        httpParamObject.setClassType(AutoCompleteResponse.class);
        return httpParamObject;

    }

    public static HttpParamObject setQualification(ContactDoctorItem doctorItem) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.DOCTOR_EDUCATION);
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(doctorItem));
        httpParamObject.setClassType(BaseApi.class);

        return httpParamObject;

    }


    public static HttpParamObject setMembership(SetMembershipItem membershipItem) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.MEMBERSHIP);
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(membershipItem));
        httpParamObject.setClassType(BaseApi.class);

        return httpParamObject;
    }

    public static HttpParamObject setAward(SetAwardItem awardItem) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SET_DOCTOR_AWARD);
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(awardItem));
        httpParamObject.setClassType(BaseApi.class);

        return httpParamObject;
    }

    public static HttpParamObject getDoctorAward(GetDoctorFile doctorFile) {

        HttpParamObject httpParamObject = new HttpParamObject();

        String json = JsonUtil.toJson(doctorFile);

        String encode = null;
        try {
            encode = URLEncoder.encode(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpParamObject.setUrl(AppConstants.PAGE_URL.GET_DOC_FILES+"?data1="+encode);
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setClassType(GetDoctorAwardTableResponse.class);

        return httpParamObject;
    }

    public static HttpParamObject deleteDoctorEntry(DeleteDocFileItem filesItem) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(filesItem));
        httpParamObject.setJSONContentType();
        httpParamObject.setClassType(BaseApi.class);
        httpParamObject.setUrl(AppConstants.PAGE_URL.DELETE_DOC_FILE);

        return httpParamObject;
    }



    public static HttpParamObject setDoctorServices(SetServiceItem serviceItem) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(serviceItem));
        httpParamObject.setUrl(AppConstants.PAGE_URL.DOCTOR_SERVICES);
        httpParamObject.setClassType(BaseApi.class);

        return httpParamObject;

    }

    public static HttpParamObject setExperienceItem(SetDoctorExperienceItem experienceItem) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(experienceItem));
        httpParamObject.setUrl(AppConstants.PAGE_URL.SET_DOCTOR_EXPERIENCE);
        httpParamObject.setClassType(BaseApi.class);

        return httpParamObject;
    }

    public static HttpParamObject getDoctorFile(GetDoctorFile doctorFile) {

        String json = JsonUtil.toJson(doctorFile);

        String encode = null;
        try {
            encode = URLEncoder.encode(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setPostMethod();
        httpParamObject.setContentType("");
        httpParamObject.setUrl(AppConstants.PAGE_URL.GET_DOC_FILES+"?data1="+encode);
        httpParamObject.setClassType(GetAllDocTableResponse.class);

        return httpParamObject;
    }

    public static FileParamObject setDoctorDocument(SetDoctorDocument doctorDocument, File file) {
        String url = convertUrlIntoEncode(doctorDocument);

        FileParamObject fileParamObject = new FileParamObject(file,"FacilityImg");
        fileParamObject.setUrl(AppConstants.PAGE_URL.UPLOAD_DOCTOR_DOCUMENT+"?Data1="+url);
        fileParamObject.setContentType("");
        fileParamObject.setPostMethod();
        fileParamObject.setClassType(BaseApi.class);

        return fileParamObject;
    }

    private static String convertUrlIntoEncode(Object doctorFile){

        String json = JsonUtil.toJson(doctorFile);

        String encode = null;
        try {
            encode = URLEncoder.encode(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

    public static HttpParamObject setCouncil(SetCouncilItem councilItem) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setJSONContentType();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(councilItem));
        httpParamObject.setUrl(AppConstants.PAGE_URL.SET_DOCTOR_REGISTRATION);
        httpParamObject.setClassType(BaseApi.class);

        return httpParamObject;
    }


    public static HttpParamObject SetDocSpecialization(SetDocSpecializationItem specializationItem) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setJSONContentType();
        httpParamObject.setJson(JsonUtil.toJson(specializationItem));
        httpParamObject.setPostMethod();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SET_DOCTOR_SPEC);
        httpParamObject.setClassType(BaseApi.class);

        return httpParamObject;
    }

    public static FileParamObject setDoctorContactDetails(File file, SetDoctorDetailsItem detailsItem) {
        String encode = convertUrlIntoEncode(detailsItem);
        FileParamObject fileParamObject = new FileParamObject(file,"FacilityImg");
        fileParamObject.setUrl(AppConstants.PAGE_URL.SET_DOCTOR_CONTACT+"?Data1="+encode);
        fileParamObject.setContentType("");
        fileParamObject.setPostMethod();
        fileParamObject.setClassType(BaseApi.class);
        return fileParamObject;
    }

    public static HttpParamObject setDoctorDetails(SetDoctorDetail setDoctorDetail) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(setDoctorDetail));
        httpParamObject.setJSONContentType();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SET_DOCTOR_DETAIL);
        httpParamObject.setClassType(BaseApi.class);

        return httpParamObject;

    }
}
