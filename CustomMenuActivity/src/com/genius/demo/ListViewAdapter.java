package com.genius.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter{

	Context mContext;
	
	String mTitles[];
	
	LayoutInflater mLayoutInflater;
	
	public ListViewAdapter(Context context, String []title)
	{
		mContext = context;
		
		mTitles = title;
		
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTitles.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null)
		{
			convertView = mLayoutInflater.inflate(R.layout.listview_item, null);
		}
		
		ImageView mImageViewLeft = (ImageView) convertView.findViewById(R.id.imageviewleft);
		
		TextView mTextView = (TextView) convertView.findViewById(R.id.textview);
		
		ImageView mImageViewRight = (ImageView) convertView.findViewById(R.id.imageviewright);
		
		mImageViewLeft.setImageResource(R.drawable.list_item_image);
		
		mTextView.setText(mTitles[position]);
		
		mImageViewRight.setImageResource(R.drawable.list_item_arrow);
		
		return convertView;
	}

}
