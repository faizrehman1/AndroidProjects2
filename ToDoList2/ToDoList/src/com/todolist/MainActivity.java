package com.todolist;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends  TabActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Set Tabs
		SetTabs();
		
		
	}
	
	private void SetTabs()
	{
		TabHost tabHost = getTabHost();	
		
		// Tab for Task
        TabSpec NewTask = tabHost.newTabSpec("Tasks");
        NewTask.setIndicator("Tasks", getResources().getDrawable(R.drawable.ic_launcher));
        Intent tskIntent = new Intent(this, Activity_NewTask.class);
        NewTask.setContent(tskIntent);
        
        
		// Tab for Completed Task
        TabSpec ComNewTask = tabHost.newTabSpec("Completed Tasks");
        ComNewTask.setIndicator("Completed Tasks", getResources().getDrawable(R.drawable.ic_launcher));
        Intent CompeletedTasIntent = new Intent(this, Activity_CompletedTasks.class);
        ComNewTask.setContent(CompeletedTasIntent);
       
        try
        {
          	tabHost.addTab(NewTask); 
        	tabHost.addTab(ComNewTask);
        }
        catch(Exception ex)
        {
        	String ssss = ex.toString();
        	ssss = "";
        	
        }
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
