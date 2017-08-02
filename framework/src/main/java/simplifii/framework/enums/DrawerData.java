package simplifii.framework.enums;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.models.DrawerItem;
import simplifii.framework.utility.AppConstants;

/**
 * Created by INNOCENTBOY on 18/05/17.
 */

public enum DrawerData {


    PAITENTS(AppConstants.PATIENTS.PATIENTS, AppConstants.PATIENTS.PATIENTS_ID),
    ALL_PAITENTS_V1(AppConstants.ALL_PATIENTS.ALL_PATIENTS, AppConstants.ALL_PATIENTS.ALL_PATIENTS_ID),
    DOCTOR_PT(AppConstants.DOCTOR_PT.DOCTOR_PT, AppConstants.DOCTOR_PT.DOCTOR_PT_ID),
    HOSPITAL_CLINICS(AppConstants.HOSPITAL_CLINICS.HOSPITAL_CLINICS, AppConstants.HOSPITAL_CLINICS.HOSPITAL_CLINICS_ID),
    COMMUNICATIONS(AppConstants.COMMUNICATIONS.COMMUNICATIONS, AppConstants.COMMUNICATIONS.COMMUNICATIONS_ID),
    REPORTS(AppConstants.REPORTS.REPORTS, AppConstants.REPORTS.REPORTS_ID),
    FEEDBACK(AppConstants.FEEDBACK.FEEDBACK, AppConstants.FEEDBACK.FEEDBACK_ID);


    String name;
    int nameID;


    DrawerData(String name, int nameId) {
        this.name = name;
        this.nameID = nameId;
    }

    public String getNameId() {
        return name;
    }

    public int getNameID() {
        return nameID;
    }

    public static List<DrawerItem> getAllDrawerItems() {
        List<DrawerItem> drawerItems = new ArrayList<>();
        for (DrawerData drawerData : values()) {
            DrawerItem drawerItem = new DrawerItem();
            drawerItem.setItemName(drawerData.getNameId());
            drawerItem.setItemID(drawerData.getNameID());
            drawerItems.add(drawerItem);
        }
        return drawerItems;
    }

    public static DrawerData getDrawerDataByPosition(int position) {
        for (DrawerData drawerData : values()) {
            if (drawerData.getNameID() == position) {
                return drawerData;
            }
        }
        return null;
    }
}
