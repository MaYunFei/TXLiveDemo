package com.dongao.kaoqian.live.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import io.github.mayunfei.utilslib.DensityUtils;

/**
 * Created by mayunfei on 17-9-19.
 */

public class SmallScreen extends FrameLayout {
    public SmallScreen(@NonNull Context context) {
        this(context, null);
    }

    public SmallScreen(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmallScreen(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int measuredWidth = (int) (DensityUtils.getWidthInPx(getContext()) / 5 * 3);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        measuredHeight = Math.round(measuredWidth / 16f * 9);
        super.onMeasure(MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));

    }
}
