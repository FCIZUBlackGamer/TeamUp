package teamup.rivile.com.teamup.ui.Project.Add.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.network.APIModels.StateModel;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.CapitalViewHolder> {
    private List<StateModel> mCategoryModels;

    private StateModel mSelectedCategoryModels;

    private CheckBox mLastCheckedCheckBox;

    public CategoriesRecyclerViewAdapter(List<StateModel> stateModels,
                                         @NonNull StateModel selectedStateModels) {
        mCategoryModels = stateModels;
        mSelectedCategoryModels = selectedStateModels;
    }

    @NonNull
    @Override
    public CapitalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CapitalViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.capital_list_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final CapitalViewHolder holder, int i) {
        final StateModel model = mCategoryModels.get(i);
        holder.checkBox.setText(model.getName());

        holder.checkBox.setOnCheckedChangeListener(null);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mSelectedCategoryModels = model;
                holder.checkBox.setChecked(true);

                if (mLastCheckedCheckBox != null)
                    mLastCheckedCheckBox.setChecked(false);
                mLastCheckedCheckBox = holder.checkBox;
            } else {
                if (mSelectedCategoryModels.equals(model))
                    mSelectedCategoryModels = null;
                holder.checkBox.setChecked(false);
//                    mLastCheckedCheckBox.setChecked(false);
            }
        });

        if (mSelectedCategoryModels != null && mSelectedCategoryModels.getId().equals(model.getId())) {
            holder.checkBox.setChecked(true);
            mLastCheckedCheckBox = holder.checkBox;
        } else holder.checkBox.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return mCategoryModels != null ? mCategoryModels.size() : 0;
    }

    class CapitalViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        CapitalViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public StateModel getSelectedCategory() {
        return mSelectedCategoryModels;
    }

    public void swapData(List<StateModel> categoryModels) {
        mCategoryModels = categoryModels;

        notifyDataSetChanged();
    }

    public void setSelectedCategoryModels(StateModel mSelectedCategoryModels) {
        this.mSelectedCategoryModels = mSelectedCategoryModels;
        notifyDataSetChanged();
    }
}
