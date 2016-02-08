package example.moosa.com.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Created by Moosa on 6/10/2015.
 */
public class SongsAdaptor extends BaseAdapter implements ListAdapter {
   private ArrayList<HashMap<String,String>> list=new ArrayList<>();
    private Context context;
    public SongsAdaptor(Context context,ArrayList<HashMap<String,String>> list){
        this.context=context;
        this.list=list;
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
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.listitem,null);
     //   MediaStore.Audio.Albums.ALBUM
        TextView title=(TextView)view.findViewById(R.id.songTitle);
        String fileName=list.get(position).get(Variables.hashMaptitle);
       String albumName=list.get(position).get(Variables.hashMapsrc);

        title.setText(fileName);
        return view;
    }
}
