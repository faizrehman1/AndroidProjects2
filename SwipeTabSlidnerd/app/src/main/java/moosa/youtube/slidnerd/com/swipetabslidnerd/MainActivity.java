package moosa.youtube.slidnerd.com.swipetabslidnerd;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);


    }
}
