package example.moosa.com.chatter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends ActionBarActivity {
    private Button signInButton;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signInButton = (Button) findViewById(R.id.signInButton);
        username = (EditText) findViewById(R.id.usernameId);
        password = (EditText) findViewById(R.id.passwordId);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString();
                String pass = password.getText().toString();
                if (!uname.equals("") || !pass.equals("")) {
                    ParseUser.logInInBackground(uname, pass, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, ChatActivity.class));
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Fill the Fields", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
