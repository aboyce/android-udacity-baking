package uk.ab.baking.database;

import android.app.Application;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.lifecycle.LiveData;

import androidx.lifecycle.Observer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import uk.ab.baking.database.dao.IngredientDao;
import uk.ab.baking.database.dao.RecipeDao;
import uk.ab.baking.database.dao.StepDao;
import uk.ab.baking.entities.Ingredient;
import uk.ab.baking.entities.Recipe;
import uk.ab.baking.entities.Step;
import uk.ab.baking.helpers.api.RecipeApiEndpoint;
import uk.ab.baking.helpers.api.RecipeApiHelper;

public class DataRepository {

    private static DataRepository sInstance;

    private ApplicationExecutors executors;
    private RecipeApiEndpoint recipeApiEndpoint;
    private RecipeDao recipeDao;
    private IngredientDao ingredientDao;
    private StepDao stepDao;

    private LiveData<List<Recipe>> mAllRecipes;

    public DataRepository(Application application) {
        ApplicationDatabase database = ApplicationDatabase.getInstance(application);
        recipeDao = database.recipeDao();
        ingredientDao = database.ingredientDao();
        stepDao = database.stepDao();
        recipeApiEndpoint = RecipeApiHelper.getApiEndpoint();
        executors = ApplicationExecutors.getInstance();

        mAllRecipes = recipeDao.getRecipes();

        refreshRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes;
    }

    public List<Ingredient> getIngredientsForRecipe(@NotNull Integer recipeId) {
        return ingredientDao.getSynchronousIngredients(recipeId);
    }

    public List<Step> getStepsForRecipe(@NotNull Integer recipeId) {
        return stepDao.getSynchronousSteps(recipeId);
    }

    private void refreshRecipes() {
        Timber.d("Will update the recipes from the recipe API on the network executor.");
        executors.networkIO().execute(() -> {

            // TODO: Check to see if this is required.

            recipeApiEndpoint.getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    Timber.i("Successful response from recipe API endpoint.");
                    executors.diskIO().execute(() -> {
                        List<Recipe> recipes = response.body();
                        if (recipes == null) {
                            Timber.e("The recipes received from the recipe API was null.");
                            return;
                        }
                        recipeDao.insertAll(recipes);
                        recipes.forEach(recipe -> {
                            Timber.d("Updating ingredients and steps for recipe " + recipe.getName() + ".");
                            ingredientDao.insertAll(recipe.getIngredients(), recipe.getApiId());
                            stepDao.insertAll(recipe.getSteps(), recipe.getApiId());
                            Timber.d("%s has " + recipe.getIngredients().size() + " ingredients.", recipe.getName());
                            Timber.d("%s has " + recipe.getSteps().size() + " steps.", recipe.getName());
                        });
                        Timber.i("Updated " + recipes.size() + " recipes, ingredients, and steps into the database.");
                    });
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable error) {
                    Timber.e(error, "Error trying to get recipes from recipe API endpoint.");
                }
            });
        });
    }
}
