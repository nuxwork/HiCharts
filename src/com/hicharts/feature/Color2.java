package com.hicharts.feature;

import android.graphics.Color;

public class Color2 extends Color {
	public static final int DARK_LEVEL_5 = -50;
	public static final int DARK_LEVEL_7 = -70;
	public static final int DARK_LEVEL_9 = -90;
	
	public static int setAlpha(int color, int alpha) {
		int a = alpha;
		int r = Color.red(color);
		int g = Color.green(color);
		int b = Color.blue(color);
		return Color.argb(a, r, g, b);
	}

	private static int regularColor(int value) {
		value = value < 0 ? 0 : value;
		value = value > 255 ? 255 : value;
		return value;
	}

	public static int toLight(int color, int light) {
		return toLight(color, light, light, light);
	}

	public static int toLight(int color, int lightR, int lightG, int lightB) {
		int a = Color.alpha(color);
		int r = regularColor(Color.red(color) + lightR);
		int g = regularColor(Color.green(color) + lightG);
		int b = regularColor(Color.blue(color) + lightB);
		return Color.argb(a, r, g, b);
	}
}
