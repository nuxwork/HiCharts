package com.hicharts.widget;

import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hicharts.R;
import com.hicharts.feature.ColorSet;
import com.hicharts.util.Math2;
import com.hicharts.view.Pie;

public class PieChartAdapter extends BaseAdapter {
	private Context			mContext;
	private LayoutInflater	mInflater;

	private float[]			mValues;
	private String[]		mLabels;
	private int				mLayoutId;

	private double			mTotal;
	private float[]			mStartAngles;
	private float[]			mSweepAngles;

	private ViewBinder		mViewBinder;

	public PieChartAdapter(Context context, float[] values) {
		this(context, values, null, View.NO_ID);
	}

	public PieChartAdapter(Context context, float[] values, String[] labels, int layoutId) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mValues = values;
		mLabels = labels;
		mLayoutId = layoutId;

		if (values != null && values.length > 0) {
			mStartAngles = new float[values.length];
			mSweepAngles = new float[values.length];
			boolean b = (labels == null);
			if (b) {
				mLabels = new String[values.length];
			}

			for (int i = 0; i != values.length; i++) {
				mTotal += values[i];
			}

			float startAngle = 0f;
			float sweepAngle = 0f;
			double ratio = 0f;
			for (int i = 0; i != values.length; i++, startAngle += sweepAngle) {
				ratio = values[i] / mTotal;
				sweepAngle = (float) (ratio * Math2.PERIGON);
				mSweepAngles[i] = sweepAngle;
				mStartAngles[i] = startAngle;
				if (b) {
					mLabels[i] = (int)mValues[i] + " , "
							+ String.format(Locale.US, "%1$.0f%%", ratio * 100);
				}
			}
		}
	}

	public ViewBinder getViewBinder() {
		return mViewBinder;
	}

	public void setViewBinder(ViewBinder viewBinder) {
		mViewBinder = viewBinder;
	}

	@Override
	public int getCount() {
		if (mValues == null)
			return 0;
		return mValues.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			if (mLayoutId != View.NO_ID) {
				v = mInflater.inflate(mLayoutId, null);
			} else {
				Pie pie = new Pie(mContext);
				pie.setId(R.id.pie);
				v = pie;
			}
		} else {

		}
		bindView(v, position);
		return v;
	}

	protected void bindView(View v, int position) {
		if (mViewBinder == null) {
			Pie pie = (Pie) v.findViewById(R.id.pie);
			pie.setShapeColor(ColorSet.getColor(position));
			pie.setStartAngle(mStartAngles[position]);
			pie.setSweepAngle(mSweepAngles[position]);
			pie.setLabel(mLabels[position] /* + "\n" + mValues[position] */);
		} else {
			// mViewBinder.setViewValue(v, data, textRepresentation);
		}
	}

	public static interface ViewBinder {
		/**
		 * Binds the specified data to the specified view.
		 * 
		 * When binding is handled by this ViewBinder, this method must return
		 * true. If this method returns false, SimpleAdapter will attempts to
		 * handle the binding on its own.
		 * 
		 * @param view
		 *            the view to bind the data to
		 * @param data
		 *            the data to bind to the view
		 * @param textRepresentation
		 *            a safe String representation of the supplied data: it is
		 *            either the result of data.toString() or an empty String
		 *            but it is never null
		 * 
		 * @return true if the data was bound to the view, false otherwise
		 */
		boolean setViewValue(View view, Object data, String textRepresentation);
	}
}
