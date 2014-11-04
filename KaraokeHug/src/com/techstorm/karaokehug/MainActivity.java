package com.techstorm.karaokehug;


import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends TabActivity {
    
    private DatabaseHelper mHelper;
	private ArrayList<String> user_fName = new ArrayList<String>();
	private ListView userList;
	private SQLiteDatabase dataBase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseCreator.openDatabase(this);
        DatabaseCreator.getMapPath();
		setTabs() ;
    }
       
    private void setTabs()
	{
		addTab("Search", R.drawable.tab_search,SearchActivity.class);
		addTab("Song", R.drawable.tab_song, OptionsActivity.class);
		
	//	addTab("Favorite", R.drawable.tab_favorite, search.class);
		//addTab("Help", R.drawable.tab_help, search.class);
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
    
    }
