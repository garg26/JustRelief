package com.justreliefdoctors.fragment;

import android.os.Bundle;
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

import com.justreliefdoctors.R;
import com.justreliefdoctors.activity.FragmentContainer;
import com.justreliefdoctors.models.CompleteDoctorDetail;
import com.justreliefdoctors.models.DoctorProfileItem;
import com.justreliefdoctors.models.GetDoctorFile;
import com.justreliefdoctors.models.SetDoctorDetail;
import com.justreliefdoctors.models.response.GetAllDocTableResponse;
import com.justreliefdoctors.models.response.GetDoctorDetails;
import com.justreliefdoctors.models.response.GetDoctorDetailsTable;
import com.justreliefdoctors.utility.ApiGenerator;

import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.GetClinicBodyItem;
import simplifii.framework.models.response.DoctorResponse;
import simplifii.framework.models.response.UserProfileResponse;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.utility.Preferences;

public class DoctorListFragment extends BaseFragment implements CustomListAdapterInterface, AdapterView.OnItemClickListener {
    private List<DoctorProfileItem> itemsList;
    private View headerView;
    private Bundle bundle = new Bundle();
    private CustomListAdapter customListAdapter;
    private CompleteDoctorDetail doctorDetail = new CompleteDoctorDetail();
    private MenuItem submit;
    private String doctorID;
    private ProgressBar bar;


    @Override
    public void initViews() {
        setHasOptionsMenu(true);

            getDoctorID();



        itemsList = DoctorProfileItem.getList();
        ListView lv_pro_list = (ListView) findView(R.id.lv_profile_option);
        customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_profile, itemsList, this);
        lv_pro_list.setOnItemClickListener(this);
        lv_pro_list.addHeaderView(getHeaderView());
        lv_pro_list.setAdapter(customListAdapter);


        bar = (ProgressBar) headerView.findViewById(R.id.progressBar3);
        setOnClickListener(R.id.btn_retry, R.id.layout_connectivity, R.id.lay_profile_complete);
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
            setDoctorDetail();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDoctorDetail() {
        if (CollectionUtils.isNotEmpty(doctorID)) {
            SetDoctorDetail setDoctorDetail = new SetDoctorDetail(doctorID);
            HttpParamObject httpParamObject = ApiGenerator.setDoctorDetails(setDoctorDetail);
            executeTask(AppConstants.TASKCODES.SET_FINAL_DOCTOR, httpParamObject);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        submit = menu.findItem(R.id.btn_submit);
    }

    private void getDoctorID() {
        HttpParamObject httpParamObject = BaseApiGenerator.getUserProfileData();
        executeTask(AppConstants.TASKCODES.GET_DOCTOR_PROFILE, httpParamObject);
    }

    private void getDoctorDetails(String facilityID) {
        GetClinicBodyItem item = new GetClinicBodyItem(facilityID);
        HttpParamObject httpParamObject = ApiGenerator.getDoctorDetails(item);
        executeTask(AppConstants.TASKCODES.GET_DOCTOR_DETAILS, httpParamObject);
    }

    public View getHeaderView() {
        return headerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header_profile_completeness, null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DoctorProfileItem item = itemsList.get(position);
        switch (item.getItemId()) {
            case AppConstants.DoctorProfileConstants.DOCTOR_ITEM_ID:
                FragmentContainer.startActivityForResult(getActivity(), AppConstants.FRAGMENT_TYPES.CONTACT_DOCTOR_DETAILS_FRAGMENT, bundle, AppConstants.REQUEST_CODES.DOCTOR_CONTACT);
                break;
            case AppConstants.QualificationDetails.QUALIFICATION_ITEM_ID:
                FragmentContainer.startActivityForResult(getActivity(), AppConstants.FRAGMENT_TYPES.QUALIFICATION_FRAGMENT, bundle, AppConstants.REQUEST_CODES.QUALIFICATION);
                break;
            case AppConstants.RegistrationDetails.REGISTRATION_ITEM_ID:
                FragmentContainer.startActivityForResult(getActivity(), AppConstants.FRAGMENT_TYPES.REGISTRATION_DETAILS_FRAGMENT, bundle, AppConstants.REQUEST_CODES.REGISTRATION);
                break;
            case AppConstants.Clinic.CLINIC_ITEM_ID:
                FragmentContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.CLINIC_FRAGMENT, null);
                break;
            case AppConstants.Services.SERVICE_ITEM_ID:
                FragmentContainer.startActivityForResult(getActivity(), AppConstants.FRAGMENT_TYPES.SERVICES_FRAGMENT, bundle, AppConstants.REQUEST_CODES.SERVICE);
                break;
            case AppConstants.AwardMembership.AWARD_ITEM_ID:
                FragmentContainer.startActivityForResult(getActivity(), AppConstants.FRAGMENT_TYPES.AWARD_MEMBERSHIP_FRAGMENT, bundle, AppConstants.REQUEST_CODES.AWARD);
                break;

        }

    }

    private void setHeaderData(int percentage) {
        bar.setMax(100);
        bar.setProgress(bar.getProgress() + percentage);
        TextView tv = (TextView) headerView.findViewById(R.id.tv_profile_rating);
        tv.setText(String.valueOf(bar.getProgress()) + "%");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_retry:
                break;
        }
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        if (response == null) {
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.GET_DOCTOR_PROFILE:
                UserProfileResponse profileResponse = (UserProfileResponse) response;
                if (profileResponse != null) {
                    List<DoctorResponse> doctorResponseList = profileResponse.getTable1();
                    doctorID = getDoctorData(doctorResponseList);
                    bundle.putString(AppConstants.BUNDLE_KEYS.DOCTOR_ID, doctorID);
                    getDoctorDetails(doctorID);
                }

                break;
            case AppConstants.TASKCODES.GET_DOCTOR_DETAILS:
                GetDoctorDetailsTable detailsTable = (GetDoctorDetailsTable) response;
                if (detailsTable != null) {
                    bundle.putSerializable(AppConstants.BUNDLE_KEYS.DOCTOR_DETAILS, detailsTable);
                    doctorDetail.setDetailsTable(detailsTable);
                    getDoctor(doctorID);
                }


                break;
            case AppConstants.TASKCODES.GET_DOCTOR:
                GetAllDocTableResponse docTableResponse = (GetAllDocTableResponse) response;
                if (docTableResponse != null) {
                    doctorDetail.setTableResponse(docTableResponse);
                    getCompleteDoctorDetail();
                }

                break;
            case AppConstants.TASKCODES.SET_FINAL_DOCTOR:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi.isSuccess()) {
                    showToast("Success");
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HospitalClinicFragment()).commit();
                } else {
                    showToast("Fail");
                }
                break;
        }
    }

    private void getDoctor(String doctorID) {
        if (CollectionUtils.isNotEmpty(doctorID)) {
            GetDoctorFile doctorFile = new GetDoctorFile(doctorID, "");
            HttpParamObject httpParamObject = ApiGenerator.getDoctorFile(doctorFile);
            executeTask(AppConstants.TASKCODES.GET_DOCTOR, httpParamObject);
        }
    }

    private String getDoctorData(List<DoctorResponse> doctorResponseList) {
        Integer doctorID = null;
        if (doctorResponseList.size() > 0) {
            DoctorResponse index = getIndex(doctorResponseList);
            if (index != null) {
                doctorID = index.getDoctorID();
            }
        }
        if (doctorID != null) {
            return doctorID.toString();
        }
        return null;
    }


    private void getCompleteDoctorDetail() {
        GetDoctorDetailsTable detailsTable = doctorDetail.getDetailsTable();
        GetAllDocTableResponse tableResponse = doctorDetail.getTableResponse();
        List<DoctorProfileItem> list = DoctorProfileItem.getList();
        bar.setProgress(0);
        if (detailsTable != null || tableResponse != null) {
            for (DoctorProfileItem profileItem : list) {
                int itemId = profileItem.getItemId();

                switch (itemId) {
                    case AppConstants.DoctorProfileConstants.DOCTOR_ITEM_ID:
                        for (int i = 0; i < detailsTable.getTable().size(); i++) {
                            List<GetDoctorDetails> table = detailsTable.getTable();
                            String doctorImageURL = table.get(i).getDoctorImageURL();
                            if (CollectionUtils.isNotEmpty(doctorImageURL)) {
                                profileItem.setCompleted(true);
                                setHeaderData(25);
                            }
                        }
                        break;

                    case AppConstants.QualificationDetails.QUALIFICATION_ITEM_ID:
                        if (detailsTable.getTable3().size() > 0 && tableResponse.getTable().size() > 0) {
                            profileItem.setCompleted(true);
                            setHeaderData(25);
                        }
                        break;

                    case AppConstants.RegistrationDetails.REGISTRATION_ITEM_ID:
                        if (tableResponse.getTable1().size() > 0 && tableResponse.getTable2().size() > 0) {
                            profileItem.setCompleted(true);
                            setHeaderData(25);
                        }
                        break;
                    case AppConstants.Services.SERVICE_ITEM_ID:
                        if (tableResponse.getTable3().size() > 0 && detailsTable.getTable2().size() > 0) {
                            profileItem.setCompleted(true);
                            setHeaderData(25);
                        }
                        break;
                    case AppConstants.AwardConstants.AWARD_ACCREDITATION_ITEM_ID:
                        if (tableResponse.getTable4().size() > 0 && detailsTable.getTable4().size() > 0) {
                            profileItem.setCompleted(true);
                        }
                        break;
                    default:
                        break;

                }
            }
        }
        itemsList.clear();
        itemsList.addAll(list);
        customListAdapter.notifyDataSetChanged();

        TextView tv = (TextView) headerView.findViewById(R.id.tv_profile_rating);
        if (tv.getText().toString().equals("100%")) {
            submit.setVisible(true);
        } else {
            submit.setVisible(false);
        }

    }


    private DoctorResponse getIndex(List<DoctorResponse> doctorResponseList) {
        DoctorResponse doctorResponse = null;
        for (int i = 0; i < doctorResponseList.size(); i++) {
            doctorResponse = doctorResponseList.get(i);
        }

        return doctorResponse;
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
        DoctorProfileItem item = itemsList.get(position);
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

    public void refresh() {
        getDoctorID();
    }

    class Holder {
        TextView tv_title;
        RelativeLayout completionSymbol;

        Holder(View view) {
            tv_title = (TextView) view.findViewById(R.id.tv_name);
            completionSymbol = (RelativeLayout) view.findViewById(R.id.rl_round_complete);
        }
    }


}
