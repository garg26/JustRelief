package com.justrelief.autoadapters;

import android.app.Dialog;
import android.content.DialogInterface;
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
import com.justrelief.models.ClassObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapterInterface;

/**
 * Created by nbansal2211 on 20/10/16.
 */

public class ClassMultiSelectDialog extends DialogFragment
        implements CustomListAdapterInterface, AdapterView.OnItemClickListener {

    private List<ClassObject> list = new ArrayList<>();
    private List<ClassObject> copyList = new ArrayList<>();
    private CustomAutoAdapter<ClassObject> adapter;
    private HashSet<String> selectedItems = new HashSet<>();
    private String titleString;
    private ListView listView;
    private EditText searchEt;
    private TextView title;
    private ItemSelector selector;

    public static ClassMultiSelectDialog getInstance(String title, List<ClassObject> list,
                                                     ItemSelector selector, List<ClassObject> selectedItems) {
        ClassMultiSelectDialog f = new ClassMultiSelectDialog();
        f.list = list;
        f.copyList.addAll(list);
        f.titleString = title;
        f.selector = selector;
        if (selectedItems != null) {
            for (ClassObject filter : selectedItems) {
                f.selectedItems.add(filter.getDisplayString());
            }
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
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selector != null) {
                    selector.onItemsSelected(getSelectedItems());
                }
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private List<ClassObject> getSelectedItems() {
        List<ClassObject> selctedItems = new ArrayList<>();
        for (ClassObject f : copyList) {
            if (selectedItems.contains(f.getDisplayString())) {
                selctedItems.add(f);
            }
        }
        return selctedItems;
    }

    private void initViews(View v) {
        listView = (ListView) v.findViewById(R.id.lv_select);
        adapter = new CustomAutoAdapter<ClassObject>(getActivity(), R.layout.row_select_item, list, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
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
        Holder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        String item = list.get(position).getDisplayString();
        holder.tv.setText(list.get(position).getDisplayString());
        if (selectedItems.contains(item)) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (selectedItems.contains(this.list.get(i).getDisplayString())) {
            selectedItems.remove(this.list.get(i).getDisplayString());
        } else {
            selectedItems.add(this.list.get(i).getDisplayString());
        }
        adapter.notifyDataSetChanged();
    }


    public class Holder {
        TextView tv;
        ImageView imageView;

        Holder(View view) {
            tv = (TextView) view.findViewById(R.id.tv_item);
            imageView = (ImageView) view.findViewById(R.id.iv_select);
        }
    }

    public void setItems(List<ClassObject> items) {
        this.list.clear();
        this.list.addAll(items);
        this.copyList.clear();
        this.copyList.addAll(items);
        this.adapter.notifyDataSetChanged();
    }

    public static interface ItemSelector {
        public void onItemsSelected(List<ClassObject> selectedItems);
    }
}

