package example.moosa.com.basic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;


public class MainActivity2Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        listview();
    }

    private void listview() {
        Button btn=(Button)findViewById(R.id.addItemButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText eText = (EditText) findViewById(R.id.addItem);
                ListView lv = (ListView) findViewById(R.id.listView);
int i=0;
                String a=eText.getText().toString();
                String array[]=new String[120];
                if(i != 6){
                    array[i]=eText.getText().toString();
                    i++;
                }
                ArrayAdapter<String> ad = new ArrayAdapter<String>(MainActivity2Activity.this, android.R.layout.simple_list_item_1,array);

                lv.setAdapter(ad);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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
