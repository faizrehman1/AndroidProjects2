package example.moosa.com.basic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

    EditText eText;
    TextView text;
    Button btn;
    RatingBar rate;
    RadioButton radio;
    Switch tog;
    CheckBox chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        work();
        clear();
    }

    private void clear() {
        Button clr = (Button) findViewById(R.id.button2);
        clr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               EditText cText = (EditText) findViewById(R.id.editText);

                cText.setText("");
            }
        });

    }

    public void work() {
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                eText = (EditText) findViewById(R.id.editText);
                text = (TextView) findViewById(R.id.textView);
                rate = (RatingBar) findViewById(R.id.ratingBar);
                tog = (Switch) findViewById(R.id.switch1);
                chk = (CheckBox) findViewById(R.id.checkBox);
                radio = (RadioButton) findViewById(R.id.radioButton);

                String str = "\n " + rate.getRating();
                String all = "\n" + tog.isChecked() + "\n " + chk.isChecked() + "\n " + radio.isChecked();
                text.setText("" + eText.getText().toString() + "" + str + "" + all);
                eText.setText("");


            }
        });


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
}
