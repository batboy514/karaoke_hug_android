package com.techstorm.karaokehug;

import java.io.IOException;
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
import com.techstorm.karaokehug.utilities.SqlCode;

public class DatabaseCreator {

	public static SQLiteDatabase DATABASE;
	
	public static SQLiteDatabase openDatabase(Context context) {
		DatabaseHelper myDbHelper = new DatabaseHelper(context);

		try {
			myDbHelper.createDataBase();

		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			DATABASE = myDbHelper.getWritableDatabase();

		} catch (SQLException sqle) {
			throw sqle;
		}

		return DATABASE;
	}

	@SuppressLint("DefaultLocale")
	public static Boolean getSearchData(Integer beginLimit, Integer countLimit, Integer searchVol, String searchString,
			ArrayList<String> userName, ArrayList<String> userId,
			ArrayList<String> userLyric, ArrayList<String> userAuthor) {
		String productchoice = SettingActivity.itemProductSelecTed;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
		String WHERE = "Z_NAME = '" + productchoice + "' ";
		if (searchVol != null) {
			WHERE += " AND ZROWID = "+ String.valueOf(searchVol) + " ";
		} else {
			String search = SqlCode.encode(searchString.toLowerCase());
			WHERE += "  AND (ZSNAMECLEAN like '" + search + "%' "
					+ "or ZSLYRICCLEAN like '" + search + "%' "
					+ "or ZSABBR like '" + search + "%')";
		}

		Cursor CURSORSEARCH = SqliteExecutor.queryStatement(DATABASE, tableName, select, WHERE, beginLimit, countLimit);

		if (CURSORSEARCH.moveToFirst()) {
			do {
				userId.add(CURSORSEARCH.getString(CURSORSEARCH
						.getColumnIndex("ZROWID")));
				userName.add(CURSORSEARCH.getString(CURSORSEARCH
						.getColumnIndex("ZSNAME")));
				userLyric.add(CURSORSEARCH.getString(CURSORSEARCH
						.getColumnIndex("ZSLYRIC")));
				userAuthor.add(CURSORSEARCH.getString(CURSORSEARCH
						.getColumnIndex("ZSMETA")));
			} while (CURSORSEARCH.moveToNext());
		} else {
			// mCursor2.close();
			return false;
		}
		CURSORSEARCH.close();

		return true;
	}
	
	public static Boolean getSongDataAll(Character abcSearch, Integer vol, Integer beginLimit, Integer countLimit, String languageCode,
			ArrayList<String> userName, ArrayList<String> userId,
			ArrayList<String> userLyric, ArrayList<String> userAuthor) {
		String productchoice = SettingActivity.itemProductSelecTed;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
		String WHERE = "Z_NAME like '" + productchoice + "'";
		
		if (languageCode != null) {
			WHERE += " AND ZSLANGUAGE = '" + languageCode + "' ";
		}
		if (vol != null) {
			WHERE += " AND ZSVOL <= " + vol + " ";
		}
		if (abcSearch != null) {
		if (abcSearch == '#') {
				WHERE += " AND substr(ZSABBR,1,1) GLOB '[0-9]' and Z_NAME like '"
						+ productchoice + "'";
			} else {
				WHERE += " AND ZSABBR like '" + abcSearch + "%' and Z_NAME like '"
						+ productchoice + "'";
			}
		}
		Cursor CURSORSONGDATA = SqliteExecutor.queryStatement(DATABASE, tableName,
				select, WHERE, beginLimit, countLimit);
		if (CURSORSONGDATA.moveToFirst()) {
			do {
				userId.add(CURSORSONGDATA.getString(CURSORSONGDATA
						.getColumnIndex("ZROWID")));
				userName.add(CURSORSONGDATA.getString(CURSORSONGDATA
						.getColumnIndex("ZSNAME")));
				userLyric.add(CURSORSONGDATA.getString(CURSORSONGDATA
						.getColumnIndex("ZSLYRIC")));
				userAuthor.add(CURSORSONGDATA.getString(CURSORSONGDATA
						.getColumnIndex("ZSMETA")));
			} while (CURSORSONGDATA.moveToNext());
			CURSORSONGDATA.close();
			return true;
		}
		CURSORSONGDATA.close();
		return false;
	}

	public static SongEntity getSongBySongId(int songId) {
		String productchoice = SettingActivity.itemProductSelecTed;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA,ZSABBR";
		String WHERE = "ZROWID = " + songId + " and Z_NAME like '" + productchoice + "'";
		Cursor CURSORSONGENTITY = SqliteExecutor.queryStatement(DATABASE, tableName,
				select, WHERE);
		SongEntity SONG = null;
		if (CURSORSONGENTITY.moveToFirst()) {
			SONG = new SongEntity(CURSORSONGENTITY.getInt(CURSORSONGENTITY
					.getColumnIndex("ZROWID")), CURSORSONGENTITY.getString(CURSORSONGENTITY
					.getColumnIndex("ZSNAME")), CURSORSONGENTITY.getString(CURSORSONGENTITY
					.getColumnIndex("ZSLYRIC")), CURSORSONGENTITY.getString(CURSORSONGENTITY
					.getColumnIndex("ZSMETA")),
					"Arirang 5 số (hầu hết các quán)",
					CURSORSONGENTITY.getString(CURSORSONGENTITY.getColumnIndex("ZSABBR")));
		}
		return SONG;
	}
	
	public static boolean isFavourite(int songId) {
		String tableName = "ZFAVORITE";
		String select = "*";
		String WHERE = "ZROWID = " + songId;
		Cursor CURSORFAVORITE = SqliteExecutor.queryStatement(DATABASE, tableName,
				select, WHERE);
		if (CURSORFAVORITE.moveToFirst()) {
			return true;
		}
		return false;
	}

	public static Boolean getFavouriteData(ArrayList<String> userName,
			ArrayList<String> userId, ArrayList<String> userLyric,
			ArrayList<String> userAuthor) {
			String tableName = " ZFAVORITE  ";
			String select = "ZSNAME,ZROWID,ZSLYRIC,ZSMETA";
			String WHERE = " 1 ";
			;
			Cursor CURSORFAVORITEDATA = SqliteExecutor.queryStatement(DATABASE, tableName,
					select, WHERE);
			userName.clear();
			userId.clear();
			userLyric.clear();
			userAuthor.clear();
			if (CURSORFAVORITEDATA.moveToFirst()) {
				do {
					userId.add(CURSORFAVORITEDATA.getString(CURSORFAVORITEDATA.getColumnIndex("ZROWID")));
					userName.add(CURSORFAVORITEDATA.getString(CURSORFAVORITEDATA
							.getColumnIndex("ZSNAME")));
					userLyric.add(CURSORFAVORITEDATA.getString(CURSORFAVORITEDATA
							.getColumnIndex("ZSLYRIC")));
					userAuthor.add(CURSORFAVORITEDATA.getString(CURSORFAVORITEDATA
							.getColumnIndex("ZSMETA")));
				} while (CURSORFAVORITEDATA.moveToNext());
				return true;
				
			} 
			CURSORFAVORITEDATA.close();
			return false;
	}

	public static Boolean spinnerDataVol(String prefix, List<String> categories) {
		String productchoice = SettingActivity.itemProductSelecTed;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = " ZSVOL ";
		String where = "ZSVOL > 0 and Z_NAME like '" + productchoice + "'";
		String groupby = " ZSVOL ";
		String orderby = " ZSVOL DESC";
		Cursor CURSORSPINNERDATAVOL = SqliteExecutor.queryStatement(DATABASE, tableName,
				select, where, groupby, orderby);
		categories.clear();
		if (CURSORSPINNERDATAVOL.moveToFirst()) {
			do {
				categories.add(productchoice + prefix
						+ CURSORSPINNERDATAVOL.getString(CURSORSPINNERDATAVOL.getColumnIndex("ZSVOL")));

			} while (CURSORSPINNERDATAVOL.moveToNext());
			return true;
		}
		return false;
	}
	public static Boolean spinnerDataVolDefault(String prefix, List<String> categories) {
		String productchoice = SettingActivity.itemProductSelecTed;
		String tableName = " ZSONG inner join Z_PRIMARYKEY on ZSONG.Z_ENT=Z_PRIMARYKEY.Z_ENT ";
		String select = " ZSVOL ";
		String where = "ZSVOL > 0 and Z_NAME like '" + productchoice + "'";
		String groupby = " ZSVOL ";
		String orderby = " ZSVOL DESC";
		Cursor CURSORSPINNERVOLDEFAULT = SqliteExecutor.queryStatement(DATABASE, tableName,
				select, where, groupby, orderby);
		categories.clear();
		if (CURSORSPINNERVOLDEFAULT.moveToFirst()) {
			do {
				categories.add(productchoice + " "
						+ CURSORSPINNERVOLDEFAULT.getString(CURSORSPINNERVOLDEFAULT.getColumnIndex("ZSVOL")));

			} while (CURSORSPINNERVOLDEFAULT.moveToNext());
			return true;
		}
		return false;
	}

	public static void spinnerDataProduct(List<String> categories) {
		String tableName = " Z_PRIMARYKEY ";
		String select = " Z_NAME ";
		String where = " 1 ";
		String groupby = " 1 ";
		String orderby = " 1 ";
		Cursor CURSORSPINNERDATAPRODUCT = SqliteExecutor.queryStatement(DATABASE, tableName,
				select, where, groupby, orderby);
		if (CURSORSPINNERDATAPRODUCT.moveToFirst()) {
			do {
				categories.add(CURSORSPINNERDATAPRODUCT.getString(CURSORSPINNERDATAPRODUCT
						.getColumnIndex("Z_NAME")));

			} while (CURSORSPINNERDATAPRODUCT.moveToNext());
		}

	}

	public static void addFavourite(SongEntity song) {
		int id = song.getSongId();
		String name = SqlCode.encode(song.getName());
		String author = SqlCode.encode(song.getAuthor());
		String lyric = SqlCode.encode(song.getLyric());
		String quicksearch = SqlCode.encode(song.getQuickSearch());
		String tableName = "ZFAVORITE(ZROWID,ZSNAME,ZSLYRIC,ZSMETA,ZSABBR)";
//		String values = "" + id + "," + name + "," + lyric 
//				+ "," + author + "," + quicksearch;
		SqliteExecutor.insertStatement(DATABASE, tableName, String.valueOf(id), name, lyric, author, quicksearch);

	}

	public static void delFavourite(int songId ) {
		String tableName = "ZFAVORITE";
		String criterial = "ZROWID = " + songId;
		SqliteExecutor.deleteStatement(DATABASE, tableName, criterial);

	}
	public static void saveMedia(){
		String tableName = " Z_CONFIG ";
		String a = SettingActivity.itemProductSelecTed;
		String setting = " Z_MEDIACHOICE = '" + a + "'";
		SqliteExecutor.updateStatement(DATABASE, tableName, setting);
	}
	public static SaveEntity showMedia(){
		String tableName = "Z_CONFIG";
		String select = " Z_MEDIACHOICE,Z_LANGUAGE ";
		Cursor CURSORSHOWMEDIA = SqliteExecutor.queryStatement(DATABASE, tableName, select);
		SaveEntity SAVE = null;
		if (CURSORSHOWMEDIA.moveToFirst()) {
			SAVE = new SaveEntity(CURSORSHOWMEDIA.getString(CURSORSHOWMEDIA
					.getColumnIndex("Z_MEDIACHOICE")), CURSORSHOWMEDIA.getString(CURSORSHOWMEDIA
					.getColumnIndex("Z_LANGUAGE")));
		}
		return SAVE;
		
	}
}
