package com.justrelief.autoadapters;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.justrelief.R;
import com.justrelief.models.BaseAutoFilter;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapterInterface;

/**
 * Created by nbansal2211 on 31/12/16.
 */

public class SingleChoiceDialog extends DialogFragment implements CustomListAdapterInterface, AdapterView.OnItemClickListener {

    private List<BaseAutoFilter> list = new ArrayList<>();
    private List<BaseAutoFilter> copyList = new ArrayList<>();
    private CustomAutoAdapter<BaseAutoFilter> adapter;
    private String selectedItems;
    private String titleString;
    private ListView listView;
    private EditText searchEt;
    private TextView title;
    private SingleChoiceDialog.ItemSelector selector;

    public static SingleChoiceDialog getInstance(String title, List<BaseAutoFilter> list,
                                                 SingleChoiceDialog.ItemSelector selector, BaseAutoFilter selectedItems) {
        SingleChoiceDialog f = new SingleChoiceDialog();
        f.list.addAll(list);
        f.copyList.addAll(list);
        f.titleString = title;
        f.selector = selector;
        if (selectedItems != null) {
            f.selectedItems = selectedItems.getDisplayString();
        }
        return f;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_multiselect, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        initViews(v);
        return builder.create();
    }

    private BaseAutoFilter getSelectedItems() {
        for (BaseAutoFilter f : copyList) {
            if (selectedItems.contains(f.getDisplayString())) {
                return f;
            }
        }
        return null;
    }

    private void initViews(View v) {
        listView = (ListView) v.findViewById(R.id.lv_select);
        adapter = new CustomAutoAdapter<BaseAutoFilter>(getActivity(), R.layout.row_select_item, list, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        v.findViewById(R.id.iv_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEt.setText("");
            }
        });
        title = (TextView) v.findViewById(R.id.tv_select_title);
        title.setText(titleString);
        searchEt = (EditText) v.findViewById(R.id.et_search);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        SingleChoiceDialog.Holder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, null);
            holder = new SingleChoiceDialog.Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SingleChoiceDialog.Holder) convertView.getTag();
        }
        String item = list.get(position).getDisplayString();
        holder.tv.setText(list.get(position).getDisplayString());
//        if (selectedItems.contains(item)) {
//            holder.imageView.setVisibility(View.VISIBLE);
//        } else {
//            holder.imageView.setVisibility(View.GONE);
//        }
        holder.imageView.setVisibility(View.GONE);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (selector != null) {
            selector.onItemSelected(this.list.get(i));
        }
        dismiss();
    }


    public class Holder {
        TextView tv;
        ImageView imageView;

        Holder(View view) {
            tv = (TextView) view.findViewById(R.id.tv_item);
            imageView = (ImageView) view.findViewById(R.id.iv_select);
        }
    }

    public void setItems(List<BaseAutoFilter> items) {
        this.list.clear();
        this.list.addAll(items);
        this.copyList.clear();
        this.copyList.addAll(items);
        if (adapter != null)
            this.adapter.notifyDataSetChanged();
    }

    public static interface ItemSelector {
        public void onItemSelected(BaseAutoFilter selectedItem);
    }
}

