package hig.no.crowdinteraction;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register_user extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Button register = (Button) findViewById(R.id.registerButton);
        final EditText firstname = (EditText) findViewById(R.id.registerFirstname);
        final EditText lastname = (EditText) findViewById(R.id.registerLastname);
        final EditText nationality = (EditText) findViewById(R.id.registerNationality);
        final EditText phoneNumber = (EditText) findViewById(R.id.registerPhoneNumber);
        final EditText code = (EditText) findViewById(R.id.registerCode);

        register.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                PostDataJSON json = new PostDataJSON();

                json.sendJson(firstname.getText().toString(), lastname.getText().toString(),
                        nationality.getText().toString(), phoneNumber.getText().toString(),
                        code.getText().toString());
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
