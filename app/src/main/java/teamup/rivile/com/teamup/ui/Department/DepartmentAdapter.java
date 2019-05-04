package teamup.rivile.com.teamup.ui.Department;

import android.content.Context;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.ui.Project.List.FragmentListProjects;
import teamup.rivile.com.teamup.R;

public class DepartmentAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Department> categories;
    FragmentManager fragmentManager;

    public DepartmentAdapter(Context context, List<Department> categories) {
        this.mContext = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Department product = categories.get(position);

        // view holder pattern
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
            convertView = layoutInflater.inflate(R.layout.adapter_category, null);

            final TextView name = (TextView) convertView.findViewById(R.id.dep_name);
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
            final MaterialCardView action = (MaterialCardView ) convertView.findViewById(R.id.action);

            final ViewHolder viewHolder = new ViewHolder(name, imageView, action);
            convertView.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.name.setText(product.getName());
        /** Download Category Icon */

        if (product.getIcon() != null && !product.getIcon().isEmpty())
            Picasso.get().load(API.BASE_URL + product.getIcon()).into(viewHolder.image);

        viewHolder.action.setOnClickListener(v -> {
            Log.e("DepId", product.getId() + "");
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, FragmentListProjects.setDepId(product.getId()))
                    .addToBackStack(FragmentListProjects.class.getSimpleName())
                    .commit();
        });

        viewHolder.image.setOnClickListener(v -> viewHolder.action.callOnClick());
        viewHolder.name.setOnClickListener(v -> viewHolder.action.callOnClick());

        return convertView;
    }

    private class ViewHolder {
        TextView name;
//        TextView numProjects;
//        RelativeLayout action;
        ImageView image;
        MaterialCardView action;

        public ViewHolder(TextView name, ImageView image, MaterialCardView action) {
            this.name = name;
            this.image = image;
            this.action = action;
        }

    }

}