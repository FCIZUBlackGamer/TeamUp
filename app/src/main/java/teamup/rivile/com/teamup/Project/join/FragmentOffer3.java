package teamup.rivile.com.teamup.Project.join;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import teamup.rivile.com.teamup.Uitls.AppModels.FilesModel;

public class FragmentOffer3 extends Fragment {

//    private static final int PICK_FILE_REQUEST_CODE = 10;
//    static final int PICK_IMAGE_REQUEST = 1;
//    static final int CAMERA_REQUEST = 1888;
//    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    CapitalsRecyclerViewAdapter mCapitalsRecyclerViewAdapter;

    ArrayList<AttachmentModel> mAttachmentModelArrayList = new ArrayList<>();

    View view;
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

    List<AttachmentModel> mAttachmentModels;
    List<OfferDetailsRequirment> mRequirementModel;

    FilesModel currentFileModel;

    ChipsAdapter mTagsRecUserAdapter;

    private static MutableLiveData<OfferDetailsJsonObject> mOffer;
    public int offerId = -1;

    RecyclerView tagsRec;

    static ViewPager pager;
    static FragmentPagerAdapter pagerAdapter;

    static FragmentOffer3 setPager(
            ViewPager viewPager, FragmentPagerAdapter pagerAdapter, MutableLiveData<OfferDetailsJsonObject> offer) {
        pager = viewPager;
        FragmentOffer3.pagerAdapter = pagerAdapter;
        mOffer = offer;
        return new FragmentOffer3();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3_join_project, container, false);
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

        mOffer.observe(this, offerDetailsJsonObject -> {
            if (offerDetailsJsonObject != null) {
                OfferDetails details = offerDetailsJsonObject.getOffers();
                offerId = details.getId();

                mRequirementModel = details.getRequirments();
                if (mRequirementModel != null && !mRequirementModel.isEmpty()) {
                    mAttachmentModels = mRequirementModel.get(0).getAttachmentModels();
                    if (mAttachmentModels != null) {
                        for (int i = 0; i < mAttachmentModels.size(); ++i) {
                            //TODO: get attachments
                        }
                    }
                }
                List<CapitalModel> capitalModels = details.getCapitals();
                if (capitalModels != null && !capitalModels.isEmpty()) {
                    mCapitalsRecyclerViewAdapter.swapData(capitalModels);
                }

                categoryTextView.setText(details.getCategoryName());
            }
        });

        setUpRecyclerViews();

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

        Join.setOnClickListener(v -> new Handler().post(() -> {
            if (offerId != -1) {
                Retrofit retrofit = new AppConfig(API.BASE_URL).getRetrofit();

                ApiConfig retrofitService = retrofit.create(ApiConfig.class);


                String attachments = new GsonBuilder().serializeNulls().create().toJson(mAttachmentModels);
                if (attachments == null) attachments = "[]";

                String requirements = new GsonBuilder().serializeNulls().create().toJson(mRequirementModel);
                if (requirements == null) requirements = "[]";

                Call<String> response = retrofitService.joinOffer(API.URL_TOKEN,
                        attachments, requirements, String.valueOf(offerId));

                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.errorBody() == null) {
                            if (response.body() != null) {
                                Log.d("DABUGG", response.body());
                            } else
                                Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
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

    }
//    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
//            final Context context) {
//        int currentAPIVersion = Build.VERSION.SDK_INT;
//        if (currentAPIVersion >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(context,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        (Activity) context,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    showDialog("External storage", context,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//                } else {
//                    ActivityCompat
//                            .requestPermissions(
//                                    (Activity) context,
//                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                }
//                return false;
//            } else {
//                return true;
//            }
//
//        } else {
//            return true;
//        }
//    }

//    public void showDialog(final String msg, final Context context,
//                           final String permission) {
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//        alertBuilder.setCancelable(true);
//        alertBuilder.setTitle("Permission necessary");
//        alertBuilder.setMessage(msg + " permission is necessary");
//        alertBuilder.setPositiveButton(android.R.string.yes,
//                (dialog, which) -> ActivityCompat.requestPermissions((Activity) context,
//                        new String[]{permission},
//                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE));
//        AlertDialog alert = alertBuilder.create();
//        alert.show();
//    }

//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 65, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//    public String getRealPathFromURI(Uri uri) {
//        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//        return cursor.getString(idx);
//    }

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

//    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        float bitmapRatio = (float) width / (float) height;
//        if (bitmapRatio > 1) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;
//            width = (int) (height * bitmapRatio);
//        }
//
//        return Bitmap.createScaledBitmap(image, width, height, true);
//}

//    public String getMimeType(Uri uri) {
//        String mimeType;
//        if (uri.getScheme() != null && uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
//            ContentResolver cr = getContext().getContentResolver();
//            mimeType = cr.getType(uri);
//        } else {
//            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
//                    .toString());
//            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
//                    fileExtension.toLowerCase());
//        }
//        return mimeType;
//    }

    private void setUpRecyclerViews() {
        recCapitals.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mCapitalsRecyclerViewAdapter = new CapitalsRecyclerViewAdapter(null);
        recCapitals.setAdapter(mCapitalsRecyclerViewAdapter);
    }
}