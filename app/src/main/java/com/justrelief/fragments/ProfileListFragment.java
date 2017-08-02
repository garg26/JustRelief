package com.justrelief.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.justrelief.R;
import com.justrelief.activity.FragmentContainer;
import com.justrelief.models.ClinicProfileItem;
import com.justrelief.models.CompleteClinicProfile;
import com.justrelief.models.SetClinicDetail;
import com.justrelief.models.response.GetClinicFileResponse;
import com.justrelief.models.response.GetClinicResponseTable;
import com.justrelief.utility.ApiGenerator;

import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.GetClinicBodyItem;
import simplifii.framework.models.GetFileItem;
import simplifii.framework.models.response.ClinicResponse;
import simplifii.framework.models.response.UserProfileResponse;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;

public class ProfileListFragment extends AppBaseFragment implements CustomListAdapterInterface, AdapterView.OnItemClickListener {
    private List<ClinicProfileItem> itemsList;
    private View headerView;
    private Bundle bundle;
    private String facilityID;
    private CompleteClinicProfile clinicProfile = new CompleteClinicProfile();
    private ProgressBar bar;
    private CustomListAdapter customListAdapter;
    private MenuItem submit;

    @Override
    public void initViews() {

        setHasOptionsMenu(true);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            facilityID = bundle.getString(AppConstants.BUNDLE_KEYS.FACILITYID);
            getClinicDetails(facilityID);
        } else {
            getClinicID();
        }

        itemsList = ClinicProfileItem.getList();
        ListView lv_pro_list = (ListView) findView(R.id.lv_profile_option);
        customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_profile, itemsList, this);
        lv_pro_list.addHeaderView(getHeaderView());

        lv_pro_list.setAdapter(customListAdapter);
        lv_pro_list.setOnItemClickListener(this);

        bar = (ProgressBar) headerView.findViewById(R.id.progressBar3);
        setOnClickListener(R.id.btn_retry, R.id.layout_connectivity);

    }

    private void getClinicID() {
        executeTask(AppConstants.TASKCODES.GET_USER, BaseApiGenerator.getUserProfileData());
    }

    private void getClinicDetails(String facilityID) {
        if (CollectionUtils.isNotEmpty(facilityID)) {
            GetClinicBodyItem item = new GetClinicBodyItem(facilityID);
            HttpParamObject httpParamObject = ApiGenerator.getClinicDetails(item);
            executeTask(AppConstants.TASKCODES.GET_CLINIC_DETAILS, httpParamObject);
        }
    }

    private View getHeaderView() {
        return headerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header_profile_completeness, null);
    }

//
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//        switch (v.getId()) {
//            case R.id.btn_retry:
//                onRetryClicked();
//                addFragment();
//                break;
//        }
//    }

//    private void onRetryClicked() {
////        if (Util.isConnectingToInternet(getActivity())) {
////            FrameLayout errorLayout = (FrameLayout) findView(simplifii.framework.R.id.frame_noInternet);
////            errorLayout.setVisibility(View.GONE);
////          // findView(R.id.frame_noInternet).setVisibility(View.GONE);
////        }
//        Intent intent = new Intent(getActivity(), HomeActivity.class);
//        startActivity(intent);
//        getActivity().finish();
//    }

    private void addFragment() {
        Fragment fragment = new ProfileListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    @Override
    protected void onInternetException() {
        super.onInternetException();
        showVisibility(R.id.layout_connectivity);
    }


    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        if (response == null) {
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.GET_CLINIC_DETAILS:
                GetClinicResponseTable responseTable = (GetClinicResponseTable) response;
                bundle = new Bundle();
                bundle.putSerializable(AppConstants.BUNDLE_KEYS.CLINIC_DETAIL, responseTable);
                bundle.putString(AppConstants.BUNDLE_KEYS.FACILITYID, facilityID);
                clinicProfile.setClinicResponseTable(responseTable);
                getClinicFile(facilityID);
                break;
            case AppConstants.TASKCODES.GET_USER:
                UserProfileResponse profileResponse = (UserProfileResponse) response;
                List<ClinicResponse> clinicResponseList = profileResponse.getTable();
                if (clinicResponseList != null) {
                    for (int i = 0; i < clinicResponseList.size(); i++) {
                        facilityID = clinicResponseList.get(i).getFacilityID().toString();
                    }
                    getClinicDetails(facilityID);
                }
                break;

            case AppConstants.TASKCODES.GET_CLINIC_FILE:
                GetClinicFileResponse fileResponse = (GetClinicFileResponse) response;
                if (fileResponse != null) {
                    clinicProfile.setClinicFileResponse(fileResponse);
                    getCompleteClinicDetail();
                }
                break;
            case AppConstants.TASKCODES.SET_FINAL_CLINIC:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi != null && baseApi.isSuccess()) {
                    showToast(R.string.success);
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();

                } else {
                    showToast(R.string.fail);
                }
                break;
        }
    }

    private void getCompleteClinicDetail() {
        GetClinicResponseTable clinicResponseTable = clinicProfile.getClinicResponseTable();
        GetClinicFileResponse clinicFileResponse = clinicProfile.getClinicFileResponse();
        List<ClinicProfileItem> itemList = ClinicProfileItem.getList();
        bar.setProgress(0);
        if (clinicResponseTable != null || clinicFileResponse != null) {
            for (ClinicProfileItem profileItem : itemList) {
                int itemId = profileItem.getItemId();
                switch (itemId) {
                    case AppConstants.ClinicProfileConstants.CONTACT_CLINIC_ITEM_ID:
                        String facilityImage = null;
                        if (clinicFileResponse.getTable() != null) {
                            if (clinicResponseTable.getTable().size() > 0) {
                                for (int i = 0; i < clinicResponseTable.getTable().size(); i++) {
                                    facilityImage = clinicResponseTable.getTable().get(i).getFacilityImage();
                                }
                                if (CollectionUtils.isNotEmpty(facilityImage)) {
                                    profileItem.setCompleted(true);
                                    setHeaderData(25);
                                }
                            }

                        }
                        break;
                    case AppConstants.TimingsConstants.TIMINGS_ITEM_ID:
                        if (clinicFileResponse.getTable2() != null) {
                            profileItem.setCompleted(true);
                            setHeaderData(25);
                        }
                        break;
                    case AppConstants.LocationPhotosConstants.LOCATION_PHOTOS_ITEM_ID:
                        Double latitude = null, longitude = null;
                        if (clinicFileResponse.getTable1() != null) {
                            if (clinicResponseTable.getTable() != null) {
                                for (int i = 0; i < clinicResponseTable.getTable().size(); i++) {
                                    latitude = clinicResponseTable.getTable().get(i).getLatitude();
                                    longitude = clinicResponseTable.getTable().get(i).getLongitude();
                                }

                                if (latitude != null && longitude != null) {
                                    profileItem.setCompleted(true);
                                    setHeaderData(25);
                                }
                            }
                        }
                        break;
                    case AppConstants.ServiceConstants.SPECIALIZATIONS_SERVICE_ITEM_ID:
                        if (clinicResponseTable.getTable3() != null && clinicResponseTable.getTable3() != null) {
                            profileItem.setCompleted(true);
                            setHeaderData(25);
                        }
                        break;
                    case AppConstants.AwardConstants.AWARD_ACCREDITATION_ITEM_ID:
                        if (clinicFileResponse.getTable3() != null && clinicFileResponse.getTable4() != null) {
                            profileItem.setCompleted(true);
                        }
                        break;
                }
            }
        }
        itemsList.clear();
        itemsList.addAll(itemList);
        customListAdapter.notifyDataSetChanged();

        TextView tv = (TextView) headerView.findViewById(R.id.tv_profile_rating);
        if (tv.getText().toString().equals("100%")) {
            submit.setVisible(true);
        } else {
            submit.setVisible(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.submit_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_submit) {
            setClinicFinalDetails();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setClinicFinalDetails() {
        if (CollectionUtils.isNotEmpty(facilityID)) {
            SetClinicDetail setClinicDetail = new SetClinicDetail(facilityID);
            HttpParamObject httpParamObject = ApiGenerator.setDoctorDetails(setClinicDetail);
            executeTask(AppConstants.TASKCODES.SET_FINAL_CLINIC, httpParamObject);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        submit = menu.findItem(R.id.btn_submit);
    }


    private void getClinicFile(String facilityID) {
        GetFileItem fileItem = new GetFileItem(facilityID, "");
        HttpParamObject httpParamObject = ApiGenerator.getClinicFile(fileItem);
        executeTask(AppConstants.TASKCODES.GET_CLINIC_FILE, httpParamObject);

    }

    private void setHeaderData(int percentage) {
        bar.setMax(100);
        bar.setProgress(bar.getProgress() + percentage);
        TextView tv = (TextView) headerView.findViewById(R.id.tv_profile_rating);
        tv.setText(String.valueOf(bar.getProgress()) + "%");
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
                onItemClick(null, v, position, 0);
            }
        });
        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ClinicProfileItem item = itemsList.get(position);
        switch (item.getItemId()) {
            case AppConstants.ClinicProfileConstants.CONTACT_CLINIC_ITEM_ID:
                FragmentContainer.startActivityForResult(getActivity(), AppConstants.FRAGMENT_TYPES.CONTACT_CLINIC_DETAILS_FRAGMENT, bundle, AppConstants.REQUEST_CODES.CLINIC);
                break;
            case AppConstants.TimingsConstants.TIMINGS_ITEM_ID:
                FragmentContainer.startActivityForResult(getActivity(), AppConstants.FRAGMENT_TYPES.TIMINGS_FRAGMENT, bundle, AppConstants.REQUEST_CODES.TIMINGS);
                break;
            case AppConstants.LocationPhotosConstants.LOCATION_PHOTOS_ITEM_ID:
                FragmentContainer.startActivityForResult(getActivity(), AppConstants.FRAGMENT_TYPES.LOCATION_PHOTOS_FRAGMENT, bundle, AppConstants.REQUEST_CODES.LOCATION_DETAILS);
                break;
            case AppConstants.DoctorsConstants.DOCTORS_ITEM_ID:
                FragmentContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.DOCTORS_FRAGMENT, null);
                break;
            case AppConstants.ServiceConstants.SPECIALIZATIONS_SERVICE_ITEM_ID:
                FragmentContainer.startActivityForResult(getActivity(), AppConstants.FRAGMENT_TYPES.SPECIALIZATIONS_SERVICES_FRAGMENT, bundle, AppConstants.REQUEST_CODES.SERVICE_SPEC);
                break;
            case AppConstants.AwardConstants.AWARD_ACCREDITATION_ITEM_ID:
                FragmentContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.AWARD_ACCREDITATION_FRAGMENT, bundle);
                break;

        }
    }

    public void refresh() {
        getClinicDetails(facilityID);
    }


    class Holder {
        TextView tv_title;
        RelativeLayout completionSymbol;

        Holder(View view) {
            tv_title = (TextView) view.findViewById(R.id.tv_name);
            completionSymbol = (RelativeLayout) view.findViewById(R.id.rl_round_complete);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
