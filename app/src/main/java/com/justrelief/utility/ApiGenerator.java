package com.justrelief.utility;

import simplifii.framework.models.GetClinicBodyItem;

import com.justrelief.models.response.GetAccreditationTableResponse;
import com.justrelief.models.response.GetAwardResponseTable;
import com.justrelief.models.response.GetClinicResponseTable;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.models.GetFileItem;
import simplifii.framework.utility.BaseApiGenerator;

import static simplifii.framework.utility.BaseApiGenerator.getFiles;


/**
 * Created by kartikeya-pc on 6/9/17.
 */

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

    public static HttpParamObject getaccreditationFile(GetFileItem fileItem) {
        HttpParamObject httpParamObject = getFiles(fileItem);
        httpParamObject.setClassType(GetAccreditationTableResponse.class);
        return httpParamObject;
    }
}
