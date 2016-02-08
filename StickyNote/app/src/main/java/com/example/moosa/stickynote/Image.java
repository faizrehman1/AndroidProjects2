package com.example.moosa.stickynote;

import android.app.AlertDialog;

import android.content.Context;
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


public class Image extends ActionBarActivity {
    List<Point> points = new ArrayList<>();
    private Database db = new Database(this);
    public static final String keyPreferences = "SHARED_KEY";
    private final int totalNumbersOfPoint = 4;
    private final int pointsRelaxation = 40;
    private boolean resetPassPoints=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
       Bundle extra= getIntent().getExtras();
        if (extra!=null) {
           resetPassPoints= extra.getBoolean(MainActivity.intentValue);
        }
        onTouchListener();

    }

    private void onTouchListener() {
        ImageView img = (ImageView) findViewById(R.id.imagemypic);
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = Math.round(event.getX());
                int y = Math.round(event.getY());
                String coords = String.format("Coordinates x=%d & y=%d", x, y);
                Log.d(MainActivity.MyDebugTag, coords);
                points.add(new Point(x, y));

                if (points.size() == 4) {
                    Log.d(MainActivity.MyDebugTag, "Collected Points =" + points.size());
                    SharedPreferences pr = getPreferences(MODE_PRIVATE);
                    boolean a = pr.getBoolean(keyPreferences, false);

                    if (!a  || resetPassPoints) {
                        savingDataToDatabase(points);
                        resetPassPoints=false;
                    } else {
                        verifyingFromDatabaseValues(points);
                    }

                }
                return false;
            }
        });
    }

    private void savingDataToDatabase(final List<Point> p) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Saving Data");
        final AlertDialog alg = alert.create();
        alg.show();
        AsyncTask<Void, Void, Void> savingTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                db.writeData(p);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(keyPreferences, true);
                editor.commit();
                points.clear();
                alg.dismiss();
            }
        };
        savingTask.execute();
    }

    private void verifyingFromDatabaseValues(final List<Point> pointsTouched) {
        final List<Point> pointsFromDataBase = db.getPointsFromDataBase();
        AsyncTask<Void, Void, Boolean> verifyTask = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                if (pointsFromDataBase.size() != totalNumbersOfPoint || pointsTouched.size() != totalNumbersOfPoint) {
                    return false;
                }
                for (int i = 0; i < totalNumbersOfPoint; i++) {
                    int xDiff = pointsFromDataBase.get(i).x - pointsTouched.get(i).x;
                    int yDiff = pointsFromDataBase.get(i).y - pointsTouched.get(i).y;
                    int squaredDiff = xDiff * xDiff + yDiff * yDiff;
                    if (squaredDiff > pointsRelaxation * pointsRelaxation) {
                        return false;
                    }

                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean passChecked) {
                points.clear();
                if (passChecked) {
                    Toast.makeText(Image.this, "Login Successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Image.this, MainActivity.class);
                    startActivity(i);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Image.this);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setMessage("Passpoints Incorrect");
                    builder.create().show();
                }
            }
        };
        verifyTask.execute();

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