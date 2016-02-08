package example.moosa.com.chatter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {
    private Button loginButton;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.loginButton);
        createAccountButton = (Button) findViewById(R.id.createAccountButtonId);
        createAccountButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

   /*     ParseUser user = new ParseUser();
        user.setUsername("Moosa");
        user.setPassword("mypass");
        user.setEmail("moosa@gmail.com");
        user.put("phone", "650-253-0000");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("Debug", "Correct");
                    Toast.makeText(MainActivity.this,"User Created",Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Debug", "Error");

                }
            }
        });
*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.createAccountButtonId:
                startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
                break;
        }
    }
}
