package com.example.crowdinteraction;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class MainActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		//Adapting data to spinners
		Spinner sportsSpinner = (Spinner) findViewById(R.id.sportsSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.Sports, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		sportsSpinner.setAdapter(adapter);
		
		Spinner eventsSpinner = (Spinner) findViewById(R.id.eventsSpinner);
		adapter = ArrayAdapter.createFromResource(this,
		        R.array.Events, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		eventsSpinner.setAdapter(adapter);
		
		Spinner athleteSpinner = (Spinner) findViewById(R.id.athleteSpinner);
		adapter = ArrayAdapter.createFromResource(this,
		        R.array.Athletes, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		athleteSpinner.setAdapter(adapter);
		
		//Score Picker
		NumberPicker scorePicker=(NumberPicker) findViewById(R.id.scorePicker);
		String[] availableScores=new String[41];
		for(int i=0;i<availableScores.length;i++)
		{
			availableScores[i]=Double.toString(i*0.5);
		}
		scorePicker.setMaxValue(availableScores.length-1);
		scorePicker.setMinValue(0);
		scorePicker.setDisplayedValues(availableScores);
		scorePicker.setWrapSelectorWheel(false);
	}
	
}
