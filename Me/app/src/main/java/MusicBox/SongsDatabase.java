package MusicBox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Moosa on 8/29/2015.
 * Dear Maintainer
 * When i wrote this code Only i and God knew What it was.
 * Now only God Knows..!
 * So if you are done trying to optimize this routine and Failed
 * Please increment the following counter as the warning to the next Guy.
 * TOTAL_HOURS_WASTED_HERE=1
 */

public class SongsDatabase extends SQLiteOpenHelper {
    public SongsDatabase(Context context) {
        super(context, "songsData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE %s (%s int primary key,%s TEXT not null,%s TEXT not null,%s TEXT not null,%s TEXT not null)", Variables.TABLENAME, Variables.COL_ID, Variables.COL_NAME, Variables.COL_PATH, Variables.COL_ALBUM, Variables.COL_ARTIST);
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
            values.put(Variables.COL_ALBUM, song.get(Variables.hashMapAlbumTitle));
            values.put(Variables.COL_ARTIST, song.get(Variables.hashMapAtrist));
            id++;
            dbWriter.insert(Variables.TABLENAME, null, values);
        }
    }

    public ArrayList<HashMap<String, String>> getFilesFromDataBase() {
        ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
        SQLiteDatabase dbReader = getReadableDatabase();
        String query = String.format("SELECT %s,%s,%s,%s FROM %s ORDER BY %s", Variables.COL_NAME, Variables.COL_PATH, Variables.COL_ALBUM, Variables.COL_ARTIST, Variables.TABLENAME, Variables.COL_ID);
        Cursor cursor = dbReader.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> song = new HashMap<>();
            song.put(Variables.hashMaptitle, cursor.getString(0));
            song.put(Variables.hashMapsrc, cursor.getString(1));
            song.put(Variables.hashMapAlbumTitle, cursor.getString(2));
            song.put(Variables.hashMapAtrist, cursor.getString(3));

            songsList.add(song);
        }
        return songsList;
    }

}
