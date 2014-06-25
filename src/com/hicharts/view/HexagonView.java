package com.hicharts.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.hicharts.R;
import com.hicharts.shape.Hexagon;

public class HexagonView extends ShapeView<Hexagon> {
	private static final String	TAG	= "AndroidLibrary.HexagonView";

	private Hexagon				mHexagon;

	public HexagonView(Context context) {
		super(context);
	}

	public HexagonView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HexagonView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onCreate(Context context, AttributeSet attrs, int defStyleAttr) {
		super.onCreate(context, attrs, defStyleAttr);
		
		Hexagon hexagon = getShape();
		int orientation = hexagon.getOrientation();
		
		if(attrs ==null){
			
		}else{
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HexagonView,
					defStyleAttr, 0);
			orientation = a.getInteger(R.styleable.HexagonView_orientation, orientation);
			a.recycle();
		}
		
		hexagon.setOrientation(orientation);
	}

	@Override
	public float getRatioSize() {
		return 0.866f;
	}

	@Override
	public Hexagon getShape() {
		if (mHexagon == null)
			mHexagon = new Hexagon();
		return mHexagon;
	}
}
