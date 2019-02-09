package uk.ab.baking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.entities.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_recipes_tv_recipe_name)
        TextView recipeName;

        @BindView(R.id.fragment_recipes_tv_servings)
        TextView recipeServings;

        @BindView(R.id.fragment_recipes_tv_step_count)
        TextView recipeStepCount;

        @BindView(R.id.fragment_recipes_tv_ingredient_count)
        TextView recipeIngredientCount;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.fragment_recipes_item_row, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder viewHolder, int position) {
        Recipe recipe = recipes.get(position);
        viewHolder.recipeName.setText(recipe.getName());
        viewHolder.recipeServings.setText(Integer.toString(recipe.getServings()));
        if (recipe.getSteps() != null) {
            viewHolder.recipeStepCount.setText(Integer.toString(recipe.getSteps().size()));
        } else {
            Timber.w("The steps for " + recipe.getName() + " was null, nothing to display on the card.");
        }
        if (recipe.getIngredients() != null) {
            viewHolder.recipeIngredientCount.setText(Integer.toString(recipe.getIngredients().size()));
        } else {
            Timber.w("The ingredients for " + recipe.getName() + " was null, nothing to display on the card.");
        }
    }

    @Override
    public int getItemCount() {
        return (recipes != null) ? recipes.size() : 0;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
        Timber.d("The recipes for the recipe adapter have been updated.");
    }
}
