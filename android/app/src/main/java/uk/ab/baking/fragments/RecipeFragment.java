package uk.ab.baking.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

import androidx.lifecycle.ViewModelProviders;
import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.viewmodels.RecipeViewModel;

public class RecipeFragment extends Fragment {

    private RecipeViewModel viewModel;

    public RecipeFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate has been called");
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RecipeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView has been called");

        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        setupViewModelEvents();

        return rootView;
    }

    private void setupViewModelEvents() {
        viewModel.getRecipe().observe(this, recipe -> {
            Timber.d("ViewModel recipe has been updated.");
            Toast.makeText(getContext(), "THE RECIPE IS: " + recipe.getName(), Toast.LENGTH_LONG).show();
        });
    }
}
