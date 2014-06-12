package com.hicharts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.hicharts.shape.Rectangle;
import com.hicharts.util.TextUtil;

public class LegendItem extends ShapeView<Rectangle> {

	private Rectangle	mRectangle;

	public LegendItem(Context context) {
		super(context);
	}

	public LegendItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LegendItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	protected void onCreate(Context context, AttributeSet attrs, int defStyleAttr) {
		super.onCreate(context, attrs, defStyleAttr);
		Rectangle rect = getShape();
	}

	@Override
	public Rectangle getShape() {
		if (mRectangle == null) {
			mRectangle = new Rectangle();
		}
		return mRectangle;
	}

	@Override
	public void setShape(Rectangle shape) {
		if (mRectangle != shape) {
			mRectangle = shape;
			invalidate();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = 0;
		int height = 0;

		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		}

		Rect bounds = getLabelBounds();
		int bw = bounds.width();
		int bh = bounds.height();

		if (widthMode != MeasureSpec.EXACTLY) {
			width = (int) (bw + bh + bh);
		}

		if (heightMode != MeasureSpec.EXACTLY) {
			height = bh;
		}

		setMeasuredDimension(width, height);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		Rect bounds = getLabelBounds();
		int bh = bounds.height();
		mRectangle.setArea(0, 0, bh, bh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = getPaint();
		paint.setColor(getShapeColor());
		mRectangle.draw(canvas, paint);

		// draw label
		String label = getLabel();
		if (TextUtils.isEmpty(label) || getShape() == null)
			return;

		Rect bounds = getLabelBounds();
		int bh = bounds.height();
		float x = bh + bh;
		float y = bh / 2f;

		paint.setColor(getLabelColor());
		paint.setTextSize(getLabelSize());
		TextUtil.draw(canvas, paint, Align.LEFT, x, y, label.split("\\n"));
	}
}
