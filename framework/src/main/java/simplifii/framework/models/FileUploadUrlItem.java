package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/6/17.
 */

public class FileUploadUrlItem {
    @SerializedName("FacilityID")
    @Expose
    private String facilityID;
    @SerializedName("FileType")
    @Expose
    private String fileType;
    @SerializedName("FileDesc")
    @Expose
    private String fileDesc;
    @SerializedName("Extension")
    @Expose
    private String extension;

    public FileUploadUrlItem(String facilityID, String fileType, String fileDesc, String extension) {
        this.facilityID = facilityID;
        this.fileType = fileType;
        this.fileDesc = fileDesc;
        this.extension = extension;
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

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}
