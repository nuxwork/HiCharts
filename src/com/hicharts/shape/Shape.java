package com.hicharts.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

//TODO Cloneable
public abstract class Shape implements Cloneable {

	private RectF mArea;

	public Shape() {
		mArea = new RectF();
	}

	public float getWidth() {

		return mArea == null ? 0 : mArea.width();
	}

	public float getHeight() {
		return mArea == null ? 0 : mArea.height();
	}

	public RectF getArea() {
		return mArea;
	}

	public void setArea(float left, float top, float right, float bottom) {
		if (mArea == null) {
			mArea = new RectF(left, top, right, bottom);
			onAreaChanged(left, top, right, bottom);
			return;
		}

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
	
	protected void onAreaChanged(float left, float top, float right, float bottom){
		
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
	 * @return
	 */
	public PointF getCenter() {
		// TODO 优化
		if (mArea == null)
			return new PointF(0, 0);
		else
			return new PointF(mArea.centerX(), mArea.centerY());
	}

	public abstract void draw(Canvas canvas, Paint paint);
}
