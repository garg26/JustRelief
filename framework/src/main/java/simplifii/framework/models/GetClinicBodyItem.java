package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/9/17.
 */

public class GetClinicBodyItem {
    @SerializedName("val")
    @Expose
    private String val;

    public GetClinicBodyItem(String facilityID) {
        val = facilityID;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

}
