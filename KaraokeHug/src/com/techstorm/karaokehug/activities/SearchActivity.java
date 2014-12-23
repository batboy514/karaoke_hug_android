package com.techstorm.karaokehug.activities;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.techstorm.karaoke_hug.R;
import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.DisplaySong;

public class SearchActivity extends Activity implements OnClickListener,
		OnScrollListener {

	private Integer searchId;
	private String searchString;
	private ArrayList<String> userName = new ArrayList<String>();
	private ArrayList<String> userId = new ArrayList<String>();
	private ArrayList<String> userLyric = new ArrayList<String>();
	private ArrayList<String> userAuthor = new ArrayList<String>();
	private static final String ALL = "All";
	private static final String LANGUAGE_CODE_VN = "vn";
	private static final String LANGUAGE_CODE_EN = "en";

	int BACKBUTTONCOUNT = 0;
	private Integer beginLimit = 0;
	private final static Integer COUNTLIMIT = 10;
	private TextView textCheck;
	private ListView listviewSearch;
	private DisplaySong disadpt;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search);
		textCheck = (TextView) findViewById(R.id.Txt_check);
		ImageButton btnsearch = (ImageButton) findViewById(R.id.btnsearch);
		String CHECK = SettingActivity.itemProductSelected;

		btnsearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!userId.isEmpty()) {
					beginLimit = 0;
					userAuthor.clear();
					userId.clear();
					userName.clear();
					userLyric.clear();
					search();
					disadpt.notifyDataSetChanged();
				} else {
					search();
				}
				hideKeyboard();
			}
		});
		listviewSearch = (ListView) findViewById(R.id.userlistfavourite);
		listviewSearch.setOnScrollListener(this);

		listviewSearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Map<String, Object> map = (Map<String, Object>) listviewSearch
						.getItemAtPosition(arg2);
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

		showBanner(R.id.banner1);
	}

	private void displayData(Integer searchId, String searchString) {
		 String languagecode = null;
			String choice = SettingActivity.itemSelected;

		 if (choice.contains(this.getString(R.string.vnsong_string))) {
				languagecode = LANGUAGE_CODE_VN;
			}
			if (choice.contains(this.getString(R.string.ensong_string))) {
				languagecode = LANGUAGE_CODE_EN;
			}
		boolean hasData = DatabaseCreator
				.getSearchData(beginLimit, COUNTLIMIT, searchId, searchString,
						userName, userId, languagecode, userLyric, userAuthor);
		if (hasData) {
			disadpt = new DisplaySong(SearchActivity.this, userId, userName,
					userLyric, userAuthor);
			disadpt.notifyDataSetChanged();
			listviewSearch.setAdapter(disadpt);
			listviewSearch.setVisibility(View.VISIBLE);
			textCheck.setVisibility(View.GONE);
		} else {
			if (userAuthor.isEmpty()) {
				disadpt.notifyDataSetChanged();
			
				listviewSearch.setVisibility(View.GONE);
				textCheck.setText(this.getApplicationContext().getString(
						R.string.check_song));
				textCheck.setVisibility(View.VISIBLE);
			}
		}
	}

	private void showBanner(int id) {
		AdView adView = (AdView) findViewById(id);
		AdRequest adRequest = new AdRequest.Builder()
		// .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		// .addTestDevice(TEST_DEVICE_ID)
				.build();
		adView.loadAd(adRequest);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		beginLimit = 0;
		Toast.makeText(
				this,
				this.getApplicationContext().getString(R.string.select_model)
						+ " " + SettingActivity.itemProductSelected,
				Toast.LENGTH_LONG).show();
		Toast.makeText(
				this,
				 SettingActivity.itemSelected,
				Toast.LENGTH_LONG).show();
	
	
			if (!userId.isEmpty()) {
			
				userAuthor.clear();
				userId.clear();
				userName.clear();
				userLyric.clear();
				search();
				disadpt.notifyDataSetChanged();
			}
		}
	

	private void search() {
		EditText textsearch = (EditText) findViewById(R.id.textsearch);
		searchId = null;
		searchString = null;
		String k = textsearch.getText().toString();
		if (k.matches("[0-9]+")) {
			// mEditText only contains numbers
			int value = Integer.parseInt(textsearch.getText().toString());
			searchId = value;
		} else {
			// mEditText contains number + text, or text only.
			String kitu = textsearch.getText().toString();
			searchString = kitu;
		}

		displayData(searchId, searchString);
	}

	@Override
	public void onClick(View v) {

	}

	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(
						this.getApplicationContext().getString(
								R.string.closeapp))
				.setMessage(
						this.getApplicationContext()
								.getString(R.string.closing))
				.setPositiveButton(
						this.getApplicationContext().getString(R.string.yes),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}

						})
				.setNegativeButton(
						this.getApplicationContext().getString(R.string.no),
						null).show();
	}

	public void ShowMedia() {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int lastItem = firstVisibleItem + visibleItemCount;
		if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
			if (beginLimit < totalItemCount) {
				search();
				beginLimit += COUNTLIMIT;
				if (disadpt != null) {
					disadpt.notifyDataSetChanged();
					listviewSearch.setSelection(totalItemCount - COUNTLIMIT);
				}

			}
			
			
		}
			
		
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	private void hideKeyboard() {
		// Check if no view has focus:
		View view = this.getCurrentFocus();
		if (view != null) {
			InputMethodManager inputManager = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(view.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
