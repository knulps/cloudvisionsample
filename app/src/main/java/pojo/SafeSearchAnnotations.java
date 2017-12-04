package pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Knulps on 2017-12-04.
 */
public class SafeSearchAnnotations {
    @SerializedName("adult")
    @Expose
    private String adult;
    @SerializedName("spoof")
    @Expose
    private String spoof;
    @SerializedName("medical")
    @Expose
    private String medical;
    @SerializedName("violence")
    @Expose
    private String violence;

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getSpoof() {
        return spoof;
    }

    public void setSpoof(String spoof) {
        this.spoof = spoof;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getViolence() {
        return violence;
    }

    public void setViolence(String violence) {
        this.violence = violence;
    }
}
