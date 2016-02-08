package com.todolist;

public class TaskListViewItem {
    public String getTask() {
		return Task;
	}

	public void setTask(String task) {
		Task = task;
	}

	public String getTaskDescription() {
		return TaskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		TaskDescription = taskDescription;
	}

	public boolean isIsChecked() {
		return IsChecked;
	}

	public void setIsChecked(boolean isChecked) {
		IsChecked = isChecked;
	}

	private  String Task;        
    private  String TaskDescription;  
    private boolean IsChecked;  
    private String Key ;
    
    public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	/*public TaskListViewItem( String title, String description, boolean IsChecked) {
      
        this.Task = title;
        this.TaskDescription = description;
        this.IsChecked = IsChecked;
    }*/
	
	public TaskListViewItem( String title, String description, boolean IsChecked, String Key) {
	      
        this.Task = title;
        this.TaskDescription = description;
        this.IsChecked = IsChecked;
        this.Key = Key;
    }
    
   
}