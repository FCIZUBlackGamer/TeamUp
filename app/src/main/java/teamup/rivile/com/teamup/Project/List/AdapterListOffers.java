package teamup.rivile.com.teamup.Project.List;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;
import teamup.rivile.com.teamup.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.Project.Details.FragmentOfferDetails;
import teamup.rivile.com.teamup.Project.join.FragmentJoinHome;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDetailsDataBase;

public class AdapterListOffers extends RecyclerView.Adapter<AdapterListOffers.Vholder> {

    private Helper mHelper;

    Context context;
    List<Offers> offersList;
    FragmentManager fragmentManager;
    Realm realm;
    List<LikeModelDataBase> likeModelDataBase;
    int ty;

    public AdapterListOffers(Context context, List<Offers> talabats, List<LikeModelDataBase> likeModel, int type, Helper helper) {
        this.context = context;
        this.offersList = talabats;
        ty = type;
        likeModelDataBase  = likeModel;
        mHelper = helper;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_project, parent, false);
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        realm = Realm.getDefaultInstance();
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        if (ty == FragmentListProjects.NORMAL) {
            holder.delete.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < likeModelDataBase.size(); i++) {
            if (offersList.get(position).getId() == likeModelDataBase.get(i).getOfferId()){
                holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like,0,0,0);
            }
        }

        holder.project_name.setText(offersList.get(position).getName());
        holder.num_likes.setText(offersList.get(position).getNumLiks() + "");
        holder.num_contributer.setText(offersList.get(position).getNumContributorTo() + "");
        holder.location.setText(offersList.get(position).getAddress());
        holder.project_desc.setText(offersList.get(position).getDescription());
        if (offersList.get(position).getTags() != null){
            for (int i = 0; i < offersList.get(position).getTags().size() ;i++) {
                holder.project_tag.append("\n#"+offersList.get(position).getTags().get(i).getName());
            }
        }


        holder.adapter = new ContributerImages(context, offersList.get(position).getUsers());
        holder.recyclerView.setAdapter(holder.adapter);
        for (int i = 0; i < offersList.get(position).getUsers().size(); i++) {
            if (offersList.get(position).getUserId() == offersList.get(position).getUsers().get(i).getId()) {
                Picasso.get().load(offersList.get(position).getUsers().get(i).getImage()).into(holder.image);
            }
        }

        holder.delete.setOnClickListener(v -> {
            realm.executeTransaction(realm1 -> {
                RealmResults<LoginDataBase> loginDataBases = realm1.where(LoginDataBase.class)
                        .findAll();
                OfferDetailsDataBase offerDetailsDataBases = loginDataBases.get(0).getOffers().get(position);
                offerDetailsDataBases.deleteFromRealm();
                realm1.commitTransaction();
                offersList.remove(position);
                notifyDataSetChanged();
            });
        });

        holder.image.setOnClickListener(v -> {
            /** Move To Profile fragment */
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, new FragmentProfileHome().setId(offersList.get(position).getUserId())).addToBackStack("FragmentProfileHome").commit();
        });

        holder.linearLayout.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.frame,
                        FragmentOfferDetails.setProjectId(offersList.get(position).getId(), ty, position))
                .addToBackStack("FragmentProfileHome").commit());

        holder.con.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.frame,
                        FragmentOfferDetails.setProjectId(offersList.get(position).getId(), ty, position))
                .addToBackStack("FragmentProfileHome").commit());

        holder.project_desc.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.frame,
                        FragmentOfferDetails.setProjectId(offersList.get(position).getId(), ty, position))
                .addToBackStack("FragmentProfileHome").commit());

        holder.make_offer.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.frame,
                        FragmentJoinHome.setOfferId(offersList.get(position).getId()))
                .addToBackStack("FragmentProfileHome").commit());

        holder.like.setOnClickListener(v -> {
            Log.e("Like","1");
            if (holder.like.getCompoundDrawables().equals(context.getResources().getDrawable(R.drawable.ic_like))){
                holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_border_black_24dp,0,0,0);
                Log.e("Like","2");
            }else {
                FragmentOfferDetails.likeOffer(offersList.get(position).getId());
                Log.e("Like","3");
            }
        });

        //TODO: get project URL and send it here
        holder.share.setOnClickListener(v -> mHelper.shareUrl("", offersList.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView project_name, location, project_desc, project_tag;
        TextView num_likes, num_contributer;
        CircleImageView image;
        LinearLayout linearLayout, con;
        TextView like, share, make_offer;
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        ImageView delete;

        public Vholder(View itemView) {
            super(itemView);
            project_name = itemView.findViewById(R.id.project_name);
            project_tag = itemView.findViewById(R.id.project_tag);
            delete = itemView.findViewById(R.id.delete);
            project_desc = itemView.findViewById(R.id.project_desc);
            location = itemView.findViewById(R.id.location);
            image = itemView.findViewById(R.id.user_image);
            linearLayout = itemView.findViewById(R.id.lin);
            con = itemView.findViewById(R.id.con);
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

    public interface Helper {
        void shareUrl(String url, String projectName);
    }
}