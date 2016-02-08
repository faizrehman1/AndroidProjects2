package com.moosa.firebase;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class FireMain extends ActionBarActivity {
    EditText titlebar = (EditText) findViewById(R.id.edittextsendtitle);
    EditText textbar = (EditText) findViewById(R.id.edittextsenddata);
    TextView textView = (TextView) findViewById(R.id.edittextviewsection);
    Firebase fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_main);
        buttonListener();
        fb = new Firebase("https://glaring-torch-1710.firebaseio.com/").child("chat");
    }

    private void buttonListener() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                Button btn = (Button) findViewById(R.id.buttonsenddata);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fb.child(titlebar.toString()).setValue(textbar.toString());
                        Toast.makeText(FireMain.this, "Data Sent to the DataBase", Toast.LENGTH_LONG).show();
                        Log.d("Moosa", "Button Clicked to send data");

                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                refreshTextView();
            }
        };
task.execute();

    }

    private void refreshTextView() {
        try {

            fb.child(titlebar.toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    textView.setText(textView.getText() + "\n" + dataSnapshot.toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(FireMain.this, "Unable to Get Text From Internet==> \n" + firebaseError.toString(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            Log.d("ExcAt Refresh Method", "Errrorrrr");
        }
    }


}
