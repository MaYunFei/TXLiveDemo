package io.github.mayunfei.simple.player;

/**
 * Created by mayunfei on 17-9-8.
 */

public interface LiveListener {
    void onPrepared();

    void onStart();

    void onLoading();

    void onConnectError();

    void onWarningReconnect();

    //i切换白板
    void onChange();

    void onVideoPause();
}
