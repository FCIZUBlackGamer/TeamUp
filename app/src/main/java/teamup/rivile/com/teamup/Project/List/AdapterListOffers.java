package teamup.rivile.com.teamup.Project.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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
import teamup.rivile.com.teamup.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.Project.Details.FragmentOfferDetails;
import teamup.rivile.com.teamup.Project.join.FragmentJoinHome;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.LikeModel;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDetailsDataBase;

public class AdapterListOffers extends RecyclerView.Adapter<AdapterListOffers.Vholder> {

    private Helper mHelper;

    private Context context;
    private List<Offers> offersList;
    private FragmentManager fragmentManager;
    private Realm realm;
    private List<LikeModelDataBase> likeModelDataBase;
    private int ty;
    private int userId;

    public AdapterListOffers(Context context, List<Offers> talabats, List<LikeModelDataBase> likeModel, int type, Helper helper) {
        this.context = context;
        this.offersList = talabats;
        ty = type;
        likeModelDataBase = likeModel;
        mHelper = helper;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_project, parent, false);
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            userId = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
        });
        Log.e("Internal Offer Size", offersList.size() + "");
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.deleteOrReport.setVisibility(View.VISIBLE);

        if (ty == FragmentListProjects.NORMAL) {
            holder.deleteOrReport.setImageResource(R.drawable.ic_report);

        } else if (ty == FragmentListProjects.MINE) {
            holder.deleteOrReport.setImageResource(R.drawable.ic_cancel);

        }
        holder.deleteOrReport.setOnClickListener(v -> {

            //final Bitmap bmap = ((BitmapDrawable) holder.deleteOrReport.getDrawable()).getBitmap();
            Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_cancel);
            Bitmap bmap = getBitmap(holder.deleteOrReport.getDrawable());

            final Bitmap myLogo = getBitmap(myDrawable);
            if (bmap.sameAs(myLogo)) {
                deleteOffer(offersList.get(position).getId(), position);
                notifyDataSetChanged();
            } else {
                //TODO: Action Report Here
                realm.executeTransaction(realm1 -> {
                    int userId = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
                    FragmentOfferDetails.makeReport(offersList.get(position).getId(), userId, context);
                });

            }

        });

        for (int i = 0; i < likeModelDataBase.size(); i++) {
            if (offersList.get(position).getId() == likeModelDataBase.get(i).getOfferId()) {
                holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
            }
        }

        holder.project_name.setText(offersList.get(position).getName());
        holder.num_likes.setText(offersList.get(position).getNumLiks() + "");
        holder.num_contributer.setText(offersList.get(position).getNumContributorTo() + "");
        holder.location.setText(offersList.get(position).getAddress());
        if (offersList.get(position).getDescription().length() > 500) {//Check Description length
            String newDesc = offersList.get(position).getDescription().substring(0, 500) + context.getString(R.string.seeMore);
            holder.project_desc.setText(newDesc);
        } else {
            holder.project_desc.setText(offersList.get(position).getDescription());
        }
        if (offersList.get(position).getTags() != null) {
            for (int i = 0; i < offersList.get(position).getTags().size(); i++) {
                holder.project_tag.append("#" + offersList.get(position).getTags().get(i).getName() + "\n");
            }
        }


        holder.adapter = new ContributerImages(context, offersList.get(position).getUsers());
        holder.recyclerView.setAdapter(holder.adapter);
        for (int i = 0; i < offersList.get(position).getUsers().size(); i++) {
            if (offersList.get(position).getUserId().equals(offersList.get(position).getUsers().get(i).getId())) {
                Log.i("ImageState", "Yes");
                String imageUrl = offersList.get(position).getUsers().get(i).getImage();
                if (imageUrl != null && !imageUrl.isEmpty())
                    Picasso.get().load(imageUrl).into(holder.image);
            }
        }


        holder.image.setOnClickListener(v -> {
            /** Move To Profile fragment */
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, FragmentProfileHome.setId(offersList.get(position).getUserId())).addToBackStack(FragmentProfileHome.class.getSimpleName()).commit();
        });

        holder.linearLayout.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.frame,
                        FragmentOfferDetails.setProjectId(offersList.get(position).getId(), ty, position))
                .addToBackStack(FragmentOfferDetails.class.getSimpleName()).commit());

        holder.con.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.frame,
                        FragmentOfferDetails.setProjectId(offersList.get(position).getId(), ty, position))
                .addToBackStack(FragmentOfferDetails.class.getSimpleName()).commit());

        holder.project_desc.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.frame,
                        FragmentOfferDetails.setProjectId(offersList.get(position).getId(), ty, position))
                .addToBackStack(FragmentOfferDetails.class.getSimpleName()).commit());

        if (ty != 1 && ty != 2)
            holder.make_offer.setOnClickListener(v -> fragmentManager.beginTransaction()
                    .replace(R.id.frame,
                            FragmentJoinHome.setOfferId(offersList.get(position).getId()))
                    .addToBackStack(FragmentJoinHome.class.getSimpleName()).commit());
        else holder.make_offer.setEnabled(false);

        realm.executeTransaction(realm1 -> {
            RealmList<LikeModelDataBase> Likes = realm1.where(LoginDataBase.class).findFirst().getLikes();
            for (int i = 0; i < Likes.size(); i++) {
                if (Likes.get(i).getOfferId() == offersList.get(position).getId()) {
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_like, 0);

                }
            }
        });

        holder.like.setOnClickListener(v -> {
            Drawable likeDrawable = holder.like.getCompoundDrawables()[2]; //right drawable
            if (likeDrawable.getConstantState()
                    .equals(context
                            .getResources()
                            .getDrawable(R.drawable.ic_like)
                            .getConstantState())) {
                holder.like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_favorite_border_black_24dp, 0);
                likeOffer(offersList.get(position).getId(), userId, 1, holder.num_likes);
            } else if (likeDrawable.getConstantState()
                    .equals(context
                            .getResources()
                            .getDrawable(R.drawable.ic_favorite_border_black_24dp)
                            .getConstantState())) {
                holder.like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_like, 0);
                likeOffer(offersList.get(position).getId(), userId, 0, holder.num_likes);
            }

        });

        //TODO: get project URL and send it here
        holder.share.setOnClickListener(v -> mHelper.shareUrl("", offersList.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    class Vholder extends RecyclerView.ViewHolder {
        TextView project_name, location, project_desc, project_tag;
        TextView num_likes, num_contributer;
        CircleImageView image;
        LinearLayout linearLayout, con;
        TextView like, share, make_offer;
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        ImageView deleteOrReport;

        Vholder(View itemView) {
            super(itemView);
            project_name = itemView.findViewById(R.id.project_name);
            project_tag = itemView.findViewById(R.id.project_tag);
            deleteOrReport = itemView.findViewById(R.id.tv_delete_report);
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

    Bitmap getBitmap(Drawable drawable){
        try {
            Bitmap bitmap;

            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            // Handle the error
            return null;
        }
    }

    public void likeOffer(int offerId, int userId, int like, TextView likeHolder) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);

        ApiConfig getOffers = appConfig.getRetrofit().create(ApiConfig.class);
        Call<String> call;
        LikeModel likeModel = new LikeModel();
        likeModel.setOfferId(offerId);
        likeModel.setUserId(userId);
        likeModel.setStatus(like);
        Gson gson = new Gson();
        Log.e("Like Model", gson.toJson(likeModel));
        call = getOffers.likeOffer(gson.toJson(likeModel), API.URL_TOKEN);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String Offers = response.body();
                Log.e("Like", Offers);
                if (Offers.equals("Success")) {
                    if (like == 1) {//dislike
                        int ll= Integer.parseInt(likeHolder.getText().toString());
                        likeHolder.setText(String.valueOf(ll - 1));
                        realm.executeTransaction(realm1 -> {
                            if (realm1.where(LoginDataBase.class).findFirst().getLikes() != null && realm1.where(LoginDataBase.class).findFirst().getLikes().size() > 0) {
                                RealmResults<LikeModelDataBase> l = realm1.where(LoginDataBase.class).findFirst().getLikes().where().equalTo("OfferId", offerId).findAll();
                                for (int i = 0; i < l.size(); i++) {
                                    Log.v("IdR", l.get(i).getId() + "");
                                    Log.v("IdRR", l.get(i).getOfferId() + "");
                                    Log.v("IdRRR", l.get(i).getUserId() + "");
                                    Log.v("IdRRRR", l.get(i).getStatus() + "");
                                }

                                l.deleteAllFromRealm();
//                    realm1.commitTransaction();
                            } else {
                                Log.v("Status", "Not Found");
                            }
                        });
                    } else {//like
                        int ll= Integer.parseInt(likeHolder.getText().toString());
                        likeHolder.setText(String.valueOf(ll + 1));
                        realm.executeTransaction(realm1 -> {
                            realm1.where(LoginDataBase.class).findFirst().addLikes(offerId, userId);
                            //realm1.commitTransaction();
                        });
                    }
                } else {

                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }

    public void deleteOffer(int offerId, int position) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);

        Log.e("OfferId", offerId + "");
        ApiConfig getOffers = appConfig.getRetrofit().create(ApiConfig.class);
        Call<String> call = getOffers.deleteOffer(offerId, API.URL_TOKEN);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String Offers = response.body();
                if (Offers.equals("Success")) {
                    realm.beginTransaction();
                    OfferDetailsDataBase l = realm.where(LoginDataBase.class).findFirst().getOffers().where().equalTo("Id", offerId).findFirst();
                    l.deleteFromRealm();
                    realm.commitTransaction();
                    offersList.remove(position);
                    notifyDataSetChanged();
                } else {
                    Log.e("Er", Offers);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Erro", t.getMessage());
            }
        });
    }

}