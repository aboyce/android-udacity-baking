package uk.ab.baking.repositories;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import uk.ab.baking.entities.Recipe;
import uk.ab.baking.helpers.api.RecipeApiEndpoint;
import uk.ab.baking.helpers.api.RecipeApiHelper;

public class RecipeRepository extends BaseRepository {

    private LiveData<List<Recipe>> allRecipes;

    public RecipeRepository(Application application) {
        super(application);
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }
}
