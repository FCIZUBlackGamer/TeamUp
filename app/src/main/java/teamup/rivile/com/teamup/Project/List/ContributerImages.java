package teamup.rivile.com.teamup.Project.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import teamup.rivile.com.teamup.R;

public class ContributerImages extends RecyclerView.Adapter<ContributerImages.Vholder> {

    Context context;
    List<String> deals;


    public ContributerImages(Context context, List<String> talabats) {
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

        try {
            if (!deals.get(position).isEmpty()) {
                Picasso.get().load(deals.get(position)).into(holder.image);
            }
        } catch (Exception e) {

        }


    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        CircleImageView image;

        public Vholder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.user_image);
        }

    }

}