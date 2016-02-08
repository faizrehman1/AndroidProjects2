package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/*
 * Created by Moosa on 7/26/2015.
 */
public class Database extends SQLiteOpenHelper {
    private String idTag = "ID";
    private String cityNameTag = "NAME";
    private String tableName = "COLLECTION";

    public Database(Context context) {
        super(context, "cities.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tableName + "(" + idTag + " integer primary key," + cityNameTag + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getCityNamesCollection() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + cityNameTag + " FROM " + tableName + " ORDER BY " + idTag;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String cityname = cursor.getString(0);

            list.add(cityname);
        }
        return list;
    }

    public void setCityNamesCollection(ArrayList<String> list) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(tableName, null, null);
        ContentValues values = new ContentValues();
        int i = 0;
        for (String name : list) {
            values.put(idTag, i);
            values.put(cityNameTag, name);
            db.insert(tableName, null, values);
            i++;

        }
        db.close();
    }

}
