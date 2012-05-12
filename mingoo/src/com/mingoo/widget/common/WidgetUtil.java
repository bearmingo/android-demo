package com.mingoo.widget.common;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;

public class WidgetUtil {

	public static Rect getScreenRect(final Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		
		return new Rect(0, 0, display.getWidth(), display.getHeight());
	}
	
	public static Rect getViewRect(final View pView) {
		return new Rect(pView.getLeft(), pView.getTop(), pView.getRight(), pView.getBottom());
	}
	
	public static Point getViewCenterPoint(final View pView) {
		return new Point((pView.getRight() + pView.getLeft()) / 2, (pView.getBottom() + pView.getTop()) / 2);
	}
}
