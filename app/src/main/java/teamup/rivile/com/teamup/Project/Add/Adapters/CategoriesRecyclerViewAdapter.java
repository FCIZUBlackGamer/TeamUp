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
    private List<CapitalModel> mCapitalModels;

    private CapitalModel mSelectedCapitalModels;

    private CheckBox mLastCheckedCheckBox;

    public CategoriesRecyclerViewAdapter(@NonNull List<CapitalModel> capitalModels, @NonNull CapitalModel selectedCapitalModels) {
        mCapitalModels = capitalModels;
        mSelectedCapitalModels = selectedCapitalModels;
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
        final CapitalModel model = mCapitalModels.get(i);
        holder.checkBox.setText(model.getName());

        if (mSelectedCapitalModels.equals(model)) holder.checkBox.setChecked(true);
        else holder.checkBox.setChecked(false);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSelectedCapitalModels = model;
                    holder.checkBox.setChecked(true);

                    if (mLastCheckedCheckBox != null)
                        mLastCheckedCheckBox.setChecked(false);
                    mLastCheckedCheckBox = holder.checkBox;
                } else {
                    mSelectedCapitalModels = null;
                    holder.checkBox.setChecked(false);
//                    mLastCheckedCheckBox.setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCapitalModels != null ? mCapitalModels.size() : 0;
    }

    class CapitalViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        CapitalViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView;
        }
    }

//    public void addAllAsSelected(@NonNull List<CapitalModel> models) {
//        mSelectedCapitalModels = (ArrayList<CapitalModel>) models;
//
//        notifyDataSetChanged();
//    }
//
//    public void removeAllSelected(){
//        mSelectedCapitalModels.clear();
//
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    public List<CapitalModel> getSelectedCapitals(){
//        return mSelectedCapitalModels;
//    }
}
