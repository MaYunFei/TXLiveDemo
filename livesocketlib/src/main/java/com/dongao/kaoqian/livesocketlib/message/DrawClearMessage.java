package com.dongao.kaoqian.livesocketlib.message;


import com.dongao.kaoqian.livesocketlib.live.ILiveManager;

/**
 * 清空
 * Created by mayunfei on 17-9-18.
 */

public class DrawClearMessage extends WhiteBoardMessage {

    @Override
    public void handleMessage(ILiveManager liveManager) {
        liveManager.getWriteBoard().drawClearAll(this);
    }
}
