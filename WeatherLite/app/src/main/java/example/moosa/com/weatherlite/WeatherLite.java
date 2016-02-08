package example.moosa.com.weatherlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import Data.CityPrefrences;
import Data.JsonWeatherParser;
import Data.WeatherHTTPClient;
import Model.Weather;
import Util.Utils;


public class WeatherLite extends ActionBarActivity {
    Weather weather = new Weather();
    private TextView cityName, temp, description, humidity, pressure, wind, sunrise, sunset, updated;
    private ImageView iconView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_lite);

        cityName = (TextView) findViewById(R.id.cityText);
        iconView = (ImageView) findViewById(R.id.thumbnailIcon);
        temp = (TextView) findViewById(R.id.tempText);
        description = (TextView) findViewById(R.id.cloudText);
        humidity = (TextView) findViewById(R.id.humidText);
        pressure = (TextView) findViewById(R.id.pressureText);
        wind = (TextView) findViewById(R.id.windText);
        sunrise = (TextView) findViewById(R.id.riseText);
        sunset = (TextView) findViewById(R.id.setText);
        updated = (TextView) findViewById(R.id.updateText);
        CityPrefrences cityPrefrences = new CityPrefrences(WeatherLite.this);

        renderWeatherData(cityPrefrences.getCity());

    }

    public void renderWeatherData(String city) {
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&units=metrics"});
    }


    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WeatherLite.this);
        builder.setTitle("Change City");
        final EditText cityInput = new EditText(WeatherLite.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("London,GB");
        builder.setView(cityInput);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CityPrefrences cityPrefrences = new CityPrefrences(WeatherLite.this);
                cityPrefrences.setCity(cityInput.getText().toString());

                    renderWeatherData(cityPrefrences.getCity());

                }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_lite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.changeCityId) {
            showInputDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class DownloadImageAsync extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImage(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            iconView.setImageBitmap(bitmap);

            super.onPostExecute(bitmap);
        }

        private Bitmap downloadImage(String code) {
            final DefaultHttpClient client = new DefaultHttpClient();
            final HttpGet getRequest = new HttpGet(Utils.ICON_URL + code + ".png");
            try {
                HttpResponse response = client.execute(getRequest);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    Log.d("Error Downloading Image", "Errors: " + statusCode);
                    return null;
                }
                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = null;
                    inputStream = entity.getContent();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public class WeatherTask extends AsyncTask<String, Void, Weather> {


        @Override
        protected Weather doInBackground(String... params) {
            String data = (new WeatherHTTPClient().getWeatherData(params[0]));
            weather = JsonWeatherParser.getWeather(data);
            weather.iconData = weather.currentCondition.getIcon();
            Log.d("MYDEBUG", weather.place.getCity());
            new DownloadImageAsync().execute(weather.iconData);
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            DateFormat df = DateFormat.getTimeInstance();
            String sunrse = df.format(new Date(weather.place.getSunrise()));
            String sunse = df.format(new Date(weather.place.getSunset()));
            String updateDate = df.format(new Date(weather.place.getLastUpdate()));
            Log.d("Current Temperature----", (weather.currentCondition.getTemprature() / 10) + "");
            DecimalFormat dc = new DecimalFormat("#.#");
            String tempFormat = dc.format((weather.currentCondition.getTemprature() / 10));

            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText(tempFormat + "C");
            humidity.setText("Humidity: " + weather.currentCondition.getHumidty() + "%");
            pressure.setText("Pressure: " + weather.currentCondition.getPressure() + "hPa");
            wind.setText("Wind: " + weather.wind.getSpeed() + "mps");
            sunrise.setText("Sunrise: " + sunrse);
            sunset.setText("Sunset: " + sunse);
            updated.setText("Last Updated: " + updateDate);
            description.setText("Condition: " + weather.currentCondition.getCondtion() + " (" + weather.currentCondition.getDescription() + ")");
        }
    }
}
