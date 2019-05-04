package teamup.rivile.com.teamup.ui.Project.Add.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.StateModel;

public class CapitalsRecyclerViewAdapter extends RecyclerView.Adapter<CapitalsRecyclerViewAdapter.CapitalViewHolder> {
    private List<StateModel> mStateModels;

    private ArrayList<StateModel> mSelectedStateModels;

    public CapitalsRecyclerViewAdapter(@NonNull List<StateModel> stateModels, @NonNull ArrayList<StateModel> selectedStateModels) {
        mStateModels = stateModels;
        mSelectedStateModels = selectedStateModels;
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
        final StateModel model = mStateModels.get(i);
        holder.checkBox.setText(model.getName());

        holder.checkBox.setOnCheckedChangeListener(null);

        if (selected(model)) holder.checkBox.setChecked(true);
        else holder.checkBox.setChecked(false);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) mSelectedStateModels.add(model);
            else mSelectedStateModels.remove(model);
        });
    }

    @Override
    public int getItemCount() {
        return mStateModels != null ? mStateModels.size() : 0;
    }

    class CapitalViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        CapitalViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public ArrayList<StateModel> getSelectedCapitals() {
        return mSelectedStateModels;
    }

    public void swapData(List<StateModel> stateModels) {
        mStateModels = stateModels;

        notifyDataSetChanged();
    }

    public void setSelectedCapitalModels(ArrayList<StateModel> mSelectedStateModels) {
        this.mSelectedStateModels = mSelectedStateModels;

        notifyDataSetChanged();
    }

    private boolean selected(StateModel model) {
        for (int i = 0; i < mSelectedStateModels.size(); ++i) {
            if (mSelectedStateModels.get(i).getName().equals(model.getName())) return true;
        }
        return false;
    }
}
