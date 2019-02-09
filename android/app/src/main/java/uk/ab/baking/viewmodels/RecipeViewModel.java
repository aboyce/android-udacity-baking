package uk.ab.baking.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import timber.log.Timber;
import uk.ab.baking.entities.Ingredient;
import uk.ab.baking.entities.Recipe;
import uk.ab.baking.entities.Step;
import uk.ab.baking.repositories.RecipeRepository;

public class RecipeViewModel extends AndroidViewModel {

    private final RecipeRepository repository;

    private LiveData<Recipe> recipe;

    public RecipeViewModel(Application application) {
        super(application);
        repository = new RecipeRepository(application);
    }

    public void updateRecipe(int recipeId) {
        Timber.i("Request to update the recipe with id " + recipeId + ".");
        recipe = repository.getRecipe(recipeId);
    }

    public LiveData<Recipe> getRecipe() {
        return recipe;
    }

    public List<Ingredient> getIngredientsForRecipeApiId(int recipeApiId) {
        return repository.getIngredientsForRecipeApiId(recipeApiId);
    }

    public List<Step> getStepsForRecipeApiId(int recipeApiId) {
        return repository.getStepsForRecipeApiId(recipeApiId);
    }
}
