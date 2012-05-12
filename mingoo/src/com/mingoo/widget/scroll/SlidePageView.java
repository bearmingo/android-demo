package com.mingoo.widget.scroll;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import com.mingoo.widget.LinearLayoutWithRect;
import com.mingoo.widget.common.WidgetUtil;

public class SlidePageView extends LinearLayoutWithRect {
	//-------------------------------------------------------------------------
	// Contents
	//-------------------------------------------------------------------------
	//private static final int SNAP_VELOCITY = 600;
	
	//-------------------------------------------------------------------------
	// Fields
	//-------------------------------------------------------------------------
	private int mCurrentPagePosition;
	private Scroller mScroller;
	private int mScrollToPositionX = 0;
	private int mScrollToPositionY = 0;
	private boolean mAllowMoveTouchScroll = true;
	private boolean mAddowThrowTouchScroll = true;
	private OnPageViewChangeListener mPageViewChangeListener;
	private float mLastMotionX;
	private VelocityTracker mVelocityTracker;
	
	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------
	public SlidePageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init(context);
	}
	
	//-------------------------------------------------------------------------
	//Getter & Setter
	//-------------------------------------------------------------------------
	
	
	public void setScrollToPositionX(int pScrollToPositionX) {
		this.mScrollToPositionX = pScrollToPositionX;
	}

	public int getScrollToPositionX() {
		return mScrollToPositionX;
	}

	public void setScrollToPositionY(int pScrollToPositionY) {
		this.mScrollToPositionY = pScrollToPositionY;
	}

	public int getScrollToPositionY() {
		return mScrollToPositionY;
	}

	public void setAllowMoveTouchScroll(boolean pAllowMoveTouchScroll) {
		this.mAllowMoveTouchScroll = pAllowMoveTouchScroll;
	}

	public boolean isAllowMoveTouchScroll() {
		return mAllowMoveTouchScroll;
	}

	public void setCurrentPagePosition(int pCurrentPagePosition) {
		this.mCurrentPagePosition = pCurrentPagePosition;
	}

	public int getCurrentPagePosition() {
		return mCurrentPagePosition;
	}

	public void setAddowThrowTouchScroll(boolean pAddowThrowTouchScroll) {
		this.mAddowThrowTouchScroll = pAddowThrowTouchScroll;
	}

	public boolean isAddowThrowTouchScroll() {
		return mAddowThrowTouchScroll;
	}
	
	public void setOnPageViewChangeListener(OnPageViewChangeListener pPageViewChangeListener) {
		this.mPageViewChangeListener = pPageViewChangeListener;
	}

	//-------------------------------------------------------------------------
	// Method
	//-------------------------------------------------------------------------
	private void init(Context context) {
		this.mScroller = new Scroller(context);
	}
	
	protected void JumpToAppointCurrentPage() {
		setScrollToPosition();
		scrollTo(mScrollToPositionX, mScrollToPositionY);
	}
	
	private void AccordingToTouchPositionToComputerCurrPagePosition() {
		Rect pScreenRect = WidgetUtil.getScreenRect((Activity)getContext());
		int screenCenterX = pScreenRect.width() / 2;
		
		int childViewNum = getChildCount();
		for (int i = 0; i < childViewNum; i++) {
			View view = getChildAt(i);
			Rect pViewRect = WidgetUtil.getViewRect(view);
			
			if ((pViewRect.left - getScrollX() > screenCenterX) ||
					(pViewRect.right - getScrollX() <= screenCenterX))
				continue;
			
			mCurrentPagePosition = i;
			return;
		}
	}
	
	public void CurrentPageScrollToScreenCenter() {
		final View pCurrentChildView = getChildAt(mCurrentPagePosition);
		final Rect pScreenRect = WidgetUtil.getScreenRect((Activity)getContext());
		int screenCenterX = pScreenRect.width() / 2;
		Point pCurChildViewCenterPoint = WidgetUtil.getViewCenterPoint(pCurrentChildView);
		int deltaX = -(getScrollX() - pCurChildViewCenterPoint.x + screenCenterX);
		int deltaY = 0;
		mScroller.startScroll(getScrollX(), mScrollToPositionY, deltaX, deltaY, Math.abs(2 * deltaX));
		
		invalidate();
	}
	
	private void setScrollToPosition() {
		final View pCurView = getChildAt(getCurrentPagePosition());
		Rect pCurViewRect = WidgetUtil.getViewRect(pCurView);
		Rect pScreenRect = WidgetUtil.getScreenRect((Activity)getContext());
		
		int gap = (pScreenRect.width() - pCurViewRect.width()) / 2;
		mScrollToPositionX = (pCurViewRect.left - gap);
	}
	
	private void CurrPageScrollToScreenCenter() {
		final View pCurChildView = getChildAt(mCurrentPagePosition);
		Rect pScreenRect = WidgetUtil.getScreenRect((Activity)getContext());
		int screenCenterX = pScreenRect.width() / 2;
		Point pCurChildViewPoint = WidgetUtil.getViewCenterPoint(pCurChildView);
		
		int deltaX = -(getScrollX() - pCurChildViewPoint.x + screenCenterX);
		int deltaY = 0;
		
		mScroller.startScroll(getScrollX(), mScrollToPositionY, deltaX, deltaY, Math.abs(2 * deltaX));
		invalidate();
	}
	
	//-------------------------------------------------------------------------
	// Interface/ Override
	//-------------------------------------------------------------------------
	@Override
	protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		for (int i = 0; i <getChildCount(); i++) {
			View view = getChildAt(i);
			if (view.getMeasuredWidth() <= 0) {
				view.measure(widthMeasureSpec, heightMeasureSpec);
			}
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		
		JumpToAppointCurrentPage();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final float x = event.getX();
		//final float y = event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mVelocityTracker == null) {
				this.mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(event);
			}
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mVelocityTracker != null) {
				mVelocityTracker.addMovement(event);
			}
			int deltaX = (int)(mLastMotionX - x);
			int deltaY = 0;
			if (isAllowMoveTouchScroll()) {
				scrollBy(deltaX, deltaY);
			}
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_UP:
			int velocityX = 0;
			
			if (isAddowThrowTouchScroll() && mVelocityTracker != null) {
				mVelocityTracker.addMovement(event);
				mVelocityTracker.computeCurrentVelocity(1000);
				
				velocityX = (int)this.mVelocityTracker.getXVelocity();
			}
			
			if ((velocityX > 600) && (getCurrentPagePosition() > 0)) {
				setCurrentPagePosition(getCurrentPagePosition() - 1);

			} else if (velocityX < -600 && (getCurrentPagePosition() < getChildCount() - 1)) {
				setCurrentPagePosition(getCurrentPagePosition() + 1);
			} else {
				AccordingToTouchPositionToComputerCurrPagePosition();
			}
			
			CurrPageScrollToScreenCenter();
			
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			
			if (mPageViewChangeListener == null) 
				break;
			
			mPageViewChangeListener.OnPageViewChanged(mCurrentPagePosition, getChildAt(mCurrentPagePosition));
		}
		
		return true;
	}
	
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			mScrollToPositionX = mScroller.getCurrX();
			
			scrollTo(mScrollToPositionX, mScrollToPositionY);
			postInvalidate();
		}
	}
	
	//-------------------------------------------------------------------------
	// Classes
	//-------------------------------------------------------------------------
	public static abstract interface OnPageViewChangeListener {
		public abstract void OnPageViewChanged(int i, View pView);
	}
}
