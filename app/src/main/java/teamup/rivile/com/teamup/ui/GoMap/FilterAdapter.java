package teamup.rivile.com.teamup.ui.GoMap;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import teamup.rivile.com.teamup.R;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_list_item, parent, false);

        return new FilterViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int i) {
        //TODO: Bind Views
    }

    @Override
    public int getItemCount() {
        return 0; //TODO: set adapter count based on the data source
    }

    class FilterViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_textView);
            imageView = itemView.findViewById(R.id.civ_icon);
            //TODO: Set on click listener
        }
    }
}
