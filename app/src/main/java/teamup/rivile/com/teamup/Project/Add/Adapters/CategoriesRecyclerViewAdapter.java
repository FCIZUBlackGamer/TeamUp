package teamup.rivile.com.teamup.Project.Add.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.CapitalModel;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.CapitalViewHolder> {
    private List<CapitalModel> mCategoryModels;

    private CapitalModel mSelectedCategoryModels;

    private CheckBox mLastCheckedCheckBox;

    public CategoriesRecyclerViewAdapter(List<CapitalModel> capitalModels,
                                         @NonNull CapitalModel selectedCapitalModels) {
        mCategoryModels = capitalModels;
        mSelectedCategoryModels = selectedCapitalModels;
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
        final CapitalModel model = mCategoryModels.get(i);
        holder.checkBox.setText(model.getName());

        if (mSelectedCategoryModels != null && mSelectedCategoryModels.getId().equals(model.getId())) {
            holder.checkBox.setChecked(true);
            mLastCheckedCheckBox = holder.checkBox;
        } else holder.checkBox.setChecked(false);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mSelectedCategoryModels = model;
                holder.checkBox.setChecked(true);

                if (mLastCheckedCheckBox != null)
                    mLastCheckedCheckBox.setChecked(false);
                mLastCheckedCheckBox = holder.checkBox;
            } else {
                mSelectedCategoryModels = null;
                holder.checkBox.setChecked(false);
//                    mLastCheckedCheckBox.setChecked(false);
            }
        });
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

    public CapitalModel getSelectedCategory() {
        return mSelectedCategoryModels;
    }

    public void swapData(List<CapitalModel> categoryModels) {
        mCategoryModels = categoryModels;

        notifyDataSetChanged();
    }

    public void setSelectedCategoryModels(CapitalModel mSelectedCategoryModels) {
        this.mSelectedCategoryModels = mSelectedCategoryModels;
        notifyDataSetChanged();
    }
}
