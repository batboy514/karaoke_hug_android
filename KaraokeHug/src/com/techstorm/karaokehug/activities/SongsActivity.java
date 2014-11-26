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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.techstorm.karaoke_hug.R;
import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.DisplaySong;
import com.techstorm.karaokehug.utilities.IntegerUtil;

/**
 * @author
 * 
 */
public class SongsActivity extends Activity implements OnItemSelectedListener,
		OnScrollListener {

	private static final String ALL = "All";
	private static final String LANGUAGE_CODE_VN = "vn";
	private static final String LANGUAGE_CODE_EN = "en";
	private static final String PREFIX_VOL_SEARCH = " Vol.";
	public static ArrayList<String> userName = new ArrayList<String>();
	public static ArrayList<String> userLyric = new ArrayList<String>();
	public static ArrayList<String> userAuthor = new ArrayList<String>();
	public static ArrayList<String> userId = new ArrayList<String>();

	private List<String> categoriesVol = new ArrayList<String>();
	ArrayAdapter<String> adapter, listadapter;
	private Character abcSearch = '#';
	private Integer volSearch = 0;
	public static ListView listviewSong;
	private Context context;
	int backButtonCount = 0;
	Spinner spinnerVolSearch;
	Spinner spinnerAbcSearch;
	private TextView textCheck;
	public static String previousVolSearch;
	private String previousAbcSearch;
	public static DisplaySong disadpt;
	private Integer beginLimit = 0;
	private Integer COUNTLIMIT = 10;
	private ArrayAdapter<String> dataAdapterVol;
	private ArrayAdapter<String> dataAdapterAbc;
	private int positionVol = 0;
	private int positionABC = 0;
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// On selecting a spinner item
		String item = parent.getItemAtPosition(position).toString();
		this.positionVol = position;
		if (!previousVolSearch.equals(item)) {
			if (parent.getItemAtPosition(position) != null) {
				item = parent
						.getItemAtPosition(position)
						.toString()
						.replace(
								SettingActivity.itemSelecTed
										+ PREFIX_VOL_SEARCH, "");
			}
			volSearch = IntegerUtil.valueOf(item);
			userAuthor.clear();
			userId.clear();
			userLyric.clear();
			userName.clear();
			beginLimit = 0;
			displayDataAll1();
			disadpt.notifyDataSetChanged();
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
		textCheck = (TextView) findViewById(R.id.Txt_check);
		showBanner(R.id.banner1);
		listviewSong = (ListView) findViewById(R.id.list1);
		spinnerVolSearch = (Spinner) findViewById(R.id.spinner);
		spinnerAbcSearch = (Spinner) findViewById(R.id.Spinner01);
		updateData();
		// Spinner click listener
		spinnerVolSearch.setOnItemSelectedListener(this);
		spinnerAbcSearch.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				positionABC = arg2;
				String abc = arg0.getItemAtPosition(arg2).toString();
				if (!userId.isEmpty()) {
					if (!previousAbcSearch.equals(abc)) {
						abcSearch = abc.charAt(0);
						userAuthor.clear();
						userId.clear();
						userLyric.clear();
						userName.clear();
						beginLimit = 0;
						displayDataAll1();
						disadpt.notifyDataSetChanged();
						previousAbcSearch = abc;
					}
				}
				if (userId.isEmpty()) {
					if (!previousAbcSearch.equals(abc)) {
						abcSearch = abc.charAt(0);
						userAuthor.clear();
						userId.clear();
						userLyric.clear();
						userName.clear();
						beginLimit = 0;
						displayDataAll1();
						previousAbcSearch = abc;
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		listviewSong.setOnScrollListener(this);
		listviewSong.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Map<String, Object> map = (Map<String, Object>) listviewSong
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
				categoriesVol);
		if (hasdata) {
			DatabaseCreator.spinnerDataVol(PREFIX_VOL_SEARCH, categoriesVol);
			String item = categoriesVol.get(0).toString()
					.replace(PREFIX_VOL_SEARCH, "");
			volSearch = IntegerUtil.parseInt(item);
		} else {
			categoriesVol.add(SettingActivity.itemProductSelecTed);
			volSearch = null;
		}

		List<String> categoriesAbc = new ArrayList<String>();
		// categories1.add(ALL);
		categoriesAbc.add("#");
		for (char character = 'A'; character < 'Z'; character++) {
			categoriesAbc.add(String.valueOf(character));
		}

		// Creating adapter for spinner
		dataAdapterVol = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categoriesVol);
		dataAdapterAbc = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categoriesAbc);

		
		if (previousVolSearch == null) {
			previousVolSearch = categoriesVol.get(0);
		}
		if (previousAbcSearch == null) {
			previousAbcSearch = categoriesAbc.get(0);
		}
		
		
		Object selectedVolItem = spinnerVolSearch.getItemAtPosition(positionVol);
		Object selectedAbcItem = spinnerAbcSearch.getItemAtPosition(positionABC);
		if (selectedVolItem != null && selectedAbcItem != null && (
				!previousVolSearch.equals(selectedVolItem.toString())
				|| !previousAbcSearch.equals(selectedAbcItem.toString()))) {
			userAuthor.clear();
			userId.clear();
			userLyric.clear();
			userName.clear();
			beginLimit = 0;
			displayDataAll1();
			disadpt.notifyDataSetChanged();
		}
		

		// Drop down layout style - list view with radio button
		dataAdapterVol
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dataAdapterAbc
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinnerVolSearch.setAdapter(dataAdapterVol);
		spinnerAbcSearch.setAdapter(dataAdapterAbc);
	}

	public void displayDataAll1() {
		String choice = SettingActivity.itemSelecTed;

		String languageCode = null;
		if (choice.contains(this.getString(R.string.vnsong_string))) {
			languageCode = LANGUAGE_CODE_VN;
		}
		if (choice.contains(this.getString(R.string.ensong_string))) {
			languageCode = LANGUAGE_CODE_EN;
		}
		boolean hasData = DatabaseCreator.getSongDataAll(abcSearch, volSearch,
				beginLimit, COUNTLIMIT, languageCode, userName, userId,
				userLyric, userAuthor);

		if (hasData) {
			disadpt = new DisplaySong(SongsActivity.this, userId, userName,
					userLyric, userAuthor);
			disadpt.notifyDataSetChanged();
			listviewSong.setAdapter(disadpt);
			listviewSong.setVisibility(View.VISIBLE);
			textCheck.setVisibility(View.GONE);
		} else {
			if (userId.isEmpty()) {
				listviewSong.setVisibility(View.GONE);
				textCheck.setText(this.getApplicationContext().getString(
						R.string.check_song));
				textCheck.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		beginLimit = 0;
		if (!userId.isEmpty()) {
			userAuthor.clear();
			userId.clear();
			userLyric.clear();
			userName.clear();
			updateData();
			disadpt.notifyDataSetChanged();
		}
		if (userId.isEmpty()) {
			userAuthor.clear();
			userId.clear();
			userLyric.clear();
			userName.clear();
			updateData();
		}

	}

	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(this.getApplicationContext().getString(R.string.closeapp))
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

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

		if ((firstVisibleItem + visibleItemCount) >= totalItemCount) {
			if (beginLimit <= totalItemCount) {
				displayDataAll1();
				beginLimit += COUNTLIMIT;
				if (disadpt != null) {
					disadpt.notifyDataSetChanged();
					listviewSong.setSelection(totalItemCount - COUNTLIMIT);
				}
			}
		}
//		if (totalItemCount < 10) {
//			listviewSong.setSelection(totalItemCount - COUNTLIMIT);
//
//		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}
}
