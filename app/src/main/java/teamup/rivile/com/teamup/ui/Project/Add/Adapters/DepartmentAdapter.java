package teamup.rivile.com.teamup.ui.Project.Add.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.StateModel;

public class DepartmentAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<StateModel> depModels;
    StateModel choosenDepModel;

    public DepartmentAdapter(Context mContext, List<StateModel> depModels, StateModel choosenDepModel) {
        this.mContext = mContext;
        this.depModels = depModels;
        this.choosenDepModel = choosenDepModel;
    }

    @Override
    public int getCount() {
        return depModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final StateModel product = depModels.get(position);

        // view holder pattern
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.adapter_capitals, null);

            final Button name = (Button) convertView.findViewById(R.id.capName);

            final ViewHolder viewHolder = new ViewHolder(name);
            convertView.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.name.setText(product.getName());
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** action select  */
                choosenDepModel = new StateModel();
                choosenDepModel.setId(product.getId());
                choosenDepModel.setName(product.getName());
            }
        });


        return convertView;
    }

    private class ViewHolder {
        Button name;

        public ViewHolder(Button name) {
            this.name = name;
        }
    }
}
