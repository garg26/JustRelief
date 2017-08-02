package com.justrelief.autoadapters;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.justrelief.R;
import com.justrelief.models.BaseAutoFilter;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapterInterface;

/**
 * Created by saurabh on 20-09-2016.
 */

public class AutoCompleteView<T extends BaseAutoFilter> implements CustomListAdapterInterface, AdapterView.OnItemClickListener,
        View.OnFocusChangeListener, TextWatcher {
    private ItemSelector listener;
    private List<T> list = new ArrayList<>();
    private AutoCompleteTextView actv;
    private AppCompatActivity activity;
    private CustomAutoAdapter<T> adapter;
    private T selectedItem;
    private TextChangeListener textChangeListener;

    public T getSelectedItem() {
        return selectedItem;
    }

    public void setText(String text) {
        this.actv.setText(text);
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
        if (selectedItem == null){
            this.actv.setText("");
            return;
        }

        this.selectedItem = selectedItem;
        this.actv.setText(selectedItem.getDisplayString());
    }

    public AutoCompleteView(AppCompatActivity activity, AutoCompleteTextView actv) {
        this(activity, actv, null);
    }

    public AutoCompleteView(AppCompatActivity activity, AutoCompleteTextView actv, ItemSelector listener) {
        this(activity, actv, listener, null);

    }

    public void showDropDown(){
        this.actv.showDropDown();
    }

    public AutoCompleteView(AppCompatActivity activity, AutoCompleteTextView actv, ItemSelector listener, TextChangeListener textChangeListener) {
        this.listener = listener;
        this.actv = actv;
        this.activity = activity;
        adapter = new CustomAutoAdapter<>(activity, R.layout.row_autocomplete, list, this);
        actv.setThreshold(0);
        actv.setAdapter(adapter);
        actv.setOnFocusChangeListener(this);
        this.actv.setOnItemClickListener(this);
        this.actv.addTextChangedListener(this);
        this.textChangeListener = textChangeListener;
        this.actv.setDropDownWidth(activity.getResources().getDisplayMetrics().widthPixels);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        Holder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv.setText(list.get(position).getDisplayString());
        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.selectedItem = list.get(i);
        if (listener != null)
            listener.onItemSelected(selectedItem);
        this.adapter.refreshItems();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            this.actv.showDropDown();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) {
            this.adapter.refreshItems();
        } else if (textChangeListener != null) {
            textChangeListener.onTextChanged(s.toString());
        }
    }


    public class Holder {
        TextView tv;

        Holder(View view) {
            tv = (TextView) view.findViewById(R.id.tv_item);
        }
    }

    public void setItems(List<T> items) {
        this.adapter.appendAllItems(items);
    }

    public static interface ItemSelector {
        public void onItemSelected(BaseAutoFilter item);
    }

    public static interface TextChangeListener {
        public void onTextChanged(String text);
    }
}

