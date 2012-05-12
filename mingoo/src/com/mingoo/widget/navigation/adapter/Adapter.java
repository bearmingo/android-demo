package com.mingoo.widget.navigation.adapter;

import android.view.View;
import android.view.ViewGroup;

public interface Adapter {
	public abstract int getCount();
	public abstract View getView(int i, View pView, ViewGroup pViewGroup);
}
