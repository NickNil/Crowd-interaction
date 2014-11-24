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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class Register_user extends Activity {


    public static final String PROPERTY_REG_ID = "registration_id";
    Context context;
    Intent intent;
    User user;
    Button nationality, register;
    EditText firstname, lastname, phoneNumber, code;
    String result, ioc, iso;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        intent = new Intent(this, LoginForm.class);
        context = getApplicationContext();
        user = new User(getApplicationContext());

        register = (Button) findViewById(R.id.registerButton);
        firstname = (EditText) findViewById(R.id.registerFirstname);
        lastname = (EditText) findViewById(R.id.registerLastname);
        phoneNumber = (EditText) findViewById(R.id.registerPhoneNumber);
        code = (EditText) findViewById(R.id.registerCode);

        //final EditText nationality = (EditText) findViewById(R.id.registerNationality);
        nationality = (Button)findViewById(R.id.nationalityButton);


        nationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Nationality.class);
                startActivityForResult(i, 1);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                result = data.getStringExtra("result");
                ioc = data.getStringExtra("ioc");
                iso = data.getStringExtra("iso");
                Log.i("extras", ioc);
                Log.i("extras", iso);
                nationality.setText(result);
                register.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        String regid;
                        regid = user.GetGmcId();
                        
                        if(firstname.getText().toString().equals("")|| lastname.getText().toString().equals("")|| phoneNumber.getText().toString().equals("") || ioc.equals("") || iso.equals("")|| code.getText().toString().equals("")) {

                            Toast toast = Toast.makeText(context, "Please fill in all the details!",
                                    Toast.LENGTH_SHORT);
                            toast.show();

                        }
                        else {
                            if (regid.isEmpty()) {


                                PostDataJSON json = new PostDataJSON(getApplicationContext());

                                json.sendJson(firstname.getText().toString(), lastname.getText().toString(),
                                        ioc, iso, phoneNumber.getText().toString(),
                                        code.getText().toString());

                                Intent login = new Intent(context, LoginForm.class);
                                startActivity(login);


                            } else {
                                Toast toast2 = Toast.makeText(context, "You are already registered",
                                        Toast.LENGTH_SHORT);
                                toast2.show();
                            }
                        }
                    }
                });

            }

        }
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
