package com.hicharts.shape3d;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import com.hicharts.feature.Color2;
import com.hicharts.shape.Sector;

public class Sector3D extends Sector implements IShape3D {
	private float	mDepth;
	private RectF	mAbove;
	private RectF	mBelow;

	public Sector3D() {
		mAbove = new RectF();
		mBelow = new RectF();
	}

	@Override
	public void setDepth(float depth) {
		mDepth = depth;
	}

	@Override
	public float getDepth() {
		return mDepth;
	}

	@Override
	public void setArea(float left, float top, float right, float bottom) {
		super.setArea(left, top, right, bottom);
		mAbove.set(left, top, right, bottom - mDepth);
		mBelow.set(left, top + mDepth, right, bottom);
	}

	private static final String	TAG	= "HiCharts.Sector3D";

	@Override
	public void draw(Canvas canvas, Paint paint) {
		float startAngle = getStartAngle();
		float sweepAngle = getSweepAngle();

		Log.i(TAG, "start: " + startAngle + ",  sweep:  " + sweepAngle);

		int color = paint.getColor();
		int colorBelow = Color2.toLight(color, Color2.DARK_LEVEL_9);
		int colorStart = Color2.toLight(color, Color2.DARK_LEVEL_7);
		int colorEnd = Color2.toLight(color, Color2.DARK_LEVEL_5);

		paint.setColor(colorBelow);
		canvas.drawArc(mBelow, startAngle, sweepAngle, true, paint);

		drawSide(canvas, paint, colorBelow, colorStart, colorEnd);

		paint.setColor(color);
		canvas.drawArc(mAbove, startAngle, sweepAngle, true, paint);
	}

	private Path	mSidePath	= new Path();

	private void drawSide(Canvas canvas, Paint paint, int colorBelow, int colorStart, int colorEnd) {
		float halfDepth = getDepth() / 2f;
		float startAngle = getStartAngle();
		float endAngle = startAngle + getSweepAngle();
		RectF rect = new RectF();
		rect.set(getArea());

		// 设置图形大小，方便计算
		rect.top += halfDepth;
		rect.bottom -= halfDepth;

		float cx = rect.centerX();
		float cy = rect.centerY();
		float rx = rect.width() / 2f;
		float ry = rect.height() / 2f;
		double radian1 = Math.toRadians(startAngle);
		double radian2 = Math.toRadians(endAngle);
		float x1 = (float) (Math.cos(radian1) * rx) + cx;
		float y1 = (float) (Math.sin(radian1) * ry) + cy;
		float x2 = (float) (Math.cos(radian2) * rx) + cx;
		float y2 = (float) (Math.sin(radian2) * ry) + cy;

		rect.set(getArea());

		// start angle side
		if (startAngle > 90f && startAngle < 270f) {
			paint.setColor(colorStart);
			mSidePath.reset();
			mSidePath.moveTo(cx, cy - halfDepth);
			mSidePath.lineTo(cx, cy + halfDepth);
			mSidePath.lineTo(x1, y1 + halfDepth);
			mSidePath.lineTo(x1, y1 - halfDepth);
			mSidePath.close();
			canvas.drawPath(mSidePath, paint);
		}

		// end angle side
		if ((endAngle >= 0f && endAngle < 90f) || (endAngle > 270f && endAngle < 450f)) {
			paint.setColor(colorEnd);
			mSidePath.reset();
			mSidePath.moveTo(cx, cy - halfDepth);
			mSidePath.lineTo(cx, cy + halfDepth);
			mSidePath.lineTo(x2, y2 + halfDepth);
			mSidePath.lineTo(x2, y2 - halfDepth);
			mSidePath.close();
			canvas.drawPath(mSidePath, paint);
		}

		// sector side
		if (startAngle < 180f && endAngle >= 180f) {
			x2 = cx - rx;
			y2 = cy;
		} else if (startAngle >= 180f) {
			if (endAngle > 360f) {
				x1 = cx + rx;
				y1 = cy;
			} else {
				return;
			}
		}
		paint.setColor(colorBelow);
		mSidePath.reset();
		mSidePath.moveTo(x1, y1 - halfDepth);
		mSidePath.lineTo(x1, y1 + halfDepth);
		mSidePath.lineTo(x2, y2 + halfDepth);
		mSidePath.lineTo(x2, y2 - halfDepth);
		mSidePath.close();

		canvas.drawPath(mSidePath, paint);
	}
}
