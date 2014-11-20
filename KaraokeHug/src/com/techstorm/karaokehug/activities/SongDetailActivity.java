package com.techstorm.karaokehug.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.techstorm.karaokehug.DatabaseCreator;
import com.techstorm.karaokehug.R;
import com.techstorm.karaokehug.entities.SongEntity;
import com.techstorm.karaokehug.utilities.IntegerUtil;

public class SongDetailActivity extends Activity implements View.OnClickListener {
	
	public static final String KEY_ROWID = "id";
	private SongEntity song ;
	private Bitmap noLove, love;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_song_detail);
		
		noLove = BitmapFactory.decodeResource(getResources(), R.drawable.no_love_32);
		love = BitmapFactory.decodeResource(getResources(), R.drawable.love_32);
		
		Bundle bundle = getIntent().getExtras();
		
		int id = IntegerUtil.parseInt(bundle.getString("id"));
		song = DatabaseCreator.getSongBySongId(id);

		final TextView textid = (TextView) findViewById(R.id.textid);
		TextView textname = (TextView) findViewById(R.id.textname);
		TextView textlyric = (TextView) findViewById(R.id.textlyric);
		TextView textauthor = (TextView) findViewById(R.id.textauthor);
		TextView textquicksearch = (TextView) findViewById(R.id.textquicksearch);
		TextView textsource = (TextView) findViewById(R.id.source);
		textsource.setText(SettingActivity.itemproductselected);
		textname.setText(song.getName());
		textid.setText(String.valueOf(song.getSongId()));
		textlyric.setText(song.getLyric());
		textauthor.setText(song.getAuthor());
		textquicksearch.setText(song.getQuickSearch());
		final ImageButton btnfavorite = (ImageButton) findViewById(R.id.btnfavorite);
		btnfavorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (DatabaseCreator.isFavourite(song.getSongId())) {
					btnfavorite.setImageBitmap(noLove);
					DatabaseCreator.delFavourite(song.getSongId());
				} else {
					btnfavorite.setImageBitmap(love);
					DatabaseCreator.addFavourite(song);
				}
			}});
		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onBackPressed();
	}
	

}