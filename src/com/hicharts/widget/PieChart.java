package com.hicharts.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;

import com.hicharts.R;
import com.hicharts.view.Pie;

/**
 * TODO 待优化
 * 
 * @author swordy
 * 
 */
public class PieChart extends AdapterView<PieChartAdapter> {

	private PieChartAdapter		mAdapter;
	private SparseArray<Pie>	mPies;
	private int					mItemCount	= 0;

	public PieChart(Context context) {
		super(context);
		onCreate(context, null, 0);
	}

	public PieChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		onCreate(context, attrs, 0);
	}

	public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		onCreate(context, attrs, defStyleAttr);
	}

	protected void onCreate(Context context, AttributeSet attrs, int defStyleAttr) {

	}

	@Override
	public PieChartAdapter getAdapter() {
		return mAdapter;
	}

	@Override
	public void setAdapter(PieChartAdapter adapter) {
		if (mAdapter != null) {
			if (mPies != null)
				mPies.clear();
		}

		mAdapter = adapter;
		if (adapter != null) {
			mItemCount = adapter.getCount();
			mPies = new SparseArray<Pie>(mItemCount);
		}
	}

	@Override
	public View getSelectedView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelection(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		if (mAdapter != null) {
			for (int i = 0; i != mItemCount; i++) {
				Pie pie = mPies.get(i);
				if (pie == null) {
					View item = mAdapter.getView(i, null, null);
					pie = (Pie) item.findViewById(R.id.pie);
					mPies.put(i, pie);
				}
				detachViewFromParent(pie);
			}
		}

		super.onDetachedFromWindow();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (mAdapter != null) {
			for (int i = 0; i != mItemCount; i++) {
				Pie pie = mPies.get(i);
				if (pie == null) {
					View item = mAdapter.getView(i, null, null);
					pie = (Pie) item.findViewById(R.id.pie);
					mPies.put(i, pie);
				}
				pie.measure(widthMeasureSpec, heightMeasureSpec);
				attachViewToParent(pie, i, getLayoutParams());
			}
		}
	}

	private static final String	TAG	= "HiCharts.PieChart";

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		Log.i(TAG, "lay:   " + new Rect(left, top, right, bottom).toString());
		int l = 0;
		int t = 0;
		int r = right - left;
		int b = bottom - top;

		if (mAdapter != null) {
			for (int i = 0; i != mItemCount; i++) {
				Pie pie = mPies.get(i);
				if (pie == null) {
					View item = mAdapter.getView(i, null, null);
					pie = (Pie) item.findViewById(R.id.pie);
					mPies.put(i, pie);
				}

				pie.layout(l + getPaddingLeft(), t + getPaddingTop(), r - getPaddingRight(), b
						- getPaddingBottom());
			}
		}
	}
}
