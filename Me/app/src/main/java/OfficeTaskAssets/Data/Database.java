package OfficeTaskAssets.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import OfficeTaskAssets.Model.Task;

/*
 * Created by Moosa on 8/19/2015.
 * Dear Maintainer
 * When i wrote this code Only i and God knew What it was.
 * Now only God Knows..!
 * So if you are done trying to optimize this routine and Failed
 * Please increment the following counter as the warning to the next Guy.
 * TOTAL_HOURS_WASTED_HERE=1
 */
public class Database extends SQLiteOpenHelper {
    private String idTag = "ID";
    private String tableName = "COLLECTION";
    private String taskNameTag = "NAME";
    private String taskDescriptionTag = "DEC";
    private String taskDateTag = "TDATE";
    private String taskCompletionTag = "COMP";
    private String taskImportanceTag = "IMP";

    public Database(Context context) {
        super(context, "tasks.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tableName + "(" + idTag + " integer primary key," + taskNameTag + " TEXT,"
                + taskDescriptionTag + " TEXT," + taskDateTag + " TEXT," + taskCompletionTag + " TEXT," + taskImportanceTag + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Task> getTaskCollection() {
        ArrayList<Task> task = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " ORDER BY " + idTag;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String taskNameCS = cursor.getString(1);
            String taskDecCS = cursor.getString(2);
            String taskDateCS = cursor.getString(3);
            String taskCompletionCS = cursor.getString(4);
            int taskImportanceCS = cursor.getInt(5);
            boolean a = taskCompletionCS.contains("1");
            task.add(new Task(taskNameCS, taskDateCS, taskImportanceCS, taskDecCS, a));
        }
        return task;
    }

    public void setTaskCollection(ArrayList<Task> task) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(tableName, null, null);
        ContentValues values = new ContentValues();
        int i = 0;
        for (Task t : task) {
            values.put(idTag, i);
            values.put(taskNameTag, t.getTaskTitle());
            values.put(taskDescriptionTag, t.getTaskDescription());
            values.put(taskDateTag, t.getTaskDate());
            values.put(taskCompletionTag, t.isCompleted());
            values.put(taskImportanceTag, t.getImportanceStarRating());
            db.insert(tableName, null, values);
            i++;
        }
        db.close();
    }
}
