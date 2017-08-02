package com.justreliefdoctors.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.justreliefdoctors.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import simplifii.framework.activity.HomeActivity;
import simplifii.framework.models.response.ClinicResponse;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.utility.Util;

/**
 * Created by kartikeya-pc on 7/1/17.
 */

public class ClinicProfileFragment extends AppBaseFragment {

    private ClinicResponse clinicResponse;
    private String facilityID;
    private PopupMenu popup;

    public static ClinicProfileFragment getInstance(ClinicResponse clinicResponse) {
        ClinicProfileFragment profileFragment = new ClinicProfileFragment();
        profileFragment.clinicResponse = clinicResponse;
        return profileFragment;
    }

    @Override
    public void initViews() {

        popup = new PopupMenu(getActivity(), v);
        ImageView im_clinic_profile = (ImageView) findView(R.id.iv_clinic_profile_pic);
        ImageView iv_offline = (ImageView) findView(R.id.iv_offline);
        String facilityImage = clinicResponse.getFacilityImage();
        if (CollectionUtils.isNotEmpty(facilityImage)) {
            Picasso.with(getActivity())
                    .load(facilityImage)
                    .into(im_clinic_profile);
        }

        TextView tv_clinic_user_name = (TextView) findView(R.id.tv_clinic_user_name);
        TextView tv_get_created_date = (TextView) findView(R.id.tv_get_created_date);
        String facilityName = clinicResponse.getFacilityName();
        if (CollectionUtils.isNotEmpty(facilityName)) {
            tv_clinic_user_name.setText(facilityName);
        }

        if (CollectionUtils.isNotEmpty(clinicResponse.getFacilityID().toString())) {
            facilityID = clinicResponse.getFacilityID().toString();
        }
        String createdDate = clinicResponse.getCreatedDate();
        if (CollectionUtils.isNotEmpty(createdDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
            try {
                Date d = sdf.parse(createdDate);
                String dateToString = Util.convertDateToString(d, "dd-MM-yyyy");
                tv_get_created_date.setText(dateToString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        Boolean isPublished = clinicResponse.getIsPublished();
        if (isPublished!=null) {
            if (isPublished) {
                iv_offline.setImageResource(R.drawable.online);
               // popup.getMenu().add("Add Doctor");
            } else {
                iv_offline.setImageResource(R.drawable.offline);
            }
        }
        setOnClickListener(R.id.im_popup_icon);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_popup_icon:
                showPopup(v);
                break;
        }
    }

    public void showPopup(View v) {

        //MenuInflater inflater = popup.getMenuInflater();
        popup.getMenu().add("Edit Clinic");
        //inflater.inflate(R.menu.edit_profile, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.BUNDLE_KEYS.FACILITYID, facilityID);

                startNextActivityForResult(bundle, com.justrelief.activity.HomeActivity.class,AppConstants.REQUEST_CODES.NEW_APP);

                return true;
            }
        });
        popup.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != getActivity().RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case AppConstants.REQUEST_CODES.NEW_APP:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HospitalClinicFragment()).commit();
                break;
        }

    }

    @Override
    public int getViewID() {
        return R.layout.fragment_clinic_profile;
    }
}
