package teamup.rivile.com.teamup.ui.Profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import teamup.rivile.com.teamup.ui.Project.List.ContributerImages;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;

public class AdapterProfileProject extends RecyclerView.Adapter<AdapterProfileProject.Vholder> {

    Context context;
    List<Offers> offersList;
    FragmentManager fragmentManager;

    public AdapterProfileProject(Context context, List<Offers> offersList) {
        this.context = context;
        this.offersList = offersList;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_profile_project, parent, false);
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.project_name.setText(offersList.get(position).getName());
        holder.num_likes.setText(offersList.get(position).getNumLiks() + "");
        holder.nun_contributor.setText(offersList.get(position).getNumContributor() + "");

        if (offersList.get(position).getUsers() != null)
            for (int i = 0; i < offersList.get(position).getUsers().size(); i++) {
                Log.i("Id", offersList.get(position).getId() + "");
                if (offersList.get(position).getUserId() == offersList.get(position).getUsers().get(i).getId()) {
                    holder.location.setText(offersList.get(position).getUsers().get(i).getAddress());
                }
            }

        holder.adapter = new ContributerImages(context, offersList.get(position).getUsers(),holder.emptyView);
        holder.recyclerView.setAdapter(holder.adapter);

        holder.image.setOnClickListener(v -> {
            /** Move To Profile fragment */
//                fragmentManager.beginTransaction()
//                        .replace(R.id.frame, new FragmentProfile()).addToBackStack(FragmentProfile.class.getSimpleName()).commit();
        });

    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView project_name, location;
        TextView num_likes, nun_contributor;
        CircleImageView image;
        LinearLayout linearLayout;
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        TextView emptyView;

        public Vholder(View itemView) {
            super(itemView);
            project_name = itemView.findViewById(R.id.project_name);
            location = itemView.findViewById(R.id.location);
            image = itemView.findViewById(R.id.user_image);
            linearLayout = itemView.findViewById(R.id.lin);
            nun_contributor = itemView.findViewById(R.id.num_contributer);
            num_likes = itemView.findViewById(R.id.num_likes);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rec);
            recyclerView.setLayoutManager(layoutManager);

            emptyView = itemView.findViewById(R.id.tv_empty_view);
        }

    }

}