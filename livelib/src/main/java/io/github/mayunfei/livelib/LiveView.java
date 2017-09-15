package io.github.mayunfei.livelib;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by AlphaGo on 2017/9/14 0014.
 */

public class LiveView extends VideoControlView {
    private ContentLoadingProgressBar pb_loading;

    public LiveView(@NonNull Context context) {
        this(context, null);
    }

    public LiveView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.live_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        pb_loading = findViewById(R.id.pb_loading);
    }


    private void showLoading(){
        pb_loading.setVisibility(VISIBLE);
    }

    private void hideLoading(){
        pb_loading.setVisibility(GONE);
    }

    @Override
    public void onVideoPrepared() {

    }

    @Override
    public void onConnectSuccess() {
        hideLoading();
    }

    @Override
    public void onVideoPlaying() {
        hideLoading();
    }

    @Override
    public void onVideoLoading() {
        showLoading();
    }

    @Override
    public void onVideoConnectError() {
        hideLoading();
    }

    @Override
    public void onVideoWarningReconnect() {
        showLoading();
    }

    @Override
    public void onVideoPause() {

    }

    @Override
    public void onVideoNetEvent() {

    }

    @Override
    public void onPlayUrlError() {

    }
}
