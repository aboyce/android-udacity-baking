package uk.ab.baking.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import uk.ab.baking.entities.Ingredient;

@Dao
public interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ingredient ingredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Ingredient> ingredients);

    @Query("SELECT * FROM ingredient WHERE recipe_id =:recipeId")
    LiveData<List<Ingredient>> getIngredients(Integer recipeId);
}
