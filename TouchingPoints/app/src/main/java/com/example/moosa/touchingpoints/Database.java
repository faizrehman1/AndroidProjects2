package com.example.moosa.touchingpoints;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moosa on 5/3/2015.
 */
public class Database extends SQLiteOpenHelper {
    private String TABLE = "TOUCH_TABLE";
    private String ID = "COL_ID";
    private String X = "COL_X";
    private String Y = "COL_Y";


    public Database(Context context) {
        super(context, "touch.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s (%s int primary key,%s int not null,%S int not null)", TABLE, ID, X, Y);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void savePointDatabase(List<Point> point) {
        SQLiteDatabase write = getWritableDatabase();
        write.delete(TABLE, null, null);
        ContentValues values = new ContentValues();
        int i = 0;
        for (Point p : point) {
            values.put(ID, i);
            values.put(X, p.x);
            values.put(Y, p.y);
            write.insert(TABLE, null, values);
            i++;
        }
        write.close();
    }

    public List<Point> getSavedPoints() {
        List<Point> points = new ArrayList<>();
        SQLiteDatabase read = getReadableDatabase();
        String query = String.format("select %s,%s from %s order by %s", X, Y, TABLE, ID);
        Cursor cs = read.rawQuery(query, null);
        while (cs.moveToNext()) {
            int x = cs.getInt(0);
            int y = cs.getInt(1);
            points.add(new Point(x, y));
        }
        return points;
    }
}
