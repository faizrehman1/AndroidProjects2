package Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/*Created by Moosa on 8/12/2015.
* Dear Maintainer
* When i wrote this code Only i and God knew What it was.
* Now only God Knows..!
* So if you are done trying to optimize this routine and Failed
* Please increment the following counter as the warning to the next Guy.
* TOTAL_HOURS_WASTED_HERE=10
 */
public class Shared {
    private SharedPreferences preferences;
    private String COMP_NAME = "company";

    public Shared(Activity activity) {
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
    }


    public String getCompanyName() {
        return preferences.getString(COMP_NAME, "Not Set");
    }

    public void setCompanyName(String com) {
        preferences.edit().putString(COMP_NAME, com).commit();
    }
}
