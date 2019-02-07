package uk.ab.baking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.entities.Recipe;
import uk.ab.baking.helpers.api.RecipeApiHelper;
import uk.ab.baking.viewmodels.RecipesViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecipesViewModel recipesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        recipesViewModel.getAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                Toast.makeText(getApplicationContext(), "Updates from the database.", Toast.LENGTH_LONG).show();
            }
        });

        Button button = findViewById(R.id.myButton);
        button.setOnClickListener(v -> {
            Timber.d("Clicked the button");
        });
    }
}
