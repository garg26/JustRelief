package com.justrelief.fragments;

import com.google.gson.Gson;
import java.util.List;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.models.response.ClinicResponse;
import simplifii.framework.models.response.UserProfileResponse;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.BaseApiGenerator;


public abstract class AppBaseFragment extends BaseFragment {



    protected void getFacilityID1(){

    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (response==null){
            return;
        }
        switch (taskCode){

        }
    }
}
