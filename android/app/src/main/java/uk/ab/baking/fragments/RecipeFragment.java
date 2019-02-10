package uk.ab.baking.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.adapters.IngredientAdapter;
import uk.ab.baking.adapters.StepAdapter;
import uk.ab.baking.database.ApplicationExecutors;
import uk.ab.baking.entities.Ingredient;
import uk.ab.baking.entities.Step;
import uk.ab.baking.viewmodels.RecipeViewModel;

public class RecipeFragment extends Fragment implements StepAdapter.OnClickListener {

    public interface StepOnClickListener {
        /**
         * Called when a Step has been clicked on.
         * @param stepId the Id of the Step that has been clicked on.
         */
        void onStepClick(int stepId);
    }

    private StepOnClickListener stepOnClickListener;

    private RecipeViewModel viewModel;

    private IngredientAdapter ingredientAdapter;
    private StepAdapter stepAdapter;

    public RecipeFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate has been called");
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RecipeViewModel.class);
        // Set up the adapter for the ingredients and steps.
        ingredientAdapter = new IngredientAdapter();
        stepAdapter = new StepAdapter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView has been called");
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        setupRecyclerViews(rootView);
        setupViewModelEvents();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Check that the host activity has implemented the callback interface.
        try {
            stepOnClickListener = (StepOnClickListener)context;
        } catch (ClassCastException exception) {
            String message = context.toString() + " must implement StepOnClickListener.";
            Timber.e(exception, message);
            throw new ClassCastException(context.toString() + " must implement StepOnClickListener.");
        }
    }

    private void setupRecyclerViews(View rootView) {
        // Set up the recycler view to display the ingredients.
        RecyclerView ingredientsRecyclerView = rootView.findViewById(R.id.fragment_recipe_rv_ingredients);
        // Optimisation due to the API only returning a set number at this stage.
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientsRecyclerView.setAdapter(ingredientAdapter);
        ingredientsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        // Set up the recycler view to display the steps.
        RecyclerView stepsRecyclerView = rootView.findViewById(R.id.fragment_recipe_rv_steps);
        // Optimisation due to the API only returning a set number at this stage.
        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stepsRecyclerView.setAdapter(stepAdapter);
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
                    Timber.d("Will update the ingredients adapter with " + ingredients.size() + " ingredients for '" + recipe.getName() + "'.");
                    executors.mainThread().execute(() -> {
                        ingredientAdapter.setIngredients(ingredients);
                    });
                }
                Timber.d("Will load the steps for '" + recipe.getName() + "'.");
                List<Step> steps = viewModel.getStepsForRecipeApiId(recipe.getApiId());
                if (steps.stream().findFirst().isPresent()) {
                    Timber.d("Set the first step in the view model.");
                    viewModel.updateStep(steps.stream().findFirst().get().getId());
                }
                if (stepAdapter != null) {
                    Timber.d("Will update the steps adapter with " + steps.size() + " steps for '" + recipe.getName() + "'.");
                    executors.mainThread().execute(() -> {
                        stepAdapter.setSteps(steps);
                    });
                }
            });
        });
    }

    @Override
    public void onAdapterStepClick(int stepId) {
        Timber.i("Step with id " + stepId + " has been clicked on.");
        // Update the view model that a new step has been clicked on.
        viewModel.updateStep(stepId);
        Timber.d("Updated the view model that step id " + stepId + " has been clicked on.");
        // Pass on the callback that the step has been clicked on.
        stepOnClickListener.onStepClick(stepId);
    }
}
