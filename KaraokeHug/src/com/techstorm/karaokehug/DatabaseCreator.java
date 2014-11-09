package com.techstorm.karaokehug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
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
			database = myDbHelper.getWritableDatabase();

		} catch (SQLException sqle) {
			throw sqle;
		}

		return database;
	}

	@SuppressLint("DefaultLocale")
	public static void getSearchData(Integer searchVol, String searchString,
			ArrayList<String> user_name, ArrayList<String> user_id,
			ArrayList<String> user_lyric, ArrayList<String> user_author) {

		String tableName = "ZSONG";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
		String where = "1";
		if (searchVol != null) {
			where = "cast(ZROWID as text) like '" + searchVol + "%'";
			
			
		} else {
			String search = searchString.toLowerCase();
			where = "  ZSNAMECLEAN like '" + search + "%' " +
					"or ZSLYRICCLEAN like '" + search + "%' " +
					"or ZSABBR like '" + search + "%'";
		}
		
		Cursor mCursor2 = SqliteExecutor.queryStatement(database, tableName,
				select, where);
		user_name.clear();
		user_id.clear();
		user_lyric.clear();
		user_author.clear();

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
	}

	public static void getSongData(Character abcSearch, int vol, ArrayList<String> user_name,
			ArrayList<String> user_id, ArrayList<String> user_lyric,
			ArrayList<String> user_author) {
		String tableName = "ZSONG";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
		String where = "ZSVOL <= " + vol;
		if (abcSearch != null) {
			where += " AND ZSABBR like '" + abcSearch + "%'";
		}
		Cursor mCursor2 = SqliteExecutor.queryStatement(database, tableName,
				select, where);
		user_name.clear();
		user_id.clear();
		user_lyric.clear();
		user_author.clear();
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
	}

	public static void getFavouriteData(ArrayList<String> user_name,
			ArrayList<String> user_id, ArrayList<String> user_lyric,
			ArrayList<String> user_author) {
		String tableName = "ZSONG";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
		String where = "favourite = 1";
		Cursor mCursor = SqliteExecutor.queryStatement(database, tableName,
				select, where);
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

	}

	public static void spinnerDataVol(List<String> categories) {
		String tableName = " ZSONG ";
		String select = " COUNT(ZSVOL),ZSVOL ";
		String groupby = " ZSVOL ";
		Cursor mCursor = SqliteExecutor.queryStatement(database, tableName,
				select, "1", groupby);
		if (mCursor.moveToFirst()) {
			do {
				categories.add(mCursor.getString(mCursor
						.getColumnIndex("ZSVOL")));

			} while (mCursor.moveToNext());
		}

	}

	public static void addFavourite(int value) {
		String tableName = "ZSONG";
		String setting = "favourite = 1";
		String criterial = "ZROWID = " + value;
		SqliteExecutor.updateStatement(database, tableName, setting, criterial);

	}

	public static void delFavourite(int value) {
		String tableName = "ZSONG";
		String setting = "favourite = 0";
		String criterial = "ZROWID = " + value;
		SqliteExecutor.updateStatement(database, tableName, setting, criterial);

	}
}
