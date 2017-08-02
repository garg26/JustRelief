package simplifii.framework.requestmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UploadImageResponse {

    @SerializedName("data")
    @Expose
    private UploadImageData data;

//    public Boolean getStatus() {
//        return status;
//    }
//
//    public void setStatus(Boolean status) {
//        this.status = status;
//    }

    public UploadImageData getData() {
        return data;
    }

    public void setData(UploadImageData data) {
        this.data = data;
    }

}