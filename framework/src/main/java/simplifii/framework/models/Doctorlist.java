package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 5/23/17.
 */

public class Doctorlist {
    @SerializedName("DoctorID")
    @Expose
    private Integer doctorID;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("DoctorImage")
    @Expose
    private Object doctorImage;

    public Integer getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Integer doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(Object doctorImage) {
        this.doctorImage = doctorImage;
    }

}
