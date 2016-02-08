package com.moosa.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    final List<Message> message = new ArrayList<>();
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setTodoListItems();
        addListItemButton();
        gotoFinishedItems();
        db = new DataBase();
        refreshListItems();
    }

    private void gotoFinishedItems() {
        Button btnToNext = (Button) findViewById(R.id.finishedItemsPage);
        btnToNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FinishedItems.class);
                startActivity(i);
            }
        });

    }

    private void addListItemButton() {
        Button addListButton = (Button) findViewById(R.id.addinglistItems);
        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder msgToAdd = new AlertDialog.Builder(MainActivity.this);
                msgToAdd.setTitle("Adding Work toDo");
                msgToAdd.setNegativeButton("Cancel", null);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialoglayout, null);
                final EditText title = (EditText) view.findViewById(R.id.editTextTitle);
                final EditText msg = (EditText) view.findViewById(R.id.editTextMsg);
                final CheckBox star = (CheckBox) view.findViewById(R.id.starImportantCheckBox);
                msgToAdd.setView(view);
                msgToAdd.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ttl = title.getText().toString();
                        String ms = msg.getText().toString();
                        boolean str = star.isChecked();
                        if (!ttl.equals("")) {
                            message.add(new Message(ms, ttl, str));
                            db.saveDatatoDatabase(message);
                            message.clear();
                            Log.d("Moosa", "Data Added to Database");

                            refreshListItems();
                        } else {
                            Toast.makeText(MainActivity.this, "Title Must Be Filled", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                msgToAdd.create().show();

            }
        });
    }

    public void refreshListItems() {
        Log.d("Moosa", "Invoke Refresh Method");
        AsyncTask<Void,Void,List<Message>> refresh=new AsyncTask<Void, Void, List<Message>>() {
            @Override
            protected List<Message> doInBackground(Void... params) {
              List<Message> msg=db.retrieveDataFromDatabase();
                Log.d("Moosa", "Size is "+msg.size());

                return msg;

            }

            @Override
            protected void onPostExecute(List<Message> messages) {
                Log.d("Moosa", "OnPostExecution Start.......");

                super.onPostExecute(messages);
                final ListView list = (ListView) findViewById(R.id.emailList);
                final MessageAdapter msgadptr = new MessageAdapter(MainActivity.this, messages);
                list.setAdapter(msgadptr);
                Log.d("Moosa", "OnPostExecuted.......");


            }
        };
        refresh.execute();
        Log.d("Moosa", "Refreshing List...");
    }

/*
    private void setTodoListItems() {
        /////////////Retrieve These Messages From SomeWhere////
        List<Message> myPreviousStoredData = ;//db.retrieveDataFromDatabase();
        for (Message m : myPreviousStoredData) {
            String a = m.getTitle();
            String b = m.getSender();
            boolean c = m.isRead();
            message.add(new Message(a, b, c));
        }
        final ListView list = (ListView) findViewById(R.id.emailList);
        final MessageAdapter msgadptr = new MessageAdapter(this, message);
        list.setAdapter(msgadptr);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder removeAlert = new AlertDialog.Builder(MainActivity.this);
                removeAlert.setTitle("Alert");
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View viewInBox = inflater.inflate(R.layout.checkbox, null);
                final CheckBox completed = (CheckBox) viewInBox.findViewById(R.id.moveToCompletedCheckBox);

                removeAlert.setView(viewInBox);
                removeAlert.setMessage("Click Remove to Delete this Item \nOr Move to Completed");
                removeAlert.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        message.remove(position);
                        db.saveDatatoDatabase(message);
                        list.setAdapter(msgadptr);
                    }
                });
                removeAlert.setNeutralButton("Finished", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (completed.isChecked()) {
                            Toast.makeText(MainActivity.this, "Moved to Finished", Toast.LENGTH_LONG).show();
                            DatabaseForFinishedItems db1 = new DatabaseForFinishedItems(MainActivity.this);
                            List<Message> msg = new ArrayList<>();
                            msg.add(message.get(position));
                            db1.saveDatatoFinishedListDatabase(msg);
                            message.remove(position);
                            db.saveDatatoDatabase(message);
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
*/


}
