package simplifii.framework.fragments;

import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import simplifii.framework.R;
import simplifii.framework.activity.HomeActivity;
import java.util.ArrayList;
import java.util.List;
import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.enums.DrawerData;
import simplifii.framework.models.DrawerItem;


public class DrawerFragment extends BaseFragment implements CustomListAdapterInterface, AdapterView.OnItemClickListener {

    private HomeActivity homeActivity;
    private DrawerLayout drawerLayout;
    private List<DrawerItem> drawerList = new ArrayList<>();




    @Override
    public void initViews() {
        View header_drawer = LayoutInflater.from(getActivity()).inflate(R.layout.header_dr, null);
        View footer_drawer = LayoutInflater.from(getActivity()).inflate(R.layout.footer_list_drawer, null);


        ListView lv = (ListView) findView(R.id.lv_side_drawer);
        CustomListAdapter customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_item_drawer, drawerList, this);
        lv.addHeaderView(header_drawer);
        lv.addFooterView(footer_drawer);
        lv.setAdapter(customListAdapter);
        setOnClickListener(R.id.rl_logout);
        setData(customListAdapter);
    }

    private void setData(CustomListAdapter customListAdapter) {
        drawerList.addAll(DrawerData.getAllDrawerItems());
        customListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_drawer;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final DrawerItem drawerItem = drawerList.get(position);
        holder.tv_side_drawer.setText(drawerItem.getItemName());
        boolean selected = drawerItem.isSelected();
        if (selected) {
            holder.layoutBackground.setVisibility(View.VISIBLE);
            convertView.setBackgroundColor(getResourceColor(R.color.grey_transparent));
        } else {
            holder.layoutBackground.setVisibility(View.GONE);
            convertView.setBackgroundColor(getResourceColor(R.color.full_transparent));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(null, v, position, 0);
            }
        });
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    class Holder {
        TextView tv_side_drawer;
        LinearLayout layoutBackground;


        Holder(View view) {
            tv_side_drawer = (TextView) view.findViewById(R.id.tv_drawer_list_item);
            layoutBackground = (LinearLayout) view.findViewById(R.id.ll_background);
        }
    }


}
