package com.justrelief.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.justrelief.R;
import com.justrelief.models.SpinnerItem;
import java.util.List;
import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.widgets.CustomFontButton;

public class MultiSelectChipView extends DialogFragment implements CustomListAdapterInterface{

    private Context context;
    private List<SpinnerItem> list;
    private List<String> list_selected;
    private OnChipSelectListener selectListener;

    public MultiSelectChipView(Context context, List<SpinnerItem> list, List<String> list1, OnChipSelectListener selectListener){
        this.context = context;
        this.list = list;
        this.selectListener=selectListener;
        this.list_selected = list1;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog, new LinearLayout(getActivity()), false);
        ListView listView = (ListView) view.findViewById(R.id.list_item);



        CustomListAdapter<SpinnerItem> customListAdapter  = new CustomListAdapter<>(context,R.layout.spinner_layout,list,this);

        listView.setAdapter(customListAdapter);
         final Dialog builder = new Dialog(getActivity());

        builder.setTitle("Select Payment mode");
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));
        builder.setContentView(view);

        CustomFontButton fontButton = (CustomFontButton) view.findViewById(R.id.bt_save);
        fontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.onSelect(list_selected);
                dismiss();
            }
        });


        return builder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {




        final Holder holder;
        if(convertView==null){
            convertView = inflater.inflate(resourceID,parent,false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }
        else{
            holder = (Holder) convertView.getTag();
        }

        final SpinnerItem spinnerItem = list.get(position);
        if (position==0) {
            holder.checkBox.setVisibility(View.GONE);
            holder.tv_title.setText(spinnerItem.getTitle());
        }else{
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.tv_title.setText(spinnerItem.getTitle());
            holder.checkBox.setChecked(spinnerItem.isSelected());
        }

        holder.checkBox.setTag(position);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    holder.checkBox.setChecked(true);
                }
                else{
                    holder.checkBox.setChecked(false);
                }
            }
        });


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CheckBox checkBox = (CheckBox) buttonView;
                if (checkBox.isChecked()){
                       list_selected.add(spinnerItem.getTitle());
                }
            }
        });





        return convertView;

    }
    class Holder {
        TextView tv_title;
        CheckBox checkBox;

        Holder(View view) {

            tv_title = (TextView) view.findViewById(R.id.text);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);


        }
    }
   public interface OnChipSelectListener{
        void onSelect(List<String> list);

    }
}
