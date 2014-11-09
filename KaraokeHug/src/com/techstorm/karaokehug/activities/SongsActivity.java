package com.techstorm.karaokehug.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.DisplaySong;
import com.techstorm.karaokehug.R;
import com.techstorm.karaokehug.R.id;
import com.techstorm.karaokehug.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * @author
 * 
 */
public class SongsActivity extends Activity implements OnItemSelectedListener {

	private static final String ALL = "All";
	private static final String PREFIX_VOL_SEARCH = "Ariang Vol.";
	private ArrayList<String> user_name = new ArrayList<String>();
	private ArrayList<String> user_lyric = new ArrayList<String>();
	private ArrayList<String> user_author = new ArrayList<String>();
	private ArrayList<String> user_id = new ArrayList<String>();
	private List<String> categories = new ArrayList<String>();
	ArrayList<String> list = new ArrayList<String>();
	ArrayAdapter<String> adapter, listadapter;
	private Character abcSearch = 'A';
	private Integer volSearch;
	private ListView userList;

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// On selecting a spinner item

		String item = parent.getItemAtPosition(position).toString().replace(PREFIX_VOL_SEARCH, "");
		volSearch = Integer.parseInt(item);
		displayDataALL();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// empty
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_songs);
		userList = (ListView) findViewById(R.id.list1);

		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		Spinner spinner1 = (Spinner) findViewById(R.id.Spinner01);
		// Spinner click listener
		spinner.setOnItemSelectedListener(this);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String abc = arg0.getItemAtPosition(arg2).toString();
				if (ALL.equals(abc)) {
					abcSearch = null;
				} else {
					abcSearch = abc.charAt(0);
				}
				displayDataALL();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// Spinner Drop down elements
		DatabaseCreator.spinnerDataVol(PREFIX_VOL_SEARCH, categories);

		List<String> categories1 = new ArrayList<String>();
//		categories1.add(ALL);
		for (char character = 'A'; character < 'Z'; character++) {
			categories1.add(String.valueOf(character));

		}

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories1);
		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dataAdapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);
		spinner1.setAdapter(dataAdapter1);
		userList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Map<String, Object> map = (Map<String, Object>) userList
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

	}

	private void displayDataALL() {
		DatabaseCreator.getSongData(abcSearch, volSearch, user_name, user_id,
				user_lyric, user_author);
		DisplaySong disadpt = new DisplaySong(SongsActivity.this, user_id,
				user_name, user_lyric, user_author);
		userList.setAdapter(disadpt);
	}
}