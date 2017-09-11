package io.github.mayunfei.simple;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import io.github.mayunfei.simple.player.DensityUtils;

/**
 * Created by mayunfei on 17-9-5.
 */

public class DragGroup extends FrameLayout {
    private ViewDragHelper mDragger;
    private View mDragView;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_LANDSCAPE = 1;
    public static final int TYPE_PORTRAIT = 2;
    private int type = TYPE_PORTRAIT;
    // 记录最后的位置
    float mLastX = -1;
    float mLastY = -1;

    public DragGroup(Context context) {
        super(context);
        init();
    }

    public DragGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = findViewById(R.id.id_small_screen);
        setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mDragView.layout((int) mLastX, (int) mLastY,
                (int) mLastX + mDragView.getMeasuredWidth(), (int) mLastY + mDragView.getMeasuredHeight());
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOrientation(int orientation) {
        switch (orientation) {
            case TYPE_LANDSCAPE: //横屏
                mLastX = getMeasuredWidth() - mDragView.getMeasuredWidth();
                mLastY = getMeasuredHeight() - DensityUtils.dip2px(getContext(), 112.5f);
                break;
            case TYPE_PORTRAIT://竖屏
                mLastX = getMeasuredWidth() - mDragView.getMeasuredWidth();
                mLastY = DensityUtils.dip2px(getContext(), 200);
                break;
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        restorePosition();
    }

    public void restorePosition() {
        if (mLastX == -1 && mLastY == -1) { // 初始位置
            setOrientation(TYPE_PORTRAIT);
        }
        if (type == TYPE_PORTRAIT) {
            setOrientation(TYPE_PORTRAIT);
            type = TYPE_NORMAL;
        }
        if (type == TYPE_LANDSCAPE) {
            setOrientation(TYPE_LANDSCAPE);
            type = TYPE_NORMAL;
        }
        mDragView.layout((int) mLastX, (int) mLastY,
                (int) mLastX + mDragView.getMeasuredWidth(), (int) mLastY + mDragView.getMeasuredHeight());
    }

    //    public void restorePosition() {
//        if (mLastX == -1 && mLastY == -1) { // 初始位置
//            mLastX = getMeasuredWidth() - floatingBtn.getMeasuredWidth();
//            mLastY = getMeasuredHeight() * 2 / 3;
//        }
//        floatingBtn.layout((int)mLastX, (int)mLastY,
//                (int)mLastX + floatingBtn.getMeasuredWidth(), (int)mLastY + floatingBtn.getMeasuredHeight());
//    }

    private void init() {
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if (child.getId() == R.id.id_small_screen) {
                    mDragView = child;
                    return true;
                }
                return false;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mDragView.getWidth() - leftBound;
                return Math.min(Math.max(left, leftBound), rightBound);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - mDragView.getHeight() - topBound;
                return Math.min(Math.max(top, topBound), bottomBound);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                mLastX = changedView.getX();
                mLastY = changedView.getY();
            }

        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }


}
