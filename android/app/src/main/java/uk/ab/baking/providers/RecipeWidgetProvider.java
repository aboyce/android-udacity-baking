package uk.ab.baking.providers;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.services.RecipeWidgetListViewService;

public class RecipeWidgetProvider extends AppWidgetProvider {

    private int recipeApiId;

    public static final String INTENT_EXTRA_RECIPE_API_ID = "INTENT_EXTRA_RECIPE_API_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("onReceive()");
        recipeApiId = intent.getIntExtra(INTENT_EXTRA_RECIPE_API_ID, -1);
        Timber.d("Recipe api id " + recipeApiId + " has been provided via the intent.");
        String action = intent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            Timber.d("An app widget update as been requested.");
            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, RecipeWidgetProvider.class);
            int[] widgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(componentName);
            widgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.recipe_widget_list_view_lv_ingredients);
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Timber.d("onUpdate()");
        for (int widgetId : appWidgetIds) {
            Timber.d("Updating app widget with id " + widgetId + ".");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_list_view);
            // Set the RecipeWidgetListViewService to act as the adapter for the ListView.
            Intent intent = new Intent(context, RecipeWidgetListViewService.class);
            intent.putExtra(RecipeWidgetListViewService.RECIPE_WIDGET_INTENT_RECIPE_ID, recipeApiId);
            views.setRemoteAdapter(R.id.recipe_widget_list_view_lv_ingredients, intent);
            views.setEmptyView(R.id.recipe_widget_list_view_lv_ingredients, R.id.recipe_widget_list_view_tv_empty);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        Timber.d("onEnabled()");
    }

    @Override
    public void onDisabled(Context context) {
        Timber.d("onDisabled()");
    }
}

