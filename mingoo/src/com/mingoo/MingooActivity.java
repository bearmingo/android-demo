package com.mingoo;

import com.mingoo.demo.PressNavigationBarDemoActivity;
import com.mingoo.demo.RollNavigationBarDemoActivity;
import com.mingoo.demo.SlidePageViewDemoActivity;
import com.mingoo.widget.scroll.SlidePageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MingooActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button pButton = (Button)findViewById(R.id.button_PressNavigationBar);
        pButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent pIntent = new Intent(MingooActivity.this, PressNavigationBarDemoActivity.class);
				startActivity(pIntent);
				
			}
		});
        
        ((Button)findViewById(R.id.button_RollNavigationBar)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent pIntent = new Intent(MingooActivity.this, RollNavigationBarDemoActivity.class);
				startActivity(pIntent);
				
			}
		});
        
       ((Button)findViewById(R.id.button_SlidePageView)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent pIntent = new Intent(MingooActivity.this, SlidePageViewDemoActivity.class);
				startActivity(pIntent);
				
			}
		});
    }
}