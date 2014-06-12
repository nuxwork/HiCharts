package com.hicharts.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListAdapter;

public class Legend extends GridView {

	public Legend(Context context) {
		super(context);
		create(context, null, 0);
	}

	public Legend(Context context, AttributeSet attrs) {
		super(context, attrs);
		create(context, attrs, 0);
	}

	public Legend(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		create(context, attrs, defStyleAttr);
	}

	private void create(Context context, AttributeSet attrs, int defStyleAttr) {

		onCreate(context, attrs, defStyleAttr);
	}

	protected void onCreate(Context context, AttributeSet attrs, int defStyleAttr) {}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
