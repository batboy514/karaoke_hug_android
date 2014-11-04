package com.techstorm.karaokehug;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
}
	