package hig.no.crowdinteraction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Register_user extends Activity {


    public static final String PROPERTY_REG_ID = "registration_id";
    Context context;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        intent = new Intent(this, LoginForm.class);
        context = getApplicationContext();

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
                regid = getRegistrationId(context);
                if (regid.isEmpty())
                {
                    PostDataJSON json = new PostDataJSON(getApplicationContext());

                    json.sendJson(firstname.getText().toString(), lastname.getText().toString(),
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

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i("RegForm", "Registration not found.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(Register_user.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }


}
