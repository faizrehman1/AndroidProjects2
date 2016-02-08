package com.moosa.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
//Created by Moosa on 5/12/2015.
*/
public class DataBase {
    private final String HASHMSGKEY = "items";
    private final String COLTITLE = "title";
    private final String COLMSG = "msg";
    private final String COLSTAR = "STAR";
    private final String ID = "ID";
    private Firebase fb = new Firebase("https://glaring-torch-1710.firebaseio.com/");

    public void saveDatatoDatabase(List<Message> messages) {
        Firebase itemsTodo = fb.child("Incomplete");
        for (Message m : messages) {
            HashMap<String, Message> messageHashMap = new HashMap<>();
            messageHashMap.put(HASHMSGKEY, m);
            itemsTodo.push().setValue(messageHashMap);
        }


    }

    public List<Message> retrieveDataFromDatabase() {
        final List<Message> msg = new ArrayList<>();
        Firebase itemsTodo = fb.child("Incomplete");
        itemsTodo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    for (DataSnapshot child : children.getChildren()) {
                        Map<String, Object> mapMsg = (Map<String, Object>) child.getValue();
                        boolean isRead = (Boolean) mapMsg.get("read");
                        String message = (String) mapMsg.get("sender");
                        String title = (String) mapMsg.get("title");
                        Log.d("Moosa", "Star Boolean is=" + isRead + " String is " + title + message);
                        msg.add(new Message(title, message, isRead));
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return msg;
    }
}
