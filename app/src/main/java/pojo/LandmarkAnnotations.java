package pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Knulps on 2017-12-04.
 */
public class LandmarkAnnotations {
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("boundingPoly")
    @Expose
    private BoundingPoly boundingPoly;
    @SerializedName("locations")
    @Expose
    private List<Location> locations = null;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(BoundingPoly boundingPoly) {
        this.boundingPoly = boundingPoly;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public class BoundingPoly {

        @SerializedName("vertices")
        @Expose
        private List<Vertex> vertices = null;

        public List<Vertex> getVertices() {
            return vertices;
        }

        public void setVertices(List<Vertex> vertices) {
            this.vertices = vertices;
        }

    }

    public class Location {
        @SerializedName("latLng")
        @Expose
        private LatLng latLng;

        public LatLng getLatLng() {
            return latLng;
        }

        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }
    }

    public class LatLng {

        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }

    public class Vertex {
        @SerializedName("x")
        @Expose
        private Integer x;
        @SerializedName("y")
        @Expose
        private Integer y;

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }
    }
}
