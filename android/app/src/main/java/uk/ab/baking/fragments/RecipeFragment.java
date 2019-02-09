package uk.ab.baking.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.adapters.IngredientAdapter;
import uk.ab.baking.database.ApplicationExecutors;
import uk.ab.baking.entities.Ingredient;
import uk.ab.baking.viewmodels.RecipeViewModel;

public class RecipeFragment extends Fragment {

    private RecipeViewModel viewModel;

    private IngredientAdapter ingredientAdapter;

    public RecipeFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate has been called");
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RecipeViewModel.class);
        // Set up the adapter for the ingredients.
        ingredientAdapter = new IngredientAdapter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView has been called");

        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        // Set up the recycler view to display the ingredients.
        RecyclerView ingredientsRecyclerView = rootView.findViewById(R.id.fragment_recipe_rv_ingredients);
        // Optimisation due to the API only returning a set number at this stage.
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientsRecyclerView.setAdapter(ingredientAdapter);

        setupViewModelEvents();

        return rootView;
    }

    private void setupViewModelEvents() {
        ApplicationExecutors executors = ApplicationExecutors.getInstance();
        viewModel.getRecipe().observe(this, recipe -> {
            Timber.d("ViewModel recipe has been updated.");
            // When the recipe has been updated, update the adapters with a the ingredients.
            executors.diskIO().execute(() -> {
                Timber.d("Will load the ingredients for '" + recipe.getName() + "'.");
                List<Ingredient> ingredients = viewModel.getIngredientsForRecipeApiId(recipe.getApiId());
                if (ingredientAdapter != null) {
                    Timber.d("Will update the adapter with " + ingredients.size() + " ingredients for '" + recipe.getName() + "'.");
                    executors.mainThread().execute(() -> {
                        ingredientAdapter.setIngredients(ingredients);
                    });
                }
            });
        });
    }
}
