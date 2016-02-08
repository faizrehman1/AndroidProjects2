package example.moosa.com.volleytest;

import android.app.Application;
import android.content.Context;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Moosa on 5/23/2015.
 */
public class MyApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "RaF8fyOfTPTBDxFwMuWZPqBP1";
    private static final String TWITTER_SECRET = "4U4YDebB4hZAGa7eO9CZNhwTvA4gXOCJDSSqORMQAaQLaz39rq";

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        mInstance=this;
    }
    public static MyApplication getInstance(){

        return mInstance;
    }


    public static Context getContext() {
        return mInstance.getApplicationContext();
    }
}
