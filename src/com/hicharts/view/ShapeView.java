package com.hicharts.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.hicharts.R;
import com.hicharts.shape.Shape;
import com.hicharts.util.TextUtil;

public abstract class ShapeView<T extends Shape> extends View implements IShapeView<T> {
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

		labelSizeChanged();

		onCreate(context, attrs, defStyleAttr);
	}

	protected void onCreate(Context context, AttributeSet attrs, int defStyleAttr) {}

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
			labelSizeChanged();
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
		setLabelSize(TypedValue.COMPLEX_UNIT_SP, labelSize);
	}

	public void setLabelSize(int unit, float labelSize) {
		Context c = getContext();
		Resources r;

		if (c == null)
			r = Resources.getSystem();
		else
			r = c.getResources();

		float size = TypedValue.applyDimension(unit, labelSize, r.getDisplayMetrics());
		if (mLabelSize != size) {
			mLabelSize = size;
			labelSizeChanged();
			invalidate();
		}
	}

	private void labelSizeChanged() {
		mPaint.setTextSize(mLabelSize);
		TextUtil.getBounds(mLabel, mLabelBounds, mPaint);
	}

	public Rect getLabelBounds() {
		return mLabelBounds;
	}

	public Paint getPaint() {
		return mPaint;
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		int[] stateSet = getDrawableState();

		if (mShapeColorList != null) {
			mShapeColor = mShapeColorList.getColorForState(stateSet, DEFAULT_SHAPE_COLOR);
		}

		if (mLabelColorList != null) {
			mLabelColor = mLabelColorList.getColorForState(stateSet, DEFAULT_LABEL_COLOR);
		}
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
