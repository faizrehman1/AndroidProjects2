package com.example.moosa.touchingpoints;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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


public class ImageActivity extends ActionBarActivity {
    private List<Point> points = new ArrayList<>();
    private Database database = new Database(this);
    private final static String PREFS = "MYPREFS";
    private String mydebugtag = "MyDebugTag";
    private static int totalPoints = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        boolean passSet = pref.getBoolean(PREFS, false);
        if (!passSet) {
            showDialogAlert();
        }
        setOnTouchListener();
    }

    private void showDialogAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setTitle("Alert");
        builder.setMessage("Set the 4 touch Points as password you have to remember that for next time");
        builder.create().show();
    }

    public void setOnTouchListener() {
        ImageView imgView = (ImageView) findViewById(R.id.imageTouch);
        imgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = Math.round(event.getX());
                int y = Math.round(event.getY());
                Log.d(mydebugtag, String.format("Coordinates x=%d,y=%d", x, y));
                points.add(new Point(x, y));
                if (points.size() == 4) {
                    SharedPreferences prefes = getPreferences(MODE_PRIVATE);
                    boolean a = prefes.getBoolean(PREFS, false);
                    if (!a) {
                        Log.d(mydebugtag, "Saving Passpoints after preferences check...");
                        savePassPointsToDatabase(points);
                    } else {
                        Log.d(mydebugtag, "Verifying Passpoints after preferences check...");
                        verifyPassPoints(points);
                    }
                }
                return false;
            }
        });
    }

    private void verifyPassPoints(final List<Point> pointsCollected) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Checking Passpoints...");
        final AlertDialog alg = builder.create();
        alg.show();
        AsyncTask<Void, Void, Boolean> verifyTask = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                List<Point> savedDbPoints = database.getSavedPoints();
                Log.d(mydebugtag, "Loaded Points=" + savedDbPoints.size());
                if (pointsCollected.size() != totalPoints || savedDbPoints.size() != totalPoints) {
                    return false;
                }
                for (int i = 0; i < totalPoints; i++) {
                    Point savedPoint = savedDbPoints.get(i);
                    Point touchedPoints = pointsCollected.get(i);
                    int xDiff = savedPoint.x - touchedPoints.x;
                    int yDiff = savedPoint.y - touchedPoints.y;
                    int diffSquared = xDiff * xDiff + yDiff * yDiff;
                    if (diffSquared>(40*40)){
                        return false;
                    }
                }


                return true;
            }

            @Override
            protected void onPostExecute(Boolean pass) {
                alg.dismiss();
                points.clear();
                if (pass) {
                    Intent i = new Intent(ImageActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(ImageActivity.this, "Access Denied", Toast.LENGTH_LONG).show();
                }
            }
        };
        verifyTask.execute();
    }

    private void savePassPointsToDatabase(final List<Point> point4) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                database.savePointDatabase(point4);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.d(mydebugtag, "Collected Points Size is :" + points.size());
                points.clear();
                Log.d(mydebugtag, "Points Cleared " + points.size());
                loadSavedPointsFromDatabase();
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(PREFS, true);
                editor.commit();

            }
        };
        task.execute();

    }

    private void loadSavedPointsFromDatabase() {
        List<Point> p = database.getSavedPoints();
        for (Point point : p) {
            Log.d(mydebugtag, "Database Values X=" + point.x + " Y=" + point.y);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
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
