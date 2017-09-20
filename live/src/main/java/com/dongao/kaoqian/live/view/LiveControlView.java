package com.dongao.kaoqian.live.view;

/**
 * Created by mayunfei on 17-9-19.
 */

public interface LiveControlView {
    void showControlView();
    void hideControlView();

    /**
     * 显示并过一段时间隐藏
     */
    void  showControlDelayedHide();

    /**
     * 隐藏全屏按钮
     */
    void hideFullScreen();

    void showFullScreen();

    void showPPT();
    void showVideo();
    void showChange();

}
