package firebasesample.moosatodo.com.worktodo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import Data.Database;

public class LocalService extends Service {
    public static String broadcastReferencePackageName = "firebasesample.moosatodo.com.worktodo";
    private LocalBinder binder = new LocalBinder();
    private Firebase url;
    private String linkFirst = "https://todolistmoosa.firebaseio.com/";
    private NotificationManager notificationManager;
    private int notifyID = 1234;
    private String companyName;

    public LocalService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(getApplicationContext());
        checkUpdate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void checkUpdate() {

        final Database database = new Database(getApplicationContext());
        final int prevSize = database.getTaskCollection().size();

        // Shared share=new Shared();

        url = new Firebase(linkFirst + "Fuuast");
        Log.d("IN-SERVICE--->", "IN USE----- " + companyName);
        //Firebase base=url.child(companyName);
        url.addChildEventListener(new ChildEventListener() {
            int currentSize = 0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("DATASNAPSHOT", s + "<-- " + dataSnapshot.toString());
                Log.d("DATASNAPSHOT-SIZE", "Current=" + currentSize + " - Previous=" + prevSize);

                if (currentSize >= prevSize) {
                    notification("New Work Added");
                    Log.d("DATASNAPSHOT-NOTIFY", s + " Notify -> " + dataSnapshot.toString());
                }
                currentSize++;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("DATASNAPSHOT", s + "<-- " + dataSnapshot.toString());
                notification("Work Changed");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("DATASNAPSHOT", dataSnapshot.toString());
                notification("Work Removed");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private void notification(String message) {
        //////////////////////BroadCast///////////////////
        Intent broadcastIntent = new Intent(broadcastReferencePackageName);
        sendBroadcast(broadcastIntent);
        ///////////////////Broadcast////End//////////////
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent openApp = new Intent(getApplicationContext(), MainActivity.class);
        openApp.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, openApp, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setTicker("Office Work")
                .setContentTitle("Work Updated")
                .setContentText(message)
                .setTicker(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{500, 500})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .build();
        notificationManager.notify(notifyID, notification);
    }


    public class LocalBinder extends Binder implements MainActivity.ServiceInterface {
        public LocalService getInstance() {
            return LocalService.this;
        }

        @Override
        public void getCityName(String name) {
            companyName = name;
            Log.d("IN-SERVICE--->", "METHOD--- ComName=" + companyName + " method Name=" + name);

        }
    }
}
