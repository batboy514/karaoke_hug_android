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
import android.widget.ListView;

public class FavouriteActivity extends Activity {
	 private DatabaseHelper mHelper;
	 private SQLiteDatabase dataBase;
	 ListView userlistfavourite;
	 private ArrayList<String> user_name = new ArrayList<String>();
		private ArrayList<String> user_id = new ArrayList<String>();
		

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);
        userlistfavourite = (ListView) findViewById(R.id.userlistfavourite);
        mHelper = new DatabaseHelper(this);
        dataBase = mHelper.getWritableDatabase();
    	mHelper.openDatabase();
    	displayDataALL();
        userlistfavourite.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				 userlistfavourite = (ListView) findViewById(R.id.userlistfavourite);
				Map<String, Object> map = (Map<String, Object>)userlistfavourite.getItemAtPosition(arg2);
				// TODO Auto-generated method stub
				 Intent intent = new Intent();
				 
				 intent.putExtra("name", (String)map.get("name"));
				 intent.putExtra("id", (String)map.get("id"));
      intent.setClass(getApplicationContext(), ArrowsActivity.class);
		            startActivity(intent);			
			}
		});
        
        
        
	}
	
	
	public void displayDataALL( ) {
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
					userlistfavourite.setAdapter(disadpt2);
					mCursor.close();
				
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		displayDataALL();
	}
}
