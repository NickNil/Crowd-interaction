package com.example.crowdinteraction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginForm extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_form);
		
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
				
				if(phoneNumber.getText().toString().equals("")|| code.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(),"Please fill Phone number and Code to login!",Toast.LENGTH_SHORT).show();
				}
				else if(phoneNumber.getText().toString().equals("1234")|| code.getText().toString().equals("1234"))
				{
					Intent i = new Intent(LoginForm.this, MainActivity.class);
					startActivity(i);
				}
				else
				{
					Toast.makeText(getApplicationContext(),"Incorrect Phone number or Code",Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		register.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent i = new Intent(LoginForm.this, RegisterUser.class);
				startActivity(i);
			}
		});
		
			
	}


}
