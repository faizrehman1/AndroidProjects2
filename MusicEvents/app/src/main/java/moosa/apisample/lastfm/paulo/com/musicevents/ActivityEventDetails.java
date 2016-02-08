package moosa.apisample.lastfm.paulo.com.musicevents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import models.Events;


public class ActivityEventDetails extends AppCompatActivity {
    private Events events;
    private TextView headLiner, venue, where, when;
    private NetworkImageView bandImage;
    private ImageLoader loader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_event_details);
        events = (Events) getIntent().getSerializableExtra("event");
        headLiner = (TextView) findViewById(R.id.detailsHeadLiner);
        venue = (TextView) findViewById(R.id.detailsVenue);
        where = (TextView) findViewById(R.id.detailsWhere);
        when = (TextView) findViewById(R.id.detailsWhen);
        bandImage = (NetworkImageView) findViewById(R.id.detailsBandImage);
        //////////////////////////////////////////////////////////////////

        setDetails();

    }

    private void setDetails() {


        headLiner.setText(events.getHeadLiner());
        venue.setText(events.getVenueName());
        where.setText("Location: " + events.getStreet() + ", " + events.getCity() + ", " + events.getCountry());
        when.setText(events.getStartDate());
        bandImage.setImageUrl(events.getUrl(), loader);
    }

}
