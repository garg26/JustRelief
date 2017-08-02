package com.justrelief.models;

import java.util.List;

/**
 * Created by kartikeya-pc on 5/13/17.
 */

public class ClinicNameItem {
    private String name;
    private int itemId;
    private String imageUrl;

    public ClinicNameItem(String name, int itemId) {
        this.name = name;
        this.itemId = itemId;
    }

    public static List<ClinicNameItem> getList() {

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }
    public String getImageUrl(){
        return imageUrl;
    }
}
