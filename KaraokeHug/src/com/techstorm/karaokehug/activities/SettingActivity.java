package com.techstorm.karaokehug.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.R;
import com.techstorm.karaokehug.entities.SaveEntity;

public class SettingActivity extends Activity implements OnItemSelectedListener {
	Spinner spinner;
	TextView text1;
	int backButtonCount = 0;
	int a;
	public static String itemselected;
	public static String itemproductselected;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting);
		Spinner spinnerproduct = (Spinner) findViewById(R.id.spinnerproduct);
		List<String> categories1 = new ArrayList<String>();
		DatabaseCreator.spinnerDataProduct(categories1);
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories1);
		dataAdapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerproduct.setAdapter(dataAdapter1);
//		a = Integer.parseInt(itemproductselected);
		Toast.makeText(
				this,
				itemproductselected,
				Toast.LENGTH_SHORT).show();
		for (String item : categories1) {
			if (itemproductselected.equals(item)) {
				spinnerproduct.setSelection(categories1.indexOf(item));
				break;
			}
		}

		Button btn = (Button) findViewById(R.id.buttonrate);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW);
				  intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.techstorm.karaokehug"));
				  startActivity(intent);
			}
		});
				
		spinnerproduct.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				itemproductselected = arg0.getItemAtPosition(arg2).toString();
				DatabaseCreator.saveMedia();
				SaveEntity saveEntity = DatabaseCreator.showMedia();
				SettingActivity.itemproductselected = saveEntity.getMedia(); 
						
				SettingActivity.itemselected = saveEntity.getLanguage();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spinner = (Spinner) findViewById(R.id.spinnersetting);
		spinner.setOnItemSelectedListener(this);
		List<String> categories = new ArrayList<String>();
		categories.add(this.getApplicationContext().getString(
				R.string.allsong_string));
		categories.add(this.getApplicationContext().getString(
				R.string.vnsong_string));
		categories.add(this.getApplicationContext().getString(
				R.string.ensong_string));
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

	public void onBackPressed() {
		 new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Closing Activity")
	        .setMessage("Are you sure you want to close this activity?")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            finish();    
	        }

	    })
	    .setNegativeButton("No", null)
	    .show();
	}
}
