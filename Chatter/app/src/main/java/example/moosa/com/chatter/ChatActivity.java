package example.moosa.com.chatter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import Data.ChatAdaptor;
import Model.Message;
import Utils.ProgressGenerator;


public class ChatActivity extends ActionBarActivity {
    public static final String USER_ID_KEY = "userId";
    private static final int MAX_CHAT_MSG_TO_SHOW = 70;
    private EditText message;
    private Button sendMessage;
    private ProgressGenerator progressGenerator;
    private String currentUserID;
    private ListView listView;
    private ArrayList<Message> mMessages;
    private ChatAdaptor mAdaptor;
    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(runnable, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getCurrentUser();
        handler.postDelayed(runnable, 500);
    }

    private void getCurrentUser() {
        currentUserID = ParseUser.getCurrentUser().getObjectId();
        messagePosting();
    }

    private void messagePosting() {
        message = (EditText) findViewById(R.id.etMessage);
        sendMessage = (Button) findViewById(R.id.buttonSend);
        listView = (ListView) findViewById(R.id.listViewChat);
        mMessages = new ArrayList<>();
        mAdaptor = new ChatAdaptor(ChatActivity.this, currentUserID, mMessages);
        listView.setAdapter(mAdaptor);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!message.getText().toString().equals("")) {
                    Message msg = new Message();
                    msg.setUserId(currentUserID);
                    msg.setBody(message.getText().toString());
                    message.setText("");
                    msg.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            receiveMessage();
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Empty Message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void receiveMessage() {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);

        query.setLimit(MAX_CHAT_MSG_TO_SHOW);

        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> list, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(list);
                    mAdaptor.notifyDataSetChanged();
                    listView.invalidate();

                } else {
                    Log.d("ERROR", "Error:" + e.getMessage());
                }
            }
        });
    }

    private void refreshMessages() {
        // int a = listView.getScrollY();
        int a = listView.getVerticalScrollbarPosition();
        receiveMessage();
        // listView.setScrollY(a);
        listView.setVerticalScrollbarPosition(a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        startActivity(new Intent(ChatActivity.this, MainActivity.class));
                    }
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
