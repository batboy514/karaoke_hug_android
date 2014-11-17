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
			where = "cast(ZROWID as text) like '" + searchVol + "%'";

		} else {
			String search = searchString.toLowerCase();
			where = "  ZSNAMECLEAN like '" + search + "%' "
					+ "or ZSLYRICCLEAN like '" + search + "%' "
					+ "or ZSABBR like '" + search + "%'";
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

	public static SongEntity getSongBySongId(int songId) {
		String tableName = "ZSONG";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA,ZSABBR,favourite";
		String where = "ZROWID = " + songId;
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
					mCursor2.getString(mCursor2.getColumnIndex("ZSABBR")),
					mCursor2.getShort(mCursor2.getColumnIndex("favourite")));
		}
		return song;
	}

	public static Boolean getFavouriteData(ArrayList<String> user_name,
			ArrayList<String> user_id, ArrayList<String> user_lyric,
			ArrayList<String> user_author) {
		String productchoice = SettingActivity.itemproductselected;
		if (productchoice != null) {
			String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
			String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
			String where = "favourite = 1 and Z_NAME like '" + productchoice + "'";
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
				mCursor.close();
				return true;
			} 
			mCursor.close();
		}
		
		return false;

	}

	public static Boolean spinnerDataVol(String prefix, List<String> categories) {
		String productchoice = SettingActivity.itemproductselected;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = " COUNT(ZSVOL),ZSVOL ";
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
