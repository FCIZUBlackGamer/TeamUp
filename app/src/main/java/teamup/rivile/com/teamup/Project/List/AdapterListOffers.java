package teamup.rivile.com.teamup.Project.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import teamup.rivile.com.teamup.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.Project.Details.FragmentOfferDetails;
import teamup.rivile.com.teamup.Project.join.FragmentJoinHome;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;

public class AdapterListOffers extends RecyclerView.Adapter<AdapterListOffers.Vholder> {

    Context context;
    List<Offers> offersList;
    FragmentManager fragmentManager;

    public AdapterListOffers(Context context, List<Offers> talabats) {
        this.context = context;
        this.offersList = talabats;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_project, parent, false);
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.project_name.setText(offersList.get(position).getName());
        holder.num_likes.setText(offersList.get(position).getNumLiks() + "");
        holder.num_contributer.setText(offersList.get(position).getNumContributorTo() + "");
        holder.location.setText(offersList.get(position).getAddress());
        holder.project_desc.setText(offersList.get(position).getDescription());

        holder.adapter = new ContributerImages(context, offersList.get(position).getUsers());
        holder.recyclerView.setAdapter(holder.adapter);
        for (int i = 0; i < offersList.get(position).getUsers().size(); i++) {
            if (offersList.get(position).getUserId() == offersList.get(position).getUsers().get(i).getId()) {
                Picasso.get().load(offersList.get(position).getUsers().get(i).getImage()).into(holder.image);
            }
        }

        holder.image.setOnClickListener(v -> {
            /** Move To Profile fragment */
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, new FragmentProfileHome().setId(offersList.get(position).getUserId())).addToBackStack("FragmentProfileHome").commit();
        });

        holder.linearLayout.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.frame,
                        FragmentOfferDetails.setProjectId(offersList.get(position).getId()))
                .addToBackStack("FragmentProfileHome").commit());

        holder.make_offer.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.frame,
                        FragmentJoinHome.setOfferId(offersList.get(position).getId()))
                .addToBackStack("FragmentProfileHome").commit());
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView project_name, location, project_desc;
        TextView num_likes, num_contributer;
        CircleImageView image;
        LinearLayout linearLayout;
        TextView like, share, make_offer;
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;

        public Vholder(View itemView) {
            super(itemView);
            project_name = itemView.findViewById(R.id.project_name);
            project_desc = itemView.findViewById(R.id.project_desc);
            location = itemView.findViewById(R.id.location);
            image = itemView.findViewById(R.id.user_image);
            linearLayout = itemView.findViewById(R.id.lin);
            like = itemView.findViewById(R.id.like);
            share = itemView.findViewById(R.id.share);
            make_offer = itemView.findViewById(R.id.make_offer);
            num_contributer = itemView.findViewById(R.id.num_contributer);
            num_likes = itemView.findViewById(R.id.num_likes);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView = itemView.findViewById(R.id.rec);
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}