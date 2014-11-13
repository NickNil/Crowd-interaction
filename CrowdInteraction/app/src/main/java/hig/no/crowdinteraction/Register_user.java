package hig.no.crowdinteraction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Register_user extends Activity {


    public static final String PROPERTY_REG_ID = "registration_id";
    Context context;
    Intent intent;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        intent = new Intent(this, LoginForm.class);
        context = getApplicationContext();
        user = new User(getApplicationContext());

        Button register = (Button) findViewById(R.id.registerButton);
        final EditText firstname = (EditText) findViewById(R.id.registerFirstname);
        final EditText lastname = (EditText) findViewById(R.id.registerLastname);
        final EditText nationality = (EditText) findViewById(R.id.registerNationality);
        final EditText phoneNumber = (EditText) findViewById(R.id.registerPhoneNumber);
        final EditText code = (EditText) findViewById(R.id.registerCode);

        /*A dropdown menu that can be implementet later on

        Spinner spinner = (Spinner) findViewById(R.id.registerNationality);
           // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sovereignStates, android.R.layout.simple_spinner_item);
           // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           // Apply the adapter to the spinner
        spinner.setAdapter(adapter);*/


        register.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String regid;
                regid = user.GetGmcId();
                if (regid.isEmpty())
                {
                    PostDataJSON json = new PostDataJSON(getApplicationContext());

                    json.register(firstname.getText().toString(), lastname.getText().toString(),
                            nationality.getText().toString(), phoneNumber.getText().toString(),
                            code.getText().toString());

                    Toast toast = Toast.makeText(context, "Good job, Sending you back!",
                            Toast.LENGTH_SHORT);
                    toast.show();


                    startActivity(intent);

                }
                else
                {
                    Toast toast = Toast.makeText(context, "You are already registered",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
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
