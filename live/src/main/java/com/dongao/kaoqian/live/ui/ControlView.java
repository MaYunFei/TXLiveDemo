package com.dongao.kaoqian.live.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongao.kaoqian.live.R;
import com.dongao.kaoqian.live.view.LiveControlView;

/**
 * Created by mayunfei on 17-9-19.
 */

public class ControlView extends FrameLayout implements LiveControlView ,View.OnClickListener {
    private View layout_control_up;
    private View layout_control_right;

    private Animation mAnimSlideInTop;
    private Animation mAnimSlideInRight;
    private Animation mAnimSlideOutTop;
    private Animation mAnimSlideOutRight;

    private ImageView iv_back,iv_play_audio,iv_full_screen;
    private TextView tv_clarity,tv_change;

    public ControlView(@NonNull Context context) {
        this(context, null);
    }

    public ControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControlView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.control_view, this);
        initAnimal();
        initControlView();
        initListener();
    }

    private void initListener() {
        setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_play_audio.setOnClickListener(this);
        iv_full_screen.setOnClickListener(this);
        tv_clarity.setOnClickListener(this);
        tv_change.setOnClickListener(this);
    }

    private void initControlView() {
        layout_control_up = findViewById(R.id.layout_control_up);
        layout_control_right = findViewById(R.id.layout_control_right);
        iv_back = findViewById(R.id.iv_back);
        iv_play_audio = findViewById(R.id.iv_play_audio);
        iv_full_screen = findViewById(R.id.iv_full_screen);
        tv_clarity = findViewById(R.id.tv_clarity);
        tv_change = findViewById(R.id.tv_change);

    }

    private void initAnimal() {
        mAnimSlideInTop = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_in_top);
        mAnimSlideOutTop = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_out_top);
        mAnimSlideInRight = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_in_right);
        mAnimSlideOutRight = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_out_right);
    }

    @Override
    public void showControlView() {
        if (layout_control_up.getVisibility() == GONE){
            layout_control_up.setVisibility(VISIBLE);
            iv_back.startAnimation(mAnimSlideInTop);
            iv_play_audio.startAnimation(mAnimSlideInTop);
            iv_full_screen.startAnimation(mAnimSlideInTop);
            tv_change.startAnimation(mAnimSlideInRight);
            tv_clarity.startAnimation(mAnimSlideInRight);
        }
    }

    @Override
    public void hideControlView() {
        if (layout_control_up.getVisibility() == VISIBLE){
            iv_back.startAnimation(mAnimSlideOutTop);
            iv_play_audio.startAnimation(mAnimSlideOutTop);
            iv_full_screen.startAnimation(mAnimSlideOutTop);
            tv_change.startAnimation(mAnimSlideOutRight);
            tv_clarity.startAnimation(mAnimSlideOutRight);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_control:
                showControlView();
                break;
            case R.id.iv_back:
                break;
            case R.id.iv_play_audio:
                break;
            case R.id.iv_full_screen:
                break;
            case R.id.tv_clarity:
                break;
            case R.id.tv_change:
                break;
        }
    }
}
