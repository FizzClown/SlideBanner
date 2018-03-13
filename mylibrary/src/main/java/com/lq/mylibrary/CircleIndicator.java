package com.lq.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/3/13.
 */

public class CircleIndicator extends View {
    private int mCount;
    private float moveX;
    private float x, y;
    private float strokeWidth;
    private int fillColor;
    private float radio;
    private Paint fillPaint;
    private float radius;
    private Paint emptyPaint;
    private float dividerWidth;
    private int emptyColor;

    public CircleIndicator(Context context) {
        this(context, null);
    }

    public CircleIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
        strokeWidth = array.getFloat(R.styleable.CircleIndicator_circle_stoke_width, 0.1f);
        fillColor = array.getColor(R.styleable.CircleIndicator_circle_fill_color, getResources().getColor(R.color.white));
        radius = array.getFloat(R.styleable.CircleIndicator_radius, 15f);
        mCount = array.getInt(R.styleable.CircleIndicator_circle_count, 3);
        emptyColor=array.getColor(R.styleable.CircleIndicator_circle_empty_color,getResources().getColor(R.color.alpha_white));
        dividerWidth = array.getDimension(R.styleable.CircleIndicator_circle_divider_width, 15);
        array.recycle();

        init(context);
    }

    private void init(Context context) {
        moveX = strokeWidth + radius;
        x = strokeWidth + radius;
        y = strokeWidth + radius;

        emptyPaint = new Paint();
        emptyPaint.setStrokeWidth(strokeWidth);
        emptyPaint.setStyle(Paint.Style.FILL);
        emptyPaint.setAntiAlias(true);
        emptyPaint.setColor(emptyColor);

        fillPaint = new Paint();
        fillPaint.setStrokeWidth(strokeWidth);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAntiAlias(true);
        fillPaint.setColor(fillColor);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = manager.getDefaultDisplay().getWidth();
        radio = (radius * 2 + strokeWidth * 2 + dividerWidth) / screenWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //静止的圆间距
        float distance = radius * 2 + strokeWidth * 2 + dividerWidth;
        //画静止的圆
        for (int i = 0; i < mCount; i++) {
            canvas.drawCircle(x + distance * i, y, radius, emptyPaint);
        }

        //画移动的圆
        canvas.drawCircle(moveX, y, radius, fillPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float width, height;
        width = mCount * (radius * 2 + strokeWidth * 2) + (mCount - 1) * dividerWidth;
        height = radius * 2 + strokeWidth * 2;

        setMeasuredDimension((int) width, (int) height);
    }

    public void moveCircle(int position, float location, int count) {
        if (count != mCount) {
            throw new RuntimeException("ViewPager的Item数量必须和指示器Item的数量一致");
        }
        float distance = radius * 2 + strokeWidth * 2 + dividerWidth;
        float first = strokeWidth + radius;
        moveX = first + position * distance + location * radio;
        invalidate();
    }
}
