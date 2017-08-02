package com.justreliefdoctors.models;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.AppConstants;

/**
 * Created by kartikeya-pc on 6/7/17.
 */

public class DoctorProfileItem {
    private String name;
    private int itemId;
    private boolean isCompleted;

    public DoctorProfileItem(String name, int itemId) {
        this.name = name;
        this.itemId = itemId;

    }

    public static List<DoctorProfileItem> getList() {
        List<DoctorProfileItem> list = new ArrayList<>();
        list.add(new DoctorProfileItem(AppConstants.DoctorProfileConstants.DOCTOR_DOCTOR_DETAILS,AppConstants.DoctorProfileConstants.DOCTOR_ITEM_ID));
        list.add(new DoctorProfileItem(AppConstants.QualificationDetails.QUALIFICATION_DETAILS,AppConstants.QualificationDetails.QUALIFICATION_ITEM_ID));
        list.add(new DoctorProfileItem(AppConstants.RegistrationDetails.REGISTRATION_DETAIL,AppConstants.RegistrationDetails.REGISTRATION_ITEM_ID));
        list.add(new DoctorProfileItem(AppConstants.Clinic.CLINIC_DETAILS,AppConstants.Clinic.CLINIC_ITEM_ID));
        list.add(new DoctorProfileItem(AppConstants.Services.SERVICES_DETAILS,AppConstants.Services.SERVICE_ITEM_ID));
        list.add(new DoctorProfileItem(AppConstants.AwardMembership.AWARD_DETAILS,AppConstants.AwardMembership.AWARD_ITEM_ID));
        return list;
    }
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

}
