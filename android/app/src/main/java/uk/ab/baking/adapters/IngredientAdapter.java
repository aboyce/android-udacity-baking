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
import uk.ab.baking.entities.Ingredient;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredients;

    static class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_recipe_ingredients_tv_ingredient_name)
        TextView ingredientName;

        @BindView(R.id.fragment_recipe_ingredients_tv_ingredient_quantity)
        TextView ingredientQuantity;

        @BindView(R.id.fragment_recipe_ingredients_tv_ingredient_measure)
        TextView ingredientMeasure;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients_item_row, viewGroup, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder viewHolder, int position) {
        Ingredient ingredient = ingredients.get(position);
        viewHolder.ingredientName.setText(ingredient.getName());
        viewHolder.ingredientQuantity.setText(Float.toString(ingredient.getQuantity()));
        viewHolder.ingredientMeasure.setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return (ingredients != null) ? ingredients.size() : 0;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
        Timber.d("The ingredients for the ingredient adapter have been updated.");
    }
}
