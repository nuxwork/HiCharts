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
import com.hicharts.view.Pie3D;

public class PieChartAdapter extends BaseAdapter {
	private Context			mContext;
	private LayoutInflater	mInflater;

	private float[]			mValues;
	private String[]		mLabels;
	private int				mLayoutId;

	private double			mTotal;
	private float[]			mStartAngles;
	private float[]			mSweepAngles;

	private boolean			mIs3D;

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

			float startAngle = -90f;
			float sweepAngle = 0f;
			double ratio = 0f;

			int index = 0;
			for (; index != values.length; index++) {

				ratio = values[index] / mTotal;
				sweepAngle = (float) (ratio * Math2.PERIGON);
				if (startAngle + sweepAngle > 90f)
					break;
				mSweepAngles[index] = sweepAngle;
				mStartAngles[index] = startAngle;
				if (b) {
					mLabels[index] = (int) mValues[index] + " , "
							+ String.format(Locale.US, "%1$.0f%%", ratio * 100);
				}
				startAngle += sweepAngle;
			}

			startAngle = Math2.PERIGON - 90f;
			for (; index != values.length; index++) {
				ratio = values[index] / mTotal;
				sweepAngle = (float) (ratio * Math2.PERIGON);
				mSweepAngles[index] = sweepAngle;
				startAngle -= sweepAngle;
				mStartAngles[index] = startAngle;
				if (b) {
					mLabels[index] = (int) mValues[index] + " , "
							+ String.format(Locale.US, "%1$.0f%%", ratio * 100);
				}
			}
		}
	}

	public boolean is3D() {
		return mIs3D;
	}

	public void set3D(boolean is3d) {
		mIs3D = is3d;
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
				Pie pie = null;
				if (mIs3D) {
					pie = new Pie3D(mContext);
				} else {
					pie = new Pie(mContext);
				}
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
