package teamup.rivile.com.teamup.Project.Add;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import teamup.rivile.com.teamup.R;

public class FragmentOffer3 extends Fragment {
    private static final int PICKFILE_REQUEST_CODE = 10;
    static final int PICK_IMAGE_REQUEST = 1;
    static final int CAMERA_REQUEST = 1888;

    View view;
    RelativeLayout attachment, cap, dep, tags;
    LinearLayout attachmentSection, CapSection, DepSection, tagSection;
    int a, c, d, t;/** متغير ثابت عشان اغير حاله ال shrink وال expand*/
    /**
     * 1: Expand, 0:Shrink
     */

    ImageView doc, image, preview;
    RecyclerView recFiles, recImages;
    GridView recCapitals, recDep;
    CheckBox egypt;
    EditText tagsInput;
    Button go;
    private ArrayList<Uri> imagesArrayUri, fileArrayUri;

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
        recFiles = view.findViewById(R.id.recFiles);
        recImages = view.findViewById(R.id.recImages);
        recCapitals = view.findViewById(R.id.recCapitals);
        recDep = view.findViewById(R.id.recDep);
        egypt = view.findViewById(R.id.egypt);
        tagsInput = view.findViewById(R.id.tagsInput);
        go = view.findViewById(R.id.go);
        imagesArrayUri = new ArrayList<>();
        fileArrayUri = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Open Storage Files*/
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Choose Either Camera Or Gallery */

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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);// ACTION_VIEW
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);

        } else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK ) {
                    /** We Got The File */
                /** Save File to Local Folder and get Uri and add it to (fileArrayUri) */

            }else if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
                ClipData mClipData = data.getClipData();
                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    imagesArrayUri.add(uri);
                    // !! You may need to resize the image if it's too large
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagesArrayUri.get(imagesArrayUri.size()-1));
                        preview.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                /** Save Image to Local Folder and get Uri and add it to (imagesArrayUri) */
                preview.setImageBitmap(bitmap);
            }
        }

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

}
