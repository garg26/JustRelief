package com.justreliefdoctors.enums;

import android.content.Context;

import com.justreliefdoctors.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import simplifii.framework.utility.AppConstants;


public enum  Qualification {

    MBBS(R.string.mbbs, AppConstants.Qualification.MBBS),
    MDS(R.string.mds,AppConstants.Qualification.MDS),
    MD(R.string.md,AppConstants.Qualification.MD),
    MPHARM(R.string.mpharma,AppConstants.Qualification.MPHARM);

    int nameId, position;

    Qualification(int nameId, int position) {
        this.nameId = nameId;
        this.position = position;
    }

    public static HashMap<Integer,String> getAllQualification(Context context){
        HashMap<Integer,String> list = new HashMap<>();

        for (Qualification qualification :values()){
            list.put(qualification.position,context.getString(qualification.nameId));
        }
        return list;
    }
}
