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

    public List<Ingredient> getIngredientsForRecipeApiId(int recipeApiId) {
        return repository.getIngredientsForRecipeApiId(recipeApiId);
    }

    public List<Step> getStepsForRecipeApiId(int recipeApiId) {
        return repository.getStepsForRecipeApiId(recipeApiId);
    }
}
