package com.mingoo.widget.navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mingoo.widget.LinearLayoutWithRect;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PressNavigationBar extends LinearLayoutWithRect {
	//----------------------------------------------------------------------------
	// Contents
	//----------------------------------------------------------------------------
	//private final String TAGS = "PressNavigationBar";
	
	//----------------------------------------------------------------------------
	// Fields
	//----------------------------------------------------------------------------
	private List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
	private int mSelectChildViewPosition = 0;
	private PressNavigationBarListener mPressNavigationBarListener;
	
	//----------------------------------------------------------------------------
	// Constructor
	//----------------------------------------------------------------------------
	public PressNavigationBar(Context pContext, AttributeSet pAttrSet) {
		super(pContext, pAttrSet);
		
		init();
	}
	
	//---------------------------------------------------------------------------
	//Getter/Setter
	//---------------------------------------------------------------------------
	public int getSelectedChildViewPosition() {
		return mSelectChildViewPosition;
	}
	
	public  void setSelectedChildViewPosition(final int pSelectedChildViewPostion) {
		mSelectChildViewPosition = pSelectedChildViewPostion;
	}
	

	//------------------------------------------------------------------------------
	// Method
	//------------------------------------------------------------------------------
	private void init() {
		setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mSelectChildViewPosition = PressNavigationBar.this.getTouchPosition(event);
					PressNavigationBar.this.RefreshView();
					break;
				case MotionEvent.ACTION_MOVE:
				case MotionEvent.ACTION_UP:
				default:
					break;
				}
				
				if (PressNavigationBar.this.mPressNavigationBarListener != null) {
					PressNavigationBar.this.mPressNavigationBarListener.onNavigationBarClick(
							PressNavigationBar.this.mSelectChildViewPosition,
							PressNavigationBar.this,
							event);
				}
				
				return true;
			}
		});
	}
	
	/**
	 * 
	 * @param pList
	 */
	public void addChild(final List<Map<String, Object>> pList) {
		mList = pList;
		
		for (int i = 0; i <pList.size(); i++) {
			final FrameLayout pFrameLayout = new FrameLayout(getContext());
			final LinearLayout.LayoutParams pLayoutParams = new LinearLayout.LayoutParams(-1, -1, 1.0f);
	
			pFrameLayout.setLayoutParams(pLayoutParams);
			addView(pFrameLayout);
			
			final ImageView pImageView = new ImageView(getContext());
			final FrameLayout.LayoutParams pImageViewParams = new FrameLayout.LayoutParams(-1, -1);
			pImageView.setLayoutParams(pImageViewParams);
			pFrameLayout.addView(pImageView);
			
			final TextView pTextView = new TextView(getContext());
			FrameLayout.LayoutParams pTextViewParams = new FrameLayout.LayoutParams(-1, -1);
			pTextView.setGravity(Gravity.CENTER);
			pTextView.setLayoutParams(pTextViewParams);
			pFrameLayout.addView(pTextView);
			
			Map<String, Object> pMap = pList.get(i);
			
			pTextView.setText((String)pMap.get("text"));
			pTextView.setTextSize(((Integer)pMap.get("textSize")).intValue());
			pTextView.setTextColor(((Integer)pMap.get("textColor")).intValue());
			
			if (mSelectChildViewPosition != i) {
				pImageView.setBackgroundResource(((Integer)pMap.get("image")).intValue());
			} else {
				pImageView.setBackgroundResource(((Integer)pMap.get("imageSelected")).intValue());
			}
		}
	}
	
	public void RefreshView() {
		removeAllViews();
		addChild(mList);
	}
	
	private Rect getSelectedChildViewRect() {
		Rect pViewSize = getViewRect();
		
		int width = pViewSize.width() / mList.size();
		
		Rect pSelectedChildViewSize = new Rect();
		
		pSelectedChildViewSize.left = width * getSelectedChildViewPosition();
		pSelectedChildViewSize.right = pSelectedChildViewSize.left + width;
		pSelectedChildViewSize.top = 0;
		pSelectedChildViewSize.bottom = pViewSize.height();
		
		return pSelectedChildViewSize;
	}
	
	private int getTouchPosition(MotionEvent pEvent) {
		Rect pSelectedChildViewRect = getSelectedChildViewRect();
		
		return (int)pEvent.getX() / pSelectedChildViewRect.width();
	}
	
	public void setPressNavigationBarListener(final PressNavigationBarListener pListener) {
		this.mPressNavigationBarListener = pListener;
	}
	//-------------------------------------------------------------------------------
	// Interface/Class
	//------------------------------------------------------------------------------
	public static abstract interface PressNavigationBarListener {
		public abstract void onNavigationBarClick(int pParam, View pView, MotionEvent pEvent);
	}
}
