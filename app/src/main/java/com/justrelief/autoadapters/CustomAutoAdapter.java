package com.justrelief.autoadapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.justrelief.models.BaseAutoFilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapterInterface;

/**
 * Created by nbansal2211 on 17/09/16.
 */
public class CustomAutoAdapter<T extends BaseAutoFilter> extends ArrayAdapter implements View.OnClickListener {
    private LayoutInflater layoutInflater;
    List<T> spinnerList, listCopy;

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((BaseAutoFilter) resultValue).getDisplayString();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                List<T> suggestions = new ArrayList<T>();
                for (T customer : listCopy) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (customer.isMatchingConstraint(constraint.toString())) {
                        suggestions.add(customer);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<T> filteredList = (List<T>) results.values;
            List<T> customerList = new ArrayList<T>();
            if (results != null && results.count > 0) {
                clear();
                for (T c : filteredList) {
                    customerList.add(c);
                }
                Iterator<T> customerIterator = customerList.iterator();
                while (customerIterator.hasNext()) {
                    T data = customerIterator.next();
                    add(data);
                }
                notifyDataSetChanged();
            }else{
                clear();
                notifyDataSetChanged();
            }
        }
    };
    int rowlayoutID;
    LayoutInflater inflater;
    CustomListAdapterInterface ref;

    public CustomAutoAdapter(Context context, int resource, List<T> customers, CustomListAdapterInterface ref) {
        super(context, resource, customers);
        inflater = LayoutInflater.from(context);
        this.spinnerList = customers;
        this.listCopy = new ArrayList<>(spinnerList.size());
        for(T f : spinnerList){
            this.listCopy.add(f);
        }
//        this.listCopy.addAll(this.spinnerList);
        this.ref = ref;
        this.rowlayoutID = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = ref.getView(position, convertView, parent, rowlayoutID, inflater);
        return v;
    }

    public void appendAllItems(List<T> items){
        this.listCopy.clear();
        this.clear();
        this.addAll(items);
        this.listCopy.addAll(items);
    }

//    public void refreshItems(){
//        this.clear();
//        this.addAll(this.listCopy);
//    }


    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Log.d("AutoFilter", "Position"+position);
    }

    public void refreshItems(){
        this.spinnerList.clear();
        this.spinnerList.addAll(listCopy);
        this.notifyDataSetChanged();
    }

    public void addAllItems(List<T> list){
        this.spinnerList.clear();
        this.listCopy.clear();
        this.spinnerList.addAll(list);
        this.listCopy.addAll(list);
        this.notifyDataSetChanged();
    }

}