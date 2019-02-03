package uk.ab.baking.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import uk.ab.baking.entities.Step;

@Dao
public interface StepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Step step);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Step> steps);

    @Query("SELECT * FROM step WHERE recipe_id =:recipeId")
    LiveData<List<Step>> getSteps(Integer recipeId);
}
