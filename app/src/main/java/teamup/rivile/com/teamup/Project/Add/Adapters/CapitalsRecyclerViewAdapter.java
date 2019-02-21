package teamup.rivile.com.teamup.Project.Add.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.CapitalModel;

public class CapitalsRecyclerViewAdapter extends RecyclerView.Adapter<CapitalsRecyclerViewAdapter.CapitalViewHolder> {
    private List<CapitalModel> mCapitalModels;

    private ArrayList<CapitalModel> mSelectedCapitalModels;

    public CapitalsRecyclerViewAdapter(@NonNull List<CapitalModel> capitalModels, @NonNull ArrayList<CapitalModel> selectedCapitalModels) {
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
    public void onBindViewHolder(@NonNull CapitalViewHolder holder, int i) {
        final CapitalModel model = mCapitalModels.get(i);
        holder.checkBox.setText(model.getName());

        if (selected(model)) holder.checkBox.setChecked(true);
        else holder.checkBox.setChecked(false);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) mSelectedCapitalModels.add(model);
            else mSelectedCapitalModels.remove(model);
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

            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public ArrayList<CapitalModel> getSelectedCapitals() {
        return mSelectedCapitalModels;
    }

    public void swapData(List<CapitalModel> capitalModels) {
        mCapitalModels = capitalModels;

        notifyDataSetChanged();
    }

    public void setSelectedCapitalModels(ArrayList<CapitalModel> mSelectedCapitalModels) {
        this.mSelectedCapitalModels = mSelectedCapitalModels;

        notifyDataSetChanged();
    }

    private boolean selected(CapitalModel model) {
        for (int i = 0; i < mSelectedCapitalModels.size(); ++i) {
            if (mSelectedCapitalModels.get(i).getId().equals(model.getId())) return true;
        }
        return false;
    }
}
