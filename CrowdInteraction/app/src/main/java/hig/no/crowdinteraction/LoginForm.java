package hig.no.crowdinteraction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginForm extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);


        Button login = (Button) findViewById(R.id.loginButton);
        Button register = (Button) findViewById(R.id.registerButton2);
        final EditText phoneNumber = (EditText) findViewById(R.id.phoneNumberInput);
        final EditText code = (EditText) findViewById(R.id.codeInput);

        login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent test = new Intent(LoginForm.this, MainActivity.class);
                startActivity(test);

                if(phoneNumber.getText().toString().equals("")
                        || code.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Please fill Phone number and Code to login!",
                            Toast.LENGTH_SHORT).show();
                }
                else if(phoneNumber.getText().toString().equals("1234")
                        || code.getText().toString().equals("1234"))
                {
                    Intent i = new Intent(LoginForm.this, MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Incorrect Phone number or Code",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
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