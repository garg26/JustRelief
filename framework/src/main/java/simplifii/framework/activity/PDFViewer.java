package simplifii.framework.activity;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import simplifii.framework.R;
import simplifii.framework.utility.AppConstants;

public class PDFViewer extends BaseActivity {
    private String docFileUrl;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_viewer);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + docFileUrl);
    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);
        docFileUrl = bundle.getString(AppConstants.BUNDLE_KEYS.DOC_FILE_URL);

    }
}
