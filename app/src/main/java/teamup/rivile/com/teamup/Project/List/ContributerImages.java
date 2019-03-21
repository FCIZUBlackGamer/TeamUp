package teamup.rivile.com.teamup.Project.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

public class ContributerImages extends RecyclerView.Adapter<ContributerImages.Vholder> {

    Context context;
    List<UserModel> deals;

    TextView mEmptyView;

    public ContributerImages(Context context, List<UserModel> talabats, TextView emptyView) {
        this.context = context;
        this.deals = talabats;
        mEmptyView = emptyView;
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

            if (deals.get(position) != null && deals.get(position).getImage() != null && !deals.get(position).getImage().isEmpty()) {
                try {
                    if (deals.get(position).getSocialId() != null){
                        Picasso.get().load(deals.get(position).getImage()).into(holder.image);
                    }else {
                        Picasso.get().load(API.BASE_URL + deals.get(position).getImage()).into(holder.image);
                    }
                    holder.image_name.setVisibility(View.GONE);
                } catch (Exception e) {
                    holder.image_name.setVisibility(View.VISIBLE);
                    String[] sp = deals.get(position).getFullName().split(" ");
                    if (sp.length == 1){
                        holder.image_name.setText(deals.get(position).getFullName().charAt(0)+"");
                    }else if (sp.length > 0 && sp.length <= 2) {
                        for (int j = 0; j < sp.length; j++) {
                            holder.image_name.append(sp[j]+"");
                        }
                    }else if (sp.length > 2){
                        for (int j = 0; j < 2; j++) {
                            holder.image_name.append(sp[j]+"");
                        }
                    }
                }
            } else {
                try {
                    holder.image_name.setVisibility(View.VISIBLE);
                    String[] sp = deals.get(position).getFullName().split(" ");
                    if (!deals.get(position).getFullName().contains(" ")){
                        Log.e("IIIII",deals.get(position).getFullName().charAt(0)+"");
                        holder.image_name.setText(deals.get(position).getFullName().charAt(0)+"");

                    }else if (sp.length > 0 && sp.length <= 2) {
                        for (int j = 0; j < sp.length; j++) {
                            holder.image_name.append(sp[j]+"");
                        }
                    }else if (sp.length > 2){
                        for (int j = 0; j < 2; j++) {
                            holder.image_name.append(sp[j]+"");
                        }
                    }
                } catch (Exception e) {

                }
            }
        }

        holder.image.setContentDescription(deals.get(position).getFullName());

        holder.image.setOnClickListener(v -> ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, FragmentProfileHome.setId(deals.get(position).getId())).addToBackStack(FragmentProfileHome.class.getSimpleName()).commit());
    }

    @Override
    public int getItemCount() {
        if (deals != null && deals.size() > 0) {
//            hideEmptyView();
            return deals.size();
        } else {
//            showEmptyView();
            return 0;
        }
    }

    private void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    private void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
    }

    public class Vholder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView image_name;

        public Vholder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.im);
            image_name = itemView.findViewById(R.id.image_name);
        }

    }

}