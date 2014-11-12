package com.techstorm.karaokehug.activities;


import java.util.ArrayList;

import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.DisplaySong;
import com.techstorm.karaokehug.R;
import com.techstorm.karaokehug.R.drawable;
import com.techstorm.karaokehug.R.id;
import com.techstorm.karaokehug.R.layout;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;


@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnTabChangeListener {
	private Character abcSearch = 'A';
	private Integer volSearch;
	private ListView userList;
	private ArrayList<String> user_name = new ArrayList<String>();
	private ArrayList<String> user_lyric = new ArrayList<String>();
	private ArrayList<String> user_author = new ArrayList<String>();
	private ArrayList<String> user_id = new ArrayList<String>();
	ArrayList<String> list = new ArrayList<String>();
	ArrayAdapter<String> adapter, listadapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseCreator.openDatabase(this);
		setTabs();
		TabHost tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);
    }
       
    private void setTabs()
	{
    	addTab(this.getApplicationContext().getString(R.string.search_string), R.drawable.tab_search,SearchActivity.class);
    	addTab(this.getApplicationContext().getString(R.string.favourite_string), R.drawable.tab_favorite, FavouriteActivity.class);
		addTab(this.getApplicationContext().getString(R.string.song_string), R.drawable.tab_song, SongsActivity.class);
		addTab(this.getApplicationContext().getString(R.string.action_settings), R.drawable.tab_st, SettingActivity.class);
	}
	
	private void addTab(String labelId, int drawableId, Class<?> c)
	{
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);	
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		
		
	}
}