package uk.ab.baking.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.viewmodels.RecipeViewModel;

public class StepFragment extends Fragment {

    private RecipeViewModel viewModel;

    @BindView(R.id.fragment_step_instructions_tv_short_description)
    TextView stepShortDescription;

    @BindView(R.id.fragment_step_instructions_tv_description)
    TextView stepDescription;

    public StepFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate has been called");
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RecipeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView has been called");
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);
        setupViewModelEvents();
        return rootView;
    }

    private void setupViewModelEvents() {
        // Update the view when the step changes.
        viewModel.getClickedOnStep().observe(this, step -> {
            Timber.d("ViewModel step has been updated.");
            if (step != null) {
                stepShortDescription.setText(step.getShortDescription());
                stepDescription.setText(step.getDescription());
            } else {
                Timber.w("ViewModel step was null.");
            }
        });
    }
}
