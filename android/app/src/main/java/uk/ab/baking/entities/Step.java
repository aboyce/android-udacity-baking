package uk.ab.baking.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "step",
        foreignKeys = {
            @ForeignKey(
                entity = Recipe.class,
                parentColumns = "api_id",
                childColumns = "recipe_id",
                onDelete = ForeignKey.CASCADE)
        })
public class Step {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "recipe_id", index = true)
    private Integer recipeId;

    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "order")
    // Use the Id as an order for sorting.
    private Integer order;

    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "description")
    private String description;

    @Expose
    @SerializedName("shortDescription")
    @ColumnInfo(name = "description_short")
    private String shortDescription;

    @Expose
    @SerializedName("videoURL")
    @ColumnInfo(name = "url_video")
    private String videoUrl;

    @Expose
    @SerializedName("thumbnailURL")
    @ColumnInfo(name = "url_thumbnail")
    private String thumbnailUrl;

    public Step(Integer id, Integer recipeId, Integer order, String description, String shortDescription, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.recipeId = recipeId;
        this.order = order;
        this.description = description;
        this.shortDescription = shortDescription;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    @Ignore
    public Step(Integer recipeId, Integer order, String description, String shortDescription, String videoUrl, String thumbnailUrl) {
        this.recipeId = recipeId;
        this.order = order;
        this.description = description;
        this.shortDescription = shortDescription;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
