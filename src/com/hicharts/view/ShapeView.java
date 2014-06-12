package com.hicharts.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hicharts.R;
import com.hicharts.shape.Shape;
import com.hicharts.util.TextUtil;

public abstract class ShapeView<T extends Shape> extends View {

	// TODO 默认值使用defStyleAttr来完成
	public static final int	DEFAULT_SHAPE_COLOR	= 0xFF8d8d8d;
	public static final int	DEFAULT_LABEL_COLOR	= 0xFFeeeeee;
	public static final int	DEFAULT_LABEL_SIZE	= 20;

	private Paint			mPaint;
	private ColorStateList	mShapeColorList;
	private int				mShapeColor;

	private String			mLabel;
	private float			mLabelSize;
	private Rect			mLabelBounds;
	private ColorStateList	mLabelColorList;
	private int				mLabelColor;

	public ShapeView(Context context) {
		super(context);
		create(context, null, 0);
	}

	public ShapeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		create(context, attrs, 0);
	}

	public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		create(context, attrs, defStyleAttr);
	}

	private void create(Context context, AttributeSet attrs, int defStyleAttr) {
		mPaint = new Paint();
		mPaint.setStyle(Style.FILL);
		mPaint.setAntiAlias(true);

		mLabelBounds = new Rect();

		mLabelColor = DEFAULT_LABEL_COLOR;
		mShapeColor = DEFAULT_SHAPE_COLOR;

		if (attrs == null) {
			mLabelSize = DEFAULT_LABEL_SIZE;
		} else {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShapeView,
					defStyleAttr, 0);

			mShapeColorList = a.getColorStateList(R.styleable.ShapeView_shapeColor);
			mLabelColorList = a.getColorStateList(R.styleable.ShapeView_labelColor);
			mLabel = a.getString(R.styleable.ShapeView_label);
			mLabelSize = a.getDimensionPixelSize(R.styleable.ShapeView_labelSize,
					DEFAULT_LABEL_SIZE);
			a.recycle();

		}
		
		mPaint.setTextSize(mLabelSize);
		TextUtil.getBounds(mLabel, mLabelBounds, mPaint);

		onCreate(context, attrs, defStyleAttr);
	}

	protected void onCreate(Context context, AttributeSet attrs, int defStyleAttr) {}

	public abstract T getShape();

	public abstract void setShape(T shape);

	public ColorStateList getShapeColorList() {
		return mShapeColorList;
	}

	public int getShapeColor() {
		return mShapeColor;
	}

	public void setShapeColor(int color) {
		if (mShapeColor != color) {
			mShapeColor = color;
			invalidate();
		}
	}

	public void setShapeColor(ColorStateList color) {
		if (mShapeColorList != color) {
			mShapeColorList = color;
			invalidate();
		}
	}

	public String getLabel() {
		return mLabel;
	}

	public void setLabel(String label) {
		if (mLabel != label) {
			mLabel = label;
			TextUtil.getBounds(mLabel, mLabelBounds, mPaint);
			invalidate();
		}
	}

	public ColorStateList getLabelColorList() {
		return mLabelColorList;
	}

	public int getLabelColor() {
		return mLabelColor;
	}

	public void setLabelColor(int color) {
		if (mLabelColor != color) {
			mLabelColor = color;
			invalidate();
		}
	}

	public void setLabelColor(ColorStateList labelColor) {
		if (mLabelColorList != labelColor) {
			mLabelColorList = labelColor;
			invalidate();
		}
	}

	public float getLabelSize() {
		return mLabelSize;
	}

	public void setLabelSize(float labelSize) {
		if (mLabelSize != labelSize) {
			mLabelSize = labelSize;
			invalidate();
		}
	}

	public Rect getLabelBounds() {
		return mLabelBounds;
	}

	protected Paint getPaint() {
		return mPaint;
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		int[] stateSet = getDrawableState();

		mShapeColor = mShapeColorList == null ? DEFAULT_SHAPE_COLOR : mShapeColorList
				.getColorForState(stateSet, DEFAULT_SHAPE_COLOR);
		mLabelColor = mLabelColorList == null ? DEFAULT_LABEL_COLOR : mLabelColorList
				.getColorForState(stateSet, DEFAULT_LABEL_COLOR);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		T shape = getShape();
		if (shape != null && shape.contains(event.getX(), event.getY())) {
			return super.onTouchEvent(event);
		} else {
			// TODO 发送出去
			return super.onTouchEvent(event);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		T shape = getShape();

		if (shape != null) {
			shape.setArea(left + getPaddingLeft(), top + getPaddingTop(), right - getPaddingTop(),
					bottom - getPaddingBottom());
		}
	}
}
