package com.example.crowdinteraction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterUser extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_users);
		
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
				json.sendJson(firstname.getText().toString(), lastname.getText().toString(), nationality.getText().toString(), phoneNumber.getText().toString(), code.getText().toString());
			}
		});
	}
	
	
}
