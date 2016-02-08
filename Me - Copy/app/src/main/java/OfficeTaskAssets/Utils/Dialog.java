package OfficeTaskAssets.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

import moosa.pana.com.me.MyTaskFragment;
import moosa.pana.com.me.R;
import OfficeTaskAssets.Model.Task;

/*
 * Created by Moosa on 8/12/2015.
 * Dear Maintainer
* When i wrote this code Only i and God knew What it was.
* Now only God Knows..!
* So if you are done trying to optimize this routine and Failed
* Please increment the following counter as the warning to the next Guy.
* TOTAL_HOURS_WASTED_HERE=10
 */
public class Dialog {
    Activity activity;
    AlertDialog.Builder builder;
    DialogInterface ref;

    public Dialog(Activity activity, MyTaskFragment myTaskFragment) {
        this.activity = activity;
        builder = new AlertDialog.Builder(activity);
        ref = (DialogInterface) myTaskFragment;


    }

    public void confirmDeleteTask(final int position, final Firebase url) {
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.sureDeleteTask);
        builder.setNegativeButton(R.string.no, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.yes, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                url.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i = 0;

                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            if (i == position) {
                                Firebase childRef = d.getRef();
                                childRef.removeValue();
                                Toast.makeText(activity, R.string.taskDeleted, Toast.LENGTH_SHORT).show();
                            }
                            i++;
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Toast.makeText(activity, activity.getString(R.string.error) + firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
        builder.show();
    }

    public void addTaskMethodDialog(final Firebase url) {
        builder.setTitle(R.string.addTask);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialoglayout, null);
        final EditText titleText = (EditText) view.findViewById(R.id.dialogTitleText);
        final EditText descriptionText = (EditText) view.findViewById(R.id.dialogDescriptionText);
        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        datePicker.setCalendarViewShown(false);
        final String[] date = new String[1];
        String a = datePicker.getMonth() < 9 ? "0" : "";
        String b = datePicker.getDayOfMonth() < 10 ? "0" : "";


        date[0] = b + datePicker.getDayOfMonth() + "-" + a + (datePicker.getMonth() + 1) + "-" + datePicker.getYear();

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String a = monthOfYear < 9 ? "0" : "";
                String b = dayOfMonth < 10 ? "0" : "";

                date[0] = b + dayOfMonth + "-" + a + (monthOfYear + 1) + "-" + year;
            }
        });
        builder.setView(view);
        builder.setPositiveButton(R.string.add, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                if (!titleText.getText().toString().equals("")) {

                    url.push().setValue(new Task(titleText.getText().toString(), date[0], (int) ratingBar.getRating(), descriptionText.getText().toString(), false));
                    ref.refresh();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

    public void onListLongClickBuilder(List<Task> myTask, final int position, final Firebase url) {

        builder.setTitle(R.string.editTask);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View view2 = inflater.inflate(R.layout.dialoglayout, null);
        final EditText titleText = (EditText) view2.findViewById(R.id.dialogTitleText);
        final EditText descriptionText = (EditText) view2.findViewById(R.id.dialogDescriptionText);
        final RatingBar ratingBar = (RatingBar) view2.findViewById(R.id.ratingBar);
        final DatePicker datePicker = (DatePicker) view2.findViewById(R.id.datePicker);
        datePicker.setCalendarViewShown(false);
        final String[] date = new String[1];

        String dateCut = myTask.get(position).getTaskDate();
        date[0] = dateCut;//datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getYear();
        datePicker.init(Integer.valueOf(dateCut.substring(6, 10)), (Integer.valueOf(dateCut.substring(3, 5)) - 1), Integer.valueOf(dateCut.substring(0, 2)), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String a = monthOfYear < 9 ? "0" : "";
                String b = dayOfMonth < 10 ? "0" : "";
                date[0] = b + dayOfMonth + "-" + a + (monthOfYear + 1) + "-" + year;
            }
        });
        titleText.setText(myTask.get(position).getTaskTitle());
        descriptionText.setText(myTask.get(position).getTaskDescription());
        ratingBar.setRating(myTask.get(position).getImportanceStarRating());
        builder.setView(view2);
        builder.setPositiveButton(R.string.edit, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                if (!titleText.getText().toString().equals("")) {
                    url.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int i = 0;
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                if (i == position) {
                                    Firebase childRef = d.getRef();
                                    childRef.setValue(new Task(titleText.getText().toString(), date[0], (int) ratingBar.getRating(), descriptionText.getText().toString(), false));
                                    Toast.makeText(activity, R.string.taskUpdated, Toast.LENGTH_LONG).show();
                                }
                                i++;
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                   // DialogInterface ref = (DialogInterface) activity;
                    ref.refresh();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void setCompanyNameBuilder() {
        final EditText text = new EditText(activity);
        builder.setView(text);
        builder.setPositiveButton(R.string.ok, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                Shared shared = new Shared(activity);
                shared.setCompanyName(text.getText().toString());
//                DialogInterface set = (DialogInterface) activity;
                ref.setName(text.getText().toString());
            }
        });

        builder.setTitle(R.string.setNameForYourCountry);
        builder.setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

    public interface DialogInterface {
        void setName(String name);
        void refresh();
    }
}
