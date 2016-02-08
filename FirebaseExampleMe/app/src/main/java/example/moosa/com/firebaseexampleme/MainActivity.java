package example.moosa.com.firebaseexampleme;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    static Firebase fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fb = new Firebase("https://glaring-torch-1710.firebaseio.com/");
        buttonListener();
        updateTextView();
    }

    private void updateTextView() {
        Log.d("Update invoked", "Starting Listener");
        Firebase ref = fb.child("finished");
        ref.addValueEventListener(new ValueEventListener() {
            TextView textView = (TextView) findViewById(R.id.textviewsection);

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("DATASNAPSHOT", dataSnapshot.getValue().toString());

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for (DataSnapshot single : child.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) single.getValue();
                        String a = (String) map.get("message");
                        String b = (String) map.get("name");
                        textView.append(b + " -- " + a + "\n");
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void buttonListener() {
        Button btn = (Button) findViewById(R.id.buttonsenddata);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
                updateTextView();

            }
        });
    }

    private void sendData() {
        Log.d("CLICKEVENT", "Button Clicked");

        EditText textName = (EditText) findViewById(R.id.edittextsendtitle);

        EditText textMessage = (EditText) findViewById(R.id.edittextsenddata);

        String n = textName.getText().toString();

        String m = textMessage.getText().toString();


        Log.d("CLICKEVENT", "Ready to Update Value on Server");
        Firebase msgRef = fb.child("finished");
        HashMap<String, Message> msgList = new HashMap<>();
        Message msg = new Message(m, n);
        msgList.put("Moosa", msg);
        msgRef.push().setValue(msgList);
        Log.d("CLICKEVENT", "Value Updated On Server");
        textMessage.setText("");
        textName.setText("");

    }
}
