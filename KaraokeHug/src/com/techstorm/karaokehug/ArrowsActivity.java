package com.techstorm.karaokehug;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**

 *
 */
public class ArrowsActivity extends Activity implements View.OnClickListener{
	
    public void onCreate(Bundle savedInstanceState) {
    	  super.onCreate(savedInstanceState);
          setContentView(R.layout.arrowspage);
          Button BackButton;
          Bundle bundle = getIntent().getExtras();
          String name = bundle.getString("name");
          String id = bundle.getString("id");
          String lyric = bundle.getString("lyric");
          String author = bundle.getString("author");

          TextView textid = (TextView) findViewById(R.id.textid);
          TextView textname = (TextView) findViewById(R.id.textname);
          TextView textlyric = (TextView) findViewById(R.id.textlyric);
          TextView textauthor = (TextView) findViewById(R.id.textauthor);
          
          textname.setText(name);
          textid.setText(id);
          textlyric.setText(lyric);
          textauthor.setText(author);
          Button  btn = (Button) findViewById(R.id.btn);  
          btn.setOnClickListener(this);
          
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent myIntent = new Intent(getBaseContext(), OptionsActivity.class);
		startActivity(myIntent);
	}
}