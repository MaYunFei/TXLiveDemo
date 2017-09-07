package io.github.mayunfei.simple;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by mayunfei on 17-9-5.
 */

public class DragGroup extends RelativeLayout {
    private ViewDragHelper mDragger;
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

    private void init() {
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback()
        {
            @Override
            public boolean tryCaptureView(View child, int pointerId)
            {
                return child.getId() == R.id.id_small_screen;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx)
            {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy)
            {
                return top;
            }


        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mDragger.processTouchEvent(event);
        return true;
    }


}
