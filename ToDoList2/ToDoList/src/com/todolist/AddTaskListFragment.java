package com.todolist;

import java.util.ArrayList;
import java.util.List;






import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class AddTaskListFragment extends ListFragment implements OnItemClickListener {

	private List<TaskListViewItem> TaskLists;
	SharedPreferences prefs;
	View rootView;
	private TextView emptyText;
	boolean IsAllRecordUploaded = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.empty_fragment, null, false);
		emptyText = (TextView)rootView.findViewById(android.R.id.empty);

		return rootView;
	}

	@Override
	public void setEmptyText(CharSequence text) {
		emptyText.setText(text);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		/*FOR Shared Prfences
		prefs = this.getActivity().getSharedPreferences(PublicVariables.UnCompleteTaskPreference, Context.MODE_PRIVATE);
		int size=prefs.getInt(PublicVariables.UnCompleteTaskPreference,0);

		for(int j=0;j<size;j++)
		{
			TaskListViewItem task = new TaskListViewItem(prefs.getString(PublicVariables.prefTask+j,""),(prefs.getString(PublicVariables.prefTaskDesc+j,"")),false);   
			TaskLists.add(task);
		}
		 */
		//FireBaseEvents();
		//FireBaseEventsOnDataChanged();
		FireBaseEventsOnDataChangedOneTimeOnly();
		FireBaseEvents();
		getListView().setOnItemClickListener(this);


	}

	private void FireBaseEventsOnDataChangedOneTimeOnly(){


		Firebase ref = new Firebase(PublicVariables.FireBaseURL);

		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {

				TaskLists = new ArrayList<TaskListViewItem>();
				for (DataSnapshot child : snapshot.getChildren()) {
					@SuppressWarnings("unchecked")
					Map<String,String> newPost=(Map<String,String>)child.getValue();

					if(String.valueOf(newPost.get(PublicVariables.FB_IsCompletedTask)).equals("NO")){
						String Key = child.getKey();
						String Task = String.valueOf(newPost.get(PublicVariables.FB_Task));
						String TaskDesc = String.valueOf(newPost.get(PublicVariables.FB_TaskDesc));
						TaskListViewItem task = new TaskListViewItem(Task,TaskDesc,false,Key);   
						TaskLists.add(task);
					}

				}

				PublicVariables.adapter = new CustomAdapterTask(getActivity(), TaskLists);
				setListAdapter(PublicVariables.adapter);

				if(TaskLists.size() == 0){

					setEmptyText(PublicVariables.EmptyText);

				}
				
				IsAllRecordUploaded = true;

			}
			@Override
			public void onCancelled(FirebaseError firebaseError) {
			}
		});
	}

	private void FireBaseEventsOnDataChanged(){
		TaskLists = new ArrayList<TaskListViewItem>();
		Firebase ref = new Firebase(PublicVariables.FireBaseURL);
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				for (DataSnapshot child : snapshot.getChildren()) {

					Map<String,Object> newPost=(Map<String,Object>)child.getValue();
					String Key = child.getKey();
					String Task = String.valueOf(newPost.get(PublicVariables.FB_Task));
					String TaskDesc = String.valueOf(newPost.get(PublicVariables.FB_TaskDesc));
					TaskListViewItem task = new TaskListViewItem(Task,TaskDesc,false,Key);   
					TaskLists.add(task);


				}

				PublicVariables.adapter = new CustomAdapterTask(getActivity(), TaskLists);
				setListAdapter(PublicVariables.adapter);

			}
			@Override
			public void onCancelled(FirebaseError firebaseError) {
				System.out.println("The read failed: " + firebaseError.getMessage());
			}
		});
	}

	private void FireBaseEvents(){

		TaskLists = new ArrayList<TaskListViewItem>();

		Firebase ref = new Firebase(PublicVariables.FireBaseURL);
		ref.addChildEventListener(new ChildEventListener() {

			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
				if(IsAllRecordUploaded == true){
					
					Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
					String Key = snapshot.getKey();
					String Task = String.valueOf(newPost.get(PublicVariables.FB_Task));
					String TaskDesc = String.valueOf(newPost.get(PublicVariables.FB_TaskDesc));
										
					TaskListViewItem obj = new TaskListViewItem(Task, TaskDesc, false,Key);
					PublicVariables.adapter.AddNewItem(obj);
				}


			}


			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildChanged(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub
				
				String Key = arg0.getKey();
				PublicVariables.adapter.DeleteItem(Key);

			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub


		// Toast.makeText(getActivity(), "zzzx", Toast.LENGTH_SHORT)
		// .show();


	}


}
