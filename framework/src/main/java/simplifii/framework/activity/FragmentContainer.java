package simplifii.framework.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import simplifii.framework.R;
import simplifii.framework.utility.AppConstants;


public class FragmentContainer extends  BaseActivity{
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
    }
}
