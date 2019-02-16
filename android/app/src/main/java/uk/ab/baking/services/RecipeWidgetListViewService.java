package uk.ab.baking.services;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.database.ApplicationDatabase;
import uk.ab.baking.database.ApplicationExecutors;
import uk.ab.baking.entities.Ingredient;

public class RecipeWidgetListViewService extends RemoteViewsService {

    public static final String RECIPE_WIDGET_INTENT_RECIPE_ID = "RECIPE_WIDGET_INTENT_RECIPE_ID";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Timber.d("updateAppWidget()");
        int recipeApiId = intent.getIntExtra(RECIPE_WIDGET_INTENT_RECIPE_ID, -1);
        Timber.d("Will create list view with recipe api id " + recipeApiId + ".");
        return new ListRemoteViewsFactory(this.getApplicationContext(), recipeApiId);
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private ApplicationDatabase database;
    private int recipeApiId;
    private List<Ingredient> ingredients = new ArrayList<>();

    public ListRemoteViewsFactory(Context context, int recipeApiId) {
        Timber.d("ListRemoteViewsFactory constructor, recipe api id " + recipeApiId + ".");
        this.context = context;
        this.database = ApplicationDatabase.getInstance(context);
        this.recipeApiId = recipeApiId;
    }

    @Override
    public void onCreate() {
        Timber.d("onCreate()");
    }

    @Override
    public void onDataSetChanged() {
        Timber.d("onDataSetChanged()");
        if (recipeApiId == -1) {
            Timber.w("The recipe api id has not been updated");
        } else {
            ApplicationExecutors.getInstance().diskIO().execute(() -> {
                ingredients = database.ingredientDao().getSynchronousIngredients(recipeApiId);
                Timber.d("onDataSetChanged() the ingredients have been updated.");
                Timber.wtf("THERE ARE " + ingredients.size() + " INGREDIENTS NOW!");
            });
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Timber.d("getViewAt(" + position + ")");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        if (ingredients != null) {
            Ingredient ingredient = ingredients.get(position);
            views.setTextViewText(R.id.recipe_widget_tv_ingredient_name, ingredient.getName());
        }
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        // Treat all items in the ListView the same.
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return ingredients == null ? 0 : ingredients.size();
    }
}
