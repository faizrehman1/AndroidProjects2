package moosa.onlineustaad.com.oudotcom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Moosa on 8/13/2015.
 * Dear Maintainer
 * When i wrote this code Only i and God knew What it was.
 * Now only God Knows..!
 * So if you are done trying to optimize this routine and Failed
 * Please increment the following counter as the warning to the next Guy.
 * TOTAL_HOURS_WASTED_HERE=1
 */
public class MyAppWebViewClient extends WebViewClient {
    private Activity activity;

    public MyAppWebViewClient(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        showToast("Error While Loading Page...!");
        view.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        showToast("Loading...");
        if (Uri.parse(url).getHost().endsWith(MainActivity.URL2) && !url.equals("http://www.onlineustaad.com/feed/")) {
            view.getSettings().setUseWideViewPort(true);
            view.getSettings().setLoadWithOverviewMode(true);
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }

    private void showToast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

}
