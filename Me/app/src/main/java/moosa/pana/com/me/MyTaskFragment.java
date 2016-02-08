package moosa.pana.com.me;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import OfficeTaskAssets.Data.Database;
import OfficeTaskAssets.Model.Task;
import OfficeTaskAssets.Utils.Dialog;
import OfficeTaskAssets.Utils.TaskAdaptor;


public class MyTaskFragment extends Fragment implements Dialog.DialogInterface, TaskAdaptor.onClickChecked {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String userId;
    private ListView taskList;
    private TaskAdaptorIn adapter;
    private ArrayList<Task> myTask = new ArrayList<>();
    private ArrayList<Task> dbTaskTemp = new ArrayList<>();
    private int addValue = 0;
    private ImageView addTaskButton;
    private TextView companyName;
    private String linkFirst = "https://mepanacloud.firebaseio.com/users";
    private Firebase url;

    private Database database;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;


    public MyTaskFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
                    adapter = new TaskAdaptorIn(myTask, getActivity());
                    taskList.setAdapter(adapter);
                    addValue++;
                    clickList();
                }
                refresh();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getActivity(), "Error:" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clickList() {
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Dialog dialog = new Dialog(getActivity(), MyTaskFragment.this);
                dialog.onListLongClickBuilder(myTask, position, url);
                return false;
            }
        });
    }

    private void addNewTaskMethod() {
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity(), MyTaskFragment.this);
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
                        Toast.makeText(getActivity(), myTask.get(position).getTaskTitle() + " is " + b, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getActivity(), "Error:" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onDeleteClicked(final int position) {
        Dialog deleteDialog = new Dialog(getActivity(), MyTaskFragment.this);
        deleteDialog.confirmDeleteTask(position, url);
    }

    /*
        @Override
        public void setName(String name) {
            companyName.setText(name);
            userId = name;
            url = new Firebase(linkFirst);
            addValues();
            refresh();
        }
    */
    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_task, container, false);
        url = new Firebase(linkFirst);

        url.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    //  userId = authData.getUid();
                    Log.d("Data Authentication->", "User Id is " + authData.getUid());
                    userId = authData.getUid();

                    url = new Firebase(linkFirst + "/" + userId + "/usertask");
                    Log.d("New URL is->", "Url is " + url.getPath().toString());
                    addValues();
                }
                //else {
                  //  new MainActivity().logout();
               // }

            }
        });
        taskList = (ListView) view.findViewById(R.id.listOfTaskToDo);
        companyName = (TextView) view.findViewById(R.id.companyName);
        addTaskButton = (ImageView) view.findViewById(R.id.addImageButton);
        adapter = new TaskAdaptorIn(dbTaskTemp, getActivity().getApplicationContext());
        taskList.setAdapter(adapter);


        addNewTaskMethod();
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface ServiceInterface {
        void getCityName(String name);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    ////////////////////ADAPTOR////////////////////////////////
    class TaskAdaptorIn extends BaseAdapter implements ListAdapter {
        List<Task> tasks;
        Context context;

        public TaskAdaptorIn(List<Task> tasks, Context context) {
            this.tasks = tasks;
            this.context = context;
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
                    //  onclick.
                    onDeleteClicked(position);
                }
            });
            //////////////////////////////////////////////////////////////////
            viewHolder.isCompleted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  onclick.
                    onClickCheckbox(position, tasks.get(position).isCompleted());
                }
            });
            viewHolder.description.setText(tasks.get(position).getTaskDescription());
            viewHolder.importanceRating.setProgress(tasks.get(position).getImportanceStarRating() * 2);
            viewHolder.isCompleted.setChecked(tasks.get(position).isCompleted());
            return convertView;
        }
/*
        public interface onClickChecked {
            void onClickCheckbox(int position, boolean checked);

            void onDeleteClicked(int position);
        }
*/

        class ViewHolder {
            TextView title;
            TextView description;
            RatingBar importanceRating;
            TextView date;
            CheckBox isCompleted;
            ImageView deleteItem;
        }
    }

}
