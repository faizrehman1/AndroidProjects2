package com.moosa.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class FinishedItems extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_items);
        loadFinishedItemsFromDatabase();
        backButtonToMAin();
    }

    private void backButtonToMAin() {
        Button backbtn = (Button) findViewById(R.id.backtodoItems);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FinishedItems.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private final List<Message> messagefinished = new ArrayList<>();
    private final DatabaseForFinishedItems db = new DatabaseForFinishedItems(FinishedItems.this);

    private void loadFinishedItemsFromDatabase() {
        /////////////Retrieve These Messages From SomeWhere////
        List<Message> myPreviousStoredData = db.retrieveDataFromFinishedListDatabase();
        for (Message m : myPreviousStoredData) {
            String a = m.getTitle();
            String b = m.getSender();
            boolean c = m.isRead();
            messagefinished.add(new Message(a, b, c));
        }
        final ListView list = (ListView) findViewById(R.id.finishedtodolist);
        final MessageAdapter msgadptr = new MessageAdapter(this, messagefinished);
        list.setAdapter(msgadptr);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder removeAlert = new AlertDialog.Builder(FinishedItems.this);
                removeAlert.setTitle("Alert");
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View viewInBox = inflater.inflate(R.layout.checkbox, null);
                final CheckBox completed = (CheckBox) viewInBox.findViewById(R.id.moveToCompletedCheckBox);
                completed.setText("Move to Undone");
                removeAlert.setView(viewInBox);
                removeAlert.setMessage("Click Remove to Delete this Item\n Or Move to Undone");
                removeAlert.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        messagefinished.remove(position);
                        db.saveDatatoFinishedListDatabase(messagefinished);
                        list.setAdapter(msgadptr);
                    }
                });
                removeAlert.setNeutralButton("Undone", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (completed.isChecked()) {
                            Toast.makeText(FinishedItems.this, "Moved to Undone", Toast.LENGTH_LONG).show();
                           // DataBase db1 = new DataBase(FinishedItems.this);
                            List<Message> msg = new ArrayList<>();
                            msg.add(messagefinished.get(position));
                           // db1.saveDatatoDatabase(msg);
                            messagefinished.remove(position);
                            db.saveDatatoFinishedListDatabase(messagefinished);
                            list.setAdapter(msgadptr);
                        }
                    }
                });

                removeAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                removeAlert.create().show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //   getMenuInflater().inflate(R.menu.menu_finished_items, menu);
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

    @Override
    protected void onPause() {
       // finish();
        super.onPause();
    }
}
