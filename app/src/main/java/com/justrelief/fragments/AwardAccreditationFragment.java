package com.justrelief.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.justrelief.R;
import com.justrelief.models.response.GetAccreditationResponse;
import com.justrelief.models.response.GetAccreditationTableResponse;
import com.justrelief.models.response.GetAwardResponse;
import com.justrelief.models.response.GetAwardResponseTable;
import com.justrelief.utility.ApiGenerator;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.models.AwardResponse;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.DeleteFilesItem;
import simplifii.framework.models.GetFileItem;
import simplifii.framework.models.MasterListResponse;
import simplifii.framework.models.response.MasterValues;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.widgets.CustomFontTextView;


public class AwardAccreditationFragment extends AppBaseFragment implements AdapterView.OnItemSelectedListener {

    private String award_year,accreditation_year,accreditation;
    private List<MasterValues> accreditation_list;
    private List<String> list_accreditation;
   // private Spinner sp_accreditation;
    private LinearLayout ll_upload_award_container,ll_upload_accr_container;

    @Override
    public void initViews() {

        ll_upload_award_container = (LinearLayout) findView(R.id.ll_upload_award_container);
        ll_upload_accr_container = (LinearLayout) findView(R.id.ll_upload_accreditation_container);

//        Spinner sp_awrad_year = (Spinner) findView(R.id.spin_award_year);
//        Spinner sp_accreditation_year = (Spinner) findView(R.id.spin_accreditation_year);
//        sp_accreditation = (Spinner) findView(R.id.spin_accreditation);

        accreditation_list = new ArrayList<>();
        list_accreditation = new ArrayList<>();

        getaccreditation();
        String[] pickYear = AppBaseFragment.pickYear();

        setAdapter(pickYear,R.id.spin_award_year);
        setAdapter(pickYear,R.id.spin_accreditation_year);

        getAwardResponse();
        getaccreditationResponse();
        setOnItemSelectedListener(this,R.id.spin_accreditation,R.id.spin_award_year,R.id.spin_accreditation_year);

//        sp_accreditation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                 accreditation = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        sp_awrad_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                award_year = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        sp_accreditation_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                 accreditation_year = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        setOnClickListener(R.id.btn_award_save,R.id.btn_accreditation_save);
    }
    private String getFacilityID(){
        String facilityID = null;
        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE);
        if (bundle.size() > 0) {
            facilityID = bundle.getString(AppConstants.BUNDLE_KEYS.FACILITYID);
        }
        return facilityID;
    }

    private void getaccreditationResponse() {
        String facilityID = getFacilityID();
        if (CollectionUtils.isNotEmpty(facilityID)) {
            GetFileItem fileItem = new GetFileItem(facilityID, AppConstants.FILE_TYPES.accreditation);
            HttpParamObject httpParamObject = ApiGenerator.getaccreditationFile(fileItem);
            executeTask(AppConstants.TASKCODES.GET_accreditation, httpParamObject);
        }
    }

    private void getAwardResponse() {
        String facilityID = getFacilityID();
        if (CollectionUtils.isNotEmpty(facilityID)) {
            GetFileItem fileItem = new GetFileItem((String.valueOf(facilityID)), AppConstants.FILE_TYPES.AWARD);
            HttpParamObject httpParamObject = ApiGenerator.getAwardFile(fileItem);
            executeTask(AppConstants.TASKCODES.GET_AWARD, httpParamObject);
        }
    }

    private void getaccreditation() {
        HttpParamObject httpParamObject = BaseApiGenerator.getMasterList();
        executeTask(AppConstants.TASKCODES.MASTER_LIST,httpParamObject);
    }

    private void setAdapter(String[] year_list, int spinID) {
        Spinner spinner = (Spinner) findView(spinID);

        if (year_list!=null) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, year_list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_award_save) {
            setAward();

        } else if (i == R.id.btn_accreditation_save) {
            setaccreditation();

        }
    }



    private void setAward() {
        String facilityID = getFacilityID();
        try {
            if (CollectionUtils.isNotEmpty(getEditText(R.id.et_award))) {
                if (CollectionUtils.isNotEmpty(award_year)) {
                    AwardResponse awardResponse = new AwardResponse(facilityID, getEditText(R.id.et_award), award_year, AppConstants.AutoCompleteTypes.AWARD);
                    HttpParamObject httpParamObject = BaseApiGenerator.addAward(awardResponse);
                    executeTask(AppConstants.TASKCODES.AWARD, httpParamObject);
                } else {
                    showToast(getString(R.string.error_award_year_empty));
                }
            } else {
                showToast(getString(R.string.award_title_empty));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    private void setaccreditation() {
        String facilityID = getFacilityID();
        if (CollectionUtils.isNotEmpty(accreditation) && !accreditation.equals("Select")) {
            if (CollectionUtils.isNotEmpty(accreditation_year)) {
                String accreditationID = getaccreditationID(accreditation);
                if (CollectionUtils.isNotEmpty(accreditationID)) {
                    AwardResponse awardResponse = new AwardResponse(facilityID, accreditationID, accreditation_year, AppConstants.AutoCompleteTypes.accreditation);
                    HttpParamObject httpParamObject = BaseApiGenerator.addaccreditation(awardResponse);
                    executeTask(AppConstants.TASKCODES.accreditation, httpParamObject);
                }
            }else{
                showToast(getString(R.string.error_accreditation_year_empty));
            }
        }else{
            showToast(getString(R.string.error_accreditation_empty));
        }

    }

    private String getaccreditationID(String accreditation) {

        int value = 0;
        for (int i=0;i<accreditation_list.size();i++){
            if (accreditation_list.get(i).getLabel().equals(accreditation)){
                 value = accreditation_list.get(i).getValue();
            }
        }
        return String.valueOf(value);
    }


    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        switch (taskCode){
            case AppConstants.TASKCODES.MASTER_LIST:
                MasterListResponse masterListResponse = (MasterListResponse) response;
                List<MasterValues> accreditationList = masterListResponse.getTable2();
                accreditation_list.addAll(accreditationList);

                list_accreditation.add("Select");
                for (MasterValues label : accreditation_list){
                    list_accreditation.add(label.getLabel());
                }
                setAdapter(list_accreditation.toArray(new String[list_accreditation.size()]),R.id.spin_accreditation);

                break;
            case AppConstants.TASKCODES.AWARD:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi!=null && baseApi.isSuccess()){
                    showToast(getString(R.string.information_save_successfully));
                    startFragment();
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                }
                else{
                    showToast(getString(R.string.fail));
                }
                break;
            case AppConstants.TASKCODES.accreditation:
                BaseApi baseApi1 = (BaseApi) response;
                if (baseApi1!=null && baseApi1.isSuccess()){
                    showToast(getString(R.string.information_save_successfully));
                    startFragment();
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                }
                else{
                    showToast(getString(R.string.fail));
                }

                break;
            case AppConstants.TASKCODES.GET_AWARD:

                GetAwardResponseTable responseTable = (GetAwardResponseTable) response;
                if (response!=null){
                    List<GetAwardResponse> awardResponseList = responseTable.getTable();
                    addAwardToLayout(awardResponseList);
                }
                break;

            case AppConstants.TASKCODES.GET_accreditation:
                GetAccreditationTableResponse responseTable1 = (GetAccreditationTableResponse) response;
                if (responseTable1!=null){
                    List<GetAccreditationResponse> responseList = responseTable1.getTable();
                    if (responseList!=null){
                        addAccrToLayout(responseList);
                    }
                }
                break;
            case AppConstants.TASKCODES.DELETE_FILE:
                BaseApi baseApi2 = (BaseApi) response;
                if (baseApi2.isSuccess()){
                    showToast(getString(R.string.delete_successfully));
                }
                startFragment();
                break;


        }
    }

    private void startFragment() {
        Fragment fragment = new AwardAccreditationFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();

    }

    private void addAwardToLayout(List<GetAwardResponse> awardResponseList) {

        if (awardResponseList==null || awardResponseList.size()==0){
            setNoRecord(R.id.tv_no_record);
        }
        else{
            for (int i=0;i<awardResponseList.size();i++) {
                String docDesc = awardResponseList.get(i).getDocDesc();
                Integer awardYear = awardResponseList.get(i).getAwardYear();
                final Integer fileID = awardResponseList.get(i).getID();

                final View view = addViewToLayout();

                CustomFontTextView tv_award = (CustomFontTextView) view.findViewById(R.id.tv_titles);
                CustomFontTextView tv_year = (CustomFontTextView) view.findViewById(R.id.tv_titles_year);

                tv_award.setText(docDesc);
                tv_year.setText(awardYear.toString());

                RelativeLayout rl_delete = (RelativeLayout) view.findViewById(R.id.rl_delete);
                rl_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteEntry(fileID.toString(), AppConstants.FILE_TYPES.AWARD);
                        ll_upload_award_container.removeView(view);
                    }
                });

                ll_upload_award_container.addView(view);
            }
        }

    }

    private void addAccrToLayout(List<GetAccreditationResponse> responseList) {
        if (responseList==null || responseList.size()==0){
            setNoRecord(R.id.tv_no_record1);
        }else{
            for (int i =0;i<responseList.size();i++){
                final View view = addViewToLayout1();
                CustomFontTextView tv_award = (CustomFontTextView)view.findViewById(R.id.tv_titles);
                CustomFontTextView tv_year = (CustomFontTextView)view.findViewById(R.id.tv_titles_year);

                final GetAccreditationResponse getaccreditationResponse = responseList.get(i);
                String docDesc = getaccreditationResponse.getDocDesc();
                Integer accrYear = getaccreditationResponse.getAccrYear();

                RelativeLayout rl_delete = (RelativeLayout) view.findViewById(R.id.rl_delete);
                rl_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Integer fileID = getaccreditationResponse.getID();
                        deleteEntry(fileID.toString(),AppConstants.FILE_TYPES.accreditation);
                        ll_upload_accr_container.removeView(view);
                    }
                });

                tv_award.setText(docDesc);
                tv_year.setText(accrYear.toString());

                ll_upload_accr_container.addView(view);

            }
        }
    }

    private View addViewToLayout1() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        return inflater.inflate(R.layout.row_award_acceredation, ll_upload_accr_container, false);

    }


    private void deleteEntry(String fileID, String fileType) {
        String facilityID = getFacilityID();
        if (CollectionUtils.isNotEmpty(facilityID)) {
            DeleteFilesItem filesItem = new DeleteFilesItem(facilityID, fileID, fileType);
            HttpParamObject httpParamObject = BaseApiGenerator.deleteTimeEntry(filesItem);
            executeTask(AppConstants.TASKCODES.DELETE_FILE, httpParamObject);
        }
    }

    private View addViewToLayout() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        return inflater.inflate(R.layout.row_award_acceredation, ll_upload_award_container, false);
    }

    private void setNoRecord(int id) {
        CustomFontTextView tv_no_record = (CustomFontTextView) findView(id);
        tv_no_record.setVisibility(View.VISIBLE);
        tv_no_record.setGravity(Gravity.CENTER);
        tv_no_record.setText(R.string.no_record_found);
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {

        switch (taskCode){
            case AppConstants.TASKCODES.AWARD:
                showToast(getString(R.string.error));
                break;
            case AppConstants.TASKCODES.accreditation:
                showToast(getString(R.string.error));
                break;
        }
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_award_acceredations;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spin_accreditation) {
            accreditation = parent.getItemAtPosition(position).toString();

        } else if (i == R.id.spin_award_year) {
            award_year = parent.getItemAtPosition(position).toString();

        } else if (i == R.id.spin_accreditation_year) {
            accreditation_year = parent.getItemAtPosition(position).toString();

        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
