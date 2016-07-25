package ru.yandex.yamblz.ui.android_views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

class AbsCanvasView extends View implements View.OnClickListener {
    private static final int LINES_COUNT = 10;

    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private final Rect textRect = new Rect();
    private final float[] verticalLines = new float[LINES_COUNT * 4];
    private final float[] horizontalLines = new float[LINES_COUNT * 4];

    private float gridLineWidth;
    private boolean drawGrid = true;
    private int gridStartX;
    private int gridStartY;
    private int stepX;
    private int stepY;

    public AbsCanvasView(Context context) {
        super(context);
        init();
    }

    public AbsCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbsCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AbsCanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        gridLineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

        linePaint.setColor(Color.BLUE);
        linePaint.setStyle(Paint.Style.STROKE);

        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
        textPaint.setColor(Color.BLUE);
        textPaint.getTextBounds("0", 0, 1, textRect);

        gridStartX = textRect.width();
        gridStartY = textRect.height();

        setOnClickListener(this);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getSize(widthMeasureSpec) < MeasureSpec.getSize(heightMeasureSpec)) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        } else {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int linesCountMinusOne = LINES_COUNT - 1;
        stepX = (getWidth() - gridStartX * 2) / linesCountMinusOne;
        stepY = (getHeight() - gridStartY * 2) / linesCountMinusOne;
        int gridWidth = stepX * linesCountMinusOne;
        int gridHeight = stepY * linesCountMinusOne;

        int verticalLineX = 0;

        for (int i = 0; i < LINES_COUNT; i++) {
            int lineIndex = i * 4;
            verticalLines[lineIndex] = verticalLines[lineIndex + 2] = verticalLineX;
            verticalLines[lineIndex + 1] = 0;
            verticalLines[lineIndex + 3] = gridHeight;
            verticalLineX += stepX;
        }

        int horizontalLineY = 0;

        for (int i = 0; i < LINES_COUNT; i++) {
            int lineIndex = i * 4;
            horizontalLines[lineIndex + 1] = horizontalLines[lineIndex + 3] = horizontalLineY;
            horizontalLines[lineIndex] = 0;
            horizontalLines[lineIndex + 2] = gridWidth;
            horizontalLineY += stepY;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        linePaint.setStrokeWidth(gridLineWidth);
        if (drawGrid) {
            canvas.translate(gridStartX, gridStartY);
            for (int i = 0; i < LINES_COUNT; i++) {
                int lineIndex = i * 4;
                canvas.drawText(String.valueOf(i), verticalLines[lineIndex] + gridStartX / 2, 0, textPaint);
                canvas.drawText(String.valueOf(i), 0 - gridStartX, horizontalLines[lineIndex + 1] + gridStartY * 1.5f, textPaint);
            }

            canvas.translate(gridStartX, gridStartY);

            canvas.drawLines(verticalLines, linePaint);
            canvas.drawLines(horizontalLines, linePaint);
        } else {
            canvas.translate(gridStartX, gridStartY);
            canvas.translate(gridStartX, gridStartY);
        }

        canvas.scale(stepX, stepY);
    }

    @Override
    public void onClick(View v) {
        drawGrid = !drawGrid;
        invalidate();
    }
}
