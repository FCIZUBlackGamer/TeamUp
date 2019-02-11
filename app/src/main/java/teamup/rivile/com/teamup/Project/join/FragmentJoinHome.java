package teamup.rivile.com.teamup.Project.join;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.Project.Details.OfferDetails;
import teamup.rivile.com.teamup.Project.Details.OfferDetailsRequirment;
import teamup.rivile.com.teamup.Project.join.Adapters.CapitalsRecyclerViewAdapter;
import teamup.rivile.com.teamup.Project.join.Adapters.ChipsAdapter;
import teamup.rivile.com.teamup.Project.join.Adapters.FilesAdapter;
import teamup.rivile.com.teamup.Project.join.Adapters.ImagesAdapter;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.AttachmentModel;
import teamup.rivile.com.teamup.Uitls.APIModels.CapitalModel;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;
import teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel;
import teamup.rivile.com.teamup.Uitls.AppModels.FilesModel;

public class FragmentJoinHome extends Fragment {

    //region variable declarations
    private static final int PICK_FILE_REQUEST_CODE = 10;
    static final int PICK_IMAGE_REQUEST = 1;
    static final int CAMERA_REQUEST = 1888;
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    private RequirmentModel mRequirementModel = new RequirmentModel();
    View view;
    RelativeLayout money, contributors;
    LinearLayout moneySection, contributorsSection;

    TextView project_name, numCon;
    RadioButton profitTypeRadioButton, availMoneyRadioButton, genderRadioButton;
    StepperIndicator educationLevel;

    EditText moneyInEditText;

    FloatingActionButton arrowContributors, arrowMoney;

    CircleImageView userImage;
    /********************************************************************/
    RelativeLayout place, experience;
    LinearLayout placeSection, experienceSection;

    RadioButton placeRadioButton, placeKindRadioButton, placeStateRadioButton, exRadioButton;
    EditText experienceEditText, exDesc;
    RecyclerView exRec;
    FloatingActionButton arrowPlace, arrowExperience;

    View map;
    /********************************************************************/
    CapitalsRecyclerViewAdapter mCapitalsRecyclerViewAdapter;

    ImageView close, minimize, cam, gal;
    View Camera_view;
    int close_type;

    ArrayList<AttachmentModel> mAttachmentModelArrayList = new ArrayList<>();

    RelativeLayout attachment, cap, dep, tags;
    LinearLayout attachmentSection, CapSection, DepSection, tagSection;

    ImageView doc, image, preview, delete;

    RecyclerView recFiles, recImages;
    RecyclerView.Adapter imagesAdapter, filesAdapter;
    RecyclerView recCapitals;
    CheckBox egypt;
    EditText tagsInput;
    Button Join;
    private ArrayList<Uri> imagesArrayUri, fileArrayUri;
    List<FilesModel> filesModels;

    TextView categoryTextView;

    FloatingActionButton arrowAttachments, arrowCapitals, arrowDepartments, arrowTags;
    RelativeLayout viewPreview;

    FilesModel currentFileModel;

    ChipsAdapter mTagsRecUserAdapter;
    RecyclerView tagsRec;

    public static int mOfferId = -1;

    private static MutableLiveData<OfferDetailsJsonObject> mOffer = new MutableLiveData<>();

    //endregion variable declarations

    public static FragmentJoinHome setOfferId(int offerId) {
        mOfferId = offerId;

        return new FragmentJoinHome();
    }

    private void loadOffer() {
        Retrofit retrofit = new AppConfig(API.BASE_URL).getRetrofit();

        ApiConfig retrofitService = retrofit.create(ApiConfig.class);

        Call<OfferDetailsJsonObject> response = retrofitService.offerDetails(mOfferId, API.URL_TOKEN);

        response.enqueue(new Callback<OfferDetailsJsonObject>() {
            @Override
            public void onResponse(@NonNull Call<OfferDetailsJsonObject> call, @NonNull Response<OfferDetailsJsonObject> response) {
                if (response.errorBody() == null) {
                    if (response.body() != null) {
                        mOffer.postValue(response.body());
                    } else
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Call<OfferDetailsJsonObject> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadOffer();

        view = inflater.inflate(R.layout.fragment_join_home, container, false);
        /** Shrink and Expand Views */
        money = view.findViewById(R.id.money);
        contributors = view.findViewById(R.id.contributors);
        moneySection = view.findViewById(R.id.moneySection);
        contributorsSection = view.findViewById(R.id.contributorsSection);
        arrowMoney = view.findViewById(R.id.arrowMoney);
        arrowContributors = view.findViewById(R.id.arrowContributors);
        /** Input Views */

        project_name = view.findViewById(R.id.project_name);

        genderRadioButton = view.findViewById(R.id.rb_gender);
        profitTypeRadioButton = view.findViewById(R.id.rb_profit_type);
        availMoneyRadioButton = view.findViewById(R.id.rb_availMoney);

        educationLevel = view.findViewById(R.id.educationLevel);

        numCon = view.findViewById(R.id.tv_con);

        moneyInEditText = view.findViewById(R.id.ed_moneyIn);

        userImage = view.findViewById(R.id.user_image);

        /***********************************************/
        /** Shrink and Expand Views */
        place = view.findViewById(R.id.place);
        experience = view.findViewById(R.id.experience);
        placeSection = view.findViewById(R.id.placeSection);
        experienceSection = view.findViewById(R.id.experienceSection);
        arrowPlace = view.findViewById(R.id.arrowPlace);
        arrowExperience = view.findViewById(R.id.arrowExperiance);
        /** Input Views */

        placeRadioButton = view.findViewById(R.id.rb_place);

        placeKindRadioButton = view.findViewById(R.id.rb_placeKind);

        placeStateRadioButton = view.findViewById(R.id.rb_placeState);

        exRadioButton = view.findViewById(R.id.rb_ex);

        experienceEditText = view.findViewById(R.id.ed_experience);
        exDesc = view.findViewById(R.id.exDesc);

        exRec = view.findViewById(R.id.exRec);
        map = view.findViewById(R.id.map);

        /************************************************/
        //Shrink and Expand Views
        attachment = view.findViewById(R.id.attachment);
        cap = view.findViewById(R.id.cap);
        dep = view.findViewById(R.id.dep);
        tags = view.findViewById(R.id.tags);
        attachmentSection = view.findViewById(R.id.attachmentSection);
        CapSection = view.findViewById(R.id.CapSection);
        DepSection = view.findViewById(R.id.DepSection);
        tagSection = view.findViewById(R.id.tagSection);
        arrowAttachments = view.findViewById(R.id.arrowAttachments);
        arrowDepartments = view.findViewById(R.id.arrowDepartments);
        arrowCapitals = view.findViewById(R.id.arrowCapitals);
        arrowTags = view.findViewById(R.id.arrowTags);
        // Input Views

        doc = view.findViewById(R.id.doc);
        image = view.findViewById(R.id.image);
        viewPreview = view.findViewById(R.id.view);
        preview = view.findViewById(R.id.preview);

        delete = view.findViewById(R.id.delete);
        recFiles = view.findViewById(R.id.recFiles);
        recImages = view.findViewById(R.id.recImages);
        recCapitals = view.findViewById(R.id.recCapitals);
        egypt = view.findViewById(R.id.egypt);
        tagsInput = view.findViewById(R.id.tagsInput);
        Join = view.findViewById(R.id.go);
        imagesArrayUri = new ArrayList<>();
        fileArrayUri = new ArrayList<>();

        if (filesModels == null) {
            filesModels = new ArrayList<>();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recImages.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recFiles.setLayoutManager(layoutManager1);

        tagsRec = view.findViewById(R.id.tagsRec);

        categoryTextView = view.findViewById(R.id.tv_category);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        setUpRecyclerViews();

        mOffer.observe(this, offerDetailsJsonObject -> {
            if (offerDetailsJsonObject != null) {
                OfferDetails details = offerDetailsJsonObject.getOffers();
                project_name.setText(details.getName());

                String profitType = null;
                if (details.getProfitType() == 0) profitType = getString(R.string.day);
                else if (details.getProfitType() == 1) profitType = getString(R.string.month);
                else if (details.getProfitType() == 2) profitType = getString(R.string.year);
                else if (details.getProfitType() == 3) profitType = getString(R.string.anotherKind);
                profitTypeRadioButton.setText(profitType);

                if (details.getRequirments() != null && !details.getRequirments().isEmpty()) {
                    String availMoney = details.getRequirments().get(0).isNeedMoney() ?
                            getString(R.string.yes) : getString(R.string.no);
                    availMoneyRadioButton.setText(availMoney);

                    if (details.getRequirments().get(0).isNeedMoney()) {
                        moneyInEditText.setEnabled(true);
                        moneyInEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (!s.toString().isEmpty()) {
                                    mRequirementModel.setMoneyTo(Integer.valueOf(s.toString()));
                                } else {
                                    moneyInEditText.setText("0");
                                    mRequirementModel.setMoneyTo(0);
                                }
                            }
                        });
                    } else moneyInEditText.setEnabled(false);
                }

                String gender = null;
                if (details.getGenderContributor() == 0) gender = getString(R.string.male);
                else if (details.getGenderContributor() == 1) gender = getString(R.string.female);
                else if (details.getGenderContributor() == 2) gender = getString(R.string.both);
                genderRadioButton.setText(gender);

                //TODO
//                conFrom.setText(String.valueOf(details.getNumContributorFrom()));
//                conTo.setText(String.valueOf(details.getNumContributorTo()));

                educationLevel.setCurrentStep(details.getEducationContributorLevel());
            } else {
                Toast.makeText(getContext(), "Error Loading Offer Details.", Toast.LENGTH_LONG).show();
            }

            if (offerDetailsJsonObject != null) {
                OfferDetails details = offerDetailsJsonObject.getOffers();
                OfferDetailsRequirment requirementModel = details.getRequirments().get(0);
                if (requirementModel != null) {
                    placeRadioButton.setText(requirementModel.isNeedPlace() ?
                            getString(R.string.yes) : getString(R.string.no));

                    if (!requirementModel.isNeedPlace() || requirementModel.isNeedPlaceStatus()) {
                        placeSection.setVisibility(View.GONE);
                    } else {
                        mRequirementModel.setPlaceAddress("Address Avoiding Null, Added From Join Offer");
                    }

                    placeStateRadioButton.setText(requirementModel.isNeedPlaceStatus() ?
                            getString(R.string.avail) : getString(R.string.notAvail));

                    placeKindRadioButton.setText(requirementModel.isNeedPlaceType() ?
                            getString(R.string.owned) : getString(R.string.rent));

                    exRadioButton.setText(requirementModel.isNeedExperience() ?
                            getString(R.string.yes) : getString(R.string.no));

                    experienceEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (!s.toString().isEmpty()) {
                                mRequirementModel.setExperienceTo(Integer.valueOf(s.toString()));
                            } else {
                                experienceEditText.setText("0");
                                mRequirementModel.setExperienceTo(0);
                            }
                        }
                    });
                    exDesc.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            mRequirementModel.setExperienceDescriptions(s.toString());
                        }
                    });
                }
            }

            if (offerDetailsJsonObject != null) {
                OfferDetails details = offerDetailsJsonObject.getOffers();
                mOfferId = details.getId();

                List<CapitalModel> capitalModels = details.getCapitals();
                if (capitalModels != null && !capitalModels.isEmpty()) {
                    mCapitalsRecyclerViewAdapter.swapData(capitalModels);
                }

                categoryTextView.setText(details.getCategoryName());
            }
        });

        //region Shrink And Expand

        money.setOnClickListener(v -> {
            if (moneySection.getVisibility() == View.VISIBLE) {
                moneySection.setVisibility(View.GONE);
                arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                moneySection.setVisibility(View.VISIBLE);
                arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
            }
        });

        contributors.setOnClickListener(v -> {
            if (contributorsSection.getVisibility() == View.VISIBLE) {
                contributorsSection.setVisibility(View.GONE);
                arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                contributorsSection.setVisibility(View.VISIBLE);
                arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
            }
        });

        //endregion

        //region Shrink And Expand

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (placeSection.getVisibility() == View.VISIBLE) {
                    placeSection.setVisibility(View.GONE);
                    arrowPlace.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                } else {
                    placeSection.setVisibility(View.VISIBLE);
                    arrowPlace.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });

        experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (experienceSection.getVisibility() == View.VISIBLE) {
                    experienceSection.setVisibility(View.GONE);
                    arrowExperience.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                } else {
                    experienceSection.setVisibility(View.VISIBLE);
                    arrowExperience.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });

        // endregion

        //region Shrink And Expand

        attachment.setOnClickListener(v -> {
            if (attachmentSection.getVisibility() == View.VISIBLE) {
                attachmentSection.setVisibility(View.GONE);
                arrowAttachments.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                attachmentSection.setVisibility(View.VISIBLE);
                arrowAttachments.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
            }
        });

        cap.setOnClickListener(v -> {
            if (CapSection.getVisibility() == View.VISIBLE) {
                CapSection.setVisibility(View.GONE);
                arrowCapitals.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                CapSection.setVisibility(View.VISIBLE);
                arrowCapitals.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
            }
        });

        dep.setOnClickListener(v -> {
            if (DepSection.getVisibility() == View.VISIBLE) {
                DepSection.setVisibility(View.GONE);
                arrowDepartments.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                DepSection.setVisibility(View.VISIBLE);
                arrowDepartments.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
            }
        });

        tags.setOnClickListener(v -> {
            if (tagSection.getVisibility() == View.VISIBLE) {
                tagSection.setVisibility(View.GONE);
                arrowTags.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                tagSection.setVisibility(View.VISIBLE);
                arrowTags.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
            }
        });
        // endregion

        imagesAdapter = new ImagesAdapter(getActivity(), filesModels, item -> {
            try {
                viewPreview.setVisibility(View.VISIBLE);
                currentFileModel = item;
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), item.getFileUri());
                preview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        filesAdapter = new FilesAdapter(getActivity(), filesModels, item -> {
            try {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + getFileName(item.getFileUri()));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "text/plain");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        recFiles.setAdapter(filesAdapter);

        if (filesModels.size() > 0) {
            viewPreview.setVisibility(View.VISIBLE);
        } else {
            viewPreview.setVisibility(View.GONE);
        }

        recImages.setAdapter(imagesAdapter);

        if (filesModels.size() > 0) {
            viewPreview.setVisibility(View.VISIBLE);
        } else {
            viewPreview.setVisibility(View.GONE);
        }

        delete.setOnClickListener(v -> {
            filesModels.remove(currentFileModel);
            imagesAdapter.notifyDataSetChanged();
            if (filesModels.size() > 0) {
                viewPreview.setVisibility(View.VISIBLE);
                try {
                    currentFileModel = filesModels.get(0);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filesModels.get(0).getFileUri());
                    preview.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                viewPreview.setVisibility(View.GONE);
            }

        });

        Join.setOnClickListener(v -> new Handler().post(() -> {
            if (mOfferId != -1) {
                if (!filesModels.isEmpty())
                    copyFilesUploadFilesJoinOffer();
                else joinOffer();
            }
        }));

        doc.setOnClickListener(v -> {
            /* Open Storage Files*/
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            //Limit selection to images an pdf files only
            intent.setType("application/pdf|text/plain");
            String[] mimeTypes = {"application/pdf", "|text/plain"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

            //local storage only
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
        });

        image.setOnClickListener(v -> {
            /** Choose Either Camera Or Gallery */
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Camera_view = inflater.inflate(R.layout.camera_view, null);

            close = Camera_view.findViewById(R.id.close);
            minimize = Camera_view.findViewById(R.id.minimize);
            cam = Camera_view.findViewById(R.id.cam);
            gal = Camera_view.findViewById(R.id.gal);

            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setCancelable(false)
                    .setView(Camera_view);

            final android.app.AlertDialog dialog = builder.create();
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
                close_type = 0;
                dialog.dismiss();

            });
        });

    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
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

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            viewPreview.setVisibility(View.VISIBLE);
            new Handler().post(() -> {
                if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                    /** We Got The File */
                    /** Save File to Local Folder and get Uri and add it to (fileArrayUri) */
                    Toast.makeText(getActivity(), "File", Toast.LENGTH_SHORT).show();

                    ClipData mClipData = data.getClipData();
                    if (mClipData == null) {
                        Uri uri = data.getData();
                        //String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                        FilesModel s = new FilesModel(uri);
                        s.setFileName(getFileName(s.getFileUri()));
                        Log.e("File1", s.getFileName());
                        filesModels.add(s);

                    } else {
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();

                            //String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                            FilesModel s = new FilesModel(uri);
                            s.setFileName(getFileName(s.getFileUri()));
                            Log.e("File2", s.getFileName());
                            filesModels.add(s);
                            // !! You may need to resize the image if it's too large

                        }
                    }
                    filesAdapter.notifyDataSetChanged();
                } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
                    ClipData mClipData = data.getClipData();
                    if (mClipData == null) {
                        Uri uri = data.getData();
                        imagesArrayUri.add(uri);
                        Log.e("File3", uri + "");
                        filesModels.add(new FilesModel(uri));
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            bitmap = getResizedBitmap(bitmap, 65);
                            preview.setImageBitmap(bitmap);
                            Toast.makeText(getActivity(), "Image:\n" + uri, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            imagesArrayUri.add(uri);
                            Log.e("File4", uri + "");
                            filesModels.add(new FilesModel(uri));
                            // !! You may need to resize the image if it's too large
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                                bitmap = getResizedBitmap(bitmap, 65);
                                preview.setImageBitmap(bitmap);
                                Toast.makeText(getActivity(), "Image:\n" + uri, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    Log.e("filesModels Length", filesModels.size() + "");
                } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    bitmap = getResizedBitmap(bitmap, 65);
//                        Uri imageUri = (Uri) data.getExtras().get("data");
//                        Log.e("Index ",imageUri+"");
//                        Toast.makeText(getActivity(), "Cam", Toast.LENGTH_SHORT).show();
                    /** Save Image to Local Folder and get Uri and add it to (imagesArrayUri) */
                    preview.setImageBitmap(bitmap);
                    if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                        Uri tempUri = getImageUri(getActivity(), bitmap);


                        Log.e("File5", tempUri + "");
                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        Toast.makeText(getActivity(), "Here " + getRealPathFromURI(tempUri), Toast.LENGTH_LONG).show();

                        filesModels.add(new FilesModel(tempUri));
                    }
                }
                imagesAdapter.notifyDataSetChanged();
                for (int i = 0; i < filesModels.size(); i++) {
                    Log.e("Index " + i, filesModels.get(i).getFileUri().toString());
                }
            });
        }

        String attachments = new GsonBuilder().serializeNulls().create().toJson(mAttachmentModelArrayList);
        Log.d("DABUGGonActivityResult", "\"Attachment\": " + attachments);

        String requirements = new GsonBuilder().serializeNulls().create().toJson(mRequirementModel);
        Log.d("DABUGGonActivityResult", "\"Requirement\": " + requirements);
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

    public String getMimeType(Uri uri) {
        String mimeType;
        if (uri.getScheme() != null && uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        if (mimeType == null) mimeType = "application/*";
        return mimeType;
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

    private void openGallery() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);

        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);// ACTION_VIEW
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    }

    private void copyFilesUploadFilesJoinOffer() {
        for (int i = filesModels.size() - 1; i >= 0; --i) {
            Uri uri = filesModels.get(i).getFileUri();

            try (Cursor cursor = getContext().getContentResolver()
                    .query(uri, null, null, null, null, null)) {
                // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
                // "if there's anything to look at, look at it" conditionals.
                if (cursor != null && cursor.moveToFirst()) {
                    final String displayName = cursor.getString(
                            cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    if (copyFileToProjectDirectory(uri, displayName, i)) {
                        //Load New File Location
                        uri = filesModels.get(i).getFileUri();
                        Log.v("NewFileUri", uri.getPath());

                        // Map is used to multipart the file using okhttp3.RequestBody
                        File file = new File(uri.getPath());
                        AppConfig appConfig = new AppConfig(API.BASE_URL);

                        // Parsing any Media type file
                        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                        ApiConfig getResponse = appConfig.getRetrofit().create(ApiConfig.class);
                        Call<List<String>> call = getResponse.uploadFile(fileToUpload, filename);
                        Uri finalUri = uri;
                        call.enqueue(new Callback<List<String>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<String>> call, @NonNull retrofit2.Response<List<String>> response) {
                                List<String> serverResponse = response.body();
                                if (serverResponse != null) {
                                    if (!serverResponse.isEmpty()) {
                                        String url = serverResponse.get(0);//get file url
                                        mAttachmentModelArrayList.add(
                                                new AttachmentModel(
                                                        displayName,
                                                        url,
                                                        !getMimeType(finalUri).substring(0, 5).equals(
                                                                "image"
                                                        )
                                                )
                                        );

                                        if (mAttachmentModelArrayList.size() == filesModels.size()) {
                                            joinOffer();
                                        } else {
                                            Toast.makeText(getContext(), "mAttachmentModelArrayList ERROR", Toast.LENGTH_SHORT).show();
                                        }

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
                }
            }
        }
    }

    private boolean copyFileToProjectDirectory(Uri srcUri, String displayName, int i) {
        try {
            String fileType = getMimeType(srcUri).substring(0, 5).equals(
                    "image"
            ) ? "Images" : "Files";

            File destFile;
            if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {
                destFile = new File(Environment.getExternalStoragePublicDirectory(
                        getString(R.string.app_name) + File.separator + fileType), displayName);

                if (!destFile.getParentFile().exists()) destFile.getParentFile().mkdirs();

                if (!destFile.exists()) destFile.createNewFile();

            } else return false;

            OutputStream destOutputStream = new FileOutputStream(destFile);
            InputStream srcInputStream = getContext().getContentResolver().openInputStream(srcUri);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = srcInputStream.read(buffer)) > 0) {
                destOutputStream.write(buffer, 0, length);
            }

            Log.d("MODELSS", "File Copied Successfully.");
            filesModels.get(i).setFileUri(Uri.parse(destFile.getPath()));
            Log.v("NewFileUrl", destFile.getPath());

        } catch (IOException e) {
            Toast.makeText(getContext(), "Failed to copy file." + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void joinOffer() {
        Retrofit retrofit = new AppConfig(API.BASE_URL).getRetrofit();

        ApiConfig retrofitService = retrofit.create(ApiConfig.class);

        String attachments = new GsonBuilder().serializeNulls().create().toJson(mAttachmentModelArrayList);
        Log.v("DABUGG", "\"Attachment\": " + attachments);

        mRequirementModel.setUserId(1);
        String requirements = new GsonBuilder().serializeNulls().create().toJson(mRequirementModel);
        Log.v("DABUGG", "\"Requirement\": " + requirements);

        Log.v("DABUGG", "OfferId: " + String.valueOf(mOfferId));

        Call<String> response = retrofitService.joinOffer(API.URL_TOKEN,
                requirements, attachments, String.valueOf(mOfferId));

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.errorBody() == null) {
                    if (response.body() != null) {
                        Log.v("DABUGG", response.body());
                        Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.v("DABUGG", "RESPONSE ERROR!");
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.v("DABUGG", response.errorBody().toString());
                    Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.v("DABUGG", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpRecyclerViews() {
        recCapitals.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mCapitalsRecyclerViewAdapter = new CapitalsRecyclerViewAdapter(null);
        recCapitals.setAdapter(mCapitalsRecyclerViewAdapter);
    }
}
