package com.techstorm.karaokehug;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * @author 
 *
 */
public class OptionsActivity extends Activity implements OnItemSelectedListener{
	 @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionspage);
    
    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    Spinner spinner1 = (Spinner) findViewById(R.id.Spinner01);
    // Spinner click listener
    spinner.setOnItemSelectedListener(this);
    spinner1.setOnItemSelectedListener(this);
    
    // Spinner Drop down elements
    List<String> categories = new ArrayList<String>();
    categories.add("Automobile");
    categories.add("Business Services");
    categories.add("Computers");
    categories.add("Education");
    categories.add("Personal");
    categories.add("Travel");
    
    List<String> categories1 = new ArrayList<String>();
    categories1.add("A");
    categories1.add("B");
    categories1.add("C");
    categories1.add("D");
    categories1.add("E");
    categories1.add("F");
    
    
    // Creating adapter for spinner
	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
	ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);
	// Drop down layout style - list view with radio button
	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	
	// attaching data adapter to spinner
	spinner.setAdapter(dataAdapter);
	spinner1.setAdapter(dataAdapter1);
}

@Override
public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	// On selecting a spinner item
	String item = parent.getItemAtPosition(position).toString();
	
	// Showing selected spinner item
	Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

}

public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
	
}

}
