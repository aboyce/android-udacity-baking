package uk.ab.baking.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.fragments.RecipeFragment;
import uk.ab.baking.viewmodels.RecipeViewModel;

public class RecipeActivity extends AppCompatActivity {

    public static final String INTENT_RECIPE_ID = "INTENT_RECIPE_ID";

    private RecipeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // Check the intent and the recipe.
        Intent callingIntent = getIntent();
        if (callingIntent == null || !callingIntent.hasExtra(INTENT_RECIPE_ID)) {
            Timber.e("The calling intent was null or not provided with a recipe id.");
            finish();
            return;
        }
        int recipeId = callingIntent.getIntExtra(INTENT_RECIPE_ID, 0);
        Timber.i("The calling intent was provided and contained recipe id " + recipeId + ".");

        // Initiate the view model.
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        // Update the view model to have the correct recipe.
        viewModel.updateRecipe(recipeId);

        // Initiate the fragment, only if it is not already done.
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_recipe_fl_recipe_container, new RecipeFragment())
                    .commit();
        }
    }
}
