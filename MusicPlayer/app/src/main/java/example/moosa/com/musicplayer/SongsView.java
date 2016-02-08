package example.moosa.com.musicplayer;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


public class SongsView extends ActionBarActivity {
    public ArrayList<HashMap<String, String>> songList = new ArrayList<>();
    Database database;
    SongsAdaptor songsAdaptor;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_view);
        database = new Database(getApplicationContext());
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean dataCheck = sharedPreferences.getBoolean(Variables.SHARED_CHK_DB, false);
        if (dataCheck) {

            ArrayList<HashMap<String, String>> songsListData = new ArrayList<>();
            this.songList = database.getFilesFromDataBase();//songsManager.getPlaylist();
            for (int i = 0; i < songList.size(); i++) {
                HashMap<String, String> singleSong = songList.get(i);
                songsListData.add(singleSong);

            }
            refreshView(songsListData);

        } else {
            refreshPlaylist();
        }
    }

    private void refreshView(ArrayList<HashMap<String, String>> viewList) {
        listView = (ListView) findViewById(R.id.list);
        songsAdaptor = new SongsAdaptor(this, viewList);
        listView.setAdapter(songsAdaptor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), Player.class);
                i.putExtra(Variables.SONGINDEX, position);
                setResult(100, i);
                finish();
            }
        });
    }

    private void refreshPlaylist() {


        AsyncTask<Void, Void, AlertDialog> ref = new AsyncTask<Void, Void, AlertDialog>() {
            @Override
            protected AlertDialog doInBackground(Void... params) {
               // AlertDialog.Builder builder = new AlertDialog.Builder(SongsView.this);
                //builder.setTitle("Alert");
                //builder.setMessage("Loading Data Please Wait...");
                //AlertDialog dialog = builder.create();
               // dialog.show();
                SongsManager songsManager = new SongsManager();
                ArrayList<HashMap<String, String>> songsListData = new ArrayList<>();
                songList = songsManager.getPlaylist();
                for (int i = 0; i < songList.size(); i++) {
                    HashMap<String, String> singleSong = songList.get(i);
                    songsListData.add(singleSong);
                }
                database.saveFilesToDataBase(songsListData);
                refreshView(songsListData);

                return null;
            }

            @Override
            protected void onPostExecute(AlertDialog aVoid) {
                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Variables.SHARED_CHK_DB, true);
                editor.commit();
                Utilities.toast(getApplicationContext(), "Files Added to the List");
                //aVoid.dismiss();
                super.onPostExecute(aVoid);
            }
        };
        ref.execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = new MenuInflater(getApplicationContext());
        inflator.inflate(R.menu.menu_songs_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int a = item.getItemId();
        switch (a) {
            case R.id.action_settings:
                refreshPlaylist();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
