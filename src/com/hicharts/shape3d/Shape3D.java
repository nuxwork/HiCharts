package com.hicharts.shape3d;

import android.graphics.drawable.shapes.Shape;

public abstract class Shape3D extends Shape{
	private float mDepth;

	public float getDepth() {
		return mDepth;
	}

	public void setDepth(float depth) {
		mDepth = depth;
	}
	
	
}
