package teamup.rivile.com.teamup.ui.Project.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.ui.Profile.FragmentProfileHome;
import teamup.rivile.com.teamup.ui.Project.Add.FragmentAddHome;
import teamup.rivile.com.teamup.ui.Project.Details.FragmentOfferDetails;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.FavouriteModel;
import teamup.rivile.com.teamup.Uitls.APIModels.LikeModel;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.FavouriteDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDataBase;

public class AdapterListOffers extends RecyclerView.Adapter<AdapterListOffers.Vholder> {

    private Helper mHelper;
    private char SPACE = ' ';

    private Context context;
    private List<Offers> offersList;
    private FragmentManager fragmentManager;
    private Realm realm;
    private List<LikeModelDataBase> likeModelDataBase;
    private List<FavouriteDataBase> favouriteDataBases;
    private int ty;
    private int userId;
    private int userState;

    private RelativeLayout mExpandedRelativeLayout = null;
    private FloatingActionButton mHiddenLikeFAB = null;
    private FloatingActionButton mHiddenFavouriteFAB = null;
    private int mExpandedPosition = -1;

    public AdapterListOffers(Context context, List<Offers> talabats, List<LikeModelDataBase> likeModel, List<FavouriteDataBase> favouriteModel, int type, Helper helper) {
        this.context = context;
        this.offersList = talabats;
        ty = type;
        likeModelDataBase = likeModel;
        favouriteDataBases = favouriteModel;
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
            userState = realm1.where(LoginDataBase.class).findFirst().getUser().getStatus();
        });
        Log.e("Internal Offer Size", offersList.size() + "");
        return new Vholder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        Offers offers = offersList.get(position);
        holder.fromToTextView.setText(offers.getNumContributor() + " Of " + offers.getNumJoinOffer());

        holder.option_menu.setVisibility(View.VISIBLE);

        holder.tv_proType.setVisibility(View.GONE);
        holder.favouriteFAB.setOnClickListener(v -> {
            Drawable favDrawable = holder.favouriteFAB.getDrawable();
            if (favDrawable.getConstantState()
                    .equals(context
                            .getResources()
                            .getDrawable(R.drawable.ic_star_full)
                            .getConstantState())) {
                holder.favouriteFAB.setImageResource(R.drawable.ic_star_empty);
                markFavourite(offersList.get(position).getId(), userId, 1);
                if (ty == FragmentListProjects.FAVOURITE) {
                    offersList.remove(position);
                    notifyDataSetChanged();
                    if (offersList.size() == 0) {

                        FragmentListProjects.showEmpty();

                    }
                }
            } else if (favDrawable.getConstantState()
                    .equals(context
                            .getResources()
                            .getDrawable(R.drawable.ic_star_empty)
                            .getConstantState())) {
                holder.favouriteFAB.setImageResource(R.drawable.ic_star_full);
                markFavourite(offersList.get(position).getId(), userId, 0);
            }

        });

        holder.option_menu.setOnClickListener(v -> {
            if (ty == FragmentListProjects.NORMAL || ty == FragmentListProjects.FAVOURITE) {
                showNormalMenu(holder.option_menu, position);

            } else if (ty == FragmentListProjects.MINE) {
                showMyMenu(holder.option_menu, position);
            }
        });

        for (int i = 0; i < likeModelDataBase.size(); i++) {
            if (offersList.get(position).getId() == likeModelDataBase.get(i).getOfferId()) {
                holder.like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_like, 0);
                holder.likeFab.setImageResource(R.drawable.ic_like);
            }
        }

        for (int i = 0; i < favouriteDataBases.size(); i++) {
            if (offersList.get(position).getId() == favouriteDataBases.get(i).getOfferId()) {
                holder.favouriteFAB.setImageResource(R.drawable.ic_star_full);
                holder.favouriteFAB.setTag(1);
            } else {
                holder.favouriteFAB.setTag(0);
            }
        }

//        holder.money.setVisibility(View.GONE);
        holder.project_name.setText(offersList.get(position).getName());
        holder.tv_initialCost.setText(
                String.valueOf(offersList.get(position).getInitialCost())
//                        + SPACE
//                        + context.getResources().getString(R.string.per)
//                        + SPACE
//                        + String.valueOf(offersList.get(position).getInitialCostType())
        );
        holder.tv_directCost.setText(
                String.valueOf(offersList.get(position).getDirectExpenses())
//                        + SPACE
//                        + context.getResources().getString(R.string.per)
//                        + SPACE
//                        + checkString(offersList.get(position).getDirectExpensesType())
        );
        holder.tv_inDirectCost.setText(
                String.valueOf(offersList.get(position).getIndectExpenses())
//                        + SPACE
//                        + context.getResources().getString(R.string.per)
//                        + SPACE
//                        + checkString(offersList.get(position).getIndectExpensesType())
        );
        holder.tv_duration.setText(
                String.valueOf(offersList.get(position).getProjectDuration())
//                        + SPACE
//                        + checkString(offersList.get(position).getProjectDurationType())
        );
        holder.tv_profit.setText(
                String.valueOf(offersList.get(position).getProfitMoney())
//                        + SPACE
//                        + context.getResources().getString(R.string.per)
//                        + SPACE
//                        + checkString(offersList.get(position).getProfitType())
        );
        holder.tv_totalCost.setText(
                String.valueOf(
                        offersList.get(position).getInitialCost() +
                                offersList.get(position).getDirectExpenses() +
                                offersList.get(position).getIndectExpenses()
                )
        );

//        holder.project_desc.setOnClickListener(v -> {
//            if (holder.money.getVisibility() == View.VISIBLE) {
//                holder.money.setVisibility(View.GONE);
//            } else {
//                holder.money.setVisibility(View.VISIBLE);
//            }
//        });

        holder.num_likes.setText(offersList.get(position).getNumLiks() + "");
        holder.num_contributer.setText(offersList.get(position).getNumContributor() + "");
//        if (offers != null) {
//            List<UserModel> userModels = offers.getUsers();
//            if (userModels != null && userModels.size() > 0) {
//                UserModel userModel = userModels.get(0);
//                if (userModel != null) {
//                    holder.fromToTextView.setText(userModel.getFullName());
//                }
//            }
//        }

        if (offersList.get(position).getProjectType() == 0) {
            holder.tv_proType.setText(context.getResources().getString(R.string.always));
        } else if (offersList.get(position).getProjectType() == 1) {//perioud
            holder.tv_proType.setText(context.getResources().getString(R.string.perioud));
        } else if (offersList.get(position).getProjectType() == 2) {
            holder.tv_proType.setText(context.getResources().getString(R.string.jsutOnce));
        }

        if (offersList.get(position).getDescription().length() > 500) {//Check Description length
            String newDesc = offersList.get(position).getDescription().substring(0, 500) + context.getString(R.string.seeMore);
            holder.project_desc.setText(newDesc);
        } else {
            holder.project_desc.setText(offersList.get(position).getDescription());
        }
        if (offersList.get(position).getTags() != null) {
            for (int i = 0; i < offersList.get(position).getTags().size(); i++) {
                if (holder.project_tag.getText().length() < 500) {
                    holder.project_tag.append("#" + offersList.get(position).getTags().get(i).getName() + "\n");
                } else {
                    holder.project_tag.append(context.getString(R.string.seeMore));
                    return;
                }
            }

        }

//        holder.adapter = new ContributerImages(context, offersList.get(position).getUsers(), holder.emptyView);
//        holder.recyclerView.setAdapter(holder.adapter);
        for (int i = 0; i < offersList.get(position).getUsers().size(); i++) {
            Log.e("UID", offersList.get(position).getUserId() + "");
            Log.e("OID", offersList.get(position).getUsers().get(i).getId() + "");
            Gson gson = new Gson();
            Log.e("GD", gson.toJson(offersList.get(position).getUsers().get(i)));
            Log.e("SGD", gson.toJson(offersList.get(position).getUsers()));
            if (offersList.get(position).getUserId().equals(offersList.get(position).getUsers().get(i).getId())) {

                String imageUrl = offersList.get(position).getUsers().get(i).getImage();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Log.i("ImageState", "YYY");
                    Log.i("ImageState", offersList.get(position).getUsers().get(i).getImage());
                    if (offersList.get(position).getUsers().get(i).getImage().startsWith("http")) {
                        Picasso.get().load(offersList.get(position).getUsers().get(i).getImage()).into(holder.image);
                    } else {
                        Picasso.get().load(API.BASE_URL + offersList.get(position).getUsers().get(i).getImage()).into(holder.image);
                    }
//                    holder.image_name.setVisibility(View.GONE);
                } else {
                    Log.i("ImageState", "NNN");
//                    holder.image_name.setVisibility(View.VISIBLE);
//                    String[] sp = offersList.get(position).getUsers().get(i).getFullName().split(" ");
//                    if (!offersList.get(position).getUsers().get(i).getFullName().contains(" ")) {
//                        holder.image_name.setText(offersList.get(position).getUsers().get(i).getFullName().charAt(0) + "");
//                    } else if (sp.length > 0 && sp.length <= 2) {
//                        for (int j = 0; j < sp.length; j++) {
//                            holder.image_name.append(sp[j] + "");
//                        }
//                    } else if (sp.length > 2) {
//                        for (int j = 0; j < 2; j++) {
//                            holder.image_name.append(sp[j] + "");
//                        }
//                    }
                }
            } else {
                Log.i("ImageState", "No");
            }
        }

        holder.image.setOnClickListener(v -> {
            /** Move To Profile fragment */
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, FragmentProfileHome.setId(offersList.get(position).getUserId())).addToBackStack(FragmentProfileHome.class.getSimpleName()).commit();
        });

        if (ty != 1 && ty != 2)
            holder.make_offer.setOnClickListener(v -> {
                //TODO: Show user alert dialog of what's happening here
                Toast.makeText(context, "هنتواصل معاك يابو عمو", Toast.LENGTH_SHORT).show();
            });
        else holder.make_offer.setEnabled(false);

        realm.executeTransaction(realm1 -> {
            RealmList<LikeModelDataBase> Likes = realm1.where(LoginDataBase.class).findFirst().getLikes();
            for (int i = 0; i < Likes.size(); i++) {
                if (Likes.get(i).getOfferId() == offersList.get(position).getId()) {
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_like, 0);
                    holder.likeFab.setImageResource(R.drawable.ic_like);
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
                holder.likeFab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                likeOffer(offersList.get(position).getId(), userId, 1, holder.num_likes);
            } else if (likeDrawable.getConstantState()
                    .equals(context
                            .getResources()
                            .getDrawable(R.drawable.ic_favorite_border_black_24dp)
                            .getConstantState())) {
                holder.like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_like, 0);
                holder.likeFab.setImageResource(R.drawable.ic_like);
                likeOffer(offersList.get(position).getId(), userId, 0, holder.num_likes);
            }
        });

        //TODO: get project URL and send it here
        holder.share.setOnClickListener(v -> mHelper.shareUrl("", offersList.get(position).getName()));


        holder.likeFab.setOnClickListener(v -> holder.like.callOnClick());

        if (position == mExpandedPosition) {
            holder.detailsRelativeLayout.setVisibility(View.VISIBLE);

            holder.favouriteFAB.hide();
            holder.likeFab.hide();
        } else {
            holder.detailsRelativeLayout.setVisibility(View.GONE);

            holder.favouriteFAB.show();
            holder.likeFab.show();
        }

        holder.projectRelativeLayout.setOnClickListener(v -> {
            if (holder.detailsRelativeLayout.getVisibility() == View.VISIBLE) {
                holder.favouriteFAB.show();
                holder.likeFab.show();

                holder.detailsRelativeLayout.setVisibility(View.GONE);
                mHiddenLikeFAB.show();
                mHiddenFavouriteFAB.show();

                mExpandedRelativeLayout = null;
                mHiddenFavouriteFAB = null;
                mHiddenLikeFAB = null;
                mExpandedPosition = -1;
            } else {
                holder.favouriteFAB.hide();
                holder.likeFab.hide();

                holder.detailsRelativeLayout.setVisibility(View.VISIBLE);

                if (mExpandedRelativeLayout != null) {
                    mExpandedRelativeLayout.setVisibility(View.GONE);

                    mHiddenLikeFAB.show();
                    mHiddenFavouriteFAB.show();
                }

                mExpandedRelativeLayout = holder.detailsRelativeLayout;
                mHiddenLikeFAB =  holder.likeFab;
                mHiddenFavouriteFAB =  holder.favouriteFAB;
                mExpandedPosition = position;
            }
        });
    }

    class Vholder extends RecyclerView.ViewHolder {
        TextView project_name, fromToTextView, project_desc, project_tag, tv_proType;
        TextView num_likes, num_contributer;
        TextView tv_initialCost, tv_directCost, tv_inDirectCost, tv_duration, tv_totalCost, tv_profit;
        ImageView image;
        LinearLayout linearLayout, con;
        TextView like, share, make_offer, image_name;
        //        RecyclerView recyclerView;
//        RecyclerView.Adapter adapter;
        ImageView option_menu;
        FloatingActionButton favouriteFAB, likeFab;
        TextView emptyView;
        RelativeLayout money;

        ConstraintLayout projectRelativeLayout;
        RelativeLayout detailsRelativeLayout;

        Vholder(View itemView) {
            super(itemView);
            project_name = itemView.findViewById(R.id.project_name);
            project_tag = itemView.findViewById(R.id.project_tag);
            option_menu = itemView.findViewById(R.id.tv_options);
            tv_initialCost = itemView.findViewById(R.id.tv_initialCost);
            tv_directCost = itemView.findViewById(R.id.tv_directCost);
            tv_inDirectCost = itemView.findViewById(R.id.tv_inDirectCost);
            tv_duration = itemView.findViewById(R.id.tv_duration);
            tv_totalCost = itemView.findViewById(R.id.tv_totalCost);
            tv_proType = itemView.findViewById(R.id.tv_proType);
            tv_profit = itemView.findViewById(R.id.tv_profit);

            favouriteFAB = itemView.findViewById(R.id.fab_favourite);
            likeFab = itemView.findViewById(R.id.fab_like);

            project_desc = itemView.findViewById(R.id.project_desc);
            fromToTextView = itemView.findViewById(R.id.tv_from_to);
            image = itemView.findViewById(R.id.user_image);
            image_name = itemView.findViewById(R.id.image_name);
            linearLayout = itemView.findViewById(R.id.lin);
            con = itemView.findViewById(R.id.con);
            like = itemView.findViewById(R.id.like);
            share = itemView.findViewById(R.id.share);
            make_offer = itemView.findViewById(R.id.make_offer);
            num_contributer = itemView.findViewById(R.id.num_contributer);
            num_likes = itemView.findViewById(R.id.num_likes);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//            recyclerView = itemView.findViewById(R.id.rec);
//            recyclerView.setLayoutManager(layoutManager);
            money = itemView.findViewById(R.id.money);
            emptyView = itemView.findViewById(R.id.tv_empty_view);

            projectRelativeLayout = itemView.findViewById(R.id.rl_project);
            detailsRelativeLayout = itemView.findViewById(R.id.rl_details);
        }
    }

    private void showNormalMenu(ImageView op, int position) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, op);
        //inflating menu from xml resource
        popup.inflate(R.menu.normal_offer_option);
        //adding click listener
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
//                case R.id.action_add_to_favourite:
//                    //handle action_add_to_favourite click
//                    if (item.getIcon()
//                            .getConstantState()
//                            .equals(context.getResources()
//                                    .getDrawable(R.drawable.ic_star_empty)
//                                    .getConstantState())) {
//                        item.setIcon(R.drawable.ic_star_full);
//                        markFavourite(offersList.get(position).getId(), userId, 0);
//                    } else {
//                        item.setIcon(R.drawable.ic_star_empty);
//                        markFavourite(offersList.get(position).getId(), userId, 1);
//                    }
//                    break;
                case R.id.action_alert:
                    //handle action_alert click
                    reportOrDelete(item, position);
                    break;
            }
            return false;
        });
        //displaying the popup
        popup.show();
    }

    private void showMyMenu(ImageView op, int position) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, op);
        //inflating menu from xml resource
        popup.inflate(R.menu.my_offer_option_list);
        //adding click listener
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    //handle action_edit click
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, FragmentAddHome.openForEdit(offersList.get(position).getId(), null))
                            .addToBackStack(AdapterListOffers.class.getName())
                            .commit();
                    break;
                case R.id.action_delete:
                    //handle action_delete click
                    reportOrDelete(item, position);
                    break;
            }
            return false;
        });
        //displaying the popup
        popup.show();
    }

    private void reportOrDelete(MenuItem op, int position) {
        if (op.getIcon()
                .getConstantState()
                .equals(context.getResources()
                        .getDrawable(R.drawable.ic_cancel)
                        .getConstantState())) {//means delete action
            deleteOffer(offersList.get(position).getId(), position);
            notifyDataSetChanged();
        } else {//make report
            //TODO: Action Report Here
            realm.executeTransaction(realm1 -> {
                int userId = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
                FragmentOfferDetails.makeReport(offersList.get(position).getId(), userId, context);
            });

        }
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public interface Helper {
        void shareUrl(String url, String projectName);
    }

    Bitmap getBitmap(Drawable drawable) {
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
        Log.e("LikeModel", gson.toJson(likeModel));
        call = getOffers.likeOffer(gson.toJson(likeModel), API.URL_TOKEN);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String Offers = response.body();
//                Log.e("Like", Offers);
                if (Offers.equals("Success")) {
                    if (like == 1) {//dislike
                        int ll = Integer.parseInt(likeHolder.getText().toString());
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
                        int ll = Integer.parseInt(likeHolder.getText().toString());
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

    public void markFavourite(int offerId, int userId, int fav) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);

        ApiConfig getOffers = appConfig.getRetrofit().create(ApiConfig.class);
        Call<String> call;
        FavouriteModel favouriteModel = new FavouriteModel();
        favouriteModel.setOfferId(offerId);
        favouriteModel.setUserId(userId);
        favouriteModel.setStatus(fav);
        Gson gson = new Gson();
        Log.e("FavModel", gson.toJson(favouriteModel));
        call = getOffers.favouriteOffer(gson.toJson(favouriteModel), API.URL_TOKEN);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String Offers = response.body();
//                Log.e("Like", Offers);
                if (Offers.equals("Success")) {
                    if (fav == 1) {//remove favouriteFAB
                        realm.executeTransaction(realm1 -> {
                            if (realm1.where(LoginDataBase.class).findFirst().getFavorites() != null && realm1.where(LoginDataBase.class).findFirst().getFavorites().size() > 0) {
                                RealmResults<FavouriteDataBase> l = realm1.where(LoginDataBase.class).findFirst().getFavorites().where().equalTo("OfferId", offerId).findAll();
                                for (int i = 0; i < l.size(); i++) {
                                    Log.v("IdR", l.get(i).getId() + "");
                                    Log.v("IdRR", l.get(i).getOfferId() + "");
                                    Log.v("IdRRR", l.get(i).getUserId() + "");
                                }

                                l.deleteAllFromRealm();
                                Log.i("Fav", "rem");
//                    realm1.commitTransaction();
                            } else {
                                Log.v("Status", "Not Found");
                            }
                        });
                    } else {//mark favouriteFAB
                        realm.executeTransaction(realm1 -> {
                            realm1.where(LoginDataBase.class).findFirst().addFavuriteOffer(offerId, userId);
                            Log.i("Fav", "Done");
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
                    OfferDataBase l = realm.where(LoginDataBase.class).findFirst().getOffers().where().equalTo("Id", offerId).findFirst();
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

    private String checkString(int num) {
        if (num == 0) {
            return context.getResources().getString(R.string.hour);
        } else if (num == 1) {//perioud
            return context.getResources().getString(R.string.daily);
        } else if (num == 2) {
            return context.getResources().getString(R.string.monthly);
        } else if (num == 4) {//perioud
            return context.getResources().getString(R.string.yearly);
        } else if (num == 5) {
            return context.getResources().getString(R.string.onceTime);
        } else {
            return null;
        }
    }

    public void sort(int type) {
        if (offersList != null && offersList.size() != 0) {
            switch (type) {
                case 0: //Sort by date ASC
                    Collections.sort(offersList, (o1, o2) -> o1.getStringDate().compareTo(o2.getStringDate()));
                    break;
                case 1: //Sort by date DESC
                    Collections.sort(offersList, (o1, o2) -> o2.getStringDate().compareTo(o1.getStringDate()));
                    break;
                case 2: //Sort by required number of contributors ASC
                    Collections.sort(offersList, (o1, o2) -> o1.getNumContributor().compareTo(o2.getNumContributor()));
                    break;
                case 3: //Sort by required number of contributors DESC
                    Collections.sort(offersList, (o1, o2) -> o2.getNumContributor().compareTo(o1.getNumContributor()));
                    break;
                case 4: //Sort by number of contributors joined ASC
                    Collections.sort(offersList, (o1, o2) -> o1.getNumJoinOffer().compareTo(o2.getNumJoinOffer()));
                    break;
                case 5: //Sort by number of contributors joined ASC
                    Collections.sort(offersList, (o1, o2) -> o2.getNumJoinOffer().compareTo(o1.getNumJoinOffer()));
                    break;
            }

            notifyDataSetChanged();
        }
    }
}