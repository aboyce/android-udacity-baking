package uk.ab.baking.helpers.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;
import uk.ab.baking.entities.Recipe;

public class RecipeApiHelper {

    private static RecipeApiEndpoint getApiEndpoint() {

        String baseUrl = RecipeApiEndpoint.BASE_URL;
        Timber.d("Base URL: '" + baseUrl + "'.");

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Timber.d("Built the Retrofit instance from the base URL using Gson Converter.");

        return retrofit.create(RecipeApiEndpoint.class);
    }

    public static List<Recipe> getRecipesFromApi() {

        RecipeApiEndpoint endpoint = getApiEndpoint();

        List<Recipe> recipes = null;

        try {
            recipes = endpoint.getRecipes().execute().body();
            Timber.d("Received the recipes from the API.");
        } catch (IOException error) {
            error.printStackTrace();
            Timber.e(error,"Error trying to get the Recipes from the API");
        }

        return recipes;
    }
}
