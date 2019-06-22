package teamup.rivile.com.teamup.ui.Search;

import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import teamup.rivile.com.teamup.network.APIModels.Department;
import teamup.rivile.com.teamup.R;

public class DepartmentsAdapter extends RecyclerView.Adapter<DepartmentsAdapter.CapitalViewHolder> {
    private List<Department> mDepartments;

    private ArrayList<Department> mSelectedDepartments;


    public DepartmentsAdapter(List<Department> departments, ArrayList<Department> selectedDepartments) {
        this.mDepartments = departments;
        this.mSelectedDepartments = selectedDepartments;
        if (mSelectedDepartments == null) {
            mSelectedDepartments = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public CapitalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CapitalViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.department_list_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final CapitalViewHolder holder, int i) {
        Department department = mDepartments.get(i);

        holder.chip.setText(department.getName());

        holder.chip.setOnCheckedChangeListener(null);
        holder.chip.setChecked(mSelectedDepartments.contains(department));

        holder.chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.chip.setChipBackgroundColorResource(R.color.colorPrimary);
                mSelectedDepartments.add(department);
            } else {
                holder.chip.setChipBackgroundColorResource(R.color.gray);
                mSelectedDepartments.remove(department);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDepartments != null ? mDepartments.size() : 0;
    }

    class CapitalViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        CapitalViewHolder(@NonNull View itemView) {
            super(itemView);

            chip = itemView.findViewById(R.id.chip);
        }
    }

    public void swapData(List<Department> departments) {
        mDepartments = departments;

        notifyDataSetChanged();
    }

    @NonNull
    public ArrayList<Department> getSelectedDepartments() {
        return mSelectedDepartments;
    }

    public void setSelectedDepartments(ArrayList<Department> selectedDepartments) {
        this.mSelectedDepartments = selectedDepartments;
        if (mSelectedDepartments == null) {
            mSelectedDepartments = new ArrayList<>();
        }
        notifyDataSetChanged();
    }
}
