package Data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Moosa on 7/4/2015.
 */
public class CityPrefrences {
    SharedPreferences pref;

    public CityPrefrences(Activity activity) {
        pref = activity.getPreferences(Activity.MODE_PRIVATE);

    }

    public String getCity() {
        return pref.getString("city", "London,UK");

    }

    public void setCity(String city) {
        pref.edit().putString("city", city).commit();
    }
}
