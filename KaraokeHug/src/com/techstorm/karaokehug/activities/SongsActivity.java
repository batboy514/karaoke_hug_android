package com.techstorm.karaokehug.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater.Filter;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.DisplaySong;
import com.techstorm.karaokehug.R;
import com.techstorm.karaokehug.utilities.IntegerUtil;

/**
 * @author
 * 
 */
public class SongsActivity extends Activity implements OnItemSelectedListener,
		OnScrollListener {

	private static final String ALL = "All";
	private static final String PREFIX_VOL_SEARCH = " Vol.";
	private ArrayList<String> user_name = new ArrayList<String>();
	private ArrayList<String> user_lyric = new ArrayList<String>();
	private ArrayList<String> user_author = new ArrayList<String>();
	private ArrayList<String> user_id = new ArrayList<String>();
	
	private ArrayList<String> user_name1 = new ArrayList<String>();
	private ArrayList<String> user_lyric1 = new ArrayList<String>();
	private ArrayList<String> user_author1 = new ArrayList<String>();
	private ArrayList<String> user_id1 = new ArrayList<String>();
	
	private List<String> categories = new ArrayList<String>();
	ArrayList<String> list = new ArrayList<String>();
	ArrayAdapter<String> adapter, listadapter;
	private Character abcSearch = '#';
	private Integer volSearch = 0;
	private ListView userList;
	private Context context;
	int backButtonCount = 0;
	Spinner spinner;
	Spinner spinner1;
	private TextView textcheck;
	private String previousVolSearch;
	private String previousAbcSearch;
	private DisplaySong disadpt;
	private Integer beginlimit = 0;
	private Integer countlimit = 10;
	private int currentPos = 0;

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// On selecting a spinner item
		String item = parent.getItemAtPosition(position).toString();
			
			if (!previousVolSearch.equals(item)) {
				if (parent.getItemAtPosition(position) != null) {
					item = parent.getItemAtPosition(position).toString()
							.replace(SettingActivity.itemselected + PREFIX_VOL_SEARCH, "");
				}
				volSearch = IntegerUtil.valueOf(item);
				displayDataAll1();
			}
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// empty
	}

	private void showBanner(int id) {
		AdView adView = (AdView) findViewById(id);
		AdRequest adRequest = new AdRequest.Builder()
		// .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		// .addTestDevice(TEST_DEVICE_ID)
				.build();
		adView.loadAd(adRequest);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		setContentView(R.layout.layout_songs);
		textcheck = (TextView) findViewById(R.id.Txt_check);
		showBanner(R.id.banner1);
		userList = (ListView) findViewById(R.id.list1);
		spinner = (Spinner) findViewById(R.id.spinner);
		spinner1 = (Spinner) findViewById(R.id.Spinner01);
		updateData();
		// Spinner click listener
		spinner.setOnItemSelectedListener(this);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			if (!user_id1.isEmpty()) {
				
				String abc = arg0.getItemAtPosition(arg2).toString();
				if (!previousAbcSearch.equals(abc)) {
					abcSearch = abc.charAt(0);
					user_author1.clear();
					user_id1.clear();
					user_lyric1.clear();
					user_name1.clear();
					beginlimit = 0;
					displayDataAll1();
					disadpt.notifyDataSetChanged();
					previousAbcSearch = abc;
				}
			}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		userList.setOnScrollListener(this);
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

				intent.setClass(getApplicationContext(),
						SongDetailActivity.class);
				startActivity(intent);
			}
		});

	}

	private void updateData() {
		// Spinner Drop down elements

		Boolean hasdata = DatabaseCreator.spinnerDataVol(PREFIX_VOL_SEARCH,
				categories);
		if (hasdata) {
			DatabaseCreator.spinnerDataVol(PREFIX_VOL_SEARCH, categories);
			String item = categories.get(0).toString()
					.replace(PREFIX_VOL_SEARCH, "");
			volSearch = IntegerUtil.parseInt(item);
		} else {
			categories.add(SettingActivity.itemproductselected);
			volSearch = null;
		}

		List<String> categories1 = new ArrayList<String>();
		// categories1.add(ALL);
		categories1.add("#");
		for (char character = 'A'; character < 'Z'; character++) {
			categories1.add(String.valueOf(character));
		}

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories1);
		
		previousVolSearch = categories.get(0);
		previousAbcSearch = categories1.get(0);
		
		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dataAdapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);
		spinner1.setAdapter(dataAdapter1);
	}

	public void displayDataAll1() {
		String choice = SettingActivity.itemselected;

		String languageCode = null;
		if (choice.contains(this.getString(R.string.vnsong_string))) {
			languageCode = "vn";
		}
		if (choice.contains(this.getString(R.string.ensong_string))) {
			languageCode = "en";
		}
		
		
		boolean hasData = DatabaseCreator.getSongDataAll(abcSearch, volSearch, beginlimit, countlimit, languageCode, user_name1,
				user_id1, user_lyric1, user_author1);
		
		if (hasData) {
			disadpt = new DisplaySong(SongsActivity.this, user_id1,
					user_name1, user_lyric1, user_author1);
			userList.setAdapter(disadpt);
			userList.setVisibility(View.VISIBLE);
			textcheck.setVisibility(View.GONE);
		} else {
			if (user_id1.isEmpty()) {
				userList.setVisibility(View.GONE);
				textcheck.setText(this.getApplicationContext().getString(R.string.check_song));
				textcheck.setVisibility(View.VISIBLE);
			}
		}
		}
	@Override
	protected void onResume() {
		super.onResume();
		updateData();
		displayDataAll1();

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


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

		if ((firstVisibleItem + visibleItemCount) >= totalItemCount)
		{
				if (beginlimit <= totalItemCount) {
					displayDataAll1();
					beginlimit += countlimit;
					if (disadpt != null) {
						disadpt.notifyDataSetChanged();
						
					}
				}
		}
		
	  
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}
}
