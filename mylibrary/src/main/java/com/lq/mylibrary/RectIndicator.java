package com.lq.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by lq on 2018/3/13.
 */

public class RectIndicator extends View {
    private int mCount;
    private float moveX;
    //没什么用貌似
    private float x, y;
    private float strokeWidth;
    private int fillColor;
    private float radio;
    private Paint fillPaint;
    private float length;
    private Paint emptyPaint;
    private float dividerWidth;
    private Paint textPaint;
    private int currentPosition;
    private int textColor;
    private int stokeColor;

    public RectIndicator(Context context) {
        this(context, null);
    }

    public RectIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RectIndicator);
        strokeWidth = array.getFloat(R.styleable.RectIndicator_rect_stoke_width, 2f);
        fillColor = array.getColor(R.styleable.RectIndicator_rect_fill_color, getResources().getColor(R.color.white));
        length = array.getFloat(R.styleable.RectIndicator_rect_length, 50f);
        mCount = array.getInt(R.styleable.RectIndicator_rect_count, 3);
        stokeColor = array.getColor(R.styleable.RectIndicator_rect_stoke_color, Color.BLACK);
        textColor = array.getColor(R.styleable.RectIndicator_rect_text_color, Color.BLACK);
        dividerWidth = array.getDimension(R.styleable.RectIndicator_rect_divider_width, 20);
        array.recycle();

        init(context);
    }

    private void init(Context context) {
        emptyPaint = new Paint();
        emptyPaint.setStrokeWidth(strokeWidth);
        emptyPaint.setStyle(Paint.Style.STROKE);
        emptyPaint.setAntiAlias(true);
        emptyPaint.setColor(stokeColor);

        fillPaint = new Paint();
        fillPaint.setStrokeWidth(strokeWidth);
        fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        fillPaint.setAntiAlias(true);
        fillPaint.setColor(fillColor);

        textPaint = new Paint();
        textPaint.setTextSize(40);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = manager.getDefaultDisplay().getWidth();
        radio = (length + strokeWidth * 2 + dividerWidth) / screenWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //静止的矩形间距
        float distance = length + strokeWidth * 2 + dividerWidth;
        //画静止的矩形
        for (int i = 0; i < mCount; i++) {
            canvas.drawRect(x + distance * i, y, x + distance * i + length, y + length, emptyPaint);
        }

        //画移动的矩形
        canvas.drawRect(moveX, y, moveX + length + strokeWidth * 2, y + length, fillPaint);

        //画数字
        String text = String.valueOf(currentPosition + 1);
        canvas.drawText(String.valueOf(currentPosition + 1), moveX + length / 4 + strokeWidth, y + length * 3 / 4, textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float width, height;
        width = mCount * (length + strokeWidth * 2) + (mCount - 1) * dividerWidth;
        height = length + strokeWidth * 2;

        setMeasuredDimension((int) width, (int) height);
    }

    public void moveRect(int position, float location, int viewPagerCount) {
        if (viewPagerCount != mCount) {
            throw new RuntimeException("ViewPager的Item数量必须和指示器Item的数量一致");
        }
        float distance = length + strokeWidth * 2 + dividerWidth;
        float first = strokeWidth;
        moveX = first + position * distance + location * radio;
        currentPosition = position;
        invalidate();
    }
}
