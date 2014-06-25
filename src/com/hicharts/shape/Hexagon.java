package com.hicharts.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class Hexagon extends Shape {

	public static final int		HORIZONTAL			= 0;
	public static final int		VERTICAL			= 1;
	public static final float	RATIO_VERTICAL		= 0.866f;
	public static final float	RATIO_HORIZONTAL	= 1.155f;

	private int					mOrientation		= HORIZONTAL;

	private Path				mPath;

	public int getOrientation() {
		return mOrientation;
	}

	public void setOrientation(int orientation) {
		mOrientation = orientation;
	}

	@Override
	public boolean isRegular() {
		return true;
	}

	@Override
	public void setArea(float left, float top, float right, float bottom) {
		if (!isRegular()) {
			super.setArea(left, top, right, bottom);
			return;
		}

		float width = right - left;
		float height = bottom - top;
		float newWidth = width;
		float newHeight = height;

		float ratio = width / height;
		float r = mOrientation == VERTICAL ? RATIO_VERTICAL : RATIO_HORIZONTAL;

		if (ratio < r) {
			newHeight = width / r;
			top = top + (height - newHeight) / 2f;
			bottom = top + newHeight;
		} else if (ratio > r) {
			newWidth = newHeight * r;
			left = left + (width - newWidth) / 2f;
			right = left + newWidth;
		}

		// if (mOrientation == VERTICAL) {
		// float ratio = width / height;
		// if (ratio < RATIO_VERTICAL) {
		// newHeight = width / RATIO_VERTICAL;
		// top = top + (height - newHeight) / 2f;
		// bottom = top + newHeight;
		// } else if (ratio > RATIO_VERTICAL) {
		// newWidth = newHeight * RATIO_VERTICAL;
		// left = left + (width - newWidth) / 2f;
		// right = left + newWidth;
		// }
		// } else {
		// float ratio = height / width;
		// if (ratio < RATIO_VERTICAL) {
		// newWidth = newHeight / RATIO_VERTICAL;
		// left = left + (width - newWidth) / 2f;
		// right = left + newWidth;
		// } else if (ratio > RATIO_VERTICAL) {
		// newHeight = width * RATIO_VERTICAL;
		// top = top + (height - newHeight) / 2f;
		// bottom = top + newHeight;
		// }
		// }
		super.setArea(left, top, right, bottom);
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		Path path = toPath();
		canvas.drawPath(path, paint);
	}

	@Override
	public Path toPath() {
		if (mPath == null) {
			mPath = new Path();
		}

		RectF area = getArea();

		if (mOrientation == VERTICAL) {
			float w = area.width() / 2f;
			float h = area.height() / 4f;
			mPath.reset();
			mPath.moveTo(area.left, area.top + h);
			mPath.lineTo(area.left, area.bottom - h);
			mPath.lineTo(area.left + w, area.bottom);
			mPath.lineTo(area.right, area.bottom - h);
			mPath.lineTo(area.right, area.top + h);
			mPath.lineTo(area.left + w, area.top);
			mPath.close();
		} else {
			float w = area.width() / 4f;
			float h = area.height() / 2f;
			mPath.reset();
			mPath.moveTo(area.left, area.top + h);
			mPath.lineTo(area.left + w, area.bottom);
			mPath.lineTo(area.right - w, area.bottom);
			mPath.lineTo(area.right, area.top + h);
			mPath.lineTo(area.right - w, area.top);
			mPath.lineTo(area.left + w, area.top);
			mPath.close();
		}

		return mPath;
	}
}
