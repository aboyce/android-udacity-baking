package uk.ab.baking.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import timber.log.Timber;
import uk.ab.baking.database.dao.IngredientDao;
import uk.ab.baking.database.dao.RecipeDao;
import uk.ab.baking.database.dao.StepDao;
import uk.ab.baking.entities.Ingredient;
import uk.ab.baking.entities.Recipe;
import uk.ab.baking.entities.Step;

@Database(entities = {
        Recipe.class,
        Ingredient.class,
        Step.class
}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "baking_db";

    // The singleton instance.
    private static ApplicationDatabase sInstance;

    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();
    public abstract StepDao stepDao();

    public static ApplicationDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (ApplicationDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ApplicationDatabase.class,
                            DATABASE_NAME).build();
                    Timber.i("Created a new database instance '" + DATABASE_NAME + "'.");
                }
            }
        }
        Timber.d("Returning the database instance '" + DATABASE_NAME + "'.");
        return sInstance;
    }
}
