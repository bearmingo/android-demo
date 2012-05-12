package com.mingoo.demo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mingoo.R;
import com.mingoo.widget.navigation.RollNavigationBarAdapter;
import com.mingoo.widget.navigation.RollNavigationBar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RollNavigationBarDemoActivity extends Activity{
	//-------------------------------------------------------------------------
	// Fields
	//-------------------------------------------------------------------------
	private String[] mTitles = {"关注", "热门", "分类", "喜欢", "我"};
	private int[] images = {
		R.drawable.nav_menu_home,
		R.drawable.nav_menu_hot,
		R.drawable.nav_menu_category,
		R.drawable.nav_menu_like,
		R.drawable.nav_menu_me
	};
	private int[] imageSelected = {
			R.drawable.nav_menu_home_selected,
			R.drawable.nav_menu_hot_selected,
			R.drawable.nav_menu_category_active,
			R.drawable.nav_menu_like_active,
			R.drawable.nav_menu_me_selected
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.rollnavigation_demo);
		
		final RollNavigationBar pRollNavigationBar = (RollNavigationBar)findViewById(R.id.navigationbartest_ui_RollNavigationBar);
		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		for (int i = 0; i < mTitles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", mTitles[i]);
			map.put("photo", images[i]);
			map.put("photoSelected", imageSelected[i]);
			list.add(map);
		}
		
		pRollNavigationBar.setSelecterMoveContinueTime(0.1f);
		pRollNavigationBar.setSelecterDrawableSource(R.drawable.nav_menu_bg);
		pRollNavigationBar.setSelectedChildPosition(0);
		
		final MyNavigationBarAdapter adapter = new MyNavigationBarAdapter(this, list);
		pRollNavigationBar.setAdapter(adapter);
		pRollNavigationBar.setNavigationBarListener(new RollNavigationBar.NavigationBarClickListener() {
			
			@Override
			public void onNavigationBarClick(int pParamInt, View pView,
					MotionEvent pEvent) {
				Log.d("test", "click " + pParamInt);
			}
		});
		
	}
	
	//--------------------------------------------------------------------------
	// Classes
	//--------------------------------------------------------------------------
	class MyNavigationBarAdapter extends RollNavigationBarAdapter {
		private List<Map<String, Object>> mList;
		private LayoutInflater mInflater;
		
		public MyNavigationBarAdapter(Activity activity, List<Map<String, Object>> list) {
			mList = list;
			mInflater = LayoutInflater.from(activity);
		}
		
		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public View getView(int i, View pView, ViewGroup pViewGroup) {
			mInflater.inflate(R.layout.item, (ViewGroup)pView);
			RollNavigationBar pBar = (RollNavigationBar)pViewGroup;
			
			ImageView pImageView = (ImageView)pView.findViewById(R.id.image_view);
			TextView pTextView = (TextView)pView.findViewById(R.id.title_view);
			
			Map<String, Object> map = mList.get(i);
			String title = (String)(map.get("title"));
			int photo = (Integer)(map.get("photo"));
			int photoSelected = (Integer)map.get("photoSelected");
			
			if (i== pBar.getSelectedChildPosition()) {
				pImageView.setBackgroundResource(photoSelected);
			} else {
				pImageView.setBackgroundResource(photo);
			}
			pTextView.setText(title);
			return pView;
		}
		
	}
}
