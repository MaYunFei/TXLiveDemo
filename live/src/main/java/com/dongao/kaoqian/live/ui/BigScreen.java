package com.dongao.kaoqian.live.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 大屏始终能呈现出 16：9
 * Created by AlphaGo on 2017/9/15 0015.
 */

public class BigScreen extends FrameLayout {
    public BigScreen(@NonNull Context context) {
        this(context, null);
    }

    public BigScreen(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigScreen(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST){
            measuredHeight = Math.round(measuredWidth / 16f * 9);
        }else {
            //高度太低了
            if (measuredWidth*1.0f/measuredHeight > 16f/9){
                measuredHeight = Math.round(measuredWidth / 16f * 9);
            }
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));

    }
}
