package com.techstorm.karaokehug.activities;

import java.util.ArrayList;
import java.util.Map;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.DisplaySong;
import com.techstorm.karaokehug.R;
import com.techstorm.karaokehug.R.id;
import com.techstorm.karaokehug.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity implements OnClickListener {
	
	private Integer searchVol;
	private String searchString;
	private ArrayList<String> user_name = new ArrayList<String>();
	private ArrayList<String> user_id = new ArrayList<String>();
	private ArrayList<String> user_lyric = new ArrayList<String>();
	private ArrayList<String> user_author = new ArrayList<String>();
	int backButtonCount = 0;
	TextView textcheck;
	ListView userlistsearch;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search);
		
		ImageButton btnsearch = (ImageButton) findViewById(R.id.btnsearch);
		btnsearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText textsearch = (EditText) findViewById(R.id.textsearch);
				searchVol = null;
				searchString = null;
				String k = textsearch.getText().toString();
				if (k.matches("[0-9]+")) {
					// mEditText only contains numbers
					int value = Integer.parseInt(textsearch.getText()
							.toString());
					searchVol = value;
					
				} else {
					// mEditText contains number + text, or text only.
					String kitu = textsearch.getText().toString();
					searchString = kitu;
					displayData();
				}
				displayData();

			}
		});
		final ListView userlistsearch = (ListView) findViewById(R.id.userlistfavourite);
		userlistsearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Map<String, Object> map = (Map<String, Object>) userlistsearch
						.getItemAtPosition(arg2);
				// TODO Auto-generated method stub
				Intent intent = new Intent();

				intent.putExtra("name", (String) map.get("name"));
				intent.putExtra("id", (String) map.get("id"));
				intent.putExtra("lyric", (String) map.get("lyric"));
				intent.putExtra("author", (String) map.get("author"));
				intent.setClass(getApplicationContext(), SongDetailActivity.class);
				startActivity(intent);
				
			}
		});
		
		showBanner(R.id.banner1);
	}

	private void displayData() {
		boolean hasData = DatabaseCreator.getSearchData(searchVol, searchString, user_name, user_id, user_lyric, user_author);
		if (hasData == false) {
			 userlistsearch = (ListView) findViewById(R.id.userlistfavourite);
			  userlistsearch.setVisibility(View.GONE);
			  textcheck = (TextView) findViewById(R.id.Txt_check);
			textcheck.setText(this.getApplicationContext().getString(R.string.check_song));
		} else {
		
			 userlistsearch = (ListView) findViewById(R.id.userlistfavourite);
			DisplaySong disadpt = new DisplaySong(SearchActivity.this, user_id,
					user_name, user_lyric, user_author);
			userlistsearch.setAdapter(disadpt);
		}
	}

	private void showBanner(int id) {
		AdView adView = (AdView) findViewById(id);
	    AdRequest adRequest = new AdRequest.Builder()
//	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//	        .addTestDevice(TEST_DEVICE_ID)
	        .build();
	    adView.loadAd(adRequest);
	}
	
	@Override
	public void onClick(View v) {

	}
	public void onBackPressed()
	{
	    if(backButtonCount >= 1)
	    {
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	        intent.addCategory(Intent.CATEGORY_HOME);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(intent);
	    }
	    else
	    {
	        Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
	        backButtonCount++;
	    }
	}
	public void ShowMedia(){
		
	
	
	
	}
}
