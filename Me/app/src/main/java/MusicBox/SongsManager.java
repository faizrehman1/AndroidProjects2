package MusicBox;

import android.media.MediaMetadataRetriever;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
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
public class SongsManager {
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
    private MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

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
                mediaMetadataRetriever.setDataSource(file.getPath());
                String album = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                String filter = album == null ? "N/A" : album;
                String artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                song.put(Variables.hashMapsrc, file.getPath());
                song.put(Variables.hashMapAlbumTitle, filter);
                String fil = artist == null ? "N/A" : artist;
                song.put(Variables.hashMapAtrist, fil);
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