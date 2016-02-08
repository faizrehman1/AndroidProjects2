package moosa.pana.com.me;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import Location.Tracker;


public class LocationFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button myLocation;
    private double lat, lng;
    private String userId;

    private String linkFirst = "https://mepanacloud.firebaseio.com/users";
    private Firebase url;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Tracker tracker;

    private OnFragmentInteractionListener mListener;

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        myLocation = (Button) view.findViewById(R.id.myLocationButton);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.myLocationMapFragment);
        mapFragment.getMapAsync(this);
        url = new Firebase(linkFirst);

        url.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    //  userId = authData.getUid();
                    Log.d("Data Authentication->", "User Id is " + authData.getUid());
                    userId = authData.getUid();

                    url = new Firebase(linkFirst + "/" + userId + "/userlocation");
                    Log.d("New URL is->", "Url is " + url.getPath().toString());
                }
                //else {
                //  new MainActivity().logout();
                // }

            }
        });
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Location= " + lng + " - " + lat, Toast.LENGTH_SHORT).show();
                url.child("Lat").setValue(lat);
                url.child("Lng").setValue(lng);

            }
        });
        enabledTracking();
        return view;
    }

    private void enabledTracking() {
        tracker = new Tracker(getActivity());
        if (tracker.isCanGetLocation()) {
            lat = tracker.getLat();
            lng = tracker.getLng();
        } else {
            Toast.makeText(getActivity(), "Cant Get Current Location", Toast.LENGTH_SHORT).show();
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();
        tracker.stopGPS();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(lat, lng);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("I am here"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(latLng)
                .zoom(18)
                .bearing(90)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
