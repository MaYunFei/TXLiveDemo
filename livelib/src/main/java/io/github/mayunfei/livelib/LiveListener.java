package io.github.mayunfei.livelib;

/**
 * Created by mayunfei on 17-9-8.
 */

public interface LiveListener {
    void onVideoPrepared();

    void onVideoPlaying();
    /**
     * 缓冲中
     */
    void onVideoLoading();

    void onVideoConnectError();

    void onVideoWarningReconnect();

    void onVideoPause();

    void onVideNetEvent();
}
