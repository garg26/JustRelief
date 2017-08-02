package com.justrelief.models;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.plumillonforge.android.chipview.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.JsonUtil;

/**
 * Created by saurabh on 16-09-2016.
 */
public class ClassObject extends BaseAutoFilter implements Chip, Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("class")
    @Expose
    private String classVal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassVal() {
        return classVal;
    }

    public void setClassVal(String classVal) {
        this.classVal = classVal;
    }


    @Override
    public String getDisplayString() {
        if (TextUtils.isDigitsOnly(classVal)) {
            return "Class " + classVal;
        }
        return classVal;
    }

    @Override
    public boolean isMatchingConstraint(String text) {
        return getDisplayString().toLowerCase().contains(text.toLowerCase());
    }

    public static List<ClassObject> parseJson(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        List<ClassObject> list = new ArrayList<>();
        for (int x = 0; x < jsonArray.length(); x++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(x);
            ClassObject cityApi = (ClassObject) JsonUtil.parseJson(jsonObject.toString(), ClassObject.class);
            list.add(cityApi);
        }
        return list;
    }

    @Override
    public String getText() {
        return getDisplayString();
    }

    public static JSONArray getSelectedClasses(List<ClassObject> selectedClasses) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (ClassObject classObject : selectedClasses) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", classObject.getId());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

}
