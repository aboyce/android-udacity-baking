package uk.ab.baking.repositories;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import uk.ab.baking.database.ApplicationDatabase;
import uk.ab.baking.database.ApplicationExecutors;
import uk.ab.baking.database.dao.IngredientDao;
import uk.ab.baking.database.dao.RecipeDao;
import uk.ab.baking.database.dao.StepDao;
import uk.ab.baking.entities.Ingredient;
import uk.ab.baking.entities.Recipe;
import uk.ab.baking.entities.Step;

public abstract class BaseRepository {

    protected ApplicationExecutors executors;

    protected RecipeDao recipeDao;
    protected IngredientDao ingredientDao;
    protected StepDao stepDao;

    BaseRepository(Application application) {
        ApplicationDatabase database = ApplicationDatabase.getInstance(application);
        recipeDao = database.recipeDao();
        ingredientDao = database.ingredientDao();
        stepDao = database.stepDao();
        executors = ApplicationExecutors.getInstance();
    }

    public LiveData<Recipe> getRecipe(int recipeId) {
        return recipeDao.getRecipeFromId(recipeId);
    }

    public List<Ingredient> getIngredientsForRecipe(int recipeId) {
        return ingredientDao.getSynchronousIngredients(recipeId);
    }

    public List<Step> getStepsForRecipe(int recipeId) {
        return stepDao.getSynchronousSteps(recipeId);
    }
}
