package com.justreliefdoctors.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.justreliefdoctors.R;
import com.justreliefdoctors.enums.Qualification;
import com.justreliefdoctors.models.ContactDoctorItem;
import com.justreliefdoctors.models.DeleteDocFileItem;
import com.justreliefdoctors.models.GetDoctorEducation;
import com.justreliefdoctors.models.GetDoctorFile;
import com.justreliefdoctors.models.SetDocSpecializationItem;
import com.justreliefdoctors.models.response.GetAllDocTableResponse;
import com.justreliefdoctors.models.response.GetDoctorDetailsTable;
import com.justreliefdoctors.utility.ApiGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.models.AutoCompleteApiRequest;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.GetClinicBodyItem;
import simplifii.framework.models.response.MasterValues;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.widgets.ContactsCompletionView;
import simplifii.framework.widgets.CustomAutoResizeTextView;
import simplifii.framework.widgets.CustomFontTextView;

public class QualificationSpecFragment extends AppBaseFragment {

    private List<MasterValues> collegeList = new ArrayList<>();
    private List<String> college_label = new ArrayList<>();
    private List<String> qualification_list = new ArrayList<>();
    private String year;
    private LinearLayout ll_education_container;
    private List<String> specialization_list = new ArrayList<>();
    private List<String> specialization_selected_list = new ArrayList<>();
    private List<MasterValues> doc_specialization = new ArrayList<>();
    private List<MasterValues> qualificationList = new ArrayList<>();

    @Override
    public void initViews() {
        ll_education_container = (LinearLayout) findView(R.id.ll_container);

        getEducationDetail();
        getDoctorSpecialization();
        getDocSpecList();
        getCollegeList();
        getQualificationList();

        String[] year_list = pickYear();
        setAdapter(year_list, R.id.spin_year);
        Spinner sp_year = (Spinner) findView(R.id.spin_year);
        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        allQualification = Qualification.getAllQualification(getContext());
//        try {
//            for (int i = 1; i <= allQualification.size(); i++) {
//                String string = allQualification.get(i);
//                qualification_list.add(string);
//            }
//        }catch (IndexOutOfBoundsException e){
//            e.printStackTrace();
//        }


        setContactCompletionView(specialization_list, specialization_selected_list, R.id.spin_specialization);
        setOnClickListener(R.id.btn_save_education,R.id.btn_save_specialization);

    }

    private void getDocSpecList() {
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID)) {
            GetClinicBodyItem item = new GetClinicBodyItem(doctorID);
            HttpParamObject httpParamObject = ApiGenerator.getDoctorDetails(item);
            executeTask(AppConstants.TASKCODES.GET_DOCTOR_DETAILS, httpParamObject);
        }
        else{
            showToast(R.string.error);
        }

    }

    private void getDoctorSpecialization() {
        HttpParamObject httpParamObject = BaseApiGenerator.getHttpToAutoComplete(AutoCompleteApiRequest.getAllDoctorSpecialization());
        executeTask(AppConstants.TASKCODES.GET_DOCTOR_SPECIALIZATION, httpParamObject);
    }

    private void getEducationDetail() {
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID)) {
            GetDoctorFile doctorFile = new GetDoctorFile(getDoctorID(), null);
            HttpParamObject httpParamObject = ApiGenerator.getDoctorFile(doctorFile);
            executeTask(AppConstants.TASKCODES.GET_DOCTOR_DOCUMENT, httpParamObject);
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

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_save_education) {
            setQualification(getACTVText(R.id.actv_qualification), getACTVText(R.id.actv_college), year);

        } else if (i == R.id.btn_save_specialization) {
            saveDocSpecialization();

        }
    }

    private void saveDocSpecialization() {
        String doctorID = getDoctorID();
        String valueIDs = getValueIDs(specialization_selected_list, doc_specialization);
        if (CollectionUtils.isNotEmpty(doctorID) && CollectionUtils.isNotEmpty(valueIDs)){
            SetDocSpecializationItem specializationItem = new SetDocSpecializationItem(doctorID,valueIDs);
            HttpParamObject httpParamObject = ApiGenerator.SetDocSpecialization(specializationItem);
            executeTask(AppConstants.TASKCODES.SET_DOCTOR_SPEC,httpParamObject);
        }
        else{
            showToast(R.string.error);
        }
    }

    private void setQualification(String qualification, String college, String year) {
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID) && CollectionUtils.isNotEmpty(qualification) && CollectionUtils.isNotEmpty(college) && CollectionUtils.isNotEmpty(year)) {
            String qualificationID = getValueID(qualificationList, qualification);
            Integer collegeID = getCollegeID(college);
            if (CollectionUtils.isNotEmpty(qualificationID) && CollectionUtils.isNotEmpty(collegeID.toString())) {
                ContactDoctorItem doctorItem = new ContactDoctorItem(doctorID, Integer.parseInt(qualificationID), collegeID, Integer.parseInt(year));
                HttpParamObject httpParamObject = ApiGenerator.setQualification(doctorItem);
                executeTask(AppConstants.TASKCODES.DOCTOR_EDUCATION, httpParamObject);
            }else{
                showToast(getString(R.string.error_write_correct_information));
            }
        }
        else{
            showToast(R.string.error);
        }
    }

    private Integer getCollegeID(String college) {

        int value = 0;
        try {


            for (int i = 0; i < collegeList.size(); i++) {
                if (collegeList.get(i).getLabel().equals(college)) {
                    value = collegeList.get(i).getValue();
                }
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return value;
    }

    private void setAdapter(String[] year_list, int spinID) {
        Spinner spinner = (Spinner) findView(spinID);

        if (year_list != null) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, year_list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }
    }

    private void getCollegeList() {
        HttpParamObject httpParamObject = ApiGenerator.getCollegeList(AutoCompleteApiRequest.getAllCollegeRequest());
        executeTask(AppConstants.TASKCODES.GET_COLLEGE, httpParamObject);
    }
    private void getQualificationList(){
        HttpParamObject httpParamObject = ApiGenerator.getCollegeList(AutoCompleteApiRequest.getAllQUALIFICATIONRequest());
        executeTask(AppConstants.TASKCODES.GET_QUALIFICATION_LIST, httpParamObject);
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_qualification_specialization_details;
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        switch (taskCode) {
            case AppConstants.TASKCODES.GET_COLLEGE:
                List<MasterValues> valuesList = (List<MasterValues>) response;
                if (valuesList != null) {
                    collegeList.addAll(valuesList);
                }
                for (MasterValues values : valuesList) {
                    college_label.add(values.getLabel());
                }
                setAdapter(college_label, R.id.actv_college);
                break;
            case AppConstants.TASKCODES.DOCTOR_EDUCATION:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi.isSuccess()) {
                    showToast(getString(R.string.information_save_successfully));
                    startFragment(new QualificationSpecFragment());
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                } else {
                    showToast(getString(R.string.fail));
                }
                break;
            case AppConstants.TASKCODES.GET_DOCTOR_DOCUMENT:
                GetAllDocTableResponse docTableResponse = (GetAllDocTableResponse) response;
                List<GetDoctorEducation> educationList = docTableResponse.getTable();
                if (educationList != null) {
                    addEducationToLayout(educationList);
                } else {
                    showToast(R.string.error);
                }
                break;
            case AppConstants.TASKCODES.DELETE_EDUCATION:
                BaseApi baseApi1 = (BaseApi) response;
                if (baseApi1.isSuccess()) {
                    showToast(R.string.delete_successfully);
                    startFragment(new QualificationSpecFragment());
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                } else {
                    showToast(R.string.error);
                }
                break;
            case AppConstants.TASKCODES.GET_DOCTOR_SPECIALIZATION:
                List<MasterValues> values = (List<MasterValues>) response;
                doc_specialization.addAll(values);
                for (MasterValues masterValues : values) {
                    specialization_list.add(masterValues.getLabel());
                }
                break;
            case AppConstants.TASKCODES.GET_DOCTOR_DETAILS:
                GetDoctorDetailsTable detailsTable = (GetDoctorDetailsTable) response;
                List<MasterValues> masterValues = detailsTable.getTable3();
                List<String> spec_list = new ArrayList<>();
                if (masterValues.size()>0){
                   for (MasterValues masterValues1 : masterValues){
                       spec_list.add(masterValues1.getLabel());
                   }
                    ContactsCompletionView completionView = (ContactsCompletionView) findView(R.id.spin_specialization);
                    for (String str : spec_list) {
                        completionView.addObject(str);
                    }
                }
                break;

            case AppConstants.TASKCODES.SET_DOCTOR_SPEC:
                BaseApi baseApi2 = (BaseApi) response;
                if (baseApi2.isSuccess()){
                    showToast(R.string.information_save_successfully);
                    startFragment(new QualificationSpecFragment());
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                }else{
                    showToast(R.string.fail);
                }
                break;

            case AppConstants.TASKCODES.GET_QUALIFICATION_LIST:
                List<MasterValues> masterValuesList = (List<MasterValues>) response;
                if (masterValuesList!=null){
                    qualificationList.addAll(masterValuesList);
                    for (MasterValues masterValues1 : masterValuesList){
                        qualification_list.add(masterValues1.getLabel());
                    }
                }
                setAdapter(qualification_list, R.id.actv_qualification);
                break;


        }
    }

    private void addEducationToLayout(List<GetDoctorEducation> educationList) {
        if (educationList.size() == 0) {
            setNoRecord();
        } else {
            try {
            for (int i = 0; i < educationList.size(); i++) {

                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    final View view = inflater.inflate(R.layout.row_qualification_item, ll_education_container, false);

                    CustomAutoResizeTextView tv_upload_qualification = (CustomAutoResizeTextView) view.findViewById(R.id.tv_upload_qualification);
                    CustomAutoResizeTextView tv_upload_college_name = (CustomAutoResizeTextView) view.findViewById(R.id.tv_upload_college_name);
                    CustomAutoResizeTextView tv_upload_passing_year = (CustomAutoResizeTextView) view.findViewById(R.id.tv_upload_passing_year);
                    RelativeLayout rl_delete = (RelativeLayout) view.findViewById(R.id.rl_delete);
                    GetDoctorEducation getDoctorEducation = educationList.get(i);
                    final Integer id = getDoctorEducation.getID();

                    rl_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showAlterDialog(id, view, AppConstants.FILE_TYPES.DOCTOR_COUNCIL, ll_education_container);
                        }
                    });


                    tv_upload_qualification.setText(getDoctorEducation.getQualName());
                    tv_upload_college_name.setText(getDoctorEducation.getCollegeName());
                    tv_upload_passing_year.setText(getDoctorEducation.getPassYear().toString());

                    ll_education_container.addView(view);
                }
                }catch (NullPointerException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void delete(Integer id, View view, String fileType, LinearLayout ll_container) {
        ll_education_container.removeView(view);
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID)) {
            DeleteDocFileItem docFileItem = new DeleteDocFileItem(doctorID, id.toString(), AppConstants.FILE_TYPES.DOCTOR_EDUCATION);
            HttpParamObject httpParamObject = ApiGenerator.deleteDoctorEntry(docFileItem);
            executeTask(AppConstants.TASKCODES.DELETE_EDUCATION, httpParamObject);
        }
    }

    private void setNoRecord() {
        CustomFontTextView tv_no_record = (CustomFontTextView) findView(R.id.tv_no_record);
        tv_no_record.setVisibility(View.VISIBLE);
        tv_no_record.setGravity(Gravity.CENTER);
        tv_no_record.setText(R.string.no_record_found);
    }



}
