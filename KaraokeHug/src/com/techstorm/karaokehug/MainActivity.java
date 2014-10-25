package com.techstorm.karaokehug;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTabs() ;
    }

    private void setTabs()
	{
		addTab("Search", R.drawable.tab_search, ArrowsActivity.class);
		addTab("Song", R.drawable.tab_song, OptionsActivity.class);
		
		addTab("Favorite", R.drawable.tab_favorite, ArrowsActivity.class);
		addTab("Help", R.drawable.tab_help, OptionsActivity.class);
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
