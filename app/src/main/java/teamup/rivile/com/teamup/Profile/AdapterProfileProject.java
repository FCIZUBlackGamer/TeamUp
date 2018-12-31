package teamup.rivile.com.teamup.Profile;

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
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import teamup.rivile.com.teamup.Project.List.ContributerImages;
import teamup.rivile.com.teamup.Project.List.Project;
import teamup.rivile.com.teamup.R;

public class AdapterProfileProject extends RecyclerView.Adapter<AdapterProfileProject.Vholder> {

    Context context;
    List<Project> projects;
    FragmentManager fragmentManager;

    public AdapterProfileProject(Context context, List<Project> talabats) {
        this.context = context;
        this.projects = talabats;
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

        holder.project_name.setText(projects.get(position).getProjectName());
        holder.location.setText(projects.get(position).getLocation());
        holder.num_likes.setText(projects.get(position).getNumLikes()+"");
        holder.num_contributer.setText(projects.get(position).getNumContributers()+"");

        holder.adapter = new ContributerImages(context, projects.get(position).getContributersImages());
        holder.recyclerView.setAdapter(holder.adapter);
        try {
            if (!projects.get(position).getUserImage().isEmpty()) {
                Picasso.get().load(projects.get(position).getUserImage()).into(holder.image);
            }
        } catch (Exception e) {

        }
        holder.ratingBar.setRating(projects.get(position).getUserRate());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Move To Profile fragment */
//                fragmentManager.beginTransaction()
//                        .replace(R.id.frame, new FragmentProfile()).addToBackStack("FragmentProfile").commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView project_name, location;
        TextView num_likes, num_contributer;
        RatingBar ratingBar;
        CircleImageView image;
        LinearLayout linearLayout;
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;

        public Vholder(View itemView) {
            super(itemView);
            project_name = itemView.findViewById(R.id.project_name);
            location = itemView.findViewById(R.id.location);
            ratingBar = itemView.findViewById(R.id.person_rate);
            image = itemView.findViewById(R.id.user_image);
            linearLayout = itemView.findViewById(R.id.lin);
            num_contributer = itemView.findViewById(R.id.num_contributer);
            num_likes = itemView.findViewById(R.id.num_likes);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rec);
            recyclerView.setLayoutManager(layoutManager);
        }

    }

}