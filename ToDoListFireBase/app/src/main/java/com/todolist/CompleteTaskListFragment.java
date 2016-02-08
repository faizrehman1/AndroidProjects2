package com.todolist;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CompleteTaskListFragment extends ListFragment implements OnItemClickListener {

	private List<TaskListViewItem> TaskLists;
	SharedPreferences prefs;
	private TextView emptyText;
	boolean IsAllRecordUploaded = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.empty_fragment, null, false);
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

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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

					if(String.valueOf(newPost.get(PublicVariables.FB_IsCompletedTask)).equals("YES")){
						String Key = child.getKey();
						String Task = String.valueOf(newPost.get(PublicVariables.FB_Task));
						String TaskDesc = String.valueOf(newPost.get(PublicVariables.FB_TaskDesc));
						TaskListViewItem task = new TaskListViewItem(Task,TaskDesc,false,Key);   
						TaskLists.add(task);
					}

				}

				PublicVariables.CompleteAdapter = new CustomAdapterTask(getActivity(), TaskLists);
				setListAdapter(PublicVariables.CompleteAdapter);

				if(TaskLists.size() == 0){

					setEmptyText(PublicVariables.EmptyTextComplete);

				}

			}
			@Override
			public void onCancelled(FirebaseError firebaseError) {
			}
		});
	}

	private void FireBaseEvents(){

		TaskLists = new ArrayList<TaskListViewItem>();

		Firebase ref = new Firebase(PublicVariables.FireBaseURL);
		ref.addChildEventListener(new ChildEventListener() {

			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {



			}


			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildChanged(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub

				if(IsAllRecordUploaded == true){

					Map<String,Object> newPost=(Map<String,Object>)arg0.getValue();
					if(String.valueOf(newPost.get(PublicVariables.FB_IsCompletedTask)).equals("YES")){
						String Key = arg0.getKey();
						String Task = String.valueOf(newPost.get(PublicVariables.FB_Task));
						String TaskDesc = String.valueOf(newPost.get(PublicVariables.FB_TaskDesc));

						TaskListViewItem obj = new TaskListViewItem(Task, TaskDesc, false,Key);
						PublicVariables.CompleteAdapter.AddNewItem(obj);
					}
				}

			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub

				String Key = arg0.getKey();
				PublicVariables.CompleteAdapter.DeleteItem(Key);

			}
		});
	}

	/*
	private void Fill(){
		TaskLists = new ArrayList<TaskListViewItem>();

		prefs = this.getActivity().getSharedPreferences(PublicVariables.CompletedTaskPreference, Context.MODE_PRIVATE);
		int size=prefs.getInt(PublicVariables.CompletedTaskPreference,0);

		for(int j=0;j<size;j++)
		{
			TaskListViewItem task = new TaskListViewItem(prefs.getString(PublicVariables.prefCompleteTask+j,""),(prefs.getString(PublicVariables.prefCompleteDesc+j,"")),false);   
			TaskLists.add(task);
		}


		PublicVariables.CompleteAdapter = new CustomAdapterTask(getActivity(), TaskLists);
		setListAdapter(PublicVariables.CompleteAdapter);
		getListView().setOnItemClickListener(this);
	}
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub


	}

	/*

	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        Toast.makeText(getActivity(), "zxzx", Toast.LENGTH_SHORT)
        .show();
    }

	 */


}
