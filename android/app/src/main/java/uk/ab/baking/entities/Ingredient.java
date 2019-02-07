package uk.ab.baking.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient",
        foreignKeys = {
            @ForeignKey(
                entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipe_id",
                onDelete = ForeignKey.CASCADE)
        })
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "recipe_id", index = true)
    private Integer recipeId;

    @Expose
    @SerializedName("ingredient")
    @ColumnInfo(name = "name")
    private String name;

    @Expose
    @SerializedName("quantity")
    @ColumnInfo(name = "quantity")
    private Float quantity;

    @Expose
    @SerializedName("measure")
    @ColumnInfo(name = "measure")
    private String measure;

    public Ingredient(Integer id, Integer recipeId, String name, Float quantity, String measure) {
        this.id = id;
        this.recipeId = recipeId;
        this.name = name;
        this.quantity = quantity;
        this.measure = measure;
    }

    @Ignore
    public Ingredient(Integer recipeId, String name, Float quantity, String measure) {
        this.recipeId = recipeId;
        this.name = name;
        this.quantity = quantity;
        this.measure = measure;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
