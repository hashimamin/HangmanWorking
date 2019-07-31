package com.notanexample.hangman;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


public class HangmanView extends View {
    Paint paint = new Paint();
    int attempts;

    private void init() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(8);
        setAttempts(0);
    }

    public void setAttempts(int a) {
        attempts = a;
        invalidate();
    }

    public HangmanView(Context context) {
        super(context);
        init();
    }

    public HangmanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HangmanView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();

        path.moveTo(getWidth()*0.2f, 0);
        path.lineTo(getWidth()*0.2f, 0);
        path.lineTo(getWidth()*0.2f, getHeight()*0.4f);
        path.addOval(getWidth()*0.2f, getHeight()*0.35f, getWidth()*0.2f + getWidth()*0.6f ,getHeight()*0.43f, Path.Direction.CW);

        if (attempts >= 1) {
            path.addCircle(getWidth() * 0.5f, getHeight() * 0.2f, getWidth() * 0.2f, Path.Direction.CW);
            path.moveTo(getWidth() * 0.5f, getHeight() * 0.29f);
            path.lineTo(getWidth() * 0.5f, getHeight() * 0.55f);
        }
        if (attempts >= 2) {
            path.moveTo(getWidth() * 0.5f, getHeight() * 0.55f);
            path.lineTo(getWidth() * 0.15f, getHeight() * 0.45f);
        }
        if (attempts >= 3) {
            path.moveTo(getWidth() * 0.5f, getHeight() * 0.55f);
            path.lineTo(getWidth() * 0.85f, getHeight() * 0.45f);
        }
        if (attempts >= 4) {
            path.moveTo(getWidth() * 0.5f, getHeight() * 0.55f);
            path.lineTo(getWidth() * 0.5f, getHeight() * 0.8f); //stem
            path.lineTo(getWidth() * 0.15f, getHeight() * 0.95f);
        }
        if (attempts >= 5) {
            path.moveTo(getWidth() * 0.5f, getHeight() * 0.8f);
            path.lineTo(getWidth() * 0.85f, getHeight() * 0.95f);
        }
        canvas.drawPath(path, paint);
    }

}
