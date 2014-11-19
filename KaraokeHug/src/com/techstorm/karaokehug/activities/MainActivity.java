package com.techstorm.karaokehug.activities;


import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.R;
import com.techstorm.karaokehug.entities.SaveEntity;


@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnTabChangeListener {
	
	ArrayList<String> list = new ArrayList<String>();
	ArrayAdapter<String> adapter, listadapter;
	private ArrayList<String> media1 = new ArrayList<String>();
	private ArrayList<String> language = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseCreator.openDatabase(this);
		setTabs();
		TabHost tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);
		SaveEntity saveEntity = DatabaseCreator.showMedia();
		Toast.makeText(
				this,
				saveEntity.getMedia(),
				Toast.LENGTH_SHORT).show();
		SettingActivity.itemproductselected = saveEntity.getMedia(); 
				
		SettingActivity.itemselected = saveEntity.getLanguage();
	
		String g ="";
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