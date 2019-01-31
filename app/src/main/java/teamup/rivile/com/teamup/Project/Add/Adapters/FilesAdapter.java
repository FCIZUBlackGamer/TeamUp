package teamup.rivile.com.teamup.Project.Add.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

        if (!filesModels.get(position).getFileUri().toString().isEmpty()) {
            fileUri = filesModels.get(position).getFileUri();
            holder.fileName.setText(filesModels.get(position).getFileName());
        }

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileUri != null){
                    filesModels.remove(position);
                    notifyDataSetChanged();
                }
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

}