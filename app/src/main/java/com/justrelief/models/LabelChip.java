package com.justrelief.models;

import com.plumillonforge.android.chipview.Chip;

/**
 * Created by nbansal2211 on 20/12/16.
 */

public class LabelChip implements Chip {
    private String label;
    private boolean isSelected;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String getText() {
        return label;
    }
}
