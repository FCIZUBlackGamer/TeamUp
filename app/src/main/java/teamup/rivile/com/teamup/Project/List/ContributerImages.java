package teamup.rivile.com.teamup.Project.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import teamup.rivile.com.teamup.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

public class ContributerImages extends RecyclerView.Adapter<ContributerImages.Vholder> {

    Context context;
    List<UserModel> deals;


    public ContributerImages(Context context, List<UserModel> talabats) {
        this.context = context;
        this.deals = talabats;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contributer_images, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        for (int i = 0; i < deals.size(); i++) {
            try {
                if (!deals.get(position).getImage().isEmpty()) {
                    Picasso.get().load(deals.get(position).getImage()).into(holder.image);
                }
            } catch (Exception e) {

            }
        }
        holder.image.setOnClickListener(v -> ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new FragmentProfileHome().setId(deals.get(position).getId())).addToBackStack("FragmentProfileHome").commit());
    }

    @Override
    public int getItemCount() {
        return deals != null ? deals.size() : 0;
    }

    public class Vholder extends RecyclerView.ViewHolder {
        CircleImageView image;

        public Vholder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.im);
        }

    }

}