package com.justrelief.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.justrelief.R;
import com.justrelief.activity.MapViewActivity;
import com.justrelief.models.response.GetClinicResponse;
import com.justrelief.models.response.GetClinicResponseTable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import simplifii.framework.activity.IMAGEViewer;
import simplifii.framework.asyncmanager.FileParamObject;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.fragments.MediaFragment;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.DeleteFilesItem;
import simplifii.framework.models.FileUploadUrlItem;
import simplifii.framework.models.GetDocFileItem;
import simplifii.framework.models.GetDocFileTable;
import simplifii.framework.models.GetFileItem;
import simplifii.framework.models.LocationResponse;
import simplifii.framework.models.MasterListResponse;
import simplifii.framework.models.response.ClinicResponse;
import simplifii.framework.models.response.MasterValues;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.widgets.CustomFontTextView;

import static android.app.Activity.RESULT_OK;


public class LocationPhotosFragment extends AppBaseFragment implements AdapterView.OnItemSelectedListener {

    private MediaFragment mediaFragment;
    private LatLng latLng;
    private List<MasterValues> clinicImage_list;
    private List<String> clinic_image_list;
    private String clinicImageType;
    private LinearLayout ll_image_container;
    private String latitude;
    private String longitude;


    @Override
    public void initViews() {

        ll_image_container = (LinearLayout) findView(R.id.ll_image_container);
        clinicImage_list = new ArrayList<>();
        clinic_image_list = new ArrayList<>();

        GetClinicResponseTable clinicResponseTable = null;
        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE);
        if (bundle != null) {
            clinicResponseTable = (GetClinicResponseTable) bundle.getSerializable(AppConstants.BUNDLE_KEYS.CLINIC_DETAIL);
        }
        getLocationResponse(clinicResponseTable);

        getClinicImageFile();
        getClinicImageList();

        setOnItemSelectedListener(this, R.id.spin_clinic_image);


        mediaFragment = new MediaFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(mediaFragment, "Profile image").commit();
        setOnClickListener(R.id.btn_locate, R.id.btn_save_and_next, R.id.btn_upload, R.id.rl_clinic_image);
    }

    private void getLocationResponse(GetClinicResponseTable clinicResponseTable) {
        List<GetClinicResponse> clinicResponse = clinicResponseTable.getTable();
        String cityName = null, locality = null, address = null,pincode =null;
        if (clinicResponse != null) {
            for (int i = 0; i < clinicResponse.size(); i++) {
                GetClinicResponse locationIndex = clinicResponse.get(i);
                if (CollectionUtils.isNotEmpty(locationIndex.getCity())) {
                    cityName = locationIndex.getCity();
                }
                if (CollectionUtils.isNotEmpty(locationIndex.getLocality())) {
                    locality = locationIndex.getLocality();
                }
                if (CollectionUtils.isNotEmpty(locationIndex.getAddress())) {
                    address = locationIndex.getAddress();
                }
                if (locationIndex.getLatitude()!=null && locationIndex.getLongitude()!=null){
          //      if (CollectionUtils.isNotEmpty(locationIndex.getLatitude().toString()) && CollectionUtils.isNotEmpty(locationIndex.getLongitude().toString())){
                    latitude = locationIndex.getLatitude().toString();
                    longitude = locationIndex.getLongitude().toString();
                }
                if (CollectionUtils.isNotEmpty(locationIndex.getPinCode())){
                    pincode = locationIndex.getPinCode();
                }

            }
        }

        setResponse(cityName, locality,address,pincode);
    }

    private void setResponse(String cityName, String locality, String address, String pincode) {
        setEditText(R.id.et_city, cityName);
        setEditText(R.id.et_area, locality);
        setEditText(R.id.et_country, getString(R.string.india));
        setEditText(R.id.et_street_address,address);
        setEditText(R.id.et_zip_code,pincode);
    }

    private String getFacilityID() {
        String facilityID = null;
        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE);
        if (bundle!=null) {
            facilityID = bundle.getString(AppConstants.BUNDLE_KEYS.FACILITYID);
            return facilityID;
        }

        return null;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_locate:
                getLocation();
                break;

            case R.id.btn_save_and_next:
                setLocation();
                break;

            case R.id.rl_clinic_image:
                askPermissions(AppConstants.MEDIA_TYPES.IMAGE);
//                if (permission) {
//                    getImage();
//                } else {
//                    askPermissions(AppConstants.MEDIA_TYPES.DOC);
//                    if (permission) {
//                        getImage();
//                    }
//                }
                break;
            case R.id.btn_upload:
                uploadImage();
//                if (imageFile != null) {
//                    uploadImage(imageFile);
//                } else {
//
//                    showToast(getString(R.string.clinic_image_empty));
//                }
                break;


        }

    }

    @Override
    protected void onPermissionVerify(String mediaType) {
        getImage();
    }

    private void uploadImage() {
        File file=null;
        if (getTag(R.id.tv_clinic_image)!=null) {
            file = getTag(R.id.tv_clinic_image);
            if (file.exists()) {
                String facilityID = getFacilityID();
                String fileExtension = getFileExtension(file);
                if (CollectionUtils.isNotEmpty(facilityID) && CollectionUtils.isNotEmpty(fileExtension)) {
                    if (CollectionUtils.isNotEmpty(clinicImageType) && !clinicImageType.equalsIgnoreCase(getString(R.string.select_image_type))) {
                        String docTypeID = getImagTypeID(clinicImageType);
                        FileUploadUrlItem urlItem = new FileUploadUrlItem(facilityID, AppConstants.FILE_TYPES.IMAGE, docTypeID, AppConstants.EXTENSIONS);
                        FileParamObject fileParamObject = BaseApiGenerator.uploadDocument(urlItem, file);
                        executeTask(AppConstants.TASKCODES.UPLOAD_IMAGE, fileParamObject);
                    } else {
                        showToast(getString(R.string.image_type_empty));
                    }
                } else {
                    showToast(R.string.error);
                }
            } else {
                showToast(getString(R.string.image_empty));
            }
        }else{
            showToast(getString(R.string.image_empty));
        }

//        String facilityID = getFacilityID();
//        if (imageFile.exists()) {
//            if (CollectionUtils.isNotEmpty(clinicImageType) && !clinicImageType.equalsIgnoreCase(getString(R.string.select_image_type)) && CollectionUtils.isNotEmpty(facilityID.toString())) {
//                String docTypeID = getImagTypeID(clinicImageType);
//                FileUploadUrlItem urlItem = new FileUploadUrlItem(facilityID, AppConstants.FILE_TYPES.IMAGE, docTypeID, AppConstants.EXTENSIONS);
//                FileParamObject fileParamObject = BaseApiGenerator.uploadDocument(urlItem, imageFile);
//                executeTask(AppConstants.TASKCODES.UPLOAD_IMAGE, fileParamObject);
//            } else {
//                showToast("Image Type is empty");
//            }
//        }
    }

    private String getImagTypeID(String clinicImageType) {
        int value = 0;
        for (int i = 0; i < clinicImage_list.size(); i++) {
            if (clinicImage_list.get(i).getLabel().equals(clinicImageType)) {
                value = clinicImage_list.get(i).getValue();
            }
        }
        return String.valueOf(value);
    }

    private String getFileExtension(File file) {
        String extension = null;
        if (file.exists()) {
            String absolutePath = file.getAbsolutePath();
            int i = absolutePath.lastIndexOf(".");
            extension = absolutePath.substring(i + 1);
        }
        return extension;
    }

    private void getImage() {
        mediaFragment.getImage(new MediaFragment.MediaListener() {
            @Override
            public void setUri(Uri uri, String MediaType) {

            }

            @Override
            public void setUri(Uri uri, String MediaType, String path) {

            }

            @Override
            public void setBitmap(Bitmap bitmap, String MediaType) {
                if (MediaType.equals(AppConstants.MEDIA_TYPES.IMAGE)) {
                    if (bitmap != null) {
                        getImagePathFromBitmap(bitmap, R.id.tv_clinic_image);
                    }
                }
//                if (bitmap != null) {
//
//                    bitmap = Util.getResizeBitmap(bitmap, 1024);
//
//                    if (bitmap != null) {
//                        try {
//                            imageFile = Util.getFile(bitmap, null);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    assert imageFile != null;
//                    if (imageFile.exists()) {
//                        String image_name = imageFile.getName();
//                        setText(R.id.tv_clinic_image, image_name);
//
//                    }
//                }
            }
        }, getActivity());
    }

//    private void askPermissions(String doc) {
//        new TedPermission(getActivity())
//                .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .setPermissionListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted() {
//                        permission = true;
//                    }
//
//                    @Override
//                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                        permission = false;
//                        Log.e("Denied", "denied");
//                    }
//                }).check();
//    }

    private void setLocation() {
        try {
            LocationResponse locationResponse = null;
            String facilityID = getFacilityID();
            if (CollectionUtils.isNotEmpty(latitude) && CollectionUtils.isNotEmpty(longitude)) {
                if (CollectionUtils.isNotEmpty(facilityID)) {
                    locationResponse = new LocationResponse(facilityID, getEditText(R.id.et_city), getEditText(R.id.et_area), getEditText(R.id.et_street_address), getEditText(R.id.et_zip_code), Double.valueOf(latitude), Double.valueOf(longitude));
                } else {
                    showToast(R.string.error);
                }
            } else {
                if (CollectionUtils.isNotEmpty(String.valueOf(latLng.latitude)) && CollectionUtils.isNotEmpty(String.valueOf(latLng.longitude)))
                    locationResponse = new LocationResponse(facilityID, getEditText(R.id.et_city), getEditText(R.id.et_area), getEditText(R.id.et_street_address), getEditText(R.id.et_zip_code), latLng.latitude, latLng.longitude);
                else{
                    showToast(R.string.error);
                }
            }
            if (locationResponse != null) {
                HttpParamObject httpParamObject = BaseApiGenerator.setLocation(locationResponse);
                executeTask(AppConstants.TASKCODES.LOCATION, httpParamObject);
            }
            else{
                showToast(R.string.error);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void getLocation() {

        if (CollectionUtils.isEmpty(getEditText(R.id.et_street_address))) {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(getActivity()), AppConstants.PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        } else {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getEditText(R.id.et_street_address)).append(",");
            stringBuilder.append(getEditText(R.id.et_area)).append(",");
            stringBuilder.append(getEditText(R.id.et_landmark)).append(",");
            stringBuilder.append(getEditText(R.id.et_city)).append(",");
            if (CollectionUtils.isEmpty(getEditText(R.id.et_zip_code))) {
                stringBuilder.append(getEditText(R.id.et_country));
            } else {
                stringBuilder.append(getEditText(R.id.et_country)).append(",");
                stringBuilder.append(getEditText(R.id.et_zip_code));
            }

            Bundle bundle = new Bundle();
            if (CollectionUtils.isNotEmpty(latitude) && CollectionUtils.isNotEmpty(longitude)) {
                bundle.putString(AppConstants.BUNDLE_KEYS.LOCATION_LATITUDE, String.valueOf(latitude));
                bundle.putString(AppConstants.BUNDLE_KEYS.LOCATION_LONGITUDE, String.valueOf(longitude));
                startNextActivityForResult(bundle, MapViewActivity.class, AppConstants.REQUEST_CODES.LOCATION);
            }
            else{
                Address locationFromAddress = getLocationFromAddress(stringBuilder.toString());
                if (locationFromAddress != null) {
                    bundle.putString(AppConstants.BUNDLE_KEYS.LOCATION_LATITUDE, String.valueOf(locationFromAddress.getLatitude()));
                    bundle.putString(AppConstants.BUNDLE_KEYS.LOCATION_LONGITUDE, String.valueOf(locationFromAddress.getLongitude()));
                    startNextActivityForResult(bundle, MapViewActivity.class, AppConstants.REQUEST_CODES.LOCATION);
                }

            }
        }
    }

    private Address getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;
        Address location = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            for (int i = 0; i < address.size(); i++) {
                location = address.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    private void getClinicImageList() {
        HttpParamObject httpParamObject = BaseApiGenerator.getMasterList();
        executeTask(AppConstants.TASKCODES.MASTER_LIST, httpParamObject);
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_location_photos;
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        switch (taskCode) {
            case AppConstants.TASKCODES.LOCATION:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi != null && baseApi.isSuccess()) {
                    showToast(getString(R.string.information_save_successfully));
                    getActivity().setResult(AppConstants.RESULT_CODE.CLINIC_CONTACT);
                    getActivity().finish();
                } else {
                    showToast(getString(R.string.fail));
                }


                break;

            case AppConstants.TASKCODES.MASTER_LIST:
                MasterListResponse masterListResponse = (MasterListResponse) response;
                List<MasterValues> clinicImageType = masterListResponse.getTable3();

                clinicImage_list.addAll(clinicImageType);
                clinic_image_list.add(getString(R.string.select_image_type));
                for (MasterValues masterValues : clinicImage_list) {
                    clinic_image_list.add(masterValues.getLabel());
                }
                setadapter(clinic_image_list, R.id.spin_clinic_image);
                break;
            case AppConstants.TASKCODES.UPLOAD_IMAGE:
                BaseApi baseApi1 = (BaseApi) response;
                if (baseApi1.isSuccess()) {
                    showToast(getString(R.string.image_upload_successfully));
                    getActivity().setResult(AppConstants.RESULT_CODE.CLINIC_CONTACT);
                    getActivity().finish();
                }

                break;
            case AppConstants.TASKCODES.IMAGE_FILE:
                GetDocFileTable imgTable = (GetDocFileTable) response;
                if (imgTable != null) {
                    setImageNameToLayout(imgTable, AppConstants.TASKCODES.IMAGE_FILE);
                }
                break;
            case AppConstants.TASKCODES.DELETE_FILE:
                BaseApi baseApi2 = (BaseApi) response;
                if (baseApi2.isSuccess()) {
                    showToast(getString(R.string.delete_successfully));
                    getActivity().setResult(AppConstants.RESULT_CODE.CLINIC_CONTACT);
                    getActivity().finish();
                }

                break;

        }
    }

    private void startFragment() {

        Fragment fragment = new LocationPhotosFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    private void setImageNameToLayout(GetDocFileTable docTable, int fileType) {
        CustomFontTextView tv_no_record = (CustomFontTextView) findView(R.id.tv_no_record);
        final List<GetDocFileItem> table = docTable.getTable();

        if (table.size() == 0) {
            tv_no_record.setVisibility(View.VISIBLE);
            tv_no_record.setGravity(Gravity.CENTER);
            tv_no_record.setText(R.string.no_record_found);
        } else {
            for (int i = 0; i < table.size(); i++) {
                final View view = addImgFileToLayout();

                CustomFontTextView fileName = (CustomFontTextView) view.findViewById(R.id.tv_upload_image);
                CustomFontTextView tv_title = (CustomFontTextView) view.findViewById(R.id.tv_image_title);


                final String docPath = table.get(i).getDocPath();
                String docDesc = table.get(i).getDocDesc();
                final Integer fileID = table.get(i).getID();
                String substring = docPath.substring(docPath.lastIndexOf("/") + 1);
                fileName.setSelected(true);
                fileName.setText(substring);
                tv_title.setText(docDesc);
                fileName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.BUNDLE_KEYS.IMG_FILE_URL, docPath);
                        startNextActivity(bundle, IMAGEViewer.class);
                    }
                });
                ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_upload_delete);
                iv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteEntry(fileID.toString(), AppConstants.FILE_TYPES.IMAGE);
                        ll_image_container.removeView(view);
                    }
                });
               // callImageViewerActivity(fileName, docPath);
                ll_image_container.addView(view);

            }


        }


    }

    private void deleteEntry(String fileID, String fileType) {
        String facilityID = getFacilityID();
        if (CollectionUtils.isNotEmpty(facilityID)) {
            DeleteFilesItem filesItem = new DeleteFilesItem(facilityID, fileID, fileType);
            HttpParamObject httpParamObject = BaseApiGenerator.deleteTimeEntry(filesItem);
            executeTask(AppConstants.TASKCODES.DELETE_FILE, httpParamObject);
        }
    }

//    private void callImageViewerActivity(CustomFontTextView fileName, final String docPath) {
//
//        fileName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString(AppConstants.BUNDLE_KEYS.DOC_FILE_URL, docPath);
//                startNextActivity(bundle, IMAGEViewer.class);
//            }
//        });
//    }

    private View addImgFileToLayout() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        return inflater.inflate(R.layout.row_photos, ll_image_container, false);
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {

        switch (taskCode) {
            case AppConstants.TASKCODES.LOCATION:
                showToast("Error");
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getContext(), data);
                latLng = place.getLatLng();

                getLocationAddress(latLng);
            }

        } else if (resultCode == AppConstants.RESULT_CODE.LOCATION) {
            // data = getActivity().getIntent();
            latitude = data.getStringExtra(AppConstants.BUNDLE_KEYS.LATITUDE);
            longitude = data.getStringExtra(AppConstants.BUNDLE_KEYS.LONGITUDE);
        }
    }


    private void getLocationAddress(LatLng latLng) {

        Geocoder geocoder;
        List<android.location.Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());


        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            Address adder = getAddresses(addresses);

            String address = adder.getAddressLine(0);
            String country = adder.getCountryName();
            String postalCode = adder.getPostalCode();
            String thoroughfare = adder.getThoroughfare();


            setAddress(country, postalCode, thoroughfare, address);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Address getAddresses(List<Address> addresses) {

        return addresses.get(0);
    }

    private void setAddress(String country, String postalCode, String thoroughfare, String address) {

        setEditText(R.id.et_country, country);
        setEditText(R.id.et_zip_code, postalCode);
        setEditText(R.id.et_landmark, thoroughfare);
        setEditText(R.id.et_street_address, address);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_clinic_image:
                clinicImageType = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getClinicImageFile() {
        String facilityID = getFacilityID();
        if (CollectionUtils.isNotEmpty(facilityID)) {
            GetFileItem fileItem = new GetFileItem(facilityID, AppConstants.MEDIA_TYPES.IMAGE);
            HttpParamObject httpParamObject = BaseApiGenerator.getImageFile(fileItem);
            executeTask(AppConstants.TASKCODES.IMAGE_FILE, httpParamObject);
        }
    }

}
