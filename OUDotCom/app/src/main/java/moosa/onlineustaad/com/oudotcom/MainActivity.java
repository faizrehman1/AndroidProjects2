package moosa.onlineustaad.com.oudotcom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {
    public static String CLIENT_KEY = "ExVUq00TW3Ise47PHvYxpf8i7yPAgdBSypj5nEKv";
    public static String APP_ID = "cs9HpIQVDsNZqmKEE7RPOAkzJl3vxGm59x8g7DrN";
    public static String URL2 = "onlineustaad.com";
    private String URL = "http://www.onlineustaad.com/";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.canGoBack();
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            loadWebContent(URL);
        }
        //Stop local links and redirects from opening in browser instead of webview
        webView.setWebViewClient(new MyAppWebViewClient(this));
        pushService();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void pushService() {
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }

    private void loadWebContent(String load) {
        Toast.makeText(this, "Loading Content Please Wait", Toast.LENGTH_LONG).show();
        webView.loadUrl(load);
    }

    @Override
    public void onBackPressed() {

        if (webView.copyBackForwardList().getCurrentIndex() >= 1) {
            Toast.makeText(MainActivity.this, "Loading", Toast.LENGTH_SHORT).show();
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
