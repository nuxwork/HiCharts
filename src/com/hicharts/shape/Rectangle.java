package com.hicharts.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Rectangle extends Shape {
	
	@Override
	public void setArea(float left, float top, float right, float bottom) {
		if (isRegular()) {
			float width = right - left;
			float height = bottom - top;

			if (width > height) {
				left = left + (width - height) / 2f;
				right = left + height;
			} else if (width < height) {
				top = top + (height - width) / 2f;
				bottom = top + width;
			}
		}
		super.setArea(left, top, right, bottom);
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawRect(getArea(), paint);
	}

}
