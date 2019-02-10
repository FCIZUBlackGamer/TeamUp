package teamup.rivile.com.teamup.Project.join.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.CapitalModel;

public class CapitalAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<CapitalModel> capitalModels, choosenCapitalModels;

    public CapitalAdapter(Context mContext, List<CapitalModel> capitalModels, List<CapitalModel> choosenCapitalModels) {
        this.mContext = mContext;
        this.capitalModels = capitalModels;
        this.choosenCapitalModels = choosenCapitalModels;
    }

    @Override
    public int getCount() {
        return capitalModels.size();
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
        final CapitalModel product = capitalModels.get(position);

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
                /** action select and deselect */
                CapitalModel model = new CapitalModel();
                model.setId(product.getId());
                model.setName(product.getName());
                if (choosenCapitalModels.contains(model)) {
                    choosenCapitalModels.remove(model);
                } else {
                    choosenCapitalModels.add(model);
                }
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
