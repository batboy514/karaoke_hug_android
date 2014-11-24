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

public class SearchActivity extends Activity implements OnClickListener,
		OnScrollListener {

	private Integer searchId;
	private String searchString;
	private ArrayList<String> userName = new ArrayList<String>();
	private ArrayList<String> userId = new ArrayList<String>();
	private ArrayList<String> userLyric = new ArrayList<String>();
	private ArrayList<String> userAuthor = new ArrayList<String>();

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
		String CHECK = SettingActivity.itemProductSelecTed;

		btnsearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				search();
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
		boolean hasData = DatabaseCreator
				.getSearchData(beginLimit, COUNTLIMIT, searchId, searchString,
						userName, userId, userLyric, userAuthor);
		if (hasData) {
			disadpt = new DisplaySong(SearchActivity.this, userId, userName,
					userLyric, userAuthor);
			disadpt.notifyDataSetChanged();
			listviewSearch.setAdapter(disadpt);
			listviewSearch.setVisibility(View.VISIBLE);
			textCheck.setVisibility(View.GONE);
		} else {
			listviewSearch.setVisibility(View.GONE);
			textCheck.setText(this.getApplicationContext().getString(
					R.string.check_song));
			textCheck.setVisibility(View.VISIBLE);
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
		Toast.makeText(
				this,
				this.getApplicationContext().getString(R.string.select_model)
						+ " " + SettingActivity.itemProductSelecTed,
				Toast.LENGTH_LONG).show();

	}

	private void search() {
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
	
	@Override
	public void onClick(View v) {

	}

	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(
						this.getApplicationContext().getString(R.string.close))
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

		if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
			if (beginLimit <= totalItemCount) {
				search();
				beginLimit += COUNTLIMIT;
				if (disadpt != null) {
					disadpt.notifyDataSetChanged();
					listviewSearch.setSelection(totalItemCount-COUNTLIMIT);
				}

			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

}
