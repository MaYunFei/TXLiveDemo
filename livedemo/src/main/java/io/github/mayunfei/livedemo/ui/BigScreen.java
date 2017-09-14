package io.github.mayunfei.livedemo.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by AlphaGo on 2017/9/15 0015.
 */

public class BigScreen extends FrameLayout {
    public BigScreen(@NonNull Context context) {
        this(context,null);
    }

    public BigScreen(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BigScreen(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = Math.round(measuredWidth / 16f * 9);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}
