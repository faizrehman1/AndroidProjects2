package com.todolist;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.firebase.client.Firebase;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapterTask extends BaseAdapter {

	Context context;
	List<TaskListViewItem> TaskItem;

	CustomAdapterTask(Context context, List<TaskListViewItem> TaskItem) {
		this.context = context;
		this.TaskItem = TaskItem;

	}

	public void AddNewItem(TaskListViewItem taskItem){
		TaskItem.add(taskItem);
		this.notifyDataSetChanged();
	}

	public void DeleteItem(TaskListViewItem taskItem){
		TaskItem.remove(taskItem);
		this.notifyDataSetChanged();
	}

	public void DeleteItem(String Key){

		for (Iterator<TaskListViewItem> iter = TaskItem.listIterator(); iter.hasNext(); ) {

			TaskListViewItem item =iter.next(); 
			if(item.getKey().equals(Key)){

				iter.remove();
			}
		}

		this.notifyDataSetChanged();
	}

	public void MarkCheckItem(int position,boolean CheckValue){
		TaskListViewItem singleItem =  (TaskListViewItem) getItem(position);
		if(singleItem != null){
			singleItem.setIsChecked(CheckValue);
			TaskItem.set(position, singleItem);

		}
	}

	@Override
	public int getCount() {

		return TaskItem.size();
	}

	@Override
	public Object getItem(int position) {

		return TaskItem.get(position);
	}


	public TaskListViewItem getTasItemObject(int position) {

		return TaskItem.get(position);
	}


	@Override
	public long getItemId(int position) {

		return TaskItem.indexOf(getItem(position));
	}

	public int getItemIdByObj(TaskListViewItem Obj) {

		return TaskItem.indexOf(Obj);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_template, null);

			CheckBox ChkBx = (CheckBox)convertView.findViewById( R.id.chk_ListTemplate_Task );
			ChkBx.setOnClickListener( new View.OnClickListener() {  
				public void onClick(View v) {  
					CheckBox cb = (CheckBox) v ;  

					int Tag =  ((Integer) cb.getTag()).intValue();
					MarkCheckItem(Tag,cb.isChecked());


				}  
			});  

		}

		if ((position & 1) == 0) {
			//convertView.setBackgroundColor(Color.LTGRAY);
			convertView.setBackgroundColor(Color.parseColor("#D1FDFF"));
		}
		else {
			convertView.setBackgroundColor(Color.parseColor("#FFEDD9"));
		}


		TextView txtTask = (TextView) convertView.findViewById(R.id.txtInputTask);
		TextView txtTaskDesc = (TextView) convertView.findViewById(R.id.txt_ListTemplate_TaskDesc);
		CheckBox ChkBx = (CheckBox)convertView.findViewById( R.id.chk_ListTemplate_Task );

		TaskListViewItem row_pos = TaskItem.get(position);


		txtTask.setText(row_pos.getTask());
		txtTask.setTag(row_pos.getKey());
		txtTaskDesc.setText(row_pos.getTaskDescription());
		ChkBx.setChecked(row_pos.isIsChecked());
		ChkBx.setTag(position);
		ChkBx.refreshDrawableState();

		return convertView;

	}

	public int GetCheckedItemCount(){

		int Count = 0;
		for (TaskListViewItem item : TaskItem) {
			if (item.isIsChecked() == true) {
				Count++;
			}
		}
		return Count;
	}


	public void DeleteSelectedTasks(){

		for (Iterator<TaskListViewItem> iter = TaskItem.listIterator(); iter.hasNext(); ) {

			TaskListViewItem item =iter.next(); 
			if (item.isIsChecked() == true) {

				FB_Delete(item.getKey());
				//iter.remove();
			}
		}

		this.notifyDataSetChanged();


	}

	public void FB_Delete(String Key){

		if(Key.equals("")){
			return;
		}

		Firebase ref = new Firebase(PublicVariables.FireBaseURL+Key);
		ref.removeValue();
	}

}

