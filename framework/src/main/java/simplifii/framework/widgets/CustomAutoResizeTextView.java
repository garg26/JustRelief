package simplifii.framework.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.lb.auto_fit_textview.AutoResizeTextView;

import simplifii.framework.R;
import simplifii.framework.utility.AppConstants;

/**
 * Created by kartikeya-pc on 6/12/17.
 */

public class CustomAutoResizeTextView extends AutoResizeTextView {

    private static final String TAG = "CustomFontTV";

    public CustomAutoResizeTextView(Context context) {
        super(context);
    }

    public CustomAutoResizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomAutoResizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs,
                R.styleable.CustomFontTxtView);
        String customFont = a
                .getString(R.styleable.CustomFontTxtView_customFont);

        setCustomFont(ctx, customFont);
        a.recycle();
    }
    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        Log.d(TAG, "Asset Name"+asset);
        try {
            if (TextUtils.isEmpty(asset)) {
                asset = AppConstants.DEF_REGULAR_FONT;
            }
            tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/"+asset);
        } catch (Exception e) {
            Log.e(TAG, "Error to get typeface: " + e.getMessage());
            return false;
        }
        setTypeface(tf);
        return true;
    }

}
