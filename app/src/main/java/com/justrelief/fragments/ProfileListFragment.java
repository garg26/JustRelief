package com.justrelief.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.justrelief.R;
import com.justrelief.activity.AppBaseActivity;
import com.justrelief.activity.FragmentContainer;
import com.justrelief.models.ClinicProfileItem;

import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.AppConstants;

public class ProfileListFragment extends AppBaseFragment implements CustomListAdapterInterface, AdapterView.OnItemClickListener {
    private ListView lv_pro_list;
    private CustomListAdapter<ClinicProfileItem> customListAdapter;
    private List<ClinicProfileItem> itemsList;
    private View headerView, footerView;
    private boolean isListSetupDone;

    @Override
    public void initViews() {
        setHasOptionsMenu(true);
        //((AppBaseActivity)getActivity()).getSupportActionBar().setTitle("Clinic Profile");

       // getActivity().setTitle("Clinic Profile");
        itemsList = ClinicProfileItem.getList();
        lv_pro_list = (ListView) findView(R.id.lv_profile_option);
        customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_profile, itemsList, this);
        lv_pro_list.addHeaderView(getHeaderView());
//        lv_pro_list.addFooterView(getFooterView());
        lv_pro_list.setAdapter(customListAdapter);
        lv_pro_list.setOnItemClickListener(this);
        setOnClickListener(R.id.btn_retry, R.id.layout_connectivity);

    }

    private View getHeaderView() {
        return headerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header_profile_completeness, null);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_retry:
                break;
        }
    }

    @Override
    protected void onInternetException() {
        super.onInternetException();
        showVisibility(R.id.layout_connectivity);
    }


    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
    }

    private void setHeaderData(int percentage) {
        ProgressBar bar = (ProgressBar) headerView.findViewById(R.id.progressBar3);
        bar.setMax(100);
        bar.setProgress(percentage);
        TextView tv = (TextView) headerView.findViewById(R.id.tv_profile_rating);
        tv.setText(String.valueOf(percentage) + "%");
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        super.onBackgroundError(re, e, taskCode, params);
        showVisibility(R.id.layout_connectivity);

    }

    @Override
    public int getViewID() {
        return R.layout.layout_profile_completeness;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent, int resourceID, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        ClinicProfileItem item = itemsList.get(position);
        holder.tv_title.setText(item.getName());
        boolean isCompleted = item.isCompleted();
        if (isCompleted) {
            holder.completionSymbol.setBackgroundResource(R.drawable.circle_complete_shape);
        } else {
            holder.completionSymbol.setBackgroundResource(R.drawable.circle_shape);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(null,v,position,0);
            }
        });
        return convertView;
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("onItemClick","inside");
        ClinicProfileItem item = itemsList.get(position);
        switch (item.getItemId()) {
            case AppConstants.ClinicProfileConstants.CONTACT_CLINIC_ITEM_ID:
                FragmentContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.CONTACT_CLINIC_DETAILS_FRAGMENT, null);
                break;
            case AppConstants.TimingsConstants.TIMINGS_ITEM_ID:
                FragmentContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.TIMINGS_FRAGMENT, null);
                break;
            case AppConstants.LocationPhotosConstants.LOCATION_PHOTOS_ITEM_ID:
                FragmentContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.LOCATION_PHOTOS_FRAGMENT, null);
                break;
            case AppConstants.DoctorsConstants.DOCTORS_ITEM_ID:
                FragmentContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.DOCTORS_FRAGMENT, null);
                break;
            case AppConstants.ServiceConstants.SPECIALIZATIONS_SERVICE_ITEM_ID:
                FragmentContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.SPECIALIZATIONS_SERVICES_FRAGMENT, null);
                break;
            case AppConstants.AwardConstants.AWARD_ACCEREDATIONS_ITEM_ID:
                FragmentContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.AWARD_ACCEREDATIONS_FRAGMENT, null);
                break;

        }
    }


    class Holder {
        TextView tv_title, tv_description;
        RelativeLayout completionSymbol;

        public Holder(View view) {
            tv_title = (TextView) view.findViewById(R.id.tv_name);
            tv_description = (TextView) view.findViewById(R.id.tv_description);
            completionSymbol = (RelativeLayout) view.findViewById(R.id.rl_round_complete);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
