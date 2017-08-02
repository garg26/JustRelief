package simplifii.framework.models.response;

import java.io.Serializable;

/**
 * Created by nbansal2211 on 16/05/17.
 */

public class MasterValues implements Serializable{
    private Integer value;
    private String label;

    public int getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
