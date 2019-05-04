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

public class CapitalAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<StateModel> stateModels, choosenStateModels;

    public CapitalAdapter(Context mContext, List<StateModel> stateModels, List<StateModel> choosenStateModels) {
        this.mContext = mContext;
        this.stateModels = stateModels;
        this.choosenStateModels = choosenStateModels;
    }

    @Override
    public int getCount() {
        return stateModels.size();
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
        final StateModel product = stateModels.get(position);

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
                StateModel model = new StateModel();
                model.setId(product.getId());
                model.setName(product.getName());
                if (choosenStateModels.contains(model)){
                    choosenStateModels.remove(model);
                }else {
                    choosenStateModels.add(model);
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
