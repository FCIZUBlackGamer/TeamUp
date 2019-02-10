package teamup.rivile.com.teamup.Project.Add.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.AppModels.FilesModel;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.Vholder> {

    Context context;
    List<FilesModel> filesModels;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FilesModel item);
    }

    public ImagesAdapter(Context context, List<FilesModel> filesModels, OnItemClickListener listener) {
        this.context = context;
        this.filesModels = filesModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_images, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {
        holder.bind(filesModels.get(position), listener);

        if (filesModels.get(position).getFileName().startsWith(API.BASE_URL)){
            Picasso.get().load(filesModels.get(position).getFileName()).into(holder.image);
            notifyDataSetChanged();
        } else if (!filesModels.get(position).getFileUri().toString().isEmpty()){
            Uri imageUri = filesModels.get(position).getFileUri();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                holder.image.setImageBitmap(bitmap);
                notifyDataSetChanged();
            }catch (Exception e){
                Log.e("Ex", e.getMessage());
            }
        }else {
            Log.e("Ex", "EMpty");
        }

    }

    @Override
    public int getItemCount() {
        return filesModels.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        ImageView image;

        public Vholder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }

        public void bind(final FilesModel item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

}