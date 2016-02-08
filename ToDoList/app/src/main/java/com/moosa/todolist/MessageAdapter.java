package com.moosa.todolist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 //Created by Moosa on 5/6/2015.
 */
public class MessageAdapter extends BaseAdapter implements ListAdapter {
    private List<Message> message;
    private Context context;

    public MessageAdapter(Context context, List<Message> message) {
        this.message = message;
        this.context = context;
    }

    @Override
    public int getCount() {
        return message.size();
    }

    @Override
    public Object getItem(int position) {
        return message.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.layoutadapter, null);
        String title = message.get(position).getTitle();
        String sender = message.get(position).getSender();
        TextView ttl = (TextView) view.findViewById(R.id.title);
        TextView sndr = (TextView) view.findViewById(R.id.sender);
        final ImageView iconView = (ImageView) view.findViewById(R.id.image_icon);
        final boolean isRead = message.get(position).isRead();
        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.get(position).setRead(!message.get(position).isRead());
                boolean a = message.get(position).isRead();
                int iconId = R.drawable.btn_radio_on_disabled_focused_holo_light;
                Log.d("Moosa", "IsREAD is " + a);
                if (a) {
                    iconId = R.drawable.btn_radio_on_focused_holo_light;
                }
                Drawable draw = context.getResources().getDrawable(iconId);
                iconView.setImageDrawable(draw);
                String contextname=context.toString();
               if (contextname.contains("MainActivity")) {
                 //  DataBase db = new DataBase(context);
                   //db.saveDatatoDatabase(message);
               }else {
                   DatabaseForFinishedItems db = new DatabaseForFinishedItems(context);
                   db.saveDatatoFinishedListDatabase(message);
               }

            }
        });
        int iconId = R.drawable.btn_radio_on_disabled_focused_holo_light;
        if (isRead) {
            iconId = R.drawable.btn_radio_on_focused_holo_light;
        }
        Drawable draw = context.getResources().getDrawable(iconId);
        iconView.setImageDrawable(draw);


        ttl.setText(title);
        sndr.setText(sender);
        return view;
    }
}
