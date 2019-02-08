package uk.ab.baking.helpers.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import uk.ab.baking.entities.Recipe;

public interface RecipeApiEndpoint {

    String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
