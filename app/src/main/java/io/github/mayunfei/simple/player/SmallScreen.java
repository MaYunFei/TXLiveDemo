package io.github.mayunfei.simple.player;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by mayunfei on 17-9-11.
 */

public class SmallScreen extends VideoControlView implements PlayerView {
    public SmallScreen(@NonNull Context context) {
        super(context);
    }

    public SmallScreen(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SmallScreen(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = (int) (childWidthSize * 1.0 / 16 * 9);
        setMeasuredDimension(childWidthSize, childHeightSize);
    }

    @Override
    public void onPrepared() {

    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onLoading() {

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
    public void showPauseUI() {

    }

    @Override
    public void showPlayingUI() {

    }

    @Override
    public void showStopUI() {

    }

    @Override
    public void showRestUI() {

    }

    @Override
    public void showErrorUI() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
