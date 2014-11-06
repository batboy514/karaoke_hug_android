package com.techstorm.karaokehug;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class FavouriteActivity extends Activity {
	 private DatabaseHelper mHelper;
	 private SQLiteDatabase dataBase;
	 ListView userlistsearch;
	 private ArrayList<String> user_name = new ArrayList<String>();
		private ArrayList<String> user_id = new ArrayList<String>();
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);
        userlistsearch = (ListView) findViewById(R.id.userlistfavourite);
        mHelper = new DatabaseHelper(this);
        dataBase = mHelper.getWritableDatabase();
    	mHelper.openDatabase();
    	displayDataALL();
        
        
        
        
	}
	
	
	private void displayDataALL( ) {
		dataBase = mHelper.getWritableDatabase();
		mHelper.openDatabase();
		String query = "SELECT ZSNAME,ZROWID FROM ZSONG where favourite = 1";
//		if (vol != -1) {
			
			Cursor mCursor = dataBase.rawQuery( query , null);
			user_name.clear();
			user_id.clear();
			
			if (mCursor.moveToFirst()) {
				do {
					user_id.add(mCursor.getString(mCursor.getColumnIndex("ZROWID")));
					user_name.add(mCursor.getString(mCursor.getColumnIndex("ZSNAME")));
					
				} while (mCursor.moveToNext());
			}
			DisplayFavourite disadpt2 = new DisplayFavourite(FavouriteActivity.this,user_id, user_name);
					userlistsearch.setAdapter(disadpt2);
					mCursor.close();
				
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		displayDataALL();
	}
}
