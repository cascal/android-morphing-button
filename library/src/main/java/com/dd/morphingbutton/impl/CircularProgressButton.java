package com.dd.morphingbutton.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.dd.morphingbutton.IProgress;
import com.dd.morphingbutton.MorphingButton;

/**
 * Created by Casey on 8/10/2017.
 */

public class CircularProgressButton extends MorphingButton implements IProgress {

    public static final int MAX_PROGRESS = 100;
    public static final int MIN_PROGRESS = 0;

    private int mProgress;
    private int mProgressColor;
    private Paint mPaint;
    private RectF mRect;
    private int mRadius;
    private int mStrokeWidth;

    public CircularProgressButton(Context context) {
        super(context);
    }

    public CircularProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (!mAnimationInProgress && mProgress > MIN_PROGRESS && mProgress <= MAX_PROGRESS) {
            if (mPaint == null) {
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(mStrokeWidth);
                mPaint.setColor(mProgressColor);
            }

            if (mRect == null) {
                mRect = new RectF();
            }

            int cx = getWidth() / 2;
            int cy = getHeight() / 2;
            int offset = mRadius - mStrokeWidth / 2;
            mRect.set(
                    cx - offset,
                    cy - offset,
                    cx + offset,
                    cy + offset
            );
            canvas.drawArc(mRect, -90f, mProgress / (float) MAX_PROGRESS * 360f, false, mPaint);
        }
    }

    @Override
    public void morph(@NonNull Params params) {
        super.morph(params);
        mProgress = MIN_PROGRESS;
        mPaint = null;
    }

    @Override
    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public void morphToProgress(int color, int progressColor, int radius, int strokeWidth, int duration) {
        mProgressColor = progressColor;
        mRadius = radius;
        mStrokeWidth = strokeWidth;
        MorphingButton.Params longRoundedSquare = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(mRadius)
                .width(mRadius  * 2)
                .height(mRadius * 2)
                .color(color)
                .colorPressed(color);
        morph(longRoundedSquare);
    }
}
