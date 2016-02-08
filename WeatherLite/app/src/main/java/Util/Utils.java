package Util;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * Created by Moosa on 7/2/2015.
 */
public class Utils {
    public static final String API_KEY="&APPID=0712fcb502d12ece9d05d6a2efb2654d";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String ICON_URL = "http://openweathermap.org/img/w/";

    public static JSONObject getObject(String tagName, JSONObject jsonObject) throws JSONException {
        JSONObject jobject = jsonObject.getJSONObject(tagName);
        return jobject;
    }

    public static String getString(String tagname, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString(tagname);
    }

    public static float getFloat(String tagname, JSONObject jsonObject) throws JSONException {
        return (float) jsonObject.getDouble(tagname);
    }
    public static double getDouble(String tagname, JSONObject jsonObject) throws JSONException {
        return (float) jsonObject.getDouble(tagname);
    }
    public static int getInt(String tagname, JSONObject jsonObject) throws JSONException {
        return  jsonObject.getInt(tagname);
    }


}
