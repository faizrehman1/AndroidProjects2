package Utils;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import Model.Task;
import firebasesample.moosatodo.com.worktodo.R;

/*
 * Created by Moosa on 8/7/2015.
 */
public class TaskAdaptor extends BaseAdapter implements ListAdapter {
    List<Task> tasks;
    Context context;
    private onClickChecked onclick;

    public TaskAdaptor(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
        onclick = (onClickChecked) context;
    }

    @Override
    public int getCount() {

        return tasks.size();
    }

    @Override
    public Object getItem(int position) {

        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listviewadaptor, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.textTitleOfTask);
            viewHolder.description = (TextView) convertView.findViewById(R.id.textDescription);
            viewHolder.importanceRating = (RatingBar) convertView.findViewById(R.id.listItemRatingImporatnce);
            viewHolder.isCompleted = (CheckBox) convertView.findViewById(R.id.checkWorkCompleted);
            viewHolder.date = (TextView) convertView.findViewById(R.id.lastDate);
            viewHolder.deleteItem = (ImageView) convertView.findViewById(R.id.deleteItem);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(tasks.get(position).getTaskTitle());
        int a = tasks.get(position).isCompleted() ? viewHolder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : 0;
        viewHolder.title.setPaintFlags(a);
        //////////////////////////////////////////////////////////////////
        viewHolder.date.setText("Last Date: \n" + tasks.get(position).getTaskDate());
        viewHolder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onDeleteClicked(position);
            }
        });
        //////////////////////////////////////////////////////////////////
        viewHolder.isCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onClickCheckbox(position, tasks.get(position).isCompleted());
            }
        });
        viewHolder.description.setText(tasks.get(position).getTaskDescription());
        viewHolder.importanceRating.setProgress(tasks.get(position).getImportanceStarRating() * 2);
        viewHolder.isCompleted.setChecked(tasks.get(position).isCompleted());
        return convertView;
    }

    public interface onClickChecked {
        void onClickCheckbox(int position, boolean checked);

        void onDeleteClicked(int position);
    }


    static class ViewHolder {
        TextView title;
        TextView description;
        RatingBar importanceRating;
        TextView date;
        CheckBox isCompleted;
        ImageView deleteItem;
    }
}