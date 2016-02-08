package example.moosa.com.music2ndprogram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Created by Moosa on 6/10/2015.
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
        View view = inflater.inflate(R.layout.plalistsingleitem, null);
        //   MediaStore.Audio.Albums.ALBUM
        TextView title = (TextView) view.findViewById(R.id.titleTextViewInSingleListViewItem);
        ImageView albumart = (ImageView) view.findViewById(R.id.albumArtInListView);
        String fileName = list.get(position).get(Variables.hashMaptitle);
        String a;
        if (fileName.toCharArray().length > 32) {
            a = fileName.substring(0, 30).concat("...");
        } else {
            a = fileName;
        }
        String albumName = list.get(position).get(Variables.hashMapsrc);
        //albumart.setImageResource(R.drawable.playingmusic);
        title.setText(a);
        return view;
    }
}
