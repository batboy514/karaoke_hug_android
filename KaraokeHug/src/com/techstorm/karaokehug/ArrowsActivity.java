package com.techstorm.karaokehug;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**

 *
 */
public class ArrowsActivity extends Activity implements View.OnClickListener {
	
	public static final String KEY_ROWID = "id";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arrowspage);
		Bundle bundle = getIntent().getExtras();
		String name = bundle.getString("name");
		String id = bundle.getString("id");
		String lyric = bundle.getString("lyric");
		String author = bundle.getString("author");

		final TextView textid = (TextView) findViewById(R.id.textid);
		TextView textname = (TextView) findViewById(R.id.textname);
		TextView textlyric = (TextView) findViewById(R.id.textlyric);
		TextView textauthor = (TextView) findViewById(R.id.textauthor);

		textname.setText(name);
		textid.setText(id);
		textlyric.setText(lyric);
		textauthor.setText(author);
		Button btnfavorite = (Button) findViewById(R.id.btnfavorite);
		btnfavorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int value = Integer.parseInt(textid.getText().toString());
				DatabaseCreator.addFavourite(value);
			}
		});
		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onBackPressed();
	}

}