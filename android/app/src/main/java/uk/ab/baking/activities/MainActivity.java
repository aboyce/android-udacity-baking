package uk.ab.baking.activities;

import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.entities.Recipe;
import uk.ab.baking.helpers.api.RecipeApiHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.myButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("Clicked the button");

                List<Recipe> recipes = RecipeApiHelper.getRecipesFromApi();

                Timber.d("Got the recipes?, How many:" + recipes.size());
            }
        });
    }
}
