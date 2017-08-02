package simplifii.framework.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kartikeya-pc on 6/9/17.
 */

public class GetDocFileTable {
    @SerializedName("Table")
    @Expose
    private List<GetDocFileItem> table = null;

    public List<GetDocFileItem> getTable() {
        return table;
    }

    public void setTable(List<GetDocFileItem> table) {
        this.table = table;
    }

}
