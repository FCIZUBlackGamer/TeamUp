package teamup.rivile.com.teamup.ui.Project.Add;

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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
import teamup.rivile.com.teamup.ui.Project.Add.Adapters.CapitalsRecyclerViewAdapter;
import teamup.rivile.com.teamup.ui.Project.Add.Adapters.CategoriesRecyclerViewAdapter;
import teamup.rivile.com.teamup.ui.Project.Add.Adapters.ChipsAdapter;
import teamup.rivile.com.teamup.ui.Project.Add.Adapters.FilesAdapter;
import teamup.rivile.com.teamup.ui.Project.Add.Adapters.ImagesAdapter;
import teamup.rivile.com.teamup.ui.Project.Add.Adapters.LoadedChipsAdapter;
import teamup.rivile.com.teamup.ui.Project.Add.StaticShit.Offers;
import teamup.rivile.com.teamup.ui.Project.Details.OfferDetails;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.AttachmentModel;
import teamup.rivile.com.teamup.Uitls.APIModels.StateModel;
import teamup.rivile.com.teamup.Uitls.APIModels.TagsModel;
import teamup.rivile.com.teamup.Uitls.AppModels.FilesModel;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.LoginDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.OfferDetailsDataBase;
import teamup.rivile.com.teamup.Uitls.InternalDatabase.UserDataBase;

public class FragmentOffer2 extends Fragment {
    private final char SPACE = ' ';
    private final char NEW_LINE = '\n';

    private static final int PICK_FILE_REQUEST_CODE = 10;
    static final int PICK_IMAGE_REQUEST = 1;
    static final int CAMERA_REQUEST = 1888;
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    private ArrayList<StateModel> mSelectedStateModels = new ArrayList<>();
    private CapitalsRecyclerViewAdapter mCapitalsRecyclerViewAdapter;

    private StateModel mSelectedCategory;
    private CategoriesRecyclerViewAdapter mCategoriesRecyclerViewAdapter;

    private ArrayList<AttachmentModel> mAttachmentModelArrayList = new ArrayList<>();

    private RelativeLayout mAttachmentRelativeLayout, mCapitalsRelativeLayout, mSectionsRelativeLayout, mTagsRelativeLayout;
    private LinearLayout mAttachmentSectionLinearLayout, mCapitalSectionLinearLayout,
            mDepartmentSectionLinearLayout, mTagsSectionLinearLayout;

    private ImageView mDocumontsImageView, mImagesImageView, mPreviewImageView, mDeleteImageView;
    private RecyclerView mFilesRecyclerView, mImagesRecyclerView;
    private RecyclerView.Adapter mImagesAdapter, mFilesAdapter;
    private RecyclerView mCapitalsRecyclerView;
    private RecyclerView mDepartmentRecyclerView;
    private CheckBox mEgyptCheckBox;
    private EditText mTagsInputEditText;
    private Button mGoButton;
    private ArrayList<FilesModel> mImagesUriArrayList, mFilesUriArrayList;
    private List<FilesModel> mFinalFileModelList;

    private View mCameraView;
    private ImageView mCloseImageView, mMinimizeImageView, mCameraImageView, mGalleryImageView;
    private ImageView mArrowAttachmentsImageView, mArrowCapitalsImageView, mArrowDepartmentsImageView, mArrowTagsImageView;
    private int mCloseType;
    private RelativeLayout mViewPreviewRelativeLayout;

    private FilesModel mCurrentFileModel;
    private Realm mRealm;
    private OfferDetailsDataBase mOfferDetailsDataBase, mOfferDetailsDataBaseIN;

    private ChipsAdapter mTagsRecUserAdapter;
    private LoadedChipsAdapter mTagsRecLoadedAdapter;

    private int mUserId = -1;

    private static MutableLiveData<ArrayList<TagsModel>> mLoadedTags = new MutableLiveData<>();
    private static MutableLiveData<ArrayList<StateModel>> mLoadedCapitals = new MutableLiveData<>();
    private static MutableLiveData<ArrayList<StateModel>> mLoadedCategories = new MutableLiveData<>();
    private RecyclerView mTagsUserLoadRecyclerView, mTagsRecyclerView;

    private static ViewPager mPager;
    private static FragmentPagerAdapter mPagerAdapter;
    private static MutableLiveData<OfferDetails> mLoadedProjectWithAllDataLiveData = null;
    private FragmentManager mFM;

    static FragmentOffer2 setPager(
            ViewPager viewPager, FragmentPagerAdapter pagerAdapter,
            MutableLiveData<ArrayList<TagsModel>> tagsArrayList,
            MutableLiveData<ArrayList<StateModel>> loadedCapitals,
            MutableLiveData<ArrayList<StateModel>> loadedCategories,
            MutableLiveData<OfferDetails> loadedProjectWithAllDataLiveData) {

        mPager = viewPager;
        FragmentOffer2.mPagerAdapter = pagerAdapter;
        mLoadedTags = tagsArrayList;
        mLoadedCapitals = loadedCapitals;
        mLoadedCategories = loadedCategories;
        mLoadedProjectWithAllDataLiveData = loadedProjectWithAllDataLiveData;
        return new FragmentOffer2();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_add_project, container, false);
        //Shrink and Expand Views
        mAttachmentRelativeLayout = view.findViewById(R.id.attachment);
        mCapitalsRelativeLayout = view.findViewById(R.id.cap);
        mSectionsRelativeLayout = view.findViewById(R.id.dep);
        mTagsRelativeLayout = view.findViewById(R.id.tags);
        mAttachmentSectionLinearLayout = view.findViewById(R.id.attachmentSection);
        mCapitalSectionLinearLayout = view.findViewById(R.id.CapSection);
        mDepartmentSectionLinearLayout = view.findViewById(R.id.DepSection);
        mTagsSectionLinearLayout = view.findViewById(R.id.tagSection);
        mArrowAttachmentsImageView = view.findViewById(R.id.arrowAttachments);
        mArrowDepartmentsImageView = view.findViewById(R.id.arrowDepartments);
        mArrowCapitalsImageView = view.findViewById(R.id.arrowCapitals);
        mArrowTagsImageView = view.findViewById(R.id.arrowTags);
        // Input Views

        mRealm = Realm.getDefaultInstance();

        mDocumontsImageView = view.findViewById(R.id.doc);
        mImagesImageView = view.findViewById(R.id.image);
        mViewPreviewRelativeLayout = view.findViewById(R.id.view);
        mPreviewImageView = view.findViewById(R.id.preview);
        mDeleteImageView = view.findViewById(R.id.tv_options);
        mFilesRecyclerView = view.findViewById(R.id.recFiles);
        mImagesRecyclerView = view.findViewById(R.id.recImages);
        mCapitalsRecyclerView = view.findViewById(R.id.recCapitals);
        mDepartmentRecyclerView = view.findViewById(R.id.recDep);
        mEgyptCheckBox = view.findViewById(R.id.egypt);
        mTagsInputEditText = view.findViewById(R.id.tagsInput);
        mGoButton = view.findViewById(R.id.go);

        if (mFinalFileModelList == null) {
            mFinalFileModelList = new ArrayList<>();
        }
        if (mImagesUriArrayList == null) {
            mImagesUriArrayList = new ArrayList<>();
        }
        if (mFilesUriArrayList == null) {
            mFilesUriArrayList = new ArrayList<>();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mImagesRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mFilesRecyclerView.setLayoutManager(layoutManager1);

        mTagsUserLoadRecyclerView = view.findViewById(R.id.tagsRecUserLoad);
        mTagsRecyclerView = view.findViewById(R.id.tagsRec);

        mRealm = Realm.getDefaultInstance();
        mFM = getFragmentManager();
        return view;
    }

    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        ((DrawerActivity) getActivity()).hideSearchBar();


        mRealm.executeTransaction(realm1 -> {
            LoginDataBase loginDataBase = realm1.where(LoginDataBase.class).findFirst();
            UserDataBase userDataBase = null;
            if (loginDataBase != null) {
                userDataBase = loginDataBase.getUser();
            }

            if (loginDataBase != null) {
                mUserId =userDataBase.getId();
                Log.e("UserIdDDDdDDD1", mUserId + "");
            }
        });
        Log.e("UserIdDDDdDDD2", mUserId + "");


        setUpRecyclerViews();

        mImagesAdapter = new ImagesAdapter(getActivity(), mImagesUriArrayList, item -> {
            try {
                mViewPreviewRelativeLayout.setVisibility(View.VISIBLE);
                mCurrentFileModel = item;
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), item.getFileUri());
                mPreviewImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mFilesAdapter = new FilesAdapter(getActivity(), mFilesUriArrayList, item -> {
            try {
                //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + (item.getFileName().isEmpty() ? item.getServerFileName(): item.getFileName()));
                MimeTypeMap myMime = MimeTypeMap.getSingleton();
                Intent newIntent = new Intent(Intent.ACTION_VIEW);
                String mimeType = myMime.getMimeTypeFromExtension(fileExt(String.valueOf(item.getFileUri())).substring(1));
                Log.i("Name", Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                        + (item.getFileName().isEmpty() ? item.getServerFileName() : item.getFileName()));
                newIntent.setDataAndType(
                        Uri.parse(
                                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                                        + (item.getFileName().isEmpty() ? item.getServerFileName() : item.getFileName()))
                        , mimeType);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mFilesRecyclerView.setAdapter(mFilesAdapter);

        if (mImagesUriArrayList.size() > 0) {
            mViewPreviewRelativeLayout.setVisibility(View.VISIBLE);
        } else {
            mViewPreviewRelativeLayout.setVisibility(View.GONE);
        }

        mDeleteImageView.setOnClickListener(v -> {
            mImagesUriArrayList.remove(mCurrentFileModel);
            mImagesAdapter.notifyDataSetChanged();
            if (mImagesUriArrayList.size() > 0) {
                mViewPreviewRelativeLayout.setVisibility(View.VISIBLE);
                try {
                    mCurrentFileModel = mImagesUriArrayList.get(0);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImagesUriArrayList.get(0).getFileUri());
                    mPreviewImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                mViewPreviewRelativeLayout.setVisibility(View.GONE);
            }

        });

        mImagesRecyclerView.setAdapter(mImagesAdapter);


        mDocumontsImageView.setOnClickListener(v -> {
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

        mImagesImageView.setOnClickListener(v -> {
            /** Choose Either Camera Or Gallery */
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            mCameraView = inflater.inflate(R.layout.camera_view, null);

            mCloseImageView = mCameraView.findViewById(R.id.close);
            mMinimizeImageView = mCameraView.findViewById(R.id.minimize);
            mCameraImageView = mCameraView.findViewById(R.id.cam);
            mGalleryImageView = mCameraView.findViewById(R.id.gal);

            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false)
                    .setView(mCameraView);

            final AlertDialog dialog = builder.create();
            dialog.show();

            mGalleryImageView.setOnClickListener(v1 -> {
                openGallery();
                dialog.dismiss();
            });

            mCameraImageView.setOnClickListener(v12 -> {
                openCamera();
                dialog.dismiss();
            });

            mCloseImageView.setOnClickListener(v13 -> {
                mCloseType = 0;
                dialog.dismiss();

            });
        });

        mEgyptCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (mLoadedCapitals.getValue() != null) {
                    mSelectedStateModels.addAll(mLoadedCapitals.getValue());
                    mCapitalsRecyclerViewAdapter.notifyDataSetChanged();
                }
            } else {
                mSelectedStateModels.clear();
                mCapitalsRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        mGoButton.setOnClickListener(v -> {

            mSelectedCategory = mCategoriesRecyclerViewAdapter.getSelectedCategory();

            Log.v("mSelectedCategory", String.valueOf(mSelectedCategory));

            if (Offers.getName() == null || Offers.getName().isEmpty()) {
                mPager.setCurrentItem(0);
                Toast.makeText(getContext(), getString(R.string.name_required), Toast.LENGTH_SHORT).show();
            } else if (Offers.getDescription() == null || Offers.getDescription().isEmpty()) {
                mPager.setCurrentItem(0);
                Toast.makeText(getContext(), getString(R.string.details_required), Toast.LENGTH_SHORT).show();
            } else if (Offers.isNeedPlace()) {
                mPager.setCurrentItem(0);
//                Toast.makeText(getContext(), getString(R.string.location_required), Toast.LENGTH_SHORT).show();
            } else if (mSelectedCategory == null) {
                Toast.makeText(getContext(), getString(R.string.dept_error), Toast.LENGTH_SHORT).show();
            } else if (mSelectedStateModels.isEmpty()) {
                Toast.makeText(getContext(), getString(R.string.cap_required), Toast.LENGTH_SHORT).show();
            } else {

                if (mUserId != -1) {
                    mGoButton.setEnabled(false);
                    Offers.setUserId(mUserId);
                } else {
                    Toast.makeText(getContext(), "User Id is not set yet", Toast.LENGTH_SHORT).show();
                    return;
                }
                Offers.setCategoryId(mSelectedCategory.getId() > 0 ? mSelectedCategory.getId() : 0);
                Offers.setCategoryName(mSelectedCategory.getName());

                mSelectedStateModels = mCapitalsRecyclerViewAdapter.getSelectedCapitals();

                //TODO: start uploading and adding...
                for (int i = 0; i < mImagesUriArrayList.size(); i++) {
                    FilesModel model = new FilesModel();
                    model.setFileName(mImagesUriArrayList.get(i).getFileName());
                    model.setServerFileName(mImagesUriArrayList.get(i).getServerFileName());
                    model.setFileUri(mImagesUriArrayList.get(i).getFileUri());
                    model.setFileName(mImagesUriArrayList.get(i).getFileName());
                    mFinalFileModelList.add(model);
                }
                for (int i = 0; i < mFilesUriArrayList.size(); i++) {
                    FilesModel model = new FilesModel();
                    model.setFileName(mFilesUriArrayList.get(i).getFileName());
                    model.setServerFileName(mFilesUriArrayList.get(i).getServerFileName());
                    model.setFileUri(mFilesUriArrayList.get(i).getFileUri());
                    model.setFileName(mFilesUriArrayList.get(i).getFileName());
                    mFinalFileModelList.add(model);
                }

                if (!mFinalFileModelList.isEmpty())
                    copyFilesUploadFilesAddOffer();
                else addOffer();
            }
        });

        //region Shrink And Expand

        mAttachmentRelativeLayout.setOnClickListener(v -> {
            if (mAttachmentSectionLinearLayout.getVisibility() == View.VISIBLE) {
                mAttachmentSectionLinearLayout.setVisibility(View.GONE);
                mArrowAttachmentsImageView.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);

            } else {
                mAttachmentSectionLinearLayout.setVisibility(View.VISIBLE);
                mArrowAttachmentsImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            }
        });

        mCapitalsRelativeLayout.setOnClickListener(v -> {
            if (mCapitalSectionLinearLayout.getVisibility() == View.VISIBLE) {
                mCapitalSectionLinearLayout.setVisibility(View.GONE);
                mArrowCapitalsImageView.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);

            } else {
                mCapitalSectionLinearLayout.setVisibility(View.VISIBLE);
                mArrowCapitalsImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            }
        });

        mSectionsRelativeLayout.setOnClickListener(v -> {
            if (mDepartmentSectionLinearLayout.getVisibility() == View.VISIBLE) {
                mDepartmentSectionLinearLayout.setVisibility(View.GONE);
                mArrowDepartmentsImageView.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);

            } else {
                mDepartmentSectionLinearLayout.setVisibility(View.VISIBLE);
                mArrowDepartmentsImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            }
        });

        mTagsRelativeLayout.setOnClickListener(v -> {
            if (mTagsSectionLinearLayout.getVisibility() == View.VISIBLE) {
                mTagsSectionLinearLayout.setVisibility(View.GONE);
                mArrowTagsImageView.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);

            } else {
                mTagsSectionLinearLayout.setVisibility(View.VISIBLE);
                mArrowTagsImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            }
        });
        // endregion

        setUpExpDepViews();

        if (mLoadedProjectWithAllDataLiveData != null) {
            mLoadedProjectWithAllDataLiveData.observe(this, offer -> {
                if (offer != null) {
                    mSelectedCategory = new StateModel();
                    mSelectedCategory.setId(offer.getCategoryId());
                    mSelectedCategory.setName(offer.getCategoryName());
                    mCategoriesRecyclerViewAdapter.setSelectedCategoryModels(mSelectedCategory);

                    List<AttachmentModel> requirmentModels = offer.getAttachments();
                    if (!requirmentModels.isEmpty()) {
                        mAttachmentModelArrayList = (ArrayList<AttachmentModel>) requirmentModels;
                    }

                    List<TagsModel> tagsModels = offer.getTags();
                    if (!tagsModels.isEmpty()) {
                        for (int i = 0; i < tagsModels.size(); ++i) {
                            TagsModel tag = tagsModels.get(i);
                            mTagsRecUserAdapter.addTypeModel(tag);
                            mTagsRecLoadedAdapter.removeTypeModel(tag);
                        }
                    }

                    List<StateModel> stateModels = offer.getStates();
                    if (!stateModels.isEmpty()) {
                        for (int i = 0; i < stateModels.size(); ++i) {
                            StateModel capital = stateModels.get(i);
                            if (!mSelectedStateModels.contains(capital)) {
                                mSelectedStateModels.add(capital);
                            }
                        }

                        mCapitalsRecyclerViewAdapter.setSelectedCapitalModels(mSelectedStateModels);
                    }
                }
            });
        }
    }

    private void setUpExpDepViews() {

        mTagsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL));

        mTagsRecLoadedAdapter = new LoadedChipsAdapter(null, mTagsRecUserAdapter);
        mTagsRecyclerView.setAdapter(mTagsRecLoadedAdapter);

        mTagsUserLoadRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL));
        mTagsRecUserAdapter = new ChipsAdapter(null, mTagsRecLoadedAdapter);
        mTagsUserLoadRecyclerView.setAdapter(mTagsRecUserAdapter);

        mTagsInputEditText.addTextChangedListener(new TextWatcher() {
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
                        List<TagsModel> userAddedExperienceTypes =
                                mTagsRecUserAdapter.getSelectedTypeModels();
                        for (int i = userAddedExperienceTypes.size() - 1; i >= 0; --i) {
                            if (userAddedExperienceTypes.get(i).getName().equals(text)) {
                                return;
                            }
                        }

                        //else if user typed something exists int loaded list
                        ArrayList<TagsModel> typeModels = mLoadedTags.getValue();
                        if (typeModels != null)
                            for (int i = typeModels.size() - 1; i >= 0; --i) {
                                TagsModel typeModel = typeModels.get(i);
                                if (typeModel.getName().equals(text)) {
                                    mTagsRecLoadedAdapter.removeTypeModel(typeModel);
                                    mTagsRecUserAdapter.addTypeModel(typeModel);
                                    return;
                                }
                            }

                        //else
                        TagsModel typeModel = new TagsModel();
                        typeModel.setId(0);
                        typeModel.setName(text);
                        mTagsRecUserAdapter.addTypeModel(typeModel);

                        //clear EditText
                        mTagsInputEditText.setText("");
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
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        } else {
            Intent intent = new Intent();
            intent.setType("mImagesImageView/*");
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
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        } else {
            if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("PageNo", 2);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }

        }

    }

//    public Uri getImageUri(Context inContext, String uri) {
//        Bitmap bitmapImage = BitmapFactory.decodeFile(uri);
//        int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), scaled, "Title", null);
//        return Uri.parse(path);
//    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed mImagesImageView is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the mImagesImageView

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original mImagesImageView

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the mImagesImageView and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), getString(R.string.app_name) + "/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
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
            mViewPreviewRelativeLayout.setVisibility(View.VISIBLE);
            new Handler().post(() -> {
                mPager.setCurrentItem(2);
                mPagerAdapter.notifyDataSetChanged();
                Log.e("Item", mPager.getCurrentItem() + "");
                if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                    /** We Got The File */
                    /** Save File to Local Folder and get Uri and add it to (mFilesUriArrayList) */
                    Toast.makeText(getActivity(), "File", Toast.LENGTH_SHORT).show();

                    ClipData mClipData = data.getClipData();
                    if (mClipData == null) {
                        Uri uri = data.getData();
                        //String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                        FilesModel s = new FilesModel(uri);
                        Log.i("File Name", getFileName(s.getFileUri()));
                        Toast.makeText(getActivity(), getFileName(s.getFileUri()), Toast.LENGTH_SHORT).show();
                        s.setFileName(getFileName(s.getFileUri()));
                        mFilesUriArrayList.add(s);

                    } else {
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();

                            //String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                            FilesModel s = new FilesModel(uri);
                            Log.i("File Name1", getFileName(s.getFileUri()));
                            Toast.makeText(getActivity(), getFileName(s.getFileUri()), Toast.LENGTH_SHORT).show();
                            s.setFileName(getFileName(s.getFileUri()));
                            mFilesUriArrayList.add(s);

                        }
                    }
                    mFilesAdapter.notifyDataSetChanged();
                } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
                    ClipData mClipData = data.getClipData();
                    if (mClipData == null) {
                        Uri uri = data.getData();
                        mImagesUriArrayList.add(new FilesModel(uri));
//                        mFinalFileModelList.add(new FilesModel(uri));
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            bitmap = getResizedBitmap(bitmap, 65);
                            mPreviewImageView.setImageBitmap(bitmap);
                            Toast.makeText(getActivity(), "Image:\n" + uri, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mImagesUriArrayList.add(new FilesModel(uri));
//                            mFinalFileModelList.add(new FilesModel(uri));
                            // !! You may need to resize the mImagesImageView if it's too large
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
//                                bitmap = getResizedBitmap(bitmap, 65);
                                mPreviewImageView.setImageBitmap(bitmap);
                                Toast.makeText(getActivity(), "Image:\n" + uri, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    Log.e("mFinalFileModelList", mFinalFileModelList.size() + "");
                } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                    bitmap = getResizedBitmap(bitmap, 400);
                    Uri imageUri = getImageUri(getActivity(), bitmap);
                    /** Save Image to Local Folder and get Uri and add it to (mImagesUriArrayList) */
                    mPreviewImageView.setImageBitmap(bitmap);
                    if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                        //Uri tempUri = getImageUri(getActivity(), String.valueOf(imageUri));
                        // CALL THIS METHOD TO GET THE ACTUAL PATH
//                        Toast.makeText(getActivity(), "Here " + getRealPathFromURI(imageUri), Toast.LENGTH_LONG).show();

                        mImagesUriArrayList.add(new FilesModel(imageUri));
                    }
                }
                mImagesAdapter.notifyDataSetChanged();
                for (int i = 0; i < mImagesUriArrayList.size(); i++) {
                    Log.e("Index " + i, mImagesUriArrayList.get(i).getFileUri().toString());
                }
            });
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 75, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
//            in.mCloseImageView();
//            in = null;
//            out.flush();
//            out.mCloseImageView();
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

        for (int i = mFinalFileModelList.size() - 1; i >= 0; --i) {
            Uri uri = mFinalFileModelList.get(i).getFileUri();

            try (Cursor cursor = getContext().getContentResolver()
                    .query(uri, null, null, null, null, null)) {
                // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
                // "if there's anything to look at, look at it" conditionals.
                if (cursor != null && cursor.moveToFirst()) {
                    final String displayName = cursor.getString(
                            cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    if (copyFileToProjectDirectory(uri, displayName, i)) {
                        //Load New File Location
                        uri = mFinalFileModelList.get(i).getFileUri();
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

                                        if (mAttachmentModelArrayList.size() == mFinalFileModelList.size()) {
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
            mFinalFileModelList.get(i).setFileUri(Uri.parse(destFile.getPath()));
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
        mCapitalsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mCapitalsRecyclerViewAdapter = new CapitalsRecyclerViewAdapter(null, mSelectedStateModels);
        mCapitalsRecyclerView.setAdapter(mCapitalsRecyclerViewAdapter);
        mLoadedCapitals.observe(this, mCapitalsRecyclerViewAdapter::swapData);

        mDepartmentRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mCategoriesRecyclerViewAdapter = new CategoriesRecyclerViewAdapter(null, mSelectedCategory);
        mDepartmentRecyclerView.setAdapter(mCategoriesRecyclerViewAdapter);
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
//        offers.setProfitType(Offers.getProfitType());
        offers.setNumContributor(Offers.getNumContributor());
        offers.setGenderContributor(Offers.getGenderContributor());
        offers.setUserId(Offers.getUserId());
        offers.setNumLiks(Offers.getNumLiks());
        offers.setNumJoinOffer(Offers.getNumJoinOffer());
        offers.setUsers(Offers.getUsers());
        offers.setAddress(Offers.getAddress());
        offers.setUserId(Offers.getUserId());
        offers.setAddress(Offers.getAddress());

        offers.setInitialCost(Offers.getInitialCost());
        offers.setProfitMoney(Offers.getProfitMoney());
        offers.setDirectExpenses(Offers.getDirectExpenses());
        offers.setIndectExpenses(Offers.getIndectExpenses());
        offers.setStatus(Offers.getStatus());
        offers.setNumJoinOffer(0);
        offers.setNumLiks(0);

        if (mOfferDetailsDataBase != null) {
            if (mLoadedProjectWithAllDataLiveData != null) {
                OfferDetails o = mLoadedProjectWithAllDataLiveData.getValue();
                if (o != null)
                    mOfferDetailsDataBase.setId(o.getId());
            }
            if (mOfferDetailsDataBase.getId() == null) mOfferDetailsDataBase.setId(0);
            mOfferDetailsDataBase.setName(Offers.getName());
            mOfferDetailsDataBase.setDescription(Offers.getDescription());
            mOfferDetailsDataBase.setCategoryId(Offers.getCategoryId());
            mOfferDetailsDataBase.setCategoryName(Offers.getCategoryName());
            mOfferDetailsDataBase.setProfitType(Offers.getProfitType());
            mOfferDetailsDataBase.setNumContributorFrom(Offers.getNumContributor());
            mOfferDetailsDataBase.setGenderContributor(Offers.getGenderContributor());
            mOfferDetailsDataBase.setUserId(Offers.getUserId());
            mOfferDetailsDataBase.setNumLiks(Offers.getNumLiks());
            mOfferDetailsDataBase.setNumJoinOffer(Offers.getNumJoinOffer());
            /**
             * start User Section
             * */
            UserDataBase userDataBase = new UserDataBase();
            userDataBase.setId(mUserId);
            userDataBase.setFullName(Offers.getUsers().get(0).getFullName());
            userDataBase.setImage(Offers.getUsers().get(0).getImage());
            RealmList<UserDataBase> userDataBases = new RealmList<>();
            userDataBases.add(userDataBase);
            mOfferDetailsDataBase.setUsers(userDataBases);
            /**
             * end User Section
             * */
            mOfferDetailsDataBase.setAddress(Offers.getAddress());
            mOfferDetailsDataBase.setUserId(Offers.getUserId());
            mOfferDetailsDataBase.setAddress(Offers.getAddress());
            mOfferDetailsDataBaseIN = mOfferDetailsDataBase;
        } else {
            mOfferDetailsDataBaseIN = new OfferDetailsDataBase();
            mOfferDetailsDataBaseIN.setName(Offers.getName());
            mOfferDetailsDataBaseIN.setDescription(Offers.getDescription());
            mOfferDetailsDataBaseIN.setCategoryId(Offers.getCategoryId());
            mOfferDetailsDataBaseIN.setCategoryName(Offers.getCategoryName());
            mOfferDetailsDataBaseIN.setProfitType(Offers.getProfitType());
            mOfferDetailsDataBaseIN.setNumContributorFrom(Offers.getNumContributor());
            mOfferDetailsDataBaseIN.setGenderContributor(Offers.getGenderContributor());
            mOfferDetailsDataBaseIN.setUserId(Offers.getUserId());
            mOfferDetailsDataBaseIN.setNumLiks(Offers.getNumLiks());
            mOfferDetailsDataBaseIN.setNumJoinOffer(Offers.getNumJoinOffer());
            mOfferDetailsDataBaseIN.setAddress(Offers.getAddress());
            mOfferDetailsDataBaseIN.setUserId(Offers.getUserId());
            mOfferDetailsDataBaseIN.setAddress(Offers.getAddress());
        }
        return offers;
    }

//    private teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel bindRequirementModel() {
//        teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel requirementModel = new teamup.rivile.com.teamup.Uitls.APIModels.RequirmentModel();
//        if (mLoadedProjectWithAllDataLiveData != null) {
//            OfferDetails o = mLoadedProjectWithAllDataLiveData.getValue();
//            if (o != null)
//                requirementModel.setId(o.getId());
//        }
//        if (requirementModel.getId() == null) requirementModel.setId(0);
//        requirementModel.setId(RequirmentModel.getId());
//        requirementModel.setNeedPlaceStatus(RequirmentModel.isNeedPlaceStatus());
//        requirementModel.setNeedPlaceType(RequirmentModel.isNeedPlaceType());
//        requirementModel.setNeedPlace(RequirmentModel.isNeedPlace());
//        requirementModel.setPlaceAddress(RequirmentModel.getPlaceAddress());
//        requirementModel.setPlaceDescriptions(RequirmentModel.getPlaceDescriptions());
//        requirementModel.setNeedMoney(RequirmentModel.isNeedMoney());
//        requirementModel.setMoneyFrom(RequirmentModel.getMoneyFrom());
//        requirementModel.setMoneyTo(RequirmentModel.getMoneyTo());
//        requirementModel.setMoneyDescriptions(RequirmentModel.getMoneyDescriptions());
//        requirementModel.setNeedExperience(RequirmentModel.isNeedExperience());
//        requirementModel.setExperienceFrom(RequirmentModel.getExperienceFrom());
//        requirementModel.setExperienceTo(RequirmentModel.getExperienceTo());
//        requirementModel.setExperienceDescriptions(RequirmentModel.getExperienceDescriptions());
//        requirementModel.setUserId(RequirmentModel.getUserId());
//        requirementModel.setExperienceTypeId(RequirmentModel.getExperienceTypeId());
//
//        RealmList<OfferDetailsRequirmentDataBase> list = new RealmList<>();
//        OfferDetailsRequirmentDataBase offerDetailsRequirmentDataBase = new OfferDetailsRequirmentDataBase();
//        offerDetailsRequirmentDataBase.setId(RequirmentModel.getId());
//        offerDetailsRequirmentDataBase.setNeedPlaceStatus(RequirmentModel.isNeedPlaceStatus());
//        offerDetailsRequirmentDataBase.setNeedPlaceType(RequirmentModel.isNeedPlaceType());
//        offerDetailsRequirmentDataBase.setNeedPlace(RequirmentModel.isNeedPlace());
//        offerDetailsRequirmentDataBase.setPlaceAddress(RequirmentModel.getPlaceAddress());
//        offerDetailsRequirmentDataBase.setPlaceDescriptions(RequirmentModel.getPlaceDescriptions());
//        offerDetailsRequirmentDataBase.setNeedMoney(RequirmentModel.isNeedMoney());
//        offerDetailsRequirmentDataBase.setMoneyFrom(RequirmentModel.getMoneyFrom());
//        offerDetailsRequirmentDataBase.setMoneyTo(RequirmentModel.getMoneyTo());
//        offerDetailsRequirmentDataBase.setMoneyDescriptions(RequirmentModel.getMoneyDescriptions());
//        offerDetailsRequirmentDataBase.setNeedExperience(RequirmentModel.isNeedExperience());
//        offerDetailsRequirmentDataBase.setExperienceFrom(RequirmentModel.getExperienceFrom());
//        offerDetailsRequirmentDataBase.setExperienceTo(RequirmentModel.getExperienceTo());
//        offerDetailsRequirmentDataBase.setExperienceDescriptions(RequirmentModel.getExperienceDescriptions());
//        offerDetailsRequirmentDataBase.setUserId(RequirmentModel.getUserId());
//        offerDetailsRequirmentDataBase.setExperienceTypeId(RequirmentModel.getExperienceTypeId());
//        list.add(offerDetailsRequirmentDataBase);
//        if (mOfferDetailsDataBase != null) mOfferDetailsDataBase.setRequirments(list);
//        return requirementModel;
//    }

    private void addOffer() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();

        //offer
        Offers.setAddress("address avoiding null");
        String offerString = gson.toJson(bindOffers());

//        RequirmentModel.setExperienceTypeId(null);
//        String requirementString = gson.toJson(bindRequirementModel());

        //Attachment
        for (int i = 0; i < mAttachmentModelArrayList.size(); ++i) {
            mAttachmentModelArrayList.get(i).setOfferId(0);
        }

        String attachmentString = gson.toJson(mAttachmentModelArrayList);

        //Tags
        String tagsString = gson.toJson(mTagsRecUserAdapter.getSelectedTypeModels());

        //Capital
        String capitalString = gson.toJson(mSelectedStateModels);

        Log.d("MODELSS",
                "{\"Offer\": " + offerString + ",\n" +
//                        "\"Requirement\": " + requirementString + ",\n" +
                        "\"Attachment\": " + attachmentString + ",\n" +
                        "\"Tags\": " + tagsString + ",\n" +
                        "\"Capital\": " + capitalString + "\n}");

        Retrofit retrofit = new RetrofitConfigurations(API.BASE_URL).getRetrofit();

        RetrofitMethods retrofitService = retrofit.create(RetrofitMethods.class);

        Call<Integer> response;
        if (mLoadedProjectWithAllDataLiveData != null) {
            OfferDetails o = mLoadedProjectWithAllDataLiveData.getValue();
            if (o != null && mOfferDetailsDataBase != null)
                mOfferDetailsDataBase.setId(o.getId());
        }

        int id = Offers.getId();
        if (id == 0 && mLoadedProjectWithAllDataLiveData != null)
            if (mLoadedProjectWithAllDataLiveData.getValue() != null)
                id = mLoadedProjectWithAllDataLiveData.getValue().getId();

        if (id == 0) {
            //TODO: Set location instead of null here
            response = retrofitService.addOffer(API.URL_TOKEN,
                    offerString, attachmentString, capitalString, tagsString, "null");
        } else {
            response = retrofitService.editOffer(API.URL_TOKEN,
                    offerString, attachmentString, "[]", capitalString, tagsString, "null");
        }

        response.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.errorBody() == null) {
                    if (response.body() != null && response.body() != 0) {
                        Log.e("OfferId", response.body() + "");
                        Toast.makeText(getContext(), "Success.", Toast.LENGTH_LONG).show();
                        mOfferDetailsDataBaseIN.setId(response.body());
                        mRealm.beginTransaction();
                        UserDataBase userDataBase = mRealm.where(LoginDataBase.class).findFirst().getUser();
                        userDataBase.updateNumProject();
                        mRealm.insertOrUpdate(userDataBase);
                        mRealm.commitTransaction();
                        mRealm.executeTransaction(realm1 -> {
                            realm1.insertOrUpdate(mOfferDetailsDataBaseIN);
                        });

//                        mFM.beginTransaction().replace(R.id.frame, new FragmentHome()).commit();
//                        getFragmentManager().popBackStack();

                        getActivity().onBackPressed();

                    } else {
                        Toast.makeText(getContext(), "RESPONSE ERROR!", Toast.LENGTH_LONG).show();
                        mGoButton.setEnabled(true);
                    }
                } else {
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                    Log.d("MODELSS", response.errorBody().toString());
                    mGoButton.setEnabled(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("MODELSS", t.getCause().getMessage());
                mGoButton.setEnabled(true);
            }
        });
    }
}