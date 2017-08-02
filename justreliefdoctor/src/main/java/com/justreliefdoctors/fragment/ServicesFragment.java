package com.justreliefdoctors.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.justreliefdoctors.R;
import com.justreliefdoctors.models.DeleteDocFileItem;
import com.justreliefdoctors.models.GetDoctorFile;
import com.justreliefdoctors.models.SetDoctorExperienceItem;
import com.justreliefdoctors.models.SetServiceItem;
import com.justreliefdoctors.models.response.GetAllDocTableResponse;
import com.justreliefdoctors.models.response.GetDocExperienceResponse;
import com.justreliefdoctors.models.response.GetDoctorDetailsTable;
import com.justreliefdoctors.util.DatePickerUtil;
import com.justreliefdoctors.utility.ApiGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.models.AutoCompleteApiRequest;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.GetClinicBodyItem;
import simplifii.framework.models.response.MasterValues;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.widgets.CustomFontTextView;

public class ServicesFragment extends AppBaseFragment {
    private DatePickerUtil datePickerUtil = new DatePickerUtil();
    private List<MasterValues> service_list = new ArrayList<>();
    private List<String> serviceList = new ArrayList<>();
    private List<String> serviceList_selected = new ArrayList<>();
    private LinearLayout ll_service_container, ll_experience;
    private List<String> list_service;
    private List<MasterValues> city_list = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();
    private String fromDate, toDate;


    @Override
    public void initViews() {

        getDoctorServiceList();
        ll_service_container = (LinearLayout) findView(R.id.ll_service_container);
        ll_experience = (LinearLayout) findView(R.id.ll_container);
        getDocExperience();
        getAllCity();

        AutoCompleteTextView actv_service = (AutoCompleteTextView) findView(R.id.actv_service);
        actv_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    showVisibility(R.id.iv_plus);
                } else {
                    noVisibility(R.id.iv_plus);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setOnClickListener(R.id.et_fromDate, R.id.et_toDate, R.id.iv_plus, R.id.btn_save_services, R.id.btn_save_membership);
    }

    private void getDocExperience() {
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID)) {
            GetDoctorFile doctorFile = new GetDoctorFile(getDoctorID(), null);
            HttpParamObject httpParamObject = ApiGenerator.getDoctorFile(doctorFile);
            executeTask(AppConstants.TASKCODES.GET_DOCTOR_EXPERIENCE, httpParamObject);
        }
    }

    private void getAllCity() {
        HttpParamObject httpParamObject = BaseApiGenerator.getHttpToAutoComplete(AutoCompleteApiRequest.getAllCityRequest());
        executeTask(AppConstants.TASKCODES.GET_CITIES, httpParamObject);
    }

    private void getDoctorServiceList() {
        HttpParamObject httpParamObject = BaseApiGenerator.getHttpToAutoComplete(AutoCompleteApiRequest.getAllDoctorServiceRequest());
        executeTask(AppConstants.TASKCODES.GET_DOCTOR_SERVICE, httpParamObject);
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_services;

    }

    private void getDocService() {
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID)) {
            GetClinicBodyItem item = new GetClinicBodyItem(doctorID);
            HttpParamObject httpParamObject = ApiGenerator.getDoctorDetails(item);
            executeTask(AppConstants.TASKCODES.GET_DOCTOR_DETAILS, httpParamObject);
        } else {
            showToast(R.string.error);
        }

    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (response == null) {
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.GET_DOCTOR_SERVICE:
                List<MasterValues> valuesList = (List<MasterValues>) response;
                service_list.addAll(valuesList);
                for (MasterValues values : valuesList) {
                    serviceList.add(values.getLabel());
                }
                setAdapter(serviceList, R.id.actv_service);

                getDocService();
                break;
            case AppConstants.TASKCODES.SET_DOCTOR_SERVICE:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi.isSuccess()) {
                    showToast(R.string.information_save_successfully);
                    startFragment(new ServicesFragment());
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                } else {
                    showToast(R.string.fail);
                }
                break;
            case AppConstants.TASKCODES.GET_CITIES:
                List<MasterValues> masterValuesList = (List<MasterValues>) response;
                city_list.addAll(masterValuesList);
                for (MasterValues values : masterValuesList) {
                    cityList.add(values.getLabel());
                }
                setAdapter(cityList, R.id.actv_city);
                break;
            case AppConstants.TASKCODES.SET_DOCTOR_EXPERIENCE:
                BaseApi baseApi2 = (BaseApi) response;
                if (baseApi2.isSuccess()) {
                    showToast(getString(R.string.information_save_successfully));
                    startFragment(new ServicesFragment());
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                } else {
                    showToast(R.string.fail);
                }
                break;
            case AppConstants.TASKCODES.GET_DOCTOR_EXPERIENCE:
                GetAllDocTableResponse allDocTableResponse = (GetAllDocTableResponse) response;
                List<GetDocExperienceResponse> experienceResponseList = allDocTableResponse.getTable3();
                addExperienceToLayout(experienceResponseList);
                break;
            case AppConstants.TASKCODES.DELETE_DOCTOR_EXPERIENCE:
                BaseApi baseApi1 = (BaseApi) response;
                if (baseApi1.isSuccess()) {
                    showToast(R.string.delete_successfully);
                    startFragment(new ServicesFragment());
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                } else {
                    showToast(R.string.fail);
                }
                break;
            case AppConstants.TASKCODES.GET_DOCTOR_DETAILS:
                GetDoctorDetailsTable detailsTable = (GetDoctorDetailsTable) response;
                List<MasterValues> masterValues = detailsTable.getTable2();
                List<String> serviceList = new ArrayList<>();
                if (masterValues.size() > 0) {
                    for (MasterValues values : masterValues) {
                        serviceList.add(values.getLabel());
                    }
                    addService(serviceList);
                }
                break;

        }
    }

    private void addExperienceToLayout(List<GetDocExperienceResponse> experienceResponseList) {
        if (experienceResponseList.size() == 0) {
            setNoRecord();
        } else {
            try {
                for (int i = 0; i < experienceResponseList.size(); i++) {


                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    final View view = inflater.inflate(R.layout.row_doctor_experience, ll_experience, false);

                    CustomFontTextView fromDate = (CustomFontTextView) view.findViewById(R.id.tv_upload_fromDate);
                    CustomFontTextView toDate = (CustomFontTextView) view.findViewById(R.id.tv_upload_toDate);
                    CustomFontTextView role = (CustomFontTextView) view.findViewById(R.id.tv_upload_role);
                    CustomFontTextView cityName = (CustomFontTextView) view.findViewById(R.id.tv_upload_council_city);
                    CustomFontTextView clinicName = (CustomFontTextView) view.findViewById(R.id.tv_upload_council_clinic_name);

                    GetDocExperienceResponse getDocExperienceResponse = experienceResponseList.get(i);
                    String from = getDocExperienceResponse.getFrom();
                    String to = getDocExperienceResponse.getTo();
                    String doctorRole = getDocExperienceResponse.getDoctorRole();
                    String cityName1 = getDocExperienceResponse.getCityName();
                    String clinicName1 = getDocExperienceResponse.getClinicName();
                    final Integer id = getDocExperienceResponse.getID();
                    RelativeLayout rl_delete = (RelativeLayout) view.findViewById(R.id.rl_delete);
                    rl_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showAlterDialog(id, view, AppConstants.FILE_TYPES.EXPERIENCE, ll_experience);
                        }
                    });

                    fromDate.setText(from);
                    toDate.setText(to);
                    role.setText(doctorRole);
                    cityName.setText(cityName1);
                    clinicName.setText(clinicName1);
                    ll_experience.addView(view);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();

            }
        }
    }

    private void setNoRecord() {
        CustomFontTextView tv_no_record = (CustomFontTextView) findView(R.id.tv_no_record);
        tv_no_record.setVisibility(View.VISIBLE);
        tv_no_record.setGravity(Gravity.CENTER);
        tv_no_record.setText(R.string.no_record_found);
    }

    @Override
    protected void delete(Integer id, View view, String fileType, LinearLayout ll_container) {
        ll_container.removeView(view);
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID)) {
            DeleteDocFileItem docFileItem = new DeleteDocFileItem(doctorID, id.toString(), fileType);
            HttpParamObject httpParamObject = ApiGenerator.deleteDoctorEntry(docFileItem);
            executeTask(AppConstants.TASKCODES.DELETE_DOCTOR_EXPERIENCE, httpParamObject);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.et_fromDate:

                datePickerUtil.showCalendar(getContext(), "", new DatePickerUtil.DatePickerListener() {
                    @Override
                    public void getDate(String date, Calendar calendar) {
                        fromDate = convertDateUPCFormat(calendar);
                        setEditText(R.id.et_fromDate, date);
                    }
                });

                break;
            case R.id.et_toDate:
                datePickerUtil.showCalendar(getContext(), getEditText(R.id.et_fromDate), new DatePickerUtil.DatePickerListener() {
                    @Override
                    public void getDate(String date, Calendar calendar) {
                        toDate = convertDateUPCFormat(calendar);
                        setEditText(R.id.et_toDate, date);
                    }
                });
                break;

            case R.id.iv_plus:
                list_service = addServiceToLayout(serviceList, serviceList_selected);
                break;
            case R.id.btn_save_services:
                saveServices(serviceList_selected);
                break;
            case R.id.btn_save_membership:
                saveMembership();
                break;

        }

    }

    private void saveMembership() {

        if (CollectionUtils.isNotEmpty(getDoctorID()) && CollectionUtils.isNotEmpty(getEditText(R.id.et_role)) && CollectionUtils.isNotEmpty(fromDate) && CollectionUtils.isNotEmpty(toDate) && CollectionUtils.isNotEmpty(getACTVText(R.id.actv_city)) && CollectionUtils.isNotEmpty(getEditText(R.id.et_clinic_name))) {
            String valueID = getValueID(city_list, getACTVText(R.id.actv_city));
            if (CollectionUtils.isNotEmpty(valueID)) {
                SetDoctorExperienceItem experienceItem = new SetDoctorExperienceItem(getDoctorID(), fromDate, toDate, getEditText(R.id.et_role), getEditText(R.id.et_clinic_name), valueID);
                HttpParamObject httpParamObject = ApiGenerator.setExperienceItem(experienceItem);
                executeTask(AppConstants.TASKCODES.SET_DOCTOR_EXPERIENCE, httpParamObject);
            }
        }
    }

    private void saveServices(List<String> list_service) {
        if (list_service!=null) {
            String valueIDs = getValueIDs(list_service, service_list);
            if (CollectionUtils.isNotEmpty(valueIDs) && CollectionUtils.isNotEmpty(getDoctorID())) {
                SetServiceItem serviceItem = new SetServiceItem(getDoctorID(), valueIDs);
                HttpParamObject httpParamObject = ApiGenerator.setDoctorServices(serviceItem);
                executeTask(AppConstants.TASKCODES.SET_DOCTOR_SERVICE, httpParamObject);
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

    private List<String> addServiceToLayout(final List<String> serviceList, final List<String> serviceList_selected) {
        hideKeyboard();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        String actv_service = getACTVText(R.id.actv_service);
        if (CollectionUtils.isNotEmpty(actv_service)) {
            setACTVText(R.id.actv_service, "");
            final View view = inflater.inflate(R.layout.layout_add_textview_row, ll_service_container, false);
            final CustomFontTextView tv_service = (CustomFontTextView) view.findViewById(R.id.tv_title);
            final ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_upload_delete);
            tv_service.setText(actv_service);
            serviceList_selected.add(actv_service);
            ll_service_container.addView(view);
            serviceList.remove(actv_service);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_service_container.removeView(view);
                    String specialization = tv_service.getText().toString();
                    serviceList.add(specialization);
                    serviceList_selected.remove(specialization);

                }
            });

        }
        setAdapter(serviceList, R.id.actv_service);
        return serviceList_selected;
    }

    private void addService(final List<String> servicelist) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (String str : servicelist) {
            final View view = inflater.inflate(R.layout.layout_add_textview_row, ll_service_container, false);
            final CustomFontTextView tv_service = (CustomFontTextView) view.findViewById(R.id.tv_title);
            final ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_upload_delete);
            tv_service.setText(str);
            serviceList_selected.add(str);
            ll_service_container.addView(view);
            serviceList.remove(str);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String service = tv_service.getText().toString();
                    serviceList_selected.remove(service);
                    ll_service_container.removeView(view);
                    serviceList.add(service);
                }
            });
        }
    }


    private String convertDateUPCFormat(Calendar calendar) {
        String myFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(calendar.getTime());
    }

}
