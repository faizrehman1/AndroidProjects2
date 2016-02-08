package example.moosa.com.fragments2prac;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    public Cities citiesArray = new Cities();
    ListAdapter arrayAdaptor;
    ListView listInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        citiesArray = new Cities();
        final PictureView pictureFragment = (PictureView) getFragmentManager().findFragmentById(R.id.pictureViewFragmentIdInXml);
        pictureFragment.setPicture(citiesArray.get(0));

        ListFragment listFragment = (ListFragment) getFragmentManager().findFragmentById(R.id.listViewFragmentIdInXml);
        View view = listFragment.getView();
        listInFragment = (ListView) view.findViewById(R.id.listViewIdInXml);
        arrayAdaptor = new ArrayAdapter<City>(this, R.layout.listlayoutview, citiesArray);
        listInFragment.setAdapter(arrayAdaptor);
        listInFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pictureFragment.setPicture(citiesArray.get(position));

            }
        });
    }


}
