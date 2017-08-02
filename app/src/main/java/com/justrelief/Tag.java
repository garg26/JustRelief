package com.justrelief;

import com.plumillonforge.android.chipview.Chip;



public class Tag implements Chip {
    private String mName;


    public Tag(String name) {
        mName = name;
    }

    @Override
    public String getText() {
        return mName;
    }

}