package io.github.mayunfei.draw_board;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * 用于展示PPT
 * Created by mayunfei on 17-9-14.
 */

public class PPTView extends AppCompatImageView {

    private int[] ppts = {R.mipmap.img_special_404, R.mipmap.img_special_dataerror, R.mipmap.img_special_nonetwork, R.mipmap.img_special_nopermission, R.mipmap.img_special_nothing, R.mipmap.img_special_success};

    public PPTView(Context context) {
        this(context,null);
    }

    public PPTView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PPTView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setData();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = Math.round(measuredWidth / 16f * 9);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    public void setData(){
        setImageResource(ppts[0]);
    }

    public void change(){
        setImageResource(ppts[new Random().nextInt(ppts.length)]);
    }


}
