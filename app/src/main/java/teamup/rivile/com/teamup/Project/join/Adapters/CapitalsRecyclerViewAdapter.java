package teamup.rivile.com.teamup.Project.join.Adapters;

import android.graphics.Color;
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

    public CapitalsRecyclerViewAdapter(@NonNull List<CapitalModel> capitalModels) {
        mCapitalModels = capitalModels;
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
        holder.checkBox.setEnabled(false);
        holder.checkBox.setChecked(true);
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

    public void swapData(List<CapitalModel> capitalModels) {
        mCapitalModels = capitalModels;

        notifyDataSetChanged();
    }
}
