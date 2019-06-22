package teamup.rivile.com.teamup.ui.Project.List;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import teamup.rivile.com.teamup.network.repository.AppNetworkRepository;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.network.APIModels.RefuseReason;
import teamup.rivile.com.teamup.network.APIModels.UserModel;

import static teamup.rivile.com.teamup.APIS.API.BASE_URL;
import static teamup.rivile.com.teamup.APIS.API.JoinRequestResponse.*;

public class JoinedPeopleAdapter extends RecyclerView.Adapter<JoinedPeopleAdapter.JoinedPeopleViewHolder> {
    private Application mApplication;

    private List<UserModel> mUsers = null;
    private int mProjectId;
    private List<RefuseReason> mRefuseReasonsList;

    private AlertDialog mParentDialog;
    private TextView mHeaderTextView;

    private AppNetworkRepository mNetworkRepository;

    private Fragment mParentFragment;
    private Context mContext;

    public JoinedPeopleAdapter(int mProjectId, List<RefuseReason> reasonsList, AlertDialog dialog, TextView headerTextView, Application application, Fragment parentFragment, Context context) {
        this.mProjectId = mProjectId;
        mRefuseReasonsList = reasonsList;

        mParentDialog = dialog;
        mHeaderTextView = headerTextView;
        mApplication = application;

        mNetworkRepository = AppNetworkRepository.getInstance(application);

        mParentFragment = parentFragment;
        mContext = context;
    }

    @NonNull
    @Override
    public JoinedPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new JoinedPeopleViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.joined_people_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull JoinedPeopleViewHolder holder, int position) {
        UserModel userModel = mUsers.get(position);

        String imageUrl = userModel.getImage();
        Picasso.get().load(BASE_URL + imageUrl).into(holder.userImageView);

        holder.nameTextView.setText(userModel.getFullName());

        int userId = userModel.getId();

        holder.acceptButton.setOnClickListener(v ->
                mNetworkRepository.acceptUserJoinRequest(mProjectId, userId)
                        .observe(mParentFragment, s -> {
                            respondToUser(s, null, STATUS_ACCEPT, position);
                        })
        );

        holder.rejectButton.setOnClickListener(v -> showRefuseAlertDialog(userId, position));

        holder.blockButton.setOnClickListener(v ->
                mNetworkRepository.blockUser(mProjectId, userId)
                        .observe(mParentFragment, s -> respondToUser(s, null, STATUS_REFUSE, position)));

        Integer joinStatus = userModel.getJoinOfferStatus();
        if (joinStatus == null) joinStatus = STATUS_ON_HOLD;

        checkJoinStatus(joinStatus, holder);
    }

    private void checkJoinStatus(Integer joinedOfferStatus, JoinedPeopleViewHolder holder) {
        switch (joinedOfferStatus) {
            case STATUS_ACCEPT:
                holder.rejectButton.setVisibility(View.GONE);
                holder.blockButton.setVisibility(View.GONE);

                holder.acceptButton.setEnabled(false);
                holder.acceptButton.setText(mApplication.getString(R.string.accepted));
                break;
            case STATUS_REFUSE:
                holder.acceptButton.setVisibility(View.GONE);
                holder.blockButton.setVisibility(View.GONE);

                holder.rejectButton.setEnabled(false);
                holder.rejectButton.setText(mApplication.getString(R.string.rejected));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    class JoinedPeopleViewHolder extends RecyclerView.ViewHolder {
        ImageView userImageView;
        TextView nameTextView;
        Button acceptButton, rejectButton, blockButton;

        JoinedPeopleViewHolder(@NonNull View itemView) {
            super(itemView);

            userImageView = itemView.findViewById(R.id.iv_user_image);
            nameTextView = itemView.findViewById(R.id.tv_name);
            acceptButton = itemView.findViewById(R.id.btn_accept);
            rejectButton = itemView.findViewById(R.id.btn_reject);
            blockButton = itemView.findViewById(R.id.btn_block);
        }
    }

    private void showRefuseAlertDialog(int userId, int position) {
        mParentDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_refuse_reason, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText otherReasonEditText = view.findViewById(R.id.ed_other_reason);

        Spinner reasonsSpinner = view.findViewById(R.id.s_reasons);

        ArrayList<String> reasonsArrayList = new ArrayList<>(); //TODO: get reasons from the server
        reasonsArrayList.add(mApplication.getString(R.string.select));
        for (RefuseReason reason : mRefuseReasonsList) {
            reasonsArrayList.add(reason.getName());
        }
        reasonsArrayList.add(mApplication.getString(R.string.other_reason));

        reasonsSpinner.setAdapter(new ArrayAdapter<>(mApplication, android.R.layout.simple_spinner_item, reasonsArrayList));
        reasonsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                otherReasonEditText.setVisibility((position == reasonsArrayList.size() - 1) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button continueButton = view.findViewById(R.id.btn_continue);
        continueButton.setOnClickListener(v -> {
            int selectedPosition = reasonsSpinner.getSelectedItemPosition();

            if (selectedPosition == 0) { // option select, used for better ui
                Toast.makeText(mApplication, mApplication.getString(R.string.please_select_reason), Toast.LENGTH_SHORT).show();
            } else if (selectedPosition == reasonsArrayList.size() - 1) { // option other reason, using otherReasonEditText text
                String otherReason = otherReasonEditText.getText().toString();
                if (otherReason.isEmpty()) {
                    Toast.makeText(mApplication, mApplication.getString(R.string.please_enter_a_reason), Toast.LENGTH_SHORT).show();
                } else {
                    continueButton.setEnabled(false);
                    sendRefuseResponse(new RefuseReason(otherReason), userId, position, dialog);
                }
            } else {
                RefuseReason selectedReason = mRefuseReasonsList.get(selectedPosition - 1);
                continueButton.setEnabled(false);
                sendRefuseResponse(selectedReason, userId, position, dialog);
            }
        });

        dialog.show();
    }

    private void sendRefuseResponse(RefuseReason reason, int userId, int position, AlertDialog dialog) {
        Integer reasonId = reason.getId();
        String reasonName = reason.getName();
        if (reasonId == null) {
            mNetworkRepository.refuseUserJoinRequestWithReason(mProjectId, userId, reasonName)
                    .observe(mParentFragment, s -> respondToUser(s, dialog, STATUS_REFUSE, position));
        } else {
            mNetworkRepository.refuseUserJoinRequestWithSystemReason(mProjectId, userId, reasonId)
                    .observe(mParentFragment, s -> respondToUser(s, dialog, STATUS_REFUSE, position));
        }
    }

    private void respondToUser(String serverResponse, @Nullable AlertDialog dialog, Integer status, int position) {
        if (serverResponse != null) {
            if (serverResponse.equals("Success")) {
                Toast.makeText(mApplication, "Success", Toast.LENGTH_SHORT).show();

                if (dialog != null) {
                    dialog.dismiss();
                    mParentDialog.show();
                }

                if (status == null) mUsers.remove(position);
                else {
                    UserModel userModel = mUsers.get(position);
                    userModel.setJoinOfferStatus(status);
                }

                swapData(mUsers, false);
            } else if (serverResponse.equals("Fail")) {
                Toast.makeText(mApplication, "Fail", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(mApplication, "Fail", Toast.LENGTH_SHORT).show();
        }
    }

    public void swapData(List<UserModel> users, boolean sort) {
        mUsers = users;

        if (mUsers != null && sort) {
            Collections.sort(mUsers, (o1, o2) -> o1.getJoinOfferStatus().compareTo(o2.getJoinOfferStatus()));
        }

        if (mUsers == null || mUsers.size() == 0) {
            mHeaderTextView.setText(mApplication.getString(R.string.no_one_joined));
        } else {
            mHeaderTextView.setText(mApplication.getString(R.string.see_joined_people));
        }

        notifyDataSetChanged();
    }
}
