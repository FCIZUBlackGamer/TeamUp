package teamup.rivile.com.teamup.Project.Add.Adapters;

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
import teamup.rivile.com.teamup.Uitls.APIModels.TagsModel;

public class ChipsAdapter extends RecyclerView.Adapter<ChipsAdapter.ChipsViewHolder> {
    private ArrayList<TagsModel> mTypeModels;

    private LoadedChipsAdapter mLoadedDataAdapter;

    public ChipsAdapter(@Nullable ArrayList<TagsModel> typeModels, @Nullable LoadedChipsAdapter loadedDataAdapter) {
        if (typeModels != null)
            mTypeModels = typeModels;
        else mTypeModels = new ArrayList<>();

        mLoadedDataAdapter = loadedDataAdapter;
    }

    @NonNull
    @Override
    public ChipsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChipsViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.entry_chip_list_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ChipsViewHolder holder, int i) {
        final TagsModel typeModel = mTypeModels.get(i);
        holder.chip.setText(typeModel.getName());

        holder.chip.setOnCloseIconClickListener(v -> {
            if (typeModel.getId() != 0 && mLoadedDataAdapter != null) {
                //restore loaded typeExperience
                mLoadedDataAdapter.addTypeModel(typeModel);
            }

            removeTypeModel(typeModel);
//            mTypeModels.remove(typeModel);
//            notifyDataSetChanged();
        });
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

    public void addTypeModel(@NonNull TagsModel typeModel) {
        mTypeModels.add(typeModel);

//        notifyItemInserted(mTypeModels.size() - 1);
        notifyDataSetChanged();
    }

    public void removeTypeModel(@NonNull TagsModel typeModel) {
        mTypeModels.remove(typeModel);

//        notifyItemRemoved(mTypeModels.size() - 1);
        notifyDataSetChanged();
    }

    @NonNull
    public List<TagsModel> getSelectedTypeModels() {
        return mTypeModels;
    }
}
