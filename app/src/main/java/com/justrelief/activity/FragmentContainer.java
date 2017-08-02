package com.justrelief.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.justrelief.R;
import com.justrelief.fragments.AwardAccreditationFragment;
import com.justrelief.fragments.ContactClinicDetailsFragment;
import com.justrelief.fragments.DoctorsFragment;
import com.justrelief.fragments.LocationPhotosFragment;
import com.justrelief.fragments.ProfileListFragment;
import com.justrelief.fragments.SpecializationsServicesFragment;
import com.justrelief.fragments.TimingsFragment;

import simplifii.framework.utility.AppConstants;

public class FragmentContainer extends AppBaseActivity {

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
            case AppConstants.FRAGMENT_TYPES.PROFILE_LIST_FRAGMENT:
                initToolBar(getString(R.string.clinic_profile));
                fragment = new ProfileListFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.CONTACT_CLINIC_DETAILS_FRAGMENT:
                initToolBar(getString(R.string.contact_clinic_details));
                fragment = new ContactClinicDetailsFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.TIMINGS_FRAGMENT:
                initToolBar(getString(R.string.timings_details));
                fragment = new TimingsFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.LOCATION_PHOTOS_FRAGMENT:
                initToolBar(getString(R.string.location_photos_details));
                fragment = new LocationPhotosFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.DOCTORS_FRAGMENT:
                initToolBar(getString(R.string.doctors_details));
                fragment = new DoctorsFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.SPECIALIZATIONS_SERVICES_FRAGMENT:
                initToolBar(getString(R.string.services_specialization_details));
                fragment = new SpecializationsServicesFragment();
                break;
            case AppConstants.FRAGMENT_TYPES.AWARD_ACCREDITATION_FRAGMENT:
                initToolBar(getString(R.string.award_accreditation_details));
                fragment = new AwardAccreditationFragment();
                break;


        }
        if (fragment != null) {
            fragment.setArguments(getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE));
        }
        return fragment;
    }
}
