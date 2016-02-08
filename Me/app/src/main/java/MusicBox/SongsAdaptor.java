package MusicBox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import moosa.pana.com.me.R;

/**
 * Created by Moosa on 8/29/2015.
 * Dear Maintainer
 * When i wrote this code Only i and God knew What it was.
 * Now only God Knows..!
 * So if you are done trying to optimize this routine and Failed
 * Please increment the following counter as the warning to the next Guy.
 * TOTAL_HOURS_WASTED_HERE=1
 */
public class SongsAdaptor extends BaseAdapter implements ListAdapter {
    private ArrayList<HashMap<String, String>> list = new ArrayList<>();
    private Context context;

    public SongsAdaptor(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listitem, null);
        //   MediaStore.Audio.Albums.ALBUM

        TextView title = (TextView) view.findViewById(R.id.songTitle);
        TextView album = (TextView) view.findViewById(R.id.textViewAlbum);
        TextView artist = (TextView) view.findViewById(R.id.textViewArtist);


        String fileName = list.get(position).get(Variables.hashMaptitle);
        String albumName ="Album: "+ list.get(position).get(Variables.hashMapAlbumTitle);
        String artistName ="Artist: " +list.get(position).get(Variables.hashMapAtrist);


        title.setText(fileName);
        album.setText(albumName);
        artist.setText(artistName);
        return view;
    }
}
