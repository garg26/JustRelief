package com.justrelief.autoadapters;

import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;

import com.justrelief.models.BaseAutoFilter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nbansal2211 on 31/12/16.
 */

public class SingleDiaogManager<T extends BaseAutoFilter> implements SingleChoiceDialog.ItemSelector {
    private AutoCompleteView.ItemSelector listener;
    private List<BaseAutoFilter> list = new ArrayList<>();
    private AutoCompleteTextView actv;
    private AppCompatActivity activity;
    private T selectedItem;
    private AutoCompleteView.TextChangeListener textChangeListener;

    public T getSelectedItem() {
        return selectedItem;
    }

    public void setText(String text) {
        this.actv.setText(text);
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
        if (selectedItem == null) {
            this.actv.setText("");
            return;
        }

        this.selectedItem = selectedItem;
        this.actv.setText(selectedItem.getDisplayString());
    }

    public SingleDiaogManager(AppCompatActivity activity, AutoCompleteTextView actv) {
        this(activity, actv, null);
    }

    public SingleDiaogManager(AppCompatActivity activity, AutoCompleteTextView actv, AutoCompleteView.ItemSelector listener) {
        this(activity, actv, listener, null);
    }

    public void showDropDown() {
        this.actv.showDropDown();
        if (dialog != null) {
            dialog.show(activity.getSupportFragmentManager(), "Dialog");
        }

    }

    private SingleChoiceDialog dialog;

    public SingleDiaogManager(AppCompatActivity activity, AutoCompleteTextView actv, AutoCompleteView.ItemSelector listener, AutoCompleteView.TextChangeListener textChangeListener) {
        this.listener = listener;
        this.actv = actv;
        this.activity = activity;
        this.textChangeListener = textChangeListener;
//        this.actv.setEnabled(false);
        this.actv.setFocusable(false);
        this.actv.setClickable(true);
        dialog = SingleChoiceDialog.getInstance("Select", list, this, null);
    }


//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        this.selectedItem = list.get(i);
//        if (listener != null)
//            listener.onItemSelected(selectedItem);
//        this.adapter.refreshItems();
//    }


    public void setItems(List<T> items) {
        this.list.clear();
        this.list.addAll(items);
        dialog.setItems(this.list);
    }

    @Override
    public void onItemSelected(BaseAutoFilter selectedItem) {
        this.selectedItem = (T) selectedItem;
        if (listener != null)
            listener.onItemSelected(selectedItem);
        this.actv.setText(this.selectedItem.getDisplayString());
    }

//    @Override
//    public void onItemSelected(T selectedItem) {
//        this.selectedItem = selectedItem;
//        if (listener != null)
//            listener.onItemSelected(selectedItem);
//    }

    public static interface ItemSelector {
        public void onItemSelected(BaseAutoFilter item);
    }

    public static interface TextChangeListener {
        public void onTextChanged(String text);
    }
}

