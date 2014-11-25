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
import com.techstorm.karaokehug.DisplaySong;
import com.techstorm.karaokehug.R;
import com.techstorm.karaokehug.entities.SaveEntity;

public class SettingActivity extends Activity implements OnItemSelectedListener {
	Spinner SPINNERLANGUAGE;
	TextView TEXT;
	int BACKBUTTONCOUNT = 0;
	int A;
	public static String itemSelecTed;
	private DisplaySong Disadpt;
	public static String itemProductSelecTed;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting);
		Spinner spinnerProDuct = (Spinner) findViewById(R.id.spinnerproduct);
		List<String> categories1 = new ArrayList<String>();
		DatabaseCreator.spinnerDataProduct(categories1);
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories1);
		dataAdapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerProDuct.setAdapter(dataAdapter1);
//		a = Integer.parseInt(itemproductselected);
		for (String item : categories1) {
			if (itemProductSelecTed.equals(item)) {
				spinnerProDuct.setSelection(categories1.indexOf(item));
				break;
			}
		}

		Button btnRate = (Button) findViewById(R.id.buttonrate);
		btnRate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW);
				  intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.techstorm.karaokehug"));
				  startActivity(intent);
			}
		});
				
		spinnerProDuct.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				itemProductSelecTed = arg0.getItemAtPosition(arg2).toString();
				DatabaseCreator.saveMedia();
				SaveEntity saveEntity = DatabaseCreator.showMedia();
				SettingActivity.itemProductSelecTed = saveEntity.getMedia(); 
						
				SettingActivity.itemSelecTed = saveEntity.getLanguage();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		SPINNERLANGUAGE = (Spinner) findViewById(R.id.spinnersetting);
		SPINNERLANGUAGE.setOnItemSelectedListener(this);
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
		SPINNERLANGUAGE.setAdapter(dataAdapter);
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View arg0, int arg2,
			long arg3) {
		itemSelecTed = parent.getItemAtPosition(arg2).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	public void onBackPressed() {
		 new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle(this.getApplicationContext().getString(R.string.closeapp))
	        .setMessage(this.getApplicationContext().getString(R.string.closing))
	        .setPositiveButton(this.getApplicationContext().getString(R.string.yes), new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            finish();    
	        }

	    })
	    .setNegativeButton(this.getApplicationContext().getString(R.string.no), null)
	    .show();
	}
}
