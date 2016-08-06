package com.bornneet.dotpict;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by tnantoka on 8/5/16.
 */
public class PictureView extends View {

    boolean grid = false;
    int quality = 16;
    int[] colors = {
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.DKGRAY,
            Color.GRAY,
            Color.GREEN,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.RED,
            Color.TRANSPARENT,
            Color.WHITE,
            Color.YELLOW
    };
    private static final int defaultBackgroundColor = Color.WHITE;
    private static final int defaultForegroundColor = Color.BLACK;
    int color = defaultForegroundColor;

    private int dots[][];
    private Paint paint = new Paint();

    public PictureView(Context context, AttributeSet attrs) {
        super(context, attrs);

        dots = new int[quality][quality];
        for (int i = 0; i < quality; i++) {
            Arrays.fill(dots[i], defaultBackgroundColor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < quality; i++) {
            int x = i * size();
            for (int j = 0; j < quality; j++) {
                int y = j * size();
                paint.setColor(dots[i][j]);
                canvas.drawRect(x, y, x + size(), y + size(), paint);
            }
        }

        if (grid) {
            for (int i = 1; i < quality; i++) {
                int x = i * size();
                int y = i * size();
                paint.setColor(defaultForegroundColor);
                canvas.drawLine(x, 0, x, getHeight(), paint);
                canvas.drawLine(0, y, getWidth(), y, paint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX() / size();
        float y = event.getY() / size();

        int i = (int)Math.floor(event.getX() / size());
        int j = (int)Math.floor(event.getY() / size());
        dots[i][j] = dots[i][j] != color ? color : defaultBackgroundColor;
        invalidate();

        Log.d("x, y", String.valueOf(x) + ", " + String.valueOf(y));
        return super.onTouchEvent(event);
    }

    private int size() {
        return getWidth() / quality;
    }
}
