package com.justrelief.autoadapters;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.MultiAutoCompleteTextView;

import com.justrelief.models.BaseAutoFilter;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapterInterface;

/**
 * Created by saurabh on 24-09-2016.
 */
public class MultiDialogFragment<T extends BaseAutoFilter> extends DialogFragment implements CustomListAdapterInterface, AdapterView.OnItemClickListener, View.OnFocusChangeListener, TextWatcher {
    private List<T> list = new ArrayList<>();
    private MultiAutoCompleteTextView mctv;
    private AppCompatActivity activity;
    private CustomAutoAdapter<T> adapter;

    public MultiDialogFragment() {

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        return null;
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
