package com.hicharts.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Rectangle extends Shape{

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawRect(getArea(), paint);
	}

}
