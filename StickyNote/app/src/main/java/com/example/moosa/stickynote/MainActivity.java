package com.example.moosa.stickynote;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {
    public static String DATA = "text.txt";
    public static String intentValue = "INTENTVALUE";
    public static String MyDebugTag = "MYTAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadTextData();
        addSaveButton();

        camButtonListener();
    }

    private void camButtonListener() {
        Button lockBtn = (Button) findViewById(R.id.cambutton);
        lockBtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                           startActivity(i);

                                       }
                                   }

        );
    }

    private void loadTextData() {
        EditText texteditor = (EditText) findViewById(R.id.edittext);
        try {
            FileInputStream fis = openFileInput(DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String lineReader;
            while ((lineReader = br.readLine()) != null) {
                texteditor.append(lineReader);
                texteditor.append("\n");
            }
            fis.close();

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Cant Read The Data From Your Device", Toast.LENGTH_LONG).show();

        }
    }

    private void addSaveButton() {
        Button savebtn = (Button) findViewById(R.id.savebutton);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText texteditor = (EditText) findViewById(R.id.edittext);
                String textbox = String.valueOf(texteditor.getText());
                try {
                    FileOutputStream fos = openFileOutput(DATA, MODE_PRIVATE);
                    fos.write(textbox.getBytes());
                    fos.close();
                    Toast.makeText(MainActivity.this, "File Saved", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Cant Save File", Toast.LENGTH_LONG).show();
                }


            }
        });
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
        if (id == R.id.reset_passpoints) {
            Toast.makeText(MainActivity.this, "Menu Selected", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Image.class);
            i.putExtra(intentValue, true);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
