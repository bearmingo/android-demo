package com.mingoo.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LinearLayoutWithRect extends LinearLayout{
	public LinearLayoutWithRect(Context pContext, AttributeSet pAttrSet) {
		super(pContext, pAttrSet);
	}
	
	public Rect getViewRect() {
		return new Rect(getLeft(), getTop(), getRight(), getBottom());
	}
	
}
