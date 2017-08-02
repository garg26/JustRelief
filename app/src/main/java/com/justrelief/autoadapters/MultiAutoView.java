package com.justrelief.autoadapters;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.justrelief.R;
import com.justrelief.models.BaseAutoFilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;

/**
 * Created by nbansal2211 on 19/09/16.
 */
public class MultiAutoView<T extends BaseAutoFilter> implements CustomListAdapterInterface, AdapterView.OnItemClickListener, View.OnFocusChangeListener, TextWatcher {
    private List<T> list = new ArrayList<>();
    private MultiAutoCompleteTextView mctv;
    private AppCompatActivity activity;
    private CustomAutoAdapter<T> adapter;
    private HashSet<T> selectedList = new HashSet<>();

    public MultiAutoView(AppCompatActivity activity, int multiViewId) {
        this.mctv = (MultiAutoCompleteTextView) activity.findViewById(multiViewId);
        this.activity = activity;
        adapter = new CustomAutoAdapter<>(activity, R.layout.row_autocomplete, list, this);
        mctv.setThreshold(0);
        mctv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        mctv.setAdapter(adapter);
        mctv.setOnFocusChangeListener(this);
        this.mctv.setOnItemClickListener(this);
        this.mctv.addTextChangedListener(this);
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

    public HashSet<T> getSelectedList() {
        return selectedList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (selectedList.contains(this.list.get(i))) {

        } else {
            selectedList.add(this.list.get(i));
        }
        this.adapter.refreshItems();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            this.mctv.showDropDown();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(TextUtils.isEmpty(s.toString())){
            this.adapter.refreshItems();
        }else{

        }
    }

    @Override
    public void afterTextChanged(Editable s) {

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
}
