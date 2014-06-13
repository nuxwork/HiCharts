package com.hicharts.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hicharts.R;
import com.hicharts.feature.ColorSet;
import com.hicharts.view.LegendItem;

public class LegendAdapter extends BaseAdapter {
	private String[]		mLabels;
	private Context			mContext;
	private int				mLayoutId;
	private LayoutInflater	mInflater;

	public LegendAdapter(Context context, String[] labels) {
		this(context, labels, View.NO_ID);
	}

	public LegendAdapter(Context context, String[] labels, int layoutId) {
		mLabels = labels;
		mContext = context;
		mLayoutId = layoutId;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (mLabels == null)
			return 0;
		return mLabels.length;
	}

	@Override
	public String getItem(int position) {
		if (mLabels == null)
			return null;
		return mLabels[position];
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
				v = new LegendItem(mContext);
				v.setId(R.id.legendItem);
			}
		} else {}

		bunView(v, position);
		return v;
	}

	private void bunView(View v, int position) {
		LegendItem l = (LegendItem) v.findViewById(R.id.legendItem);
		l.setLabel(mLabels[position]);
		l.setLabelColor(ColorSet.getColor(position));
		l.setShapeColor(ColorSet.getColor(position));
	}
}