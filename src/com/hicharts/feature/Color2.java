package com.hicharts.feature;

import android.graphics.Color;

public class Color2 extends Color{
	public static int setAlpha(int color, int alpha) {
		int a = alpha;
		int r = Color.red(color);
		int g = Color.green(color);
		int b = Color.blue(color);
		return Color.argb(a, r, g, b);
	}
}
