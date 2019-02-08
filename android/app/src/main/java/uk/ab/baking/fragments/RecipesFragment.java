package uk.ab.baking.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.adapters.RecipeAdapter;
import uk.ab.baking.database.ApplicationExecutors;
import uk.ab.baking.viewmodels.RecipesViewModel;

public class RecipesFragment extends Fragment {

    private RecipesViewModel viewModel;
    private RecipeAdapter recipeAdapter;

    public RecipesFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate has been called");
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RecipesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView has been called");

        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

        RecyclerView recipesRecyclerView = rootView.findViewById(R.id.fragment_recipes_rv_root);
        // Optimisation due to the API only returning a set number at this stage.
        recipesRecyclerView.setHasFixedSize(true);
        // Set up the recycler view for the recipes.
        recipeAdapter = new RecipeAdapter();
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipesRecyclerView.setAdapter(recipeAdapter);

        // Ensure the view model
        setupViewModelEvents();

        return rootView;
    }

    private void setupViewModelEvents() {
        viewModel.getAllRecipes().observe(this, recipes -> {
            Timber.d("ViewModel recipes has been updated.");
            if (recipeAdapter != null) {
                ApplicationExecutors.getInstance().diskIO().execute(() -> {
                    recipes.forEach(recipe -> {
                        recipe.setIngredients(viewModel.getIngredientsForRecipe(recipe.getApiId()));
                        recipe.setSteps(viewModel.getStepsForRecipe(recipe.getApiId()));
                    });
                    ApplicationExecutors.getInstance().mainThread().execute(() -> {
                        recipeAdapter.setRecipes(recipes);
                    });
                });
            }
        });
    }
}
