package com.dongao.kaoqian.livesocketlib.message;


import com.dongao.kaoqian.livesocketlib.live.ILiveManager;

/**
 * Created by mayunfei on 17-9-18.
 */

public interface ILiveMessage {
    void handleMessage(ILiveManager liveManager);
}
