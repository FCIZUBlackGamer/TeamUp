package teamup.rivile.com.teamup.Project.Add.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.AppModels.FilesModel;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.Vholder> {

    Context context;
    List<FilesModel> filesModels;
    Uri fileUri = null;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FilesModel item);
    }

    public FilesAdapter(Context context, List<FilesModel> filesModels, OnItemClickListener listener) {
        this.context = context;
        this.filesModels = filesModels;
        this.listener = listener;
        if (this.filesModels == null) {
            this.filesModels = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_files, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {
        holder.bind(filesModels.get(position), listener);

        filesModels.get(position).setIndex(position);
        try {Log.e("Files", "Here");
            Log.e("Files", filesModels.get(position).getFileName());
            fileUri = filesModels.get(position).getFileUri();
            if (filesModels.get(position).getFileName().isEmpty()){
                holder.fileName.setText(filesModels.get(position).getFileName());
            }else {
                holder.fileName.setText(filesModels.get(position).getServerFileName());
            }
        } catch (Exception e) {
            Log.e("EX", e.getMessage());
        }

        holder.remove.setOnClickListener(v -> {
            if (fileUri != null){
                filesModels.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return filesModels.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView fileName;
        Button remove;

        public Vholder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.fileName);
            remove = itemView.findViewById(R.id.remove);
        }

        public void bind(final FilesModel item, final OnItemClickListener listener) {

            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }

    }

}