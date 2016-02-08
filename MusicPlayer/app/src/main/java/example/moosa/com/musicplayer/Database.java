package example.moosa.com.musicplayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Created by Moosa on 6/13/2015.
 */
public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "songsData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE %s (%s int primary key,%s TEXT not null,%s TEXT not null)", Variables.TABLENAME, Variables.COL_ID, Variables.COL_NAME, Variables.COL_PATH);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveFilesToDataBase(ArrayList<HashMap<String, String>> songsList) {
        SQLiteDatabase dbWriter = getWritableDatabase();
        dbWriter.delete(Variables.TABLENAME, null, null);
        int id = 0;
        for (HashMap<String, String> song : songsList) {
            ContentValues values = new ContentValues();
            values.put(Variables.COL_ID, id);
            values.put(Variables.COL_NAME, song.get(Variables.hashMaptitle));
            values.put(Variables.COL_PATH, song.get(Variables.hashMapsrc));
            id++;
            dbWriter.insert(Variables.TABLENAME, null, values);
        }
    }

    public ArrayList<HashMap<String, String>> getFilesFromDataBase() {
        ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
        SQLiteDatabase dbReader = getReadableDatabase();
        String query = String.format("SELECT %s,%s FROM %s ORDER BY %s", Variables.COL_NAME, Variables.COL_PATH, Variables.TABLENAME, Variables.COL_ID);
        Cursor cursor = dbReader.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> song = new HashMap<>();
            song.put(Variables.hashMaptitle, cursor.getString(0));
            song.put(Variables.hashMapsrc, cursor.getString(1));
            songsList.add(song);
        }
        return songsList;
    }

}
