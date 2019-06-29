package teamup.rivile.com.teamup.ui.Profile;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.FavouriteDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.JoinedProjectRealmObject;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.model.OfferDataBase;
import teamup.rivile.com.teamup.network.APIModels.FavouriteModel;
import teamup.rivile.com.teamup.network.APIModels.LikeModel;
import teamup.rivile.com.teamup.network.APIModels.Offers;
import teamup.rivile.com.teamup.network.APIModels.ReportModel;
import teamup.rivile.com.teamup.network.repository.AppNetworkRepository;
import teamup.rivile.com.teamup.ui.Project.Add.FragmentAddHome;
import teamup.rivile.com.teamup.ui.Project.List.FragmentListProjects;
import teamup.rivile.com.teamup.ui.Project.List.JoinedPeopleAdapter;

import static teamup.rivile.com.teamup.APIS.API.BASE_URL;
import static teamup.rivile.com.teamup.ui.Project.List.FragmentListProjects.FAVOURITE;
import static teamup.rivile.com.teamup.ui.Project.List.FragmentListProjects.MINE;

public class AdapterListOffers extends RecyclerView.Adapter<AdapterListOffers.Vholder> {
    private Application mApplication;
    private List<Offers> offersList;
    private FragmentManager fragmentManager;
    private Realm realm;
    private ArrayList<LikeModelDataBase> likeModelDataBase = new ArrayList<>();
    private ArrayList<FavouriteDataBase> favouriteDataBases = new ArrayList<>();
    private int ty = 1; //mine
    private int userId;

    private RelativeLayout mExpandedRelativeLayout = null;
    private ImageView mHiddenLikeFAB = null;
    private ImageView mHiddenFavouriteFAB = null;
    private int mExpandedPosition = -1;
    private Fragment mParentFragment;
    private AppNetworkRepository mNetworkRepository;

    private Context mContext;

    public AdapterListOffers(Application application, List<Offers> talabats, FragmentProfileHome fragmentProfileHome, Context context) {
        this.mApplication = application;
        this.offersList = talabats;
        mContext = context;

        realm = Realm.getDefaultInstance();
        likeModelDataBase.addAll(realm.where(LikeModelDataBase.class).findAll());
        favouriteDataBases.addAll(realm.where(FavouriteDataBase.class).findAll());

        mParentFragment = fragmentProfileHome;
        mNetworkRepository = AppNetworkRepository.getInstance(mApplication);
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_project, parent, false);
        fragmentManager = mParentFragment.getFragmentManager();
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            userId = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
        });
        Log.e("Internal Offer Size", offersList.size() + "");
        return new Vholder(view);
    }

    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        Offers offer = offersList.get(position);

        holder.fromToTextView.setText(offer.getNumContributor() + " Of " + offer.getNumJoinOffer());

        holder.option_menu.setVisibility(View.VISIBLE);

        holder.tv_proType.setVisibility(View.GONE);
        holder.favouriteImageView.setOnClickListener(v -> {
            boolean isFav = false;
            for(FavouriteDataBase dataBase : favouriteDataBases){
                if(dataBase.getOfferId() == offer.getId()){
                    holder.favouriteImageView.setImageResource(R.drawable.ic_star_empty);
                    markFavourite(offersList.get(position).getId(), userId, 1);
                    if (ty == FAVOURITE) {
                        offersList.remove(position);
                        notifyDataSetChanged();
                        if (offersList.size() == 0) {
                            FragmentListProjects.showEmptyView();
                        } else {
                            FragmentListProjects.hideEmptyView();
                        }
                    }
                    isFav = true;
                }
            }

            if (!isFav){
                holder.favouriteImageView.setImageResource(R.drawable.ic_star_full);
                markFavourite(offersList.get(position).getId(), userId, 0);
            }
        });


        holder.option_menu.setOnClickListener(v -> {
            if (ty == FragmentListProjects.NORMAL || ty == FAVOURITE) {
                showNormalMenu(holder.option_menu, position);

            } else if (ty == MINE) {
                showMyMenu(holder.option_menu, position);
            }
        });

        for (int i = 0; i < likeModelDataBase.size(); i++) {
            if (offersList.get(position).getId() == likeModelDataBase.get(i).getOfferId()) {
                holder.like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_like, 0);
                holder.likeImageView.setImageResource(R.drawable.ic_like);
            }
        }

        for (int i = 0; i < favouriteDataBases.size(); i++) {
            if (offersList.get(position).getId() == favouriteDataBases.get(i).getOfferId()) {
                holder.favouriteImageView.setImageResource(R.drawable.ic_star_full);
                holder.favouriteImageView.setTag(1);
            } else {
                holder.favouriteImageView.setTag(0);
            }
        }

//        holder.money.setVisibility(View.GONE);
        holder.project_name.setText(offersList.get(position).getName());
        holder.tv_initialCost.setText(
                String.valueOf(offersList.get(position).getInitialCost())
//                        + SPACE
//                        + mApplication.getResources().getString(R.string.per)
//                        + SPACE
//                        + String.valueOf(offersList.get(position).getInitialCostType())
        );
        holder.tv_directCost.setText(
                String.valueOf(offersList.get(position).getDirectExpenses())
//                        + SPACE
//                        + mApplication.getResources().getString(R.string.per)
//                        + SPACE
//                        + checkString(offersList.get(position).getDirectExpensesType())
        );
        holder.tv_inDirectCost.setText(
                String.valueOf(offersList.get(position).getIndectExpenses())
//                        + SPACE
//                        + mApplication.getResources().getString(R.string.per)
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
//                        + mApplication.getResources().getString(R.string.per)
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
//        if (offer != null) {
//            List<UserModel> userModels = offer.getUsers();
//            if (userModels != null && userModels.size() > 0) {
//                UserModel userModel = userModels.get(0);
//                if (userModel != null) {
//                    holder.fromToTextView.setText(userModel.getFullName());
//                }
//            }
//        }

//        if (offersList.get(position).getProjectType() == 0) {
//            holder.tv_proType.setText(mApplication.getResources().getString(R.string.always));
//        } else if (offersList.get(position).getProjectType() == 1) {//perioud
//            holder.tv_proType.setText(mApplication.getResources().getString(R.string.perioud));
//        } else if (offersList.get(position).getProjectType() == 2) {
//            holder.tv_proType.setText(mApplication.getResources().getString(R.string.jsutOnce));
//        }

        if (offersList.get(position).getDescription().length() > 500) {//Check Description length
            String newDesc = offersList.get(position).getDescription().substring(0, 500) + mApplication.getString(R.string.seeMore);
            holder.project_desc.setText(newDesc);
        } else {
            holder.project_desc.setText(offersList.get(position).getDescription());
        }
        if (offersList.get(position).getTags() != null) {
            for (int i = 0; i < offersList.get(position).getTags().size(); i++) {
                if (holder.project_tag.getText().length() < 500) {
                    holder.project_tag.append("#" + offersList.get(position).getTags().get(i).getName() + "\n");
                } else {
                    holder.project_tag.append(mApplication.getString(R.string.seeMore));
                    return;
                }
            }

        }

//        holder.adapter = new ContributerImages(mApplication, offersList.get(position).getUsers(), holder.emptyView);
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
                        Picasso.get().load(BASE_URL + offersList.get(position).getUsers().get(i).getImage()).into(holder.image);
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

        if (ty != MINE && ty != FAVOURITE) {
            if (offer.getUserId().equals(userId)) {
                holder.make_offer.setEnabled(false);
            } else {
                holder.make_offer.setOnClickListener(v -> {
                    v.setEnabled(false);

                    //TODO: Show user alert dialog of what's happening here
                    mNetworkRepository.joinOffer(userId, offer.getId())
                            .observe(mParentFragment, serverResponse -> {
                                if (serverResponse != null) {
                                    if (serverResponse.equals("Success")) {
                                        Toast.makeText(mApplication, "هنتواصل معاك يابو عمو", Toast.LENGTH_SHORT).show();
                                        realm.executeTransaction(realm1 ->
                                        {

                                            JoinedProjectRealmObject joinedProject = null;
                                            try {
                                                joinedProject =
                                                        realm.where(JoinedProjectRealmObject.class)
                                                                .equalTo("OfferId", offer.getId())
                                                                .findAll().last();
                                            } catch (Exception ignored) {
                                            }

                                            if (joinedProject == null) {
                                                realm1.insertOrUpdate(new JoinedProjectRealmObject(
                                                        offer.getId(),
                                                        offer.getName(),
                                                        userId,
                                                        API.JoinRequestResponse.STATUS_ON_HOLD,
                                                        null, null
                                                ));
                                            } else {
                                                joinedProject.setStatus(0);
                                                realm1.insertOrUpdate(joinedProject);
                                            }
                                        });
                                    } else if (serverResponse.equals("Fail")) {
                                        Toast.makeText(mApplication, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(mApplication, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                });

                try {
                    JoinedProjectRealmObject joinedProject =
                            realm.where(JoinedProjectRealmObject.class)
                                    .equalTo("OfferId", offer.getId())
                                    .findAll().last();

                    if (joinedProject != null) {
                        if (joinedProject.getStatus() == API.JoinRequestResponse.STATUS_ON_HOLD) {
//                            holder.make_offer.setEnabled(false);
                            holder.make_offer.setOnClickListener(v -> {
                                Toast.makeText(mApplication, mApplication.getString(R.string.request_on_hold), Toast.LENGTH_LONG).show();
                            });
                        } else if (joinedProject.getStatus() == API.JoinRequestResponse.STATUS_ACCEPT) {
//                            holder.make_offer.setEnabled(false);
                            holder.make_offer.setOnClickListener(v -> {
                                Toast.makeText(mApplication, mApplication.getString(R.string.request_accepted), Toast.LENGTH_LONG).show();
                            });
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        } else if (ty == MINE) {
            holder.make_offer.setOnClickListener(v -> {
                //TODO: Show alertDialog listing joined people
                //with the ability to accept, decline or block them

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view = LayoutInflater.from(mContext).inflate(R.layout.popup_see_joined_people, null);
                builder.setView(view);

                TextView headerTextView = view.findViewById(R.id.tv_header);

                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ImageView closeImageView = view.findViewById(R.id.iv_cancel);
                closeImageView.setOnClickListener(v1 -> dialog.dismiss());

                RecyclerView joinedPeopleRecyclerView = view.findViewById(R.id.recyclerView);
                joinedPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(mApplication));

                //TODO: send list

                mNetworkRepository.getSystemRefuseReasons()
                        .observe(mParentFragment, reasonsList -> {
                            if (reasonsList != null) {
                                JoinedPeopleAdapter peopleAdapter = new JoinedPeopleAdapter(offer.getId(), reasonsList, dialog, headerTextView, mApplication, mParentFragment, mContext);
                                joinedPeopleRecyclerView.setAdapter(peopleAdapter);

                                mNetworkRepository.listUsersJoinRequests(offer.getId())
                                        .observe(mParentFragment, offerDetailsJsonObject -> {
                                            if (offerDetailsJsonObject != null) {
                                                peopleAdapter.swapData(offerDetailsJsonObject, true);

                                                dialog.show();
                                            }
                                        });
                            }
                        });
            });
        }

        realm.executeTransaction(realm1 -> {
            RealmList<LikeModelDataBase> Likes = realm1.where(LoginDataBase.class).findFirst().getLikes();
            for (int i = 0; i < Likes.size(); i++) {
                if (Likes.get(i).getOfferId() == offersList.get(position).getId()) {
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_like, 0);
                    holder.likeImageView.setImageResource(R.drawable.ic_like);
                }
            }
        });

        holder.like.setOnClickListener(v -> {
            boolean isLike = false;
            for(LikeModelDataBase dataBase : likeModelDataBase){
                if(dataBase.getOfferId() == offer.getId()){
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_favorite_border_black_24dp, 0);
                    holder.likeImageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    likeOffer(offersList.get(position).getId(), userId, 1, holder.num_likes);
                    isLike = true;
                }
            }

            if (!isLike){
                holder.like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_like, 0);
                holder.likeImageView.setImageResource(R.drawable.ic_like);
                likeOffer(offersList.get(position).getId(), userId, 0, holder.num_likes);
            }
        });

        holder.likeImageView.setOnClickListener(v -> holder.like.callOnClick());

        if (position == mExpandedPosition) {
            holder.detailsRelativeLayout.setVisibility(View.VISIBLE);

            holder.favouriteImageView.setVisibility(View.GONE);
            holder.likeImageView.setVisibility(View.GONE);
        } else {
            holder.detailsRelativeLayout.setVisibility(View.GONE);

            holder.favouriteImageView.setVisibility(View.VISIBLE);
            holder.likeImageView.setVisibility(View.VISIBLE);
        }

        holder.like.setVisibility(View.GONE);
        holder.make_offer.setVisibility(View.GONE);
        holder.share.setVisibility(View.GONE);
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
        ImageView favouriteImageView, likeImageView;
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

            favouriteImageView = itemView.findViewById(R.id.iv_favourite);
            likeImageView = itemView.findViewById(R.id.iv_like);

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
            LinearLayoutManager layoutManager = new LinearLayoutManager(mApplication, LinearLayoutManager.HORIZONTAL, false);
//            recyclerView = itemView.findViewById(R.id.rec);
//            recyclerView.setLayoutManager(layoutManager);
            money = itemView.findViewById(R.id.money);
            emptyView = itemView.findViewById(R.id.tv_empty_view);

            projectRelativeLayout = itemView.findViewById(R.id.rl_project);
            detailsRelativeLayout = itemView.findViewById(R.id.rl_details);

            if (ty == MINE) {
                make_offer.setText(mApplication.getString(R.string.see_joined_people));
                like.setVisibility(View.GONE);
            }
        }
    }

    private void showNormalMenu(ImageView op, int position) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(mApplication, op);
        //inflating menu from xml resource
        popup.inflate(R.menu.normal_offer_option);
        //adding click listener
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
//                case R.id.action_add_to_favourite:
//                    //handle action_add_to_favourite click
//                    if (item.getIcon()
//                            .getConstantState()
//                            .equals(mApplication.getResources()
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
        PopupMenu popup = new PopupMenu(mApplication, op);
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
        if (ty == MINE) {//means delete action

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(R.string.delete_project)
                    .setPositiveButton(mApplication.getString(R.string.yes), (dialog, which) -> {
                        deleteOffer(offersList.get(position).getId(), position);
                        notifyDataSetChanged();
                    }).setNegativeButton(mApplication.getString(R.string.no), (dialog, which) -> dialog.dismiss())
                    .show();

        } else {//make report
            //TODO: Action Report Here
            realm.executeTransaction(realm1 -> {
                int userId = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
                makeReport(offersList.get(position).getId(), userId, mContext);
            });

        }
    }

    private void makeReport(int projectId, int userId, @NonNull Context context) {

        View message = LayoutInflater.from(context).inflate(R.layout.popup_report, null);

        EditText message_type = message.findViewById(R.id.editText);
        FloatingActionButton confirm = message.findViewById(R.id.confirm);
        ImageView close = message.findViewById(R.id.close);


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(message);

        final AlertDialog dialog2 = builder.create();
        dialog2.show();

        close.setOnClickListener(v ->
                dialog2.dismiss()
        );
        confirm.setOnClickListener(v -> {
            if (!message_type.getText().toString().isEmpty()) {
                reportOffer(userId, projectId, message_type.getText().toString(), context);
            }
        });
    }

    private void reportOffer(int userId, int projectId, String desc, @NonNull Context context) {
        ReportModel model = new ReportModel();
        model.setOfferId(projectId);
        model.setUserId(userId);
        model.setDescripation(desc);
        Gson gson = new Gson();

        mNetworkRepository.reportOffer(gson.toJson(model))
                .observe(mParentFragment,s -> {
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                });
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

    private void likeOffer(int offerId, int userId, int like, TextView likeHolder) {

        LikeModel likeModel = new LikeModel();
        likeModel.setOfferId(offerId);
        likeModel.setUserId(userId);
        likeModel.setStatus(like);

        mNetworkRepository.likeOffer(new Gson().toJson(likeModel))
                .observe(mParentFragment, offers -> {
                    if (offers.equals("Success")) {
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
                    }
                });
    }

    private void markFavourite(int offerId, int userId, int fav) {
        FavouriteModel favouriteModel = new FavouriteModel();
        favouriteModel.setOfferId(offerId);
        favouriteModel.setUserId(userId);
        favouriteModel.setStatus(fav);
        Gson gson = new Gson();
        mNetworkRepository.favouriteOffer(gson.toJson(favouriteModel))
                .observe(mParentFragment, offers -> {
                    if (offers.equals("Success")) {
                        if (fav == 1) {//remove favouriteImageView
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
                        } else {//mark favouriteImageView
                            realm.executeTransaction(realm1 -> {
                                realm1.where(LoginDataBase.class).findFirst().addFavuriteOffer(offerId, userId);
                                Log.i("Fav", "Done");
                                //realm1.commitTransaction();
                            });
                        }
                    } else {
                    }
                });
    }

    private void deleteOffer(int offerId, int position) {
        mNetworkRepository.deleteOffer(offerId)
                .observe(mParentFragment, offers -> {
                    if (offers.equals("Success")) {
                        realm.beginTransaction();
                        OfferDataBase l = realm.where(LoginDataBase.class).findFirst().getOffers().where().equalTo("Id", offerId).findFirst();
                        l.deleteFromRealm();
                        realm.commitTransaction();
                        offersList.remove(position);
                        notifyDataSetChanged();
                    } else {
                        Log.e("Er", offers);
                    }
                });
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