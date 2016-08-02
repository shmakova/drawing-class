package ru.yandex.yamblz.ui.android_views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {
    private final int paintColor = Color.BLACK;
    private Paint drawPaint;
    private Path path = new Path();

    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        // Checks for the event that occurs
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Starts a new line in the path
                path.moveTo(pointX, pointY);
                break;
            case MotionEvent.ACTION_MOVE:
                // Draws line between last point and this point
                path.lineTo(pointX, pointY);
                break;
            default:
                return false;
        }

        postInvalidate(); // Indicate view should be redrawn
        return true; // Indicate we've consumed the touch
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(0.1f);
//        canvas.drawCircle(4f, 4f, 3f, paint);
//        canvas.drawLine(4f, 1f, 1f, 1f, paint);
//        canvas.drawLine(1f, 1f, 1f, 4f, paint);
//
//        canvas.drawLine(4f, 1f, 7f, 1f, paint);
//        canvas.drawLine(7f, 1f, 7f, 4f, paint);
//
//        canvas.drawPoint(3f, 3f, paint);
//        canvas.drawCircle(3f, 3f, 0.2f, paint);
//
//        canvas.drawPoint(5f, 3f, paint);
//        canvas.drawCircle(5f, 3f, 0.2f, paint);
//        canvas.drawPoint(4f, 4f, paint);
//        canvas.drawLine(3.5f, 5.5f, 4.5f, 5.5f, paint);

        canvas.drawPath(path, drawPaint);

        //draw please
    }
}
