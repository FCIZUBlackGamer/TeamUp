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

        if (mSelectedCapitalModels.contains(model)) holder.checkBox.setChecked(true);
        else holder.checkBox.setChecked(false);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mSelectedCapitalModels.add(model);
                else mSelectedCapitalModels.remove(model);
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
