package teamup.rivile.com.teamup.Project.Add;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ServerResponse;
import teamup.rivile.com.teamup.Project.Add.Adapters.FilesAdapter;
import teamup.rivile.com.teamup.Project.Add.Adapters.ImagesAdapter;
import teamup.rivile.com.teamup.Project.Add.StaticShit.Offers;
import teamup.rivile.com.teamup.Project.Add.StaticShit.RequirmentModel;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.AppModels.FilesModel;

public class FragmentOffer3 extends Fragment {
    private static final int PICKFILE_REQUEST_CODE = 10;
    static final int PICK_IMAGE_REQUEST = 1;
    static final int CAMERA_REQUEST = 1888;
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;


    View view;
    RelativeLayout attachment, cap, dep, tags;
    LinearLayout attachmentSection, CapSection, DepSection, tagSection;
    int a, c, d, t;/** متغير ثابت عشان اغير حاله ال shrink وال expand*/
    /**
     * 1: Expand, 0:Shrink
     */

    ImageView doc, image, preview, delete;
    RecyclerView recFiles, recImages;
    RecyclerView.Adapter imagesAdapter, filesAdapter;
    GridView recCapitals, recDep;
    CheckBox egypt;
    EditText tagsInput;
    Button go;
    private ArrayList<Uri> imagesArrayUri, fileArrayUri;
    List<FilesModel> filesModels;

    View Camera_view;
    ImageView close, minimize, cam, gal;
    FloatingActionButton appear;
    int close_type;
    RelativeLayout viewPreview;


    FilesModel currentFileModel;

    static ViewPager pager;
    static FragmentPagerAdapter pagerAdapter;

    static FragmentOffer3 setPager(ViewPager viewPager, FragmentPagerAdapter pagerAdapte) {
        pager = viewPager;
        pagerAdapter = pagerAdapte;
        return new FragmentOffer3();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3_add_project, container, false);
        a = c = d = t = 1;
        /** Shrink and Expand Views */
        attachment = view.findViewById(R.id.attachment);
        cap = view.findViewById(R.id.cap);
        dep = view.findViewById(R.id.dep);
        tags = view.findViewById(R.id.tags);
        attachmentSection = view.findViewById(R.id.attachmentSection);
        CapSection = view.findViewById(R.id.CapSection);
        DepSection = view.findViewById(R.id.DepSection);
        tagSection = view.findViewById(R.id.tagSection);
        /** Input Views */

        doc = view.findViewById(R.id.doc);
        image = view.findViewById(R.id.image);
        preview = view.findViewById(R.id.preview);
        delete = view.findViewById(R.id.delete);
        viewPreview = view.findViewById(R.id.view);
        recFiles = view.findViewById(R.id.recFiles);
        recImages = view.findViewById(R.id.recImages);
        recCapitals = view.findViewById(R.id.recCapitals);
        recDep = view.findViewById(R.id.recDep);
        egypt = view.findViewById(R.id.egypt);
        tagsInput = view.findViewById(R.id.tagsInput);
        go = view.findViewById(R.id.go);
        imagesArrayUri = new ArrayList<>();
        fileArrayUri = new ArrayList<>();

        if (filesModels == null) {
            filesModels = new ArrayList<>();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recImages.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recFiles.setLayoutManager(layoutManager1);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        imagesAdapter = new ImagesAdapter(getActivity(), filesModels, new ImagesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FilesModel item) {
                try {
                    viewPreview.setVisibility(View.VISIBLE);
                    currentFileModel = item;
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), item.getFileUri());
                    preview.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        filesAdapter = new FilesAdapter(getActivity(), filesModels, new FilesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FilesModel item) {
                try {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+getFileName(item.getFileUri()));
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "text/plain");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        recFiles.setAdapter(filesAdapter);

        if (filesModels.size() > 0) {
            viewPreview.setVisibility(View.VISIBLE);
        } else {
            viewPreview.setVisibility(View.GONE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });

        recImages.setAdapter(imagesAdapter);


        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Open Storage Files*/
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                //Limit selection to images an pdf files only
                intent.setType("application/pdf|text/plain");
                String[] mimeTypes = {"application/pdf","|text/plain"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

                //local storage only
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                gal.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                    @Override
                    public void onClick(View v) {
                        openGalary();
                        dialog.dismiss();
                    }
                });

                cam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera();
                        dialog.dismiss();
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        close_type = 0;
                        dialog.dismiss();

                    }
                });


            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filesModels.size() > 0){
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }
        });

        /*region Shrink And Expand */
        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == 1) {
                    a = 0;
                    attachmentSection.setVisibility(View.GONE);
                } else {
                    a = 1;
                    attachmentSection.setVisibility(View.VISIBLE);
                }
            }
        });

        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c == 1) {
                    c = 0;
                    CapSection.setVisibility(View.GONE);
                } else {
                    c = 1;
                    CapSection.setVisibility(View.VISIBLE);
                }
            }
        });

        dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (d == 1) {
                    d = 0;
                    DepSection.setVisibility(View.GONE);
                } else {
                    d = 1;
                    DepSection.setVisibility(View.VISIBLE);
                }
            }
        });

        tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t == 1) {
                    t = 0;
                    tagSection.setVisibility(View.GONE);
                } else {
                    t = 1;
                    tagSection.setVisibility(View.VISIBLE);
                }
            }
        });
        /*endregion*/

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openGalary() {
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
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
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
        if (data != null){
            viewPreview.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pager.setCurrentItem(2);
                    pagerAdapter.notifyDataSetChanged();
                    Log.e("Item", pager.getCurrentItem() + "");
                    if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                        /** We Got The File */
                        /** Save File to Local Folder and get Uri and add it to (fileArrayUri) */
                        Toast.makeText(getActivity(), "File", Toast.LENGTH_SHORT).show();

                        ClipData mClipData = data.getClipData();
                        if (mClipData == null) {
                            Uri uri = data.getData();
                            //String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                            FilesModel s = new FilesModel(uri);
                            s.setFileName(getFileName(s.getFileUri()));
                            filesModels.add(s);

                        } else {
                            for (int i = 0; i < mClipData.getItemCount(); i++) {
                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri uri = item.getUri();

                                //String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                                FilesModel s = new FilesModel(uri);
                                s.setFileName(getFileName(s.getFileUri()));
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
                            filesModels.add(new FilesModel(uri));
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                                bitmap = getResizedBitmap(bitmap, 100);
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
                                filesModels.add(new FilesModel(uri));
                                // !! You may need to resize the image if it's too large
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                                    bitmap = getResizedBitmap(bitmap, 100);
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
                        bitmap = getResizedBitmap(bitmap, 100);
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

                            filesModels.add(new FilesModel(tempUri));
                        }
                    }
                    imagesAdapter.notifyDataSetChanged();
                    for (int i = 0; i < filesModels.size(); i++) {
                        Log.e("Index " + i, filesModels.get(i).getFileUri().toString());
                    }
                }
            }, 100);


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


    @SuppressLint("WorldReadableFiles")
    private void CopyReadPDFFromAssets(String fileName, Uri fileUri) {
        AssetManager assetManager = getActivity().getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(getActivity().getFilesDir(), fileName);
        try {
            in = assetManager.open(fileName);
            out = getActivity().openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyPdfFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse(fileUri.toString()),
                "application/pdf");

        Intent.createChooser(intent, "Select App");
    }

    private void copyPdfFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void uploadFile(Uri uri) {
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(uri.getPath());
        AppConfig appConfig = new AppConfig(API.HOME_URL);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ApiConfig getResponse = appConfig.getRetrofit().create(ApiConfig.class);
        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        //textView.setText(serverResponse.getMessage());
                    } else {
                        //textView.setText(serverResponse.getMessage());
                    }
                } else {
                    //textView.setText(serverResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }
}
