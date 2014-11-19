package com.techstorm.karaokehug;

import java.io.IOException;
import java.text.ChoiceFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.techstorm.karaokehug.activities.SettingActivity;
import com.techstorm.karaokehug.entities.SaveEntity;
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
	public static Boolean getSearchData(Integer searchVol, String searchString,
			ArrayList<String> user_name, ArrayList<String> user_id,
			ArrayList<String> user_lyric, ArrayList<String> user_author) {
		String productchoice = SettingActivity.itemproductselected;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
		String where = "Z_NAME like '" + productchoice + "'";
		if (searchVol != null) {
			where = "Z_NAME like '"
					+ productchoice + "'";

		} else {
			String search = searchString.toLowerCase();
			where = "  (ZSNAMECLEAN like '" + search + "%' "
					+ "or ZSLYRICCLEAN like '" + search + "%' "
					+ "or ZSABBR like '" + search + "%') and Z_NAME like '"
					+ productchoice + "'";
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
		} else {
			// mCursor2.close();
			return false;
		}
		mCursor2.close();

		return true;
	}

	public static Boolean getSongDataVietNam(Character abcSearch, Integer vol,
			ArrayList<String> user_name, ArrayList<String> user_id,
			ArrayList<String> user_lyric, ArrayList<String> user_author) {
		String productchoice = SettingActivity.itemproductselected;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
		String where = "ZSVOL <= " + vol
				+ " and ZSLANGUAGE like 'vn' and Z_NAME like '" + productchoice
				+ "'";

		if (abcSearch != null) {
			where += " AND ZSABBR like '" + abcSearch
					+ "%' and ZSLANGUAGE like 'vn' and Z_NAME like '"
					+ productchoice + "'";
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
			mCursor2.close();
			return true;
		}
		mCursor2.close();
		return false;
	}

	public static Boolean getSongDataEngLish(Character abcSearch, Integer vol,
			ArrayList<String> user_name, ArrayList<String> user_id,
			ArrayList<String> user_lyric, ArrayList<String> user_author) {
		String productchoice = SettingActivity.itemproductselected;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
		String where = "ZSVOL <= " + vol
				+ " and ZSLANGUAGE like 'en' and Z_NAME like '" + productchoice
				+ "'";
		if (abcSearch != null) {
			where += " AND ZSABBR like '" + abcSearch
					+ "%' and ZSLANGUAGE like 'en' and Z_NAME like '"
					+ productchoice + "'";
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
			mCursor2.close();
			return true;
		}
		mCursor2.close();
		return false;
	}

	public static Boolean getSongDataAll(Character abcSearch, Integer vol,
			ArrayList<String> user_name, ArrayList<String> user_id,
			ArrayList<String> user_lyric, ArrayList<String> user_author) {
		String productchoice = SettingActivity.itemproductselected;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
		String where = "Z_NAME like '" + productchoice + "'";
		if (vol != null) {
			where += " AND ZSVOL <= " + vol + " ";
		}
		if (abcSearch != null) {
			where += " AND ZSABBR like '" + abcSearch + "%' and Z_NAME like '"
					+ productchoice + "'";
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
			mCursor2.close();
			return true;
		}
		mCursor2.close();
		return false;
	}
	public static Boolean getSongDataNumber(Character abcSearch, Integer vol,
			ArrayList<String> user_name, ArrayList<String> user_id,
			ArrayList<String> user_lyric, ArrayList<String> user_author) {
		String productchoice = SettingActivity.itemproductselected;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
		String where = "ZSNAMECLEAN like '1%' and  Z_NAME like '" + productchoice 	+ "'";
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
			mCursor2.close();
			return true;
		}
		mCursor2.close();
		return false;
	}
	public static SongEntity getSongBySongId(int songId) {
		String productchoice = SettingActivity.itemproductselected;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA,ZSABBR";
		String where = "ZROWID = " + songId + " and Z_NAME like '" + productchoice + "'";
		Cursor mCursor2 = SqliteExecutor.queryStatement(database, tableName,
				select, where);
		SongEntity song = null;
		if (mCursor2.moveToFirst()) {
			song = new SongEntity(mCursor2.getInt(mCursor2
					.getColumnIndex("ZROWID")), mCursor2.getString(mCursor2
					.getColumnIndex("ZSNAME")), mCursor2.getString(mCursor2
					.getColumnIndex("ZSLYRIC")), mCursor2.getString(mCursor2
					.getColumnIndex("ZSMETA")),
					"Arirang 5 số (hầu hết các quán)",
					mCursor2.getString(mCursor2.getColumnIndex("ZSABBR")));
		}
		return song;
	}
	
	public static boolean isFavourite(int songId) {
		String tableName = "ZFAVORITE";
		String select = "*";
		String where = "ZROWID = " + songId;
		Cursor mCursor2 = SqliteExecutor.queryStatement(database, tableName,
				select, where);
		if (mCursor2.moveToFirst()) {
			return true;
		}
		return false;
	}

	public static Boolean getFavouriteData(ArrayList<String> user_name,
			ArrayList<String> user_id, ArrayList<String> user_lyric,
			ArrayList<String> user_author) {
			String tableName = " ZFAVORITE  ";
			String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
			String where = " 1 ";
			;
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
				return true;
				
			} 
			mCursor.close();
			return false;
	}

	public static Boolean spinnerDataVol(String prefix, List<String> categories) {
		String productchoice = SettingActivity.itemproductselected;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = " ZSVOL ";
		String where = "ZSVOL > 0 and Z_NAME like '" + productchoice + "'";
		String groupby = " ZSVOL ";
		String orderby = " ZSVOL DESC";
		Cursor mCursor = SqliteExecutor.queryStatement(database, tableName,
				select, where, groupby, orderby);
		categories.clear();
		if (mCursor.moveToFirst()) {
			do {
				categories.add(productchoice + " "
						+ mCursor.getString(mCursor.getColumnIndex("ZSVOL")));

			} while (mCursor.moveToNext());
			return true;
		}
		return false;
	}
	public static Boolean spinnerDataVolDefault(String prefix, List<String> categories) {
		String productchoice = SettingActivity.itemproductselected;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = " ZSVOL ";
		String where = "ZSVOL > 0 and Z_NAME like '" + productchoice + "'";
		String groupby = " ZSVOL ";
		String orderby = " ZSVOL DESC";
		Cursor mCursor = SqliteExecutor.queryStatement(database, tableName,
				select, where, groupby, orderby);
		categories.clear();
		if (mCursor.moveToFirst()) {
			do {
				categories.add(productchoice + " "
						+ mCursor.getString(mCursor.getColumnIndex("ZSVOL")));

			} while (mCursor.moveToNext());
			return true;
		}
		return false;
	}

	public static void spinnerDataProduct(List<String> categories1) {
		String tableName = " Z_PRIMARYKEY ";
		String select = " Z_NAME ";
		String where = " 1 ";
		String groupby = " 1 ";
		String orderby = " 1 ";
		Cursor mCursor = SqliteExecutor.queryStatement(database, tableName,
				select, where, groupby, orderby);
		if (mCursor.moveToFirst()) {
			do {
				categories1.add(mCursor.getString(mCursor
						.getColumnIndex("Z_NAME")));

			} while (mCursor.moveToNext());
		}

	}

	public static void addFavourite(SongEntity song) {
		int id = song.getSongId();
		String name = song.getName();
		String author = song.getAuthor();
		String lyric = song.getLyric();
		String quicksearch = song.getQuickSearch();
		String tableName = "ZFAVORITE(ZROWID,ZSNAME,ZSLYRIC,ZSMETA,ZSABBR)";
//		String values = "" + id + "," + name + "," + lyric 
//				+ "," + author + "," + quicksearch;
		SqliteExecutor.insertStatement(database, tableName, String.valueOf(id), name, lyric, author, quicksearch);

	}

	public static void delFavourite(SongEntity song) {
		int id = song.getSongId();
		String tableName = "ZFAVORITE";
		String criterial = "ZROWID = " + id;
		SqliteExecutor.deleteStatement(database, tableName, criterial);

	}
	public static void saveMedia(){
		String tableName = " Z_CONFIG ";
		String a = SettingActivity.itemproductselected;
		String setting = " Z_MEDIACHOICE = '" + a + "'";
		SqliteExecutor.updateStatement(database, tableName, setting);
	}
	public static SaveEntity showMedia(){
		String tableName = "Z_CONFIG";
		String select = " Z_MEDIACHOICE,Z_LANGUAGE ";
		Cursor mCursor = SqliteExecutor.queryStatement(database, tableName, select);
		SaveEntity save = null;
		if (mCursor.moveToFirst()) {
			save = new SaveEntity(mCursor.getString(mCursor
					.getColumnIndex("Z_MEDIACHOICE")), mCursor.getString(mCursor
					.getColumnIndex("Z_LANGUAGE")));
		}
		return save;
		
	}
}
