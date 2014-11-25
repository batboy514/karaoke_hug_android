package com.techstorm.karaokehug.activities;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Toast;

import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.DisplayFavourite;
import com.techstorm.karaokehug.R;

public class FavouriteActivity extends Activity implements OnItemSelectedListener{

	 static ListView userlistFavourite;
	public static ArrayList<String> userName = new ArrayList<String>();
	public static ArrayList<String> userId = new ArrayList<String>();
	public static ArrayList<String> userLyric = new ArrayList<String>();
	public static ArrayList<String> userAuthor = new ArrayList<String>();
		int backButtonCount = 0;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_favourite);
        userlistFavourite = (ListView) findViewById(R.id.userlistfavourite);
       
    	displayDataALL();
    	userlistFavourite.setClickable(true);
    	userlistFavourite.setOnItemSelectedListener(this);
    	userlistFavourite.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    	
        
	}
	
	
	public void displayDataALL( ) {
		DatabaseCreator.getFavouriteData(userName, userId, userLyric, userAuthor); 	
		
		 DatabaseCreator.getFavouriteData(userName, userId, userLyric, userAuthor);
		DisplayFavourite disadpt2 = new DisplayFavourite(FavouriteActivity.this,userId, userName, userLyric, userAuthor);
					userlistFavourite.setAdapter(disadpt2);
		}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		displayDataALL();
	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		userlistFavourite = (ListView) findViewById(R.id.userlistfavourite);
		Map<String, Object> map = (Map<String, Object>)userlistFavourite.getItemAtPosition(arg2);
		 Intent intent = new Intent();
		 intent.putExtra("lyric", (String)map.get("lyric"));
		 intent.putExtra("author", (String)map.get("author"));
		 intent.putExtra("name", (String)map.get("name"));
		 intent.putExtra("id", (String)map.get("id"));
intent.setClass(getApplicationContext(), SongDetailActivity.class);
            startActivity(intent);
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	public void onBackPressed() {
		 new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle(this.getApplicationContext().getString(R.string.closeapp))
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
}

