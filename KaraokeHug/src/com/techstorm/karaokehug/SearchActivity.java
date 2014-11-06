package com.techstorm.karaokehug;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity implements OnClickListener{
	private SQLiteDatabase dataBase;
	 private DatabaseHelper mHelper;
	private int b ;
	private String c ;
	private ArrayList<String> user_name = new ArrayList<String>();
	private ArrayList<String> user_id = new ArrayList<String>();
	private ArrayList<String> user_lyric = new ArrayList<String>();
	private ArrayList<String> user_author = new ArrayList<String>();
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earch);
        mHelper = new DatabaseHelper(this);
        Button btnsearch = (Button) findViewById(R.id.btnsearch);        
        btnsearch.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			EditText textsearch = (EditText) findViewById(R.id.textsearch);
		
					
			String k = textsearch.getText().toString();
			if(k.matches("[0-9]+")) {
			    // mEditText only contains numbers
				int value = Integer.parseInt(textsearch.getText().toString());
				b = value;
				displayData();
			} else {
			    // mEditText contains number + text, or text only. 
				String kitu = textsearch.getText().toString();
				c = kitu;
				displayData();
			}
			
			

		}
	});
        final ListView userlistsearch = (ListView) findViewById(R.id.userlistfavourite);
        userlistsearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Map<String, Object> map = (Map<String, Object>)userlistsearch.getItemAtPosition(arg2);
				// TODO Auto-generated method stub
				 Intent intent = new Intent();
				 
				 intent.putExtra("name", (String)map.get("name"));
				 intent.putExtra("id", (String)map.get("id"));
				 intent.putExtra("lyric", (String)map.get("lyric"));
				 intent.putExtra("author", (String)map.get("author"));
      intent.setClass(getApplicationContext(), ArrowsActivity.class);
		            startActivity(intent);			

			}
		});
        
        
        
        
	}
	
	
	private void displayData( ) {
		ListView userlistsearch = (ListView) findViewById(R.id.userlistfavourite);
		dataBase = mHelper.getWritableDatabase();
		mHelper.openDatabase();
		String query = "SELECT ZSNAME,ZROWID,ZSLYRIC,ZSMETA FROM ZSONG";
		query = query + " WHERE ZSNAMECLEAN like '" + c +"%'" ;
		String query1 = "SELECT ZSNAME,ZROWID,ZSLYRIC,ZSMETA FROM ZSONG";
		query1 = query1 + " WHERE ZROWID = '" + b +"'" ;
		String query10 = "SELECT ZSNAME,ZROWID,ZSLYRIC,ZSMETA FROM ZSONG";
		query10 = query10 + " WHERE ZSABBR like '" + c +"%'" ;
		Cursor mCursor = dataBase.rawQuery( query1 , null);
		Cursor mCursor2 = dataBase.rawQuery( query , null);
		Cursor mCursor3 = dataBase.rawQuery( query10 , null);

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
					DisplaySearch disadpt = new DisplaySearch(SearchActivity.this,user_id, user_name, user_lyric, user_author);
					userlistsearch.setAdapter(disadpt);
					mCursor.close();
			if (mCursor2.moveToFirst()) {
				do {
					user_id.add(mCursor2.getString(mCursor2.getColumnIndex("ZROWID")));
					user_name.add(mCursor2.getString(mCursor2.getColumnIndex("ZSNAME")));
					user_lyric.add(mCursor2.getString(mCursor2.getColumnIndex("ZSLYRIC")));
					user_author.add(mCursor2.getString(mCursor2.getColumnIndex("ZSMETA")));
				} while (mCursor2.moveToNext());
			}
					DisplaySearch disadpt2 = new DisplaySearch(SearchActivity.this,user_id, user_name, user_lyric, user_author);
					userlistsearch.setAdapter(disadpt2);
					mCursor2.close();
					if (mCursor3.moveToFirst()) {
						do {
							user_id.add(mCursor3.getString(mCursor3.getColumnIndex("ZROWID")));
							user_name.add(mCursor3.getString(mCursor3.getColumnIndex("ZSNAME")));
							user_lyric.add(mCursor3.getString(mCursor3.getColumnIndex("ZSLYRIC")));
							user_author.add(mCursor3.getString(mCursor3.getColumnIndex("ZSMETA")));
						} while (mCursor3.moveToNext());
					}
							DisplaySearch disadpt3 = new DisplaySearch(SearchActivity.this,user_id, user_name, user_lyric, user_author);
							userlistsearch.setAdapter(disadpt3);
							mCursor3.close();


	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
