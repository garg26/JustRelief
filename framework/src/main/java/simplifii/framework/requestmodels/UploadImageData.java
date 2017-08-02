package simplifii.framework.requestmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saurabh on 23-09-2016.
 */
public class UploadImageData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("originalName")
    @Expose
    private String originalName;
    @SerializedName("encoding")
    @Expose
    private String encoding;
    @SerializedName("mimetype")
    @Expose
    private String mimetype;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("uri")
    @Expose
    private String uri;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
