package firebasesample.moosatodo.com.worktodo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Data.Database;
import Model.Task;
import Utils.Dialog;
import Utils.Shared;
import Utils.TaskAdaptor;


public class MainActivity extends AppCompatActivity implements Dialog.DialogInterface, TaskAdaptor.onClickChecked {

    protected LocalService localService;
    private ServiceInterface serviceInterface;
    private String company;
    protected ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            localService = binder.getInstance();
            serviceInterface = (ServiceInterface) service;
            Toast.makeText(MainActivity.this, "Service Connected", Toast.LENGTH_SHORT).show();
            Log.d("---> Company=", "" + company);
            serviceInterface.getCityName(company);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "Service Disconnected", Toast.LENGTH_SHORT).show();

        }
    };
    private ListView taskList;
    private Firebase url;
    private TaskAdaptor adapter;
    private ArrayList<Task> myTask = new ArrayList<>();
    private ArrayList<Task> dbTaskTemp = new ArrayList<>();
    private int addValue = 0;
    private ImageView addTaskButton;
    private TextView companyName;
    private String linkFirst = "https://todolistmoosa.firebaseio.com/";
    private Database database;
    ///////////////////BroadCast///////////////
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            addValues();
        }
    };

    ////////////////BroadcastEnd///////////////////////////
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocalService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(LocalService.broadcastReferencePackageName));

    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(MainActivity.this);
        taskList = (ListView) findViewById(R.id.listOfTaskToDo);
        companyName = (TextView) findViewById(R.id.companyName);
        companyName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setCompanyNameBuilder();
                return false;
            }
        });
        addTaskButton = (ImageView) findViewById(R.id.addImageButton);
        adapter = new TaskAdaptor(dbTaskTemp, MainActivity.this);
        taskList.setAdapter(adapter);
        Shared pref = new Shared(MainActivity.this);
        company = pref.getCompanyName();
        companyName.setText(company);
        database = new Database(this);
        url = new Firebase(linkFirst + company);
        addNewTaskMethod();
        clickList();
        temporarySetDataFromDatabase();
    }


    private void temporarySetDataFromDatabase() {
        List<Task> dbTask = database.getTaskCollection();
        for (Task t : dbTask) {
            dbTaskTemp.add(t);
        }
        adapter.notifyDataSetChanged();
        addValues();
    }

    private void addValues() {
        url.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                myTask.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Task task = dataSnapshot1.getValue(Task.class);
                    myTask.add(task);
                }
                if (addValue == 0) {
                    adapter = new TaskAdaptor(myTask, MainActivity.this);
                    taskList.setAdapter(adapter);
                    addValue++;
                }
                adapter.notifyDataSetChanged();
                database.setTaskCollection(myTask);
                Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(MainActivity.this, "Error:" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clickList() {
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.onListLongClickBuilder(myTask, position, url);
                return false;
            }
        });
    }

    private void addNewTaskMethod() {
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.addTaskMethodDialog(url);
            }
        });
    }

    @Override
    public void onClickCheckbox(final int position, final boolean isChecked) {
        url.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if (i == position) {
                        Firebase childRef = d.getRef();
                        // childRef.removeValue();
                        boolean a = !isChecked;
                        myTask.get(position).setIsCompleted(a);
                        childRef.child("completed").setValue(a);
                        String b = a ? " now Completed" : " set to Not completed";
                        Toast.makeText(MainActivity.this, myTask.get(position).getTaskTitle() + " is " + b, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(MainActivity.this, "Error:" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onDeleteClicked(final int position) {
        Dialog deleteDialog = new Dialog(MainActivity.this);
        deleteDialog.confirmDeleteTask(position, url);
    }

    @Override
    public void setName(String name) {
        companyName.setText(name);
        company = name;
        url = new Firebase(linkFirst + company);
        addValues();
        refresh();
    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    public interface ServiceInterface {
        void getCityName(String name);
    }
}
