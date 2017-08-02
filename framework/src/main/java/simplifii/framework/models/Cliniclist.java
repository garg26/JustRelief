package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cliniclist {

@SerializedName("FacilityID")
@Expose
private Integer facilityID;
@SerializedName("FacilityName")
@Expose
private String facilityName;
@SerializedName("FacilityDate")
@Expose
private String facilityDate;
@SerializedName("FacilityImage")
@Expose
private Object facilityImage;

public Integer getFacilityID() {
return facilityID;
}

public void setFacilityID(Integer facilityID) {
this.facilityID = facilityID;
}

public String getFacilityName() {
return facilityName;
}

public void setFacilityName(String facilityName) {
this.facilityName = facilityName;
}

public String getFacilityDate() {
return facilityDate;
}

public void setFacilityDate(String facilityDate) {
this.facilityDate = facilityDate;
}

public Object getFacilityImage() {
return facilityImage;
}

public void setFacilityImage(Object facilityImage) {
this.facilityImage = facilityImage;
}

}