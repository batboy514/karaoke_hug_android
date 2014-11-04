package com.techstorm.karaokehug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * adapter to populate listview with data
 * @author ketan(Visit my <a
 *         href="http://androidsolution4u.blogspot.in/">blog</a>)
 */
public class DisplayAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> name;
	private ArrayList<String> lyric;
	private ArrayList<String> author;

	public DisplayAdapter(Context c,ArrayList<String> id,ArrayList<String> name,ArrayList<String> lyric,ArrayList<String> author) {
		this.mContext = c;
	
		this.id = id;
		this.name = name;
		this.lyric = lyric;
		this.author = author;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return id.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name.get(position));
		map.put("id", id.get(position));
		map.put("lyric", lyric.get(position));
		map.put("author", author.get(position));
		return map;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View child, ViewGroup arg2) {
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.listcell, null);
			mHolder = new Holder();
			mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
			mHolder.txt_name = (TextView) child.findViewById(R.id.txt_name);
			mHolder.txt_lyric = (TextView) child.findViewById(R.id.txt_lyric);
			mHolder.txt_author = (TextView) child.findViewById(R.id.txt_author);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.txt_name.setText(name.get(pos));
		mHolder.txt_id.setText(id.get(pos));
		mHolder.txt_lyric.setText(lyric.get(pos));
		mHolder.txt_author.setText(author.get(pos));
		return child;
	}

	public class Holder {
		TextView txt_id;
		TextView txt_name;
		TextView txt_lyric;
		TextView txt_author;
	}
	
}
