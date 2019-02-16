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
import uk.ab.baking.helpers.SharedPreferencesHelper;

public class RecipeWidgetListViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Timber.d("updateAppWidget() will create list view to display ingredients.");
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private ApplicationDatabase database;
    private List<Ingredient> ingredients = new ArrayList<>();

    public ListRemoteViewsFactory(Context context) {
        Timber.d("ListRemoteViewsFactory constructor");
        this.context = context;
        this.database = ApplicationDatabase.getInstance(context);
    }

    @Override
    public void onCreate() {
        Timber.d("onCreate()");
    }

    @Override
    public void onDataSetChanged() {
        Timber.d("onDataSetChanged()");
        ApplicationExecutors.getInstance().diskIO().execute(() -> {
            int recipeApiId = SharedPreferencesHelper.getLastRecipeApiId(context);
            if (recipeApiId != -1) {
                ingredients = database.ingredientDao().getSynchronousIngredients(recipeApiId);
                Timber.d("onDataSetChanged() the ingredients have been updated.");
            } else {
                Timber.w("onDataSetChanged() the recipe api id has not yet been set.");
            }
        });
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
