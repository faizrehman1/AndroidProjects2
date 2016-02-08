package example.moosa.com.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


public class MainActivity extends ActionBarActivity {
    public Cities cityArray = new Cities();
    private ListAdapter mAdapter;
    private AbsListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CityFragment cityFragment = (CityFragment) getFragmentManager().findFragmentById(R.id.listviewfragment);

        View view = cityFragment.getView();
        mAdapter = new ArrayAdapter<City>(this, R.layout.city_list_item, cityArray);
        mListView = (AbsListView) view.findViewById(R.id.list_city_view);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        final PictureView pictureView = (PictureView) getFragmentManager().findFragmentById(R.id.pictureview);
        pictureView.setPicture(cityArray.get(0));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pictureView.setPicture(cityArray.get(position));
            }
        });


    }

}
