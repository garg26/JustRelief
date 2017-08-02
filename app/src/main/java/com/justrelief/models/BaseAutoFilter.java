package com.justrelief.models;

import simplifii.framework.requestmodels.BaseAdapterModel;

/**
 * Created by nbansal2211 on 17/09/16.
 */
public abstract class BaseAutoFilter extends BaseAdapterModel{
    public abstract String getDisplayString();
    public abstract boolean isMatchingConstraint(String text);
}
