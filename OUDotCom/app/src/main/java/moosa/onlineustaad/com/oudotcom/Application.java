package moosa.onlineustaad.com.oudotcom;

import com.parse.Parse;

/**
 * Created by Moosa on 8/13/2015.
 * Dear Maintainer
 * When i wrote this code Only i and God knew What it was.
 * Now only God Knows..!
 * So if you are done trying to optimize this routine and Failed
 * Please increment the following counter as the warning to the next Guy.
 * TOTAL_HOURS_WASTED_HERE=1
 */
public class Application extends android.app.Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, MainActivity.APP_ID, MainActivity.CLIENT_KEY);

    }
}
