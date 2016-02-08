package example.moosa.com.volleyjsonplaceholder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {
    public static String URL = "http://www.begreen2day.com/gallery/images/outdoor_lighting.jpg";
    TextView textView;
    Button button;
    StringBuilder stringBuilder = new StringBuilder();
    private NetworkImageView networkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textToShow);
        button = (Button) findViewById(R.id.clickButton);
        networkImageView = (NetworkImageView) findViewById(R.id.networkImageLoader);
        getImage(URL);

    }

    private void getImage(String url) {
        ImageLoader imageLoader = VolleySingleton.getInstance(this).getImageLoader();
        networkImageView.setImageUrl(url, imageLoader);

    }

    public void jsonArrayRequest() {
        final JsonArrayRequest getData = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("title");
                        //    String email = jsonObject.getString("email");
                        String body = jsonObject.getString("body");
                        Log.d("DATA", name + body);
                        //  textView.append();
                        stringBuilder.append("\n" + "Name:" + name
                                + "\nBody:" + body);//+ "\nEmail:" + email
                        Log.d("Moosa", "Setting text");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                textView.setText(stringBuilder.toString());

//                textView.setText(jsonArray.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleySingleton.getInstance(MainActivity.this).getRequestQueue().add(getData);

            }
        });
    }
}
