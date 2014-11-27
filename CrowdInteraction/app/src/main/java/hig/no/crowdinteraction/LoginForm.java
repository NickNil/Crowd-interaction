package hig.no.crowdinteraction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginForm extends Activity {

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        user = new User(getApplicationContext());

        Button login = (Button) findViewById(R.id.loginButton);
        Button register = (Button) findViewById(R.id.registerButton2);

        final EditText phoneNumber = (EditText) findViewById(R.id.phoneNumberInput);
        final EditText code = (EditText) findViewById(R.id.codeInput);
        final String regid;
        regid = user.GetGmcId();

        login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                if(phoneNumber.getText().toString().equals("")
                        || code.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Please fill Phone number and Code to login!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    LoginJSON json = new LoginJSON(getApplicationContext());

                    json.sendJson(phoneNumber.getText().toString(),
                            code.getText().toString());

                    //Intent intent = new Intent(LoginForm.this, Home.class);
                    //startActivity(intent);

                    if (!user.GetMongoId().equals("") && !user.GetMongoId().equals(null) ) {

                    Intent intent2 = new Intent(LoginForm.this, Home.class);
                    startActivity(intent2);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "You are not a registered user. Please register first!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                CountryCodesJSON json = new CountryCodesJSON(getApplicationContext());
                json.sendJson();
                Intent i = new Intent(LoginForm.this, Register_user.class);
                startActivity(i);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_form, menu);
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
