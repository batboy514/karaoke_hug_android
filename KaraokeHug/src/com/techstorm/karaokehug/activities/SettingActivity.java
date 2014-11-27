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

import com.techstorm.karaoke_hug.R;
import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.DisplaySong;
import com.techstorm.karaokehug.entities.SaveEntity;

public class SettingActivity extends Activity implements OnItemSelectedListener {
	private Spinner spinnerLanguage;
	TextView TEXT;
	int backButtonCount = 0;
	int A;
	public static String itemSelected;
	public static String itemProductSelected;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting);
		Spinner spinnerProDuct = (Spinner) findViewById(R.id.spinnerproduct);
		List<String> categoriesProduct = new ArrayList<String>();
		DatabaseCreator.spinnerDataProduct(categoriesProduct);
		ArrayAdapter<String> dataAdapterProduct = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categoriesProduct);
		dataAdapterProduct
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerProDuct.setAdapter(dataAdapterProduct);
		for (String item : categoriesProduct) {
			if (itemProductSelected.equals(item)) {
				spinnerProDuct.setSelection(categoriesProduct.indexOf(item));
				break;
			}
		}

		Button btnRate = (Button) findViewById(R.id.buttonrate);
		btnRate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				  intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.techstorm.karaoke_hug"));
				  startActivity(intent);
			}
		});
				
		spinnerProDuct.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				itemProductSelected = arg0.getItemAtPosition(arg2).toString();
				DatabaseCreator.saveMedia();
				SaveEntity saveEntity = DatabaseCreator.showMedia();
				SettingActivity.itemProductSelected = saveEntity.getMedia(); 
						
				SettingActivity.itemSelected = saveEntity.getLanguage();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spinnerLanguage = (Spinner) findViewById(R.id.spinnersetting);
		spinnerLanguage.setOnItemSelectedListener(this);
		List<String> categoriesLanguage = new ArrayList<String>();
		categoriesLanguage.add(this.getApplicationContext().getString(
				R.string.vnsong_string));
		categoriesLanguage.add(this.getApplicationContext().getString(
				R.string.ensong_string));
		categoriesLanguage.add(this.getApplicationContext().getString(
				R.string.allsong_string));
		
		for (String item : categoriesLanguage) {
			if (itemSelected.equals(item)) {
				spinnerLanguage.setSelection(categoriesLanguage.indexOf(item));
				break;
			}
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categoriesLanguage);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerLanguage.setAdapter(dataAdapter);
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View arg0, int arg2,
			long arg3) {
		itemSelected = parent.getItemAtPosition(arg2).toString();
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
