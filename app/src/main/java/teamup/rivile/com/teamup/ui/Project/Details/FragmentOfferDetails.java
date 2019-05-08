package teamup.rivile.com.teamup.ui.Project.Details;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import com.badoualy.stepperindicator.StepperIndicator;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.ui.DrawerActivity;
import teamup.rivile.com.teamup.ui.Project.Add.Adapters.FilesAdapter;
import teamup.rivile.com.teamup.ui.Project.Add.Adapters.ImagesAdapter;
import teamup.rivile.com.teamup.ui.Project.List.ContributerImages;
import teamup.rivile.com.teamup.ui.Project.List.FragmentListProjects;
import teamup.rivile.com.teamup.ui.Project.ShareDialogFragment;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.FavouriteModel;
import teamup.rivile.com.teamup.Uitls.APIModels.LikeModel;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;
import teamup.rivile.com.teamup.Uitls.APIModels.ReportModel;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.AppModels.FilesModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.FavouriteDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LikeModelDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDataBase;

public class FragmentOfferDetails extends Fragment implements ShareDialogFragment.Helper {
    private String mProjectURL = "";
    private String mProjectName = "";
    private OfferDetailsJsonObject offerDetailsJsonObject = null;

    View view;
    RelativeLayout money, contributors;
    LinearLayout moneySection, contributorsSection, moneyRequired;
    RelativeLayout place, experience;
    LinearLayout placeSection, experienceSection;
    RelativeLayout attachment, cap, dep, tags, DepSection;
    LinearLayout CapSection, tagSection;

    int m, c, p, e, a, ca, d, t;/** متغير ثابت عشان اغير حاله ال shrink وال expand*/
    /**
     * 1: Expand, 0:Shrink
     */

    CircleImageView user_image;
    TextView project_name, user_name;
    TextView proDetail/*, moneyDesc*/, image_name;
    TextView moneyProfitType, genderRequired, placeState, placeType;
    StepperIndicator educationLevel;

    FloatingActionButton arrowContributors, arrowMoney, arrowTag, arrowDep, arrowAttach, arrowEx, arrowPlace;
    TextView moneyOutFrom, moneyOutTo, moneyInFrom, moneyInTo, conFrom, conTo;


    TextView placeDesc, placeAddress, exDesc, exDep, depName, experienceFrom, experienceTo, tagsList;
    TextView nun_contributor, num_likes;

    View map;

    RecyclerView recFiles, recImages;
    RecyclerView.Adapter imagesAdapter, filesAdapter;
    List<FilesModel> filesModels, imagesModels;
    ImageView preview;
    TextView mEmptyView;

    RecyclerView recCont;
    RecyclerView.Adapter conAdapter;

    TextView like, make_offer, share;
    static int type, position;


    static int projectId = 50;
    DownloadManager downloadManager;
    ImageView offerOptions;
    Realm realm;
    static int userId;

    public static FragmentOfferDetails setProjectId(int proId, int ty, int pos) {
        projectId = proId;
        type = ty;
        position = pos;
        return new FragmentOfferDetails();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        registerReceiver(onComplete,
//                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        realm = Realm.getDefaultInstance();


        realm.executeTransaction(realm1 -> {
            userId = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
        });
        loadOfferDetails(projectId);
    }

    @Override
    public void onStart() {
        super.onStart();

        ((DrawerActivity) getActivity()).hideFab();
        ((DrawerActivity) getActivity()).hideSearchBar();

//        if (type == FragmentListProjects.NORMAL) {
//            offerOptions.setImageResource(R.drawable.ic_report);
//
//        } else if (type == FragmentListProjects.MINE) {
//            offerOptions.setImageResource(R.drawable.ic_cancel);
//
//        }

        offerOptions.setOnClickListener(v -> {

//            final Bitmap bmap = ((BitmapDrawable) offerOptions.getDrawable()).getBitmap();
//            Drawable myDrawable = getResources().getDrawable(R.drawable.ic_cancel);
//            final Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
//            if (bmap.sameAs(myLogo)) {
//                deleteOffer(projectId);
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frame, new FragmentHome());
//                fragmentTransaction.addToBackStack(FragmentProfileHome.class.getSimpleName()).commit();
//
//            } else {
//                //TODO: Action Report Here`
//                makeReport(projectId, userId, getActivity());
//            }

            if (type == FragmentListProjects.NORMAL) {
                showNormalMenu(offerOptions);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                EditText textReport = new EditText(getActivity());
                textReport.setHint(getString(R.string.reportText));
                //Todo: Report Action (view, inflate, webservice)
            } else if (type == FragmentListProjects.FAVOURITE) {

            } else if (type == FragmentListProjects.MINE) {
                showMyMenu(offerOptions, projectId);
                realm.executeTransaction(realm1 -> {
                    RealmResults<LoginDataBase> loginDataBases = realm1.where(LoginDataBase.class)
                            .findAll();
                    OfferDataBase offerDetailsDataBases = loginDataBases.get(0).getOffers().get(position);
                    offerDetailsDataBases.deleteFromRealm();
                    realm1.commitTransaction();
                });
            }


        });


        like.setOnClickListener(v -> {
            Drawable likeDrawable = like.getCompoundDrawables()[2]; //right drawable
            if (likeDrawable.getConstantState()
                    .equals(getContext()
                            .getResources()
                            .getDrawable(R.drawable.ic_like)
                            .getConstantState())) {
                like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_favorite_border_black_24dp, 0);
                likeOffer(projectId, userId,  1);
            } else if (likeDrawable.getConstantState()
                    .equals(getContext()
                            .getResources()
                            .getDrawable(R.drawable.ic_favorite_border_black_24dp)
                            .getConstantState())) {
                like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_like, 0);
                likeOffer(projectId, userId, 0);
            }
        });

        share.setOnClickListener(
                v -> ShareDialogFragment.getInstance(this)
                        .show(getFragmentManager(), "ShareDialogFragment")
        );

        imagesAdapter = new ImagesAdapter(getActivity(), imagesModels, item -> {
            try {
                Picasso.get().load(item.getFileName()).into(preview);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        filesAdapter = new FilesAdapter(getActivity(), filesModels, item -> {
            try {
                String fileType = "Files";
                Environment.getExternalStoragePublicDirectory(
                        getString(R.string.app_name) + File.separator + fileType);
//                File file = new File(path, item.getFileName());

                downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri;
                Log.e("File Link", item.getFileName());
                uri = Uri.parse(item.getFileName());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                File folder = new File(Environment.getExternalStoragePublicDirectory(
                        getString(R.string.app_name) + File.separator + fileType), item.getFileName());
                Uri path = Uri.withAppendedPath(Uri.fromFile(folder), item.getFileName());


                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdirs();
                    System.out.println("Done");
                }
                if (success) {
                    System.out.println("Done");
                    request.setDestinationUri(path);
//                    request.setDestinationInExternalFilesDir(getActivity(), getString(R.string.app_name) + File.separator + fileType, item.getFileName());
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
                    request.setAllowedOverRoaming(false);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                            DownloadManager.Request.NETWORK_MOBILE);
                    request.setTitle(item.getServerFileName());
                    downloadManager.enqueue(request);
                } else {
//                    Toast.makeText(getActivity(), getString(R.string.noSapce), Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        recFiles.setAdapter(filesAdapter);

        recImages.setAdapter(imagesAdapter);

        money.setOnClickListener(v -> {
            if (m == 1) {
                m = 0;
                moneySection.setVisibility(View.GONE);
                arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                m = 1;
                moneySection.setVisibility(View.VISIBLE);
                arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));

            }
        });

        contributors.setOnClickListener(v -> {
            if (c == 1) {
                c = 0;
                contributorsSection.setVisibility(View.GONE);
                arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                c = 1;
                contributorsSection.setVisibility(View.VISIBLE);
                arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
            }

        });


        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p == 1) {
                    p = 0;
                    placeSection.setVisibility(View.GONE);
                    arrowPlace.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                } else {
                    p = 1;
                    placeSection.setVisibility(View.VISIBLE);
                    arrowPlace.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });

        experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e == 1) {
                    e = 0;
                    experienceSection.setVisibility(View.GONE);
                    arrowEx.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                } else {
                    e = 1;
                    experienceSection.setVisibility(View.VISIBLE);
                    arrowEx.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });
        //attachment, cap, dep, tags
        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == 1) {
                    a = 0;
//                    attachment.setVisibility(View.GONE);
//                attachmentSection.setVisibility(View.GONE);
                    recImages.setVisibility(View.GONE);
                    recFiles.setVisibility(View.GONE);
                    preview.setVisibility(View.GONE);
                    arrowAttach.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                } else {
                    a = 1;
//                    attachment.setVisibility(View.VISIBLE);
//                attachmentSection.setVisibility(View.GONE);
                    recImages.setVisibility(View.VISIBLE);
                    recFiles.setVisibility(View.VISIBLE);
                    preview.setVisibility(View.VISIBLE);
                    arrowAttach.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });
        dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (d == 1) {
                    d = 0;
                    DepSection.setVisibility(View.GONE);
                    arrowDep.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                } else {
                    d = 1;
                    DepSection.setVisibility(View.VISIBLE);
                    arrowDep.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });
        tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t == 1) {
                    t = 0;
                    tagSection.setVisibility(View.GONE);
                    arrowTag.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                } else {
                    t = 1;
                    tagSection.setVisibility(View.VISIBLE);
                    arrowTag.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });

    }

    private void showNormalMenu(ImageView op) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(getActivity(), op);
        //inflating menu from xml resource
        popup.inflate(R.menu.normal_offer_option);
        //adding click listener
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
//                case R.id.action_add_to_favourite:
//                    //handle action_add_to_favourite click
//                    if (item.getIcon()
//                            .getConstantState()
//                            .equals(getActivity().getResources()
//                                    .getDrawable(R.drawable.ic_star_empty)
//                                    .getConstantState())) {
//                        item.setIcon(R.drawable.ic_star_full);
//                        markFavourite(projectId, userId, 0);
//                    } else {
//                        item.setIcon(R.drawable.ic_star_empty);
//                        markFavourite(projectId, userId, 1);
//                    }
//                    break;
                case R.id.action_alert:
                    //handle action_alert click
                    makeReport(projectId, userId, getActivity());
                    break;
            }
            return false;
        });
        //displaying the popup
        popup.show();
    }

    public void markFavourite(int offerId, int userId, int fav) {
        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);

        RetrofitMethods getOffers = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
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
                    if (fav == 1) {//remove favourite
                        realm.executeTransaction(realm1 -> {
                            if (realm1.where(LoginDataBase.class).findFirst().getFavorites() != null && realm1.where(LoginDataBase.class).findFirst().getFavorites().size() > 0) {
                                RealmResults<FavouriteDataBase> l = realm1.where(LoginDataBase.class).findFirst().getFavorites().where().equalTo("OfferId", offerId).findAll();
                                for (int i = 0; i < l.size(); i++) {
                                    Log.v("IdR", l.get(i).getId() + "");
                                    Log.v("IdRR", l.get(i).getOfferId() + "");
                                    Log.v("IdRRR", l.get(i).getUserId() + "");
                                }

                                l.deleteAllFromRealm();
//                    realm1.commitTransaction();
                            } else {
                                Log.v("Status", "Not Found");
                            }
                        });
                    } else {//mark favourite
                        realm.executeTransaction(realm1 -> {
                            realm1.where(LoginDataBase.class).findFirst().addFavuriteOffer(offerId, userId);
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
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);

        Log.e("OfferId", offerId + "");
        RetrofitMethods getOffers = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
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

    private void showMyMenu(ImageView op, int position) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(getActivity(), op);
        //inflating menu from xml resource
        popup.inflate(R.menu.my_offer_option_detail);
        //adding click listener
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
//                case R.id.action_add_to_favourite:
//                    //handle action_add_to_favourite click
//                    if (item.getIcon()
//                            .getConstantState()
//                            .equals(getActivity().getResources()
//                                    .getDrawable(R.drawable.ic_star_empty)
//                                    .getConstantState())) {
//                        item.setIcon(R.drawable.ic_star_full);
//                        markFavourite(projectId, userId, 0);
//                    } else {
//                        item.setIcon(R.drawable.ic_star_empty);
//                        markFavourite(projectId, userId, 1);
//                    }
//                    break;
                case R.id.action_delete:
                    //handle action_delete click
                    realm.executeTransaction(realm1 -> {
                        int userId = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
                        FragmentOfferDetails.makeReport(projectId, userId, getActivity());
                    });
//                    reportOrDelete(item, position);
                    break;
                case R.id.action_edit_offer:
//                    if (offerDetailsJsonObject != null
//                            && offerDetailsJsonObject.getOffer() != null && offerDetailsJsonObject.getOffer().getRequirments() != null){
////                        getFragmentManager().beginTransaction()
////                                .replace(R.id.frame, FragmentJoinHome.setRequirement(offerDetailsJsonObject.getOffer().getRequirments().get(0), projectId))
////                                .addToBackStack(FragmentOfferDetails.class.getName())
////                                .commit();
//                    }
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
                .equals(getActivity().getResources()
                        .getDrawable(R.drawable.ic_cancel)
                        .getConstantState())) {//means delete action
            deleteOffer(projectId, position);

        } else {//make report
            //TODO: Action Report Here
            realm.executeTransaction(realm1 -> {
                int userId = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
                makeReport(projectId, userId, getActivity());
            });

        }
    }

    public static void makeReport(int projectId, int userId, Context context) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View message = inflater.inflate(R.layout.popup_report, null);

        EditText message_type = message.findViewById(R.id.editText);
        FloatingActionButton confirm = message.findViewById(R.id.confirm);
        ImageView close = message.findViewById(R.id.close);


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false)
                .setView(message)
                .setNegativeButton("اغلاق", (dialog, which) -> {
                    // Do Nothing
                    dialog.dismiss();
                });
        final AlertDialog dialog2 = builder.create();
        dialog2.show();

        close.setOnClickListener(v ->
                dialog2.dismiss()
        );
        confirm.setOnClickListener(v -> {
            if (!message_type.getText().toString().isEmpty()) {
                reportOffer(userId, projectId, message_type.getText().toString(), context, dialog2);
            }
        });
    }

    public static void reportOffer(int userId, int projectId, String desc, Context context, AlertDialog dialog2) {
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);

        RetrofitMethods getOffers = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<String> call;
        ReportModel model = new ReportModel();
        model.setOfferId(projectId);
        model.setUserId(userId);
        model.setDescripation(desc);
        Gson gson = new Gson();

        call = getOffers.reportOffer(gson.toJson(model), API.URL_TOKEN);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String res = response.body();
                Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
                dialog2.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //textView.setText(t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadOfferDetails(int Id) {
        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);

        RetrofitMethods getOffers = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<OfferDetailsJsonObject> call;

        call = getOffers.offerDetails(Id, API.URL_TOKEN);

        call.enqueue(new Callback<OfferDetailsJsonObject>() {
            @Override
            public void onResponse(Call<OfferDetailsJsonObject> call, retrofit2.Response<OfferDetailsJsonObject> response) {
                OfferDetailsJsonObject Offers = response.body();
                List<UserModel> Users = Offers.getOffer().getUsers();
                if (Offers.getOffer() != null) {
                    Gson gson = new Gson();
                    Log.e("GSON", gson.toJson(Offers.getOffer()));
                    fillOffers(Offers.getOffer());
                    offerDetailsJsonObject = Offers;
                } else {

                }
            }

            @Override
            public void onFailure(Call<OfferDetailsJsonObject> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }


    public void likeOffer(int offerId, int userId, int like) {
        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);

        RetrofitMethods getOffers = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<String> call;
        LikeModel likeModel = new LikeModel();
        likeModel.setOfferId(offerId);
        likeModel.setUserId(userId);
        Log.v("offerId", offerId+"");
        Log.v("userId", userId+"");
        Log.v("Like", like+"");
        likeModel.setStatus(like);
        if (like == 1) {//dislike
            realm.executeTransaction(realm1 -> {
                LikeModelDataBase l = realm1.where(LoginDataBase.class).findFirst().getLikes().where().equalTo("OfferId",offerId).findFirst();
                l.deleteFromRealm();
//                realm1.commitTransaction();
            });
        } else {//like
            realm.executeTransaction(realm1 -> {
                realm1.where(LoginDataBase.class).findFirst().addLikes(offerId, userId);
//                realm1.commitTransaction();
            });
        }
        Gson gson = new Gson();
        call = getOffers.likeOffer(gson.toJson(likeModel), API.URL_TOKEN);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String Offers = response.body();
                Log.e("Like", Offers);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }

    public void deleteOffer(int offerId) {
        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);

        RetrofitMethods getOffers = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<String> call = getOffers.deleteOffer(offerId, API.URL_TOKEN);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String Offers = response.body();
                if (Offers.equals("\"Success\"")) {
                    realm.executeTransaction(realm1 -> {
                        OfferDataBase l = realm1.where(LoginDataBase.class).findFirst().getOffers().where().equalTo("OfferId", offerId).findFirst();
                        l.deleteFromRealm();
                        realm1.commitTransaction();
                    });
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }
    public void exportDatabase() {

        File exportRealmFile = null;
        // get or create an "export.realm" file
        exportRealmFile = new File(getActivity().getExternalCacheDir(), "export.realm");

        // if "export.realm" already exists, delete
        exportRealmFile.delete();

        // copy current realm to "export.realm"
        realm.writeCopyTo(exportRealmFile);

        realm.close();

        // init email intent and add export.realm as attachment
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "YOUR MAIL");
        intent.putExtra(Intent.EXTRA_SUBJECT, "YOUR SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT, "YOUR TEXT");
        Uri u = Uri.fromFile(exportRealmFile);
        intent.putExtra(Intent.EXTRA_STREAM, u);

        // start email intent
        startActivity(Intent.createChooser(intent, "YOUR CHOOSER TITLE"));
    }

    @SuppressLint("ResourceType")
    private void fillOffers(OfferDetails Offers) {
        //TODO: get project URL and send it here
        //TODO:mProjectURL = Offers.getURL();

        project_name.setText(Offers.getName());

        realm.executeTransaction(realm1 -> {
            RealmList<LikeModelDataBase> Likes = realm1.where(LoginDataBase.class).findFirst().getLikes();
            for (int i = 0; i < Likes.size(); i++) {
                if (Likes.get(i).getOfferId() == Offers.getId()) {
                    like.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_like, 0);
                    return;
                }
            }
        });

        ((DrawerActivity) getActivity())
                .setTitle(Offers.getName());
        mProjectName = Offers.getName();

        proDetail.setText(Offers.getDescription());
        num_likes.setText(String.valueOf(Offers.getNumLiks()));
//        nun_contributor.setText(String.valueOf(Offers.getNumContributorTo()));
        if (Offers.getUsers() != null && Offers.getUsers().size() > 1) {
            Log.e("U", Offers.getUsers().get(0).getImage() + " WWWWWw");
            conAdapter = new ContributerImages(getActivity(), Offers.getUsers(), mEmptyView);
            recCont.setAdapter(conAdapter);
        }

//        if (Offers.getProfitType() == 0) {
//            moneyProfitType.setText(getResources().getString(R.string.day));
//        } else if (Offers.getProfitType() == 1) {
//            moneyProfitType.setText(getResources().getString(R.string.month));
//        } else if (Offers.getProfitType() == 2) {
//            moneyProfitType.setText(getResources().getString(R.string.year));
//        } else if (Offers.getProfitType() == 3) {
//            moneyProfitType.setText(getResources().getString(R.string.anotherKind));
//        }

//        if (Offers.getRequirments() != null && !Offers.getRequirments().isEmpty() && Offers.getRequirments().get(0).isNeedPlace()) {
//            if (Offers.getRequirments().get(0).isNeedPlaceType()) {
//                placeType.setText(getResources().getString(R.string.avail));
//            } else if (Offers.getProfitType() == 1) {
//                placeType.setText(getResources().getString(R.string.notAvail));
//            }
//
//            if (Offers.getRequirments().get(0).isNeedPlaceStatus()) {
//                placeState.setText(getResources().getString(R.string.rent));
//            } else if (Offers.getProfitType() == 1) {
//                placeState.setText(getResources().getString(R.string.owned));
//            }
//
//            if (Offers.getRequirments().get(0).getPlaceAddress() != null && !Offers.getRequirments().get(0).getPlaceAddress().isEmpty()) {
//                placeAddress.setText(Offers.getRequirments().get(0).getPlaceAddress());
//            } else {
//                placeAddress.setText(getString(R.string.noSpAddress));
//            }
//
//            if (Offers.getRequirments().get(0).getPlaceDescriptions() != null && !Offers.getRequirments().get(0).getPlaceDescriptions().isEmpty()) {
//                placeDesc.setText(Offers.getRequirments().get(0).getPlaceDescriptions());
//            } else {
//                placeDesc.setText(getString(R.string.noDesPlace));
//            }
//
//        } else {
//            place.setVisibility(View.GONE);
//            placeSection.setVisibility(View.GONE);
//        }

        depName.setText(Offers.getCategoryName());
//        if (Offers.getRequirments() != null && !Offers.getRequirments().isEmpty() && Offers.getRequirments().get(0).isNeedExperience()) {
//            //Todo: load Data From Experience Model and load it to (exDep)
//            experienceFrom.setText(String.valueOf(Offers.getRequirments().get(0).getExperienceFrom()));
//            experienceTo.setText(String.valueOf(Offers.getRequirments().get(0).getExperienceTo()));
//            exDesc.setText(String.valueOf(Offers.getRequirments().get(0).getExperienceDescriptions()));
//        } else {
//            experience.setVisibility(View.GONE);
//            experienceSection.setVisibility(View.GONE);
//        }
//        if (Offers.getRequirments() != null && !Offers.getRequirments().isEmpty() && Offers.getRequirments().get(0).isNeedMoney()) {
//            moneyOutFrom.setText(String.valueOf(Offers.getProfitFrom()));
//            moneyOutTo.setText(String.valueOf(Offers.getProfitTo()));
//            moneyInFrom.setText(String.valueOf(Offers.getRequirments().get(0).getMoneyFrom()));
//            moneyInTo.setText(String.valueOf(Offers.getRequirments().get(0).getMoneyTo()));
//        } else {
//            moneyRequired.setVisibility(View.GONE);
//        }

        if (Offers.getGenderContributor() == 0) {
            genderRequired.setText(getResources().getString(R.string.male));
        } else if (Offers.getGenderContributor() == 1) {
            genderRequired.setText(getResources().getString(R.string.female));
        } else if (Offers.getGenderContributor() == 2) {
            genderRequired.setText(getResources().getString(R.string.both));
        }

//        conFrom.setText(String.valueOf(Offers.getNumContributorFrom()));
//        conTo.setText(String.valueOf(Offers.getNumContributorTo()));
//        educationLevel.setCurrentStep(Offers.getEducationContributorLevel());

        if (Offers.getUsers() != null) {
            for (int i = 0; i < Offers.getUsers().size(); i++) {
                if (Offers.getUserId().equals(Offers.getUsers().get(i).getId())) {
                    if (Offers.getUsers().get(i).getImage() != null && !Offers.getUsers().get(i).getImage().isEmpty()) {
//                        user_name.setText(Offers.getUsers().get(i).getFullName());
//                        Picasso.get().load(API.BASE_URL + Offers.getUsers().get(i).getImage()).into(user_image);

                        String[] name = Offers.getUsers().get(i).getFullName().split(" ");
                        user_name.setText(name[0]);
//            user_name.setText(User.getFullName());
                        if (Offers.getUsers().get(i).getImage() != null && !Offers.getUsers().get(i).getImage().isEmpty()) {
                            try {
                                if (Offers.getUsers().get(i).getSocialId() != null) {
                                    Picasso.get().load(Offers.getUsers().get(i).getImage()).into(user_image);
                                } else {
                                    Picasso.get().load(API.BASE_URL + Offers.getUsers().get(i).getImage()).into(user_image);
                                }
                                image_name.setVisibility(View.GONE);
                            } catch (Exception e) {
                                image_name.setVisibility(View.VISIBLE);
                                String[] sp = Offers.getUsers().get(i).getFullName().split(" ");
                                if (!Offers.getUsers().get(i).getFullName().contains(" ")) {
                                    image_name.setText(Offers.getUsers().get(i).getFullName().charAt(0) + "");
                                } else if (sp.length > 0 && sp.length <= 2) {
                                    for (int j = 0; j < sp.length; j++) {
                                        image_name.append(sp[j] + "");
                                    }
                                } else if (sp.length > 2) {
                                    for (int j = 0; j < 2; j++) {
                                        image_name.append(sp[j] + "");
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

//        if (!Offers.getRequirments().isEmpty() && Offers.getRequirments().get(0).getAttachmentModels().size() > 0) {
//            for (int i = 0; i < Offers.getRequirments().get(0).getAttachmentModels().size(); i++) {
//                AttachmentModel model = Offers.getRequirments().get(0).getAttachmentModels().get(i);
//                Gson gson = new Gson();
//                Log.e("Gson", gson.toJson(model));
//                if (model.getType().equals(true)) {
//                    Log.e("Type", "File");
//                    try {
//                        FilesModel f = new FilesModel();
//                        f.setFileName(API.BASE_URL + model.getSource());
//                        f.setServerFileName(model.getName());
//                        filesModels.add(f);
//                        filesAdapter.notifyDataSetChanged();
//                    } catch (Exception ex) {
//                        Log.e("Files EX", ex.getMessage());
//                    }
//                } else {
//                    Log.e("Type", "Image");
//                    try {
//                        FilesModel f = new FilesModel();
//                        f.setFileName(API.BASE_URL + model.getSource());
//                        imagesModels.add(f);
//                        imagesAdapter.notifyDataSetChanged();
//                        Picasso.get().load(API.BASE_URL + model.getSource()).into(preview);
//
//                    } catch (Exception ex) {
//                        Log.e("Images EX", ex.getMessage());
//                    }
//                }
//
//            }
//
//            if (filesModels.size() > 0) {
//                recFiles.setVisibility(View.VISIBLE);
//            }
//
//            if (imagesModels.size() > 0) {
//                recImages.setVisibility(View.VISIBLE);
//                preview.setVisibility(View.VISIBLE);
//            }
//
//
//        } else {
//            attachment.setVisibility(View.GONE);
////                attachmentSection.setVisibility(View.GONE);
//            recImages.setVisibility(View.GONE);
//            recFiles.setVisibility(View.GONE);
//            preview.setVisibility(View.GONE);
//        }


//        //Todo: Download file names & images source
//        RetrofitConfigurations appConfig = new RetrofitConfigurations(API.BASE_URL);
//
//        if (Offers.getRequirments() != null) {
//            if (Offers.getRequirments().get(0).getAttachmentModels().size() > 0) {
//                for (int i = 0; i < Offers.getRequirments().get(0).getAttachmentModels().size(); i++) { // Todo: Attachment.size()
//                    AttachmentModel model = Offers.getRequirments().get(0).getAttachmentModels().get(i);
//                    String fileLink = model.getName(); //Todo: attachment.getName()
//                    if (!model.getType()) {
//                        RetrofitMethods getResponse = appConfig.getRetrofit().create(RetrofitMethods.class);
//                        getResponse.download(fileLink, new Callback<AttachmentModel>() {
//                            @Override
//                            public void onResponse(Call<AttachmentModel> call, Response<AttachmentModel> response) {
//                                AttachmentModel model = response.body();
//                                if (model != null) {
//                                    try {
//                                        FilesModel f = new FilesModel();
//                                        f.setFileName(API.BASE_URL + model.getSource());
//                                        imagesModels.add(f);
//                                        imagesAdapter.notifyDataSetChanged();
//                                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), f.getFileUri());
//                                        preview.setImageBitmap(bitmap);
//
//                                    } catch (Exception ex) {
//                                        Log.e("Images EX", ex.getMessage());
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<AttachmentModel> call, Throwable t) {
//
//                            }
//                        });
//                    } else {
//                        RetrofitMethods getResponse = appConfig.getRetrofit().create(RetrofitMethods.class);
//                        getResponse.download(fileLink, new Callback<AttachmentModel>() {
//                            @Override
//                            public void onResponse(Call<AttachmentModel> call, Response<AttachmentModel> response) {
//                                AttachmentModel model = response.body();
//                                if (model != null) {
//                                    try {
//                                        FilesModel f = new FilesModel();
//                                        f.setFileName(API.BASE_URL + model.getSource());
//                                        filesModels.add(f);
//                                        filesAdapter.notifyDataSetChanged();
//                                    } catch (Exception ex) {
//                                        Log.e("Files EX", ex.getMessage());
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<AttachmentModel> call, Throwable t) {
//
//                            }
//                        });
//                    }
//                }
//            } else {
//                attachment.setVisibility(View.GONE);
////                attachmentSection.setVisibility(View.GONE);
//                recImages.setVisibility(View.GONE);
//                recFiles.setVisibility(View.GONE);
//                preview.setVisibility(View.GONE);
//            }
//
//        }
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
//            Toast.makeText(ctxt, getString(R.string.finishFileDownload), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.cl_facebook:
                try {
                    shareToFaceBook();
                } catch (IOException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.cl_twitter:
                shareToTwitter();
                break;

            case R.id.cl_whatsapp:
                shareToWhatsApp();
        }
    }

    private void shareToWhatsApp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);

        if (ShareDialogFragment.isWhatsAppAvailable)
            whatsappIntent.setPackage(ShareDialogFragment.WHATSAPP_PACKAGE);
        else if (ShareDialogFragment.isWhatsAppBusinessAvailable)
            whatsappIntent.setPackage(ShareDialogFragment.WHATSAPP_BUSINESS_PACKAGE);

        whatsappIntent.setType("text/plain");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, mProjectName + "\n" + mProjectURL + "\n");
//        if (imageUri != null)
//            whatsappIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ignored) {
            Toast.makeText(getContext(), ignored.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void shareToTwitter() {
        Intent twitterIntent = new Intent(Intent.ACTION_SEND);
        twitterIntent.setType("*/*");

        if (ShareDialogFragment.isTwitterAvailable)
            twitterIntent.setPackage(ShareDialogFragment.TWITTER_PACKAGE);
        else if (ShareDialogFragment.isTwitterLiteAvailable)
            twitterIntent.setPackage(ShareDialogFragment.TWITTER_LITE_PACKAGE);

        twitterIntent.putExtra(Intent.EXTRA_TEXT, mProjectName + "\n" + mProjectURL + "\n");
//        if (imageUri != null)
//            twitterIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

        twitterIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(twitterIntent);
        } catch (android.content.ActivityNotFoundException ignored) {
            Toast.makeText(getContext(), ignored.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void shareToFaceBook() throws IOException {
//        if (imageUri != null) {
//            //share image(s)
//            SharePhoto photo1 = new SharePhoto.Builder()
//                    .setBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri))
//                    .build();
//            ShareContent shareContent = new ShareMediaContent.Builder()
//                    .addMedium(photo1)
////                .addMedium(photo2)
////                .addMedium(photo3)
////                .addMedium(photo4)
//                    .build();
//
//            ShareDialog shareDialog = new ShareDialog(this);
//            shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);
//        } else {
        //share URL
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(mProjectURL))
                .setQuote(mProjectName)
                .build();

        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(shareLinkContent, ShareDialog.Mode.AUTOMATIC);
//        }
    }
}
