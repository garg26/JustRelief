package com.justreliefdoctors.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.justreliefdoctors.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.models.AutoCompleteApiRequest;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.FindDoctorResponse;
import simplifii.framework.models.response.MasterValues;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.CollectionUtils;


public class CreateDoctor extends AppBaseActivity{

    private String year;
    private List<MasterValues> list_specialization,list_city,list_registration_council;
    private String registration_counil,city_name,specialization_name;
    private String cityID,specializationID,registrationCouncilID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_doctor);

        getSpecialization();
        getCity();
        getRegistartionCouncil();

        String[] year_list = pickYear();
        setAdapter(year_list,R.id.spin_year);

        final AutoCompleteTextView actv_city = (AutoCompleteTextView) findViewById(R.id.actv_city);
        final AutoCompleteTextView actv_specialization = (AutoCompleteTextView) findViewById(R.id.actv_specialization);
        final AutoCompleteTextView actv_registration_council = (AutoCompleteTextView) findViewById(R.id.actv_registration_council);

        actv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city_name = actv_city.getText().toString();
            }
        });
        actv_specialization.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                specialization_name = actv_specialization.getText().toString();
            }
        });
        actv_registration_council.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                registration_counil = actv_registration_council.getText().toString();
            }
        });



       // setOnItemClickListener(this,R.id.actv_city,R.id.actv_registration_council,R.id.actv_specialization);

        Spinner sp_year = (Spinner) findViewById(R.id.spin_year);

        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setOnClickListener(R.id.btn_find_my_doctor);

   }

    private void setAdapter(String[] year_list, int id) {

        Spinner spinner = (Spinner) findViewById(id);
        if (year_list!=null) {
            ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, year_list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }
    }


    private String getIDfrom(String string, List<MasterValues> list) {
        int value = 0;
        for (MasterValues str:list) {
            if (str.getLabel().equals(string)){
                 value = str.getValue();
            }
        }
        return String.valueOf(value);
    }



    private void getCity() {
        HttpParamObject httpParamObject = BaseApiGenerator.getHttpToAutoComplete(AutoCompleteApiRequest.getAllCityRequest());
        executeTask(AppConstants.TASKCODES.CITY, httpParamObject);
    }


    private void getSpecialization() {
        HttpParamObject httpParamObject = BaseApiGenerator.findSpecialization(AutoCompleteApiRequest.getAllSpecializationRequest());
        executeTask(AppConstants.TASKCODES.SPECIALIZATION,httpParamObject);
    }

    private void getRegistartionCouncil() {
        HttpParamObject httpParamObject = BaseApiGenerator.findSpecialization(AutoCompleteApiRequest.getAllRegistrationCouncil());
        executeTask(AppConstants.TASKCODES.REGISTARTION_COUNCIL,httpParamObject);
    }


    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.btn_find_my_doctor) {
            findDoctor();

        }
    }

    private void findDoctor() {

         cityID = getIDfrom(city_name, list_city);
         specializationID = getIDfrom(specialization_name, list_specialization);


        if (CollectionUtils.isNotEmpty(getEditText(R.id.et_doctor_name)) && CollectionUtils.isNotEmpty(getEditText(R.id.et_registration_number))) {
            if(CollectionUtils.isNotEmpty(registration_counil)) {
                if (CollectionUtils.isNotEmpty(specializationID)) {
                    if (CollectionUtils.isNotEmpty(cityID)) {
                        FindDoctorResponse findDoctorResponse = new FindDoctorResponse(getEditText(R.id.et_doctor_name), specializationID, cityID, getEditText(R.id.et_registration_number), registration_counil,year);
                        HttpParamObject httpParamObject = BaseApiGenerator.findDoctor(findDoctorResponse);
                        executeTask(AppConstants.TASKCODES.FIND_DOCTOR, httpParamObject);
                    } else {
                        showToast(getString(R.string.error_city_name));
                    }
                } else {
                    showToast(getString(R.string.error_specilalization_name_empty));
                }
            }else{
                showToast(R.string.error_registration_council_empty);
                }

        } else{
                showToast(getString(R.string.error_check_information));
        }

    }
     @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {

        switch (taskCode){
            case AppConstants.TASKCODES.FIND_DOCTOR:
                showToast(getString(R.string.background_error));
                break;
        }
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

       switch (taskCode){

           case AppConstants.TASKCODES.FIND_DOCTOR:
               BaseApi baseApi = (BaseApi) response;
               if (baseApi!=null && baseApi.isSuccess()){
                   setResult(RESULT_OK);
                   finish();
               }
               else {
                   Bundle bundle = new Bundle();
                   bundle.putString(AppConstants.BUNDLE_KEYS.DOCTOR_NAME, getEditText(R.id.et_doctor_name));
                   bundle.putString(AppConstants.BUNDLE_KEYS.SPECIALIZATION_ID, specializationID);
                   bundle.putString(AppConstants.BUNDLE_KEYS.CITY_ID, cityID);
                   bundle.putString(AppConstants.BUNDLE_KEYS.REGISTRATION_NUMBER, getEditText(R.id.et_registration_number));
                   bundle.putString(AppConstants.BUNDLE_KEYS.REGISTRATION_COUNCIL, registrationCouncilID);
                   bundle.putString(AppConstants.BUNDLE_KEYS.YEAR, year);

                   startNextActivityForResult(bundle, NewDoctorProfile.class, AppConstants.REQUEST_CODES.NEW_CLINIC);
               }
               break;

           case AppConstants.TASKCODES.SPECIALIZATION:

               List<String> specialization = new ArrayList<>();
               if (response!=null){
                   list_specialization = (List<MasterValues>)response;
               }
               for (MasterValues label : (List<MasterValues>)response){
                   specialization.add(label.getLabel());
               }
               setadapter_actv(specialization, R.id.actv_specialization);
               break;
           case AppConstants.TASKCODES.CITY:
               List<String> cities = new ArrayList<>();
               if (response!=null){
                    list_city = (List<MasterValues>)response ;
               }
               for (MasterValues label:(List<MasterValues>)response) {
                   cities.add(label.getLabel());

               }
               setadapter_actv(cities,R.id.actv_city);
               break;
           case AppConstants.TASKCODES.REGISTARTION_COUNCIL:
               List<String> registration_council = new ArrayList<>();
               if (response!=null){
                    list_registration_council = (List<MasterValues>)response;
               }
               for (MasterValues label:(List<MasterValues>)response) {
                   registration_council.add(label.getLabel());

               }
               setadapter_actv(registration_council,R.id.actv_registration_council);
               break;
       }

    }

    private String[] pickYear() {

        Calendar mcurrentYear = Calendar.getInstance();
        int year = mcurrentYear.get(Calendar.YEAR);
         String[] year_list = new String[70];

        for (int i = 0; i < year_list.length; i++) {

            year_list[i] = String.valueOf(year);
            year--;

        }

        return year_list;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode!=RESULT_OK)
            return;

        switch (requestCode){
            case AppConstants.REQUEST_CODES.NEW_DOCTOR:
                setResult(RESULT_OK);
                finish();
                break;


        }
    }


}


