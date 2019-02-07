package uk.ab.baking.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "api_id")
    private Integer apiId;

    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @Expose
    @SerializedName("servings")
    @ColumnInfo(name = "servings")
    private Integer servings;

    @Expose
    @SerializedName("image")
    @ColumnInfo(name = "image")
    private String image;

    @Expose
    @Ignore
    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    @Expose
    @Ignore
    @SerializedName("steps")
    private List<Step> steps;

    public Recipe(Integer id, Integer apiId, String name, Integer servings, String image) {
        this.id = id;
        this.apiId = apiId;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    @Ignore
    public Recipe(Integer id, Integer apiId, String name, Integer servings, String image, List<Ingredient> ingredients, List<Step> steps) {
        this.id = id;
        this.apiId = apiId;
        this.name = name;
        this.servings = servings;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    @Ignore
    public Recipe(Integer apiId, String name, Integer servings, String image) {
        this.apiId = apiId;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
