package com.justreliefdoctors.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import simplifii.framework.utility.CollectionUtils;

/**
 * Created by kartikeya-pc on 6/13/17.
 */

public class DatePickerUtil  {
    public static Calendar myCalendar = Calendar.getInstance();
    public DatePickerDialog.OnDateSetListener date;

    public interface DatePickerListener{
        void getDate(String date,Calendar calendar);
    }
    public void showCalendar(Context context, String fromDate, DatePickerListener datePickerListener) {
        DatePickerDialog.OnDateSetListener onDateSet = setDate(datePickerListener);
        DatePickerDialog dialog = new DatePickerDialog(context, onDateSet, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        if (CollectionUtils.isEmpty(fromDate)) {
            dialog.getDatePicker().setMaxDate(new Date().getTime());
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                Date date = sdf.parse(fromDate);
                long startDate = date.getTime();
                dialog.getDatePicker().setMinDate(startDate);
                dialog.getDatePicker().setMaxDate(new Date().getTime());

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        dialog.show();
    }


    public DatePickerDialog.OnDateSetListener setDate(final DatePickerListener datePickerListener){
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String date = sdf.format(myCalendar.getTime());
                datePickerListener.getDate(date,myCalendar);

            }
        };
        return date;
    }
}
