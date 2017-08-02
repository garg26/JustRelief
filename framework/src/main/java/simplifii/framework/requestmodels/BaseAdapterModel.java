package simplifii.framework.requestmodels;

import java.io.Serializable;

/**
 * Created by my on 28-10-2016.
 */

public class BaseAdapterModel implements Serializable {
    protected int viewType;
    private boolean isSelectedForDelete;

    public boolean isSelectedForDelete() {
        return isSelectedForDelete;
    }

    public void setSelectedForDelete(boolean selectedForDelete) {
        isSelectedForDelete = selectedForDelete;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

}
