package com.hicharts.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

//TODO Cloneable
public abstract class Shape implements Cloneable {

	private RectF	mArea;
	private Path	mPath;
	private boolean mRegular;

	public Shape() {
		mArea = new RectF();
	}

	public void setRegular(boolean regular) {
		mRegular = regular;
	}

	public boolean isRegular() {
		return mRegular;
	}

	public float getWidth() {

		return mArea.width();
	}

	public float getHeight() {
		return mArea.height();
	}

	public RectF getArea() {
		return mArea;
	}

	public void setArea(float left, float top, float right, float bottom) {
		if (mArea.left == left && mArea.top == top && mArea.right == right
				&& mArea.bottom == bottom) {
			return;
		}

		mArea.left = left;
		mArea.top = top;
		mArea.right = right;
		mArea.bottom = bottom;
		onAreaChanged(left, top, right, bottom);
	}

	protected void onAreaChanged(float left, float top, float right, float bottom) {

	}

	public boolean contains(float x, float y) {
		return mArea == null ? false : mArea.contains(x, y);
	}

	public boolean contains(Shape shape) {
		if (shape == null || mArea == null)
			return false;

		return mArea.contains(shape.getArea());
	}

	/**
	 * content center
	 * 
	 * @return
	 */
	public PointF getCenter() {
		return new PointF(mArea.centerX(), mArea.centerY());
	}

	public abstract void draw(Canvas canvas, Paint paint);

	public Path toPath() {
		if (mPath == null) {
			mPath = new Path();
		}

		RectF area = getArea();
		mPath.reset();
		mPath.moveTo(area.left, area.top);
		mPath.lineTo(area.left, area.bottom);
		mPath.lineTo(area.right, area.bottom);
		mPath.lineTo(area.right, area.top);
		mPath.close();
		return mPath;
	}
}
