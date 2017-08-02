package com.justreliefdoctors.fragment;

import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.justreliefdoctors.R;
import com.justreliefdoctors.activity.HomeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import simplifii.framework.models.response.DoctorResponse;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;

/**
 * Created by kartikeya-pc on 7/2/17.
 */

public class DoctorProfileFragment extends AppBaseFragment {

    private DoctorResponse doctorResponse;
    private String doctorID;


    public static DoctorProfileFragment getInstance(DoctorResponse doctorResponse) {
        DoctorProfileFragment profileFragment = new DoctorProfileFragment();
        profileFragment.doctorResponse = doctorResponse;
        return profileFragment;
    }

    @Override
    public void initViews() {

        setHasOptionsMenu(true);
        ImageView iv_offline = (ImageView) findView(R.id.iv_offline);
        TextView tv_get_created_date = (TextView) findView(R.id.tv_get_created_date);
        TextView tv_get_doctor_name = (TextView) findView(R.id.tv_get_doctor_name);

        ImageView iv_get_profile_pic = (ImageView) findView(R.id.iv_get_profile_pic);

        String doctorImage = doctorResponse.getDoctorImage();
        if (CollectionUtils.isNotEmpty(doctorImage)) {
            Glide.with(getActivity())
                    .load(doctorImage)
                    .into(iv_get_profile_pic);
        }

        Boolean isPublished = doctorResponse.getIsPublished();
        if (isPublished != null) {
            if (isPublished) {
                iv_offline.setImageResource(R.drawable.online);
            } else {
                iv_offline.setImageResource(R.drawable.offline);
            }
        }
        String createdDate = doctorResponse.getCreatedDate();
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

        String doctorName = doctorResponse.getDoctorName();
        if (CollectionUtils.isNotEmpty(doctorName)) {
            tv_get_doctor_name.setText(doctorName);
        }
        if (CollectionUtils.isNotEmpty(doctorResponse.getDoctorID().toString())) {
            doctorID = doctorResponse.getDoctorID().toString();
        }
        setOnClickListener(R.id.im_popup_icon);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.im_popup_icon) {
            showPopup(v);

        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        popup.getMenu().add("Edit Doctor");
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.BUNDLE_KEYS.DOCTOR_ID, doctorID);
                bundle.putString(AppConstants.BUNDLE_KEYS.AUTH_TOKEN, Preferences.getData(Preferences.KEY_AUTH_TOKEN, null));
                startNextActivityForResult(bundle, HomeActivity.class, AppConstants.REQUEST_CODES.HOME);

                return true;
            }
        });
        popup.show();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public int getViewID() {
        return R.layout.fragment_doctor_profile;
    }
}
