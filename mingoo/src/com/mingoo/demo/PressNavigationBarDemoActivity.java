package com.mingoo.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mingoo.R;
import com.mingoo.widget.navigation.PressNavigationBar;
import com.mingoo.widget.navigation.PressNavigationBar.PressNavigationBarListener;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class PressNavigationBarDemoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pressnavigationbar_demo);
		
		PressNavigationBar pPressNavigationBar = (PressNavigationBar)findViewById(R.id.navigationbartest_ui_PressNavigationBar);
		
		String[] texts = {"最热", "最新", "猜你喜欢"};
		int[] textSizes = {14, 14, 14};
		int[] textColors = { Color.WHITE, Color.WHITE, Color.WHITE};
		int[] images = { R.drawable.message_left_button_normal,
				R.drawable.message_middle_button_normal,
				R.drawable.message_right_button_normal};
		int[] imageSelected = { R.drawable.message_left_button_pressed,
				R.drawable.message_middle_button_pressed,
				R.drawable.message_right_button_pressed };
		
		List<Map<String, Object>> pressBarList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < images.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("text", texts[i]);
			map.put("textSize", textSizes[i]);
			map.put("textColor", textColors[i]);
			map.put("image", images[i]);
			map.put("imageSelected", imageSelected[i]);
			pressBarList.add(map);
		}
		
		pPressNavigationBar.addChild(pressBarList);
		pPressNavigationBar.setPressNavigationBarListener(new PressNavigationBarListener() {
			
			@Override
			public void onNavigationBarClick(int arg0, View arg1, MotionEvent arg2) {
				
			}
		});
	}
	
}
