package uk.ab.baking.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "step",
        foreignKeys = {
            @ForeignKey(
                entity = Recipe.class,
                parentColumns = "apiId",
                childColumns = "recipeId",
                onDelete = ForeignKey.CASCADE)
        })
public class Step {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    // This is the 'api_id' as this is how they are linked.
    @ColumnInfo(name = "recipe_id")
    private Integer recipeId;

    @ColumnInfo(name = "api_id")
    private Integer apiId;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "description_short")
    private String shortDescription;

    @ColumnInfo(name = "url_video")
    private String videoUrl;

    @ColumnInfo(name = "url_thumbnail")
    private String thumbnailUrl;

    public Step(Integer recipeId, Integer apiId, String description, String shortDescription, String videoUrl, String thumbnailUrl) {
        this.recipeId = recipeId;
        this.apiId = apiId;
        this.description = description;
        this.shortDescription = shortDescription;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public Integer getApiId() {
        return apiId;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
