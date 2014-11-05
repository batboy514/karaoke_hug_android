package com.techstorm.karaokehug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DisplaySearch extends BaseAdapter{
	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> name;
	private ArrayList<String> lyric;
	private ArrayList<String> author;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return id.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name.get(arg0));
		map.put("id", id.get(arg0));
		map.put("lyric", lyric.get(arg0));
		map.put("author", author.get(arg0));
		return map;
		
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (arg1 == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg1 = layoutInflater.inflate(R.layout.listsearch, null);
			mHolder = new Holder();
			mHolder.txt_id = (TextView) arg1.findViewById(R.id.Txt_id);
			mHolder.txt_name = (TextView) arg1.findViewById(R.id.Txt_name);
		mHolder.txt_lyric = (TextView) arg1.findViewById(R.id.TXT_LYRIC);
	mHolder.txt_author = (TextView) arg1.findViewById(R.id.Txt_author);
			arg1.setTag(mHolder);
		} else {
			mHolder = (Holder) arg1.getTag();
		}
		mHolder.txt_name.setText(name.get(arg0));
		mHolder.txt_id.setText(id.get(arg0));
	mHolder.txt_lyric.setText(lyric.get(arg0));
	mHolder.txt_author.setText(author.get(arg0));
		return arg1;
	}
	public DisplaySearch(Context c,ArrayList<String> id,ArrayList<String> name,ArrayList<String> lyric,ArrayList<String> author) {
		this.mContext = c;
		this.id = id;
		this.name = name;
	this.lyric = lyric;
	this.author = author;
	}
	

	public class Holder {
		TextView txt_id;
		TextView txt_name;
		TextView txt_lyric;
	TextView txt_author;
	}
}
