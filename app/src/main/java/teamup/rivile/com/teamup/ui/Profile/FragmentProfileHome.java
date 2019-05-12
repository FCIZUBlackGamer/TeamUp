package teamup.rivile.com.teamup.ui.Profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmList;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitMethods;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.RetrofitConfigurations;
import teamup.rivile.com.teamup.ui.DrawerActivity;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.AttachmentModel;
import teamup.rivile.com.teamup.Uitls.APIModels.Offers;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.AppModels.FilesModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.UserDataBase;

public class FragmentProfileHome extends Fragment {
    private ConstraintLayout mLoadingViewConstraintLayout;

    FloatingActionButton fab_edit;
    ImageView cir_user_image;
    TextView txt_name, txt_job_title, txt_location, txt_bio, txt_dateOfBirth, txt_email, txt_phone, txt_num_projects;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Offers> offersList;
    View view;
    FragmentManager fragmentManager;
    //    ViewPager viewPager = null;
    static int Id = -1;
    //    CollapsingToolbarLayout ctl ;
    Realm realm;
    View edit_data;
    /**
     * edit_data Views
     */
    EditText ed_name, ed_address, ed_job, ed_email, ed_national_id, ed_password, ed_bio, ed_phone;
    TextView tv_birth_date;
    RadioGroup rb_gender;
    RadioButton male, female;
    ImageView /*iv_national_image,*/ iv_cancel;
    CircleImageView civ_user_image, civ_edit/*, civ_edit2*/;
    Button btn_save;
    int id = 0;

    /**
     * Image view
     */

    View Camera_view;
    ImageView close, cam, gal;
    static final int PICK_IMAGE_REQUEST = 1;
    static final int CAMERA_REQUEST = 1888;
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    Uri imageUri;
    int imageType;
    final int USER_IMAGE = 0;
    final int USER_SSN = 1;
    private FilesModel userImageFile, userSSNCode;
    private AttachmentModel user, ssn;
    DatePickerDialog.OnDateSetListener datePicker;
    DatePickerDialog.OnDateSetListener DatePicker1;

    /**
     * to get data from realm and initialize it to popup edit views
     **/

    UserModel profObject;

    public static FragmentProfileHome setId(int id) {
        Id = id; //TODO
//        Id = 1;
        return new FragmentProfileHome();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        mLoadingViewConstraintLayout = view.findViewById(R.id.cl_loading);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = view.findViewById(R.id.rec);
        txt_name = view.findViewById(R.id.name);
        txt_job_title = view.findViewById(R.id.job_title);
        txt_location = view.findViewById(R.id.location);
        txt_bio = view.findViewById(R.id.bio);
        txt_dateOfBirth = view.findViewById(R.id.dateOfBirth);
        txt_email = view.findViewById(R.id.email);
        txt_phone = view.findViewById(R.id.phone);
        fab_edit = view.findViewById(R.id.edit);
        cir_user_image = view.findViewById(R.id.user_image);
        txt_num_projects = view.findViewById(R.id.num_projects);
//        ctl = view.findViewById(R.id.collapsing_toolbar);

        recyclerView.setLayoutManager(layoutManager);
        offersList = new ArrayList<>();
        fragmentManager = getFragmentManager();
        realm = Realm.getDefaultInstance();
//        viewPager = (ViewPager) view.findViewById(R.id.pager);
        userImageFile = new FilesModel();
        userSSNCode = new FilesModel();
        user = new AttachmentModel();
        ssn = new AttachmentModel();
        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).setTitle(getString(R.string.profileSettings));
        ((DrawerActivity) getActivity()).hideSearchBar();
        ((DrawerActivity) getActivity()).hideFab();
//        ctl.setCollapsedTitleTextAppearance(R.style.coll_toolbar_title);
//        ctl.setExpandedTitleTextAppearance(R.style.exp_toolbar_title);
        mLoadingViewConstraintLayout.setVisibility(View.GONE);
//        viewPager.setAdapter(new pager(fragmentManager));
        adapter = new AdapterProfileProject(getActivity(), offersList);
        recyclerView.setAdapter(adapter);

        realm.executeTransaction(realm1 -> {
            LoginDataBase loginDataBases = realm1.where(LoginDataBase.class)
                    .findFirst();
//            Log.i("Name", loginDataBases.getUser().getFullName());
//            Log.i("Num Users", loginDataBases.getOffers().get(0).getUsers().size()+"");
            if (Id != loginDataBases.getUser().getId()) {
                loadProfile(Id);
                fab_edit.setVisibility(View.GONE);
            } else {
                fab_edit.setVisibility(View.VISIBLE);
                loadProfileFromDB(loginDataBases);
            }
        });

        fab_edit.setOnClickListener(v -> {

            realm.executeTransaction(realm1 -> {
                id = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
            });

            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            edit_data = inflater.inflate(R.layout.popup_edit_personal_data, null);
            iv_cancel = edit_data.findViewById(R.id.iv_cancel);
            ed_name = edit_data.findViewById(R.id.ed_name);
            ed_bio = edit_data.findViewById(R.id.ed_bio);
            ed_address = edit_data.findViewById(R.id.ed_address);
            ed_job = edit_data.findViewById(R.id.ed_job);
//            ed_email = edit_data.findViewById(R.id.ed_email);
            ed_phone = edit_data.findViewById(R.id.ed_phone);
//            ed_national_id = edit_data.findViewById(R.id.ed_national_id);
//            ed_password = edit_data.findViewById(R.id.ed_password);
            tv_birth_date = edit_data.findViewById(R.id.tv_birth_date);
            rb_gender = edit_data.findViewById(R.id.rb_gender);
            male = edit_data.findViewById(R.id.male);
            female = edit_data.findViewById(R.id.female);
//            iv_national_image = edit_data.findViewById(R.id.iv_national_image);
            civ_user_image = edit_data.findViewById(R.id.civ_user_image);
            civ_edit = edit_data.findViewById(R.id.civ_edit);
//            civ_edit2 = edit_data.findViewById(R.id.civ_edit2);
            btn_save = edit_data.findViewById(R.id.btn_save);

            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false)
                    .setView(edit_data);

            final AlertDialog dialog = builder.create();
            dialog.show();

            iv_cancel.setOnClickListener(v1 -> {
                dialog.dismiss();
            });

            /**
             * Display default values stored in realm
             * */

            ed_name.setText(profObject.getFullName());
            ed_bio.setText(profObject.getBio());
            ed_address.setText(profObject.getAddress());
            ed_job.setText(profObject.getJobtitle());
//            ed_email.setText(profObject.getMail());
            ed_phone.setText(profObject.getPhone());
//            ed_national_id.setText(profObject.getIdentityNum());
//            ed_password.setText(profObject.getPassword());
            tv_birth_date.setText(profObject.getDateOfBirth());
            if (profObject.getGender())
                male.setChecked(true);
            else
                female.setChecked(true);
            /**
             * replace code with one which get Image from phone if exist
             * */
            if (profObject.getImage() != null && !profObject.getImage().isEmpty())
                Picasso.get().load(profObject.getImage()).into(civ_user_image);

//
//            if (profObject.getIdentityImage() != null && !profObject.getIdentityImage().isEmpty())
//                Picasso.get().load(profObject.getIdentityImage()).into(cir_user_image);

            civ_user_image.setOnClickListener(v1 -> {
                imageType = USER_IMAGE;
                addImage();
            });

            civ_edit.setOnClickListener(v1 -> {
                imageType = USER_IMAGE;
                addImage();
            });

//            iv_national_image.setOnClickListener(v1 -> {
//                imageType = USER_SSN;
//                addImage();
//            });
//
//            civ_edit2.setOnClickListener(v1 -> {
//                imageType = USER_SSN;
//                addImage();
//            });

            tv_birth_date.setOnClickListener(v1 -> {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , DatePicker1
                        , year, month, day);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();
            });

            DatePicker1 = (view, year, month, dayOfMonth) -> {
                month = month + 1;
                String dateTo = month + "/" + dayOfMonth + "/" + year;
                tv_birth_date.setText(dateTo);
            };

            btn_save.setOnClickListener(v1 -> {
                if (id != 0) {
                    /** Validation **/

                    copyFilesUploadFilesAddOffer(userImageFile, USER_IMAGE);
//                    copyFilesUploadFilesAddOffer(userSSNCode, USER_SSN);

                    UserModel model = new UserModel();

                    realm.executeTransaction(realm1 -> {
                        model.setSocialId(realm1.where(LoginDataBase.class).findFirst().getUser().getSocialId());
                    });

                    model.setId(id);
                    model.setFullName(ed_name.getText().toString());
                    model.setPhone(ed_phone.getText().toString());
                    model.setJobtitle(ed_job.getText().toString());
//                    model.setIdentityNum(ed_national_id.getText().toString());
                    model.setBio(ed_bio.getText().toString());
                    model.setDateOfBirth(tv_birth_date.getText().toString());
                    model.setAddress(ed_address.getText().toString());
//                    model.setMail(ed_email.getText().toString());
//                    model.setPassword(ed_password.getText().toString());
                    if (rb_gender.getCheckedRadioButtonId() == R.id.male) {
                        model.setGender(true);
                    } else {
                        model.setGender(false);
                    }

                    /**
                     * upload userImage
                     * //upload user SSN image (Not Here) in settings page
                     * */

                    model.setImage(profObject.getImage());

//                    model.setIdentityImage(profObject.getIdentityImage());
                    mLoadingViewConstraintLayout.setVisibility(View.VISIBLE);
                    editAction(model);
                    dialog.dismiss();
                }
            });

        });

    }

    private void copyFilesUploadFilesAddOffer(FilesModel model, int type) {

        Uri uri = model.getFileUri();

        try /*(Cursor cursor = getContext().getContentResolver()
                .query(uri, null, null, null, null, null))*/ {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
//            if (cursor != null && cursor.moveToFirst()) {
//                final String displayName = cursor.getString(
//                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

            if (copyFileToProjectDirectory(model, "fee")) {
                //Load New File Location
                uri = model.getFileUri();
                Log.v("NewFileUri", uri.getPath());

                // Map is used to multipart the file using okhttp3.RequestBody
                File file = new File(uri.getPath());
                RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);

                // Parsing any Media type file
                final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                RetrofitMethods getResponse = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
                Call<List<String>> call = getResponse.uploadFile(fileToUpload, filename);
                call.enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<String>> call, @NonNull retrofit2.Response<List<String>> response) {
                        List<String> serverResponse = response.body();
                        if (serverResponse != null) {
                            if (!serverResponse.isEmpty()) {
                                String url = serverResponse.get(0);//get file url
                                if (type == USER_IMAGE) {
                                    user = new AttachmentModel(
                                            "fee",
                                            url,
                                            false
                                    );
                                    profObject.setImage(url);
                                    Log.v("getImage", profObject.getImage());
                                }
//                                else {
//                                    ssn = new AttachmentModel(
//                                            "fee",
//                                            url,
//                                            false
//                                    );
//                                    profObject.setIdentityImage(url);
//                                    Log.v("getIdentityImage", profObject.getIdentityImage());
//                                }

                                Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Failed To Upload File.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Failed To Upload File.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                        //textView.setText(t.getMessage());
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
//            }

        } catch (Exception e) {

            Log.v("getIdentityImage", "bug");
        }
    }

    private boolean copyFileToProjectDirectory(FilesModel model, String displayName) {
        try {
            String fileType = "Images";

            File destFile;
            if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {
                destFile = new File(Environment.getExternalStoragePublicDirectory(
                        getString(R.string.app_name) + File.separator + fileType), displayName);

                if (!destFile.getParentFile().exists()) destFile.getParentFile().mkdirs();

                if (!destFile.exists()) destFile.createNewFile();

            } else return false;

            OutputStream destOutputStream = new FileOutputStream(destFile);
            InputStream srcInputStream = getContext().getContentResolver().openInputStream(model.getFileUri());

            byte[] buffer = new byte[1024];
            int length;
            while ((length = srcInputStream.read(buffer)) > 0) {
                destOutputStream.write(buffer, 0, length);
            }

            Log.d("MODELSS", "File Copied Successfully.");
            model.setFileUri(Uri.parse(destFile.getPath()));
            Log.v("NewFileUrl", destFile.getPath());

        } catch (IOException e) {
            Toast.makeText(getContext(), "Failed to copy file." + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addImage() {
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Camera_view = inflater.inflate(R.layout.camera_view, null);

        close = Camera_view.findViewById(R.id.close);
        cam = Camera_view.findViewById(R.id.cam);
        gal = Camera_view.findViewById(R.id.gal);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false)
                .setView(Camera_view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        gal.setOnClickListener(v1 -> {
            openGallery();
            dialog.dismiss();
        });

        cam.setOnClickListener(v12 -> {
            openCamera();
            dialog.dismiss();
        });

        close.setOnClickListener(v13 -> {
            dialog.dismiss();

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openGallery() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);

        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);// ACTION_VIEW
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                (dialog, which) -> ActivityCompat.requestPermissions((Activity) context,
                        new String[]{permission},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE));
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);

        } else {
            if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("PageNo", 2);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }

        }

    }

    private void editAction(UserModel userModel) {
        Gson gson = new Gson();

        Retrofit retrofit = new RetrofitConfigurations(API.BASE_URL).getRetrofit();

        RetrofitMethods retrofitService = retrofit.create(RetrofitMethods.class);
        Log.e("Request Model", gson.toJson(userModel));

        Call<String> response = retrofitService.editProfile(gson.toJson(userModel), "null", API.URL_TOKEN);

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.errorBody() == null) {
                    if (response.body() != null && response.body().equals("Success")) {
                        Toast.makeText(getContext(), "Profile # Successfully.", Toast.LENGTH_SHORT).show();
                        updateUserDB(userModel);
                    } else
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                    Log.d("MODELSS", response.errorBody().toString());
                }
                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("MODELSS", t.getCause().getMessage());
                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }
        });

    }

    private void updateUserDB(UserModel userModel) {
        realm.beginTransaction();
        UserDataBase userDataBase = realm.where(LoginDataBase.class).findFirst().getUser();
        if (userDataBase != null) {
            userDataBase.setFullName(userModel.getFullName());
            userDataBase.setAddress(userModel.getAddress());
            userDataBase.setBio(userModel.getBio());
            userDataBase.setDateOfBirth(userModel.getDateOfBirth());
            userDataBase.setGender(userModel.getGender());
            userDataBase.setJobtitle(userModel.getJobtitle());
            userDataBase.setPhone(userModel.getPhone());
            userDataBase.setImage(userModel.getImage());
            userDataBase.setIdentityImage(userModel.getIdentityImage());
            userDataBase.setSocialId(userModel.getSocialId());
        }
        realm.commitTransaction();
        /** Reload profile data */
        profObject = loadUser(userDataBase);
        fillProfData(profObject);
        ((DrawerActivity) getActivity()).updateNavData(userModel);
    }

    private void loadProfileFromDB(LoginDataBase loginDataBases) {
        UserDataBase userDataBase = loginDataBases.getUser();
        RealmList<OfferDataBase> offerDetailsDataBase = loginDataBases.getOffers();
        profObject = loadUser(userDataBase);
        List<Offers> offers = loadOffers(offerDetailsDataBase);
        fillProfData(profObject);
        Gson gson = new Gson();
        Log.e("Gson", gson.toJson(offers));
        fillProfOffersData(offers);

        mLoadingViewConstraintLayout.setVisibility(View.GONE);
    }

    private List<Offers> loadOffers(List<OfferDataBase> offerDetailsDataBase) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
//        Log.i("Gson", gson.toJson(offerDetailsDataBase.toString()));
        List<Offers> offers = new ArrayList<>();
        for (int i = 0; i < offerDetailsDataBase.size(); i++) {
            Offers offers1 = new Offers();
            OfferDataBase base = offerDetailsDataBase.get(i);
            offers1.setUserId(base.getUserId());
            offers1.setId(base.getId());
//            offers1.setCategoryId(base.getCategoryId());
//            offers1.setCategoryName(base.getCategoryName());
            offers1.setDescription(base.getDescription());
            offers1.setName(base.getName());
            offers1.setAddress(base.getAddress());
//            offers1.setAgeRequiredFrom(base.getAgeRequiredFrom());
//            offers1.setAgeRequiredTo(base.getAgeRequiredTo());
//            offers1.setDate(base.getDate());
//            offers1.setEducationContributorLevel(base.getEducationContributorLevel());
//            offers1.setNumContributorFrom(base.getNumContributorFrom());
            offers1.setNumContributor(base.getNumContributor());
            offers1.setGenderContributor(base.getGenderContributor());
            offers1.setNumJoinOffer(base.getNumJoinOffer());
            offers1.setNumLiks(base.getNumLiks());
//            offers1.setProfitFrom(base.getProfitFrom());
//            offers1.setProfitTo(base.getProfitTo());
//            offers1.setProfitType(base.getProfitType());
            offers1.setStatus(base.getStatus());
            if (base.getUsers() != null && base.getUsers().size() > 0) {
                List<UserModel> userModels = new ArrayList<>();
                for (int j = 0; j < base.getUsers().size(); j++) {
                    UserModel userModel = new UserModel();
                    UserDataBase base1 = base.getUsers().get(j);
                    userModel.setId(base1.getId());
                    userModel.setFullName(base1.getFullName());
                    userModel.setImage(base1.getImage());
                    userModel.setId(base1.getId());
                    userModels.add(userModel);
                }
                offers1.setUsers(userModels);
            }
            offers.add(offers1);
//            List<RequirmentModel> rec = new ArrayList<>();
//            for (int j = 0; j < base.getRequirments().size(); j++) {
//                RequirmentModel requirmentModel = new RequirmentModel();
//                OfferDetailsRequirmentDataBase re = base.getRequirments().get(j);
//                requirmentModel.setExperienceDescriptions(re.getExperienceDescriptions());
//                requirmentModel.setNeedExperience(re.isNeedExperience());
//                requirmentModel.setNeedPlaceType(re.isNeedPlaceType());
//                requirmentModel.setNeedPlace(re.isNeedPlace());
//                requirmentModel.setNeedMoney(re.isNeedMoney());
//                requirmentModel.setPlaceAddress(re.getPlaceAddress());
//                requirmentModel.setNeedPlaceStatus(re.isNeedPlaceStatus());
//            }
        }
        return offers;
    }

    private UserModel loadUser(UserDataBase userDataBase) {
        UserModel model = new UserModel();
        model.setSocialId(userDataBase.getSocialId());
        model.setPassword(userDataBase.getPassword());
        model.setMail(userDataBase.getMail());
        model.setId(userDataBase.getId());
        model.setImage(userDataBase.getImage());
        model.setFullName(userDataBase.getFullName());
        model.setGender(userDataBase.getGender());
        model.setDateOfBirth(userDataBase.getDateOfBirth());
        model.setAddress(userDataBase.getAddress());
        model.setBio(userDataBase.getBio());
        model.setCapitalId(userDataBase.getCapitalId());
        model.setCode(userDataBase.getCode());
        model.setIdentityImage(userDataBase.getIdentityImage());
        model.setIdentityNum(userDataBase.getIdentityNum());
        model.setCoded(userDataBase.getCoded());
        model.setJobtitle(userDataBase.getJobtitle());
        model.setPhone(userDataBase.getPhone());
        model.setStatus(userDataBase.getStatus());
        return model;
    }

    private void loadProfile(int id) {
        // Map is used to multipart the file using okhttp3.RequestBody
        RetrofitConfigurations retrofitConfigurations = new RetrofitConfigurations(API.BASE_URL);

        final RetrofitMethods profile = retrofitConfigurations.getRetrofit().create(RetrofitMethods.class);
        Call<ProfileResponse> call = profile.getProfile(id, API.URL_TOKEN);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull retrofit2.Response<ProfileResponse> response) {
                ProfileResponse allData = response.body();
                UserModel profObject = allData.getUserDetails();
                List<Offers> offers = allData.getListOffer();
                Gson gson = new Gson();
                Log.e("GS", gson.toJson(profObject));
                fillProfData(profObject);
                fillProfOffersData(offers);
                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                Log.d("loadProfile", t.getMessage());
                mLoadingViewConstraintLayout.setVisibility(View.GONE);
            }
        });
    }

    private void fillProfOffersData(List<Offers> offers) {
        txt_num_projects.setText(String.valueOf(offers.size()));
//        offersList = new ArrayList<>();
        offersList.addAll(offers);
        adapter.notifyDataSetChanged();
    }

    private void fillProfData(UserModel user) {
//        ctl.setTitle(user.getFullName());
        txt_name.setText(user.getFullName());

        if (user.getJobtitle() != null && !user.getJobtitle().isEmpty())
            txt_job_title.setText(user.getJobtitle());

        if (user.getAddress() != null && !user.getAddress().isEmpty())
            txt_location.setText(user.getAddress());

        if (user.getBio() != null && !user.getBio().isEmpty())
            txt_bio.setText(user.getBio());

        if (user.getDateOfBirth() != null && !user.getDateOfBirth().isEmpty())
            txt_dateOfBirth.setText(user.getDateOfBirth());

        if (user.getMail() != null && !user.getMail().isEmpty())
            txt_email.setText(user.getMail());

        if (user.getPhone() != null && !user.getPhone().isEmpty())
            txt_phone.setText(user.getPhone());

        if (user.getNumProject() != null)
            txt_num_projects.setText(String.valueOf(user.getNumProject()));

        if (user.getImage() != null && !user.getImage().isEmpty() && user.getImage() != null) {
            Picasso.get().load(user.getImage()).into(cir_user_image);
        }
    }

    class pager extends FragmentPagerAdapter {

        public pager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentSwipe1();
            } else if (position == 1) {
                fragment = new FragmentSwipe2();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            new Handler().post(() -> {

                if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
                    ClipData mClipData = data.getClipData();
                    if (mClipData == null) {
                        Uri uri = data.getData();
                        imageUri = uri;
                        userImageFile.setFileUri(imageUri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            bitmap = getResizedBitmap(bitmap, 65);
                            if (imageType == USER_IMAGE) {
                                civ_user_image.setImageBitmap(bitmap);
                            }
//                            else if (imageType == USER_SSN) {
//                                iv_national_image.setImageBitmap(bitmap);
//                            }
                            Toast.makeText(getActivity(), "Image:\n" + uri, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    bitmap = getResizedBitmap(bitmap, 65);
                    if (imageType == USER_IMAGE) {
                        civ_user_image.setImageBitmap(bitmap);
                    }
//                    else if (imageType == USER_SSN) {
//                        iv_national_image.setImageBitmap(bitmap);
//                    }
                    if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                        imageUri = getImageUri(getActivity(), bitmap);
                        userImageFile.setFileUri(imageUri);
                    }
                }

            });
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 65, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
