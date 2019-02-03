package uk.ab.baking.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import uk.ab.baking.database.DataRepository;
import uk.ab.baking.entities.Recipe;

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
}
