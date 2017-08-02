package com.justreliefdoctors.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.justreliefdoctors.R;
import com.justreliefdoctors.models.DeleteDocFileItem;
import com.justreliefdoctors.models.GetDoctorEducation;
import com.justreliefdoctors.models.GetDoctorFile;
import com.justreliefdoctors.models.SetCouncilItem;
import com.justreliefdoctors.models.SetDoctorDocument;
import com.justreliefdoctors.models.response.GetAllDocTableResponse;
import com.justreliefdoctors.models.response.GetCouncilDetailResponse;
import com.justreliefdoctors.models.response.GetDoctorDocumentResponse;
import com.justreliefdoctors.models.response.GetDocumentType;
import com.justreliefdoctors.utility.ApiGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import simplifii.framework.activity.IMAGEViewer;
import simplifii.framework.activity.PDFViewer;
import simplifii.framework.asyncmanager.FileParamObject;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.fragments.MediaFragment;
import simplifii.framework.models.AutoCompleteApiRequest;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.response.MasterValues;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.widgets.CustomFontTextView;

public class RegistrationFragment extends AppBaseFragment implements AdapterView.OnItemSelectedListener {
    private String documentType, year;
    private MediaFragment mediaFragment;
    private String mediaType;
    private LinearLayout ll_council_container, ll_document_container;
    private List<MasterValues> valuesList = new ArrayList<>();
    private List<String> councilName_list = new ArrayList<>();

    @Override
    public void initViews() {

        getRegistrationCouncil();
        ll_council_container = (LinearLayout) findView(R.id.ll_container);
        ll_document_container = (LinearLayout) findView(R.id.ll_document_container);

        setSpinAdapter(pickYear(), R.id.spin_year);
        setOnItemSelectedListener(this, R.id.spin_document_type, R.id.spin_year);

        mediaFragment = new MediaFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(mediaFragment, null).commit();

        setOnClickListener(R.id.rl_document_image, R.id.btn_registration_save, R.id.tv_document_image, R.id.btn_upload);
    }

    private void getRegistrationCouncil() {
        HttpParamObject httpParamObject = BaseApiGenerator.findSpecialization(AutoCompleteApiRequest.getAllRegistrationCouncil());
        executeTask(AppConstants.TASKCODES.REGISTARTION_COUNCIL, httpParamObject);
    }

    private void getDocumentList() {
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID)) {
            GetDoctorFile doctorFile = new GetDoctorFile(getDoctorID(), null);
            HttpParamObject httpParamObject = ApiGenerator.getDoctorFile(doctorFile);
            executeTask(AppConstants.TASKCODES.GET_DOCTOR_DOCUMENT, httpParamObject);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload:
                upload();
                break;
            case R.id.btn_registration_save:
                saveCouncil();
                break;
            case R.id.tv_document_image:
                askPermissions();
                break;

        }
    }

    private void saveCouncil() {
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID) && CollectionUtils.isNotEmpty(getEditText(R.id.et_council_registration_number)) && CollectionUtils.isNotEmpty(getACTVText(R.id.actv_council_name)) && CollectionUtils.isNotEmpty(year)) {
            SetCouncilItem councilItem = new SetCouncilItem(doctorID, getEditText(R.id.et_council_registration_number), getACTVText(R.id.actv_council_name), year);
            HttpParamObject httpParamObject = ApiGenerator.setCouncil(councilItem);
            executeTask(AppConstants.TASKCODES.SET_DOCTOR_REGISTRATION, httpParamObject);
        } else {
            showToast(R.string.error);
        }
    }

    private void upload() {

        File file = null;
        if (getTag(R.id.tv_document_image) != null) {
            file = getTag(R.id.tv_document_image);
            if (file.exists()) {
                String doctorID = getDoctorID();
                String fileExtension = getFileExtension(file);
                if (CollectionUtils.isNotEmpty(doctorID) && CollectionUtils.isNotEmpty(documentType) && !documentType.equals("Select Document Type")) {
                    SetDoctorDocument doctorDocument = new SetDoctorDocument(doctorID, documentType, fileExtension);
                    FileParamObject fileParamObject = ApiGenerator.setDoctorDocument(doctorDocument, file);
                    executeTask(AppConstants.TASKCODES.UPLOAD_DOCUMENT, fileParamObject);
                } else {
                    showToast("Please Select Document Type");
                }
            } else {
                showToast(getString(R.string.error_document_empty));
            }
        } else {
            showToast(getString(R.string.error_document_empty));
        }

    }

    @Override
    protected void onPermissionVerify() {
        getDocument();
    }

    private void getDocument() {
        mediaFragment.getDoc(new MediaFragment.MediaListener() {
            @Override
            public void setUri(Uri uri, String MediaType) {

            }

            @Override
            public void setUri(Uri uri, String MediaType, String path) {
                mediaType = AppConstants.MEDIA_TYPES.DOC;
                if (MediaType.equals(AppConstants.MEDIA_TYPES.DOC)) {
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
                mediaType = AppConstants.MEDIA_TYPES.IMAGE;
                if (MediaType.equals(AppConstants.MEDIA_TYPES.IMAGE)) {
                    if (bitmap != null) {
                        getImagePathFromBitmap(bitmap, R.id.tv_document_image);
                    }
                }
            }
        }, getActivity());

    }

    @Override
    public int getViewID() {
        return R.layout.fragment_registration_details;
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
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        if (response == null) {
            return;
        }

        switch (taskCode) {
            case AppConstants.TASKCODES.GET_DOCTOR_DOCUMENT:
                GetAllDocTableResponse tableResponse = (GetAllDocTableResponse) response;
                List<GetDocumentType> documentTypeList = new ArrayList<>();
                List<GetDoctorEducation> doctorEducationList = tableResponse.getTable();
                if (doctorEducationList.size() > 0) {

                    for (int i = 0; i < doctorEducationList.size(); i++) {
                        GetDocumentType documentTypes = new GetDocumentType();
                        documentTypes.setQualName(doctorEducationList.get(i).getQualName());
                        documentTypeList.add(documentTypes);
                    }
                }


                List<GetCouncilDetailResponse> detailResponseList = tableResponse.getTable1();
                List<GetDoctorDocumentResponse> documentResponseList = tableResponse.getTable2();

                addCouncilToLayout(detailResponseList);
                addDocumentToLayout(documentResponseList);

                if (detailResponseList.size() > 0) {
                    for (int i = 0; i < detailResponseList.size(); i++) {
                        GetDocumentType documentTypes = new GetDocumentType();
                        String councilName = detailResponseList.get(i).getCouncilName();
                        if (CollectionUtils.isNotEmpty(councilName)) {
                            documentTypes.setCouncilName(councilName);
                        }
                        String councilNumber = detailResponseList.get(i).getCouncilNumber();
                        if (CollectionUtils.isNotEmpty(councilNumber)) {
                            documentTypes.setCouncilNumber(councilNumber);
                        }
                        documentTypeList.add(documentTypes);
                    }

                } else {
                    showToast(getString(R.string.error_document_type_fail));
                }

                setSpinnerAdapter(documentTypeList, R.id.spin_document_type);
                break;

            case AppConstants.TASKCODES.UPLOAD_DOCUMENT:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi.isSuccess()) {
                    showToast(getString(R.string.document_save_successfully));
                    startFragment(new RegistrationFragment());
                     getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                    // getActivity().finish();
                } else {
                    showToast(R.string.error);
                }
                break;
            case AppConstants.TASKCODES.SET_DOCTOR_REGISTRATION:
                BaseApi baseApi1 = (BaseApi) response;
                if (baseApi1.isSuccess()) {
                    showToast(R.string.information_save_successfully);
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                    getActivity().finish();
                } else {
                    showToast(R.string.fail);
                }
                break;
            case AppConstants.TASKCODES.REGISTARTION_COUNCIL:
                valuesList = (List<MasterValues>) response;
                for (MasterValues values : valuesList) {
                    if (values.getLabel() != null) {
                        councilName_list.add(values.getLabel());
                    }
                }
                setAdapter(councilName_list, R.id.actv_council_name);
                getDocumentList();
                break;
            case AppConstants.TASKCODES.DELETE_COUNCIL:
                BaseApi baseApi2 = (BaseApi) response;
                if (baseApi2.isSuccess()) {
                    showToast(R.string.delete_successfully);
                    startFragment(new RegistrationFragment());
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                } else {
                    showToast(R.string.fail);
                }
                break;

        }
    }

    private void setSpinnerAdapter(List<GetDocumentType> documentTypeList, int spin_id) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Select Document Type");
        if (documentTypeList.size() > 0) {
            for (GetDocumentType documentType : documentTypeList) {
                if (CollectionUtils.isNotEmpty(documentType.getQualName())) {
                    arrayList.add(documentType.getQualName());
                }
                if (CollectionUtils.isNotEmpty(documentType.getCouncilName()) && CollectionUtils.isNotEmpty(documentType.getCouncilNumber())) {
                    arrayList.add(documentType.getCouncilName() + "," + documentType.getCouncilNumber());
                }
            }
        }
        setadapter(arrayList, spin_id);
    }


    private void addDocumentToLayout(List<GetDoctorDocumentResponse> documentResponseList) {
        if (documentResponseList==null) {
            setNoRecord(R.id.tv_document_no_record);
        } else {
            try {
                for (int i = 0; i < documentResponseList.size(); i++) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    final View view = inflater.inflate(R.layout.row_photos, ll_document_container, false);
                    CustomFontTextView tv_document_title = (CustomFontTextView) view.findViewById(R.id.tv_image_title);
                    CustomFontTextView iv_upload_document = (CustomFontTextView) view.findViewById(R.id.tv_upload_image);
                    ImageView iv_upload_delete = (ImageView) view.findViewById(R.id.iv_upload_delete);

                    GetDoctorDocumentResponse documentResponse = documentResponseList.get(i);
                    tv_document_title.setTextSize(12);
                    tv_document_title.setText(documentResponse.getDocumentType());

                    final String documentPath = documentResponse.getDocumentPath();
                    String documentName = documentPath.substring(documentPath.lastIndexOf("/") + 1);
                    iv_upload_document.setText(documentName);

                    final Integer id = documentResponse.getID();
                    iv_upload_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showAlterDialog(id, view, AppConstants.FILE_TYPES.DOCTOR_DOCUMENT, ll_document_container);
                        }
                    });

                    iv_upload_document.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString(AppConstants.BUNDLE_KEYS.DOC_FILE_URL, documentPath);
                            int i1 = documentPath.lastIndexOf(".");
                            String substring = documentPath.substring(i1 + 1);
                            if (substring.equals("pdf")) {
                                startNextActivity(bundle, PDFViewer.class);
                            } else {
                                startNextActivity(bundle, IMAGEViewer.class);
                            }


                        }
                    });

                    ll_document_container.addView(view);

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


    private void addCouncilToLayout(List<GetCouncilDetailResponse> detailResponseList) {
        if (detailResponseList.size() == 0) {
            setNoRecord(R.id.tv_council_no_record);
        } else {
            try {
                for (int i = 0; i < detailResponseList.size(); i++) {

                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    final View view = inflater.inflate(R.layout.row_council_detail, ll_council_container, false);
                    LinearLayout ll_upload_council = (LinearLayout) view.findViewById(R.id.ll_upload_council);
                    final GetCouncilDetailResponse getCouncilDetailResponse = detailResponseList.get(i);
                    String councilName = getCouncilDetailResponse.getCouncilName();

                    String councilNumber = getCouncilDetailResponse.getCouncilNumber();
                    String councilYear = String.valueOf(getCouncilDetailResponse.getCouncilYear());
                    if (CollectionUtils.isNotEmpty(councilName) && CollectionUtils.isNotEmpty(councilNumber) && CollectionUtils.isNotEmpty(councilYear)) {

                        CustomFontTextView tv_council_number = (CustomFontTextView) view.findViewById(R.id.tv_upload_council_number);
                        CustomFontTextView tv_council_name = (CustomFontTextView) view.findViewById(R.id.tv_upload_council_name);
                        CustomFontTextView tv_council_year = (CustomFontTextView) view.findViewById(R.id.tv_upload_council_year);

                        if (i % 2 != 0) {
                            ll_upload_council.setBackgroundResource(R.drawable.rectangle_textview_shape);
                        } else {
                            ll_upload_council.setBackgroundColor(Color.TRANSPARENT);
                        }
                        RelativeLayout rl_delete = (RelativeLayout) view.findViewById(R.id.rl_delete);
                        rl_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Integer id = getCouncilDetailResponse.getID();
                                if (CollectionUtils.isNotEmpty(id.toString())) {
                                    showAlterDialog(id, view, AppConstants.FILE_TYPES.DOCTOR_COUNCIL, ll_council_container);
                                }
                            }


                        });
                        if (CollectionUtils.isNotEmpty(councilName) && valuesList != null) {
                            tv_council_name.setText(councilName);
                        }

                        tv_council_number.setText(councilNumber);

                        tv_council_year.setText(councilYear);

                        ll_council_container.addView(view);
                    }

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void delete(Integer id, View view, String fileType, LinearLayout ll_container) {
        ll_container.removeView(view);
        String doctorID = getDoctorID();
        if (CollectionUtils.isNotEmpty(doctorID)) {
            DeleteDocFileItem docFileItem = new DeleteDocFileItem(doctorID, id.toString(), fileType);
            HttpParamObject httpParamObject = ApiGenerator.deleteDoctorEntry(docFileItem);
            executeTask(AppConstants.TASKCODES.DELETE_COUNCIL, httpParamObject);
        }
    }


    private void setNoRecord(int id) {
        CustomFontTextView tv_no_record = (CustomFontTextView) findView(id);
        tv_no_record.setVisibility(View.VISIBLE);
        tv_no_record.setGravity(Gravity.CENTER);
        tv_no_record.setText(R.string.no_record_found);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_document_type:
                documentType = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spin_year:
                year = parent.getItemAtPosition(position).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
