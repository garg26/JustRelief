package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;


/**
 * Created by kartikeya-pc on 5/20/17.
 */

public class TimingsItem {
    @SerializedName("FacilityID")
    @Expose
    private String facilityID;
    @SerializedName("WeekDay")
    @Expose
    private String weekDay;
    @SerializedName("StartTime")
    @Expose
    private String startTime;
    @SerializedName("EndTime")
    @Expose
    private String endTime;
    @SerializedName("StartTime2")
    @Expose
    private String startTime2;
    @SerializedName("EndTime2")
    @Expose
    private String endTime2;

    public TimingsItem(String facilityID, List<String> list_selected_days, String startTime, String endTime, String startTime2, String endTime2) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startTime2 = startTime2;
        this.endTime2 = endTime2;
        this.facilityID = facilityID;

        weekDay = getString(list_selected_days.toArray(new String[list_selected_days.size()]));


    }

    public TimingsItem(String facilityID, String[] stringArray, String startTime, String endTime, String startTime2, String endTime2) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startTime2 = startTime2;
        this.endTime2 = endTime2;
        this.facilityID = facilityID;
        weekDay = getString(stringArray);
    }


    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String[] weekDay) {
        this.weekDay =  getString(weekDay);
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }

    public String getString(String[] strings){
        StringBuilder stringBuilder = new StringBuilder();
        if (strings!=null) {

            for (int i = 0; i < strings.length; i++) {
                stringBuilder.append(strings[i] + ",");
            }
            if (stringBuilder.length()>0){
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
        }
        return stringBuilder.toString();
    }

}
