package pana.com.withoutview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", "Activity");
        //setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, MyService.class);
    //    startService(intent);
        finish();
        //onDestroy();
    }
}
