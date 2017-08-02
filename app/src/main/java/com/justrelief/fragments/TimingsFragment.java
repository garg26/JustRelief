package com.justrelief.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.justrelief.R;
import com.lb.auto_fit_textview.AutoResizeTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.models.BaseApi;
import simplifii.framework.models.DeleteFilesItem;
import simplifii.framework.models.GetFileItem;
import simplifii.framework.models.TimingsItem;
import simplifii.framework.models.TimingsTable;
import simplifii.framework.models.response.TimingsResponse;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.widgets.CustomAutoResizeTextView;

public class TimingsFragment extends AppBaseFragment implements AdapterView.OnItemSelectedListener {

    private List<String> list_selected_days;
    private String start_time1, end_time1, start_time2, end_time2;
    private RelativeLayout relativeLayout;
    private boolean checked;
    private LinearLayout ll_time_container;

    @Override
    public void initViews() {

        list_selected_days = new ArrayList<>();
        final List<String> list_days = new ArrayList<>();

        ll_time_container = (LinearLayout) findView(R.id.ll_time_container);

        relativeLayout = (RelativeLayout) findView(R.id.rl_timings);

        getTimingsList();
        setOnItemSelectedListener(this, R.id.sp_start_time, R.id.sp_end_time, R.id.sp_start_time2, R.id.sp_end_time2);

        for (int i = 0; i < getResources().getStringArray(R.array.days).length; i++) {
            list_days.add(getResources().getStringArray(R.array.days)[i]);
        }

        setContactCompletionView(list_days, list_selected_days, R.id.spin_days);
        setStartTimeSession1(start_time1);
        setOnClickListener(R.id.btn_save_and_next, R.id.cb_clinic_open24);
    }

    private String getFacilityID() {
        String facilityID = null;
        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE);
        if (bundle.size() > 0) {
            facilityID = bundle.getString(AppConstants.BUNDLE_KEYS.FACILITYID);
        }
        return facilityID;
    }

    private void getTimingsList() {
        String facilityID = getFacilityID();
        if (CollectionUtils.isNotEmpty(facilityID)) {
            GetFileItem timingsUrlItem = new GetFileItem(facilityID, AppConstants.TIME);
            HttpParamObject httpParamObject = BaseApiGenerator.getTimeFiles(timingsUrlItem);
            executeTask(AppConstants.TASKCODES.TIMINGS, httpParamObject);
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_save_and_next) {
            setTimings();

        } else if (i == R.id.cb_clinic_open24) {
            checked = ((CheckBox) v).isChecked();
            if (checked) {
                relativeLayout.setVisibility(View.GONE);
            } else {
                relativeLayout.setVisibility(View.VISIBLE);
            }

        }
    }

    private void setEndTimeSession2(String start_time1, String start_time2, String end_time1) {
        Spinner sp_end_time2 = (Spinner) findView(R.id.sp_end_time2);

        if (CollectionUtils.isEmpty(start_time1) || CollectionUtils.isEmpty(start_time2) || CollectionUtils.isEmpty(end_time1) || start_time1.equals("Select Time") || start_time2.equals("Select Time") || end_time1.equals("Select Time")) {
            sp_end_time2.setEnabled(false);
        } else {
            sp_end_time2.setEnabled(true);
            List<String> strings = setTime(start_time2);

            if (strings != null) {
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strings);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_end_time2.setAdapter(dataAdapter);

            }
        }
    }

    private void setStartTimeSession2(String startTime, String endTime) {

        Spinner sp_start_time2 = (Spinner) findView(R.id.sp_start_time2);
        Spinner sp_end_time2 = (Spinner) findView(R.id.sp_end_time2);

        if (CollectionUtils.isEmpty(startTime) || CollectionUtils.isEmpty(endTime) || startTime.equals("Select Time") || endTime.equals("Select Time")) {
            sp_start_time2.setEnabled(false);
            sp_end_time2.setEnabled(false);
        } else {
            sp_start_time2.setEnabled(true);
            List<String> strings = setTime(endTime);
            if (strings != null) {
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strings);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_start_time2.setAdapter(dataAdapter);

            }

        }
    }

    public void setStartTimeSession1(String startTime) {

        Spinner sp_start_time2 = (Spinner) findView(R.id.sp_start_time2);
        Spinner sp_end_time2 = (Spinner) findView(R.id.sp_end_time2);
        Spinner sp_end_time1 = (Spinner) findView(R.id.sp_end_time);
        Spinner sp_start_time1 = (Spinner) findView(R.id.sp_start_time);

        if (CollectionUtils.isEmpty(startTime) || startTime.equals("Select Time")) {
            sp_end_time1.setEnabled(false);
            sp_start_time2.setEnabled(false);
            sp_end_time2.setEnabled(false);
        }

        List<String> strings = setTime(AppConstants.START_TIME1);

        if (strings != null) {
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strings);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_start_time1.setAdapter(dataAdapter);

        }
    }

    private void setEndTimeSession1(String time) {

        Spinner sp_start_time2 = (Spinner) findView(R.id.sp_start_time2);
        Spinner sp_end_time2 = (Spinner) findView(R.id.sp_end_time2);
        Spinner sp_end_time1 = (Spinner) findView(R.id.sp_end_time);

        if (CollectionUtils.isEmpty(time) || time.equals("Select Time")) {
            sp_end_time1.setEnabled(false);
            sp_start_time2.setEnabled(false);
            sp_end_time2.setEnabled(false);
        } else {
            sp_end_time1.setEnabled(true);
            List<String> strings = setTime(time);

            if (strings != null) {
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strings);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_end_time1.setAdapter(dataAdapter);

            }


        }
    }

    private void setTimings() throws NullPointerException {
        String facilityID = getFacilityID();

        if (CollectionUtils.isNotEmpty(facilityID)) {

            if (!checked) {

                if (list_selected_days.size() > 0 && !CollectionUtils.isEmpty(list_selected_days)) {

                    if (CollectionUtils.isNotEmpty(start_time1) && CollectionUtils.isNotEmpty(end_time1) && !start_time1.equals("Select Time") && !end_time1.equals("Select Time")) {
                        if (start_time2.equals(getString(R.string.select_time))) {
                            start_time2 = "";
                            end_time2 = "";
                        } else if (end_time2.equals(getString(R.string.select_time))) {
                            end_time2 = "";
                        }
                        TimingsItem timingsItem = new TimingsItem(facilityID, list_selected_days, start_time1, end_time1, start_time2, end_time2);
                        HttpParamObject httpParamObject = BaseApiGenerator.setTiming(timingsItem);
                        executeTask(AppConstants.TASKCODES.CLINIC_TIMINGS, httpParamObject);
                    } else {
                        showToast(getString(R.string.error_session1_time_empty));
                    }
                } else {
                    showToast(getString(R.string.error_days_empty));
                }
            } else {
                TimingsItem timingsItem = new TimingsItem(facilityID, getResources().getStringArray(R.array.days), AppConstants.START_TIME1, AppConstants.END_TIME1, AppConstants.START_TIME2, AppConstants.END_TIME2);
                HttpParamObject httpParamObject = BaseApiGenerator.setTiming(timingsItem);
                executeTask(AppConstants.TASKCODES.CLINIC_TIMINGS, httpParamObject);
            }
        }
    }


    @Override
    public int getViewID() {
        return R.layout.fragment_timings;
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        switch (taskCode) {
            case AppConstants.TASKCODES.CLINIC_TIMINGS:
                BaseApi baseApi = (BaseApi) response;
                if (baseApi != null && baseApi.isSuccess()) {
                    showToast(R.string.information_save_successfully);
                    getActivity().setResult(AppConstants.RESULT_CODE.CLINIC_CONTACT);
                    getActivity().finish();
                }
                saveTimings();


                break;
            case AppConstants.TASKCODES.TIMINGS:
                TimingsTable timingsTable = (TimingsTable) response;
                if (timingsTable != null) {
                    final List<TimingsResponse> table = timingsTable.getTable();
                    if (table == null || table.isEmpty()) {
                        CustomAutoResizeTextView tv_no_record = (CustomAutoResizeTextView) findView(R.id.tv_no_record);
                        tv_no_record.setVisibility(View.VISIBLE);
                        tv_no_record.setGravity(Gravity.CENTER);
                        tv_no_record.setText(R.string.no_record_found);

                    } else if (table.size()==0){
                        CustomAutoResizeTextView tv_no_record = (CustomAutoResizeTextView) findView(R.id.tv_no_record);
                        tv_no_record.setVisibility(View.VISIBLE);
                        tv_no_record.setGravity(Gravity.CENTER);
                        tv_no_record.setText(R.string.no_record_found);
                    }
                    else {
                        for (int i = 0; i < table.size(); i++) {

                            final View view = addTimeToLayout();
                            final AutoResizeTextView weekDay = (AutoResizeTextView) view.findViewById(R.id.tv_week_day);
                            AutoResizeTextView startTime = (AutoResizeTextView) view.findViewById(R.id.tv_start_time);
                            AutoResizeTextView EndTime = (AutoResizeTextView) view.findViewById(R.id.tv_end_time);
                            AutoResizeTextView startTime2 = (AutoResizeTextView) view.findViewById(R.id.tv_start_time2);
                            AutoResizeTextView EndTime2 = (AutoResizeTextView) view.findViewById(R.id.tv_end_time2);
                            RelativeLayout rl_delete = (RelativeLayout) view.findViewById(R.id.rl_delete);
                            final int finalI = i;
                            rl_delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Integer id = table.get(finalI).getID();
                                    deleteEntry(id.toString());
                                    ll_time_container.removeView(view);
                                }
                            });

                            LinearLayout ll_time_layout = (LinearLayout) view.findViewById(R.id.ll_time_layout);
                            TimingsResponse timingsIndex = table.get(i);


                            weekDay.setText(table.get(i).getWeekDay());
                            startTime.setText(table.get(i).getStartTime());
                            EndTime.setText(table.get(i).getEndTime());

                            if (CollectionUtils.isNotEmpty(table.get(i).getStartTime2())) {
                                startTime2.setVisibility(View.VISIBLE);
                                startTime2.setText(table.get(i).getStartTime2());
                            }
                            if (CollectionUtils.isNotEmpty(table.get(i).getEndTime2())) {
                                EndTime2.setVisibility(View.VISIBLE);
                                EndTime2.setText(table.get(i).getEndTime2());
                            }
                            //setClinicTime(view,timingsIndex.getWeekDay(),timingsIndex.getStartTime(),timingsIndex.getEndTime(),timingsIndex.getStartTime2(),timingsIndex.getEndTime2());


                            if (i % 2 != 0) {
                                ll_time_layout.setBackgroundResource(R.drawable.rectangle_textview_shape);
                            } else {
                                ll_time_layout.setBackgroundColor(Color.TRANSPARENT);
                            }
                            ll_time_container.addView(view);
                        }
                    }

                }

                break;
            case AppConstants.TASKCODES.DELETE_FILE:
                BaseApi baseApi2 = (BaseApi) response;
                if (baseApi2.isSuccess()) {
                    showToast(getString(R.string.delete_successfully));
                }
                startFragment();
                break;

        }
    }

    private void startFragment() {
        Fragment fragment = new TimingsFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }


//    private void setClinicTime(View view,String day, String startTime1, String endTime1, String startTime2, String endTime2) {
//        switch (view.getRootView()){
//            case R.id.tv_week_day:
//                AutoResizeTextView weekDay = (AutoResizeTextView) view.findViewById(R.id.tv_week_day);
//                weekDay.setText(day);
//                break;
//            case R.id.tv_start_time:
//                AutoResizeTextView startTime = (AutoResizeTextView) view.findViewById(R.id.tv_start_time);
//                startTime.setText(startTime1);
//                break;
//            case R.id.tv_end_time:
//                AutoResizeTextView endTime = (AutoResizeTextView) view.findViewById(R.id.tv_end_time);
//                endTime.setText(endTime1);
//                break;
//            case R.id.tv_start_time2:
//                AutoResizeTextView startTime_2 = (AutoResizeTextView) view.findViewById(R.id.tv_start_time2);
//                if (CollectionUtils.isNotEmpty(startTime2)){
//                    startTime_2.setVisibility(View.VISIBLE);
//                    startTime_2.setText(startTime2);
//                }
//                break;
//            case R.id.tv_end_time2:
//                AutoResizeTextView endTime_2 = (AutoResizeTextView) view.findViewById(R.id.tv_end_time2);
//                if (CollectionUtils.isNotEmpty(endTime2)){
//                    endTime_2.setVisibility(View.VISIBLE);
//                    endTime_2.setText(endTime2);
//                }
//
//                break;
//        }
//


    //}

    private void deleteEntry(String id) {
        String facilityID = getFacilityID();
        if (CollectionUtils.isNotEmpty(facilityID)) {
            DeleteFilesItem filesItem = new DeleteFilesItem(facilityID, id, "time");
            HttpParamObject httpParamObject = BaseApiGenerator.deleteTimeEntry(filesItem);
            executeTask(AppConstants.TASKCODES.DELETE_FILE, httpParamObject);
        }
    }

    private View addTimeToLayout() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        return inflater.inflate(R.layout.row_time, null);
    }

    private void saveTimings() {

    }


    private List<String> setTime(String time) {
        List<String> timeList = new ArrayList<>();
        String endTime = AppConstants.END_TIME1;

        timeList.add("Select Time");
        SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
        long milsec = 0;
        long milsec_end = 0;

        try {
            Date parse = format.parse(time);
            Date parse_end = format.parse(endTime);
            if (!time.equals(AppConstants.START_TIME1)) {
                milsec = parse.getTime();
                milsec = milsec + (15 * 60 * 1000);
                //  timeList.add(format.format(milsec));
            } else {
                milsec = parse.getTime();
            }
            milsec_end = parse_end.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (milsec <= milsec_end) {
            timeList.add(format.format(milsec));
            milsec = milsec + (15 * 60 * 1000);
        }
        return timeList;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.sp_start_time) {
            start_time1 = parent.getItemAtPosition(position).toString();
            setEndTimeSession1(start_time1);

        } else if (i == R.id.sp_start_time2) {
            start_time2 = parent.getItemAtPosition(position).toString();
            setEndTimeSession2(start_time1, start_time2, end_time1);

        } else if (i == R.id.sp_end_time) {
            end_time1 = parent.getItemAtPosition(position).toString();
            setStartTimeSession2(start_time1, end_time1);

        } else if (i == R.id.sp_end_time2) {
            end_time2 = parent.getItemAtPosition(position).toString();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
