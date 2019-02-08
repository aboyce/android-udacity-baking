package uk.ab.baking.helpers.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RecipeApiHelper {

    public static RecipeApiEndpoint getApiEndpoint() {
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
}
