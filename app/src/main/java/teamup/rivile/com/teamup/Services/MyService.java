package teamup.rivile.com.teamup.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.ui.FirstActivity;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.Model;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.NotificationModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.Settings;

public class MyService extends Service {

    public Context context = this;
    Realm realm;
    NotificationModel notificationModel;
    int userId;

    public MyService() {
        //super("onCreate");
        Log.e("onCreate", "onCreate");
//        realm = Realm.getDefaultInstance();

        //Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        while (true){
//
//        }

//        }
    }

    private String convertNotiModel(NotificationModel notificationModel) {
        Gson gson = new Gson();
        teamup.rivile.com.teamup.Uitls.APIModels.NotificationModel result = new teamup.rivile.com.teamup.Uitls.APIModels.NotificationModel();
        result.setUserId(userId);

        if (notificationModel != null && notificationModel.getAcceptJoinOffer() != null && notificationModel.getAcceptJoinOffer().last() != null) {
            result.setAcceptJoinOffer(convertList(notificationModel.getAcceptJoinOffer().last()));
        } else {
            List<Model> m = new ArrayList<>();
            m.add(new Model());
            result.setAcceptJoinOffer(m);
        }

        if (notificationModel != null && notificationModel.getJoinOffer() != null && notificationModel.getJoinOffer().last() != null) {
            result.setJoinOffer(convertList(notificationModel.getJoinOffer().last()));
        } else {
            List<Model> m = new ArrayList<>();
            m.add(new Model());
            result.setJoinOffer(m);
        }

        if (notificationModel != null && notificationModel.getLike() != null && notificationModel.getLike().last() != null) {
            result.setLike(convertList(notificationModel.getLike().last()));
        } else {
            List<Model> m = new ArrayList<>();
            m.add(new Model());
            result.setLike(m);
        }

        if (notificationModel != null && notificationModel.getMyProjectStatus() != null && notificationModel.getMyProjectStatus().last() != null) {
            result.setMyProjectStatus(convertList(notificationModel.getMyProjectStatus().last()));
        } else {
            List<Model> m = new ArrayList<>();
            m.add(new Model());
            result.setMyProjectStatus(m);
        }
        return gson.toJson(result);
    }

    private List<Model> convertList(teamup.rivile.com.teamup.Uitls.InternalDatabase.Model last) {
        List<Model> list = new ArrayList<>();
        Model m = new Model();
        m.setAcceptJoinOfferId(last.getAcceptJoinOfferId());
        m.setJoinOfferId(last.getJoinOfferId());
        m.setLikeId(last.getLikeId());
        m.setOfferId(last.getOfferId());
        m.setOfferName(last.getOfferName());
        m.setStatus(last.getStatus());
        m.setUserFullName(last.getUserFullName());
        list.add(m);
        return list;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Intent intent1 = new Intent();
//        intent.setAction("teamup.rivile.com.teamup.MY_NOTIFICATION");
//        intent.putExtra("data", "Notice me senpai!");
//        sendBroadcast(intent1);

        try {
            if(realm == null) {
                Realm.init(context);
                realm = Realm.getDefaultInstance();
            }
            realm.executeTransaction(realm1 -> {
                if (realm1.where(LoginDataBase.class).findFirst() != null && realm1.where(LoginDataBase.class).findFirst().getUser() != null) {
                    userId = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
//                    realm.executeTransaction(realm1 -> {
                    if (realm1.where(teamup.rivile.com.teamup.Uitls.InternalDatabase.NotificationModel.class).findFirst() != null){
                        notificationModel = realm1.where(teamup.rivile.com.teamup.Uitls.InternalDatabase.NotificationModel.class).findFirst();
                    }else {
                        Log.e("GG","Not okay");
                        RealmResults<Settings> settingsRealmList = realm1.where(Settings.class).findAll();
                        settingsRealmList.deleteAllFromRealm();

                        Settings s = new Settings();
                        s.setNotificaionStatus(true);
                        realm1.insertOrUpdate(s);
                    }
//                    });
//                    SystemClock.sleep(50000);
                    getNotification(convertNotiModel(notificationModel));
                }else {
                    Log.e("GG","So bad");
                }
            });
        }catch (Exception e){

        }finally {
            if(realm != null) {
                realm.close();
            }
        }


//        while (true) {
//        try {
//            if(realm == null) {
//                Realm.init(context);
//                realm = Realm.getDefaultInstance();
//            }
//            realm.executeTransaction(realm1 -> {
//                if (realm1.where(teamup.rivile.com.teamup.Uitls.InternalDatabase.NotificationModel.class).findFirst() != null)
//                notificationModel = realm1.where(teamup.rivile.com.teamup.Uitls.InternalDatabase.NotificationModel.class).findFirst();
//            });
//            SystemClock.sleep(50000);
//            getNotification(convertNotiModel(notificationModel));
//        } catch (Exception e) {
//
//        }finally {
//            if(realm != null) {
//                realm.close();
//            }
//        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    private void sendNotification(String title, String messageBody) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        Random random = new Random(100000);
        int notifyID = random.nextInt();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Sets an ID for the notification, so it can be updated.
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = ("Ok");// The user-visible name of the channel.
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            Intent ii = new Intent(context, FirstActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

            Notification.BigTextStyle bigText = new Notification.BigTextStyle();
            bigText.bigText(messageBody);
            bigText.setBigContentTitle(title);
            bigText.setSummaryText(getString(R.string.noti_body));

            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(getString(R.string.noti_body))
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setChannelId(CHANNEL_ID)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
                    .setStyle(bigText)
                    .build();

            mNotificationManager.notify(notifyID, notification);

            mNotificationManager.createNotificationChannel(mChannel);
        } else {

            Intent ii = new Intent(context, FirstActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

            Notification.BigTextStyle bigText = new Notification.BigTextStyle();
            bigText.bigText(messageBody);
            bigText.setBigContentTitle(title);
            bigText.setSummaryText(getString(R.string.noti_body));

            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(getString(R.string.noti_body))
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
                    .setStyle(bigText)
                    .build();

            mNotificationManager.notify(notifyID, notification);
        }
    }

    private void getNotification(String model) {
        // Map is used to multipart the file using okhttp3.RequestBody

        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);

        RetrofitMethods reg = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<teamup.rivile.com.teamup.Uitls.APIModels.NotificationModel> call = reg.getNotification(model, API.URL_TOKEN);
        call.enqueue(new Callback<teamup.rivile.com.teamup.Uitls.APIModels.NotificationModel>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<teamup.rivile.com.teamup.Uitls.APIModels.NotificationModel> call, retrofit2.Response<teamup.rivile.com.teamup.Uitls.APIModels.NotificationModel> response) {
                teamup.rivile.com.teamup.Uitls.APIModels.NotificationModel serverResponse = response.body();
                if (serverResponse != null) {

                    if ( serverResponse.getMyProjectStatus() != null && !serverResponse.getMyProjectStatus().isEmpty()) {
                        /** receive MyProjectStatus **/
                        List<Model> mMyProjectStatus = serverResponse.getMyProjectStatus();
                        String body = getString(R.string.congrateForConfirmingUrProjects);
                        if (mMyProjectStatus.size() == 3) {
                            body += mMyProjectStatus.get(0).getOfferName()
                                    + getString(R.string.and)
                                    + mMyProjectStatus.get(1).getOfferName()
                                    + getString(R.string.and)
                                    + mMyProjectStatus.get(2).getOfferName();
                        } else if (mMyProjectStatus.size() == 2) {
                            body += mMyProjectStatus.get(0).getOfferName()
                                    + getString(R.string.and)
                                    + mMyProjectStatus.get(1).getOfferName();
                        } else if (mMyProjectStatus.size() == 1) {
                            body += mMyProjectStatus.get(0).getOfferName();
                        } else {
                            body += mMyProjectStatus.get(0).getOfferName()
                                    + getString(R.string.and)
                                    + mMyProjectStatus.get(1).getOfferName()
                                    + getString(R.string.and)
                                    + mMyProjectStatus.get(2).getOfferName()
                                    + getString(R.string.and)
                                    + String.valueOf(mMyProjectStatus.size() - 3)
                                    + getString(R.string.others);
                        }
                        body += getString(R.string.nowUCanTraceUrProject);

                        notificationModel.setMyProjectStatus(convertRealmList(mMyProjectStatus));
                        sendNotification(getString(R.string.app_name), body);
                        SystemClock.sleep(3000);
                    }

                    if ( serverResponse.getLike() != null && !serverResponse.getLike().isEmpty()) {
                        /** receive Like **/
                        List<Model> mLike = serverResponse.getLike();
                        String body = "";
                        if (mLike.size() == 3) {
                            body = mLike.get(0).getUserFullName()
                                    + getString(R.string.and)
                                    + mLike.get(1).getUserFullName()
                                    + getString(R.string.and)
                                    + mLike.get(2).getUserFullName();
                        } else if (mLike.size() == 2) {
                            body = mLike.get(0).getUserFullName()
                                    + getString(R.string.and)
                                    + mLike.get(1).getUserFullName();
                        } else if (mLike.size() == 1) {
                            body = mLike.get(0).getUserFullName();
                        } else {
                            body = mLike.get(0).getUserFullName()
                                    + getString(R.string.and)
                                    + mLike.get(1).getUserFullName()
                                    + getString(R.string.and)
                                    + mLike.get(2).getUserFullName()
                                    + getString(R.string.and)
                                    + String.valueOf(mLike.size() - 3)
                                    + getString(R.string.others);
                        }
                        body += getString(R.string.likesUrProjects);

                        notificationModel.setLike(convertRealmList(mLike));
                        sendNotification(getString(R.string.app_name), body);
                        SystemClock.sleep(3000);
                    }

                    if ( serverResponse.getJoinOffer() != null && !serverResponse.getJoinOffer().isEmpty()) {
                        /** receive JoinOffer **/
                        List<Model> mJoinOffer = serverResponse.getJoinOffer();
                        String body = "";
                        if (mJoinOffer.size() == 3) {
                            body = mJoinOffer.get(0).getUserFullName()
                                    + getString(R.string.and)
                                    + mJoinOffer.get(1).getUserFullName()
                                    + getString(R.string.and)
                                    + mJoinOffer.get(2).getUserFullName();
                        } else if (mJoinOffer.size() == 2) {
                            body = mJoinOffer.get(0).getUserFullName()
                                    + getString(R.string.and)
                                    + mJoinOffer.get(1).getUserFullName();
                        } else if (mJoinOffer.size() == 1) {
                            body = mJoinOffer.get(0).getUserFullName();
                        } else {
                            body = mJoinOffer.get(0).getUserFullName()
                                    + getString(R.string.and)
                                    + mJoinOffer.get(1).getUserFullName()
                                    + getString(R.string.and)
                                    + mJoinOffer.get(2).getUserFullName()
                                    + getString(R.string.and)
                                    + String.valueOf(mJoinOffer.size() - 3)
                                    + getString(R.string.others);
                        }
//                        body += getString(R.string.othersSendsRequestsToJoinOffer);

                        notificationModel.setJoinOffer(convertRealmList(mJoinOffer));
                        sendNotification(getString(R.string.app_name), body);
                        SystemClock.sleep(3000);
                    }

                    if ( serverResponse.getAcceptJoinOffer() != null && !serverResponse.getAcceptJoinOffer().isEmpty()) {
                        /** receive AcceptJoinOffer **/
                        List<Model> mAcceptJoinOffer = serverResponse.getAcceptJoinOffer();
                        String body = getString(R.string.congrateForAcceptingUrRequirement);
                        body += getString(R.string.congrateForAcceptingUrRequirement);
                        if (mAcceptJoinOffer.size() == 3) {
                            body += mAcceptJoinOffer.get(0).getOfferName()
                                    + getString(R.string.and)
                                    + mAcceptJoinOffer.get(1).getOfferName()
                                    + getString(R.string.and)
                                    + mAcceptJoinOffer.get(2).getOfferName();
                        } else if (mAcceptJoinOffer.size() == 2) {
                            body += mAcceptJoinOffer.get(0).getOfferName()
                                    + getString(R.string.and)
                                    + mAcceptJoinOffer.get(1).getOfferName();
                        } else if (mAcceptJoinOffer.size() == 1) {
                            body += mAcceptJoinOffer.get(0).getOfferName();
                        } else {
                            body += mAcceptJoinOffer.get(0).getOfferName()
                                    + getString(R.string.and)
                                    + mAcceptJoinOffer.get(1).getOfferName()
                                    + getString(R.string.and)
                                    + mAcceptJoinOffer.get(2).getOfferName()
                                    + getString(R.string.and)
                                    + String.valueOf(mAcceptJoinOffer.size() - 3)
                                    + getString(R.string.others);
                        }

                        notificationModel.setAcceptJoinOffer(convertRealmList(mAcceptJoinOffer));
                        sendNotification(getString(R.string.app_name), body);
                        //SystemClock.sleep(3000);
                    }

                    if (notificationModel != null)
                    realm.executeTransactionAsync(realm1 -> realm1.insertOrUpdate(notificationModel));


                } else {
                    //textView.setText(serverResponse.toString());
                    Log.e("Err", "Empty");
//                    Toast.makeText(getContext(), getString(R.string.login_failed_try_again), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<teamup.rivile.com.teamup.Uitls.APIModels.NotificationModel> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Err", t.getMessage());

            }
        });
    }

    private RealmList<teamup.rivile.com.teamup.Uitls.InternalDatabase.Model> convertRealmList(List<Model> mMyProjectStatus) {
        RealmList<teamup.rivile.com.teamup.Uitls.InternalDatabase.Model> list = new RealmList<>();
        for (int i = 0; i < mMyProjectStatus.size(); i++) {
            Model m = mMyProjectStatus.get(i);
            teamup.rivile.com.teamup.Uitls.InternalDatabase.Model nModel = new teamup.rivile.com.teamup.Uitls.InternalDatabase.Model();
            nModel.setAcceptJoinOfferId(m.getAcceptJoinOfferId());
            nModel.setJoinOfferId(m.getJoinOfferId());
            nModel.setLikeId(m.getLikeId());
            nModel.setOfferId(m.getOfferId());
            nModel.setOfferName(m.getOfferName());
            nModel.setStatus(m.getStatus());
            nModel.setUserFullName(m.getUserFullName());
            list.add(nModel);
        }

        return list;
    }
}
