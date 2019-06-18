package teamup.rivile.com.teamup.ui.Project.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.RefuseReason;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

import static teamup.rivile.com.teamup.APIS.API.BASE_URL;
import static teamup.rivile.com.teamup.APIS.API.JoinRequestResponse.*;
import static teamup.rivile.com.teamup.APIS.API.URL_TOKEN;

public class JoinedPeopleAdapter extends RecyclerView.Adapter<JoinedPeopleAdapter.JoinedPeopleViewHolder> {
    private Context mContext;
    private RetrofitMethods mRetrofitMethods;

    private List<UserModel> mUsers = null;
    private int mProjectId;
    private List<RefuseReason> mRefuseReasonsList;

    private AlertDialog mParentDialog;
    private TextView mHeaderTextView;

    public JoinedPeopleAdapter(int mProjectId, List<RefuseReason> reasonsList, AlertDialog dialog, TextView headerTextView, Context context) {
        this.mProjectId = mProjectId;
        mRefuseReasonsList = reasonsList;

        mParentDialog = dialog;
        mHeaderTextView = headerTextView;
        mContext = context;

        mRetrofitMethods = new RetrofitConfigurations(BASE_URL)
                .getRetrofit().create(RetrofitMethods.class);
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
                respondToUser(
                        mRetrofitMethods.acceptUserJoinRequest(mProjectId, userId, STATUS_ACCEPT, URL_TOKEN),
                        position, null, STATUS_ACCEPT
                ));

        holder.rejectButton.setOnClickListener(v -> showRefuseAlertDialog(userId, position));

        holder.blockButton.setOnClickListener(v ->
                respondToUser(mRetrofitMethods.blockUser(mProjectId, userId, STATUS_BLOCK, URL_TOKEN),
                        position, null, null));

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
                holder.acceptButton.setText(mContext.getString(R.string.accepted));
                break;
            case STATUS_REFUSE:
                holder.acceptButton.setVisibility(View.GONE);
                holder.blockButton.setVisibility(View.GONE);

                holder.rejectButton.setEnabled(false);
                holder.rejectButton.setText(mContext.getString(R.string.rejected));
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
        reasonsArrayList.add(mContext.getString(R.string.select));
        for (RefuseReason reason : mRefuseReasonsList) {
            reasonsArrayList.add(reason.getName());
        }
        reasonsArrayList.add(mContext.getString(R.string.other_reason));

        reasonsSpinner.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, reasonsArrayList));
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
                Toast.makeText(mContext, mContext.getString(R.string.please_select_reason), Toast.LENGTH_SHORT).show();
            } else if (selectedPosition == reasonsArrayList.size() - 1) { // option other reason, using otherReasonEditText text
                String otherReason = otherReasonEditText.getText().toString();
                if (otherReason.isEmpty()) {
                    Toast.makeText(mContext, mContext.getString(R.string.please_enter_a_reason), Toast.LENGTH_SHORT).show();
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
        Call<String> stringCall;
        if (reasonId == null) {
            stringCall = mRetrofitMethods.refuseUserJoinRequestWithReason(mProjectId, userId, STATUS_REFUSE, reasonName, URL_TOKEN);
        } else {
            stringCall = mRetrofitMethods.refuseUserJoinRequestWithSystemReason(mProjectId, userId, STATUS_REFUSE, reasonId, URL_TOKEN);
        }

        respondToUser(stringCall, position, dialog, STATUS_REFUSE);
    }

    private void respondToUser(Call<String> stringCall, int position, @Nullable AlertDialog dialog, Integer status) {
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                String serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.equals("Success")) {
                        Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(mContext, "Fail", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void swapData(List<UserModel> users, boolean sort) {
        mUsers = users;

        if (mUsers != null && sort) {
            Collections.sort(mUsers, (o1, o2) -> o1.getJoinOfferStatus().compareTo(o2.getJoinOfferStatus()));
        }

        if (mUsers == null || mUsers.size() == 0) {
            mHeaderTextView.setText(mContext.getString(R.string.no_one_joined));
        } else {
            mHeaderTextView.setText(mContext.getString(R.string.see_joined_people));
        }

        notifyDataSetChanged();
    }
}
