package com.example.moosa.touchdatabase;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    public static final String MyTAG = "tag";
    private List<Point> points = new ArrayList<>();
    private DataBase db = new DataBase(this);
    private String keyShared = "sharedKey";
    private int totalNumberOfPoints = 4;
    private int touchedSquareRelaxation = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onTouchActivity();

    }

    private void onTouchActivity() {
        ImageView img = (ImageView) findViewById(R.id.imageview);
       img.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               int x=Math.round(event.getX());
               int y=Math.round(event.getY());
               Log.d("Moosa","Coordinates x="+x+" y="+y);
               Toast.makeText(MainActivity.this,"Touched",Toast.LENGTH_LONG).show();
               return false;
           }
       });
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = Math.round(event.getX());
                int y = Math.round(event.getY());
                points.add(new Point(x, y));
                Log.d(MainActivity.MyTAG, String.format("Coordinates x=%d, y=%d", x, y));
                if (points.size() == 4) {
                    Log.d(MainActivity.MyTAG, "Point Size is =" + points.size());
                    SharedPreferences checkPrefs = getPreferences(MODE_PRIVATE);
                    boolean a = checkPrefs.getBoolean(keyShared, false);
                    if (!a) {
                        Log.d(MyTAG, "going to Saving to Database Method");
                        savingDataToDatabase(points);
                    } else {
                        Log.d(MyTAG, "Going to Verifying Method");
                        verifyPassPoints(points);
                    }

                }


                return false;
            }
        });
    }

    private void verifyPassPoints(final List<Point> pointsTouched) {
        final List<Point> pointsDatabase = db.pointsFromDatabase();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Checking Passpoints...");
        final AlertDialog alrg = builder.create();
        alrg.show();
        Log.d(MyTAG, "Verifying Method");
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {

                Log.d(MyTAG, "Doing in Background");
                if (pointsDatabase.size() != totalNumberOfPoints || pointsTouched.size() != totalNumberOfPoints) {
                    return false;
                }
                Log.d(MyTAG, "Doing in BackGround After if before For");
                for (int i = 0; i < totalNumberOfPoints; i++) {

                    int xDiff = pointsDatabase.get(i).x - pointsTouched.get(i).x;
                    int yDiff = pointsDatabase.get(i).y - pointsTouched.get(i).y;
                    int squareDiff = xDiff * xDiff + yDiff * yDiff;
                    if (squareDiff > touchedSquareRelaxation * touchedSquareRelaxation) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean passChecked) {
                points.clear();
                if (passChecked) {
                    Toast.makeText(MainActivity.this, "Verified", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Access Denied", Toast.LENGTH_LONG).show();
                }
                alrg.dismiss();
            }
        };
        task.execute();
    }

    private void savingDataToDatabase(final List<Point> p) {
        Log.d(MyTAG, "Saving Data to Database method");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Saving Data To The Storage");
        final AlertDialog alg = builder.create();
        alg.show();

        //////////////////////////ASYNC TASK////////////////////////////////////
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                db.saveDataToDatabase(p);
                Log.d(MyTAG, "Saved to DB");
                Log.d(MyTAG, "Background Data Saved");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.d(MyTAG, "Dismissing Dialog");
                points.clear();
                alg.dismiss();
                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(keyShared, true);
                editor.commit();

            }
        };

        task.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
