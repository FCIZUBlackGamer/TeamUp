package teamup.rivile.com.teamup.Project.Add;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.MutableLiveData;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Realm;
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
import teamup.rivile.com.teamup.Project.Add.Adapters.CapitalsRecyclerViewAdapter;
import teamup.rivile.com.teamup.Project.Add.Adapters.CategoriesRecyclerViewAdapter;
import teamup.rivile.com.teamup.Project.Add.Adapters.ChipsAdapter;
import teamup.rivile.com.teamup.Project.Add.Adapters.FilesAdapter;
import teamup.rivile.com.teamup.Project.Add.Adapters.ImagesAdapter;
import teamup.rivile.com.teamup.Project.Add.Adapters.LoadedChipsAdapter;
import teamup.rivile.com.teamup.Project.Add.StaticShit.Offers;
import teamup.rivile.com.teamup.Project.Add.StaticShit.RequirmentModel;
import teamup.rivile.com.teamup.Project.Details.OfferDetails;
import teamup.rivile.com.teamup.Project.Details.OfferDetailsRequirment;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.AttachmentModel;
import teamup.rivile.com.teamup.Uitls.APIModels.CapitalModel;
import teamup.rivile.com.teamup.Uitls.APIModels.ExperienceTypeModel;
import teamup.rivile.com.teamup.Uitls.AppModels.FilesModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDetailsDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDetailsRequirmentDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.UserDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;

public class FragmentOffer3 extends Fragment {
    private final char SPACE = ' ';
    private final char NEW_LINE = '\n';

    private static final int PICK_FILE_REQUEST_CODE = 10;
    static final int PICK_IMAGE_REQUEST = 1;
    static final int CAMERA_REQUEST = 1888;
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    ArrayList<CapitalModel> mSelectedCapitalModels = new ArrayList<>();
    CapitalsRecyclerViewAdapter mCapitalsRecyclerViewAdapter;

    CapitalModel mSelectedCategory;
    CategoriesRecyclerViewAdapter mCategoriesRecyclerViewAdapter;

    ArrayList<AttachmentModel> mAttachmentModelArrayList = new ArrayList<>();

    View view;
    RelativeLayout attachment, cap, dep, tags;
    LinearLayout attachmentSection, CapSection, DepSection, tagSection;

    ImageView doc, image, preview, delete;
    RecyclerView recFiles, recImages;
    RecyclerView.Adapter imagesAdapter, filesAdapter;
    RecyclerView recCapitals;
    RecyclerView recDep;
    CheckBox egypt;
    EditText tagsInput;
    Button go;
    private ArrayList<FilesModel> imagesArrayUri, fileArrayUri;
    List<FilesModel> finalFileModel;

    View Camera_view;
    ImageView close, minimize, cam, gal;
    FloatingActionButton arrowAttachments, arrowCapitals, arrowDepartments, arrowTags;
    int close_type;
    RelativeLayout viewPreview;


    FilesModel currentFileModel;
    Realm realm;
    OfferDetailsDataBase offerDetailsDataBase;

    ChipsAdapter mTagsRecUserAdapter;
    LoadedChipsAdapter mTagsRecLoadedAdapter;

    private int mUserId = -1;

    static MutableLiveData<ArrayList<ExperienceTypeModel>> mLoadedTags = new MutableLiveData<>();
    static MutableLiveData<ArrayList<CapitalModel>> mLoadedCapitals = new MutableLiveData<>();
    static MutableLiveData<ArrayList<CapitalModel>> mLoadedCategories = new MutableLiveData<>();
    RecyclerView tagsRecUserLoad, tagsRec;

    static ViewPager pager;
    static FragmentPagerAdapter pagerAdapter;
    private static MutableLiveData<OfferDetails> mLoadedProjectWithAllDataLiveData = null;

    static FragmentOffer3 setPager(
            ViewPager viewPager, FragmentPagerAdapter pagerAdapter,
            MutableLiveData<ArrayList<ExperienceTypeModel>> tagsArrayList,
            MutableLiveData<ArrayList<CapitalModel>> loadedCapitals,
            MutableLiveData<ArrayList<CapitalModel>> loadedCategories,
            MutableLiveData<OfferDetails> loadedProjectWithAllDataLiveData) {

        pager = viewPager;
        FragmentOffer3.pagerAdapter = pagerAdapter;
        mLoadedTags = tagsArrayList;
        mLoadedCapitals = loadedCapitals;
        mLoadedCategories = loadedCategories;
        mLoadedProjectWithAllDataLiveData = loadedProjectWithAllDataLiveData;
        return new FragmentOffer3();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3_add_project, container, false);
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

        realm = Realm.getDefaultInstance();

        doc = view.findViewById(R.id.doc);
        image = view.findViewById(R.id.image);
        viewPreview = view.findViewById(R.id.view);
        preview = view.findViewById(R.id.preview);
        delete = view.findViewById(R.id.delete);
        recFiles = view.findViewById(R.id.recFiles);
        recImages = view.findViewById(R.id.recImages);
        recCapitals = view.findViewById(R.id.recCapitals);
        recDep = view.findViewById(R.id.recDep);
        egypt = view.findViewById(R.id.egypt);
        tagsInput = view.findViewById(R.id.tagsInput);
        go = view.findViewById(R.id.go);

        if (finalFileModel == null) {
            finalFileModel = new ArrayList<>();
        }
        if (imagesArrayUri == null) {
            imagesArrayUri = new ArrayList<>();
        }
        if (fileArrayUri == null) {
            fileArrayUri = new ArrayList<>();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recImages.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recFiles.setLayoutManager(layoutManager1);

        tagsRecUserLoad = view.findViewById(R.id.tagsRecUserLoad);
        tagsRec = view.findViewById(R.id.tagsRec);

        realm = Realm.getDefaultInstance();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        realm.executeTransaction(realm1 -> {
            mUserId = realm1.where(LoginDataBase.class).findFirst().getUser().getId();
        });


        setUpRecyclerViews();

        imagesAdapter = new ImagesAdapter(getActivity(), imagesArrayUri, item -> {
            try {
                viewPreview.setVisibility(View.VISIBLE);
                currentFileModel = item;
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), item.getFileUri());
                preview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        filesAdapter = new FilesAdapter(getActivity(), fileArrayUri, item -> {
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

        if (imagesArrayUri.size() > 0) {
            viewPreview.setVisibility(View.VISIBLE);
        } else {
            viewPreview.setVisibility(View.GONE);
        }

        delete.setOnClickListener(v -> {
            imagesArrayUri.remove(currentFileModel);
            imagesAdapter.notifyDataSetChanged();
            if (imagesArrayUri.size() > 0) {
                viewPreview.setVisibility(View.VISIBLE);
                try {
                    currentFileModel = imagesArrayUri.get(0);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagesArrayUri.get(0).getFileUri());
                    preview.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                viewPreview.setVisibility(View.GONE);
            }

        });

        recImages.setAdapter(imagesAdapter);


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
                close_type = 0;
                dialog.dismiss();

            });
        });

        egypt.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (mLoadedCapitals.getValue() != null) {
                    mSelectedCapitalModels.addAll(mLoadedCapitals.getValue());
                    mCapitalsRecyclerViewAdapter.notifyDataSetChanged();
                }
            } else {
                mSelectedCapitalModels.clear();
                mCapitalsRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        go.setOnClickListener(v -> new Handler().post(() -> {
            RequirmentModel.setExperienceTypeId(1);
            mSelectedCategory = mCategoriesRecyclerViewAdapter.getSelectedCategory();

            if (Offers.getName() == null || Offers.getName().isEmpty()) {
                pager.setCurrentItem(0);
                Toast.makeText(getContext(), getString(R.string.name_required), Toast.LENGTH_SHORT).show();
            } else if (Offers.getDescription() == null || Offers.getDescription().isEmpty()) {
                pager.setCurrentItem(0);
                Toast.makeText(getContext(), getString(R.string.details_required), Toast.LENGTH_SHORT).show();
            } else if (RequirmentModel.isNeedPlace() && Offers.getAddress() != null && Offers.getAddress().isEmpty()) {
                pager.setCurrentItem(1);
                Toast.makeText(getContext(), getString(R.string.location_required), Toast.LENGTH_SHORT).show();
            } else if (mSelectedCategory == null) {
                Toast.makeText(getContext(), getString(R.string.dept_error), Toast.LENGTH_SHORT).show();
            } else if (mSelectedCapitalModels.isEmpty()) {
                Toast.makeText(getContext(), getString(R.string.cap_required), Toast.LENGTH_SHORT).show();
            } else {

                if (mUserId != -1) {
                    Offers.setUserId(mUserId);
                } else {
                    Toast.makeText(getContext(), "User Id is not set yet", Toast.LENGTH_SHORT).show();
                    return;
                }
                Offers.setCategoryId(mSelectedCategory.getId() > 0 ? mSelectedCategory.getId() : 0);
                Offers.setCategoryName(mSelectedCategory.getName());

                mSelectedCapitalModels = mCapitalsRecyclerViewAdapter.getSelectedCapitals();

                //TODO: start uploading and adding...
                for (int i = 0; i < imagesArrayUri.size(); i++) {
                    FilesModel model = new FilesModel();
                    model.setFileName(imagesArrayUri.get(i).getFileName());
                    model.setServerFileName(imagesArrayUri.get(i).getServerFileName());
                    model.setFileUri(imagesArrayUri.get(i).getFileUri());
                    model.setFileName(imagesArrayUri.get(i).getFileName());
                    finalFileModel.add(model);
                }
                for (int i = 0; i < fileArrayUri.size(); i++) {
                    FilesModel model = new FilesModel();
                    model.setFileName(fileArrayUri.get(i).getFileName());
                    model.setServerFileName(fileArrayUri.get(i).getServerFileName());
                    model.setFileUri(fileArrayUri.get(i).getFileUri());
                    model.setFileName(fileArrayUri.get(i).getFileName());
                    finalFileModel.add(model);
                }

                if (!finalFileModel.isEmpty())
                    copyFilesUploadFilesAddOffer();
                else addOffer();
            }
        }));

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

        setUpExpDepViews();

        if (mLoadedProjectWithAllDataLiveData != null) {
            mLoadedProjectWithAllDataLiveData.observe(this, offer -> {
                if (offer != null) {
                    mSelectedCategory = new CapitalModel();
                    mSelectedCategory.setId(offer.getCategoryId());
                    mSelectedCategory.setName(offer.getCategoryName());
                    mCategoriesRecyclerViewAdapter.setSelectedCategoryModels(mSelectedCategory);

                    List<OfferDetailsRequirment> requirmentModels = offer.getRequirments();
                    if (!requirmentModels.isEmpty()) {
                        OfferDetailsRequirment requirmentModel = requirmentModels.get(0);

                        List<AttachmentModel> attachmentModels = requirmentModel.getAttachmentModels();
                        if (!attachmentModels.isEmpty()) {
                            for (int i = 0; i < attachmentModels.size(); ++i) {
                                AttachmentModel Attachment = attachmentModels.get(i);
                                //TODO: Load Attachment Here...
                            }
                        }

                        mAttachmentModelArrayList = (ArrayList<AttachmentModel>) attachmentModels;
                    }

                    List<ExperienceTypeModel> tagsModels = offer.getTags();
                    if (!tagsModels.isEmpty()) {
                        for (int i = 0; i < tagsModels.size(); ++i) {
                            ExperienceTypeModel tag = tagsModels.get(i);
                            mTagsRecUserAdapter.addTypeModel(tag);
                            mTagsRecLoadedAdapter.removeTypeModel(tag);
                        }
                    }

                    List<CapitalModel> capitalModels = offer.getCapitals();
                    if (!capitalModels.isEmpty()) {
                        for (int i = 0; i < capitalModels.size(); ++i) {
                            CapitalModel capital = capitalModels.get(i);
                            if (!mSelectedCapitalModels.contains(capital)) {
                                mSelectedCapitalModels.add(capital);
                            }
                        }

                        mCapitalsRecyclerViewAdapter.setSelectedCapitalModels(mSelectedCapitalModels);
                    }
                }
            });
        }
    }

    private void setUpExpDepViews() {

        tagsRec.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL));

        mTagsRecLoadedAdapter = new LoadedChipsAdapter(null, mTagsRecUserAdapter);
        tagsRec.setAdapter(mTagsRecLoadedAdapter);

        tagsRecUserLoad.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL));
        mTagsRecUserAdapter = new ChipsAdapter(null, mTagsRecLoadedAdapter);
        tagsRecUserLoad.setAdapter(mTagsRecUserAdapter);

        tagsInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                boolean addText = false;
                if (!text.isEmpty()) {
                    for (int i = text.length() - 1; i >= 0; --i) {
                        if (text.charAt(i) != SPACE && text.charAt(i) != NEW_LINE)
                            addText = true;
                    }

                    addText = addText && (text.charAt(text.length() - 1) == SPACE ||
                            text.charAt(text.length() - 1) == NEW_LINE);

                    if (addText) {
                        //if user added this before
                        List<ExperienceTypeModel> userAddedExperienceTypes =
                                mTagsRecUserAdapter.getSelectedTypeModels();
                        for (int i = userAddedExperienceTypes.size() - 1; i >= 0; --i) {
                            if (userAddedExperienceTypes.get(i).getName().equals(text)) {
                                return;
                            }
                        }

                        //else if user typed something exists int loaded list
                        ArrayList<ExperienceTypeModel> typeModels = mLoadedTags.getValue();
                        if (typeModels != null)
                            for (int i = typeModels.size() - 1; i >= 0; --i) {
                                ExperienceTypeModel typeModel = typeModels.get(i);
                                if (typeModel.getName().equals(text)) {
                                    mTagsRecLoadedAdapter.removeTypeModel(typeModel);
                                    mTagsRecUserAdapter.addTypeModel(typeModel);
                                    return;
                                }
                            }

                        //else
                        ExperienceTypeModel typeModel = new ExperienceTypeModel();
                        typeModel.setId(0);
                        typeModel.setName(text);
                        mTagsRecUserAdapter.addTypeModel(typeModel);

                        //clear EditText
                        tagsInput.setText("");
                    }
                }
            }
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
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
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

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            viewPreview.setVisibility(View.VISIBLE);
            new Handler().post(() -> {
                pager.setCurrentItem(2);
                pagerAdapter.notifyDataSetChanged();
                Log.e("Item", pager.getCurrentItem() + "");
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
                        fileArrayUri.add(s);

                    } else {
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();

                            //String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                            FilesModel s = new FilesModel(uri);
                            s.setFileName(getFileName(s.getFileUri()));
                            fileArrayUri.add(s);
                            // !! You may need to resize the image if it's too large

                        }
                    }
                    filesAdapter.notifyDataSetChanged();
                } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
                    ClipData mClipData = data.getClipData();
                    if (mClipData == null) {
                        Uri uri = data.getData();
                        imagesArrayUri.add(new FilesModel(uri));
//                        finalFileModel.add(new FilesModel(uri));
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
                            imagesArrayUri.add(new FilesModel(uri));
//                            finalFileModel.add(new FilesModel(uri));
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
                    Log.e("finalFileModel Length", finalFileModel.size() + "");
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


                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        Toast.makeText(getActivity(), "Here " + getRealPathFromURI(tempUri), Toast.LENGTH_LONG).show();

                        imagesArrayUri.add(new FilesModel(tempUri));
                    }
                }
                imagesAdapter.notifyDataSetChanged();
                for (int i = 0; i < imagesArrayUri.size(); i++) {
                    Log.e("Index " + i, imagesArrayUri.get(i).getFileUri().toString());
                }
            });
        }
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


//    @SuppressLint("WorldReadableFiles")
//    private void CopyReadPDFFromAssets(String fileName, Uri fileUri) {
//        AssetManager assetManager = getActivity().getAssets();
//
//        InputStream in = null;
//        OutputStream out = null;
//        File file = new File(getActivity().getFilesDir(), fileName);
//        try {
//            in = assetManager.open(fileName);
//            out = getActivity().openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
//
//            copyPdfFile(in, out);
//            in.close();
//            in = null;
//            out.flush();
//            out.close();
//            out = null;
//        } catch (Exception e) {
//            Log.e("exception", e.getMessage());
//        }
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(
//                Uri.parse(fileUri.toString()),
//                "application/pdf");
//
//        Intent.createChooser(intent, "Select App");
//    }
//
//    private void copyPdfFile(@NonNull InputStream in, OutputStream out) throws IOException {
//        byte[] buffer = new byte[1024];
//        int read;
//        while ((read = in.read(buffer)) != -1) {
//            out.write(buffer, 0, read);
//        }
//    }

    private void copyFilesUploadFilesAddOffer() {

        for (int i = finalFileModel.size() - 1; i >= 0; --i) {
            Uri uri = finalFileModel.get(i).getFileUri();

            try (Cursor cursor = getContext().getContentResolver()
                    .query(uri, null, null, null, null, null)) {
                // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
                // "if there's anything to look at, look at it" conditionals.
                if (cursor != null && cursor.moveToFirst()) {
                    final String displayName = cursor.getString(
                            cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    if (copyFileToProjectDirectory(uri, displayName, i)) {
                        //Load New File Location
                        uri = finalFileModel.get(i).getFileUri();
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

                                        Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

                                        boolean fileType;
                                        try {
                                            fileType = !getMimeType(
                                                    finalUri)
                                                    .substring(0, 5).equals("image");
                                        } catch (NullPointerException e) {
                                            //FAILED_TO_DETECT_FILE_TYPE.
                                            //FILE_ADDED_TO_FILES_FOLDER
                                            //TODO: What's going on here ?
                                            fileType = true;
                                        }
                                        mAttachmentModelArrayList.add(
                                                new AttachmentModel(displayName, url, fileType)
                                        );

                                        if (mAttachmentModelArrayList.size() == finalFileModel.size()) {
                                            addOffer();
                                            Toast.makeText(getContext(), "Adding Offer...", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Failed To Upload File.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Failed To Upload File.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
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
            String fileType;
            try {
                fileType = getMimeType(srcUri).substring(0, 5).equals(
                        "image"
                ) ? "Images" : "Files";
            } catch (NullPointerException e) {
                //FAILED_TO_DETECT_FILE_TYPE.
                //FILE_ADDED_TO_FILES_FOLDER
                //TODO: What's going on here ?
                fileType = "Files";
            }


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
            finalFileModel.get(i).setFileUri(Uri.parse(destFile.getPath()));
            Log.v("NewFileUrl", destFile.getPath());

        } catch (IOException e) {
            Toast.makeText(getContext(), "Failed to copy file." + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
        return mimeType;
    }

    private void setUpRecyclerViews() {
        recCapitals.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mCapitalsRecyclerViewAdapter = new CapitalsRecyclerViewAdapter(null, mSelectedCapitalModels);
        recCapitals.setAdapter(mCapitalsRecyclerViewAdapter);
        mLoadedCapitals.observe(this, capitalModels -> mCapitalsRecyclerViewAdapter.swapData(capitalModels));

        recDep.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mCategoriesRecyclerViewAdapter = new CategoriesRecyclerViewAdapter(null, mSelectedCategory);
        recDep.setAdapter(mCategoriesRecyclerViewAdapter);
        mLoadedCategories.observe(this, capitalModels -> mCategoriesRecyclerViewAdapter.swapData(capitalModels));
    }

    private teamup.rivile.com.teamup.Uitls.APIModels.Offers bindOffers() {
        teamup.rivile.com.teamup.Uitls.APIModels.Offers offers = new teamup.rivile.com.teamup.Uitls.APIModels.Offers();
        if (mLoadedProjectWithAllDataLiveData != null) {
            OfferDetails o = mLoadedProjectWithAllDataLiveData.getValue();
            if (o != null)
                offers.setId(o.getId());
        }
        if (offers.getId() == null) offers.setId(0);
        offers.setName(Offers.getName());
        offers.setDescription(Offers.getDescription());
        offers.setCategoryId(Offers.getCategoryId());
        offers.setCategoryName(Offers.getCategoryName());
        offers.setProfitType(Offers.getProfitType());
        offers.setProfitFrom(Offers.getProfitFrom());
        offers.setProfitTo(Offers.getProfitTo());
        offers.setNumContributorFrom(Offers.getNumContributorFrom());
        offers.setNumContributorTo(Offers.getNumContributorTo());
        offers.setAgeRequiredFrom(Offers.getAgeRequiredFrom());
        offers.setAgeRequiredTo(Offers.getAgeRequiredTo());
        offers.setGenderContributor(Offers.getGenderContributor());
        offers.setEducationContributorLevel(Offers.getEducationContributorLevel());
        offers.setUserId(Offers.getUserId());
        offers.setNumLiks(Offers.getNumLiks());
        offers.setNumJoinOffer(Offers.getNumJoinOffer());
        offers.setUsers(Offers.getUsers());
        offers.setAddress(Offers.getAddress());
        offers.setUserId(Offers.getUserId());
        offers.setAddress(Offers.getAddress());

        if (mLoadedProjectWithAllDataLiveData != null) {
            OfferDetails o = mLoadedProjectWithAllDataLiveData.getValue();
            if (o != null)
                offerDetailsDataBase.setId(o.getId());
        }
        if (offerDetailsDataBase.getId() == null) offerDetailsDataBase.setId(0);
        offerDetailsDataBase.setName(Offers.getName());
        offerDetailsDataBase.setDescription(Offers.getDescription());
        offerDetailsDataBase.setCategoryId(Offers.getCategoryId());
        offerDetailsDataBase.setCategoryName(Offers.getCategoryName());
        offerDetailsDataBase.setProfitType(Offers.getProfitType());
        offerDetailsDataBase.setProfitFrom(Offers.getProfitFrom());
        offerDetailsDataBase.setProfitTo(Offers.getProfitTo());
        offerDetailsDataBase.setNumContributorFrom(Offers.getNumContributorFrom());
        offerDetailsDataBase.setNumContributorTo(Offers.getNumContributorTo());
        offerDetailsDataBase.setAgeRequiredFrom(Offers.getAgeRequiredFrom());
        offerDetailsDataBase.setAgeRequiredTo(Offers.getAgeRequiredTo());
        offerDetailsDataBase.setGenderContributor(Offers.getGenderContributor());
        offerDetailsDataBase.setEducationContributorLevel(Offers.getEducationContributorLevel());
        offerDetailsDataBase.setUserId(Offers.getUserId());
        offerDetailsDataBase.setNumLiks(Offers.getNumLiks());
        offerDetailsDataBase.setNumJoinOffer(Offers.getNumJoinOffer());
        /**
         * start User Section
         * */
        UserDataBase userDataBase = new UserDataBase();
        userDataBase.setId(mUserId);
        userDataBase.setFullName(Offers.getUsers().get(0).getFullName());
        userDataBase.setImage(Offers.getUsers().get(0).getImage());
        RealmList<UserDataBase> userDataBases = new RealmList<>();
        userDataBases.add(userDataBase);
        offerDetailsDataBase.setUsers(userDataBases);
        /**
         * end User Section
         * */
        offerDetailsDataBase.setAddress(Offers.getAddress());
        offerDetailsDataBase.setUserId(Offers.getUserId());
        offerDetailsDataBase.setAddress(Offers.getAddress());
        return offers;
    }

    private teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel bindRequirementModel() {
        teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel requirementModel = new teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel();
        requirementModel.setId(RequirmentModel.getId());
        requirementModel.setNeedPlaceStatus(RequirmentModel.isNeedPlaceStatus());
        requirementModel.setNeedPlaceType(RequirmentModel.isNeedPlaceType());
        requirementModel.setNeedPlace(RequirmentModel.isNeedPlace());
        requirementModel.setPlaceAddress(RequirmentModel.getPlaceAddress());
        requirementModel.setPlaceDescriptions(RequirmentModel.getPlaceDescriptions());
        requirementModel.setNeedMoney(RequirmentModel.isNeedMoney());
        requirementModel.setMoneyFrom(RequirmentModel.getMoneyFrom());
        requirementModel.setMoneyTo(RequirmentModel.getMoneyTo());
        requirementModel.setMoneyDescriptions(RequirmentModel.getMoneyDescriptions());
        requirementModel.setNeedExperience(RequirmentModel.isNeedExperience());
        requirementModel.setExperienceFrom(RequirmentModel.getExperienceFrom());
        requirementModel.setExperienceTo(RequirmentModel.getExperienceTo());
        requirementModel.setExperienceDescriptions(RequirmentModel.getExperienceDescriptions());
        requirementModel.setUserId(RequirmentModel.getUserId());
        requirementModel.setExperienceTypeId(RequirmentModel.getExperienceTypeId());

        RealmList<OfferDetailsRequirmentDataBase> list = new RealmList<>();
        OfferDetailsRequirmentDataBase offerDetailsRequirmentDataBase = new OfferDetailsRequirmentDataBase();
        offerDetailsRequirmentDataBase.setId(RequirmentModel.getId());
        offerDetailsRequirmentDataBase.setNeedPlaceStatus(RequirmentModel.isNeedPlaceStatus());
        offerDetailsRequirmentDataBase.setNeedPlaceType(RequirmentModel.isNeedPlaceType());
        offerDetailsRequirmentDataBase.setNeedPlace(RequirmentModel.isNeedPlace());
        offerDetailsRequirmentDataBase.setPlaceAddress(RequirmentModel.getPlaceAddress());
        offerDetailsRequirmentDataBase.setPlaceDescriptions(RequirmentModel.getPlaceDescriptions());
        offerDetailsRequirmentDataBase.setNeedMoney(RequirmentModel.isNeedMoney());
        offerDetailsRequirmentDataBase.setMoneyFrom(RequirmentModel.getMoneyFrom());
        offerDetailsRequirmentDataBase.setMoneyTo(RequirmentModel.getMoneyTo());
        offerDetailsRequirmentDataBase.setMoneyDescriptions(RequirmentModel.getMoneyDescriptions());
        offerDetailsRequirmentDataBase.setNeedExperience(RequirmentModel.isNeedExperience());
        offerDetailsRequirmentDataBase.setExperienceFrom(RequirmentModel.getExperienceFrom());
        offerDetailsRequirmentDataBase.setExperienceTo(RequirmentModel.getExperienceTo());
        offerDetailsRequirmentDataBase.setExperienceDescriptions(RequirmentModel.getExperienceDescriptions());
        offerDetailsRequirmentDataBase.setUserId(RequirmentModel.getUserId());
        offerDetailsRequirmentDataBase.setExperienceTypeId(RequirmentModel.getExperienceTypeId());
        list.add(offerDetailsRequirmentDataBase);
        offerDetailsDataBase.setRequirments(list);
        return requirementModel;
    }

    private void addOffer() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();

        //offer
        Offers.setAddress("address avoiding null");
        String offerString = gson.toJson(bindOffers());

        RequirmentModel.setId(0);
//        RequirmentModel.setExperienceTypeId(null);
        String requirementString = gson.toJson(bindRequirementModel());

        //Attachment
        for (int i = 0; i < mAttachmentModelArrayList.size(); ++i) {
            mAttachmentModelArrayList.get(i).setRequirmentId(null);
        }

        String attachmentString = gson.toJson(mAttachmentModelArrayList);

        //Tags
        String tagsString = gson.toJson(mTagsRecUserAdapter.getSelectedTypeModels());

        //Capital
        String capitalString = gson.toJson(mSelectedCapitalModels);

        Log.d("MODELSS",
                "{\"Offer\": " + offerString + ",\n" +
                        "\"Requirement\": " + requirementString + ",\n" +
                        "\"Attachment\": " + attachmentString + ",\n" +
                        "\"Tags\": " + tagsString + ",\n" +
                        "\"Capital\": " + capitalString + "\n}");

        Retrofit retrofit = new AppConfig(API.BASE_URL).getRetrofit();

        ApiConfig retrofitService = retrofit.create(ApiConfig.class);

        Call<Integer> response ;
        if (mLoadedProjectWithAllDataLiveData != null) {
            OfferDetails o = mLoadedProjectWithAllDataLiveData.getValue();
            if (o != null)
                offerDetailsDataBase.setId(o.getId());
        }

        if (offerDetailsDataBase==null){
            response = retrofitService.addOffer(API.URL_TOKEN,
                    offerString, requirementString, attachmentString, capitalString, tagsString);
        }else {
            response = retrofitService.editOffer(API.URL_TOKEN,
                    offerString, requirementString, attachmentString, capitalString, tagsString);
        }

        response.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.errorBody() == null) {
                    if (response.body() != null && response.body() != 0) {
                        Toast.makeText(getContext(), "Offer Added Successfully.", Toast.LENGTH_LONG).show();
                        offerDetailsDataBase.setId(response.body());
                        realm.executeTransaction(realm1 -> {
                            realm1.insertOrUpdate(offerDetailsDataBase);
                            realm1.commitTransaction();
                        });
                    } else
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                    Log.d("MODELSS", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("MODELSS", t.getCause().getMessage());
            }
        });
    }
}