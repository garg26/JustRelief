package com.justrelief.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.justrelief.R;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.widgets.CustomFontRadio;

/**
 * Created by nbansal2211 on 08/11/16.
 */

public class SelectorDialog {

    private List<String> list;
    private ItemSelector itemSelector;
    private Context context;
    private Dialog dialog;
    private String selectedString;
    private String title;

    public static SelectorDialog getInstance(Context context, String title, List<String> list, ItemSelector itemSelector, String selectedString) {
        SelectorDialog selectorDialog = new SelectorDialog();
        selectorDialog.list = list;
        selectorDialog.itemSelector = itemSelector;
        selectorDialog.context = context;
        selectorDialog.selectedString = selectedString;
        selectorDialog.title = title;
        return selectorDialog;
    }

    public void showDialog() {
        dialog = new Dialog(context);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setTitle(null);

        View view = LayoutInflater.from(context).inflate(R.layout.selector_dialog, null);

        TextView titleTv = (TextView) view.findViewById(R.id.tv_dialog_title);
        titleTv.setText(title);
        RadioGroup grp = (RadioGroup) view.findViewById(R.id.rg_signup);
        for (String s : list) {
            CustomFontRadio row = (CustomFontRadio) LayoutInflater.from(context).inflate(R.layout.row_radio_selector_dialog, null);
            row.setText(s);
            row.setTag(s);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = (String) v.getTag();
                    if (itemSelector != null) {
                        itemSelector.onItemSelected(s);
                    }
                    dialog.dismiss();
                }
            });
            if (s.equalsIgnoreCase(selectedString)) {
                row.setSelected(true);
                row.setChecked(true);
            }
            grp.addView(row);
        }

        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.show();
    }


    public static List<String> getMinutes() {
        List<String> list = new ArrayList<>();
        list.add("00");
        list.add("15");
        list.add("30");
        list.add("45");
        return list;
    }

    public static List<String> getBillingCycles() {
        List<String> list = new ArrayList<>();
        list.add("Custom");
        return list;
    }

    public static List<String> getTuitionTypes() {
        List<String> list = new ArrayList<>();
        list.add("1:1");
//        list.add("Group");
        return list;
    }

    public static interface ItemSelector {
        public void onItemSelected(String item);
    }
}
