package com.mingoo.widget.navigation;

import com.mingoo.widget.LinearLayoutWithRect;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class RollNavigationBar extends LinearLayoutWithRect{

	//-----------------------------------------------------------
	//Contents
	//-----------------------------------------------------------
	
	//-----------------------------------------------------------
	//Fields
	//-----------------------------------------------------------
	private NavigationBarClickListener mListener;
	private RollNavigationBarAdapter mAdapter;
	private int mSelectedChildPosition = 0;
	private float mSelecterMoveContinueTime = 10;
	private boolean mIsMoving = false;
	private Rect mRect;
	private Paint mPaint;
	private BitmapDrawable mSelecterBitmapDrawable;
	private int mSelecterDrawableSource;
	
	//-----------------------------------------------------------
	// Constructor
	//-----------------------------------------------------------
	public RollNavigationBar(Context pContext, AttributeSet pAttrSet) {
		super(pContext, pAttrSet);
		
		init();
	}
	
	//-----------------------------------------------------------
	// Getters & Setters
	//-----------------------------------------------------------
	public int getSelectedChildPosition() {
		return mSelectedChildPosition;
	}
	
	public void setSelectedChildPosition(final int pSelectedChildPosition) {
		mSelectedChildPosition = pSelectedChildPosition;
	}
	
	public float getSelecterMoveContinueTime() {
		return mSelecterMoveContinueTime;
	}
	
	public void setSelecterMoveContinueTime(final float pSelectedMoveContinueTime) {
		if (pSelectedMoveContinueTime >= 0.1D && pSelectedMoveContinueTime <= 1.0F)
			mSelecterMoveContinueTime = pSelectedMoveContinueTime;
	}
	
	public void setSelecterDrawableSource(int pDrawableSource) {
		mSelecterDrawableSource = pDrawableSource;
	}
	
	public int getSelecterDrawableSouce() {
		return mSelecterDrawableSource;
	}
	
	public void setNavigationBarListener(NavigationBarClickListener pListener) {
		mListener = pListener;
	}
	//-----------------------------------------------------------
	// Method
	//-----------------------------------------------------------
	private void init() {
		setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					RollNavigationBar.this.mSelectedChildPosition = RollNavigationBar.this.getChooseFieldPosition(v, event);
					RollNavigationBar.this.MoveSelecter();
					break;
				case MotionEvent.ACTION_MOVE:
				case MotionEvent.ACTION_UP:
					break;
				}
				
				if (RollNavigationBar.this.mListener != null) {
					RollNavigationBar.this.mListener.onNavigationBarClick(
							RollNavigationBar.this.mSelectedChildPosition, 
							RollNavigationBar.this, 
							event);
				}
				return true;
			}
		});
	}
	
	private int getChooseFieldPosition(final View v, final MotionEvent pEvent) {
		Rect pRect = getViewRect();
		float width = pRect.width() / mAdapter.getCount();
		return (int)(pEvent.getX() / width);
	}
	
	public void setAdapter(final RollNavigationBarAdapter adapter) {
		this.mAdapter = adapter;
		LinearLayout pLinearLayout = null;
		
		for (int i = 0; i < this.mAdapter.getCount(); i++) {
			pLinearLayout = new LinearLayout(getContext());
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
			params.weight = 1.0F;
			params.gravity = Gravity.CENTER;
			
			pLinearLayout.setLayoutParams(params);
			addView(pLinearLayout);
			
			mAdapter.getView(i, pLinearLayout, this);
		}
	}
	
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}
	
	protected void onAttachedToWindow(){
		super.onAttachedToWindow();
	}
	
	protected void onDraw(Canvas canvas) {
		if (!this.mIsMoving) {
			setSelecter();
		}
		
		super.onDraw(canvas);
		this.mSelecterBitmapDrawable.setBounds(mRect);
		this.mSelecterBitmapDrawable.draw(canvas);
	}
	
	private void setSelecter() {
		if ((mPaint == null) || (mRect == null) || (mSelecterBitmapDrawable == null)) {
			mPaint = new Paint();
			mRect = new Rect();
			mSelecterBitmapDrawable = new BitmapDrawable(getContext().getResources(),
					BitmapFactory.decodeResource(getContext().getResources(), getSelecterDrawableSouce()));
		}
		
		Rect pViewRect = getViewRect();
		int width = pViewRect.width() / mAdapter.getCount();
		
		mRect.left = mSelectedChildPosition * (width);
		mRect.right = mRect.left + width;
		mRect.top = 0;
		mRect.bottom = pViewRect.height();
	}
	
	private void MoveSelecter() {
		(new Thread() {
			public void run() {
				float s = 0.01F;
				RollNavigationBar.WillMoveInfo pWillMoveInfo = getWillMoveInfo();
				
				Rect pRect = new Rect(pWillMoveInfo.getStartViewRect());
				float time = 0.0F;
				
				RollNavigationBar.this.mIsMoving = true;
				
				while (RollNavigationBar.this.mIsMoving) {
					time += s;
					pRect.left = (int) (pRect.left + pWillMoveInfo.getDv() * s);
					pRect.right = (int)(pRect.right + pWillMoveInfo.getDv() * s);
					
					mRect.left = pRect.left;
					mRect.right = pRect.right;
					
					if (time >= RollNavigationBar.this.mSelecterMoveContinueTime) {
						RollNavigationBar.this.mIsMoving = false;
						RollNavigationBar.this.mRect = pWillMoveInfo.getEndViewRect();
						
						RollNavigationBar.this.mHandler.sendMessage(new Message());
					}
					
					RollNavigationBar.this.postInvalidate();
					
					try {
						Thread.sleep((int)(s * 1000.0f));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	private WillMoveInfo getWillMoveInfo() {
		WillMoveInfo pwInfo = new WillMoveInfo();
		Rect pStartRect = new Rect(mRect);
		
		pwInfo.setStartViewRect(pStartRect);
		
		int width = mRect.width();
		int endViewDistance = width * mSelectedChildPosition;
		
		Rect pEndRect = new Rect(endViewDistance, mRect.top, endViewDistance + width, mRect.bottom);
		pwInfo.setEndViewRect(pEndRect);
		
		int willMoveDistance = endViewDistance - mRect.left;
		
		int dv = (int) (willMoveDistance / getSelecterMoveContinueTime());
		
		pwInfo.setDv(dv);
		
		return pwInfo;
	}
	
	public void RefreshView(RollNavigationBarAdapter adapter) {
		removeAllViews();
		setAdapter(adapter);
	}
	//-----------------------------------------------------------
	// Classes /Interfaces
	//-----------------------------------------------------------
	public static abstract interface NavigationBarClickListener {
		public abstract void onNavigationBarClick(int pParamInt, View pView, MotionEvent pEvent);
	}
	
	private class WillMoveInfo {
	    private int dv;
	    private Rect startViewRect;
	    private Rect endViewRect;

	    WillMoveInfo()
	    {
	    }

	    public int getDv()
	    {
	      return this.dv;
	    }

	    public void setDv(int dv)
	    {
	      this.dv = dv;
	    }

	    public Rect getStartViewRect()
	    {
	      return this.startViewRect;
	    }

	    public void setStartViewRect(Rect startViewRect)
	    {
	      this.startViewRect = startViewRect;
	    }

	    public Rect getEndViewRect()
	    {
	      return this.endViewRect;
	    }

	    public void setEndViewRect(Rect endViewRect)
	    {
	      this.endViewRect = endViewRect;
	    }
	}
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			RollNavigationBar.this.RefreshView(RollNavigationBar.this.mAdapter);
		}
	};
}
