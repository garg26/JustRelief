package com.justreliefdoctors.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.justreliefdoctors.R;
import com.justreliefdoctors.models.DeleteDocFileItem;
import com.justreliefdoctors.models.GetDoctorFile;
import com.justreliefdoctors.models.SetAwardItem;
import com.justreliefdoctors.models.SetMembershipItem;
import com.justreliefdoctors.models.response.GetAllDocTableResponse;
import com.justreliefdoctors.models.response.GetDoctorAwardResponse;
import com.justreliefdoctors.models.response.GetDoctorAwardTableResponse;
import com.justreliefdoctors.models.response.GetDoctorDetailsTable;
import com.justreliefdoctors.utility.ApiGenerator;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.models.AutoCompleteApiRequest;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.GetClinicBodyItem;
import simplifii.framework.models.response.MasterValues;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.widgets.ContactsCompletionView;
import simplifii.framework.widgets.CustomFontTextView;


public class AwardMembershipFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {
    private List<MasterValues> membership_list = new ArrayList<>();
    private List<String> membership_labelList = new ArrayList<>();
    private String year;
    private List<String> list_selected_membership;
    private LinearLayout ll_award_container;

    @Override
    public void initViews() {
        ll_award_container = (LinearLayout) findView(R.id.ll_upload_award_container);
        list_selected_membership = new ArrayList<>();

        getDoctorFile();
        getMembershipList();
        setSpinAdapter(pickYear(), R.id.spin_year);
        setOnItemSelectedListener(this, R.id.spin_year);

        setOnClickListener(R.id.btn_membership_save, R.id.btn_award_save);

    }

    private void getDoctorFile() {
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID)) {
            GetDoctorFile doctorFile = new GetDoctorFile(doctorID, "");
            HttpParamObject httpParamObject = ApiGenerator.getDoctorFile(doctorFile);
            executeTask(AppConstants.TASKCODES.GET_DOCTOR_AWARD, httpParamObject);
        }
    }
    private void getDocMembership() {
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID)) {
            GetClinicBodyItem item = new GetClinicBodyItem(doctorID);
            HttpParamObject httpParamObject = ApiGenerator.getDoctorDetails(item);
            executeTask(AppConstants.TASKCODES.GET_DOCTOR_DETAILS, httpParamObject);
        }else{
            showToast(R.string.error);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_membership_save:
                saveMembership();
                break;
            case R.id.btn_award_save:
                saveAward(getEditText(R.id.et_title), year);
                break;
        }
    }

    private void saveAward(String award_title, String year) {
        if (CollectionUtils.isNotEmpty(getDoctorID())) {
            if (CollectionUtils.isNotEmpty(award_title)) {
                if (CollectionUtils.isNotEmpty(year)) {
                    SetAwardItem awardItem = new SetAwardItem(getDoctorID(), award_title, year);
                    HttpParamObject httpParamObject = ApiGenerator.setAward(awardItem);
                    executeTask(AppConstants.TASKCODES.SET_AWARD, httpParamObject);
                }else{
                    showToast(getString(R.string.error_year_empty));
                }
            } else {
                showToast(getString(R.string.error_award_title));
            }
        } else {
            showToast(getString(R.string.error));
        }
    }

    private void saveMembership() {
        if (CollectionUtils.isNotEmpty(getDoctorID())) {
            String memberIDs = getValueIDs(list_selected_membership, membership_list);
            if (CollectionUtils.isNotEmpty(memberIDs)) {
                SetMembershipItem membershipItem = new SetMembershipItem(getDoctorID(), memberIDs);
                HttpParamObject httpParamObject = ApiGenerator.setMembership(membershipItem);
                executeTask(AppConstants.TASKCODES.SET_MEMBERSHIP, httpParamObject);
            } else {
                showToast(getString(R.string.error_membership_empty));
            }
        }
    }

    private String getDoctorID() {
        String doctorID = null;
        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE);
        if (bundle != null) {
            doctorID = bundle.getString(AppConstants.BUNDLE_KEYS.DOCTOR_ID);
        }
        return doctorID;
    }

    private void getMembershipList() {

        HttpParamObject httpParamObject = BaseApiGenerator.getHttpToAutoComplete(AutoCompleteApiRequest.getAllMembershipRequest());
        executeTask(AppConstants.TASKCODES.GET_MEMBERSHIP, httpParamObject);
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_award_membership;
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        if (response == null) {
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.GET_MEMBERSHIP:
                List<MasterValues> masterValues = (List<MasterValues>) response;
                if (masterValues != null) {
                    membership_list.addAll(masterValues);
                    for (MasterValues values : masterValues) {
                        membership_labelList.add(values.getLabel());
                    }
                    setAdapter(membership_labelList, list_selected_membership, R.id.ccv_membership_title);
                    getDocMembership();
                }
                break;

            case AppConstants.TASKCODES.SET_MEMBERSHIP:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi.isSuccess()) {
                    showToast(getString(R.string.success));
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);

                } else {
                    showToast(getString(R.string.fail));
                }
                break;
            case AppConstants.TASKCODES.SET_AWARD:
                BaseApi baseApi1 = (BaseApi) response;
                if (baseApi1.isSuccess()) {
                    showToast(getString(R.string.success));
                    startFragment();
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                } else {
                    showToast(getString(R.string.fail));
                }
                break;

            case AppConstants.TASKCODES.GET_DOCTOR_AWARD:
                GetAllDocTableResponse tableResponse = (GetAllDocTableResponse) response;
                List<GetDoctorAwardResponse> awardResponseList = tableResponse.getTable4();
                if (awardResponseList != null) {
                    setAwardToLayout(awardResponseList);
                } else {
                    setNoRecord();
                }
                break;

            case AppConstants.TASKCODES.DELETE_FILE:
                BaseApi baseApi2 = (BaseApi) response;
                if (baseApi2.isSuccess()) {
                    showToast(getString(R.string.delete_successfully));
                    startFragment();
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);

                } else {
                    showToast(R.string.fail);
                }
                break;
            case AppConstants.TASKCODES.GET_DOCTOR_DETAILS:
                GetDoctorDetailsTable detailsTable = (GetDoctorDetailsTable) response;
                List<MasterValues> valuesList = detailsTable.getTable4();
                List<String> membership_list = new ArrayList<>();
                if (valuesList.size()>0){
                    for (MasterValues masterValues1 : valuesList){
                        membership_list.add(masterValues1.getLabel());
                    }
                    ContactsCompletionView completionView = (ContactsCompletionView) findView(R.id.ccv_membership_title);
                    for (String str : membership_list) {
                        membership_labelList.remove(str);
                        completionView.addObject(str);
                    }
                }

                break;

        }
    }

    private void startFragment() {
        Fragment fragment = new AwardMembershipFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    private void setAwardToLayout(List<GetDoctorAwardResponse> awardResponseList) {
        if (awardResponseList.size() == 0) {
            setNoRecord();
        } else {
            try {
                for (int i = 0; i < awardResponseList.size(); i++) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    final View view = inflater.inflate(R.layout.row_award_item, ll_award_container, false);
                    setText(R.id.tv_upload_award, awardResponseList.get(i).getAwardDesc(), view);
                    setText(R.id.tv_upload_award_year, awardResponseList.get(i).getAwardYear().toString(), view);
                    final Integer id = awardResponseList.get(i).getID();

                    ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
                    iv_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showAlterDialog(id, view, AppConstants.FILE_TYPES.DOCTOR_COUNCIL, ll_award_container);

                        }
                    });
                    ll_award_container.addView(view);
                }
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void delete(Integer id, View view, String fileType, LinearLayout ll_container) {
        ll_award_container.removeView(view);
        deleteEntry(id.toString(), AppConstants.FILE_TYPES.AWARD);
    }

    private void deleteEntry(String fileID, String fileType) {
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID) && CollectionUtils.isNotEmpty(fileID)) {
            DeleteDocFileItem filesItem = new DeleteDocFileItem(doctorID, fileID, fileType);
            HttpParamObject httpParamObject = ApiGenerator.deleteDoctorEntry(filesItem);
            executeTask(AppConstants.TASKCODES.DELETE_FILE, httpParamObject);
        }
    }

    private GetDoctorAwardResponse getAwardIndex(List<GetDoctorAwardResponse> awardResponseList) {
        return awardResponseList.get(0);
    }

    private void setNoRecord() {
        CustomFontTextView textView = (CustomFontTextView) findView(R.id.tv_no_record);
        textView.setVisibility(View.VISIBLE);
        textView.setText(getString(R.string.no_record_found));
    }

    private void setAdapter(List<String> membership_labelList, List<String> list_selected_membership, int ccv_membership_title) {
        setContactCompletionView(membership_labelList, list_selected_membership, ccv_membership_title);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_year:
                year = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
