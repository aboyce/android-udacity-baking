package uk.ab.baking.database;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

import uk.ab.baking.database.dao.IngredientDao;
import uk.ab.baking.database.dao.RecipeDao;
import uk.ab.baking.database.dao.StepDao;
import uk.ab.baking.entities.Recipe;

public class DataRepository {

    private static DataRepository sInstance;

    private RecipeDao recipeDao;
    private IngredientDao ingredientDao;
    private StepDao stepDao;

    private LiveData<List<Recipe>> mAllRecipes;

    public DataRepository(Application application) {
        ApplicationDatabase database = ApplicationDatabase.getInstance(application);
        recipeDao = database.recipeDao();
        ingredientDao = database.ingredientDao();
        stepDao = database.stepDao();
        mAllRecipes = recipeDao.getRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes;
    }
}
