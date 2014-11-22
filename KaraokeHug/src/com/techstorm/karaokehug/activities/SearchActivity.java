package com.techstorm.karaokehug.activities;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.DisplaySong;
import com.techstorm.karaokehug.R;

public class SearchActivity extends Activity implements OnClickListener {
	
	private Integer searchId;
	private String searchString;
	private ArrayList<String> user_name = new ArrayList<String>();
	private ArrayList<String> user_id = new ArrayList<String>();
	private ArrayList<String> user_lyric = new ArrayList<String>();
	private ArrayList<String> user_author = new ArrayList<String>();
	private ArrayList<String> user_name1 = new ArrayList<String>();
	private ArrayList<String> user_lyric1 = new ArrayList<String>();
	private ArrayList<String> user_author1 = new ArrayList<String>();
	private ArrayList<String> user_id1 = new ArrayList<String>();
	int backButtonCount = 0;
	private Integer begin = 0;
	private Integer count = 10;
	private TextView textcheck;
	private ListView userlistsearch;
	private DisplaySong disadpt;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search);
		textcheck = (TextView) findViewById(R.id.Txt_check);
		ImageButton btnsearch = (ImageButton) findViewById(R.id.btnsearch);
		String check = SettingActivity.itemproductselected;
		
		btnsearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText textsearch = (EditText) findViewById(R.id.textsearch);
				searchId = null;
				searchString = null;
				String k = textsearch.getText().toString();
				if (k.matches("[0-9]+")) {
					// mEditText only contains numbers
					int value = Integer.parseInt(textsearch.getText()
							.toString());
					searchId = value;
					
				} else {
					// mEditText contains number + text, or text only.
					String kitu = textsearch.getText().toString();
					searchString = kitu;
				}
				displayData(searchId, searchString);

			}
		});
		userlistsearch = (ListView) findViewById(R.id.userlistfavourite);
		userlistsearch.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if ((firstVisibleItem + visibleItemCount) >= totalItemCount)
				{
			if (begin <= totalItemCount) {

							begin += count;
							

							if (disadpt != null) {
								disadpt.notifyDataSetChanged();
							displayData(searchId, searchString);
							}
								
						}
				}
			}
		});
		userlistsearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Map<String, Object> map = (Map<String, Object>) userlistsearch
						.getItemAtPosition(arg2);
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

	private void displayData(Integer searchId, String searchString) {
		boolean hasData = DatabaseCreator.getSearchData(begin, count, searchId, searchString, user_name, user_id, user_lyric, user_author);
		if (hasData) {
			DisplaySong disadpt = new DisplaySong(SearchActivity.this, user_id1,
					user_name1, user_lyric1, user_author1);
			userlistsearch.setAdapter(disadpt);
			userlistsearch.setVisibility(View.VISIBLE);
			textcheck.setVisibility(View.GONE);
		} else {
			userlistsearch.setVisibility(View.GONE);
			textcheck.setText(this.getApplicationContext().getString(R.string.check_song));
			textcheck.setVisibility(View.VISIBLE);
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(
				this,
				this.getApplicationContext().getString(R.string.select_model) + " " + SettingActivity.itemproductselected,
				Toast.LENGTH_LONG).show();

	}
	
	@Override
	public void onClick(View v) {

	}
	public void onBackPressed() {
		 new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle(this.getApplicationContext().getString(R.string.clo))
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

	public void ShowMedia(){
	}
	
}
