package com.hicharts.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

public class TextUtil {
	private static final String TAG = "HiCharts.TextUtil";
	
	public static void getBounds(String text, Rect bounds, Paint paint) {
		if (text == null) {
			bounds.set(0, 0, 0, 0);
		} else {
			paint.getTextBounds(text, 0, text.length(), bounds);
			Log.i(TAG, bounds.toString());
			bounds.set(0, 0, bounds.width(), bounds.height());
		}
	}

	public static void draw(Canvas canvas, Paint paint, Align align, float x, float y,
			String... texts) {
		paint.setTextAlign(align);

		// Rect bounds = new Rect();

		int count = texts.length;
		float size = paint.getTextSize();
		float ox = x;
		float oy = y;
		oy = y - size * (count - 1) / 2f + size / 3f;
		for (int i = 0; i != count; i++) {
			String text = texts[i];
			// getBounds(text, bounds, paint);
			canvas.drawText(text, ox, oy, paint);
			oy += paint.getTextSize();
		}
	}

	public static void draw(Canvas canvas, Paint paint, float x, float y, String... texts) {
		draw(canvas, paint, Align.CENTER, x, y, texts);
	}

	public static void draw(Canvas canvas, Paint paint, PointF center, String... texts) {
		draw(canvas, paint, center.x, center.y, texts);
	}
}
