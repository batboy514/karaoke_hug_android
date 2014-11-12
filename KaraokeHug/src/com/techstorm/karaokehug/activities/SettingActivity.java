package com.techstorm.karaokehug.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.techstorm.karaokehug.R;

public class SettingActivity extends Activity implements OnItemSelectedListener {
	Spinner spinner;
	TextView text1;
	
	public static String itemselected ;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting);
//		  Spinner spinner1 = (Spinner) findViewById(R.id.spinnermachine);	        
//	        spinner1.setOnItemSelectedListener(this);
//	        List<String> categories1 = new ArrayList<String>();
//	        categories1.add("Đầu karaoke Arirang 5 số");   
//	        categories1.add("Đầu karaoke california 6 số");   
//			ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);
//			dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);			
//			spinner1.setAdapter(dataAdapter1);
		 spinner = (Spinner) findViewById(R.id.spinnersetting);
		spinner.setOnItemSelectedListener(this);
		List<String> categories = new ArrayList<String>();
		categories.add(this.getApplicationContext().getString(R.string.allsong_string));
		categories.add(this.getApplicationContext().getString(R.string.vnsong_string));
		categories.add(this.getApplicationContext().getString(R.string.ensong_string));
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg0, int arg2,
			long arg3) {
	 itemselected = parent.getItemAtPosition(arg2).toString();	
	
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
