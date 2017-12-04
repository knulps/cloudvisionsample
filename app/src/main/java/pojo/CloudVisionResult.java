package pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Knulps on 2017-12-04.
 */
public class CloudVisionResult {
    @SerializedName("labelAnnotations")
    @Expose
    private List<LabelAnnotations> labelAnnotations = null;

    @SerializedName("safeSearchAnnotation")
    @Expose
    private SafeSearchAnnotations safeSearchAnnotation;

    @SerializedName("imagePropertiesAnnotation")
    @Expose
    private ImagePropertiesAnnotation imagePropertiesAnnotation;

    @SerializedName("webDetection")
    @Expose
    private WebDetection webDetection;


    @SerializedName("landmarkAnnotations")
    @Expose
    private List<LandmarkAnnotations> landmarkAnnotations = null;

    public List<LabelAnnotations> getLabelAnnotations() {
        return labelAnnotations;
    }

    public void setLabelAnnotations(List<LabelAnnotations> labelAnnotations) {
        this.labelAnnotations = labelAnnotations;
    }

    public SafeSearchAnnotations getSafeSearchAnnotation() {
        return safeSearchAnnotation;
    }

    public void setSafeSearchAnnotation(SafeSearchAnnotations safeSearchAnnotation) {
        this.safeSearchAnnotation = safeSearchAnnotation;
    }

    public ImagePropertiesAnnotation getImagePropertiesAnnotation() {
        return imagePropertiesAnnotation;
    }

    public void setImagePropertiesAnnotation(ImagePropertiesAnnotation imagePropertiesAnnotation) {
        this.imagePropertiesAnnotation = imagePropertiesAnnotation;
    }

    public WebDetection getWebDetection() {
        return webDetection;
    }

    public void setWebDetection(WebDetection webDetection) {
        this.webDetection = webDetection;
    }

    public List<LandmarkAnnotations> getLandmarkAnnotations() {
        return landmarkAnnotations;
    }

    public void setLandmarkAnnotations(List<LandmarkAnnotations> landmarkAnnotations) {
        this.landmarkAnnotations = landmarkAnnotations;
    }
}
