package com.justreliefdoctors.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.justreliefdoctors.R;
import com.justreliefdoctors.fragment.AwardMembershipFragment;
import com.justreliefdoctors.fragment.ClinicFragment;
import com.justreliefdoctors.fragment.ContactDoctorDetailsFragment;
import com.justreliefdoctors.fragment.DoctorListFragment;
import com.justreliefdoctors.fragment.QualificationSpecFragment;
import com.justreliefdoctors.fragment.QualificationSpecializationFragment;
import com.justreliefdoctors.fragment.RegistrationFragment;
import com.justreliefdoctors.fragment.ServicesFragment;
import simplifii.framework.utility.AppConstants;



public class FragmentContainer extends simplifii.framework.activity.FragmentContainer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(simplifii.framework.R.layout.activity_fragment_container);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, getFragment(getIntent().getExtras().getInt(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE))).commit();
    }
    public static void startActivity(Context ctx, int fragmentType, Bundle extraBundle) {
        Intent i = new Intent(ctx, FragmentContainer.class);
        if (extraBundle != null) {
            i.putExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE, extraBundle);
        }
        i.putExtra(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE, fragmentType);
        ctx.startActivity(i);
    }

    public static void startActivityForResult(Context context, int fragmentType, Bundle extraBundle, int requestCode, Fragment fragment){
        Intent intent = new Intent(context, FragmentContainer.class);
        if(extraBundle != null){
            intent.putExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE, extraBundle);
        }
        intent.putExtra(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE, fragmentType);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Activity context, int fragmentType, Bundle extraBundle, int requestCode){
        Intent intent = new Intent(context, FragmentContainer.class);
        if(extraBundle != null){
            intent.putExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE, extraBundle);
        }
        intent.putExtra(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE, fragmentType);
        context.startActivityForResult(intent, requestCode);
    }



    private Fragment getFragment(int fragmentType) {
        Fragment fragment = null;
        switch (fragmentType) {
            case AppConstants.FRAGMENT_TYPES.DOCTOR_LIST_FRAGMENT:
                initToolBar(getString(R.string.doctors_details));
                fragment = new DoctorListFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.CONTACT_DOCTOR_DETAILS_FRAGMENT:
                initToolBar(getString(R.string.contact_doctor_details));
                fragment = new ContactDoctorDetailsFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.QUALIFICATION_FRAGMENT:
                initToolBar(getString(R.string.qualification_specialization));
                fragment = new QualificationSpecFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.REGISTRATION_DETAILS_FRAGMENT:
                initToolBar(getString(R.string.registration_detail));
                fragment = new RegistrationFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.CLINIC_FRAGMENT:
                initToolBar(getString(R.string.clinic_details));
                fragment = new ClinicFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.SERVICES_FRAGMENT:
                initToolBar(getString(R.string.services));
                fragment = new ServicesFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.AWARD_MEMBERSHIP_FRAGMENT:
                initToolBar(getString(R.string.award_membership_details));
                fragment = new AwardMembershipFragment();
                break;


        }
        if(fragment != null){
            fragment.setArguments(getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE));
        }
        return fragment;
    }

}
