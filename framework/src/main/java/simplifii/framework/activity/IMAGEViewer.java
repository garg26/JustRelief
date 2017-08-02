package simplifii.framework.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import simplifii.framework.R;
import simplifii.framework.utility.AppConstants;

public class IMAGEViewer extends BaseActivity {
    private String imgFileUrl;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_viewer);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(imgFileUrl);
    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);
        imgFileUrl = bundle.getString(AppConstants.BUNDLE_KEYS.IMG_FILE_URL);


    }
}
