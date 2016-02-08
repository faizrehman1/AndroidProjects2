package moosa.apisample.lastfm.paulo.com.musicevents;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import data.CustomListViewAdaptor;
import models.Events;
import utils.Database;
import utils.Prefs;


public class MainActivity extends AppCompatActivity {
    private static final ArrayList<String> CITIES = new ArrayList<>();
    private CustomListViewAdaptor adaptor;
    private ArrayList<Events> eventsList = new ArrayList<>();
    private String getCityURL = "http://ws.audioscrobbler.com/2.0/?method=geo.getmetros&api_key=6aa9fabbde3e59eafe4843f4532107d4&format=json";
    private String urlLeft = "http://ws.audioscrobbler.com/2.0/?method=geo.getevents&location=";
    private String urlRight = "&api_key=6aa9fabbde3e59eafe4843f4532107d4&format=json";
    private ListView listView;
    private TextView selectedCity;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        selectedCity = (TextView) findViewById(R.id.selectedLocationText);
        adaptor = new CustomListViewAdaptor(this, R.layout.list_row, eventsList);
        listView.setAdapter(adaptor);

        Prefs cityPref = new Prefs(MainActivity.this);
        String city = cityPref.getCity();
        selectedCity.setText("Selected City: " + city.toUpperCase());
        showEvents(city);
        database = new Database(this);
        boolean cityDatabase = cityPref.getCitiesNameData();
        if (!cityDatabase) {
            getCitiesNameDataOnline();
        } else {
            addCitiesData();
        }
    }

    private void addCitiesData() {
        Log.d("INVOKE---->>", "addCitiesData");

        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                CITIES.clear();
                Log.d("Extracting Db Size--->", "Size=" + database.getCityNamesCollection().size());

                for (String a : database.getCityNamesCollection()) {
                    CITIES.add(a);
                    Log.d("Extracting Db Array--->", a);

                }
                return null;
            }
        };
        asyncTask.execute();
    }

    private void getCitiesNameDataOnline() {
        final ArrayList<String> myList = new ArrayList<>();
        JsonObjectRequest citiesNamesRequest = new JsonObjectRequest(Request.Method.GET, getCityURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, "RESP TOAST", Toast.LENGTH_LONG).show();

                try {
                    JSONObject temp = jsonObject.getJSONObject("metros");
                    JSONArray metroArray = temp.getJSONArray("metro");
                    for (int i = 0; i < metroArray.length(); i++) {
                        String get = metroArray.getJSONObject(i).getString("name");
                        Log.d("Extracting Array------>", get);
                        myList.add(get);

                    }
                    AsyncTask<Void, Void, Void> asyTask = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            Log.d("Extracted Array Size-->", "Size is " + myList.size());

                            database.setCityNamesCollection(myList);
                            CITIES.clear();
                            for (String a : myList) {
                                CITIES.add(a);
                            }
                            Prefs prefs = new Prefs(MainActivity.this);
                            prefs.setCitiesNameData(true);
                            return null;
                        }
                    };
                    asyTask.execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "Error: " + volleyError.toString().replaceAll("com.android.volley.", ""), Toast.LENGTH_LONG).show();

            }
        });
        AppController.getInstance().addToRequestQueue(citiesNamesRequest);

    }

    private void getEvents(String city) {
        eventsList.clear();
        String finalUrl = urlLeft + city + urlRight;
        if (city.contains(" ")) {
            finalUrl = urlLeft + city.replace(" ", "%20") + urlRight;
        }
        JsonObjectRequest eventsRequest = new JsonObjectRequest(Request.Method.GET, finalUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject eventsObject = response.getJSONObject("events");
                    JSONArray eventsArray = eventsObject.getJSONArray("event");
                    for (int i = 0; i < eventsArray.length(); i++) {
                        JSONObject jsonObject = eventsArray.getJSONObject(i);
                        //get artist
                        JSONObject artistObject = jsonObject.getJSONObject("artists");
                        String headlinerText = artistObject.getString("headliner");
                        //venue
                        JSONObject venueObject = jsonObject.getJSONObject("venue");
                        String venueName = venueObject.getString("name");

                        //Location Object
                        JSONObject locationObject = venueObject.getJSONObject("location");
                        String city = locationObject.getString("city");
                        String country = locationObject.getString("country");
                        String street = locationObject.getString("street");
                        //String postalCode = locationObject.getString("postalcode");

                        //Start date
                        String startDate = jsonObject.getString("startDate");
                        //website
                        String website = jsonObject.getString("website");

                        //get url Image
                        JSONArray imageArry = jsonObject.getJSONArray("image");
                        JSONObject largeImage = imageArry.getJSONObject(3);
                        String largeImageURL = largeImage.getString("#text");

                        Events event = new Events();
                        event.setHeadLiner(headlinerText);
                        event.setVenueName(venueName);
                        event.setCity(city);
                        event.setWebsite(website);
                        event.setStartDate(startDate);
                        event.setStreet(street);
                        event.setCountry(country);
                        event.setUrl(largeImageURL);
                        eventsList.add(event);
                    }
                    adaptor.notifyDataSetChanged();

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Error While Retrieving Data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "Error: " + volleyError.toString().replaceAll("com.android.volley.", ""), Toast.LENGTH_LONG).show();

            }
        });
        AppController.getInstance().addToRequestQueue(eventsRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.changeLocation) {
            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change City");
        final AutoCompleteTextView autoCountry = new AutoCompleteTextView(MainActivity.this);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.hint_view, CITIES);
        autoCountry.setAdapter(stringArrayAdapter);
        autoCountry.setInputType(InputType.TYPE_CLASS_TEXT);
        autoCountry.setHint("Search City...");
        builder.setView(autoCountry);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Prefs cityPreferences = new Prefs(MainActivity.this);
                cityPreferences.setCity(autoCountry.getText().toString());
                String newCity = cityPreferences.getCity();
                selectedCity.setText("Selected City: " + newCity.toUpperCase());
                showEvents(newCity);
            }
        });
        builder.show();
    }

    private void showEvents(String newCity) {
        getEvents(newCity);
    }
}
