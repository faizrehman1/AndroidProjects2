package Location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Moosa on 9/1/2015.
 * Dear Maintainer
 * When i wrote this code Only i and God knew What it was.
 * Now only God Knows..!
 * So if you are done trying to optimize this routine and Failed
 * Please increment the following counter as the warning to the next Guy.
 * TOTAL_HOURS_WASTED_HERE=1
 */
public class Tracker extends Service implements LocationListener {
    public Location location;
    public LocationManager locationManager;
    boolean isGpsEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    double lat, lng;
    private Context mContext;

    public Tracker(Context mContext) {
        this.mContext = mContext;
        accessLocation();
    }

    private Location accessLocation() {
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        try {
            if (!isNetworkEnabled && !isGpsEnabled) {
                Toast.makeText(mContext, "No Network Enabled", Toast.LENGTH_SHORT).show();
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }
                    }
                }
                if (isGpsEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            lng = location.getLongitude();
                            lat = location.getLatitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public double getLng() {
        if (location != null) {
            lng = location.getLongitude();
        }
        return lng;
    }


    public double getLat() {
        if (location != null) {
            lat = location.getLatitude();
        }
        return lat;
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
    }

    public void stopGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
