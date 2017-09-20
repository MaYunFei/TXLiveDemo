package com.dongao.kaoqian.live.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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
 * 界面控制器
 * Created by mayunfei on 17-9-19.
 */

public class ControlView extends FrameLayout implements LiveControlView, View.OnClickListener {
    private static final long DELAY_TIME = 1500;
    private View layout_control_up;
    private View layout_control_right;

    //    private Animation mAnimSlideInTop;
    private Animation mAnimSlideInBottom;
    private Animation mAnimSlideInRight;
    //    private Animation mAnimSlideOutTop;
    private Animation mAnimSlideOutBottom;
    private Animation mAnimSlideOutRight;

    private ImageView iv_back, iv_play_audio, iv_full_screen;
    private TextView tv_clarity, tv_change;

    private MediaControlListener mediaControlListener;
    private static final int WHAT_SHOW = 1;
    private static final int WHAT_HIDE = 2;

    public MediaControlListener getMediaControlListener() {
        return mediaControlListener;
    }

    public void setMediaControlListener(MediaControlListener mediaControlListener) {
        this.mediaControlListener = mediaControlListener;
    }

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
        showChange();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_HIDE:
                    hideControlView();
                    break;
                case WHAT_SHOW:
                    showControlView();
                    break;

            }
        }
    };

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

    @Override
    protected void onDetachedFromWindow() {
        //清理
        super.onDetachedFromWindow();
    }


    private void initAnimal() {
//        mAnimSlideInTop = AnimationUtils.loadAnimation(getContext(),
//                R.anim.slide_in_top);
//        mAnimSlideOutTop = AnimationUtils.loadAnimation(getContext(),
//                R.anim.slide_out_top);
        mAnimSlideInRight = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_in_right);
        mAnimSlideOutRight = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_out_right);
        mAnimSlideInBottom = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_in_bottom);
        mAnimSlideOutBottom = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_out_bottom);
        mAnimSlideOutBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout_control_up.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimSlideOutRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout_control_right.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void showControlView() {
        mHandler.removeMessages(WHAT_HIDE);
        if (layout_control_up.getVisibility() == GONE) {
            layout_control_up.setVisibility(VISIBLE);
            layout_control_right.setVisibility(VISIBLE);
//            iv_back.startAnimation(mAnimSlideInBottom);
//            iv_play_audio.startAnimation(mAnimSlideInBottom);
//            iv_full_screen.startAnimation(mAnimSlideInBottom);
            layout_control_up.startAnimation(mAnimSlideInBottom);
            tv_change.startAnimation(mAnimSlideInRight);
            tv_clarity.startAnimation(mAnimSlideInRight);
        }
    }

    @Override
    public void hideControlView() {
        if (layout_control_up.getVisibility() == VISIBLE) {
//            layout_control_up.setVisibility(GONE);
//            layout_control_right.setVisibility(GONE);
//            iv_back.startAnimation(mAnimSlideOutBottom);
//            iv_play_audio.startAnimation(mAnimSlideOutBottom);
//            iv_full_screen.startAnimation(mAnimSlideOutBottom);
            layout_control_up.startAnimation(mAnimSlideOutBottom);
            tv_change.startAnimation(mAnimSlideOutRight);
            tv_clarity.startAnimation(mAnimSlideOutRight);
        }
    }

    @Override
    public void showControlDelayedHide() {
        showControlView();
        mHandler.sendEmptyMessageDelayed(WHAT_HIDE, DELAY_TIME);
    }

    @Override
    public void hideFullScreen() {
        iv_full_screen.setVisibility(GONE);
    }

    @Override
    public void showFullScreen() {
        iv_full_screen.setVisibility(VISIBLE);
    }

    @Override
    public void showPPT() {
        tv_change.setText("PPT");
        tv_change.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaControlListener.onPPTClick();
            }
        });
    }

    @Override
    public void showVideo() {
        tv_change.setText("视频");
        tv_change.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaControlListener.onVideoClick();
            }
        });
    }

    @Override
    public void showChange() {
        tv_change.setText("切换");
        tv_change.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaControlListener.onChangeClick();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_control:
                if (layout_control_up.getVisibility() == GONE) {
                    showControlDelayedHide();
                } else {
                    hideControlView();
                }
                break;
            case R.id.iv_back:
                mediaControlListener.onBackClick();
                break;
            case R.id.iv_play_audio:
                mediaControlListener.onAudioClick();
                break;
            case R.id.iv_full_screen:
                if (mediaControlListener != null) {
                    mediaControlListener.onFullScreenClick();
                }
                break;
            case R.id.tv_clarity:
                break;
//            case R.id.tv_change:
//                mediaControlListener.onChangeClick();
//                showControlDelayedHide();
//                break;
        }
    }

    public interface MediaControlListener {
        void onFullScreenClick();

        void onChangeClick();

        void onPPTClick();

        void onVideoClick();

        void onBackClick();

        void onAudioClick();
    }
}
