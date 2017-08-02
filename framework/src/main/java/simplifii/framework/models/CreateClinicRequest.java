package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 5/17/17.
 */

public class CreateClinicRequest {

    @SerializedName("FName")
    @Expose
    private String fName;
    @SerializedName("CityID")
    @Expose
    private String cityID;
    @SerializedName("LocalityID")
    @Expose
    private String localityID;
    @SerializedName("RelationID")
    @Expose
    private String relationID;

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getLocalityID() {
        return localityID;
    }

    public void setLocalityID(String localityID) {
        this.localityID = localityID;
    }

    public String getRelationID() {
        return relationID;
    }

    public void setRelationID(String relationID) {
        this.relationID = relationID;
    }


}
