package com.justrelief.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import com.justrelief.R;
import com.justrelief.fragments.AwardAcceredationsFragment;
import com.justrelief.fragments.ContactClinicDetailsFragment;
import com.justrelief.fragments.DoctorsFragment;
import com.justrelief.fragments.LocationPhotosFragment;
import com.justrelief.fragments.ProfileListFragment;
import com.justrelief.fragments.SpecializationsServicesFragment;
import com.justrelief.fragments.TimingsFragment;

import simplifii.framework.utility.AppConstants;

public class FragmentContainer extends AppBaseActivity {

    public static void startActivity(Context ctx, int fragmentType, Bundle extraBundle) {
        Intent i = new Intent(ctx, FragmentContainer.class);
        if (extraBundle != null) {
            i.putExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE, extraBundle);
        }
        i.putExtra(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE, fragmentType);
        ctx.startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, getFragment(getIntent().getExtras().getInt(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE))).commit();
    }

    private Fragment getFragment(int fragmentType) {
        Fragment fragment = null;
        switch (fragmentType) {
            case AppConstants.FRAGMENT_TYPES.PROFILE_LIST_FRAGMENT:
                 initToolBar("Clinic Profile");
                 fragment = new ProfileListFragment();
                 break;
            case AppConstants.FRAGMENT_TYPES.CONTACT_CLINIC_DETAILS_FRAGMENT:
                 initToolBar("Contact & Clinic Details");
                 fragment = new ContactClinicDetailsFragment();
                 break;
            case AppConstants.FRAGMENT_TYPES.TIMINGS_FRAGMENT:
                 initToolBar("Timings Details");
                 fragment = new TimingsFragment();
                 break;
            case AppConstants.FRAGMENT_TYPES.LOCATION_PHOTOS_FRAGMENT:
                 initToolBar("Location & Photos Details");
                 fragment = new LocationPhotosFragment();
                 break;
            case AppConstants.FRAGMENT_TYPES.DOCTORS_FRAGMENT:
                 initToolBar("Doctors Details");
                 fragment = new DoctorsFragment();
                 break;
            case AppConstants.FRAGMENT_TYPES.SPECIALIZATIONS_SERVICES_FRAGMENT:
                 initToolBar("Specialization & Services Details");
                 fragment = new SpecializationsServicesFragment();
                 break;
            case AppConstants.FRAGMENT_TYPES.AWARD_ACCEREDATIONS_FRAGMENT:
                 initToolBar("Award & Acceredations Details");
                 fragment = new AwardAcceredationsFragment();
                 break;


        }
        if(fragment != null){
            fragment.setArguments(getIntent().getBundleExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE));
       }
        return fragment;
    }
}
