package teamup.rivile.com.teamup.Department;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.Project.List.FragmentListProjects;
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
            final Button action = (Button) convertView.findViewById(R.id.action);

            final ViewHolder viewHolder = new ViewHolder(name, imageView, action);
            convertView.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.name.setText(product.getName());
        /** Download Category Image */

//        Picasso.get().load(API.BASE_URL +product.getImage()).into(viewHolder.image);

        viewHolder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frame, new FragmentListProjects().setDepId(product.getId()));
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView name;
//        TextView numProjects;
        Button action;
        ImageView image;

        public ViewHolder(TextView name, ImageView image, Button action) {
            this.name = name;
            this.image = image;
            this.action = action;
        }

    }

}