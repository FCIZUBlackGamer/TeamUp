package teamup.rivile.com.teamup.ui.projectsJoinRequests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.JoinedProject;

import static teamup.rivile.com.teamup.APIS.API.Constants.STATUS_ACCEPT;
import static teamup.rivile.com.teamup.APIS.API.Constants.STATUS_ON_HOLD;
import static teamup.rivile.com.teamup.APIS.API.Constants.STATUS_REFUSE;

public class JoinedProjectsAdapter extends RecyclerView.Adapter<JoinedProjectsAdapter.JoinedProjectsHolder> {
    private List<JoinedProject> mProjectList = null;
    private Context mContext;

    public JoinedProjectsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public JoinedProjectsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new JoinedProjectsHolder(
                LayoutInflater.from(viewGroup.getContext()).
                        inflate(R.layout.joined_project_list_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull JoinedProjectsHolder holder, int position) {
        JoinedProject joinedProject = mProjectList.get(position);

        holder.projectNameTextView.setText(joinedProject.getOfferName());

        Integer status = joinedProject.getStatus();
        int textColor = mContext.getResources().getColor(R.color.textColorBlack); // On Hold

        String statusString = mContext.getString(R.string.on_hold);
        if (status != null && status != STATUS_ON_HOLD) {
            if (status == STATUS_ACCEPT) {
                statusString = mContext.getString(R.string.accepted);
                textColor = mContext.getResources().getColor(R.color.textColorBlue);
            } else if (status == STATUS_REFUSE) {
                statusString = mContext.getString(R.string.rejected);
                textColor = mContext.getResources().getColor(R.color.buttonColorRed);
            }
        }

        holder.statusTextView.setText(statusString);
        holder.statusTextView.setTextColor(textColor);
    }

    @Override
    public int getItemCount() {
        return mProjectList == null ? 0 : mProjectList.size();
    }

    class JoinedProjectsHolder extends RecyclerView.ViewHolder {
        TextView projectNameTextView;
        TextView statusTextView;

        JoinedProjectsHolder(@NonNull View itemView) {
            super(itemView);

            projectNameTextView = itemView.findViewById(R.id.tv_project_name);
            statusTextView = itemView.findViewById(R.id.tv_status);
        }
    }

    public void swapData(List<JoinedProject> joinedProjectList) {
        mProjectList = joinedProjectList;

        notifyDataSetChanged();
    }
}
