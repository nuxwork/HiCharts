package com.hicharts.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.hicharts.R;
import com.hicharts.shape.Sector;
import com.hicharts.util.Math2;
import com.hicharts.util.TextUtil;

public class Pie extends ShapeView<Sector> {
	private static final String TAG = "MCharts.Pie";

	private Sector mSector;
	private float mStartAngle;
	private float mSweepAngle;

	public Pie(Context context) {
		super(context);
	}

	public Pie(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Pie(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onCreate(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super.onCreate(context, attrs, defStyleAttr);

		if (attrs != null) {
			TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
					R.styleable.Pie, defStyleAttr, 0);

			mStartAngle = a.getFloat(R.styleable.Pie_startAngle, 0f);
			mSweepAngle = a.getFloat(R.styleable.Pie_sweepAngle, Math2.PERIGON);
			a.recycle();
		} else {
			mStartAngle = 0f;
			mSweepAngle = Math2.PERIGON;
		}

		Sector sector = getShape();
		sector.setSweepAngle(mSweepAngle);
		sector.setStartAngle(mStartAngle);
	}

	public Sector getShape() {
		if (mSector == null)
			mSector = new Sector();

		return mSector;
	}

	@Override
	public void setShape(Sector shape) {
		if (mSector != shape && !mSector.equals(shape)) {
			mSector = shape;
			invalidate();
		}
	}

	public float getStartAngle() {
		return mStartAngle;
	}

	public void setStartAngle(float startAngle) {
		if (mStartAngle != startAngle) {
			mStartAngle = startAngle;
			if (mSector != null) {
				mSector.setStartAngle(mStartAngle);
			}
			invalidate();
		}
	}

	public float getSweepAngle() {
		return mSweepAngle;
	}

	public void setSweepAngle(float sweepAngle) {
		if (mSweepAngle != sweepAngle) {
			mSweepAngle = sweepAngle;
			if (mSector != null) {
				mSector.setSweepAngle(sweepAngle);
			}
			invalidate();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Sector shape = getShape();
		Paint paint = getPaint();
		int[] state = getDrawableState();

		if (shape != null) {
			paint.setColor(getShapeColor());
			shape.draw(canvas, paint);
		}

		// draw label
		String label = getLabel();
		if (!TextUtils.isEmpty(label) && shape != null) {
			paint.setColor(getLabelColorList().getColorForState(state,
					DEFAULT_LABEL_COLOR));
			paint.setTextSize(getLabelSize());
			TextUtil.draw(canvas, paint, shape.getCenter(), label.split("\\n"));
		}
	}
}
