package com.techstorm.karaokehug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.techstorm.karaoke_hug.R;
import com.techstorm.karaokehug.activities.FavouriteActivity;
import com.techstorm.karaokehug.entities.SongEntity;

@SuppressLint("InflateParams") public class DisplayFavourite extends BaseAdapter{
	private final Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> name;
	private ArrayList<String> lyric;
	private ArrayList<String> author;
	private SongEntity song;

	@Override
	public int getCount() {
		return id.size();
	}

	@Override
	public Object getItem(int arg0) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name.get(arg0));
		map.put("id", id.get(arg0));
		map.put("lyric", lyric.get(arg0));
		map.put("author", author.get(arg0));
		return map;
		
		
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (arg1 == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg1 = layoutInflater.inflate(R.layout.layout_favourite_row, null);
			mHolder = new Holder();
			mHolder.txt_id = (TextView) arg1.findViewById(R.id.Txt_id);
			mHolder.txt_name = (TextView) arg1.findViewById(R.id.Txt_name);
			mHolder.btndel = (Button) arg1.findViewById(R.id.btndel);
			arg1.setTag(mHolder);
		} else {
			mHolder = (Holder) arg1.getTag();
		}
		mHolder.txt_name.setText(name.get(arg0));
		mHolder.txt_id.setText(id.get(arg0));
		mHolder.btndel.setTag(id.get(arg0));
		mHolder.btndel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = (String)v.getTag();
				int value = Integer.parseInt(id);
				DatabaseCreator.delFavourite(value);
				FavouriteActivity act = ((FavouriteActivity)mContext);
				act.displayDataALL();
			}
		});
		return arg1;
	}
	
	public DisplayFavourite(Context c,ArrayList<String> id,ArrayList<String> name,ArrayList<String> lyric,ArrayList<String> author) {
		this.mContext = c;
		this.id = id;
		this.name = name;
		this.lyric = lyric;
		this.author = author;
	}
	

	public class Holder {
		TextView txt_id;
		TextView txt_name;
		Button btndel;
		TextView txt_lyric;
		TextView txt_author;
	}
}
