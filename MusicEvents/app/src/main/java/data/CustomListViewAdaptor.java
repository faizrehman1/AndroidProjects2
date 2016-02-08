package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import models.Events;
import moosa.apisample.lastfm.paulo.com.musicevents.ActivityEventDetails;
import moosa.apisample.lastfm.paulo.com.musicevents.AppController;
import moosa.apisample.lastfm.paulo.com.musicevents.R;


/*
 * Created by Moosa on 7/23/2015.
 */
public class CustomListViewAdaptor extends ArrayAdapter<Events> {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private LayoutInflater inflater;
    private ArrayList<Events> data;
    private Activity context;
    private int layoutResId;


    public CustomListViewAdaptor(Activity context, int resourceId, ArrayList<Events> objs) {
        super(context, resourceId, objs);
        data = objs;
        this.context = context;
        layoutResId = resourceId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Events getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(Events item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewHolder viewHolder;
        if (row == null) {
            inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.bandImage = (NetworkImageView) row.findViewById(R.id.bandImage);
            viewHolder.headliner = (TextView) row.findViewById(R.id.headLinearText);
            viewHolder.venue = (TextView) row.findViewById(R.id.venueText);
            viewHolder.when = (TextView) row.findViewById(R.id.whenText);
            viewHolder.where = (TextView) row.findViewById(R.id.whereText);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();

        }
        viewHolder.events = data.get(position);
        viewHolder.headliner.setText("Head Line: " + viewHolder.events.getHeadLiner());
        viewHolder.venue.setText("Venue: " + viewHolder.events.getVenueName());
        viewHolder.when.setText("Start Date: " + viewHolder.events.getStartDate());
        viewHolder.where.setText("Location: " + viewHolder.events.getStreet() + ", " + viewHolder.events.getCity() + ", " + viewHolder.events.getCountry());
        viewHolder.bandImage.setImageUrl(viewHolder.events.getUrl(), imageLoader);
        viewHolder.website = viewHolder.events.getWebsite();
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, ActivityEventDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("event", viewHolder.events);
                detailIntent.putExtras(bundle);
                context.startActivity(detailIntent);
            }
        });
        return row;
    }

    class ViewHolder {
        Events events;
        TextView headliner;
        TextView venue;
        TextView where;
        TextView when;
        String website;
        NetworkImageView bandImage;
    }
}
