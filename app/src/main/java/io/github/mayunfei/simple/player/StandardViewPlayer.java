package io.github.mayunfei.simple.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import io.github.mayunfei.simple.R;

/**
 * Created by mayunfei on 17-9-8.
 */

public class StandardViewPlayer extends VideoControlView implements PlayerView {
    public interface VideoPlayerFullScreenListener {
        void onVideoPlayerFullScreen(boolean isScreen);
    }



    private VideoPlayerFullScreenListener videoPlayerFullScreenListener;

    public void setVideoPlayerFullScreenListener(VideoPlayerFullScreenListener videoPlayerFullScreenListener) {
        this.videoPlayerFullScreenListener = videoPlayerFullScreenListener;
    }

    private boolean isFullScreen = false;
    private static final String TAG = "StandardViewPlayer";

    private ImageView iv_start, iv_fullscreen;
    private ContentLoadingProgressBar loadingBar;

    public StandardViewPlayer(@NonNull Context context) {
        this(context, null);
    }

    public StandardViewPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.standard_player, this);
        initView();
        initEvent();

    }

    private void initView() {
        iv_start = findViewById(R.id.iv_start);
        iv_fullscreen = findViewById(R.id.iv_fullscreen);
        loadingBar = findViewById(R.id.pb_loading);
    }

    private void initEvent() {
        //全屏按钮
        iv_fullscreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                isFullScreen = !isFullScreen;
                if (videoPlayerFullScreenListener != null) {
                    videoPlayerFullScreenListener.onVideoPlayerFullScreen(isFullScreen);
                }
            }
        });

        iv_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LivePlayerManager.getInstance().isPause()) {
                    LivePlayerManager.getInstance().onResume();
                    showPlayingUI();
                } else {
                    LivePlayerManager.getInstance().pause();
                    showPauseUI();
                }
            }
        });
    }


    @Override
    public void onPrepared() {
        Log.i(TAG, "准备播放");
    }

    @Override
    public void onPlaying() {
        Log.i(TAG, "开始播放 切换UI");
        showPlayingUI();
    }

    @Override
    public void onLoading() {
        showLoading();
    }

    @Override
    public void onConnectError() {

    }

    @Override
    public void onWarningReconnect() {

    }

    @Override
    public void onChange() {

    }

    @Override
    public void onVideoPause() {

    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        Log.i("YunFei", " visibility = " + visibility + " width = " + getWidth() + " height = " + getHeight());
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    public void showPauseUI() {
        hideLoading();
        iv_start.setImageResource(R.mipmap.video_play_normal);
    }

    @Override
    public void showPlayingUI() {
        hideLoading();
        hideControlUi();
        iv_start.setImageResource(R.mipmap.video_pause_normal);
    }

    private void hideControlUi() {
        iv_start.setVisibility(GONE);
    }

    @Override
    public void showStopUI() {
        hideLoading();
    }

    @Override
    public void showRestUI() {
        hideLoading();
    }

    @Override
    public void showErrorUI() {
        hideLoading();
    }

    @Override
    public void showLoading() {
        if (loadingBar.getVisibility() == GONE)
        loadingBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (loadingBar.getVisibility() == VISIBLE)
            loadingBar.setVisibility(GONE);
    }
}
