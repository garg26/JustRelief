package com.justrelief.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.justrelief.R;
import com.justrelief.models.ClinicNameItem;

import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;



public class ClinicNameListFragment extends AppBaseFragment implements CustomListAdapterInterface{
    private List<ClinicNameItem> itemsList;


    @Override
    public void initViews() {
        setHasOptionsMenu(true);
        getActivity().setTitle("Contact & Clinic Detail");
        itemsList = ClinicNameItem.getList();
        ListView lv_clinic_list = (ListView) findView(R.id.lv_clinic_name);
        CustomListAdapter<ClinicNameItem> customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_name, itemsList, this);
        lv_clinic_list.setAdapter(customListAdapter);



    }

    @Override
    public int getViewID() {
        return R.layout.layout_clinic_name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {

        final Holder holder;
        if(convertView==null){
            convertView = inflater.inflate(resourceID,parent,false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }
        else{
            holder = (Holder)convertView.getTag();
        }

        ClinicNameItem item = itemsList.get(position);
        holder.tv_title.setText(item.getName());


        return convertView;
    }
    class Holder {
        TextView tv_title, tv_description;
        ImageView iv_clinic;

        public Holder(View view) {
            tv_title = (TextView) view.findViewById(R.id.tv_name);
           // tv_description = (TextView) view.findViewById(R.id.tv_description);
            iv_clinic = (ImageView)view.findViewById(R.id.iv_clinic);

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
