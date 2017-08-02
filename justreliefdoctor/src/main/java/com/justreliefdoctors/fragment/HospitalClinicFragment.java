package com.justreliefdoctors.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justrelief.activity.CreateClinic;
import com.justreliefdoctors.R;
import com.justreliefdoctors.activity.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomPagerAdapter;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.models.response.ClinicResponse;
import simplifii.framework.models.response.DoctorResponse;
import simplifii.framework.models.response.UserProfileResponse;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;

import static android.app.Activity.RESULT_OK;

public class HospitalClinicFragment extends AppBaseFragment {


    private List<ClinicProfileFragment> clinicProfileFragmentList = new ArrayList<>();
    private List<DoctorProfileFragment> doctorProfileFragmentList = new ArrayList<>();
    private int count;
    private ImageView[] dots;
    private CustomPagerAdapter doctor_pageAdapter;
    private CustomPagerAdapter clinic_pageAdapter;
    private ImageView iv_offline, iv_profile_pic;
    private TextView tv_user_name;


    @Override
    public void initViews() {
        setHasOptionsMenu(true);
        iv_offline = (ImageView) findView(R.id.iv_offline);
        tv_user_name = (TextView) findView(R.id.tv_user_name);
        iv_profile_pic = (ImageView) findView(R.id.iv_profile_pic);

        getUserProfile();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.create_new_clinic, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_create_new_clinic) {

            startNextActivityForResult(null, CreateClinic.class,AppConstants.REQUEST_CODES.CREATE_CLINIC);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case AppConstants.REQUEST_CODES.NEW_APP:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HospitalClinicFragment()).commit();
                break;

        }


    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.btn_create_new_clinic);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.edit_profile, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startNextActivity(HomeActivity.class);
                return true;
            }
        });
        popup.show();
    }


    private void getUserProfile() {
        HttpParamObject httpParamObject = BaseApiGenerator.getUserProfileData();
        executeTask(AppConstants.TASKCODES.GET_USER, httpParamObject);

    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (response == null) {
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.GET_USER:
                UserProfileResponse profileResponse = (UserProfileResponse) response;
                List<ClinicResponse> clinicResponseList = profileResponse.getTable();
                List<DoctorResponse> doctorResponseList = profileResponse.getTable1();
                if (doctorResponseList != null) {
                    DoctorProfileFragment profileFragment = null;
                    for (DoctorResponse doctorResponse : doctorResponseList) {
                        profileFragment = DoctorProfileFragment.getInstance(doctorResponse);

                        Boolean isPublished = doctorResponse.getIsPublished();
                        if(isPublished!=null) {
                            if (isPublished) {
                                iv_offline.setImageResource(R.drawable.online);
                            } else {
                                iv_offline.setImageResource(R.drawable.offline);
                            }
                        }

                        String doctorName = doctorResponse.getDoctorName();
                        if (CollectionUtils.isNotEmpty(doctorName)) {
                            tv_user_name.setText(doctorName);
                        }

                        String doctorImage = doctorResponse.getDoctorImage();
                        if (CollectionUtils.isNotEmpty(doctorImage)) {
                            Picasso.with(getActivity())
                                    .load(doctorImage)
                                    .into(iv_profile_pic);
                        }
                        doctorProfileFragmentList.add(profileFragment);
                    }


                    if (profileFragment != null) {
                        addDoctorToLayout(doctorProfileFragmentList);
                        setUiPageViewController(doctor_pageAdapter, R.id.ll_doctor_indicator, R.id.view_pager_indicator_doctor, R.id.ll_doctor_profile);
                    }
                }
                if (clinicResponseList != null) {
                    ClinicProfileFragment profileFragment = null;
                    for (ClinicResponse clinicResponse : clinicResponseList) {
                        profileFragment = ClinicProfileFragment.getInstance(clinicResponse);
                        clinicProfileFragmentList.add(profileFragment);
                    }
                    if (profileFragment != null) {
                        addClinicToLayout(clinicProfileFragmentList);
                        setUiPageViewController(clinic_pageAdapter, R.id.ll_indicator, R.id.view_pager_indicator, R.id.ll_clinic_profile);
                    }
                }
        }
    }


    private void addDoctorToLayout(final List<DoctorProfileFragment> doctorProfileFragmentList) {
        ViewPager viewPager = (ViewPager) findView(R.id.view_pager);
        doctor_pageAdapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager(), doctorProfileFragmentList, new CustomPagerAdapter.PagerAdapterInterface() {
            @Override
            public Fragment getFragmentItem(int position, Object listItem) {
                return doctorProfileFragmentList.get(position);

            }

            @Override
            public CharSequence getPageTitle(int position, Object listItem) {
                return null;
            }
        });
        viewPager.setAdapter(doctor_pageAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < count; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.not_selected_indicator));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selected_indicator));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addClinicToLayout(final List<ClinicProfileFragment> clinicProfileFragmentList) {

        ViewPager viewPager = (ViewPager) findView(R.id.view_pager1);
        clinic_pageAdapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager(), clinicProfileFragmentList, new CustomPagerAdapter.PagerAdapterInterface() {
            @Override
            public Fragment getFragmentItem(int position, Object listItem) {
                return clinicProfileFragmentList.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position, Object listItem) {
                return null;
            }
        });
        viewPager.setAdapter(clinic_pageAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < count; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.not_selected_indicator));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selected_indicator));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void setUiPageViewController(CustomPagerAdapter pagerAdapter, int ll_id, int rl_id, int id) {
        LinearLayout pager_indicator = (LinearLayout) findView(ll_id);
        count = pagerAdapter.getCount();
        if (count > 1) {
            dots = new ImageView[count];

            for (int i = 0; i < count; i++) {
                dots[i] = new ImageView(getActivity());
                dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.not_selected_indicator));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                params.setMargins(4, 0, 4, 0);

                pager_indicator.addView(dots[i], params);
            }

            dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selected_indicator));
        } else {
            hideVisibility(rl_id);
            LinearLayout linearLayout = (LinearLayout) findView(id);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            float px = Util.convertDpToPixel(126, getActivity());
            layoutParams.height = (int) px;

        }
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_user_profile;
    }


}
