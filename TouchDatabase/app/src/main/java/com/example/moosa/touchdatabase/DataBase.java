package com.example.moosa.touchdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moosa on 4/30/2015.
 */
public class DataBase extends SQLiteOpenHelper {
    private static final String Tablename = "touchtable";
    private static final String ID = "ID";
    private static final String Xcol = "COL_X";
    private static final String Ycol = "COL_Y";


    public DataBase(Context context) {
        super(context, "touch.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s (%s int primary key,%s int not null,%s int not null)", Tablename, ID, Xcol, Ycol);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveDataToDatabase(List<Point> points) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Tablename, null, null);
        int i = 0;
        for (Point p : points) {
            ContentValues values = new ContentValues();
            values.put(ID, i);
            values.put(Xcol, p.x);
            values.put(Ycol, p.y);
            db.insert(Tablename, null, values);
            i++;
        }
        db.close();
    }
    public List<Point> pointsFromDatabase(){

      List<Point> points=new ArrayList<>();
       SQLiteDatabase db=getReadableDatabase();

        String sqlRetrieve=String.format("select %s,%s from %s order by %s",Xcol,Ycol,Tablename,ID);
        Cursor cs=db.rawQuery(sqlRetrieve, null);
        while (cs.moveToNext()){
            int x=cs.getInt(0);
            int y=cs.getInt(1);
            points.add(new Point(x,y));
        }
        Log.d(MainActivity.MyTAG, "Method Returns");
        return points;
    }
}
