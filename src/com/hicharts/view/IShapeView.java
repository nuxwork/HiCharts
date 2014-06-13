package com.hicharts.view;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.Rect;

import com.hicharts.shape.Shape;

public interface IShapeView<T extends Shape> {
	// TODO 默认值使用defStyleAttr来完成
	public static final int	DEFAULT_SHAPE_COLOR	= 0xFF8d8d8d;
	public static final int	DEFAULT_LABEL_COLOR	= 0xFFeeeeee;
	public static final int	DEFAULT_LABEL_SIZE	= 20;

	void setShape(T shape);

	T getShape();

	String getLabel();

	void setLabel(String label);

	ColorStateList getShapeColorList();

	int getShapeColor();

	void setShapeColor(int color);

	void setShapeColor(ColorStateList color);

	ColorStateList getLabelColorList();

	int getLabelColor();

	void setLabelColor(int color);

	void setLabelColor(ColorStateList labelColor);

	float getLabelSize();

	void setLabelSize(float labelSize);

	void setLabelSize(int unit, float labelSize);

	Rect getLabelBounds();

	Paint getPaint();
}
