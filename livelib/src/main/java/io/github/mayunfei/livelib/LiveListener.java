package io.github.mayunfei.livelib;

/**
 * Created by mayunfei on 17-9-8.
 */

public interface LiveListener {
    void onVideoPrepared();

    /**
     * 链接服务器成功
     */
    void onConnectSuccess();

    /**
     * 正在播放
     */
    void onVideoPlaying();
    /**
     * 缓冲中
     */
    void onVideoLoading();

    /**
     * 链接失败
     */
    void onVideoConnectError();

    /**
     * 重连中
     */
    void onVideoWarningReconnect();

    void onVideoPause();

    void onVideoNetEvent();

    /**
     * 播放链接格式错误
     */
    void onPlayUrlError();
}
