package pana.com.withoutview;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class MyService extends IntentService {

    public MyService() {
        super("MyService");
        //Toast.makeText(getApplicationContext(), "Service", Toast.LENGTH_SHORT).show();
        Log.d("Constructor", "Service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Toast.makeText(getApplicationContext(), "Service", Toast.LENGTH_SHORT).show();

        Log.d("OnCreate", "Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("OnStartCommand", "Service");
//        Toast.makeText(getApplicationContext(), "Service", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + "*444#"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
      try {
          startActivity(intent2);

      }catch (Exception ex){
          ex.printStackTrace();
      }
        return START_NOT_STICKY;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("OnHandleIntent", "Service");
  //      Toast.makeText(getApplicationContext(), "Service", Toast.LENGTH_SHORT).show();

    }

}
