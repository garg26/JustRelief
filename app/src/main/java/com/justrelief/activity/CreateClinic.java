package com.justrelief.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.justrelief.R;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.models.AutoCompleteApiRequest;
import simplifii.framework.models.response.MasterValues;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.utility.Preferences;


public class CreateClinic extends AppBaseActivity {

    private AutoCompleteTextView actv_city, actv_locality;
    private List<String> cities, localities;
    private int cityID;
    private int localityID;
    private List<MasterValues> masterValues = new ArrayList<>();
    private String constant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_create_clinic);


        actv_city = (AutoCompleteTextView) findViewById(R.id.actv_city);
        actv_locality = (AutoCompleteTextView) findViewById(R.id.actv_locality);
        cities = new ArrayList<>();
        localities = new ArrayList<>();
        getCityList();

        actv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String city_name = actv_city.getText().toString().trim();
                cityID = getLabelID(city_name);
                getLocality(city_name);
            }
        });

        actv_locality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String locality_name = actv_locality.getText().toString().trim();
                Preferences.saveData(Preferences.LOCALITY_KEY, locality_name);
                localityID = getLabelID(locality_name);


            }
        });
        setOnClickListener(R.id.btn_find_my_clinic);
    }

    private int getLabelID(String label) {
        int value = 0;
        for (int i = 0; i < masterValues.size(); i++) {
            if (masterValues.get(i).getLabel().equalsIgnoreCase(label)) {
                value = masterValues.get(i).getValue();
            }
        }
        return value;
    }

    private void refreshLocality() {
        actv_locality.setText("");
        setadapter(localities, actv_locality);
    }

    private void setadapter(List<String> str, AutoCompleteTextView actv) {
        if (CollectionUtils.isNotEmpty(str)) {
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, str);
            actv.setAdapter(adapter);
            actv.setThreshold(1);
        }

    }

    private void getLocality(String city_name) {

        HttpParamObject httpParamObject = BaseApiGenerator.getHttpToAutoComplete(AutoCompleteApiRequest.getAllLocalityRequest(city_name));
        executeTask(AppConstants.TASKCODES.LOCALITY, httpParamObject);
    }

    private void getCityList() {
        HttpParamObject httpParamObject = BaseApiGenerator.getHttpToAutoComplete(AutoCompleteApiRequest.getAllCityRequest());
        executeTask(AppConstants.TASKCODES.CITY, httpParamObject);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_find_my_clinic) {
            findClinic(cityID, localityID);

        }
    }


    private void findClinic(int cityID, int localityID) {
        try {

            if (isValidate(R.id.et_name)) {
                if (CollectionUtils.isNotEmpty(String.valueOf(cityID)) && CollectionUtils.isNotEmpty(String.valueOf(localityID))) {
                    executeTask(AppConstants.TASKCODES.FIND_CLINIC, BaseApiGenerator.findClinic(getEditText(R.id.et_name), String.valueOf(cityID), String.valueOf(localityID)));
                } else {
                    showToast(getString(R.string.error_inf_correctlt));
                }
            } else {
                showToast(getString(R.string.error));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {

        switch (taskCode) {
            case AppConstants.TASKCODES.FIND_CLINIC:
                showToast(getString(R.string.error_not_find_clinic));
                break;
        }
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        switch (taskCode) {

            case AppConstants.TASKCODES.FIND_CLINIC:

                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.BUNDLE_KEYS.CLINIC_NAME, getEditText(R.id.et_name));
                bundle.putString(AppConstants.BUNDLE_KEYS.CITY_NAME, String.valueOf(cityID));
                bundle.putString(AppConstants.BUNDLE_KEYS.LOCALITY_NAME, String.valueOf(localityID));
                bundle.putString(AppConstants.BUNDLE_KEYS.CONSTANT, constant);

                startNextActivityForResult(bundle, NewClinicProfile.class, AppConstants.REQUEST_CODES.NEW_CLINIC);
                break;

            case AppConstants.TASKCODES.CITY:
                List<MasterValues> citiesList = (List<MasterValues>) response;
                masterValues.addAll(citiesList);
                for (MasterValues label : masterValues) {
                    cities.add(label.getLabel());

                }
                setadapter(cities, actv_city);
                break;
            case AppConstants.TASKCODES.LOCALITY:
                localities.clear();
                List<MasterValues> localitiesList = (List<MasterValues>) response;
                masterValues.addAll(localitiesList);
                for (MasterValues label : (List<MasterValues>) response) {
                    localities.add(label.getLabel());
                }
                refreshLocality();

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case AppConstants.REQUEST_CODES.NEW_CLINIC:
                setResult(RESULT_OK);
                finish();
                break;

        }
    }
}
