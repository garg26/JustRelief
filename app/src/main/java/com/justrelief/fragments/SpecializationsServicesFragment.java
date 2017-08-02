package com.justrelief.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.justrelief.R;
import com.justrelief.activity.HomeActivity;
import com.justrelief.models.response.GetClinicResponseTable;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.models.AutoCompleteApiRequest;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.ServicesSpecializationResponse;
import simplifii.framework.models.response.MasterValues;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.widgets.CustomFontTextView;


public class SpecializationsServicesFragment extends AppBaseFragment {

    private List<String> services_list, specialization_list, services_selected_list, specialization_selected_list;
    private AutoCompleteTextView actv_services, actv_specialization;
    private LinearLayout service_container, specialization_container;
    private List<String> service_list, Specialization_list;
    private List<MasterValues> list_service, list_specialization;
   // private LinearLayout ll_upload_service_container, ll_upload_specialization_container;
    private List<MasterValues> serviceValues = new ArrayList<>();
    private List<MasterValues> specValues = new ArrayList<>();


    @Override
    public void initViews() {
        actv_services = (AutoCompleteTextView) findView(R.id.actv_service);
        actv_specialization = (AutoCompleteTextView) findView(R.id.actv_specialization);

//        ll_upload_service_container = (LinearLayout) findView(R.id.ll_upload_service_container);
//        ll_upload_specialization_container = (LinearLayout) findView(R.id.ll_upload_specialization_container);

        GetClinicResponseTable clinicResponseTable = null;
        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE);
        if (bundle != null) {
            clinicResponseTable = (GetClinicResponseTable) bundle.getSerializable(AppConstants.BUNDLE_KEYS.CLINIC_DETAIL);
        }

        if (clinicResponseTable != null) {
            serviceValues = clinicResponseTable.getTable3();
            specValues = clinicResponseTable.getTable4();
        }



//        addServiceToLayout(serviceValues);
//        addSpecialization(specValues);

        service_container = (LinearLayout) findView(R.id.ll_service_container);
        specialization_container = (LinearLayout) findView(R.id.ll_specialization_container);

        services_list = new ArrayList<>();
        specialization_list = new ArrayList<>();

        list_service = new ArrayList<>();
        list_specialization = new ArrayList<>();

        services_selected_list = new ArrayList<>();
        specialization_selected_list = new ArrayList<>();

        getServices();
        getSpecialization();

        setOnClickListener(R.id.iv_plus, R.id.iv_plus1, R.id.btn_save_and_next);


    }

    private void setService(List<MasterValues> serviceValues, final List<String> services_list) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        if (serviceValues!=null && serviceValues.size()>0){
            for (MasterValues values : serviceValues){
                final View view = inflater.inflate(R.layout.layout_add_textview_row, null);
                final CustomFontTextView tv_services = (CustomFontTextView) view.findViewById(R.id.tv_title);
                final ImageView iv_negative = (ImageView) view.findViewById(R.id.iv_upload_delete);

                if (CollectionUtils.isNotEmpty(values.getLabel())){
                    tv_services.setText(values.getLabel());
                }
                services_selected_list.add(values.getLabel());

                String str = null;
               for (String string : services_list){
                   if (string.equals(values.getLabel())){
                       str = string;
                   }
               }

                services_list.remove(str);
                iv_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String service = tv_services.getText().toString();
                        services_selected_list.remove(service);
                        service_container.removeView(view);
                        services_list.add(service);

                    }
                });
                service_container.addView(view);

            }
        }
    }
    private void setSpecialization(List<MasterValues> specValues, final List<String> specialization_list) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        if (specValues!=null && specValues.size()>0){
            for (MasterValues values : specValues) {
                final View view = inflater.inflate(R.layout.layout_add_textview_row, specialization_container, false);
                final CustomFontTextView tv_specialization = (CustomFontTextView) view.findViewById(R.id.tv_title);
                final ImageView iv_negative = (ImageView) view.findViewById(R.id.iv_upload_delete);

                if (CollectionUtils.isNotEmpty(values.getLabel())){
                    tv_specialization.setText(values.getLabel());
                }

                String str = null;
                for (String string : specialization_list){
                    if (string.equals(values.getLabel())){
                        str = string;
                    }
                }
                specialization_selected_list.add(str);
                specialization_list.remove(str);
                iv_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String specialization = tv_specialization.getText().toString();
                        specialization_selected_list.remove(specialization);
                        specialization_container.removeView(view);
                        specialization_list.add(specialization);
                    }
                });
                specialization_container.addView(view);
            }
        }
    }


    private String getFacilityID() {
        String facilityID = null;
        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE);
        if (bundle.size() > 0) {
            facilityID = bundle.getString(AppConstants.BUNDLE_KEYS.FACILITYID);
        }
        return facilityID;
    }

//    private void addSpecialization(List<MasterValues> masterValues1) {
//        if (masterValues1 == null) {
//            setNoRecord();
//            noVisibility(R.id.tv_title, R.id.tv_specializations);
//        } else {
//            for (MasterValues label : masterValues1) {
//                String string = label.getLabel();
//                addViewToLayout1(string);
//            }
//        }
//    }

//    private void addViewToLayout1(String string) {
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View view = inflater.inflate(R.layout.row_services_specialization, ll_upload_specialization_container, false);
//        setTextToLayout1(view, string);
//    }
//
//    private void setTextToLayout1(final View view, String string) {
//        CustomFontTextView tv_title = (CustomFontTextView) view.findViewById(R.id.tv_award_title);
//        tv_title.setText(string);
//
//
//        ll_upload_specialization_container.addView(view);
//    }

//    private void addServiceToLayout(List<MasterValues> masterValues) {
//
//        if (masterValues == null) {
//            setNoRecord();
//            noVisibility(R.id.tv_title, R.id.tv_specializations);
//        } else {
//            for (MasterValues label : masterValues) {
//                String services = label.getLabel();
//
//                LayoutInflater inflater = LayoutInflater.from(getActivity());
//                final View view = inflater.inflate(R.layout.layout_add_textview_row, null);
//                final CustomFontTextView tv_services = (CustomFontTextView) view.findViewById(R.id.tv_title);
//                final ImageView iv_negative = (ImageView) view.findViewById(R.id.iv_upload_delete);
//                tv_services.setText(services);
//
//                services_selected_list.add(services);
//                service_container.addView(view);
//                services_list.remove(services);
//                iv_negative.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        service_container.removeView(view);
//                        String services = tv_services.getText().toString();
//                        services_list.add(services);
//                        services_selected_list.remove(services);
//
//                    }
//                });
//                setAdapter(services_list, R.id.actv_service);
//
//            }
//        }

//            LayoutInflater inflater = LayoutInflater.from(getActivity());
//            final View view = inflater.inflate(R.layout.row_services_specialization, ll_upload_service_container, false);
//            CustomFontTextView tv_title = (CustomFontTextView) view.findViewById(R.id.tv_award_title);
//            tv_title.setText(string);

//            ll_upload_service_container.addView(view);
            //addViewToLayout(string);



//}

//    private void addViewToLayout(String label) {
//
//
//    }


//    private void setNoRecord() {
//        CustomFontTextView tv_no_record = (CustomFontTextView) findView(R.id.tv_no_record);
//        tv_no_record.setVisibility(View.VISIBLE);
//        tv_no_record.setGravity(Gravity.CENTER);
//        tv_no_record.setText(R.string.no_record_found);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_plus:
                service_list = addServiceToLayout(services_selected_list, services_list);

            case R.id.iv_plus1:
                Specialization_list = addSpecializationToLayout(specialization_selected_list, specialization_list);
                break;
            case R.id.btn_save_and_next:
                saveServices(services_selected_list,specialization_selected_list );

        }
    }

    private List<String> addSpecializationToLayout(final List<String> specialization_selected_list, final List<String> specializationList) {
        hideKeyboard();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        String services = actv_specialization.getText().toString().trim();
        if (CollectionUtils.isNotEmpty(services)) {
            actv_specialization.setText("");
            final View view = inflater.inflate(R.layout.layout_add_textview_row, null);
            final CustomFontTextView tv_specialization = (CustomFontTextView) view.findViewById(R.id.tv_title);
            final ImageView iv_negative = (ImageView) view.findViewById(R.id.iv_upload_delete);
            tv_specialization.setText(services);
            specialization_selected_list.add(services);
            specialization_container.addView(view);
            specializationList.remove(services);
            iv_negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    specialization_container.removeView(view);
                    String specialization = tv_specialization.getText().toString();
                    specializationList.add(specialization);
                    specialization_selected_list.remove(specialization);

                }
            });
            setAdapter(specializationList, R.id.actv_specialization);


        }
        return specialization_selected_list;
    }

    private List<String> addServiceToLayout(final List<String> services_selected_list, final List<String> servicesList) {
        hideKeyboard();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        String services = getACTVText(R.id.actv_service);

        if (CollectionUtils.isNotEmpty(services)) {
            actv_services.setText("");
            final View view = inflater.inflate(R.layout.layout_add_textview_row, null);
            final CustomFontTextView tv_services = (CustomFontTextView) view.findViewById(R.id.tv_title);
            final ImageView iv_negative = (ImageView) view.findViewById(R.id.iv_upload_delete);
            tv_services.setText(services);

            services_selected_list.add(services);
            service_container.addView(view);
            servicesList.remove(services);
            iv_negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    service_container.removeView(view);
                    String services = tv_services.getText().toString();
                    servicesList.add(services);
                    services_selected_list.remove(services);

                }
            });
            setAdapter(servicesList, R.id.actv_service);

        }
        return services_selected_list;

    }

    private void saveServices(List<String> service_list, List<String> specialization_list) {

        String facilityID = getFacilityID();
        String servicesID = getValueIDs(service_list, list_service);
        String specializationID = getValueIDs(specialization_list, list_specialization);
        try {
            if (CollectionUtils.isNotEmpty(servicesID)) {
                if (CollectionUtils.isNotEmpty(specializationID)) {

                    ServicesSpecializationResponse servicesSpecializationResponse = new ServicesSpecializationResponse(facilityID, servicesID, specializationID);
                    HttpParamObject httpParamObject = BaseApiGenerator.addServices(servicesSpecializationResponse);
                    executeTask(AppConstants.TASKCODES.SERVICES_SPECIALIZATION, httpParamObject);

                } else {
                    showToast(getString(R.string.error_specialization_empty));
                }
            } else {
                showToast(getString(R.string.error_services_empty));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void getServices() {
        HttpParamObject httpParamObject = BaseApiGenerator.getHttpToAutoComplete(AutoCompleteApiRequest.getAllServicesRequest());
        executeTask(AppConstants.TASKCODES.SERVICES, httpParamObject);
    }

    private void getSpecialization() {
        HttpParamObject httpParamObject = BaseApiGenerator.getHttpToAutoComplete(AutoCompleteApiRequest.getAllSpecializationRequest());
        executeTask(AppConstants.TASKCODES.SPECIALIZATION, httpParamObject);
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_specializations_services;
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        switch (taskCode) {
            case AppConstants.TASKCODES.SERVICES:
                List<MasterValues> serviceList = (List<MasterValues>) response;
                list_service.addAll(serviceList);

                for (MasterValues label : (List<MasterValues>) response) {
                    services_list.add(label.getLabel());
                }
                setAdapter(services_list, R.id.actv_service);
                setService(serviceValues,services_list);
                break;
            case AppConstants.TASKCODES.SPECIALIZATION:
                List<MasterValues> specializationList = (List<MasterValues>) response;
                list_specialization.addAll(specializationList);
                for (MasterValues label : (List<MasterValues>) response) {
                    specialization_list.add(label.getLabel());
                }
                setAdapter(specialization_list, R.id.actv_specialization);
                setSpecialization(specValues,specialization_list);
                break;

            case AppConstants.TASKCODES.SERVICES_SPECIALIZATION:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi != null && baseApi.isSuccess()) {
                    showToast(getString(R.string.information_save_successfully));
                    getActivity().setResult(AppConstants.RESULT_CODE.CLINIC_CONTACT);
                    getActivity().finish();

                } else {
                    showToast(getString(R.string.fail));
                }

                getActivity().finish();
                break;


        }
    }


    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {

        switch (taskCode) {
            case AppConstants.TASKCODES.SERVICES_SPECIALIZATION:
                showToast(getString(R.string.error));
                break;
        }
    }


}
