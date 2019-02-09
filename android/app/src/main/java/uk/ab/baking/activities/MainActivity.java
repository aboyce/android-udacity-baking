package uk.ab.baking.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import uk.ab.baking.R;
import uk.ab.baking.fragments.RecipesFragment;
import uk.ab.baking.viewmodels.RecipesViewModel;

public class MainActivity extends AppCompatActivity {

    private RecipesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initiate the view model.
        viewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);

        // Initiate the fragment, only if it is not already done.
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main_fl_recipes_container, new RecipesFragment())
                    .commit();
        }
    }
}
