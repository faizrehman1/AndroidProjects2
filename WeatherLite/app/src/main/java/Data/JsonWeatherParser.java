package Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.Place;
import Model.Weather;
import Util.Utils;

/**
 * Created by Moosa on 7/2/2015.
 */
public class JsonWeatherParser {
    public static Weather getWeather(String data) {
        Weather weather = new Weather();
        //Creating JsonObjectFrom Data
        try {
            JSONObject jsonObject = new JSONObject(data);
            Place place = new Place();
            JSONObject coorObj = Utils.getObject("coord", jsonObject);
            place.setLat(Utils.getFloat("lat", coorObj));
            place.setLon(Utils.getFloat("lon", coorObj));


            //Getting Sys Object
            JSONObject sysObject = Utils.getObject("sys", jsonObject);
            place.setCountry(Utils.getString("country", sysObject));
            place.setLastUpdate(Utils.getInt("dt", jsonObject));
            place.setSunrise(Utils.getInt("sunrise", sysObject));
            place.setSunset(Utils.getInt("sunset", sysObject));
            place.setCity(Utils.getString("name", jsonObject));
            weather.place = place;

            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utils.getInt("id", jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description", jsonWeather));
            weather.currentCondition.setCondtion(Utils.getString("main", jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon", jsonWeather));

            JSONObject wind = Utils.getObject("wind", jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed", wind));
            weather.wind.setSpeed(Utils.getFloat("deg", wind));

            JSONObject main = Utils.getObject("main", jsonObject);
            weather.currentCondition.setHumidty(Utils.getInt("humidity",main));
            weather.currentCondition.setPressure(Utils.getInt("pressure", main));
            weather.currentCondition.setMaxTemp(Utils.getFloat("temp_min", main));
            weather.currentCondition.setMinTemp(Utils.getFloat("temp_max", main));
            weather.currentCondition.setTemprature(Utils.getFloat("temp",main));

            JSONObject cloudObj = Utils.getObject("clouds", jsonObject);
            weather.clouds.setPercipitations(Utils.getInt("all", cloudObj));

            return weather;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
