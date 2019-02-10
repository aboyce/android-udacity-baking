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

    private int stepId;
    private LiveData<Recipe> recipe;
    private LiveData<Step> clickedOnStep;

    public RecipeViewModel(Application application) {
        super(application);
        repository = new RecipeRepository(application);
    }

    public void updateRecipe(int recipeId) {
        Timber.i("Request to update the recipe with id " + recipeId + ".");
        recipe = repository.getRecipe(recipeId);
    }

    public void updateStep(int stepId) {
        Timber.i("Request to update the step with id " + stepId + ".");
        this.stepId = stepId;
        clickedOnStep = repository.getStep(stepId);
    }

    public LiveData<Recipe> getRecipe() {
        return recipe;
    }

    public LiveData<Step> getClickedOnStep() {
        if (clickedOnStep == null) {
            clickedOnStep = repository.getStep(stepId);
        }
        return clickedOnStep;
    }

    public List<Ingredient> getIngredientsForRecipeApiId(int recipeApiId) {
        return repository.getIngredientsForRecipeApiId(recipeApiId);
    }

    public List<Step> getStepsForRecipeApiId(int recipeApiId) {
        return repository.getStepsForRecipeApiId(recipeApiId);
    }
}
