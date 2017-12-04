package pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Knulps on 2017-12-04.
 */
public class WebDetection {

    @SerializedName("webEntities")
    @Expose
    private List<WebEntity> webEntities = null;

    public List<WebEntity> getWebEntities() {
        return webEntities;
    }

    public void setWebEntities(List<WebEntity> webEntities) {
        this.webEntities = webEntities;
    }

    public class WebEntity {
        @SerializedName("entityId")
        @Expose
        private String entityId;
        @SerializedName("score")
        @Expose
        private Double score;
        @SerializedName("description")
        @Expose
        private String description;

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
