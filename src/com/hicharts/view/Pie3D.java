package com.hicharts.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.hicharts.R;
import com.hicharts.shape3d.IShape3D;
import com.hicharts.shape3d.Sector3D;

public class Pie3D extends Pie implements IShape3D {

	private Sector3D	mSector;

	public Pie3D(Context context) {
		super(context);
	}

	public Pie3D(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Pie3D(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onCreate(Context context, AttributeSet attrs, int defStyleAttr) {
		super.onCreate(context, attrs, defStyleAttr);

		mSector = getShape();

		float depth = DEFAULT_DEPTH;
		if (attrs == null) {

		} else {
			TypedArray a = context
					.obtainStyledAttributes(attrs, R.styleable.Pie3D, defStyleAttr, 0);
			depth = a.getDimensionPixelSize(R.styleable.Pie3D_depth, DEFAULT_DEPTH);

			a.recycle();
		}

		mSector.setDepth(depth);
	}

	@Override
	public Sector3D getShape() {
		if (mSector == null) {
			mSector = new Sector3D();
		}

		return mSector;
	}

	@Override
	public void setDepth(float depth) {
		if (mSector != null && depth != mSector.getDepth()) {
			mSector.setDepth(depth);
			invalidate();
		}
	}

	@Override
	public float getDepth() {
		return mSector.getDepth();
	}
}
