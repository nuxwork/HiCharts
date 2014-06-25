package com.hicharts.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Sector
 * 
 * @author swordy
 * 
 */
public class Sector extends Square {
	/** the sweep angle of sector */
	private float mSweepAngle;

	/** the start angle of sector */
	private float mStartAngel;
	
	public float getSweepAngle() {
		return mSweepAngle;
	}

	public void setSweepAngle(float sweepAngle) {
		mSweepAngle = sweepAngle;
	}

	public float getStartAngle() {
		return mStartAngel;
	}

	public void setStartAngle(float startAngle) {
		mStartAngel = startAngle;
	}

	@Override
	public boolean contains(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Shape shape) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PointF getCenter() {
		RectF area = getArea();
		if(area == null)
			return new PointF(0, 0);
		
		float angle = (float) (Math.toRadians(mStartAngel + mSweepAngle / 2));
		float x = (float) (Math.cos(angle) * area.width() / 3) + area.centerX();
		float y = (float) (Math.sin(angle) * area.height() / 3)
				+ area.centerY();
		return new PointF(x, y);
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawArc(getArea(), mStartAngel, mSweepAngle, true, paint);
	}
}
