package io.github.mayunfei.draw_board;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 白板类
 * Created by mayunfei on 17-9-14.
 */

public class Whiteboard extends View {

    private static final String TAG = "Whiteboard";
    private final Canvas mCanvas;
    private Bitmap mBitmap;
    private final Paint mPaint;
    private final Rect mRectSrc;
    private static final int X_MAX = 1600;
    private static final int Y_MAX = 900;
    private final RectF mRectF;


    public Whiteboard(Context context) {
        this(context, null);
    }

    public Whiteboard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Whiteboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float screenWidth = DensityUtils.getWidthInPx(context);
        float height = screenWidth / 16 * 9;
        //坐标系
        mBitmap = Bitmap.createBitmap(X_MAX, Y_MAX, Bitmap.Config.ARGB_8888);
        Log.i(TAG, "Szie = " + mBitmap.getByteCount());
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mRectSrc = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mCanvas = new Canvas(mBitmap);
        drawLine();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = Math.round(measuredWidth / 16f * 9);
        setMeasuredDimension(measuredWidth, measuredHeight);

        mRectF.set(0, 0, measuredWidth, measuredHeight);
        Log.i(TAG, "onMeasure");
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawBitmap(this.mBitmap, 0, 0, this.mPaint);
        canvas.drawBitmap(mBitmap, mRectSrc, mRectF, mPaint);
        Log.i(TAG, "onDraw");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout");
    }

    public void drawLine() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        mCanvas.drawLine(0, 0, X_MAX, Y_MAX, paint);
        Log.i(TAG, "Szie2 = " + mBitmap.getByteCount());
        invalidate();
    }

    public void clear(){
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();
    }

}
