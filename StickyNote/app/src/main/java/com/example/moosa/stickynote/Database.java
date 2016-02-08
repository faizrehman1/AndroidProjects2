package com.example.moosa.stickynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moosa on 4/30/2015.
 */
public class Database extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "POINTS";
    private static final String COL_ID = "ID";
    private static final String COL_X = "X";
    private static final String COL_Y = "Y";

    public Database(Context context) {
        super(context, "note.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s (%s int primary key ,%s int not null ,%s int not null)", TABLE_NAME, COL_ID, COL_X, COL_Y);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void writeData(List<Point> points) {
     SQLiteDatabase db=getWritableDatabase();
        int i=0;
        db.delete(TABLE_NAME,null,null);
        for (Point point:points){
            ContentValues values=new ContentValues();
            values.put(COL_ID,i);
            values.put(COL_X,point.x);
            values.put(COL_Y,point.y);
            db.insert(TABLE_NAME,null,values);
            i++;
        }
        db.close();
    }
    public List<Point> getPointsFromDataBase(){
        List<Point> points=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String retrieveQuery=String.format("select %s,%s from %s order by %s",COL_X,COL_Y,TABLE_NAME,COL_ID);
        Cursor cursor=db.rawQuery(retrieveQuery, null);
        while (cursor.moveToNext()){
          int x=  cursor.getInt(0);
          int y=  cursor.getInt(1);
          points.add(new Point(x,y));
        }
        return points;
    }
}
