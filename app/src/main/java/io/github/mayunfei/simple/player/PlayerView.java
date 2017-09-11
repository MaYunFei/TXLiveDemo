package io.github.mayunfei.simple.player;

/**
 * Created by mayunfei on 17-9-11.
 */

public interface PlayerView {
    /**
     * 显示暂停ui
     */
    void showPauseUI();

    /**
     * 显示播放ui
     */
    void showPlayingUI();

    /**
     * 显示结束UI
     */
    void showStopUI();

    /**
     * 显示下课UI
     */
    void showRestUI();

    /**
     * 显示错误UI
     */
    void showErrorUI();

    void showLoading();

    void hideLoading();
}
