package com.hicharts.feature;


public class ColorSet {
	public static final int[] colors  = {
		0xFFB14946, 0xFF8FAD52, 0xFF755C95,
		0xFF459FB7, 0xFFE58A40, 0xFF3B6392,
		0xFFB1B046, 0xFFB146AD, 0xFF46B18A,
	};
	
	public static int getColor(int index) {
		return colors[index % colors.length];
	}


}
