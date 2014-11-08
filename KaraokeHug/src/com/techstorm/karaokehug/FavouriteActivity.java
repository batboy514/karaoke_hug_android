package com.techstorm.karaokehug;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

public class FavouriteActivity extends Activity implements OnItemSelectedListener{
	 private DatabaseHelper mHelper;
	 private SQLiteDatabase dataBase;
	 ListView userlistfavourite;
	 private ArrayList<String> user_name = new ArrayList<String>();
		private ArrayList<String> user_id = new ArrayList<String>();
		private ArrayList<String> user_lyric = new ArrayList<String>();
		private ArrayList<String> user_author = new ArrayList<String>();
		

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);
        userlistfavourite = (ListView) findViewById(R.id.userlistfavourite);
        mHelper = new DatabaseHelper(this);
        dataBase = mHelper.getWritableDatabase();
    	mHelper.openDatabase();
    	displayDataALL();
    	userlistfavourite.setClickable(true);
    	userlistfavourite.setOnItemSelectedListener(this);
    	userlistfavourite.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        
	}
	
	
	public void displayDataALL( ) {
		dataBase = mHelper.getWritableDatabase();
		mHelper.openDatabase();
		String query = "SELECT ZSNAME,ZROWID,ZSLYRIC,ZSMETA FROM ZSONG where favourite = 1";
//		if (vol != -1) {
			
			Cursor mCursor = dataBase.rawQuery( query , null);
			user_name.clear();
			user_id.clear();
			user_lyric.clear();
			user_author.clear();
			if (mCursor.moveToFirst()) {
				do {
					user_id.add(mCursor.getString(mCursor.getColumnIndex("ZROWID")));
					user_name.add(mCursor.getString(mCursor.getColumnIndex("ZSNAME")));
					user_lyric.add(mCursor.getString(mCursor.getColumnIndex("ZSLYRIC")));
					user_author.add(mCursor.getString(mCursor.getColumnIndex("ZSMETA")));
				} while (mCursor.moveToNext());
			}
			DisplayFavourite disadpt2 = new DisplayFavourite(FavouriteActivity.this,user_id, user_name, user_lyric, user_author);
					userlistfavourite.setAdapter(disadpt2);
					mCursor.close();
				
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		displayDataALL();
	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		userlistfavourite = (ListView) findViewById(R.id.userlistfavourite);
		Map<String, Object> map = (Map<String, Object>)userlistfavourite.getItemAtPosition(arg2);
		// TODO Auto-generated method stub
		 Intent intent = new Intent();
		 intent.putExtra("lyric", (String)map.get("lyric"));
		 intent.putExtra("author", (String)map.get("author"));
		 intent.putExtra("name", (String)map.get("name"));
		 intent.putExtra("id", (String)map.get("id"));
intent.setClass(getApplicationContext(), ArrowsActivity.class);
            startActivity(intent);
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
