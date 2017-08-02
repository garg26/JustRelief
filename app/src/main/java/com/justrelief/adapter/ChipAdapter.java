package com.justrelief.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.justrelief.R;
import com.justrelief.Tag;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipViewAdapter;

import java.util.List;

import simplifii.framework.widgets.CustomFontTextView;

public class ChipAdapter extends ChipViewAdapter {

    private List<Chip> list;

    public ChipAdapter(Context context, List<Chip> list) {
        super(context);

        this.list=list;
    }

    @Override
    public int getLayoutRes(int position) {
        return R.layout.view_chip;
    }

    @Override
    public int getBackgroundRes(int position) {
        return R.drawable.chip_label_selected;
    }

    @Override
    public int getBackgroundColor(int position) {
        return 0;
    }

    @Override
    public int getBackgroundColorSelected(int position) {
        return 0;
    }

    @Override
    public void onLayout(View view, int position) {

        Tag tag = (Tag)list.get(position);
        String text = tag.getText();


        CustomFontTextView textView = (CustomFontTextView) view.findViewById(R.id.chip_text);
        if (!TextUtils.isEmpty(text)){
            textView.setText(text);
        }

    }
}
