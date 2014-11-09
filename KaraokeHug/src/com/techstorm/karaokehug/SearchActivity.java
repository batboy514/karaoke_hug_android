package com.techstorm.karaokehug;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class SearchActivity extends Activity implements OnClickListener {
	
	private Integer searchVol;
	private String searchString;
	private ArrayList<String> user_name = new ArrayList<String>();
	private ArrayList<String> user_id = new ArrayList<String>();
	private ArrayList<String> user_lyric = new ArrayList<String>();
	private ArrayList<String> user_author = new ArrayList<String>();

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
				intent.setClass(getApplicationContext(), ArrowsActivity.class);
				startActivity(intent);
				
			}
		});

	}

	private void displayData() {
		DatabaseCreator.getSearchData(searchVol, searchString, user_name, user_id, user_lyric, user_author);
		ListView userlistsearch = (ListView) findViewById(R.id.userlistfavourite);
		DisplaySearch disadpt = new DisplaySearch(SearchActivity.this, user_id,
				user_name, user_lyric, user_author);
		userlistsearch.setAdapter(disadpt);

	}

	@Override
	public void onClick(View v) {

	}
}