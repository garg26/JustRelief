package simplifii.framework.requestmodels;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

import simplifii.framework.utility.AppConstants;

/**
 * Created by my on 25-10-2016.
 */

public class SelectContent extends BaseAdapterModel implements Serializable {
    File file;
    String fileType;
    String filePath;
    String desc;
    String views;
    @SerializedName("contentUrl")
    private String uri;
    private String mimetype;
    private boolean isFeedContent;
    private boolean isSingleItem;


    public boolean isSingleItem() {
        return isSingleItem;
    }

    public void setSingleItem(boolean singleItem) {
        isSingleItem = singleItem;
    }

    public boolean isFeedContent() {
        return isFeedContent;
    }

    public void setFeedContent(boolean feedContent) {
        isFeedContent = feedContent;
    }

    private boolean isDeleteRequired;

    public boolean isDeleteRequired() {
        return isDeleteRequired;
    }

    public void setDeleteRequired(boolean deleteRequired) {
        isDeleteRequired = deleteRequired;
    }

    public String getUri() {
        return uri;
    }

    public String getFullUrl() {
        return AppConstants.PAGE_URL.PHOTO_URL + uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFileType() {

        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    private static SelectContent runningInstance;

    public static SelectContent getRunningInstance() {
        return runningInstance;
    }

    public static void setRunningInstance(SelectContent runningInstance) {
        SelectContent.runningInstance = runningInstance;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public int getViewType() {
        if (!TextUtils.isEmpty(uri)) {
            return AppConstants.VIEW_TYPE.FEED_CONTENT;
        }
        if (TextUtils.isEmpty(getFileType())) {
            return viewType;
        }
        switch (getFileType()) {
            case AppConstants.FILE_TYPES.IMAGE:
                return AppConstants.VIEW_TYPE.GET_IMAGE;
            case AppConstants.FILE_TYPES.VIDEO:
                return AppConstants.VIEW_TYPE.GET_VIDEO;
            case AppConstants.FILE_TYPES.AUDIO:
                if (this.isFeedContent()) {
                    return AppConstants.VIEW_TYPE.FEED_AUDIO;
                }
                return AppConstants.VIEW_TYPE.GET_AUDIO;
            case AppConstants.FILE_TYPES.PDF:
                return AppConstants.VIEW_TYPE.PDF_FILE;
        }
        return viewType;
    }

}
