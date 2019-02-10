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
import uk.ab.baking.entities.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private List<Step> steps;

    static class StepViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_recipe_steps_tv_step_number)
        TextView stepNumber;

        @BindView(R.id.fragment_recipe_steps_tv_step_description)
        TextView stepShortDescription;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.fragment_recipe_steps_item_row, viewGroup, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder viewHolder, int position) {
        Step step = steps.get(position);
        String stepNumber = String.format("%d.", (step.getOrder() + 1));
        viewHolder.stepNumber.setText(stepNumber);
        viewHolder.stepShortDescription.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return (steps != null) ? steps.size() : 0;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
        Timber.d("The steps for the step adapter have been updated.");
    }
}
