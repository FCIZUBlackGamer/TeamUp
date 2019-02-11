package teamup.rivile.com.teamup.Project.join.Adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.ExperienceTypeModel;

public class ChipsAdapter extends RecyclerView.Adapter<ChipsAdapter.ChipsViewHolder> {
    private ArrayList<ExperienceTypeModel> mTypeModels;

    public ChipsAdapter(@Nullable ArrayList<ExperienceTypeModel> typeModels) {
        if (typeModels != null)
            mTypeModels = typeModels;
        else mTypeModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChipsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChipsViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.chip_list_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ChipsViewHolder holder, int i) {
        final ExperienceTypeModel typeModel = mTypeModels.get(i);
        holder.chip.setText(typeModel.getName());
    }

    @Override
    public int getItemCount() {
        return (mTypeModels != null) ? mTypeModels.size() : 0;
    }

    class ChipsViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        ChipsViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = itemView.findViewById(R.id.chip);
        }
    }

    public void swapData(ArrayList<ExperienceTypeModel> typeModels){
        mTypeModels = typeModels;

        notifyDataSetChanged();
    }
}
