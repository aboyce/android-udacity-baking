package uk.ab.baking.viewmodels;

import android.app.Application;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import uk.ab.baking.database.DataRepository;
import uk.ab.baking.entities.Ingredient;
import uk.ab.baking.entities.Recipe;
import uk.ab.baking.entities.Step;

public class RecipesViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    private LiveData<List<Recipe>> mAllRecipes;

    public RecipesViewModel (Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mAllRecipes = mRepository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes;
    }

    public List<Ingredient> getIngredientsForRecipe(@NotNull Integer recipeId) {
        return mRepository.getIngredientsForRecipe(recipeId);
    }

    public List<Step> getStepsForRecipe(@NotNull Integer recipeId) {
        return mRepository.getStepsForRecipe(recipeId);
    }
}
