package uk.ab.baking.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import timber.log.Timber;

public class SharedPreferencesHelper {

    private static String KEY_LAST_RECIPE_ID = "KEY_LAST_RECIPE_ID";

    public static int getLastRecipeApiId(Context context) {
        Timber.d("getLastRecipeApiId()");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(KEY_LAST_RECIPE_ID, -1);
    }

    public static void setLastRecipeApiId(Context context, int recipeApiId) {
        Timber.d("setLastRecipeApiId(" + recipeApiId + ")");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_LAST_RECIPE_ID, recipeApiId);
        editor.apply();
    }
}
