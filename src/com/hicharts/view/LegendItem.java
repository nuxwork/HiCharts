package com.hicharts.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hicharts.R;
import com.hicharts.shape.Rectangle;
import com.hicharts.util.TextUtil;

public class LegendItem extends LinearLayout implements IShapeView<Rectangle> {
	private Rectangle		mRectangle;
	private View			mIndicator;
	private TextView		mLabel;
	private ColorStateList	mShapeColorList;
	private int				mShapeColor;
	private int				mLabelColor;
	private int				mGap;

	public LegendItem(Context context) {
		super(context);
		onCreate(context, null, 0);
	}

	public LegendItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		onCreate(context, attrs, 0);
	}

	public LegendItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs);
		// super(context, attrs, defStyleAttr);
	}

	protected void onCreate(Context context, AttributeSet attrs, int defStyleAttr) {
		mLabel = new TextView(context);
		mIndicator = new View(context);

		String label = "";
		float labelSize = DEFAULT_LABEL_SIZE;

		if (attrs == null) {
			mShapeColor = DEFAULT_SHAPE_COLOR;
			mLabelColor = DEFAULT_LABEL_SIZE;
			mGap = (int) (labelSize / 4f);
		} else {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LegendItem,
					defStyleAttr, 0);
			mShapeColorList = a.getColorStateList(R.styleable.LegendItem_shapeColor);
			label = a.getString(R.styleable.LegendItem_label);
			labelSize = a.getDimensionPixelSize(R.styleable.LegendItem_labelSize,
					DEFAULT_LABEL_SIZE);
			mGap = a.getDimensionPixelSize(R.styleable.LegendItem_gap, (int) (labelSize / 4f));
			a.recycle();
		}

		if (mShapeColorList == null) {
			mShapeColorList = ColorStateList.valueOf(mShapeColor);
		}
		mShapeColor = mShapeColorList.getColorForState(getDrawableState(), mShapeColor);

		mLabel.setText(label);
		mLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
		mLabel.setTextColor(mLabelColor);
		mLabel.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		mIndicator.setBackgroundColor(mShapeColor);

		setOrientation(HORIZONTAL);
		setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

		LayoutParams indiP = new LayoutParams((int) labelSize - mGap * 2, (int) labelSize - mGap
				* 2);
		indiP.setMargins(mGap, 0, mGap, 0);
		addView(mIndicator, indiP);
		addView(mLabel, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void setShape(Rectangle shape) {
		if (mRectangle != shape) {
			mRectangle = shape;
			invalidate();
		}
	}

	@Override
	public Rectangle getShape() {
		if (mRectangle == null) {
			mRectangle = new Rectangle();
		}
		return mRectangle;
	}

	@Override
	public String getLabel() {
		return mLabel.getEditableText().toString();
	}

	@Override
	public void setLabel(String label) {
		mLabel.setText(label);
	}

	@Override
	public ColorStateList getShapeColorList() {
		return mShapeColorList;
	}

	@Override
	public int getShapeColor() {
		return mShapeColor;
	}

	@Override
	public void setShapeColor(int color) {
		if (mShapeColor != color) {
			mShapeColor = color;
			mIndicator.setBackgroundColor(color);
		}
	}

	@Override
	public void setShapeColor(ColorStateList color) {
		if (mShapeColorList != color) {
			mShapeColorList = color;
			mShapeColor = mShapeColorList.getColorForState(getDrawableState(), mShapeColor);
			mIndicator.setBackgroundColor(mShapeColor);
		}
	}

	@Override
	public ColorStateList getLabelColorList() {
		return mLabel.getTextColors();
	}

	@Override
	public int getLabelColor() {
		return mLabelColor;
	}

	@Override
	public void setLabelColor(int color) {
		if (mLabelColor != color) {
			mLabelColor = color;
			mLabel.setTextColor(color);
		}
	}

	@Override
	public void setLabelColor(ColorStateList labelColor) {
		mLabel.setTextColor(labelColor);
	}

	@Override
	public float getLabelSize() {
		return mLabel.getTextSize();
	}

	public void setLabelSize(float labelSize) {
		setLabelSize(TypedValue.COMPLEX_UNIT_SP, labelSize);
		labelSizeChanged();
	}

	public void setLabelSize(int unit, float labelSize) {
		mLabel.setTextSize(unit, labelSize);
		labelSizeChanged();
	}

	private void labelSizeChanged() {
		ViewGroup.LayoutParams params = mIndicator.getLayoutParams();
		int size = (int) mLabel.getTextSize();
		params.width = size;
		params.height = size;
		requestLayout();
	}

	@Override
	public Rect getLabelBounds() {
		Rect bounds = new Rect();
		TextUtil.getBounds(mLabel.getEditableText().toString(), bounds, getPaint());
		return bounds;
	}

	public int getGap() {
		return mGap;
	}

	public void setGap(int gap) {
		if (mGap != gap) {
			mGap = gap;
			requestLayout();
		}
	}

	@Override
	public Paint getPaint() {
		return mLabel.getPaint();
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		int[] stateSet = getDrawableState();
		mShapeColor = mShapeColorList.getColorForState(stateSet, DEFAULT_SHAPE_COLOR);
		mLabelColor = mLabel.getTextColors().getColorForState(stateSet, DEFAULT_LABEL_COLOR);
	}
}
