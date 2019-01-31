package uk.ab.baking.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient",
        foreignKeys = {
            @ForeignKey(
                entity = Recipe.class,
                parentColumns = "apiId",
                childColumns = "recipeId",
                onDelete = ForeignKey.CASCADE)
        })
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    // This is the 'api_id' as this is how they are linked.
    @ColumnInfo(name = "recipe_id")
    private Integer recipeId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "quantity")
    private Integer quantity;

    @ColumnInfo(name = "measure")
    private String measure;

    public Ingredient(Integer recipeId, String name, Integer quantity, String measure) {
        this.recipeId = recipeId;
        this.name = name;
        this.quantity = quantity;
        this.measure = measure;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }
}
