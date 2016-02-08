package com.moosa.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moosa on 5/12/2015.
 */
public class DatabaseForFinishedItems extends SQLiteOpenHelper {
    private final String TABLENAME = "todotable";
    private final String COLTITLE = "title";
    private final String COLMSG = "msg";
    private final String COLSTAR = "STAR";
    private final String ID = "ID";

    public DatabaseForFinishedItems(Context context) {
        super(context, "todolistItemsFinished.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = String.format("create table %s (%s int primary key,%s TEXT not null,%s TEXT,%s TEXT)", TABLENAME, ID, COLTITLE, COLMSG, COLSTAR);
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveDatatoFinishedListDatabase(List<Message> messages) {
        SQLiteDatabase db = getWritableDatabase();
        int i = 0;
        if (messages.size() > 1) {
            db.delete(TABLENAME, null, null);
            i = 0;
        } else {
            SQLiteDatabase dbcount = getReadableDatabase();
            String getQuery = String.format("select %s from %s", ID, TABLENAME);
            Cursor cs = dbcount.rawQuery(getQuery, null);
            while (cs.moveToNext()) {
                i = cs.getInt(0) + 1;
            }
        }
        for (Message m : messages) {
            ContentValues values = new ContentValues();
            values.put(ID, i);
            values.put(COLTITLE, m.getTitle());
            values.put(COLMSG, m.getSender());
            Log.d("Moosa", "While saving Boolean in Database Class isRead =" + m.isRead());

            values.put(COLSTAR, m.isRead());
            db.insert(TABLENAME, null, values);
            i++;
        }
        db.close();
    }

    public List<Message> retrieveDataFromFinishedListDatabase() {
        List<Message> msg = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String getQuery = String.format("select %s,%s,%s from %s order by %s", COLTITLE, COLMSG, COLSTAR, TABLENAME, ID);
        Cursor cs = db.rawQuery(getQuery, null);
        while (cs.moveToNext()) {
            String title = cs.getString(0);
            String mg = cs.getString(1);
            boolean star = false;
            if (cs.getString(2).equals("1")) {
                star = true;
            }
            Log.d("Moosa", "Star Boolean in Database Class is=" + star + " but String is " + cs.getString(2));
            msg.add(new Message(title, mg, star));
        }


        return msg;
    }
}


