package com.todolist;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_CompletedTasks extends Activity {

	//Class Level Variables
	SharedPreferences prefs;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_completed_tasks);
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__completed_tasks, menu);
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
	
	
	public void DeleteCompletedTasks(View View)
	{
		/*ListView ObjlstView;
		ObjlstView = (ListView)findViewById(R.id.CompleteListFragment);
		
		 SparseBooleanArray checkedItemPositions = ObjlstView.getCheckedItemPositions();
		 int CheckedItemCount =  checkedItemPositions.size();
		 if(CheckedItemCount == 0)
		 {
			 Toast.makeText(getApplicationContext(), "No Task Selected For Delete", Toast.LENGTH_SHORT).show();
			 return;
		 }
		 
		 int DeleteItemCount = ObjlstView.getCount();
              

         for(int i=DeleteItemCount-1; i >= 0; i--){
             if(checkedItemPositions.get(i)){
            	 CompleteTaskArrayAdp.remove(CompleteTaskList.get(i));
            	 
        	   }
         }
         checkedItemPositions.clear();
         CompleteTaskArrayAdp.notifyDataSetChanged();
         
        
         RefillSharedPrefrences();*/
		
		 int CheckedItemCount =  PublicVariables.CompleteAdapter.GetCheckedItemCount();
		 if(CheckedItemCount == 0)
		 {
			 Toast.makeText(getApplicationContext(), "No Task Selected For Delete", Toast.LENGTH_SHORT).show();
			 return;
		 }
		 
		             
		 PublicVariables.CompleteAdapter.DeleteSelectedTasks();
		 if(PublicVariables.CompleteAdapter.getCount() == 0){
				TextView emptyText;
				emptyText = (TextView)findViewById(android.R.id.empty);
				emptyText.setText(PublicVariables.EmptyText);
			}
        
        // RefillPrefrence();
		
	}
	
	private void RefillPrefrence()
	{
		
			
		int itemCount = PublicVariables.CompleteAdapter.getCount();
		prefs = this.getSharedPreferences(PublicVariables.CompletedTaskPreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefs.edit();
        
        prefEdit.clear();
        prefEdit.putInt(PublicVariables.CompletedTaskPreference,itemCount);
       
        for(int i= 0; i < itemCount; i++){
        	
        	TaskListViewItem item = PublicVariables.CompleteAdapter.getTasItemObject(i);
        	prefEdit.putString(PublicVariables.prefCompleteTask+i , item.getTask());
        	prefEdit.putString(PublicVariables.prefCompleteDesc+i , item.getTaskDescription());
        }
        
        prefEdit.commit();
        
	}
	
	
}
