package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 5/17/17.
 */

public class CheckPasswordRequest {
    @SerializedName("val")
    @Expose
    private String val;
    @SerializedName("pass")
    @Expose
    private String pass;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("otp")
    @Expose
    private String otp;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
        this.uid = val;
    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
