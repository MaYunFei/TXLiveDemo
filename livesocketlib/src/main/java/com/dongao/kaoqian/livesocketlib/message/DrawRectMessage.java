package com.dongao.kaoqian.livesocketlib.message;


import com.dongao.kaoqian.livesocketlib.live.ILiveManager;

/**
 * 矩形
 * Created by mayunfei on 17-9-18.
 */

public class DrawRectMessage extends WhiteBoardMessage {

    @Override
    public void handleMessage(ILiveManager liveManager) {
        liveManager.getWriteBoard().drawRect(this);
    }
}
