package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/9/17.
 */

public class GetDocFileItem {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("DocDesc")
    @Expose
    private String docDesc;
    @SerializedName("DocPath")
    @Expose
    private String docPath;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getDocDesc() {
        return docDesc;
    }

    public void setDocDesc(String docDesc) {
        this.docDesc = docDesc;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

}
