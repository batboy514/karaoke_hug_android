package com.techstorm.karaokehug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.techstorm.karaokehug.entities.SongEntity;

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
	
	public static SongEntity getSongBySongId(int songId) {
		String tableName = "ZSONG";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA,ZSABBR,favourite";
		String where = "ZROWID = " + songId;
		Cursor mCursor2 = SqliteExecutor.queryStatement(database, tableName,
				select, where);
		SongEntity song = null;
		if (mCursor2.moveToFirst()) {
			song = new SongEntity(
				mCursor2.getInt(mCursor2
						.getColumnIndex("ZROWID")), 
				mCursor2.getString(mCursor2
						.getColumnIndex("ZSNAME")), 
				mCursor2.getString(mCursor2
						.getColumnIndex("ZSLYRIC")), 
				mCursor2.getString(mCursor2
						.getColumnIndex("ZSMETA")), 
				"Arirang 5 số (hầu hết các quán)", 
				mCursor2.getString(mCursor2
						.getColumnIndex("ZSABBR")),
				mCursor2.getShort(mCursor2
						.getColumnIndex("favourite")));
		}
		return song;
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

	public static void spinnerDataVol(String prefix, List<String> categories) {
		String tableName = " ZSONG ";
		String select = " COUNT(ZSVOL),ZSVOL ";
		String where = "ZSVOL > 0";
		String groupby = " ZSVOL ";
		Cursor mCursor = SqliteExecutor.queryStatement(database, tableName,
				select, where, groupby);
		if (mCursor.moveToFirst()) {
			do {
				categories.add(prefix + mCursor.getString(mCursor
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
