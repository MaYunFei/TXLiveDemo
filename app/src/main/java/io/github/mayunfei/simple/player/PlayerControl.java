package io.github.mayunfei.simple.player;

/**
 * Created by mayunfei on 17-9-11.
 */

public interface PlayerControl {
    /**
     * 暂停播放器
     */
    void pausePlayer();

    /**
     * 暂停重启
     */
    void resumePlayer();

    /**
     * 启动播放器
     */
    void startPlayer();
}
