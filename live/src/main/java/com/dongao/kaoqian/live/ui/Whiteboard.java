package com.dongao.kaoqian.live.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.dongao.kaoqian.livesocketlib.live.IWhiteBoard;
import com.dongao.kaoqian.livesocketlib.message.ChangePPTMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawArrowMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawClearMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawEllipseMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawEraseMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawLineMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawRectMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawTextMessage;
import com.dongao.kaoqian.livesocketlib.message.DrawTextSaveMessage;
import com.dongao.kaoqian.livesocketlib.message.LiveMessage;
import com.dongao.kaoqian.livesocketlib.message.WhiteBoardMessage;

import java.util.List;

/**
 * 白板类
 * Created by mayunfei on 17-9-14.
 */

public class Whiteboard extends AppCompatImageView implements IWhiteBoard {

    private static final String TAG = "Whiteboard";
    private final Canvas mCanvas;
    private Bitmap mBitmap;
    private final Paint mPaint;
    private final Rect mRectSrc;
    //参考坐标
    private static final int X_MAX = 2000;
    private static final int Y_MAX = 1125;
    private final RectF mRectF;
    Paint mDrawPaint = new Paint();
    private int mPptIndex = 0;


    public Whiteboard(Context context) {
        this(context, null);
    }

    public Whiteboard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Whiteboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBitmap = Bitmap.createBitmap(X_MAX, Y_MAX, Bitmap.Config.ARGB_8888);
        Log.i(TAG, "Szie = " + mBitmap.getByteCount());
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mRectSrc = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mCanvas = new Canvas(mBitmap);
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
        canvas.drawBitmap(mBitmap, mRectSrc, mRectF, mPaint);
        Log.i(TAG, "onDraw");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout");
    }


    @Override
    public void drawLine(DrawLineMessage message) {
        pauseMessage(message, new HandlerPoints() {
            @Override
            public void draw(Paint paint, WhiteBoardMessage.Point point) {
                mCanvas.drawLine(point.getInitX(), point.getInitY(), point.getX(), point.getY(), paint);
            }
        });
    }

    private Paint getPaint(WhiteBoardMessage message) {
        WhiteBoardMessage.Opt opt = message.getData().getOpt();
        mDrawPaint.reset();
        mDrawPaint.setAntiAlias(true);

        int color = Color.BLACK;
        try {
            color = Color.parseColor(opt.getColor());

        } catch (Exception e) {
            Log.i(TAG, "颜色初始化失败" + opt.getColor());
        }
        mDrawPaint.setColor(color);
        mDrawPaint.setStrokeWidth(opt.getWidth());
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setStrokeCap(Paint.Cap.ROUND);
        return mDrawPaint;
    }

    /**
     * 画圆
     */
    @Override
    public void drawEllipse(DrawEllipseMessage message) {
        pauseMessage(message, new HandlerPoints() {
            @Override
            public void draw(Paint paint, WhiteBoardMessage.Point point) {
                RectF rectF = new RectF(point.getInitX(), point.getInitY(), point.getX(), point.getY());
                mCanvas.drawOval(rectF, paint);
            }
        });
    }

    @Override
    public void drawClearAll(DrawClearMessage message) {
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    @Override
    public void drawRect(DrawRectMessage message) {
        pauseMessage(message, new HandlerPoints() {
            @Override
            public void draw(Paint paint, WhiteBoardMessage.Point point) {
                mCanvas.drawRect(point.getInitX(), point.getInitY(), point.getX(), point.getY(), paint);

            }
        });
    }

    /**
     * 橡皮擦
     */
    @Override
    public void drawErase(DrawEraseMessage message) {
        pauseMessage(message, new HandlerPoints() {
            @Override
            public void draw(Paint paint, WhiteBoardMessage.Point point) {
                paint.setStyle(Paint.Style.FILL);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                if (point.getInitX() == point.getX()&&point.getInitY() == point.getY()){ //点击一下画点
                    mCanvas.drawPoint(point.getInitX(),point.getInitY(),paint);
                }else {
                    mCanvas.drawLine(point.getInitX(), point.getInitY(), point.getX(), point.getY(), paint);
                }

                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            }
        });
    }

    @Override
    public void drawText(DrawTextMessage message) {
        Paint paint = getPaint(message);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(message.getData().getOpt().getWidth());
        List<WhiteBoardMessage.Point> points = message.getData().getPoints();
        List<String> val = message.getData().getOpt().getVal();
        WhiteBoardMessage.Point point = points.get(0);
        for (int i = 0; i <  val.size(); i++) {
            String text = val.get(i);
            mCanvas.drawText(text, point.getX(), point.getY()+(i+1)*message.getData().getOpt().getWidth(), paint);
        }
        invalidate();
    }

    @Override
    public void drawTextSave(DrawTextSaveMessage message){
        WhiteBoardMessage.Opt opt = message.getData().getOpt();
        mDrawPaint.reset();
        mDrawPaint.setAntiAlias(true);

        int color = Color.BLACK;
        try {
            color = Color.parseColor(opt.getColor());

        } catch (Exception e) {
            Log.i(TAG, "颜色初始化失败" + opt.getColor());
        }
        mDrawPaint.setColor(color);
        mDrawPaint.setStrokeWidth(opt.getWidth());
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setStrokeCap(Paint.Cap.ROUND);

//        mCanvas.drawText(text, point.getX(), point.getY()+(i+1)*message.getData().getOpt().getWidth(), paint);

    }

    @Override
    public void changePPPT(ChangePPTMessage message) {
        String current = message.getData().getCurrent();
        if (current.equals(LiveMessage.PPT_BLACK)) {
            if (message.getData().isAction()) {
                setImageResource(android.R.color.white);
            } else {

                Glide.with(getContext()).load(getPptUrl(mPptIndex)).into(this);
            }
            return;
        }
        try {
            Integer index = Integer.valueOf(current);
            mPptIndex = index;
            String pptUrl = getPptUrl(mPptIndex);
            Glide.with(getContext()).load(pptUrl).into(this);
        } catch (Exception e) {

        }
    }

    @Override
    public void drawArrow(DrawArrowMessage message) {
        WhiteBoardMessage.Data data = message.getData();
        WhiteBoardMessage.Opt opt = data.getOpt();
        WhiteBoardMessage.Point points = data.getPoints().get(0);
        double[] point = new double[12];

        double ang = opt.getAng();//箭头头部角度
        double plagioclase = opt.getPlagioclase();//箭头斜长
        double xl = 0,//箭头长度
                angle = 0,//当前斜角度数
                w = 0,//x长
                h = 0;//y长
        float initX = points.getInitX(),
                initY = points.getInitY(),
                x = points.getX(),
                y = points.getY();
        point[0] = points.getInitX();
        point[1] = points.getInitY();
        point[6] = points.getX();
        point[7] = points.getY();
        w = x - initX;
        h = y - initY;
        xl = Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
        if (xl < 10) {
            plagioclase = plagioclase * (10 / 50);
            ang = ang * (10 / 50);
        } else if (xl < 50) {
            plagioclase = plagioclase * (xl / 50);
            ang = ang * (xl / 50);
        }
        angle = Math.atan2(y - initY, x - initX) / Math.PI * 180;//当前箭头旋转角度
        point[8] = x - plagioclase * Math.cos(Math.PI / 180 * (angle + ang));
        point[9] = y - plagioclase * Math.sin(Math.PI / 180 * (angle + ang));
        point[4] = x - plagioclase * Math.cos(Math.PI / 180 * (angle - ang));
        point[5] = y - plagioclase * Math.sin(Math.PI / 180 * (angle - ang));

        WhiteBoardMessage.Point midpoint = new WhiteBoardMessage.Point();

        midpoint.setX((float) ((point[4] + point[8]) / 2));
        midpoint.setY((float) ((point[5] + point[9]) / 2));
        point[2] = (point[4] + midpoint.getX()) / 2;
        point[3] = (point[5] + midpoint.getY()) / 2;
        point[10] = (point[8] + midpoint.getX()) / 2;
        point[11] = (point[9] + midpoint.getY()) / 2;

        Paint paint = getPaint(message);
        paint.setStyle(Paint.Style.FILL);
        //开始绘制
        Path path = new Path();
        path.moveTo((float)(point[0]), (float)(point[1]));
        path.lineTo((float)(point[2]),(float)( point[3]));
        path.lineTo((float)(point[4]),(float)( point[5]));
        path.lineTo((float)(point[6]),(float)( point[7]));
        path.lineTo((float)(point[8]),(float)( point[9]));
        path.lineTo((float)(point[10]),(float)( point[11]));
        mCanvas.drawPath(path,paint);
        invalidate();
    }


    public String getPptUrl(int index) {
        //切换ppt
        String ppturl = "http://dev.img.lvb.dongaocloud.tv/live/616e37d491280e3f1cf5f1f4604f13fb/616e37d491280e3f1cf5f1f4604f13fb-%d.png";
        return String.format(ppturl, index);
    }

    public <T extends WhiteBoardMessage> void pauseMessage(T message, HandlerPoints handlerPoints) {
        Paint paint = getPaint(message);
        List<WhiteBoardMessage.Point> points = message.getData().getPoints();
        for (WhiteBoardMessage.Point point :
                points) {
            handlerPoints.draw(paint, point);
        }
        invalidate();
    }

    interface HandlerPoints {
        void draw(Paint paint, WhiteBoardMessage.Point point);
    }
}
