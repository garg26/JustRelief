package com.justrelief.utility;

import simplifii.framework.models.BaseApi;
import simplifii.framework.models.GetClinicBodyItem;

import com.justrelief.models.SetClinicDetail;
import com.justrelief.models.response.GetAccreditationTableResponse;
import com.justrelief.models.response.GetAwardResponseTable;
import com.justrelief.models.response.GetClinicFileResponse;
import com.justrelief.models.response.GetClinicResponseTable;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.models.GetFileItem;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.JsonUtil;

import static simplifii.framework.utility.BaseApiGenerator.getFiles;




public class ApiGenerator {

    public static HttpParamObject getClinicDetails(GetClinicBodyItem item) {
        HttpParamObject httpParamObject = BaseApiGenerator.getDetails(item);
        httpParamObject.setClassType(GetClinicResponseTable.class);
        return httpParamObject;

    }
    public static HttpParamObject getAwardFile(GetFileItem fileItem) {

        HttpParamObject httpParamObject = getFiles(fileItem);
        httpParamObject.setClassType(GetAwardResponseTable.class);
        return httpParamObject;


    }
    public static HttpParamObject getClinicFile(GetFileItem fileItem) {

        HttpParamObject httpParamObject = getFiles(fileItem);
        httpParamObject.setClassType(GetClinicFileResponse.class);
        return httpParamObject;


    }

    public static HttpParamObject getaccreditationFile(GetFileItem fileItem) {
        HttpParamObject httpParamObject = getFiles(fileItem);
        httpParamObject.setClassType(GetAccreditationTableResponse.class);
        return httpParamObject;
    }

    public static HttpParamObject setDoctorDetails(SetClinicDetail setClinicDetail) {

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setPostMethod();
        httpParamObject.setJson(JsonUtil.toJson(setClinicDetail));
        httpParamObject.setJSONContentType();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SET_CLINIC_FINAL);
        httpParamObject.setClassType(BaseApi.class);

        return httpParamObject;

    }
}
