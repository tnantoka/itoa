package com.bornneet.generativepolygon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.*;

/**
 * Created by tnantoka on 7/17/16.
 */
public class PolygonView extends View {

    int circuits = 3;
    Bitmap bitmap;

    private Paint paint = new Paint();

    public PolygonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);

        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas bitmapCanvas = new Canvas(bitmap);

//        bitmapCanvas.drawColor(Color.WHITE);

        for (int i = 0; i < getPolygons(); i++) {
            drawPolygon(bitmapCanvas);
        }

        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    private void drawPolygon(Canvas canvas) {
        float radius = getRadius();

        PointF firstPoint = getPoint(0, radius);
        PointF lastPoint = firstPoint;

        int number = getNumber();
        int step = 360 / number;

        for (int i = 1; i <= 360; i += step) {
            paint.setColor(getColor());
            paint.setAlpha(200);

            float degree = i;
            PointF point = getPoint(degree, radius);
            canvas.drawLine(lastPoint.x, lastPoint.y, point.x, point.y, paint);

            lastPoint = point;
            radius = getRadius();
        }

        canvas.drawLine(lastPoint.x, lastPoint.y, firstPoint.x, firstPoint.y, paint);
    }

    private PointF getPoint(float degree, float radius) {
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;

        double radian = degree * Math.PI / 180;
        float x = (float)Math.cos(radian) * radius + centerX;
        float y = (float)Math.sin(radian) * radius + centerY;

        return new PointF(x, y);
    }

    private float getRadius() {
        float maxRadius = 400;
        float step = maxRadius / circuits;

        List<Float> radiuses = new ArrayList<Float>();

        for (float i = step; i <= maxRadius; i += step) {
            radiuses.add(i);
        }

        shuffle(radiuses);

        return radiuses.get(0);
    }

    private int getColor() {
        int[] colors = {
                Color.parseColor("#f44336"),
                Color.parseColor("#e91e63"),
                Color.parseColor("#9c27b0"),
                Color.parseColor("#673ab7"),
                Color.parseColor("#3f51b5"),
                Color.parseColor("#2196f3"),
                Color.parseColor("#03a9f4"),
                Color.parseColor("#00bcd4"),
                Color.parseColor("#009688"),
                Color.parseColor("#4caf50"),
                Color.parseColor("#8bc34a"),
                Color.parseColor("#cddc39"),
                Color.parseColor("#ffeb3b"),
                Color.parseColor("#ffc107"),
                Color.parseColor("#ff9800"),
                Color.parseColor("#ff5722"),
                Color.parseColor("#795548"),
                Color.parseColor("#9e9e9e"),
                Color.parseColor("#607d8b"),
        };
        return colors[new Random().nextInt(colors.length)];
    }

    private int getPolygons() {
        return new Random().nextInt(3) + circuits;
    }

    private int getNumber() {
        return new Random().nextInt(100) + 50;
    }
}
