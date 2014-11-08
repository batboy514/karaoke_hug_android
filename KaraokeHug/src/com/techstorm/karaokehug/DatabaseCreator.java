package com.techstorm.karaokehug;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

public class DatabaseCreator {

	public static SQLiteDatabase database;
	
	public static SQLiteDatabase openDatabase(Context context) {
		DatabaseHelper myDbHelper = new DatabaseHelper(context);

		try {
			myDbHelper.createDataBase();

		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			database = myDbHelper.openDatabase();

		} catch (SQLException sqle) {
			throw sqle;
		}
		
		return database;
	}

	public static void getMapPath() {
		Cursor c = SqliteExecutor.queryStatement(database, "ZSONG", "*", "Z_PK = 1" );
		//If Cursor is valid
    	if (c != null ) {
    		//Move cursor to first row
    		if  (c.moveToFirst()) {
    			do {
    				String columnIndex = c.getString(c.getColumnIndex("ZSLYRIC"));
    				

    			}while (c.moveToNext()); //Move to next row
    		}
    	}
    	
	}
	
	public static void getSearchData(int b, String c, 
			ArrayList<String> user_name,
			ArrayList<String> user_id,
			ArrayList<String> user_lyric,
			ArrayList<String> user_author
			) {


		String query = "SELECT ZSNAME,ZROWID,ZSLYRIC,ZSMETA FROM ZSONG";
		query = query + " WHERE ZSNAMECLEAN like '" + c + "%'";
		String query1 = "SELECT ZSNAME,ZROWID,ZSLYRIC,ZSMETA FROM ZSONG";
		query1 = query1 + " WHERE ZROWID = '" + b + "'";
		String query10 = "SELECT ZSNAME,ZROWID,ZSLYRIC,ZSMETA FROM ZSONG";
		query10 = query10 + " WHERE ZSABBR like '" + c + "%'";
		Cursor mCursor = database.rawQuery(query1, null);
		Cursor mCursor2 = database.rawQuery(query, null);
		Cursor mCursor3 = database.rawQuery(query10, null);

		user_name.clear();
		user_id.clear();
		user_lyric.clear();
		user_author.clear();
		if (mCursor.moveToFirst()) {
			do {
				user_id.add(mCursor.getString(mCursor.getColumnIndex("ZROWID")));
				user_name.add(mCursor.getString(mCursor
						.getColumnIndex("ZSNAME")));
				user_lyric.add(mCursor.getString(mCursor
						.getColumnIndex("ZSLYRIC")));
				user_author.add(mCursor.getString(mCursor
						.getColumnIndex("ZSMETA")));
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		if (mCursor2.moveToFirst()) {
			do {
				user_id.add(mCursor2.getString(mCursor2
						.getColumnIndex("ZROWID")));
				user_name.add(mCursor2.getString(mCursor2
						.getColumnIndex("ZSNAME")));
				user_lyric.add(mCursor2.getString(mCursor2
						.getColumnIndex("ZSLYRIC")));
				user_author.add(mCursor2.getString(mCursor2
						.getColumnIndex("ZSMETA")));
			} while (mCursor2.moveToNext());
		}
		mCursor2.close();
		if (mCursor3.moveToFirst()) {
			do {
				user_id.add(mCursor3.getString(mCursor3
						.getColumnIndex("ZROWID")));
				user_name.add(mCursor3.getString(mCursor3
						.getColumnIndex("ZSNAME")));
				user_lyric.add(mCursor3.getString(mCursor3
						.getColumnIndex("ZSLYRIC")));
				user_author.add(mCursor3.getString(mCursor3
						.getColumnIndex("ZSMETA")));
			} while (mCursor3.moveToNext());
		}
		mCursor3.close();
	}
}
	