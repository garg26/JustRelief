package com.justrelief.fragments;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.justrelief.R;
import com.justrelief.models.response.GetClinicResponse;
import com.justrelief.models.response.GetClinicResponseTable;
import com.justrelief.models.response.GetPaymentResponse;
import com.tokenautocomplete.TokenCompleteTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import simplifii.framework.activity.IMAGEViewer;
import simplifii.framework.activity.PDFViewer;
import simplifii.framework.asyncmanager.FileParamObject;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.fragments.MediaFragment;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.ContactClinicResponse;
import simplifii.framework.models.DeleteFilesItem;
import simplifii.framework.models.FileUploadUrlItem;
import simplifii.framework.models.GetDocFileItem;
import simplifii.framework.models.GetDocFileTable;
import simplifii.framework.models.GetFileItem;
import simplifii.framework.models.MasterListResponse;
import simplifii.framework.models.response.MasterValues;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.utility.Util;
import simplifii.framework.widgets.ContactsCompletionView;
import simplifii.framework.widgets.CustomFontTextView;


public class ContactClinicDetailsFragment extends AppBaseFragment implements AdapterView.OnItemSelectedListener {

    private MediaFragment mediaFragment;
    private List<String> list_selected_payment;
    private String documentType;
    private List<MasterValues> document_list, payment_list;
    private LinearLayout ll_image_container;
    private List<String> paymentlist = new ArrayList<>();
    private Bundle bundle;


    @Override
    public void initViews() {

         ll_image_container = (LinearLayout) findView(R.id.ll_image_container);
         list_selected_payment = new ArrayList<>();
         bundle = getActivity().getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE);

        ContactsCompletionView completionView = (ContactsCompletionView) findView(R.id.ccv_payment_mode);
        completionView.setTokenListener(new TokenCompleteTextView.TokenListener<String>() {
            @Override
            public void onTokenAdded(String token) {
                list_selected_payment.add(token);
            }

            @Override
            public void onTokenRemoved(String token) {
                list_selected_payment.remove(token);
            }
        });

        GetClinicResponseTable clinicResponseTable = null;

        if (bundle != null) {
            clinicResponseTable = (GetClinicResponseTable) bundle.getSerializable(AppConstants.BUNDLE_KEYS.CLINIC_DETAIL);
        }
        getClinicResponse(clinicResponseTable);
        getPaymentResponse(clinicResponseTable);

        getDocumentFile();
        getPaymentList();

        mediaFragment = new MediaFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(mediaFragment, "Profile image").commit();

        setOnItemSelectedListener(this, R.id.spin_documents_type);




        setOnClickListener(R.id.btn_save_and_next, R.id.tv_clinic_type_image, R.id.tv_document_image, R.id.btn_upload);
    }

    private void getPaymentResponse(GetClinicResponseTable clinicResponseTable) {
        List<GetPaymentResponse> paymentResponse = clinicResponseTable.getTable2();
        String str1 = null;
        if (paymentResponse != null && paymentResponse.size() > 0) {

            for (int i = 0; i < paymentResponse.size(); i++) {
                String label = paymentResponse.get(i).getLabel();
                ContactsCompletionView completionView = (ContactsCompletionView) findView(R.id.ccv_payment_mode);
                completionView.addObject(label);

              //  list_selected_payment.add(label);

//                for (String string : paymentlist){
//                    if (string.equals(label)){
//                        str1 = string;
//                    }
//                }
//                paymentlist.remove(str1);

            }
        }




    }

    private void getClinicResponse(GetClinicResponseTable clinicResponseTable) {

        String clinic_name = null, clinic_email = null, clinic_phone = null, facilityImage = null, overView = null;
        List<GetClinicResponse> clinicResponse = clinicResponseTable.getTable();
        if (clinicResponse != null) {
            for (int i = 0; i < clinicResponse.size(); i++) {
                GetClinicResponse clinicIndex = clinicResponse.get(i);
                clinic_name = clinicIndex.getFacilityName();
                clinic_email = clinicIndex.getFacilityEmailId();
                clinic_phone = clinicIndex.getFacilityPhone();
                facilityImage = clinicIndex.getFacilityImage();
                overView = clinicIndex.getOverView();
            }

        }
        setResponse(clinic_name, clinic_email, clinic_phone, overView, facilityImage);

    }

    private void setResponse(String clinic_name, String clinic_email, String clinic_phone, String overView, String facilityImage) {
        setEditText(R.id.et_contact_number, clinic_phone);
        setEditText(R.id.et_email, clinic_email);
        setEditText(R.id.et_clinic_name, clinic_name);
        setEditText(R.id.et_about_clinic, overView);
        setImageNameToLayout(facilityImage);
    }

    private void getPaymentList() {
        HttpParamObject httpParamObject = BaseApiGenerator.getMasterList();
        executeTask(AppConstants.TASKCODES.MASTER_LIST, httpParamObject);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_and_next:
                upload();
//                if (imageFile != null) {
//                    uploadImage(imageFile);
//                } else {
//
//                    showToast(getString(R.string.clinic_image_empty));
//                }
                break;

            case R.id.tv_document_image:
                askPermissions(AppConstants.MEDIA_TYPES.DOC);
//                if (permission) {
//                    getDocument();
//                } else {
//                    askPermissions();
//                    if (permission) {
//                        getDocument();
//                    }
//                }

                break;

            case R.id.tv_clinic_type_image:
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
                uploadDocument();
                break;

//                if (imageFile != null && docFile == null) {
//                    uploadDocument(imageFile);
//                } else if (docFile != null && imageFile == null) {
//                    uploadDocument(docFile);
//                } else if (imageFile != null && docFile != null) {
//                    String fileExtension = getFileExtension(docFile);
//                    if (fileExtension.equals("pdf")) {
//                        uploadDocument(docFile);
//                    } else {
//                        uploadDocument(imageFile);
//                    }
//                } else {
//                    showToast(R.string.document_file_empty);
//                }

        }
    }


    @Override
    protected void onPermissionVerify(String mediaType) {
        if (mediaType.equals("doc")) {
            getDocument();
        } else {
            getImage();
        }
    }

    private void uploadDocument() {
        File file = null;
        if (getTag(R.id.tv_document_image)!=null){
            file=getTag(R.id.tv_document_image);
        }
        if (file != null) {
            if (file.exists()) {
                String facilityID = getFacilityID();
                String fileExtension = getFileExtension(file);
                if (CollectionUtils.isNotEmpty(facilityID) && CollectionUtils.isNotEmpty(fileExtension)){
                    String docTypeID = getDocTypeID(documentType);
                    if (CollectionUtils.isNotEmpty(docTypeID) && !docTypeID.equalsIgnoreCase("0") ){
                        FileUploadUrlItem urlItem = new FileUploadUrlItem(facilityID, AppConstants.FILE_TYPES.PDF, docTypeID, fileExtension);
                        FileParamObject fileParamObject = BaseApiGenerator.uploadDocument(urlItem, file);
                        executeTask(AppConstants.TASKCODES.UPLOAD_DOCUMENT, fileParamObject);
                    }else{
                        showToast(getString(R.string.error_document_type_empty));
                    }
                }else{
                    showToast(R.string.error);
                }
    //            String docTypeID = getDocTypeID(documentType);
    //            FileUploadUrlItem urlItem = new FileUploadUrlItem(facilityID, AppConstants.FILE_TYPES.PDF, docTypeID, fileExtension);
    //            FileParamObject fileParamObject = BaseApiGenerator.uploadDocument(urlItem, file);
    //            executeTask(AppConstants.TASKCODES.UPLOAD_DOCUMENT, fileParamObject);
            }else{
                showToast(getString(R.string.error_document_empty));
            }
        }else{
            showToast(getString(R.string.error_document_empty));
        }
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
//                if (bitmap != null) {
//                    setImagePath(bitmap, R.id.tv_clinic_type_image);
//                }
                if (MediaType.equals("img")) {
                    if (bitmap != null) {
                        getImagePathFromBitmap(bitmap, R.id.tv_clinic_type_image);
                    }
                }

            }
        }, getActivity());
    }

//    private void uploadImage(File file) {
//        if (file != null) {
//            if (file.exists()) {
//                upload(file);
//            } else {
//                showToast(getString(R.string.error_image_fime_not_exist));
//            }
//        }
//
//    }

    private void getDocument() {
        mediaFragment.getDoc(new MediaFragment.MediaListener() {
            @Override
            public void setUri(Uri uri, String MediaType) {

            }

            @Override
            public void setUri(Uri uri, String MediaType, String path) {

//                if (MediaType.equals(AppConstants.MEDIA_TYPES.DOC)) {
//                    File file = new File(path);
//                    docFile = file;
//
//                    if (file.exists()) {
//                        String name = file.getName();
//                        setText(R.id.tv_document_image, name);
//                    }
//                }
                if (MediaType.equals("doc")) {
                    File doc_path = new File(path);
                    if (doc_path.exists()) {
                        String name = doc_path.getName();
                        setText(R.id.tv_document_image, name);
                        setTag(R.id.tv_document_image, doc_path);
                    }
                }
            }

            @Override
            public void setBitmap(Bitmap bitmap, String MediaType) {
                if (MediaType.equals("img")) {
                    if (bitmap != null) {
                        getImagePathFromBitmap(bitmap, R.id.tv_document_image);
                    }
                }

            }
        }, getActivity());
    }

//    private void setImagePath(Bitmap bitmap, int tv_id) {
//        bitmap = Util.getResizeBitmap(bitmap, 1024);
//
//        if (bitmap != null) {
//            try {
//                imageFile = Util.getFile(bitmap, null);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        assert imageFile != null;
//        if (imageFile.exists()) {
//            String image_name = imageFile.getName();
//            setText(tv_id, image_name);
//
//        }
//
//    }

    private String getDocTypeID(String documentType) {

        int value = 0;
        for (int i = 0; i < document_list.size(); i++) {
            if (document_list.get(i).getLabel().equals(documentType)) {
                value = document_list.get(i).getValue();
            }
        }

        return String.valueOf(value);
    }

    private void upload() {


        File file = null;
        if (getTag(R.id.tv_clinic_type_image)!=null){
            file = getTag(R.id.tv_clinic_type_image);
        }
        if (file != null) {
            if (file.exists()) {
                String facilityID = getFacilityID();
                String fileExtension = getFileExtension(file);

                if (CollectionUtils.isNotEmpty(facilityID) && CollectionUtils.isNotEmpty(fileExtension)) {
                    if (CollectionUtils.isNotEmpty(list_selected_payment)) {
                        String payModeID = getValueIDs(list_selected_payment, payment_list);
                        if (CollectionUtils.isNotEmpty(payModeID)) {
                            if (CollectionUtils.isNotEmpty(getEditText(R.id.et_contact_number))) {
                                if (CollectionUtils.isNotEmpty(getEditText(R.id.et_email))) {
                                    if (CollectionUtils.isNotEmpty(getEditText(R.id.et_about_clinic))) {
                                        ContactClinicResponse clinicResponse = new ContactClinicResponse(facilityID, getEditText(R.id.et_contact_number), getEditText(R.id.et_email), getEditText(R.id.et_about_clinic), payModeID, fileExtension);
                                        FileParamObject fileParamObject = BaseApiGenerator.uploadImage(file, file.getName(), clinicResponse);
                                        executeTask(AppConstants.TASKCODES.UPLOAD_IMAGE, fileParamObject);
                                    }else{
                                        showToast(getString(R.string.error_about_clinic_empty));
                                    }
                                }else{
                                    showToast(getString(R.string.email_address_empty));
                                }
                            }else{
                                showToast(getString(R.string.error_contact_no_empty));
                            }
                        }else{
                            showToast(getString(R.string.error_payment_mode_empty));
                        }
                    }else{
                        showToast(getString(R.string.error_payment_mode_empty));
                    }
                }else{
                    showToast(R.string.error);
                }
            }else{
                showToast(R.string.clinic_image_empty);
            }
        }else{
            showToast(R.string.clinic_image_empty);
        }

//        String fileExtension = getFileExtension(file);
//        String facilityID = getFacilityID();
//
//        if (CollectionUtils.isNotEmpty(facilityID)) {
//            String payModeID = getValueIDs(list_selected_payment, payment_list);
//            if (CollectionUtils.isNotEmpty(getEditText(R.id.et_contact_number)) && CollectionUtils.isNotEmpty(getEditText(R.id.et_email)) && CollectionUtils.isNotEmpty(payModeID)) {
//                ContactClinicResponse clinicResponse = new ContactClinicResponse(facilityID, getEditText(R.id.et_contact_number), getEditText(R.id.et_email), getEditText(R.id.et_about_clinic), payModeID, fileExtension);
//                FileParamObject fileParamObject = BaseApiGenerator.uploadImage(file, file.getName(), clinicResponse);
//                executeTask(AppConstants.TASKCODES.UPLOAD_IMAGE, fileParamObject);
//            }
//        }
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

    private String getStringExtension(String string) {
        String extension = null;
        if (CollectionUtils.isNotEmpty(string)) {
            int i = string.lastIndexOf(".");
            extension = string.substring(i + 1);
        }
        return extension;
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_contact_clinic_details;
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {

        super.onPostExecute(response, taskCode, params);

        if (response == null) {
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.UPLOAD_IMAGE:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi.isSuccess()) {
                    showToast(getString(R.string.information_save_successfully));
                    getActivity().setResult(AppConstants.RESULT_CODE.CLINIC_CONTACT);
                    getActivity().finish();
                } else {
                    showToast(R.string.fail);
                }

                break;

            case AppConstants.TASKCODES.UPLOAD_DOCUMENT:
                BaseApi baseApi1 = (BaseApi) response;
                if (baseApi1.isSuccess()) {
                    showToast(getString(R.string.document_upload_successfully));
                    ll_image_container.removeAllViews();
                    startFragment();
                }
                break;
            case AppConstants.TASKCODES.MASTER_LIST:
                MasterListResponse masterListResponse = (MasterListResponse) response;
                List<MasterValues> paymentList = masterListResponse.getTable();
                List<MasterValues> documentList = masterListResponse.getTable1();

                payment_list = paymentList;
                document_list = documentList;

                List<MasterValues> payment_list = getList(paymentList);
                paymentlist = getLabel(payment_list);
                if (CollectionUtils.isNotEmpty(paymentlist)) {
                    setAdapter(paymentlist);
                }

                List<MasterValues> document_list = getList(documentList);

                List<String> label = getLabel(document_list);
                label.add(0, getString(R.string.select_document_type));
                setadapter(label, R.id.spin_documents_type);



                break;
            case AppConstants.TASKCODES.DOC_FILE:
                GetDocFileTable docTable = (GetDocFileTable) response;
                setDocNameToLayout(docTable);
                break;
            case AppConstants.TASKCODES.DELETE_FILE:
                BaseApi baseApi2 = (BaseApi) response;
                if (baseApi2.isSuccess()) {
                    showToast(getString(R.string.delete_successfully));
                    startFragment();

                } else {
                    showToast(R.string.fail);
                }

                break;

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

    private void startFragment() {
        Fragment fragment = new ContactClinicDetailsFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    private void setDocNameToLayout(GetDocFileTable docTable) {

        List<GetDocFileItem> docFileItems = docTable.getTable();
        if (docFileItems == null) {
            setNoRecord();

        } else {
            for (int i = 0; i < docFileItems.size(); i++) {

                GetDocFileItem getDocFileItem = docFileItems.get(i);
                String docDesc = getDocFileItem.getDocDesc();
                String docPath = getDocFileItem.getDocPath();
                Integer id = getDocFileItem.getID();
                String stringExtension = getStringExtension(docPath);
                if (stringExtension.equals("pdf")) {
                    setImageToLayout(docPath, docDesc, AppConstants.FILE_TYPES.PDF, id.toString());
                }else{
                    setImageToLayout(docPath, docDesc, AppConstants.FILE_TYPES.IMG, id.toString());
                }
            }
        }

    }

    private void setImageToLayout(final String fileURL, String type, final String fileType, final String fileID) {
        final View view = addDocFileToLayout();
        setNoRecordGone();
        findView(R.id.tv_image_name).setVisibility(View.VISIBLE);
        findView(R.id.tv_upload_file).setVisibility(View.VISIBLE);
        CustomFontTextView fileName = (CustomFontTextView) view.findViewById(R.id.tv_upload_image);
        CustomFontTextView tv_title = (CustomFontTextView) view.findViewById(R.id.tv_image_title);

        String name = fileURL.substring(fileURL.lastIndexOf("/") + 1);

        fileName.setSelected(true);
        fileName.setText(name);
        tv_title.setText(type);

        ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_upload_delete);
        if (fileType.equals(AppConstants.FILE_TYPES.IMAGE)) {
            iv_delete.setVisibility(View.GONE);
        }
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEntry(fileID, AppConstants.FILE_TYPES.PDF);
                ll_image_container.removeView(view);
            }
        });
        if (fileType.equals(AppConstants.FILE_TYPES.PDF)) {
            fileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.BUNDLE_KEYS.DOC_FILE_URL, fileURL);
                    startNextActivity(bundle, PDFViewer.class);
                }
            });
        } else {
            fileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.BUNDLE_KEYS.IMG_FILE_URL, fileURL);
                    startNextActivity(bundle, IMAGEViewer.class);
                }
            });
        }
        ll_image_container.addView(view);
    }

    private void setNoRecordGone() {
        CustomFontTextView tv_no_record = (CustomFontTextView) findView(R.id.tv_no_record);
        tv_no_record.setVisibility(View.GONE);
    }

    private void deleteEntry(String fileID, String fileType) {
        String facilityID = getFacilityID();
        if (CollectionUtils.isNotEmpty(facilityID)) {
            DeleteFilesItem filesItem = new DeleteFilesItem(facilityID, fileID, fileType);
            HttpParamObject httpParamObject = BaseApiGenerator.deleteTimeEntry(filesItem);
            executeTask(AppConstants.TASKCODES.DELETE_FILE, httpParamObject);
        }
    }


    private void setNoRecord() {
        CustomFontTextView tv_no_record = (CustomFontTextView) findView(R.id.tv_no_record);
        tv_no_record.setVisibility(View.VISIBLE);
        tv_no_record.setGravity(Gravity.CENTER);
        tv_no_record.setText(R.string.no_record_found);
    }

    private void setImageNameToLayout(String fileURL) {
        if (CollectionUtils.isEmpty(fileURL)) {
            setNoRecord();
        } else {

            setImageToLayout(fileURL, getString(R.string.upload_image), AppConstants.FILE_TYPES.IMAGE, AppConstants.FILE_TYPES.PDF);
        }
    }

    private void callImageViewerActivity(CustomFontTextView fileName, final String docPath) {

    }

    private void callPdfViewerActivity(CustomFontTextView fileName, final String docPath) {

    }

    private View addDocFileToLayout() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        return inflater.inflate(R.layout.row_photos, ll_image_container, false);
    }

    private List<MasterValues> getList(List<MasterValues> paymentList) {
        List<MasterValues> masterValues = new ArrayList<>();
        masterValues.addAll(paymentList);

        return masterValues;

    }

    private List<String> getLabel(List<MasterValues> masterValuesList) {

        List<String> list = new ArrayList<>();

        for (MasterValues masterValues : masterValuesList) {
            list.add(masterValues.getLabel());
        }
        return list;
    }

    private void setAdapter(List<String> list) {
        setContactCompletionView(list, list_selected_payment, R.id.ccv_payment_mode);
    }


    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {

        super.onBackgroundError(re, e, taskCode, params);

        switch (taskCode) {
            case AppConstants.TASKCODES.UPLOAD_IMAGE:
                showToast(getString(R.string.error));
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_documents_type:
                documentType = parent.getItemAtPosition(position).toString();
                break;
        }
    }


    private void getDocumentFile() {
        String facilityID = getFacilityID();
        if (CollectionUtils.isNotEmpty(String.valueOf(facilityID))) {
            GetFileItem fileItem = new GetFileItem((String.valueOf(facilityID)), AppConstants.MEDIA_TYPES.DOC);
            HttpParamObject httpParamObject = BaseApiGenerator.getImageFile(fileItem);
            executeTask(AppConstants.TASKCODES.DOC_FILE, httpParamObject);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
