package task.lab.sylani.com.listfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Moosa on 7/12/2015.
 */
public class ItemAdaptor extends BaseAdapter {
    private Context context;
    private ArrayList<Item> list;

    public ItemAdaptor(Context context, ArrayList<Item> list) {
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
        View view = inflater.inflate(R.layout.listitemsingleview, null);

        return view;
    }
}
