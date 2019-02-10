package uk.ab.baking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

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

    private OnClickListener listener;

    public interface OnClickListener {
        /**
         * Called when a Step has been clicked from the Adapter
         * @param stepId the Id of the Step that has been clicked on.
         */
        void onAdapterStepClick(int stepId);
    }

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

    public StepAdapter(@NotNull StepAdapter.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.fragment_recipe_steps_item_row, viewGroup, false);
        StepViewHolder viewHolder = new StepViewHolder(view);
        view.setOnClickListener(clickedView -> {
            int stepPosition = viewHolder.getAdapterPosition();
            Timber.i("Step at position " + stepPosition + " has been clicked on.");
            listener.onAdapterStepClick(steps.get(stepPosition).getId());
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder viewHolder, int position) {
        Step step = steps.get(position);
        viewHolder.stepShortDescription.setText(step.getShortDescription());
        if (step.getOrder() < 1) {
            viewHolder.stepNumber.setText("");
        } else {
            viewHolder.stepNumber.setText(Integer.toString(step.getOrder()) + ".");
        }
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
