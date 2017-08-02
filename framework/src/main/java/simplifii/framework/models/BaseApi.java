package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kartikeya-pc on 5/16/17.
 */

public class BaseApi implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return "success".equalsIgnoreCase(message) || "true".equalsIgnoreCase(message);
    }
    public boolean isFail() {
        return "error".equalsIgnoreCase(message) || "false".equalsIgnoreCase(message);
    }
    public boolean isUserAlreadyExist() {
        return "User Already Exists".equalsIgnoreCase(message) || "false".equalsIgnoreCase(message);
    }

}

