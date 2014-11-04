package com.techstorm.karaokehug;

import java.util.ArrayList;
import java.util.List;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * @author 
 *
 */
public class OptionsActivity extends Activity implements OnItemSelectedListener{
	
	 private DatabaseHelper mHelper;
		private ArrayList<String> user_name = new ArrayList<String>();
		private ArrayList<String> user_lyric = new ArrayList<String>();
		private ArrayList<String> user_author = new ArrayList<String>();
		private ArrayList<String> user_id = new ArrayList<String>();
		private ArrayList<String> user_vol = new ArrayList<String>();
        ArrayList<String> list = new ArrayList<String>();
        ArrayAdapter<String> adapter,listadapter;
        private char b = 'a';
        private Integer c;
		private ListView userList,userlist1;
		private SQLiteDatabase dataBase;
		private Cursor p;
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// On selecting a spinner item
	
			String item = parent.getItemAtPosition(position).toString();
			c=Integer.parseInt(item);
			displayDataALL();
						// Showing selected spinner item
			Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
			

				
			
		}
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionspage);
		char c = 'a';
        userList = (ListView) findViewById(R.id.list1);
		String s = null;
		mHelper = new DatabaseHelper(this);
		displayData(0);
		displayData1(c);

    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    Spinner spinner1 = (Spinner) findViewById(R.id.Spinner01);
       // Spinner click listener
    spinner.setOnItemSelectedListener(this);
    spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
    @Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		char item1 = arg0.getItemAtPosition(arg2).toString().charAt(0);
		
		b=arg0.getItemAtPosition(arg2).toString().charAt(0);
		displayDataALL();
		// Showing selected spinner item
		Toast.makeText(arg0.getContext(), "Selected: " + item1, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
});
    
    dataBase = mHelper.getWritableDatabase();
	mHelper.openDatabase();
	Cursor mCursor = dataBase.rawQuery("SELECT COUNT(ZSVOL),ZSVOL FROM ZSONG GROUP BY ZSVOL" , null);
	Cursor mCursor1 = dataBase.rawQuery("SELECT COUNT(ZSABBR),ZSABBR FROM ZSONG GROUP BY ZSABBR" , null);
    // Spinner Drop down elements
    List<String> categories = new ArrayList<String>();
    if (mCursor.moveToFirst()) {
		do {
			categories.add(mCursor.getString(mCursor.getColumnIndex("ZSVOL")));

		} while (mCursor.moveToNext());
	}

			mCursor.close();
			List<String> categories1 = new ArrayList<String>();
		    for (char character = 'a'; character < 'z'; character++) {
		    	categories1.add(String.valueOf(character));
		    	
			}
   
    
    // Creating adapter for spinner
	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
	ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);
	// Drop down layout style - list view with radio button
	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	
	// attaching data adapter to spinner
	spinner.setAdapter(dataAdapter);
	spinner1.setAdapter(dataAdapter1);
		userList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Map<String, Object> map = (Map<String, Object>)userList.getItemAtPosition(arg2);
				// TODO Auto-generated method stub
				 Intent intent = new Intent();
				 
				 intent.putExtra("name", (String)map.get("name"));
				 intent.putExtra("id", (String)map.get("id"));
				 intent.putExtra("lyric", (String)map.get("lyric"));
				 intent.putExtra("author", (String)map.get("author"));

		            intent.setClass(getApplicationContext(), ArrowsActivity.class);
		            startActivity(intent);			
			}});

	}




private void displayData(int vol) {
	dataBase = mHelper.getWritableDatabase();
	mHelper.openDatabase();
	
	String query = "SELECT ZSNAME,ZROWID,ZSLYRIC,ZSMETA FROM ZSONG";
//	if (vol != -1) {
		query = query + " WHERE ZSVOL = " + vol;
//	}
		
//		}
	Cursor mCursor = dataBase.rawQuery( query , null);
	

	user_name.clear();
	user_id.clear();
	
	user_author.clear();
	user_lyric.clear();
	if (mCursor.moveToFirst()) {
		do {
			user_id.add(mCursor.getString(mCursor.getColumnIndex("ZROWID")));
			user_name.add(mCursor.getString(mCursor.getColumnIndex("ZSNAME")));
			user_lyric.add(mCursor.getString(mCursor.getColumnIndex("ZSLYRIC")));
			user_author.add(mCursor.getString(mCursor.getColumnIndex("ZSMETA")));
		} while (mCursor.moveToNext());
	}
			DisplayAdapter disadpt = new DisplayAdapter(OptionsActivity.this,user_id, user_name, user_lyric, user_author);
			userList.setAdapter(disadpt);
			mCursor.close();
			
			

}
private void displayData1(char c ) {
	dataBase = mHelper.getWritableDatabase();
	mHelper.openDatabase();
	String query1 = "SELECT ZSNAME,ZROWID,ZSLYRIC,ZSMETA FROM ZSONG";
//	if (vol != -1) {
		query1 = query1 + " WHERE ZSABBR like '" + c +"%'";
		Cursor mCursor1 = dataBase.rawQuery( query1 , null);
		user_name.clear();
		user_id.clear();
		user_lyric.clear();
		user_author.clear();
		
		if (mCursor1.moveToFirst()) {
			do {
				user_id.add(mCursor1.getString(mCursor1.getColumnIndex("ZROWID")));
				user_name.add(mCursor1.getString(mCursor1.getColumnIndex("ZSNAME")));
				user_lyric.add(mCursor1.getString(mCursor1.getColumnIndex("ZSLYRIC")));
				user_author.add(mCursor1.getString(mCursor1.getColumnIndex("ZSMETA")));
			} while (mCursor1.moveToNext());
		}
				DisplayAdapter disadpt1 = new DisplayAdapter(OptionsActivity.this,user_id, user_name, user_lyric, user_author);
				userList.setAdapter(disadpt1);
				mCursor1.close();
	
}
private void displayDataALL( ) {
	dataBase = mHelper.getWritableDatabase();
	mHelper.openDatabase();
	String query2 = "SELECT ZSNAME,ZROWID,ZSLYRIC,ZSMETA FROM ZSONG";
//	if (vol != -1) {
		query2 = query2 + " WHERE ZSABBR like '" + b +"' and ZSVOL <= '" + c +"'";
		Cursor mCursor2 = dataBase.rawQuery( query2 , null);
		user_name.clear();
		user_id.clear();
		user_lyric.clear();
		user_author.clear();
		if (mCursor2.moveToFirst()) {
			do {
				user_id.add(mCursor2.getString(mCursor2.getColumnIndex("ZROWID")));
				user_name.add(mCursor2.getString(mCursor2.getColumnIndex("ZSNAME")));
				user_lyric.add(mCursor2.getString(mCursor2.getColumnIndex("ZSLYRIC")));
				user_author.add(mCursor2.getString(mCursor2.getColumnIndex("ZSMETA")));
			} while (mCursor2.moveToNext());
		}
				DisplayAdapter disadpt2 = new DisplayAdapter(OptionsActivity.this,user_id, user_name, user_lyric, user_author);
				userList.setAdapter(disadpt2);
				mCursor2.close();
			
}
}
