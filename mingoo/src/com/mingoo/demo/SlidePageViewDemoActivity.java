package com.mingoo.demo;

import com.mingoo.R;
import com.mingoo.widget.scroll.SlidePageView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SlidePageViewDemoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidpageviewdemo_ui);
		
		SlidePageView pSlidePageView = (SlidePageView)findViewById(R.id.slidepageview_text);
		
		pSlidePageView.setOnPageViewChangeListener(new SlidePageView.OnPageViewChangeListener() {
			
			@Override
			public void OnPageViewChanged(int i, View pView) {
				Log.v("test", "current page view is " + i);
				
			}
		});
	}

}
