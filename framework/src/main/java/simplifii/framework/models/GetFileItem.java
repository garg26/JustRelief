package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/3/17.
 */

public class GetFileItem {


    @SerializedName("FacilityID")
    @Expose
    private String facilityID;
    @SerializedName("FileType")
    @Expose
    private String fileType;

    public GetFileItem(String FacilityID, String FileType) {
        this.facilityID = FacilityID;
        this.fileType = FileType;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }





}
