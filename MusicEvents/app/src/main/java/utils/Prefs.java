package utils;

import android.app.Activity;
import android.content.SharedPreferences;

/*
 * Created by Moosa on 7/23/2015.
 */
public class Prefs {
    SharedPreferences preferences;

    public Prefs(Activity activity) {
        preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    //if city not selected
    public String getCity() {
        return preferences.getString("city", "Spokane");
    }

    public void setCity(String city) {
        preferences.edit().putString("city", city).commit();
    }


    public boolean getCitiesNameData() {
        return preferences.getBoolean("name", false);
    }

    public void setCitiesNameData(boolean city) {
        preferences.edit().putBoolean("name", city).commit();
    }

}
