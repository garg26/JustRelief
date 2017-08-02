package com.justrelief.autoadapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.justrelief.R;
import com.justrelief.models.LabelChip;
import com.plumillonforge.android.chipview.ChipViewAdapter;

import java.util.List;

/**
 * Created by robin on 11/28/16.
 */

public class ActionChipViewAdapter extends ChipViewAdapter {

    private List<LabelChip> questionInfos;

    public ActionChipViewAdapter(Context context, List<LabelChip> list) {
        super(context);
        this.questionInfos = list;
    }

    @Override
    public int getLayoutRes(int position) {
        return R.layout.row_chip_header_label;
    }

    @Override
    public int getBackgroundRes(int position) {
        LabelChip info = questionInfos.get(position);
        if (info.isSelected()) {
            return R.drawable.chip_label_selected;
        } else {
            return R.drawable.chip_label_unselected;
        }
    }

    @Override
    public int getBackgroundColor(int position) {
        return R.color.white;
    }

    @Override
    public int getBackgroundColorSelected(int position) {
        return R.color.light_gray;
    }

    @Override
    public void onLayout(View view, int position) {
        LabelChip action = (LabelChip) getChip(position);
        TextView tvActionName = (TextView) view.findViewById(android.R.id.text1);
        tvActionName.setText(action.getText());
    }
}
