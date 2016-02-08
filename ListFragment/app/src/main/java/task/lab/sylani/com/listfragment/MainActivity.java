package task.lab.sylani.com.listfragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity implements ListFragment.OnFragmentInteractionListener {
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
