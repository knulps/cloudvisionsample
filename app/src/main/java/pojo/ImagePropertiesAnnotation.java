package pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Knulps on 2017-12-04.
 */

public class ImagePropertiesAnnotation {
    @SerializedName("dominantColors")
    @Expose
    private DominantColors dominantColors;

    public DominantColors getDominantColors() {
        return dominantColors;
    }

    public void setDominantColors(DominantColors dominantColors) {
        this.dominantColors = dominantColors;
    }

    public class DominantColors {
        @SerializedName("colors")
        @Expose
        private List<Color> colors = null;

        public List<Color> getColors() {
            return colors;
        }

        public void setColors(List<Color> colors) {
            this.colors = colors;
        }
    }


    public class Color {
        @SerializedName("color")
        @Expose
        private Color_ color;
        @SerializedName("score")
        @Expose
        private Double score;
        @SerializedName("pixelFraction")
        @Expose
        private Double pixelFraction;

        public Color_ getColor() {
            return color;
        }

        public void setColor(Color_ color) {
            this.color = color;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public Double getPixelFraction() {
            return pixelFraction;
        }

        public void setPixelFraction(Double pixelFraction) {
            this.pixelFraction = pixelFraction;
        }
    }

    public class Color_ {

        @SerializedName("red")
        @Expose
        private Integer red;
        @SerializedName("green")
        @Expose
        private Integer green;
        @SerializedName("blue")
        @Expose
        private Integer blue;

        public Integer getRed() {
            return red;
        }

        public void setRed(Integer red) {
            this.red = red;
        }

        public Integer getGreen() {
            return green;
        }

        public void setGreen(Integer green) {
            this.green = green;
        }

        public Integer getBlue() {
            return blue;
        }

        public void setBlue(Integer blue) {
            this.blue = blue;
        }
    }
}