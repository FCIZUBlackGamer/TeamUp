package teamup.rivile.com.teamup.SuggestedProject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.EmptyView.FragmentEmpty;
import teamup.rivile.com.teamup.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.Project.Add.FragmentAddHome;
import teamup.rivile.com.teamup.Project.Details.FragmentOfferDetails;
import teamup.rivile.com.teamup.Project.List.ContributerImages;
import teamup.rivile.com.teamup.Project.join.FragmentJoinHome;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.FavouriteModel;
import teamup.rivile.com.teamup.Uitls.APIModels.LikeModel;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.FavouriteDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDetailsDataBase;

public class AdapterListOffers extends RecyclerView.Adapter<AdapterListOffers.Vholder> {

    private Context context;
    private List<Offers> offersList;
    private FragmentManager fragmentManager;
    private Realm realm;
    private List<LikeModelDataBase> likeModelDataBase;

    /**
     * @param likeModel will replaced by the viewed projects
     *                  to mark it with purple color
     * */
    public AdapterListOffers(Context context, List<Offers> talabats, List<LikeModelDataBase> likeModel) {
        this.context = context;
        this.offersList = talabats;
        likeModelDataBase = likeModel;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sugested_projects, parent, false);
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        realm = Realm.getDefaultInstance();

        Log.e("Internal Offer Size", offersList.size() + "");
        return new Vholder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.project_name.setText(offersList.get(position).getName());
//        if (offersList.get(position).getDescription().length() > 500) {//Check Description length
//            String newDesc = offersList.get(position).getDescription().substring(0, 500) + context.getString(R.string.seeMore);
//            holder.project_desc.setText(newDesc);
//        } else {
//            holder.project_desc.setText(offersList.get(position).getDescription());
//        }
//        if (offersList.get(position).getTags() != null) {
//            for (int i = 0; i < offersList.get(position).getTags().size(); i++) {
//                if (holder.project_tag.getText().length() < 500) {
//                    holder.project_tag.append("#" + offersList.get(position).getTags().get(i).getName() + "\n");
//                } else {
//                    holder.project_tag.append(context.getString(R.string.seeMore));
//                    return;
//                }
//            }
//
//        }


    }

    public int getItemCount() {
        return offersList.size();
    }

    class Vholder extends RecyclerView.ViewHolder {
        Button project_name;

        Vholder(View itemView) {
            super(itemView);
            project_name = itemView.findViewById(R.id.projectName);
        }
    }

}