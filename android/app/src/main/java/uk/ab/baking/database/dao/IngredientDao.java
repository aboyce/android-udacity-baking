package uk.ab.baking.database.dao;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import timber.log.Timber;
import uk.ab.baking.entities.Ingredient;

@Dao
public abstract class IngredientDao {

    public void insertAll(List<Ingredient> ingredients, @NotNull Integer recipeId) {
        Timber.d("Called insertAll with recipe id " + recipeId + " to add.");
        ingredients.forEach(recipe -> recipe.setRecipeId(recipeId));
        insertAll(ingredients);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(Ingredient ingredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertAll(List<Ingredient> ingredients);

    @Query("SELECT * FROM ingredient WHERE recipe_id =:recipeId")
    public abstract LiveData<List<Ingredient>> getIngredients(Integer recipeId);
}
