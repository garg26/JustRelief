package com.justreliefdoctors.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.justreliefdoctors.R;
import com.justreliefdoctors.models.SetDoctorDetailsItem;
import com.justreliefdoctors.models.response.GetDoctorDetails;
import com.justreliefdoctors.models.response.GetDoctorDetailsTable;
import com.justreliefdoctors.utility.ApiGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import simplifii.framework.activity.IMAGEViewer;
import simplifii.framework.asyncmanager.FileParamObject;
import simplifii.framework.fragments.MediaFragment;
import simplifii.framework.models.BaseApi;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.widgets.CustomFontTextView;

public class ContactDoctorDetailsFragment extends AppBaseFragment implements AdapterView.OnItemSelectedListener {
    private String doctorID;
    private String prefix;
    private List<String> list_prefix = new ArrayList<>();
    private String gender;
    private MediaFragment mediaFragment;
    private LinearLayout ll_image_container;
    private String cityName;
    private RadioButton rb_male, rb_female;

    @Override
    public void initViews() {
        rb_male = (RadioButton) findView(R.id.rb_male);
        rb_female = (RadioButton) findView(R.id.rb_female);
        ll_image_container = (LinearLayout) findView(R.id.ll_container);
        try {
            for (int i = 0; i < getResources().getStringArray(R.array.prefix).length; i++) {
                list_prefix.add(getResources().getStringArray(R.array.prefix)[i]);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        setadapter(list_prefix, R.id.spin_prefix);
        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE);
        if (bundle != null) {
            doctorID = bundle.getString(AppConstants.BUNDLE_KEYS.DOCTOR_ID);
            GetDoctorDetailsTable getDoctorDetailsTableDetails = (GetDoctorDetailsTable) bundle.getSerializable(AppConstants.BUNDLE_KEYS.DOCTOR_DETAILS);
            if (getDoctorDetailsTableDetails != null) {
                setDoctorDetail(getDoctorDetailsTableDetails);
            } else {
                setNoRecord();
            }
        }

        RadioGroup rg_gender = (RadioGroup) findView(R.id.rg_gender);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.rb_male:

                        gender = rb_male.getText().toString();
                        break;
                    case R.id.rb_female:

                        gender = rb_female.getText().toString();
                        break;
                }
            }
        });


        setOnItemSelectedListener(this, R.id.spin_prefix);

        mediaFragment = new MediaFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(mediaFragment, null).commit();

        setOnClickListener(R.id.btn_save_and_next, R.id.tv_image);

    }

    @Override
    protected void onRetryClicked(View view) {
        super.onRetryClicked(view);
    }

    private void setDoctorDetail(GetDoctorDetailsTable doctorDetailsTable) {
        List<GetDoctorDetails> getDoctorDetails = doctorDetailsTable.getTable();
        try {
            if (getDoctorDetails.size() > 0) {
                for (int j = 0; j < getDoctorDetails.size(); j++) {
                    try {
                        GetDoctorDetails index = getDoctorDetails.get(j);

                        if (CollectionUtils.isNotEmpty(index.getDoctorName())) {
                            setEditText(R.id.et_name, index.getDoctorName());
                        }
                        if (CollectionUtils.isNotEmpty(index.getDoctorPhone())) {
                            setEditText(R.id.et_contact_number, index.getDoctorPhone());
                        }
                        if (CollectionUtils.isNotEmpty(index.getDoctorEmail())) {
                            setEditText(R.id.et_email_address, index.getDoctorEmail());
                        }
                        if (CollectionUtils.isNotEmpty(index.getCity())) {
                            String city = index.getCity();
                            int i = city.lastIndexOf(",");
                            cityName = city.substring(0, i);

                            String city_name = city.substring(i + 1);
                            setEditText(R.id.et_city, city_name);
                        }
                        if (CollectionUtils.isNotEmpty(index.getDoctorGender())) {
                            if (index.getDoctorGender().equals("Male")) {
                                rb_male.setChecked(true);
                            } else {
                                rb_female.setChecked(true);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(index.getDoctorExperience())) {
                            setEditText(R.id.et_year_experience, index.getDoctorExperience());
                        }
                        if (CollectionUtils.isNotEmpty(index.getAboutDoctor())) {
                            setEditText(R.id.et_about_me, index.getAboutDoctor());
                        }

                        if (CollectionUtils.isNotEmpty(index.getDoctorTitle())) {
                            setSpinText(R.id.spin_prefix, index.getDoctorTitle(), list_prefix);
//                        Spinner spinner = (Spinner) findView(R.id.spin_prefix);
//                        if (CollectionUtils.isNotEmpty(list_prefix)) {
//                            int position = setadapter.getNameID(index.getDoctorTitle());
//                            spinner.setSelection(position);
//                           // position = list_prefix.indexOf(index.getDoctorTitle());
//                        }


                        }
                        if (CollectionUtils.isNotEmpty(index.getDoctorImageURL())) {
                            addImageToLayout(index.getDoctorImageURL());
                        } else {
                            setNoRecord();
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void addImageToLayout(final String doctorImageURL) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View view = inflater.inflate(R.layout.row_photos, ll_image_container, false);

        CustomFontTextView tv_image_title = (CustomFontTextView) view.findViewById(R.id.tv_image_title);
        CustomFontTextView tv_upload_image = (CustomFontTextView) view.findViewById(R.id.tv_upload_image);
        // RelativeLayout rl_upload_image = (RelativeLayout) view.findViewById(R.id.tv_upload_image);
        ImageView iv_upload_delete = (ImageView) view.findViewById(R.id.iv_upload_delete);
        iv_upload_delete.setVisibility(View.INVISIBLE);

        String imageName = doctorImageURL.substring(doctorImageURL.lastIndexOf("/") + 1);

        tv_upload_image.setText(imageName);
        tv_image_title.setText("Upload Image 1");

        tv_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.BUNDLE_KEYS.IMG_FILE_URL, doctorImageURL);
                startNextActivity(bundle, IMAGEViewer.class);
            }
        });

        ll_image_container.addView(view);

    }

    private void setNoRecord() {
        CustomFontTextView tv_no_record = (CustomFontTextView) findView(R.id.tv_no_record);
        tv_no_record.setVisibility(View.VISIBLE);
        tv_no_record.setGravity(Gravity.CENTER);
        tv_no_record.setText(R.string.no_record_found);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_and_next:
                setDoctorContactDetail();
                break;
            case R.id.tv_image:
                askPermissions();
                break;
        }
    }

    @Override
    protected void onPermissionVerify() {

        mediaFragment.getImage(new MediaFragment.MediaListener() {
            @Override
            public void setUri(Uri uri, String MediaType) {

            }

            @Override
            public void setUri(Uri uri, String MediaType, String path) {

            }

            @Override
            public void setBitmap(Bitmap bitmap, String MediaType) {
                if (bitmap != null) {
                    getImagePathFromBitmap(bitmap, R.id.tv_image);
                }

            }
        }, getActivity());

    }

    private void setDoctorContactDetail() {

        if (rb_male.isChecked()) {
            gender = "Male";
        } else if (rb_female.isChecked()) {
            gender = "Female";
        } else {
            gender = null;
        }
        File file = null;
        if (getTag(R.id.tv_image) != null) {
            file = getTag(R.id.tv_image);
        }
        if (CollectionUtils.isNotEmpty(doctorID)) {
            if (CollectionUtils.isNotEmpty(prefix) && !prefix.equals("Select")) {
                if (CollectionUtils.isNotEmpty(getEditText(R.id.et_name))) {
                    if (CollectionUtils.isNotEmpty(gender)) {
                        if (CollectionUtils.isNotEmpty(cityName)) {
                            if (CollectionUtils.isNotEmpty(getEditText(R.id.et_year_experience))) {
                                if (CollectionUtils.isNotEmpty(getEditText(R.id.et_about_me))) {
                                    if (file != null && file.exists()) {
                                        String fileExtension = getFileExtension(file);
                                        if (CollectionUtils.isNotEmpty(getEditText(R.id.et_contact_number))) {
                                            SetDoctorDetailsItem detailsItem = new SetDoctorDetailsItem(doctorID, getEditText(R.id.et_name), cityName, getEditText(R.id.et_year_experience), getEditText(R.id.et_about_me), getEditText(R.id.et_contact_number), getEditText(R.id.et_email_address), prefix, gender, fileExtension);
                                            FileParamObject fileParamObject = ApiGenerator.setDoctorContactDetails(file, detailsItem);
                                            executeTask(AppConstants.TASKCODES.DOCTOR_CONTACT_DETAILS, fileParamObject);
                                        } else {

                                            showToast(getString(R.string.error_contact_no_empty));

                                        }
                                    } else {
                                        showToast(getString(R.string.error_choose_image_empty));

                                    }
                                } else {
                                    showToast(getString(R.string.error_about_me_empty));
                                }
                            } else {
                                showToast(getString(R.string.error_year_experience_is_empty));
                            }
                        } else {
                            showToast(getString(R.string.error_city_name_empty));
                        }
                    } else {
                        showToast(getString(R.string.error_gender_empty));

                    }

                } else {
                    showToast(R.string.error_doctor_name_empty);
                }

//                if (CollectionUtils.isNotEmpty(gender) && CollectionUtils.isNotEmpty(prefix) && CollectionUtils.isNotEmpty(getEditText(R.id.et_name)) && CollectionUtils.isNotEmpty(cityName) && CollectionUtils.isNotEmpty(getEditText(R.id.et_year_experience)) && CollectionUtils.isNotEmpty(getEditText(R.id.et_about_me)) && CollectionUtils.isNotEmpty(getEditText(R.id.et_email_address)) && CollectionUtils.isNotEmpty(getEditText(R.id.et_contact_number))) {
//                    SetDoctorDetailsItem detailsItem = new SetDoctorDetailsItem(doctorID, getEditText(R.id.et_name), cityName, getEditText(R.id.et_year_experience), getEditText(R.id.et_about_me), getEditText(R.id.et_contact_number), getEditText(R.id.et_email_address), prefix, gender,fileExtension);
//                    FileParamObject fileParamObject = ApiGenerator.setDoctorContactDetails(file, detailsItem);
//                    executeTask(AppConstants.TASKCODES.DOCTOR_CONTACT_DETAILS, fileParamObject);
//                } else {
//                    showToast(getString(R.string.error));
//                }

            } else {
                showToast(getString(R.string.error_prefix_empty));
            }

        } else {
            showToast(getString(R.string.error));
        }
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        switch (taskCode) {
            case AppConstants.TASKCODES.DOCTOR_CONTACT_DETAILS:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi.isSuccess()) {
                    showToast(R.string.information_save_successfully);
                    //startFragment(new ContactDoctorDetailsFragment());
                    getActivity().setResult(AppConstants.REQUEST_CODES.CONTACT);
                    getActivity().finish();
                    // startFragment(new ContactDoctorDetailsFragment());
                } else {
                    showToast(R.string.fail);
                }
                break;
        }
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_contact_doctor_details;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_prefix:
                prefix = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
