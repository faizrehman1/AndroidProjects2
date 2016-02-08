package example.moosa.com.music2ndprogram;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Moosa on 6/7/2015.
 */
public class SongsManager {
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getPlaylist() {
        File home = Environment.getExternalStorageDirectory();
        return getPlaylist(home);
    }

    public ArrayList<HashMap<String, String>> getPlaylist(File root) {
        ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
        if (root.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file : root.listFiles(new FileExtensionFilter())) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put(Variables.hashMaptitle, file.getName().substring(0, (file.getName().length() - 4)));
                Log.d("SONG NAME", "---> " + file.getName());
                song.put(Variables.hashMapsrc, file.getPath());
                Log.d("SONG Path", "---> " + file.getPath());

                // Adding each song to SongList
                songsList.add(song);
            }
        }
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                songsList.addAll(getPlaylist(file));
            }
        }
        return songsList;
    }

    class FileExtensionFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String filename) {
            return (filename.endsWith(".mp3") || filename.endsWith(".MP3"));
        }
    }
}