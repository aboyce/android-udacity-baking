package uk.ab.baking.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "api_id")
    private Integer apiId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "servings")
    private Integer servings;

    @ColumnInfo(name = "image")
    private String image;

    public Recipe(Integer apiId, String name, Integer servings, String image) {
        this.apiId = apiId;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public Integer getApiId() {
        return apiId;
    }

    public String getName() {
        return name;
    }

    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
