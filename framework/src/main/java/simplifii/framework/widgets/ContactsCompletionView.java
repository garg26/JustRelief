package simplifii.framework.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.tokenautocomplete.TokenCompleteTextView;

import java.util.List;

import simplifii.framework.R;


public class ContactsCompletionView extends TokenCompleteTextView<String> {


    private int defStyleAttr;
    private AttributeSet attrs;
    private Context context;

    @Override
    protected View getViewForObject(String object) {
        View view = null;
        if (object!=null) {
             view = LayoutInflater.from(context).inflate(R.layout.row_chip_days, (ViewGroup) getParent(), false);
            TextView textView = (TextView) view.findViewById(R.id.tv_days);
            textView.setText(object);

        }
        return view;
    }


    @Override
    protected String defaultObject(String completionText) {
        return "";
    }




    public ContactsCompletionView(Context context) {
        super(context);
        this.context = context;
    }

    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomAttributes(context,attrs);
        this.context = context;
        this.attrs = attrs;
    }

    public ContactsCompletionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomAttributes(context,attrs);
        this.context = context;
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;
    }

    private void setCustomAttributes(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs,
                simplifii.framework.R.styleable.CustomTextInputLayout);
        String errorMsg = a.getString(simplifii.framework.R.styleable.CustomTextInputLayout_errorMsg);

        String emptyErrorMsg = a.getString(simplifii.framework.R.styleable.CustomTextInputLayout_emptyErrorMsg);
        a.recycle();
    }


}
