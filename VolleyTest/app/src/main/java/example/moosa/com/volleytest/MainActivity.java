package example.moosa.com.volleytest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;


public class MainActivity extends ActionBarActivity {
    public static final String TAG = "mytag";
    RequestQueue queue;
    StringRequest stringRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonListener();
        cancelButton();

    }

    private void cancelButton() {
        Button btn = (Button) findViewById(R.id.tweet);
        btn.setText("Request Picture");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText medit = (EditText) findViewById(R.id.edittext);

                String url = medit.getText().toString();
                ListView list=(ListView)findViewById(R.id.listView);
                final UserTimeline userTimeline = new UserTimeline.Builder()
                        .screenName(url)
                        .build();
                final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(MainActivity.this, userTimeline);
                list.setAdapter(adapter);
            }
        });
    }

    private void loadImage() {
        EditText medit = (EditText) findViewById(R.id.edittext);

        String url = medit.getText().toString();
        final ImageView imgView = (ImageView) findViewById(R.id.imageview);
        ImageRequest mImageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imgView.setImageBitmap(bitmap);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                    imgView.setImageResource(R.drawable.abc_popup_background_mtrl_mult);
            }
        });

    }

    private void buttonListener() {
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResponse();
            }
        });
    }

    private void getResponse() {
        final TextView tv = (TextView) findViewById(R.id.textview);
        queue = VolleySingleton.getmInstance().getRequestQueue();
        String url = "http://www.google.com";
        stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                tv.setText("Response is =" + s.substring(0, 500));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tv.setText("Error Getting Request");
            }
        });
        stringRequest.setTag(TAG);
        queue.add(stringRequest);


    }


}


