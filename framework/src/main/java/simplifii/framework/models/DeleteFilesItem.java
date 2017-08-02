package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/3/17.
 */

public class DeleteFilesItem {
    @SerializedName("FacilityID")
    @Expose
    private String facilityID;
    @SerializedName("FileID")
    @Expose
    private String fileID;
    @SerializedName("Type")
    @Expose
    private String type;

    public DeleteFilesItem(String facilityID, String fileID, String type) {

        this.facilityID=facilityID;
        this.fileID=fileID;
        this.type=type;

    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
