package com.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.DateTimeKeyListener;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_NewTask extends Activity {

	//Class Level Variables
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_task);

		Firebase.setAndroidContext(this);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__new_task, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("SimpleDateFormat")
	public void AddTaskInList(View view)
	{

		EditText ObjtxtMessage;
		ObjtxtMessage = (EditText)findViewById(R.id.txtInputTask);
		if(ObjtxtMessage.getText().toString().equals(""))
		{
			Toast.makeText(getApplicationContext(), "Required : Task Message",Toast.LENGTH_SHORT).show();
			return;
		}

		String strNewTask = ObjtxtMessage.getText().toString();
		String date = PublicVariables.Space+"Task Added On :"+new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
		String Key = "";

		//FIREBASE WORKING
		Firebase ref = new Firebase(PublicVariables.FireBaseURL);
		Firebase newPostRef = ref.push();

		Map<String, String> Task = new HashMap<String, String>();
		Task.put(PublicVariables.FB_Task, strNewTask);
		Task.put(PublicVariables.FB_TaskDesc, date);
		Task.put(PublicVariables.FB_IsCompletedTask, "NO");
		newPostRef.setValue(Task);
		Key =newPostRef.getKey(); 
		//END
		
		//Add New Task In adapter
	//	TaskListViewItem obj = new TaskListViewItem(strNewTask, date, false,Key);
	//	PublicVariables.adapter.AddNewItem(obj);

/*
		//Object of Sahred Pre Task
		prefs= getSharedPreferences(PublicVariables.UnCompleteTaskPreference,0);
		SharedPreferences.Editor PrefEdit = prefs.edit();

		int size=prefs.getInt(PublicVariables.UnCompleteTaskPreference,0) ;
		PrefEdit.putString(PublicVariables.prefTask+size , strNewTask);
		PrefEdit.putString(PublicVariables.prefTaskDesc+size , date);

		PrefEdit.putInt(PublicVariables.UnCompleteTaskPreference, PublicVariables.adapter.getCount());
		PrefEdit.commit();
*/
		ObjtxtMessage.setText("");

	}


	@SuppressLint("ShowToast")
	public void DeleteItem(View view)
	{
		int CheckedItemCount =  PublicVariables.adapter.GetCheckedItemCount();
		if(CheckedItemCount == 0)
		{
			Toast.makeText(getApplicationContext(), "No Task Selected For Delete", Toast.LENGTH_SHORT).show();
			return;
		}

		PublicVariables.adapter.DeleteSelectedTasks();
		if(PublicVariables.adapter.getCount() == 0){
			TextView emptyText;
			emptyText = (TextView)findViewById(android.R.id.empty);
			emptyText.setText(PublicVariables.EmptyText);
		}
		//RefillPrefrence();
	}

	private void RefillPrefrence()
	{
		int itemCount = PublicVariables.adapter.getCount();
		prefs = this.getSharedPreferences(PublicVariables.UnCompleteTaskPreference, Context.MODE_PRIVATE);
		SharedPreferences.Editor prefEdit = prefs.edit();

		prefEdit.clear();
		prefEdit.putInt(PublicVariables.UnCompleteTaskPreference,itemCount);

		for(int i= 0; i < itemCount; i++){

			TaskListViewItem item = PublicVariables.adapter.getTasItemObject(i);
			prefEdit.putString(PublicVariables.prefTask+i , item.getTask());
			prefEdit.putString(PublicVariables.prefTaskDesc+i , item.getTaskDescription());
		}

		prefEdit.commit();
	}

	public void MarkCompleted(View view)
	{
		int CheckedItemCount =  PublicVariables.adapter.GetCheckedItemCount();
		if(CheckedItemCount == 0)
		{
			Toast.makeText(getApplicationContext(), "No Task Selected For Mark As Complete", Toast.LENGTH_SHORT).show();
			return;
		}

		int itemCount =  PublicVariables.adapter.getCount();
		
		/*
		prefs = this.getSharedPreferences(PublicVariables.CompletedTaskPreference, Context.MODE_PRIVATE);
		SharedPreferences.Editor prefEdit = prefs.edit();
		int SizeOfCTask = 0;
		SizeOfCTask = prefs.getInt(PublicVariables.CompletedTaskPreference,0) ;
		prefEdit.putInt(PublicVariables.CompletedTaskPreference,SizeOfCTask+ CheckedItemCount);
		*/
		
		String date = ", Task Completed On :"+new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) +", ";
		int Counter = 0;
		
		for(int i=itemCount-1; i >= 0; i--){
			TaskListViewItem item = PublicVariables.adapter.getTasItemObject(i);
			if( item.isIsChecked() == true ){	
				
				
				String Key = item.getKey();
				Firebase ref = new Firebase(PublicVariables.FireBaseURL+Key);
				Map<String, Object> New = new HashMap<String, Object>();
				New.put(PublicVariables.FB_IsCompletedTask, "YES");
				New.put(PublicVariables.FB_TaskDesc, item.getTaskDescription().trim()+ date);
				ref.updateChildren(New);
				/*
				prefEdit.putString(PublicVariables.prefCompleteTask+(SizeOfCTask+Counter) , item.getTask()); 
				prefEdit.putString(PublicVariables.prefCompleteDesc+(SizeOfCTask+Counter) , item.getTaskDescription().trim() +date);
				*/ 
				PublicVariables.adapter.DeleteItem(item);
				Counter++;

			}
		}
		
		if(PublicVariables.adapter.getCount() == 0){
			TextView emptyText;
			emptyText = (TextView)findViewById(android.R.id.empty);
			emptyText.setText(PublicVariables.EmptyText);
		}
		/*
		prefEdit.commit();
		RefillPrefrence();
		*/
	}

}
