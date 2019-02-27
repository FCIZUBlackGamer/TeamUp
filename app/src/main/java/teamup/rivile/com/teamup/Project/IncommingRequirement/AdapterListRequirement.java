package teamup.rivile.com.teamup.Project.IncommingRequirement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import teamup.rivile.com.teamup.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.Project.join.FragmentJoinHome;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

public class AdapterListRequirement extends RecyclerView.Adapter<AdapterListRequirement.Vholder> {

    Context context;
    OfferDetailsJsonObject incomingRequirments;
    FragmentManager fragmentManager;

    public AdapterListRequirement(Context context, OfferDetailsJsonObject talabats) {
        this.context = context;
        this.incomingRequirments = talabats;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_requirement, parent, false);
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {
        UserModel userModel = incomingRequirments.getOffer().getUsers().get(0);
        holder.person_name.setText(userModel.getFullName());
        holder.sent_time.setText(incomingRequirments.getOffer().getDate());

        if (userModel.getImage() != null && !userModel.getImage().isEmpty()) {
            try {
                Picasso.get().load(userModel.getImage()).into(holder.image);
            } catch (Exception e) {

            }
        }

        holder.image.setOnClickListener(v -> {
            /** Move To Profile fragment */
            fragmentManager.beginTransaction()
                    .replace(R.id.frame,  FragmentProfileHome.setId(userModel.getId())).addToBackStack(FragmentProfileHome.class.getSimpleName()).commit();
        });

        //Todo: implement details Action
        holder.details.setOnClickListener(v -> {
            for (int i = 0; i < incomingRequirments.getOffer().getRequirments().size(); i++) {
                if (incomingRequirments.getOffer().getRequirments().get(i).getUserId() == incomingRequirments.getOffer().getUsers().get(position).getId()) {
                    Gson gson = new Gson();
                    Log.e("Requirement", gson.toJson(incomingRequirments.getOffer().getRequirments().get(i)));
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame,
                                    FragmentJoinHome.setRequirement(incomingRequirments.getOffer().getRequirments().get(i), incomingRequirments.getOffer().getId()))
                            .addToBackStack(FragmentJoinHome.class.getSimpleName()).commit();
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return incomingRequirments.getOffer().getRequirments().size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView person_name, sent_time;
        CircleImageView image;
        Button details;

        public Vholder(View itemView) {
            super(itemView);
            person_name = itemView.findViewById(R.id.person_name);
            sent_time = itemView.findViewById(R.id.sent_time);
            image = itemView.findViewById(R.id.im);
            details = itemView.findViewById(R.id.details);
        }
    }

}