package uk.ab.baking.database.dao;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import timber.log.Timber;
import uk.ab.baking.entities.Step;

@Dao
public abstract class StepDao {

    public void insertAll(List<Step> steps, @NotNull Integer recipeId) {
        Timber.d("Called insertAll with recipe id " + recipeId + " to add.");
        steps.forEach(step -> step.setRecipeId(recipeId));
        insertAll(steps);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(Step step);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertAll(List<Step> steps);

    @Query("SELECT * FROM step WHERE recipe_id =:recipeId")
    public abstract LiveData<List<Step>> getSteps(int recipeId);

    @Query("SELECT * FROM step WHERE recipe_id =:recipeId")
    public abstract List<Step> getSynchronousSteps(int recipeId);
}
