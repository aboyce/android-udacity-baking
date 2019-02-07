package uk.ab.baking.helpers.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;
import uk.ab.baking.entities.Recipe;

public class RecipeApiResponse {

    private List<Recipe> recipes;

    public RecipeApiResponse() {
        recipes = new ArrayList<>();
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    public static RecipeApiResponse parseJson(String json) {
        Timber.d("Will parse the JSON: '" + json + "' using gson.");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        RecipeApiResponse response = gson.fromJson(json, RecipeApiResponse.class);
        Timber.d("Parsed the JSON, excluding non-exposed properties.");
        return response;
    }
}
