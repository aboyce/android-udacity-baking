package uk.ab.baking.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import uk.ab.baking.repositories.RecipesRepository;
import uk.ab.baking.entities.Ingredient;
import uk.ab.baking.entities.Recipe;
import uk.ab.baking.entities.Step;

public class RecipesViewModel extends AndroidViewModel {

    private final RecipesRepository repository;

    private LiveData<List<Recipe>> allRecipes;

    public RecipesViewModel (Application application) {
        super(application);
        repository = new RecipesRepository(application);
        allRecipes = repository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }

    public List<Ingredient> getIngredientsForRecipe(int recipeId) {
        return repository.getIngredientsForRecipe(recipeId);
    }

    public List<Step> getStepsForRecipe(int recipeId) {
        return repository.getStepsForRecipe(recipeId);
    }
}
