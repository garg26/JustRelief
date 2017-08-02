package com.justrelief.models;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.AppConstants;

/**
 * Created by nbansal2211 on 12/05/17.
 */

public class ClinicProfileItem {
    private String name;
    private int itemId;
    private boolean isCompleted;

    public ClinicProfileItem(String name, int itemId) {
        this.name = name;
        this.itemId = itemId;
    }

    public static List<ClinicProfileItem> getList() {
        List<ClinicProfileItem> list = new ArrayList<>();
        list.add(new ClinicProfileItem(AppConstants.ClinicProfileConstants.CONTACT_CLINIC_DETAILS, AppConstants.ClinicProfileConstants.CONTACT_CLINIC_ITEM_ID));
        list.add(new ClinicProfileItem(AppConstants.TimingsConstants.TIMINGS_DETAILS, AppConstants.TimingsConstants.TIMINGS_ITEM_ID));
        list.add(new ClinicProfileItem(AppConstants.LocationPhotosConstants.LOCATION_PHOTOS_DETAILS,AppConstants.LocationPhotosConstants.LOCATION_PHOTOS_ITEM_ID));
        list.add(new ClinicProfileItem(AppConstants.DoctorsConstants.DOCTORS_DETAILS,AppConstants.DoctorsConstants.DOCTORS_ITEM_ID));
        list.add(new ClinicProfileItem(AppConstants.ServiceConstants.SPECIALIZATIONS_SERVICE_DETAILS,AppConstants.ServiceConstants.SPECIALIZATIONS_SERVICE_ITEM_ID));
        list.add(new ClinicProfileItem(AppConstants.AwardConstants.AWARD_Accreditation_DETAILS,AppConstants.AwardConstants.AWARD_ACCREDITATION_ITEM_ID));

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
